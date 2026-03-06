package net.lmor.botanicalextramachinery.blocks.containers.mechanicalDaisy;

import net.lmor.botanicalextramachinery.blocks.screens.uitlScreen.ScreenAddInventory;
import net.lmor.botanicalextramachinery.blocks.screens.uitlScreen.ScreenInventory;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalDaisy.BlockEntityDaisyUltimate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.moddingx.libx.menu.BlockEntityMenu;

import javax.annotation.Nonnull;

public class ContainerDaisyUltimate extends BlockEntityMenu<BlockEntityDaisyUltimate> {
    // Force 8 inventory slots to match BlockEntityDaisyPattern which always uses 8 slots internally.
    private static final int SIZE_INVENTORY = 8;
    public final static int WIDTH_GUI = 208;
    public final static int HEIGHT_GUI = 140;

    public ContainerDaisyUltimate(MenuType<? extends BlockEntityMenu<?>> type, int windowId, Level level, BlockPos pos, Inventory playerContainer, Player player) {
        super(type, windowId, level, pos, playerContainer, player, SIZE_INVENTORY + 1, SIZE_INVENTORY + 1);

        IItemHandlerModifiable inventory = this.blockEntity.getInventory();

        // Only the first 8 machine slots are added (0..7). The upgrade slot is added separately below.
        this.addSlot(new SlotItemHandler(inventory, 0, 96, 34));
        this.addSlot(new SlotItemHandler(inventory, 1, 118, 40));
        this.addSlot(new SlotItemHandler(inventory, 2, 124, 62));
        this.addSlot(new SlotItemHandler(inventory, 3, 118, 84));
        this.addSlot(new SlotItemHandler(inventory, 4, 96, 90));
        this.addSlot(new SlotItemHandler(inventory, 5, 74, 84));
        this.addSlot(new SlotItemHandler(inventory, 6, 68, 62));
        this.addSlot(new SlotItemHandler(inventory, 7, 74, 40));

        // Upgrade slot (separate handler)
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventoryUpgrade(), 0, 166, 105));

        int[] x_y = ScreenAddInventory.getCoordInventorySlot(ScreenInventory.ULTIMATE, WIDTH_GUI, HEIGHT_GUI);
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
