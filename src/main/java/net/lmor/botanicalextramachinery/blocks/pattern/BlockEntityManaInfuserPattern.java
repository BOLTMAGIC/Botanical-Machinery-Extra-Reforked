package net.lmor.botanicalextramachinery.blocks.pattern;

import appeng.api.config.Actionable;
import appeng.api.networking.GridHelper;
import appeng.api.networking.IGridNode;
import appeng.api.networking.IInWorldGridNodeHost;
import appeng.api.networking.IManagedGridNode;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.util.AECableType;
import appeng.hooks.ticking.TickHandler;
import appeng.me.helpers.BlockEntityNodeListener;
import appeng.me.helpers.IGridConnectedBlockEntity;
import com.google.common.collect.Range;
import net.lmor.botanicalextramachinery.ModBlocks;
import net.lmor.botanicalextramachinery.ModItems;
import net.lmor.botanicalextramachinery.blocks.base.WorkingTile;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaInfuser.BlockEntityManaInfuserAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaInfuser.BlockEntityManaInfuserBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaInfuser.BlockEntityManaInfuserUpgraded;
import net.lmor.botanicalextramachinery.config.LibXServerConfig;
import net.lmor.botanicalextramachinery.util.SettingPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.moddingx.libx.crafting.RecipeHelper;
import org.moddingx.libx.inventory.BaseItemStackHandler;

