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
import net.lmor.botanicalextramachinery.ModBlocks;
import net.lmor.botanicalextramachinery.ModItems;
import net.lmor.botanicalextramachinery.blocks.base.RecipeTile;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolUpgraded;
import net.lmor.botanicalextramachinery.config.LibXClientConfig;
import net.lmor.botanicalextramachinery.config.LibXServerConfig;
import net.lmor.botanicalextramachinery.util.SettingPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.Nullable;
import org.moddingx.libx.crafting.RecipeHelper;
import net.lmor.botanicalextramachinery.util.RecipeValidityCache;
import org.moddingx.libx.inventory.BaseItemStackHandler;
import vazkii.botania.api.recipe.ManaInfusionRecipe;
import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BlockEntityManaPoolPattern extends RecipeTile<ManaInfusionRecipe>
        implements IInWorldGridNodeHost, IGridConnectedBlockEntity {

    private static List<Item> CACHED_CATALYSTS;

    private final int CATALYSTS_SLOT;
    private final int UPGRADE_SLOT;
    private final int FIRST_INPUT_SLOT;
    private final int LAST_INPUT_SLOT;
    private final int FIRST_OUTPUT_SLOT;
    private final int LAST_OUTPUT_SLOT;

    private final BaseItemStackHandler inventory;
    private final SettingPattern settingPattern;
    private final Boolean isUpgrade;

    private int cooldown = 0;
    private boolean checkWithCatalyst = false;
    private int timeCheckOutputSlot = LibXServerConfig.tickOutputSlots;

    private boolean isInfinityMana = false;


    public BlockEntityManaPoolPattern(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state,
                                      int manaCap, int[] slots, boolean isUpgrade, int countCraft, SettingPattern config) {
        super(blockEntityType, BotaniaRecipeTypes.MANA_INFUSION_TYPE, pos, state, manaCap, slots[1], slots[3], countCraft);

        CATALYSTS_SLOT = slots[0];
        UPGRADE_SLOT = isUpgrade ? slots[5] : -1;
        FIRST_INPUT_SLOT = slots[1];
        LAST_INPUT_SLOT = slots[2];
        FIRST_OUTPUT_SLOT = slots[3];
        LAST_OUTPUT_SLOT = slots[4];

        if (isUpgrade){
            inventory = BaseItemStackHandler.builder(LAST_OUTPUT_SLOT + 1)
                    .validator((stack) -> { return this.getCatalysts().contains(stack.getItem()); }, CATALYSTS_SLOT)
                    .validator((stack) -> { return stack.getItem() == ModItems.catalystManaInfinity.asItem();}, UPGRADE_SLOT)
                    .validator((stack) -> { return this.level != null && RecipeValidityCache.isItemValidInput(this.level.getRecipeManager(), BotaniaRecipeTypes.MANA_INFUSION_TYPE, stack);}, Range.closedOpen(FIRST_INPUT_SLOT, LAST_INPUT_SLOT + 1))
                    .slotLimit(1, CATALYSTS_SLOT, UPGRADE_SLOT).output(Range.closedOpen(FIRST_OUTPUT_SLOT, LAST_OUTPUT_SLOT + 1)).contentsChanged(() -> {this.setChanged();this.setDispatchable();this.needsRecipeUpdate();})
                    .build();

        } else {
            inventory = BaseItemStackHandler.builder(LAST_OUTPUT_SLOT + 1)
                    .validator((stack) -> { return this.getCatalysts().contains(stack.getItem()); }, CATALYSTS_SLOT)
                    .validator((stack) -> { return this.level != null && RecipeValidityCache.isItemValidInput(this.level.getRecipeManager(), BotaniaRecipeTypes.MANA_INFUSION_TYPE, stack);}, Range.closedOpen(FIRST_INPUT_SLOT, LAST_INPUT_SLOT + 1))
                    .slotLimit(1, CATALYSTS_SLOT).output(Range.closedOpen(FIRST_OUTPUT_SLOT, LAST_OUTPUT_SLOT + 1)).contentsChanged(() -> {this.setChanged();this.setDispatchable();this.needsRecipeUpdate();})
                    .build();
        }

        this.settingPattern = config;
        this.isUpgrade = isUpgrade;

        //region AE INTEGRATION

        this.setChangedQueued = false;

        //endregion
    }

    //region BASE CODE
    public void tick() {
        if (this.level != null && !this.level.isClientSide) {
            if (!this.getMainNode().isReady()){
                this.getMainNode().create(this.level, this.getBlockPos());
            }

            isInfinityMana = UPGRADE_SLOT != -1 && !inventory.getStackInSlot(UPGRADE_SLOT).isEmpty();

            this.updateRecipeIfNeeded();

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

            if (this.cooldown > 0) {
                --this.cooldown;
                this.setChanged();
                this.setDispatchable();
            }

            if (this.cooldown <= 0 && this.recipe != null) {
                this.craftRecipe();
            }

        } else if (this.level != null && LibXClientConfig.RenderingVisualContent.all && settingPattern.getConfigBoolean("mechanicalManaPoolRender")) {
            double particleChance = (double)this.getCurrentMana() / (double)this.getMaxMana() * 0.1;
            if (Math.random() < particleChance) {
                float red = 0.0F;
                float green = 0.7764706F;
                float blue = 1.0F;
                WispParticleData data = WispParticleData.wisp((float)Math.random() / 3.0F, red, green, blue, 2.0F);
                this.level.addParticle(data, (double)this.worldPosition.getX() + 0.3 + this.level.random.nextDouble() * 0.4, (double)this.worldPosition.getY() + 0.5 + this.level.random.nextDouble() * 0.25, (double)this.worldPosition.getZ() + 0.3 + this.level.random.nextDouble() * 0.4, 0.0, (double)(this.level.random.nextFloat() / 25.0F), 0.0);
            }
        }
    }

    protected Predicate<Integer> getExtracts(Supplier<IItemHandlerModifiable> supplier) {
        return (slot) -> {
            return slot >= FIRST_OUTPUT_SLOT && slot <= LAST_OUTPUT_SLOT;
        };
    }

    @Nonnull
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    protected java.util.function.BiPredicate<Integer, ItemStack> getInserts(Supplier<IItemHandlerModifiable> supplier) {
        // Delegate to the centralized insert guard in ExtraBotanicalTile so the "pool full" check is respected.
        return super.getInserts(supplier);
    }

    protected void updateRecipe(BiConsumer<ItemStack, Integer> usedStacks) {
        this.recipe = null;
        this.checkWithCatalyst = true;
        super.updateRecipe(usedStacks);
        this.checkWithCatalyst = false;
        if (this.recipe == null) {
            super.updateRecipe(usedStacks);
        }
    }

    protected boolean matchRecipe(ManaInfusionRecipe recipe, List<ItemStack> stacks) {
        if (recipe.getManaToConsume() > this.getCurrentMana() && !isInfinityMana) {
            return false;
        } else {
            Item catalystItem = this.checkWithCatalyst && !this.inventory.getStackInSlot(CATALYSTS_SLOT).isEmpty() ? this.inventory.getStackInSlot(CATALYSTS_SLOT).getItem() : null;
            Block catalyst = catalystItem == null ? null : Block.byItem(catalystItem);
            if (catalyst == null && recipe.getRecipeCatalyst() != null) {
                return false;
            } else {
                return (catalyst == null || recipe.getRecipeCatalyst() != null && recipe.getRecipeCatalyst().test(catalyst.defaultBlockState())) && super.matchRecipe(recipe, stacks);
            }
        }
    }

    protected void onCrafted(ManaInfusionRecipe recipe, int countItemCraft) {
        this.cooldown = Math.max(1, settingPattern.getConfigInt("craftTime"));

        if (isUpgrade && !inventory.getStackInSlot(UPGRADE_SLOT).isEmpty()) return;

        if (isInfinityMana) return;
        for (int i = 0; i < countItemCraft; i++){
            this.receiveMana(-recipe.getManaToConsume());
        }
    }

    public int getComparatorOutput() {
        if (this.inventory.getStackInSlot(CATALYSTS_SLOT).isEmpty()) {
            return 0;
        } else {
            Item item = this.inventory.getStackInSlot(CATALYSTS_SLOT).getItem();
            return this.getCatalysts().contains(item) ? Mth.clamp(1 + this.getCatalysts().indexOf(item), 0, 15) : 0;
        }
    }

    public void receiveMana(int i) {
        if (inventory.getStackInSlot(CATALYSTS_SLOT).getItem() == BotaniaBlocks.manaVoid.asItem()) {
            super.receiveMana(Math.min(i, this.getAvailableSpaceForMana()));
        } else {
            super.receiveMana(i);
        }

        if (this.recipe == null && checkInputSlots()) {
            this.needsRecipeUpdate();
        }
    }

    private boolean checkInputSlots(){
        for (int i = FIRST_INPUT_SLOT; i <= LAST_INPUT_SLOT; i++){
            if (!inventory.getStackInSlot(i).isEmpty()){
                return true;
            }
        }
        return false;
    }

    private boolean checkOutputSlots(){
        for (int i = FIRST_OUTPUT_SLOT; i <= LAST_OUTPUT_SLOT; i++){
            if (!inventory.getStackInSlot(i).isEmpty()){
                return true;
            }
        }
        return false;
    }

    public boolean isFull() {
        return this.inventory.getStackInSlot(CATALYSTS_SLOT).getItem() != BotaniaBlocks.manaVoid.asItem() && super.isFull();
    }

    public int getAvailableSpaceForMana() {
        return this.inventory.getStackInSlot(CATALYSTS_SLOT).getItem() == BotaniaBlocks.manaVoid.asItem() ? this.getMaxMana() : super.getAvailableSpaceForMana();
    }

    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);
        this.cooldown = nbt.getInt("cooldown");
        this.getMainNode().loadFromNBT(nbt);

        this.setChanged();
        this.setDispatchable();

    }

    public void saveAdditional(@Nonnull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("cooldown", this.cooldown);
        this.getMainNode().saveToNBT(nbt);
    }

    public void handleUpdateTag(CompoundTag nbt) {
        super.handleUpdateTag(nbt);
        if (this.level == null || this.level.isClientSide) {
            this.cooldown = nbt.getInt("cooldown");

        }
    }

    @Nonnull
    public CompoundTag getUpdateTag() {
        if (this.level != null && this.level.isClientSide) {
            return super.getUpdateTag();
        } else {
            CompoundTag nbt = super.getUpdateTag();
            nbt.putInt("cooldown", this.cooldown);

            return nbt;
        }
    }

    public static void invalidateCatalysts() {
        CACHED_CATALYSTS = null;
    }

    public List<Item> getCatalysts() {
        if (CACHED_CATALYSTS == null) {
            if (this.level == null) {
                return List.of();
            }

            List<Item> catalysts = new ArrayList();
            this.level.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.MANA_INFUSION_TYPE).forEach((recipe) -> {
                if (recipe.getRecipeCatalyst() != null) {
                    recipe.getRecipeCatalyst().getDisplayedStacks().stream().map(ItemStack::getItem).forEach((item) -> {
                        if (!catalysts.contains(item)) {
                            catalysts.add(item);
                        }

                    });
                }

            });
            CACHED_CATALYSTS = List.copyOf(catalysts);
        }

        return CACHED_CATALYSTS;
    }

    private BlockEntity getBlockEntity(){
        return this;
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
        if (blockEntity instanceof BlockEntityManaPoolBase){
            return ModBlocks.baseManaPool.asItem();
        }
        else if (blockEntity instanceof BlockEntityManaPoolUpgraded){
            return ModBlocks.upgradedManaPool.asItem();
        }
        else if (blockEntity instanceof BlockEntityManaPoolAdvanced){
            return ModBlocks.advancedManaPool.asItem();
        }
        else {
            return ModBlocks.ultimateManaPool.asItem();
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
