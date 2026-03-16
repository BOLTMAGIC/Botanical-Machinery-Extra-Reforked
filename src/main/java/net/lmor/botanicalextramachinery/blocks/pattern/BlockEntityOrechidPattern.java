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
import net.lmor.botanicalextramachinery.blocks.base.ExtraBotanicalTile;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalOrechid.BlockEntityOrechidAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalOrechid.BlockEntityOrechidBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalOrechid.BlockEntityOrechidUpgraded;
import net.lmor.botanicalextramachinery.config.LibXServerConfig;
import net.lmor.botanicalextramachinery.util.SettingPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.moddingx.libx.inventory.BaseItemStackHandler;
import org.moddingx.libx.inventory.IAdvancedItemHandlerModifiable;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.crafting.OrechidRecipe;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BlockEntityOrechidPattern extends ExtraBotanicalTile
        implements IInWorldGridNodeHost, IGridConnectedBlockEntity {

    private final List<Integer> FILTER_SLOTS = new ArrayList<>();

    private final int FIRST_INPUT_SLOT;
    private final int LAST_INPUT_SLOT;
    private final int FIRST_OUTPUT_SLOT;
    private final int LAST_OUTPUT_SLOT;
    private int UPGRADE_SLOT_1 = -1;
    private int UPGRADE_SLOT_2 = -1;
    private final BaseItemStackHandler inventory;
    private final SettingPattern settingPattern;

    // Static caches for inputs/outputs to avoid scanning recipes every time
    private static volatile Set<Item> STATIC_ORECHID_INPUTS = null;
    private static volatile Set<Item> STATIC_ORECHID_OUTPUTS = null;

    private int cooldown;

    private int timeCheckOutputSlot = LibXServerConfig.tickOutputSlots;

    private boolean isInfinityMana = false;

    public BlockEntityOrechidPattern(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state,
                                     int manaCap, int[] slots, int[] upgrade_slot, SettingPattern settingPattern) {

        super(blockEntityType, pos, state, manaCap);

        FIRST_INPUT_SLOT = slots[0];
        LAST_INPUT_SLOT = slots[1];
        FIRST_OUTPUT_SLOT = slots[2];
        LAST_OUTPUT_SLOT = slots[3];

        for (int i = 4; i < slots.length; i++){
            FILTER_SLOTS.add(slots[i]);
        }

        if (upgrade_slot != null){
            UPGRADE_SLOT_1 = upgrade_slot[0];
            UPGRADE_SLOT_2 = upgrade_slot[1];
        }

        this.settingPattern = settingPattern;

        if (upgrade_slot != null){
            this.inventory = BaseItemStackHandler.builder(LAST_OUTPUT_SLOT + 1)
                    .validator((stack) -> { return stack.getItem() == ModItems.catalystManaInfinity.asItem() || stack.getItem() == ModItems.catalystStoneInfinity.asItem();}, UPGRADE_SLOT_1, UPGRADE_SLOT_2)
                    .validator((stack) -> { return this.level != null && getInputs().contains(stack.getItem());}, Range.closedOpen(FIRST_INPUT_SLOT, LAST_INPUT_SLOT + 1))
                    .validator((stack) -> { return getOutputs().contains(stack.getItem());}, FILTER_SLOTS.stream().mapToInt(Integer::intValue).toArray())
                    .output(Range.closedOpen(FIRST_OUTPUT_SLOT, LAST_OUTPUT_SLOT + 1)).contentsChanged(() -> {this.setChanged();this.setDispatchable();})
                    .slotLimit(1, UPGRADE_SLOT_1, UPGRADE_SLOT_2)
                    .slotLimit(1, FILTER_SLOTS.stream().mapToInt(Integer::intValue).toArray())
                    .build();
        }
        else{
            this.inventory = BaseItemStackHandler.builder(LAST_OUTPUT_SLOT + 1)
                    .validator((stack) -> { return this.level != null && getInputs().contains(stack.getItem());}, Range.closedOpen(FIRST_INPUT_SLOT, LAST_INPUT_SLOT + 1))
                    .validator((stack) -> { return getOutputs().contains(stack.getItem());}, FILTER_SLOTS.stream().mapToInt(Integer::intValue).toArray())
                    .output(Range.closedOpen(FIRST_OUTPUT_SLOT, LAST_OUTPUT_SLOT + 1)).contentsChanged(() -> {this.setChanged();this.setDispatchable();})
                    .slotLimit(1, FILTER_SLOTS.stream().mapToInt(Integer::intValue).toArray())
                    .build();
        }

        this.setChangedQueued = false;
    }

    //region Base
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

    private boolean checkFilterSlots(){
        for (int index: FILTER_SLOTS){
            if (!inventory.getStackInSlot(index).isEmpty()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick() {
        if (this.level != null && !this.level.isClientSide) {
            if (!this.getMainNode().isReady()) {
                this.getMainNode().create(this.level, this.getBlockPos());
            }

            if (getMainNode() != null && getMainNode().getNode() != null && getMainNode().isOnline()) {
                if (timeCheckOutputSlot <= 0) {
                    if (checkOutputSlots()) {
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


            if (this.cooldown > 0) {
                --this.cooldown;
                this.setChanged();
                this.setDispatchable();
            }

            if (this.cooldown <= 0) {
                int output_slot = LAST_INPUT_SLOT;
                int count_success = 0;
                for (int input_slot = FIRST_INPUT_SLOT; input_slot <= LAST_INPUT_SLOT; input_slot++){
                    output_slot++;
                    if (inventory.getStackInSlot(input_slot).isEmpty() || !inventory.getStackInSlot(output_slot).isEmpty()) continue;

                    OrechidRecipe recipe = !checkFilterSlots() ? getRecipeOrechid() : getRecipeOrechidFilter();
                    if (recipe == null) continue;


                    int count_recipe = Math.min(inventory.getStackInSlot(input_slot).getCount(), settingPattern.getConfigInt("countCraft"));
                    count_recipe = Math.min(count_recipe, inventory.getStackInSlot(output_slot).getMaxStackSize() - inventory.getStackInSlot(output_slot).getCount());

                    if (!isInfinityMana) {
                        if (this.getCurrentMana() < LibXServerConfig.OrechidSettings.recipeCost * count_recipe){
                            while (this.getCurrentMana() < LibXServerConfig.OrechidSettings.recipeCost * count_recipe){
                                count_recipe--;
                            }
                        }

                        if (count_recipe == 0) break;

                        this.receiveMana(-(count_recipe * LibXServerConfig.OrechidSettings.recipeCost));
                    }

                    ItemStack resItemStack = new ItemStack(recipe.getOutput().pick(this.level.random).getBlock().asItem());
                    resItemStack.setCount(count_recipe);

                    IAdvancedItemHandlerModifiable inventory = this.getInventory().getUnrestricted();

                    inventory.extractItem(input_slot, count_recipe, false);
                    inventory.insertItem(output_slot, resItemStack, false);
                    count_success++;
                }

                if (count_success != 0){
                    if (UPGRADE_SLOT_1 != -1 && UPGRADE_SLOT_2 != -1){
                        if (this.inventory.getStackInSlot(UPGRADE_SLOT_1).getItem() == ModItems.catalystStoneInfinity
                                || this.inventory.getStackInSlot(UPGRADE_SLOT_2).getItem() == ModItems.catalystStoneInfinity){
                            ItemStack stone = new ItemStack(Items.STONE);
                            stone.setCount(64);
                            for (int i = FIRST_INPUT_SLOT; i <= LAST_INPUT_SLOT; i++){
                                inventory.setStackInSlot(i, stone.copy());
                            }
                        }
                    }
                }

                this.cooldown = settingPattern.getConfigInt("cooldown");
            }
        }
    }

    @Override
    protected Predicate<Integer> getExtracts(Supplier<IItemHandlerModifiable> supplier) {
        return (slot) -> {
            return slot >= FIRST_OUTPUT_SLOT && slot <= LAST_OUTPUT_SLOT;
        };
    }

    @NotNull
    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public int getComparatorOutput() {
        return 0;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        if (this.getMainNode() != null) {
            this.getMainNode().destroy();
        }
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        this.cooldown = nbt.getInt("cooldown");
        this.getMainNode().loadFromNBT(nbt);

        this.setChanged();
        this.setDispatchable();
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("cooldown", this.cooldown);
        this.getMainNode().saveToNBT(nbt);
    }

    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        super.handleUpdateTag(nbt);
        if (this.level == null || this.level.isClientSide) {
            this.cooldown = nbt.getInt("cooldown");

        }
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        if (this.level != null && this.level.isClientSide) {
            return super.getUpdateTag();
        } else {
            CompoundTag nbt = super.getUpdateTag();
            nbt.putInt("cooldown", this.cooldown);

            return nbt;
        }
    }

    public List<Item> getOutputs(){
        if (STATIC_ORECHID_OUTPUTS == null) {
            synchronized (BlockEntityOrechidPattern.class) {
                if (STATIC_ORECHID_OUTPUTS == null) {
                    Set<Item> outputs = new LinkedHashSet<>();
                    if (this.level != null) {
                        this.level.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.ORECHID_TYPE).forEach(recipe -> {
                            if (recipe.getOutput() != null) {
                                recipe.getOutput().getDisplayedStacks().stream().map(ItemStack::getItem).forEach(outputs::add);
                            }
                        });
                    }
                    STATIC_ORECHID_OUTPUTS = outputs;
                }
            }
        }
        return STATIC_ORECHID_OUTPUTS == null ? List.of() : List.copyOf(STATIC_ORECHID_OUTPUTS);
    }

    public List<Item> getInputs(){
        if (STATIC_ORECHID_INPUTS == null) {
            synchronized (BlockEntityOrechidPattern.class) {
                if (STATIC_ORECHID_INPUTS == null) {
                    Set<Item> inputs = new LinkedHashSet<>();
                    if (this.level != null) {
                        this.level.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.ORECHID_TYPE).forEach(recipe -> {
                            if (recipe.getInput() != null) {
                                recipe.getInput().getDisplayedStacks().stream().map(ItemStack::getItem).forEach(inputs::add);
                            }
                        });
                    }
                    STATIC_ORECHID_INPUTS = inputs;
                }
            }
        }
        return STATIC_ORECHID_INPUTS == null ? List.of() : List.copyOf(STATIC_ORECHID_INPUTS);
    }

    public static void invalidateOrechidCaches() {
        STATIC_ORECHID_INPUTS = null;
        STATIC_ORECHID_OUTPUTS = null;
    }

    public @Nullable OrechidRecipe getRecipeOrechid(){
        List<WeightedEntry.Wrapper<OrechidRecipe>> values = new ArrayList();

        this.level.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.ORECHID_TYPE).forEach((recipe) -> {
            values.add(WeightedEntry.wrap(recipe, recipe.getWeight()));
        });

        return WeightedRandom.getRandomItem(this.level.random, values).map(WeightedEntry.Wrapper::getData).orElse(null);
    }

    public @Nullable OrechidRecipe getRecipeOrechidFilter(){
        List<WeightedEntry.Wrapper<OrechidRecipe>> values = new ArrayList();

        this.level.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.ORECHID_TYPE).forEach((recipe) -> {
            recipe.getOutput().getDisplayedStacks().stream().forEach(itemStack -> {
                for (int index: FILTER_SLOTS){
                    if (inventory.getStackInSlot(index).getItem() == itemStack.getItem()){
                        values.add(WeightedEntry.wrap(recipe, recipe.getWeight()));
                    }
                }
            });
        });

        return WeightedRandom.getRandomItem(this.level.random, values).map(WeightedEntry.Wrapper::getData).orElse(null);
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
        if (blockEntity instanceof BlockEntityOrechidBase){
            return ModBlocks.baseOrechid.asItem();
        }
        else if (blockEntity instanceof BlockEntityOrechidUpgraded){
            return ModBlocks.upgradedOrechid.asItem();
        }
        else if (blockEntity instanceof BlockEntityOrechidAdvanced){
            return ModBlocks.advancedOrechid.asItem();
        }
        else {
            return ModBlocks.ultimateOrechid.asItem();
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