import javax.annotation.Nonnull;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BlockEntityManaInfuserPattern extends WorkingTile<net.minecraft.world.item.crafting.Recipe<net.minecraft.world.Container>>
        implements IInWorldGridNodeHost, IGridConnectedBlockEntity {
    public static final int MAX_MANA_PER_TICK = LibXServerConfig.ManaInfuserSettings.workingDuration;

    private final int FIRST_INPUT_SLOT;
    private final int LAST_INPUT_SLOT;
    private final int FIRST_OUTPUT_SLOT;
    private final int LAST_OUTPUT_SLOT;
    private int UPGRADE_SLOT_1 = -1;
    private int UPGRADE_SLOT_2 = -1;

    private final SettingPattern settingPattern;

    private final BaseItemStackHandler inventory;
    private int timeCheckOutputSlot = LibXServerConfig.tickOutputSlots;

    protected int speedMulti = 1;

    private boolean isInfinityMana = false;

    public BlockEntityManaInfuserPattern(BlockEntityType<?> type, BlockPos pos, BlockState state, int manaCapacity, int countCraft, int[] slots, SettingPattern settingPattern) {
        // Call super first. Use Botania's MANA_INFUSION_TYPE as a safe default for construction.
        super(type, (net.minecraft.world.item.crafting.RecipeType) vazkii.botania.common.crafting.BotaniaRecipeTypes.MANA_INFUSION_TYPE, pos, state, manaCapacity, slots[0], slots[2], countCraft);

        FIRST_INPUT_SLOT = slots[0];
        LAST_INPUT_SLOT = slots[1];
        FIRST_OUTPUT_SLOT = slots[2];
        LAST_OUTPUT_SLOT = slots[3];

        if (slots.length >= 5){
            UPGRADE_SLOT_1 = slots[4];
            UPGRADE_SLOT_2 = slots[5];
        }

        this.settingPattern = settingPattern;

        // Try to obtain the MythicBotany infuser RecipeType via reflection. If not available, fall back
        // to Botania's infusion type. We do this after calling super because super must be the first
        // statement in the constructor.
        net.minecraft.world.item.crafting.RecipeType<?> infuserType = vazkii.botania.common.crafting.BotaniaRecipeTypes.MANA_INFUSION_TYPE;
        try {
            Class<?> modRecipes = Class.forName("mythicbotany.register.ModRecipes");
            java.lang.reflect.Field field = modRecipes.getField("infuser");
            Object val = field.get(null);
            if (val instanceof net.minecraft.world.item.crafting.RecipeType) {
                infuserType = (net.minecraft.world.item.crafting.RecipeType<?>) val;
            }
        } catch (Throwable ignored) {
        }

        final net.minecraft.world.item.crafting.RecipeType<?> finalInfuserType = infuserType;

        if (UPGRADE_SLOT_1 != -1 && UPGRADE_SLOT_2 != -1){
            this.inventory = BaseItemStackHandler.builder(LAST_OUTPUT_SLOT + 1)
                    .validator((stack) -> this.level != null && RecipeHelper.isItemValidInput(this.level.getRecipeManager(), (net.minecraft.world.item.crafting.RecipeType) finalInfuserType, stack), Range.closedOpen(FIRST_INPUT_SLOT, LAST_INPUT_SLOT + 1))
                    .validator((stack) -> {return (stack.getItem() == ModItems.catalystSpeed.asItem() || stack.getItem() == ModItems.catalystManaInfinity.asItem());}, UPGRADE_SLOT_1, UPGRADE_SLOT_2)
                    .output(Range.closedOpen(FIRST_OUTPUT_SLOT, LAST_OUTPUT_SLOT + 1))
                    .slotLimit(1, UPGRADE_SLOT_1, UPGRADE_SLOT_2)
                    .contentsChanged(() -> { this.setChanged();this.setDispatchable();this.needsRecipeUpdate();})
                    .build();
        }
        else {
            this.inventory = BaseItemStackHandler.builder(LAST_OUTPUT_SLOT + 1)
                    .validator((stack) -> this.level != null && RecipeHelper.isItemValidInput(this.level.getRecipeManager(), (net.minecraft.world.item.crafting.RecipeType) finalInfuserType, stack), Range.closedOpen(FIRST_INPUT_SLOT, LAST_INPUT_SLOT + 1))
                    .output(Range.closedOpen(FIRST_OUTPUT_SLOT, LAST_OUTPUT_SLOT + 1))
                    .contentsChanged(() -> { this.setChanged();this.setDispatchable();this.needsRecipeUpdate();})
                    .build();
        }

        this.setChangedQueued = false;
    }

    //region Base
    public void tick() {
        if (this.level != null && !this.level.isClientSide) {
            if (!this.getMainNode().isReady()){
                this.getMainNode().create(this.level, this.getBlockPos());
            }


            if (getMainNode() != null && getMainNode().getNode() != null && getMainNode().isOnline()){
                if (timeCheckOutputSlot <= 0){
                    if (checkOutputSlots()){
                        this.exportResultsItemsME();
                    }

                    timeCheckOutputSlot = LibXServerConfig.tickOutputSlots;
                } else {
                    timeCheckOutputSlot--;
                }
            }

            isInfinityMana = (
                (UPGRADE_SLOT_1 != -1 && this.inventory.getStackInSlot(UPGRADE_SLOT_1).getItem() == ModItems.catalystManaInfinity) ||
                (UPGRADE_SLOT_2 != -1 && this.inventory.getStackInSlot(UPGRADE_SLOT_2).getItem() == ModItems.catalystManaInfinity)
            );

            if (this.speedMulti == 1 && (
                    (UPGRADE_SLOT_1 != -1 && this.inventory.getStackInSlot(UPGRADE_SLOT_1).getItem().asItem() == ModItems.catalystSpeed)||
                            (UPGRADE_SLOT_2 != -1 && this.inventory.getStackInSlot(UPGRADE_SLOT_2).getItem().asItem() == ModItems.catalystSpeed))){
                this.speedMulti = 4;
            } else{
                this.speedMulti = 1;
            }

            this.runRecipeTick();

        }

    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        this.getMainNode().loadFromNBT(nbt);

        this.setChanged();
        this.setDispatchable();
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        this.getMainNode().saveToNBT(nbt);
    }

    protected Predicate<Integer> getExtracts(Supplier<IItemHandlerModifiable> inventory) {
        return (slot) -> {
            return slot >= FIRST_OUTPUT_SLOT && slot <= LAST_OUTPUT_SLOT;
        };
    }

    @Nonnull
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    protected int getMaxProgress(net.minecraft.world.item.crafting.Recipe<net.minecraft.world.Container> recipe) {
        // Try to call InfuserRecipe.getManaUsage() via reflection if available
        try {
            java.lang.reflect.Method m = recipe.getClass().getMethod("getManaUsage");
            Object val = m.invoke(recipe);
            if (val instanceof Integer) return (Integer) val;
        } catch (Throwable ignored) {
        }
        // fallback: no mana usage info available
        return 0;
    }

    public int getMaxManaPerTick() {
        return MAX_MANA_PER_TICK * this.settingPattern.getConfigInt("craftTime") * this.speedMulti;
    }

    @Override
    public boolean getUpgradeInfinityMana() {
        return this.isInfinityMana;
    }

    public BlockEntity getBlockEntity(){
        return this;
    }

    private boolean checkOutputSlots(){
        for (int i = FIRST_OUTPUT_SLOT; i <= LAST_OUTPUT_SLOT; i++){
            if (!inventory.getStackInSlot(i).isEmpty()){
                return true;
            }
        }
        return false;
    }

    public void setRemoved() {
        super.setRemoved();

        if (this.getMainNode() != null) {
            this.getMainNode().destroy();
        }
    }
    //endregion

    //region AE INTEGRATION

    private boolean setChangedQueued;

    private final IManagedGridNode mainNode = this.createMainNode().setVisualRepresentation(this.getItemFromBlockEntity()).setInWorldNode(true).setTagName("proxy");


    protected IManagedGridNode createMainNode() {
        return GridHelper.createManagedNode(this, BlockEntityNodeListener.INSTANCE);
    }

    protected Item getItemFromBlockEntity() {
        BlockEntity blockEntity = this.getBlockEntity();
        if (blockEntity instanceof BlockEntityManaInfuserBase){
            return ModBlocks.baseManaInfuser.asItem();
        }
        else if (blockEntity instanceof BlockEntityManaInfuserUpgraded){
            return ModBlocks.upgradedManaInfuser.asItem();
        }
        else if (blockEntity instanceof BlockEntityManaInfuserAdvanced){
            return ModBlocks.advancedManaInfuser.asItem();
        }
        else {
            return ModBlocks.ultimateManaInfuser.asItem();
        }
    }

    private Object setChangedAtEndOfTick(Level level) {
        this.setChanged();
        this.setChangedQueued = false;
        return null;
    }

    @Nullable
    @Override
    public IGridNode getGridNode(Direction direction) {
        return this.getMainNode().getNode();
    }

    @Override
    public IManagedGridNode getMainNode() {
        return this.mainNode;
    }

    @Override
    public void saveChanges() {
        if (level != null) {
            if (level.isClientSide) {
                this.setChanged();
            } else {
                this.level.blockEntityChanged(this.worldPosition);
                if (!this.setChangedQueued) {
                    TickHandler.instance().addCallable(null, this::setChangedAtEndOfTick);
                    this.setChangedQueued = true;
                }
            }
        }
    }

    @Override
    public AECableType getCableConnectionType(Direction dir) {
        return AECableType.SMART;
    }

    private void exportResultsItemsME(){
        for (int slot = FIRST_OUTPUT_SLOT; slot <= LAST_OUTPUT_SLOT; slot++) {
            ItemStack stackInSlot = this.inventory.getStackInSlot(slot);

            if (!stackInSlot.isEmpty()) {
                int getCountExport = Math.toIntExact(this.getMainNode().getGrid().getStorageService().getInventory().insert(AEItemKey.of(stackInSlot), stackInSlot.getCount(), Actionable.MODULATE, IActionSource.empty()));

                if (getCountExport > 0) {
                    stackInSlot.shrink(getCountExport);
                    this.inventory.setStackInSlot(slot, stackInSlot);
                }
            }
        }
    }
    //endregion

}
