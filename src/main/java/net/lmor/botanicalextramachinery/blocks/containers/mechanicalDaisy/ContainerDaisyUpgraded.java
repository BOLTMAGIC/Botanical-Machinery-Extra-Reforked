package net.lmor.botanicalextramachinery.blocks.containers.mechanicalDaisy;

import net.lmor.botanicalextramachinery.blocks.screens.uitlScreen.ScreenAddInventory;
import net.lmor.botanicalextramachinery.blocks.screens.uitlScreen.ScreenInventory;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalDaisy.BlockEntityDaisyUpgraded;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.moddingx.libx.menu.BlockEntityMenu;

import javax.annotation.Nonnull;

public class ContainerDaisyUpgraded extends BlockEntityMenu<BlockEntityDaisyUpgraded> {
    private static final int SIZE_INVENTORY = BlockEntityDaisyUpgraded.SLOT_INVENTORY;
    public final static int WIDTH_GUI = 196;
    public final static int HEIGHT_GUI = 124;

    public ContainerDaisyUpgraded(MenuType<? extends BlockEntityMenu<?>> type, int windowId, Level level, BlockPos pos, Inventory playerContainer, Player player) {
        super(type, windowId, level, pos, playerContainer, player, SIZE_INVENTORY, SIZE_INVENTORY);

        var inventory = this.blockEntity.getInventory();

        this.addSlot(inventory.createSlot(0, 90, 26));
        this.addSlot(inventory.createSlot(1, 112, 32));
        this.addSlot(inventory.createSlot(2, 118, 54));
        this.addSlot(inventory.createSlot(3, 112, 76));
        this.addSlot(inventory.createSlot(4, 90, 82));
        this.addSlot(inventory.createSlot(5, 68, 76));
        this.addSlot(inventory.createSlot(6, 62, 54));
        this.addSlot(inventory.createSlot(7, 68, 32));
        this.addSlot(inventory.createSlot(8, 44, 32));
        this.addSlot(inventory.createSlot(9, 43, 76));
        this.addSlot(inventory.createSlot(10, 136, 32));
        this.addSlot(inventory.createSlot(11, 136, 76));

        int[] x_y = ScreenAddInventory.getCoordInventorySlot(ScreenInventory.UPGRADE, WIDTH_GUI, HEIGHT_GUI);
        this.layoutPlayerInventorySlots(x_y[0], x_y[1]);
    }

    @Nonnull
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();

            if (index < SIZE_INVENTORY) {
                if (!this.moveItemStackTo(stack, SIZE_INVENTORY, 36 + SIZE_INVENTORY, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack, itemstack);
            } else {
                if (!this.moveItemStackTo(stack, 0, SIZE_INVENTORY, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        if (level != null && level.getBlockState(pos).isAir()) {
            player.closeContainer();
            return false;
        }
        return super.stillValid(player);
    }

}
