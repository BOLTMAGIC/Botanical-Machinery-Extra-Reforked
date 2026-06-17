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
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockEntityManaInfuserPattern extends WorkingTile<net.minecraft.world.item.crafting.Recipe<net.minecraft.world.Container>>
        implements IInWorldGridNodeHost, IGridConnectedBlockEntity {
    public static final int MAX_MANA_PER_TICK = LibXServerConfig.ManaInfuserSettings.workingDuration;

    private final int FIRST_OUTPUT_SLOT;
    private final int LAST_OUTPUT_SLOT;
    private int UPGRADE_SLOT_1 = -1;
    private int UPGRADE_SLOT_2 = -1;

    private final SettingPattern settingPattern;

    private final BaseItemStackHandler inventory;
    private int timeCheckOutputSlot = LibXServerConfig.tickOutputSlots;

    protected int speedMulti = 1;

    private boolean isInfinityMana = false;

    private static final Logger LOGGER = LogManager.getLogger();

    public BlockEntityManaInfuserPattern(BlockEntityType<?> type, BlockPos pos, BlockState state, int manaCapacity, int countCraft, int[] slots, SettingPattern settingPattern) {
        // Call super first. Use Botania's MANA_INFUSION_TYPE as a safe default for construction.
        //noinspection unchecked,rawtypes
        super(type, (net.minecraft.world.item.crafting.RecipeType) vazkii.botania.common.crafting.BotaniaRecipeTypes.MANA_INFUSION_TYPE, pos, state, manaCapacity, slots[0], slots[2], countCraft);

        int FIRST_INPUT_SLOT = slots[0];
        int LAST_INPUT_SLOT = slots[1];
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
        final net.minecraft.world.item.crafting.RecipeType<?> finalInfuserType = findInfuserRecipeType();

        // If we found a different recipe type (e.g. MythicBotany's), set the parent RecipeTile.recipeType
        // reflectively so that recipe lookups in the superclass use the correct RecipeType.
        try {
            java.lang.reflect.Field f = net.lmor.botanicalextramachinery.blocks.base.RecipeTile.class.getDeclaredField("recipeType");
            f.setAccessible(true);
            // suppress unchecked warning: runtime type fits RecipeType<?>
            f.set(this, finalInfuserType);
        } catch (Throwable ignored) {
            // If reflection fails, we keep the original Botania recipeType passed to super and hope validation covers it.
        }

        // Log which recipe type is being used to help debugging recipe matching issues
        try {
            if (LOGGER.isDebugEnabled()) LOGGER.debug("BlockEntityManaInfuserPattern using RecipeType: {} (class: {})", finalInfuserType, finalInfuserType.getClass().getName());
            if (finalInfuserType != vazkii.botania.common.crafting.BotaniaRecipeTypes.MANA_INFUSION_TYPE) {
                LOGGER.info("BlockEntityManaInfuserPattern: detected non-Botania infuser RecipeType: {}", finalInfuserType);
            }
        } catch (Throwable ignored) {}

        if (UPGRADE_SLOT_1 != -1 && UPGRADE_SLOT_2 != -1){
            this.inventory = BaseItemStackHandler.builder(LAST_OUTPUT_SLOT + 1)
                    .validator((stack) -> this.level != null && RecipeHelper.isItemValidInput(this.level.getRecipeManager(), finalInfuserType, stack), Range.closedOpen(FIRST_INPUT_SLOT, LAST_INPUT_SLOT + 1))
                    .validator((stack) -> (stack.getItem() == ModItems.catalystSpeed.asItem() || stack.getItem() == ModItems.catalystManaInfinity.asItem()), UPGRADE_SLOT_1, UPGRADE_SLOT_2)
                    .output(Range.closedOpen(FIRST_OUTPUT_SLOT, LAST_OUTPUT_SLOT + 1))
                    .slotLimit(1, UPGRADE_SLOT_1, UPGRADE_SLOT_2)
                    .contentsChanged(() -> { this.setChanged();this.setDispatchable();this.needsRecipeUpdate();})
                    .build();
        }
        else {
            this.inventory = BaseItemStackHandler.builder(LAST_OUTPUT_SLOT + 1)
                    .validator((stack) -> this.level != null && RecipeHelper.isItemValidInput(this.level.getRecipeManager(), finalInfuserType, stack), Range.closedOpen(FIRST_INPUT_SLOT, LAST_INPUT_SLOT + 1))
                    .output(Range.closedOpen(FIRST_OUTPUT_SLOT, LAST_OUTPUT_SLOT + 1))
                    .contentsChanged(() -> { this.setChanged();this.setDispatchable();this.needsRecipeUpdate();})
                    .build();
        }

        this.setChangedQueued = false;
    }

    /**
     * Try several known MythicBotany class/field/method names to retrieve the infuser RecipeType via reflection.
     * If none found, return Botania's default MANA_INFUSION_TYPE. Keeps MythicBotany optional at runtime.
     */
    private static net.minecraft.world.item.crafting.RecipeType<?> findInfuserRecipeType() {
        net.minecraft.world.item.crafting.RecipeType<?> botaniaType = vazkii.botania.common.crafting.BotaniaRecipeTypes.MANA_INFUSION_TYPE;

        String[] candidateClasses = new String[]{
                "mythicbotany.register.ModRecipes",
                "mythicbotany.ModRecipes",
                "mythicbotany.init.ModRecipes",
                "mythicbotany.common.register.ModRecipes"
        };

        String[] candidateFields = new String[]{"infuser", "INFUSER", "INFUSER_TYPE", "INFUSER_RECIPE_TYPE"};
        String[] candidateMethods = new String[]{"infuser", "getInfuser", "getInfuserRecipeType", "infuserRecipeType"};

        for (String clsName : candidateClasses) {
            try {
                Class<?> cls = Class.forName(clsName);
                // try fields
                for (String fName : candidateFields) {
                    try {
                        java.lang.reflect.Field f = cls.getField(fName);
                        Object val = f.get(null);
                        if (val instanceof net.minecraft.world.item.crafting.RecipeType) return (net.minecraft.world.item.crafting.RecipeType<?>) val;
                    } catch (NoSuchFieldException nsf) {
                        // try declared field (private)
                        try {
                            java.lang.reflect.Field f = cls.getDeclaredField(fName);
                            f.setAccessible(true);
                            Object val = f.get(null);
                            if (val instanceof net.minecraft.world.item.crafting.RecipeType) return (net.minecraft.world.item.crafting.RecipeType<?>) val;
                        } catch (Throwable ignored) {}
                    } catch (Throwable ignored) {}
                }

                // try static methods
                for (String mName : candidateMethods) {
                    try {
                        java.lang.reflect.Method m = cls.getMethod(mName);
                        Object val = m.invoke(null);
                        if (val instanceof net.minecraft.world.item.crafting.RecipeType) return (net.minecraft.world.item.crafting.RecipeType<?>) val;
                    } catch (NoSuchMethodException nsme) {
                        try {
                            java.lang.reflect.Method m = cls.getDeclaredMethod(mName);
                            m.setAccessible(true);
                            Object val = m.invoke(null);
                            if (val instanceof net.minecraft.world.item.crafting.RecipeType) return (net.minecraft.world.item.crafting.RecipeType<?>) val;
                        } catch (Throwable ignored) {}
                    } catch (Throwable ignored) {}
                }
            } catch (Throwable ignored) {
                // class not present or other issue - try next candidate
            }
        }

        return botaniaType;
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
        return (slot) -> slot >= FIRST_OUTPUT_SLOT && slot <= LAST_OUTPUT_SLOT;
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

    private void setChangedAtEndOfTick(Level level) {
        this.setChanged();
        this.setChangedQueued = false;
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
                int getCountExport = Math.toIntExact(Objects.requireNonNull(this.getMainNode().getGrid()).getStorageService().getInventory().insert(AEItemKey.of(stackInSlot), stackInSlot.getCount(), Actionable.MODULATE, IActionSource.empty()));

                if (getCountExport > 0) {
                    stackInSlot.shrink(getCountExport);
                    this.inventory.setStackInSlot(slot, stackInSlot);
                }
            }
        }
    }
    //endregion

    @Override
    protected boolean matchRecipe(net.minecraft.world.item.crafting.Recipe<net.minecraft.world.Container> recipe, java.util.List<net.minecraft.world.item.ItemStack> stacks) {
        boolean noMatch = super.matchRecipe(recipe, stacks);
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Testing recipe {}: matched={}, inputs={}", recipe.getId(), !noMatch, stacks);
            }
        } catch (Throwable ignored) {}
        return noMatch;
    }
}
