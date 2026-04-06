package net.lmor.botanicalextramachinery.blocks.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;

public abstract class WorkingTile<T extends Recipe<Container>> extends RecipeTile<T> {
    private int progress = 0;
    private int maxProgress = 0;

    public WorkingTile(BlockEntityType<?> blockEntityType, RecipeType<T> recipeType, BlockPos pos, BlockState state, int manaCap, int firstInputSlot, int firstOutputSlot, int countCraft) {
        super(blockEntityType, recipeType, pos, state, manaCap, firstInputSlot, firstOutputSlot, countCraft);
    }

    protected void runRecipeTick() {
        this.runRecipeTick(() -> {
        }, (stack, slot) -> {
        }, (stack, slot) -> {
        });
    }

    protected void runRecipeTick(Runnable doUpdate, BiConsumer<ItemStack, Integer> updateStack, BiConsumer<ItemStack, Integer> consumeStack) {
        if (this.level != null && !this.level.isClientSide) {
            this.updateRecipeIfNeeded(doUpdate, updateStack);
            if (this.recipe != null) {
                int newMaxProgress = this.getMaxProgress(this.recipe);
                if (newMaxProgress != this.maxProgress) {
                    this.maxProgress = newMaxProgress;
                    this.setChanged();
                    this.setDispatchable();
                }

                this.progress += this.getAndApplyProgressThisTick();
                if (this.progress >= this.getMaxProgress(this.recipe)) {
                    this.progress = 0;
                    this.craftRecipe(consumeStack);
                }

                this.setChanged();
                this.setDispatchable();
            } else if (this.progress != 0 || this.maxProgress != 0) {
                this.progress = 0;
                this.maxProgress = 0;
                this.setChanged();
                this.setDispatchable();
            }
        }

    }

    public int getProgress() {
        return this.progress;
    }

    public int getMaxProgress() {
        return this.maxProgress;
    }

    protected int getAndApplyProgressThisTick() {
        int manaToTransfer;
        if (!this.getUpgradeInfinityMana()) {
            manaToTransfer = Math.min(Math.min(this.getCurrentMana(), this.getMaxManaPerTick()), this.getMaxProgress(this.recipe) - this.progress);
            this.receiveMana(-manaToTransfer * this.getCountCraft());
        } else  {
            manaToTransfer = Math.min(this.getMaxManaPerTick(), this.getMaxProgress(this.recipe) - this.progress);
        }
        return manaToTransfer;
    }

    protected abstract int getMaxProgress(T var1);

    public abstract int getMaxManaPerTick();

    protected boolean getUpgradeInfinityMana() {
        return false;
    }

    public int getComparatorOutput() {
        return this.getProgress() > 0 ? 15 : 0;
    }

    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);
        this.progress = nbt.getInt("progress");
        this.maxProgress = nbt.getInt("maxProgress");
    }

    public void saveAdditional(@Nonnull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("progress", this.progress);
        nbt.putInt("maxProgress", this.maxProgress);
    }

    @Nonnull
    public CompoundTag getUpdateTag() {
        if (this.level != null && this.level.isClientSide) {
            return super.getUpdateTag();
        } else {
            CompoundTag nbt = super.getUpdateTag();
            nbt.putInt("progress", this.progress);
            nbt.putInt("maxProgress", this.maxProgress);
            return nbt;
        }
    }

    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        if (this.level == null || this.level.isClientSide) {
            this.progress = tag.getInt("progress");
            this.maxProgress = tag.getInt("maxProgress");
        }
    }
}
