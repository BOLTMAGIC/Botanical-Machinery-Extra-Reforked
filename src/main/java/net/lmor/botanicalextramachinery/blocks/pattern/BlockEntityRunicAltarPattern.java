package net.lmor.botanicalextramachinery.blocks.pattern;

import appeng.api.config.Actionable;
import appeng.api.networking.*;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.util.AECableType;
import appeng.hooks.ticking.TickHandler;
import appeng.me.helpers.BlockEntityNodeListener;
import appeng.me.helpers.IGridConnectedBlockEntity;
import com.google.common.collect.Range;
import com.google.common.collect.Streams;
import net.lmor.botanicalextramachinery.ModBlocks;
import net.lmor.botanicalextramachinery.ModItems;
import net.lmor.botanicalextramachinery.blocks.base.WorkingTile;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalRunicAltar.BlockEntityRunicAltarAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalRunicAltar.BlockEntityRunicAltarBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalRunicAltar.BlockEntityRunicAltarUpgraded;
import net.lmor.botanicalextramachinery.config.LibXClientConfig;
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
import vazkii.botania.api.recipe.RunicAltarRecipe;
import vazkii.botania.client.fx.SparkleParticleData;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.lib.BotaniaTags;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BlockEntityRunicAltarPattern extends WorkingTile<RunicAltarRecipe>
        implements IInWorldGridNodeHost, IGridConnectedBlockEntity {
    public static final int MAX_MANA_PER_TICK = LibXServerConfig.RunicAltarSettings.manaCost;

    private final int LIVINGROCK_SLOT_1;
    private final int LIVINGROCK_SLOT_2;
    private final int LIVINGROCK_SLOT_3;
    private final int UPGRADE_SLOT_1;
    private final int UPGRADE_SLOT_2;
    private final int FIRST_INPUT_SLOT;
    private final int LAST_INPUT_SLOT;
    private final int FIRST_OUTPUT_SLOT;
    private final int LAST_OUTPUT_SLOT;

    private final BaseItemStackHandler inventory;
    private final SettingPattern settingPattern;

    private final List<Integer> slotsUsed = new ArrayList();
    private int timeCheckOutputSlot = LibXServerConfig.tickOutputSlots;

    private List<Integer> slotsLivingRock = new ArrayList<>();

    private boolean isInfinityMana = false;
    private int livingRockBuffer = 0;

    public BlockEntityRunicAltarPattern(BlockEntityType<?> type, BlockPos pos, BlockState state,
                                        int manaCap, int[] slots, boolean[] isUpgrade, int countCraft,
                                        SettingPattern config) {

        super(type, BotaniaRecipeTypes.RUNE_TYPE, pos, state, manaCap, slots[1], slots[3], countCraft);

        LIVINGROCK_SLOT_1 = addLivingRockSlotsArray(slots[0]);

        FIRST_INPUT_SLOT = slots[1];
        LAST_INPUT_SLOT = slots[2];
        FIRST_OUTPUT_SLOT = slots[3];
        LAST_OUTPUT_SLOT = slots[4];

        UPGRADE_SLOT_1 = slots[5];
        UPGRADE_SLOT_2 = slots[6];


        LIVINGROCK_SLOT_2 = addLivingRockSlotsArray(slots[7]);
        LIVINGROCK_SLOT_3 = addLivingRockSlotsArray(slots[8]);

        if (isUpgrade[0] && isUpgrade[1]){
            inventory = BaseItemStackHandler.builder(LAST_OUTPUT_SLOT + 1)
                    .validator((stack) -> {return stack.getItem() == BotaniaBlocks.livingrock.asItem();}, LIVINGROCK_SLOT_1, LIVINGROCK_SLOT_2, LIVINGROCK_SLOT_3)
                    .validator((stack) -> {return (stack.getItem() == ModItems.catalystManaInfinity.asItem() || stack.getItem() == ModItems.catalystLivingRockInfinity.asItem());}, UPGRADE_SLOT_1)
                    .validator((stack) -> {return (stack.getItem() == ModItems.catalystManaInfinity.asItem() || stack.getItem() == ModItems.catalystLivingRockInfinity.asItem()) ;}, UPGRADE_SLOT_2)
                    .validator((stack) -> {return this.level != null && RecipeHelper.isItemValidInput(this.level.getRecipeManager(), BotaniaRecipeTypes.RUNE_TYPE, stack);}, Range.closedOpen(FIRST_INPUT_SLOT, LAST_INPUT_SLOT + 1))
                    .slotLimit(1, UPGRADE_SLOT_1, UPGRADE_SLOT_2).output(Range.closedOpen(FIRST_OUTPUT_SLOT, LAST_OUTPUT_SLOT + 1)).contentsChanged(() -> {this.setChanged();this.setDispatchable();this.needsRecipeUpdate();})
                    .build();
        } else if (isUpgrade[0]){
            inventory = BaseItemStackHandler.builder(LAST_OUTPUT_SLOT + 1)
                    .validator((stack) -> {return stack.getItem() == BotaniaBlocks.livingrock.asItem();}, LIVINGROCK_SLOT_1, LIVINGROCK_SLOT_2, LIVINGROCK_SLOT_3)
                    .validator((stack) -> {return stack.getItem() == ModItems.catalystManaInfinity.asItem() || stack.getItem() == ModItems.catalystLivingRockInfinity.asItem();}, UPGRADE_SLOT_1)
                    .validator((stack) -> {return this.level != null && RecipeHelper.isItemValidInput(this.level.getRecipeManager(), BotaniaRecipeTypes.RUNE_TYPE, stack);}, Range.closedOpen(FIRST_INPUT_SLOT, LAST_INPUT_SLOT + 1))
                    .slotLimit(1, UPGRADE_SLOT_1, UPGRADE_SLOT_2).output(Range.closedOpen(FIRST_OUTPUT_SLOT, LAST_OUTPUT_SLOT + 1)).contentsChanged(() -> {this.setChanged();this.setDispatchable();this.needsRecipeUpdate();})
                    .build();
        } else {
            inventory = BaseItemStackHandler.builder(LAST_OUTPUT_SLOT + 1)
                    .validator((stack) -> {return stack.getItem() == BotaniaBlocks.livingrock.asItem();}, LIVINGROCK_SLOT_1, LIVINGROCK_SLOT_2, LIVINGROCK_SLOT_3)
                    .validator((stack) -> {return this.level != null && RecipeHelper.isItemValidInput(this.level.getRecipeManager(), BotaniaRecipeTypes.RUNE_TYPE, stack);}, Range.closedOpen(FIRST_INPUT_SLOT, LAST_INPUT_SLOT + 1))
                    .slotLimit(1, UPGRADE_SLOT_1, UPGRADE_SLOT_2).output(Range.closedOpen(FIRST_OUTPUT_SLOT, LAST_OUTPUT_SLOT + 1)).contentsChanged(() -> {this.setChanged();this.setDispatchable();this.needsRecipeUpdate();})
                    .build();
        }

        this.settingPattern = config;

        //region AE INTEGRATION
        this.setChangedQueued = false;

        //endregion
    }

    public void bufferCheck(){
        for (int i = 0; i < 3; i++){
            if (livingRockBuffer == 0) break;

            if (this.inventory.getStackInSlot(i).isEmpty()) continue;

            int old = livingRockBuffer;
            livingRockBuffer = livingRockBuffer > this.inventory.getStackInSlot(i).getCount() ? livingRockBuffer - this.inventory.getStackInSlot(i).getCount(): 0;
            this.inventory.extractItem(0, livingRockBuffer == 0 ? old : this.inventory.getStackInSlot(i).getCount(), false);
        }
    }


    //region BASE CODE

    public int addLivingRockSlotsArray(int index){
        slotsLivingRock.add(index);
        return slotsLivingRock.get(index);
    }

    public List<ItemStack> getUpgrades(){
        List<ItemStack> res = new ArrayList<>();

        res.add(new ItemStack(ModItems.catalystManaInfinity));
        res.add(new ItemStack(ModItems.catalystLivingRockInfinity));

        return res;
    }


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

            if (!this.inventory.getStackInSlot(0).isEmpty() && livingRockBuffer != 0) bufferCheck();


            isInfinityMana = (
                (UPGRADE_SLOT_1 != -1 && this.inventory.getStackInSlot(UPGRADE_SLOT_1).getItem() == ModItems.catalystManaInfinity) ||
                (UPGRADE_SLOT_2 != -1 && this.inventory.getStackInSlot(UPGRADE_SLOT_2).getItem() == ModItems.catalystManaInfinity)
            );

            if (inventory.getStackInSlot(LIVINGROCK_SLOT_1).isEmpty() ||
                inventory.getStackInSlot(LIVINGROCK_SLOT_2).isEmpty() ||
                inventory.getStackInSlot(LIVINGROCK_SLOT_3).isEmpty()) {

                if (getSlotUpgrade1().is(ModItems.catalystLivingRockInfinity) || getSlotUpgrade2().is(ModItems.catalystLivingRockInfinity)) {
                    addLivingRockSlots();
                }
            }

            List slotsUsed = this.slotsUsed;
            this.runRecipeTick(
                    slotsUsed::clear,
                    (stack, slot) -> {this.slotsUsed.add(slot); },
                    (stack, slot) -> {});

        } else if (this.level != null && LibXClientConfig.RenderingVisualContent.all && settingPattern.getConfigBoolean("mechanicalRunicAltarRender") && this.getMaxProgress() > 0 && this.getProgress() >= this.getMaxProgress() - 5 * this.getMaxManaPerTick()) {
            for (int i = 0; i < 5; ++i) {
                SparkleParticleData data = SparkleParticleData.sparkle(this.level.random.nextFloat(), this.level.random.nextFloat(), this.level.random.nextFloat(), this.level.random.nextFloat(), 10);
                this.level.addParticle(data, (double) this.worldPosition.getX() + 0.3 + this.level.random.nextDouble() * 0.4, (double) this.worldPosition.getY() + 0.7, (double) this.worldPosition.getZ() + 0.3 + this.level.random.nextDouble() * 0.4, 0.0, 0.0, 0.0);
            }
        }
    }

    public ItemStack getSlotUpgrade1(){
        if (UPGRADE_SLOT_1 == -1){
            return ItemStack.EMPTY;
        }
        return inventory.getStackInSlot(UPGRADE_SLOT_1);
    }

    public ItemStack getSlotUpgrade2(){
        if (UPGRADE_SLOT_2 == -1){
            return ItemStack.EMPTY;
        }
        return inventory.getStackInSlot(UPGRADE_SLOT_2);
    }

    public void addLivingRockSlots(){
        ItemStack livingrock = new ItemStack(BotaniaBlocks.livingrock.asItem());
        livingrock.setCount(64);

        if (LIVINGROCK_SLOT_1 == LIVINGROCK_SLOT_2 && LIVINGROCK_SLOT_2 == LIVINGROCK_SLOT_3
                && inventory.getStackInSlot(LIVINGROCK_SLOT_1).isEmpty()){
            inventory.setStackInSlot(LIVINGROCK_SLOT_1, livingrock);
        } else{
            if (inventory.getStackInSlot(LIVINGROCK_SLOT_1).isEmpty()) inventory.setStackInSlot(LIVINGROCK_SLOT_1, livingrock);
            else if (inventory.getStackInSlot(LIVINGROCK_SLOT_2).isEmpty()) inventory.setStackInSlot(LIVINGROCK_SLOT_2, livingrock);
            else if (inventory.getStackInSlot(LIVINGROCK_SLOT_3).isEmpty()) inventory.setStackInSlot(LIVINGROCK_SLOT_3, livingrock);
        }
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

    protected boolean canMatchRecipes() {
        return !this.inventory.getStackInSlot(LIVINGROCK_SLOT_1).isEmpty() ||
                !this.inventory.getStackInSlot(LIVINGROCK_SLOT_2).isEmpty() ||
                !this.inventory.getStackInSlot(LIVINGROCK_SLOT_3).isEmpty();
    }

    protected void onCrafted(RunicAltarRecipe recipe, int countCraft) {
        for (int slot: slotsLivingRock){
            if (!inventory.getStackInSlot(slot).isEmpty()){
                livingRockBuffer = countCraft > this.inventory.getStackInSlot(slot).getCount() ? countCraft - this.inventory.getStackInSlot(slot).getCount(): 0;
                this.inventory.extractItem(slot, livingRockBuffer == 0 ? countCraft : this.inventory.getStackInSlot(slot).getCount(), false);
                if (livingRockBuffer == 0) return;
            }
        }
    }

    protected List<ItemStack> resultItems(RunicAltarRecipe recipe, @NotNull List<ItemStack> stacks) {
        List res = Streams.concat(new Stream[]
            {
                stacks.stream()
                    .filter((s) -> {return s.is(BotaniaTags.Items.RUNES);})
                    .map(s -> {
                        ItemStack copy = s.copy();
                        copy.setCount(1);
                        return copy;
                }),
                super.resultItems(recipe, stacks)
                        .stream()
            }).toList();
        return res;
    }

    protected int getMaxProgress(@NotNull RunicAltarRecipe recipe) {
        return recipe.getManaUsage();
    }

    private boolean checkOutputSlots(){
        for (int i = FIRST_OUTPUT_SLOT; i < LAST_OUTPUT_SLOT + 1; i++){
            if (!this.inventory.getStackInSlot(i).isEmpty()){
                return true;
            }
        }

        return false;
    }

    public int getMaxManaPerTick() {
        return MAX_MANA_PER_TICK * settingPattern.getConfigInt("craftTime");
    }

    @Override
    public boolean getUpgradeInfinityMana() {
        return this.isInfinityMana;
    }

    public boolean isSlotUsedCurrently(int slot) {
        return this.slotsUsed.contains(slot);
    }

    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);
        this.slotsUsed.clear();
        this.slotsUsed.addAll(Arrays.stream(nbt.getIntArray("slotsUsed")).boxed().toList());
        this.livingRockBuffer = nbt.getInt("livingRockBuffer");
        this.getMainNode().loadFromNBT(nbt);

        this.setChanged();
        this.setDispatchable();
    }

    public void saveAdditional(@Nonnull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putIntArray("slotsUsed", this.slotsUsed);
        nbt.putInt("livingRockBuffer", this.livingRockBuffer);
        this.getMainNode().saveToNBT(nbt);
    }

    public void handleUpdateTag(CompoundTag nbt) {
        super.handleUpdateTag(nbt);
        if (this.level == null || this.level.isClientSide) {
            this.slotsUsed.clear();
            this.slotsUsed.addAll(Arrays.stream(nbt.getIntArray("slotsUsed")).boxed().toList());
            this.livingRockBuffer = nbt.getInt("livingRockBuffer");
        }
    }

    @Nonnull
    public CompoundTag getUpdateTag() {
        if (this.level != null && this.level.isClientSide) {
            return super.getUpdateTag();
        } else {
            CompoundTag nbt = super.getUpdateTag();

            nbt.putIntArray("slotsUsed", this.slotsUsed);
            nbt.putInt("livingRockBuffer", this.livingRockBuffer);

            return nbt;
        }
    }

    public BlockEntity getBlockEntity() {
        return this;
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
        if (blockEntity instanceof BlockEntityRunicAltarBase){
            return ModBlocks.baseRunicAltar.asItem();
        }
        else if (blockEntity instanceof BlockEntityRunicAltarUpgraded){
            return ModBlocks.upgradedRunicAltar.asItem();
        }
        else if (blockEntity instanceof BlockEntityRunicAltarAdvanced){
            return ModBlocks.advancedRunicAltar.asItem();
        }
        else {
            return ModBlocks.ultimateRunicAltar.asItem();
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        if (this.getMainNode() != null) {
            this.getMainNode().destroy();
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

    @Override
    public AECableType getCableConnectionType(Direction dir) {
        return AECableType.SMART;
    }
    //endregion
}