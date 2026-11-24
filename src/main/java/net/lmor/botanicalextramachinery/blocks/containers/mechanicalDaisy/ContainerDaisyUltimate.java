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
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.moddingx.libx.menu.BlockEntityMenu;

import javax.annotation.Nonnull;

public class ContainerDaisyUltimate extends BlockEntityMenu<BlockEntityDaisyUltimate> {
    private static final int SIZE_INVENTORY = BlockEntityDaisyUltimate.SLOT_INVENTORY;
    public final static int WIDTH_GUI = 208;
    public final static int HEIGHT_GUI = 140;

    public ContainerDaisyUltimate(MenuType<? extends BlockEntityMenu<?>> type, int windowId, Level level, BlockPos pos, Inventory playerContainer, Player player) {
        super(type, windowId, level, pos, playerContainer, player, SIZE_INVENTORY + 1, SIZE_INVENTORY + 1);

        var inventory = this.blockEntity.getInventory();

        // All 18 processing slots; the catalyst upgrade is a separate handler.
        this.addSlot(inventory.createSlot(0, 96, 34));
        this.addSlot(inventory.createSlot(1, 118, 40));
        this.addSlot(inventory.createSlot(2, 124, 62));
        this.addSlot(inventory.createSlot(3, 118, 84));
        this.addSlot(inventory.createSlot(4, 96, 90));
        this.addSlot(inventory.createSlot(5, 74, 84));
        this.addSlot(inventory.createSlot(6, 68, 62));
        this.addSlot(inventory.createSlot(7, 74, 40));
        this.addSlot(inventory.createSlot(8, 74, 19));
        this.addSlot(inventory.createSlot(9, 50, 40));
        this.addSlot(inventory.createSlot(10, 40, 62));
        this.addSlot(inventory.createSlot(11, 50, 84));
        this.addSlot(inventory.createSlot(12, 74, 105));
        this.addSlot(inventory.createSlot(13, 118, 19));
        this.addSlot(inventory.createSlot(14, 142, 40));
        this.addSlot(inventory.createSlot(15, 152, 62));
        this.addSlot(inventory.createSlot(16, 142, 84));
        this.addSlot(inventory.createSlot(17, 118, 105));

        // Upgrade slot (separate handler)
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventoryUpgrade(), 0, 166, 105));

        int[] x_y = ScreenAddInventory.getCoordInventorySlot(ScreenInventory.ULTIMATE, WIDTH_GUI, HEIGHT_GUI);
        this.layoutPlayerInventorySlots(x_y[0], x_y[1]);
    }

    @Nonnull
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack original = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) {
            return original;
        }

        ItemStack stack = slot.getItem();
        original = stack.copy();
        int upgradeSlot = SIZE_INVENTORY;
        int playerStart = upgradeSlot + 1;
        int playerEnd = playerStart + 36;

        if (index < playerStart) {
            // Both processing slots and the separate catalyst slot go to the player.
            if (!this.moveItemStackTo(stack, playerStart, playerEnd, true)) {
                return ItemStack.EMPTY;
            }
            slot.onQuickCraft(stack, original);
        } else {
            // Catalysts go into the dedicated upgrade slot, not processing slots.
            boolean moved = this.slots.get(upgradeSlot).mayPlace(stack)
                    && this.moveItemStackTo(stack, upgradeSlot, upgradeSlot + 1, false);
            if (!moved && !this.moveItemStackTo(stack, 0, SIZE_INVENTORY, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        if (stack.getCount() == original.getCount()) {
            return ItemStack.EMPTY;
        }
        slot.onTake(player, stack);
        return original;
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
