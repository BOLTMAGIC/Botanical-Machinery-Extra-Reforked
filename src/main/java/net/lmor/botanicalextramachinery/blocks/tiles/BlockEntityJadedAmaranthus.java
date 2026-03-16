package net.lmor.botanicalextramachinery.blocks.tiles;

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
import net.lmor.botanicalextramachinery.ModBlocks;
import net.lmor.botanicalextramachinery.ModItems;
import net.lmor.botanicalextramachinery.blocks.base.ExtraBotanicalTile;
import net.lmor.botanicalextramachinery.config.LibXServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.moddingx.libx.inventory.BaseItemStackHandler;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.item.BotaniaItems;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BlockEntityJadedAmaranthus extends ExtraBotanicalTile implements IInWorldGridNodeHost, IGridConnectedBlockEntity {
    private final List<Integer> UPGRADE_SLOT = new ArrayList<>();
    private final int FIRST_OUTPUT_SLOT;
    private final int LAST_OUTPUT_SLOT;

    private int cooldown = LibXServerConfig.JadedAmaranthusSettings.cooldown;
    private int countCraft = LibXServerConfig.JadedAmaranthusSettings.countCraft;
    private int costMana = LibXServerConfig.JadedAmaranthusSettings.costMana;
    private boolean petalUpgrade = false;
    private boolean petalBlockUpgrade = false;

    private int timeCheckOutputSlot = LibXServerConfig.tickOutputSlots;

    private final BaseItemStackHandler inventory;

    public BlockEntityJadedAmaranthus(BlockEntityType<?> blockEntityTypeIn, BlockPos pos, BlockState state) {
        super(blockEntityTypeIn, pos, state, LibXServerConfig.JadedAmaranthusSettings.manaStorage);

        for (int i = 0; i <= 1; i++){
            UPGRADE_SLOT.add(i);
        }

        FIRST_OUTPUT_SLOT = UPGRADE_SLOT.size();
        LAST_OUTPUT_SLOT = FIRST_OUTPUT_SLOT + 15;

        inventory = BaseItemStackHandler.builder(LAST_OUTPUT_SLOT + 1)
                .validator((stack) -> { return stack.getItem() == ModItems.catalystManaInfinity.asItem() || stack.getItem() == ModItems.catalystPetal.asItem() || stack.getItem() == ModItems.catalystPetalBlock.asItem();}, UPGRADE_SLOT.stream().mapToInt(Integer::intValue).toArray())
                .output(FIRST_OUTPUT_SLOT, LAST_OUTPUT_SLOT + 1)
                .slotLimit(1, UPGRADE_SLOT.stream().mapToInt(Integer::intValue).toArray())
                .contentsChanged((slot) -> {this.setChanged();this.setDispatchable();this.changedUpgrade(slot);})
                .build();

        this.setChangedQueued = false;

    }

    public void changedUpgrade(int slot){
        if (UPGRADE_SLOT.contains(slot)){
            petalUpgrade = false;
            petalBlockUpgrade = false;

            for (int slotUpgrade: UPGRADE_SLOT){
                if (!this.getInventory().getStackInSlot(slotUpgrade).isEmpty() && this.getInventory().getStackInSlot(slotUpgrade).getItem() == ModItems.catalystPetal.asItem()){
                    petalUpgrade = true;
                }

                if (!this.getInventory().getStackInSlot(slotUpgrade).isEmpty() && this.getInventory().getStackInSlot(slotUpgrade).getItem() == ModItems.catalystPetalBlock.asItem()){
                    petalBlockUpgrade = true;
                }
            }
        }
    }

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide) return;

        if (!this.getMainNode().isReady()) {
            this.getMainNode().create(this.level, this.getBlockPos());
        }

        if (this.getCurrentMana() != this.getMaxMana()){
            if (inventory.getStackInSlot(0).getItem() == ModItems.catalystManaInfinity ||
                    inventory.getStackInSlot(1).getItem() == ModItems.catalystManaInfinity){
                this.receiveMana(this.getMaxMana());
            }
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


        if (this.getCurrentMana() <= 0){
            this.cooldown = LibXServerConfig.JadedAmaranthusSettings.cooldown;
            return;
        }

        if (cooldown > 0){
            --cooldown;
            this.setChanged();
            this.setDispatchable();
        }
        else {

            for (int dyeIndex = 0; dyeIndex < 16; dyeIndex++){
                DyeColor color = DyeColor.byId(dyeIndex);

                Item flowerOrPetal;
                if (petalUpgrade && !petalBlockUpgrade){
                    flowerOrPetal = BotaniaItems.getPetal(color).asItem();
                } else if (petalBlockUpgrade){
                    flowerOrPetal = BotaniaBlocks.getPetalBlock(color).asItem();
                } else {
                    flowerOrPetal = BotaniaBlocks.getFlower(color).defaultBlockState().getBlock().asItem();
                }

                ItemStack itemSlot = this.getInventory().getStackInSlot(dyeIndex + FIRST_OUTPUT_SLOT);
                if (itemSlot.isEmpty() || ((itemSlot.is(flowerOrPetal)) && itemSlot.getCount() != itemSlot.getMaxStackSize())){

                    ItemStack addItem = new ItemStack(flowerOrPetal);
                    int mana = this.costMana * countCraft;

                    if (petalUpgrade && !petalBlockUpgrade){
                        addItem.setCount(Math.min(itemSlot.getMaxStackSize(), itemSlot.getCount() + countCraft * 2));
                    } else {
                        addItem.setCount(Math.min(itemSlot.getMaxStackSize(), itemSlot.getCount() + countCraft));
                    }

                    if (petalBlockUpgrade){
                        mana = this.costMana * countCraft * 9;
                    }


                    if (this.getInventory().getStackInSlot(dyeIndex + FIRST_OUTPUT_SLOT).getCount() ==
                            this.getInventory().getStackInSlot(dyeIndex + FIRST_OUTPUT_SLOT).getMaxStackSize()) continue;

                    if (this.getCurrentMana() - mana < 0) break;

                    this.getInventory().setStackInSlot(dyeIndex + FIRST_OUTPUT_SLOT, addItem);
                    this.receiveMana(-mana);
                }
            }

            cooldown = LibXServerConfig.JadedAmaranthusSettings.cooldown;
        }

    }

    private boolean checkOutputSlots(){
        for (int i = FIRST_OUTPUT_SLOT; i <= LAST_OUTPUT_SLOT; i++){
            if (!inventory.getStackInSlot(i).isEmpty()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("cooldown", this.cooldown);
        nbt.putBoolean("petalUpgrade", this.petalUpgrade);
        nbt.putBoolean("petalUpgradeBlock", this.petalBlockUpgrade);
    }


    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        this.cooldown = nbt.getInt("cooldown");
        this.petalUpgrade = nbt.getBoolean("petalUpgrade");
        this.petalBlockUpgrade = nbt.getBoolean("petalUpgradeBlock");

        this.setChanged();
        this.setDispatchable();
    }

    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        super.handleUpdateTag(nbt);
        if (this.level == null || this.level.isClientSide) {
            this.cooldown = nbt.getInt("cooldown");
            this.petalUpgrade = nbt.getBoolean("petalUpgrade");
            this.petalBlockUpgrade = nbt.getBoolean("petalUpgradeBlock");
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

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        if (this.level != null && this.level.isClientSide) {
            return super.getUpdateTag();
        } else {
            CompoundTag nbt = super.getUpdateTag();
            nbt.putInt("cooldown", this.cooldown);
            nbt.putBoolean("petalUpgrade", this.petalUpgrade);
            nbt.putBoolean("petalUpgradeBlock", this.petalBlockUpgrade);

            return nbt;
        }
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


    //region AE INTEGRATION

    private boolean setChangedQueued;

    private final IManagedGridNode mainNode = this.createMainNode().setVisualRepresentation(this.getItemFromBlockEntity()).setInWorldNode(true).setTagName("proxy");

    protected IManagedGridNode createMainNode() {
        return GridHelper.createManagedNode(this, BlockEntityNodeListener.INSTANCE);
    }

    protected Item getItemFromBlockEntity() {
        return ModBlocks.jadedAmaranthus.asItem();
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
                    TickHandler.instance().addCallable((LevelAccessor)null, this::setChangedAtEndOfTick);
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
