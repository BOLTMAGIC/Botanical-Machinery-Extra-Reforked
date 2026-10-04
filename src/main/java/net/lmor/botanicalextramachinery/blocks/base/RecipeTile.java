package net.lmor.botanicalextramachinery.blocks.base;

import com.google.common.collect.Streams;
import net.lmor.botanicalextramachinery.config.LibXServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.moddingx.libx.crafting.RecipeHelper;
import org.moddingx.libx.inventory.IAdvancedItemHandlerModifiable;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.lib.BotaniaTags;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class RecipeTile<T extends Recipe<Container>> extends ExtraBotanicalTile {
    private final RecipeType<T> recipeType;
    private final int firstInputSlot;
    private final int firstOutputSlot;
    protected T recipe;
    private boolean needsRecipeUpdate;
    private final int countCraft;
    private int countCraftPerRecipe;
    /**
     * Transient cache for simple ingredient item arrays for the currently selected recipe.
     * If an entry is null, the corresponding Ingredient is not simple and Ingredient.test() must be used.
     */
    private transient List<Item[]> cachedIngredientItems = null;


    public RecipeTile(BlockEntityType<?> blockEntityType, RecipeType<T> recipeType, BlockPos pos, BlockState state, int manaCap, int firstInputSlot, int firstOutputSlot, int countCraft) {
        super(blockEntityType, pos, state, manaCap);
        this.recipeType = recipeType;
        this.firstInputSlot = firstInputSlot;
        this.firstOutputSlot = firstOutputSlot;
        this.needsRecipeUpdate = true;
        this.countCraft = countCraft;
        this.countCraftPerRecipe = countCraft;
    }

    public int getCountCraft() {
        return countCraftPerRecipe;
    }

    protected void updateRecipeIfNeeded() {
        this.updateRecipeIfNeeded(() -> {
        }, (stack, slot) -> {
        });
    }

    protected void updateRecipeIfNeeded(Runnable doUpdate, BiConsumer<ItemStack, Integer> usedStacks) {
        if (this.level != null && !this.level.isClientSide) {
            if (this.needsRecipeUpdate) {
                this.needsRecipeUpdate = false;
                doUpdate.run();
                this.updateRecipe(usedStacks);
            }

        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void updateRecipe(BiConsumer<ItemStack, Integer> usedStacks) {
        if (this.level != null && !this.level.isClientSide) {
            if (!this.canMatchRecipes()) {
                this.recipe = null;
            } else {
                IAdvancedItemHandlerModifiable inventory = this.getInventory().getUnrestricted();
                IntStream range = IntStream.range(this.firstInputSlot, this.firstOutputSlot);
                Objects.requireNonNull(inventory);
                List<ItemStack> stacks = range.mapToObj(inventory::getStackInSlot).toList();

                // Inputs may hold more items than a recipe needs, so a small recipe can match items
                // meant for a bigger one. With largestRecipeFirst, pick the matching recipe with the
                // most ingredients; on a tie the first one in recipe order wins.
                boolean largestFirst = LibXServerConfig.largestRecipeFirst;
                Recipe recipe = null;
                for (T candidate : this.level.getRecipeManager().getAllRecipesFor(this.recipeType)) {
                    // Skip recipes that cannot beat the current pick before the costlier match check
                    if (recipe != null && candidate.getIngredients().size() <= recipe.getIngredients().size()) continue;

                    if (!this.matchRecipe(candidate, stacks)) {
                        recipe = candidate;
                        if (!largestFirst) break;
                    }
                }

                if (recipe == null) {
                    this.recipe = null;
                    return;
                }

                // Build a simple per-ingredient item array cache for fast matching when possible
                List<Ingredient> ingredients = recipe.getIngredients();
                int nIngredients = ingredients.size();
                this.cachedIngredientItems = new ArrayList<>(nIngredients);
                for (int ingIdx = 0; ingIdx < nIngredients; ingIdx++) {
                    Ingredient ing = ingredients.get(ingIdx);
                    ItemStack[] items = ing.getItems();
                    if (!ing.isSimple() || items.length == 0) {
                        this.cachedIngredientItems.add(null);
                        continue;
                    }
                    boolean simple = true;
                    Item[] arr = new Item[items.length];
                    for (int i = 0; i < items.length; i++) {
                        ItemStack is = items[i];
                        if (is == null || is.hasTag()) {
                            simple = false;
                            break;
                        }
                        arr[i] = is.getItem().asItem();
                    }
                    this.cachedIngredientItems.add(simple ? arr : null);
                }

                List<ItemStack> consumedStacks = new ArrayList<>();

                this.countCraftPerRecipe = maxCountCraft(recipe.getIngredients().iterator());
                if (this.countCraftPerRecipe == 0) {
                    this.recipe = null;
                    return;
                }

                if (recipe.getResultItem(this.level.registryAccess()).getCount() != 0){
                    int remainingItemsToPlace;
                    if (recipe.getResultItem(this.level.registryAccess()).getCount() != 0){
                        remainingItemsToPlace = countCraftPerRecipe * recipe.getResultItem(this.level.registryAccess()).getCount();
                    } else {
                        remainingItemsToPlace = countCraftPerRecipe;
                    }

                    for (int slot = this.firstOutputSlot; slot < inventory.getSlots(); ++slot) {
                        ItemStack slotStack = inventory.getStackInSlot(slot);

                        if (slotStack.isEmpty() || slotStack == recipe.getResultItem(this.level.registryAccess())) {
                            int maxStackSize = recipe.getResultItem(this.level.registryAccess()).getMaxStackSize();
                            int currentStackSize = slotStack.getCount();
                            int availableSpace = maxStackSize - currentStackSize;

                            int itemsToPlaceInSlot = Math.min(remainingItemsToPlace, availableSpace);

                            remainingItemsToPlace -= itemsToPlaceInSlot;

                            if (remainingItemsToPlace <= 0) {
                                break;
                            }
                        }
                    }

                    if (remainingItemsToPlace < countCraftPerRecipe * recipe.getResultItem(this.level.registryAccess()).getCount()) {
                        this.countCraftPerRecipe -= remainingItemsToPlace / recipe.getResultItem(this.level.registryAccess()).getCount();

                    } else if (remainingItemsToPlace >= countCraftPerRecipe * recipe.getResultItem(this.level.registryAccess()).getCount()) {
                        this.recipe = null;
                        return;
                    }
                }

                if (recipe.getType() == BotaniaRecipeTypes.RUNE_TYPE) {
                    // collect all ingredient items and check for runes
                    List<ItemStack> inputItemRes = new ArrayList<>();
                    for (int ingIdx = 0; ingIdx < nIngredients; ingIdx++) {
                        Ingredient ingredient = ingredients.get(ingIdx);
                        inputItemRes.addAll(Arrays.stream(ingredient.getItems()).toList());
                    }

                    List<ItemStack> res = Streams.concat(new Stream[]{
                            inputItemRes.stream()
                                    .filter(s -> s.is(BotaniaTags.Items.RUNES))
                                    .map(ItemStack::copy)
                    }).toList();

                    if (!res.isEmpty()){
                        int emptySlot = 0;
                        for (int slot = this.firstOutputSlot; slot < inventory.getSlots(); ++slot) {
                            ItemStack slotItem = inventory.getStackInSlot(slot);
                            if (slotItem.isEmpty()){
                                emptySlot++;
                            }
                        }
                        if (emptySlot == 0 || emptySlot < res.size() + 1){
                            this.recipe = null;
                            return;
                        }
                    }
                }

                // Match ingredients using index-based loops and the cachedIngredientItems fast-path
                for (int ingIdx = 0; ingIdx < nIngredients; ingIdx++) {
                    Ingredient ingredient = ingredients.get(ingIdx);
                    Item[] simple = this.cachedIngredientItems.get(ingIdx);

                    for (int stackIdx = 0; stackIdx < stacks.size(); ++stackIdx) {
                        ItemStack candidate = stacks.get(stackIdx);
                        boolean matched = false;
                        if (simple != null) {
                            Item it = candidate.getItem().asItem();
                            for (Item si : simple) {
                                if (si == it) { matched = true; break; }
                            }
                        } else {
                            if (ingredient.test(candidate)) matched = true;
                        }

                        if (matched) {
                            ItemStack theStack = stacks.get(stackIdx).copy();
                            theStack.setCount(this.countCraftPerRecipe);
                            consumedStacks.add(theStack.copy());
                            usedStacks.accept(theStack, this.firstInputSlot + stackIdx);
                            break;
                        }
                    }
                }

                List<ItemStack> resultItems = this.resultItems((T) recipe, consumedStacks);

                if (!resultItems.isEmpty() && !inventory.hasSpaceFor(resultItems, this.firstOutputSlot, inventory.getSlots())) {
                    this.recipe = null;
                } else {
                    this.recipe = (T) recipe;
                }

            }
        }
    }

    public int maxCountCraft(@SuppressWarnings("rawtypes") Iterator iteratorRecipe){
        List<Ingredient> ingredients = new ArrayList<>();
        while (iteratorRecipe.hasNext()) {
            ingredients.add((Ingredient) iteratorRecipe.next());
        }

        List<ItemStack> availableStacks = new ArrayList<>();
        for (int slotIdx = this.firstInputSlot; slotIdx < this.firstOutputSlot; slotIdx++) {
            ItemStack slotStack = this.getInventory().getStackInSlot(slotIdx);
            if (!slotStack.isEmpty()) {
                availableStacks.add(slotStack);
            }
        }

        if (ingredients.isEmpty()) return 0;

        // Reserve counts in the same ingredient/slot order as extraction, keeping the real NBT.
        for (int crafts = this.countCraft; crafts > 0; crafts--) {
            int[] remaining = availableStacks.stream().mapToInt(ItemStack::getCount).toArray();
            boolean complete = true;
            for (Ingredient ingredient : ingredients) {
                int needed = crafts;
                for (int slot = 0; slot < availableStacks.size() && needed > 0; slot++) {
                    if (remaining[slot] > 0 && ingredient.test(availableStacks.get(slot))) {
                        int taken = Math.min(needed, remaining[slot]);
                        remaining[slot] -= taken;
                        needed -= taken;
                    }
                }
                if (needed > 0) {
                    complete = false;
                    break;
                }
            }
            if (complete) return crafts;
        }
        return 0;
    }

    protected void craftRecipe() {
        this.craftRecipe((stack, slot) -> {
        });
    }

    protected void craftRecipe(BiConsumer<ItemStack, Integer> usedStacks) {
        if (this.level != null && !this.level.isClientSide) {
            if (this.recipe != null) {
                IAdvancedItemHandlerModifiable inventory = this.getInventory().getUnrestricted();
                List<ItemStack> consumedStacks = new ArrayList<>();

                List<Ingredient> ingredients = this.recipe.getIngredients();
                int nIngredients = ingredients.size();

                int actualExtractedMin = Integer.MAX_VALUE;

                // Continue across slots if one slot doesn't have enough items for an ingredient.
                for (int ingIdx = 0; ingIdx < nIngredients; ingIdx++) {
                    Ingredient ingredient = ingredients.get(ingIdx);
                    Item[] simple = this.cachedIngredientItems == null ? null : this.cachedIngredientItems.get(ingIdx);

                    int stillNeeded = this.countCraftPerRecipe;

                    for (int slot = this.firstInputSlot; slot < this.firstOutputSlot && stillNeeded > 0; ++slot) {
                        ItemStack cand = inventory.getStackInSlot(slot);
                        boolean matched = false;
                        if (simple != null) {
                            Item it = cand.getItem().asItem();
                            for (Item si : simple) { if (si == it) { matched = true; break; } }
                        } else {
                            if (ingredient.test(cand)) matched = true;
                        }

                        if (matched) {
                            ItemStack extracted = inventory.extractItem(slot, stillNeeded, false);
                            if (!extracted.isEmpty()) {
                                int extractedCount = extracted.getCount();
                                stillNeeded -= extractedCount;
                                consumedStacks.add(extracted);
                                usedStacks.accept(extracted, slot);
                            }
                            // Don't break - continue to next slot if we still need more
                        }
                    }
                    actualExtractedMin = Math.min(actualExtractedMin, this.countCraftPerRecipe - stillNeeded);
                }

                if (actualExtractedMin == Integer.MAX_VALUE) {
                    actualExtractedMin = 0;
                }

                // Only produce crafts for which every ingredient was extracted.
                if (actualExtractedMin > 0) {
                    List<ItemStack> results = this.resultItems(this.recipe, consumedStacks);
                    for (ItemStack result : results) {
                        int actualCount = result.getCount() * actualExtractedMin;

                        // Split into multiple stacks if needed (max stack size limit)
                        int remaining = actualCount;
                        while (remaining > 0) {
                            ItemStack toOutput = result.copy();
                            int maxStackSize = toOutput.getMaxStackSize();
                            int toTake = Math.min(remaining, maxStackSize);
                            toOutput.setCount(toTake);
                            this.putIntoOutputOrDrop(toOutput);
                            remaining -= toTake;
                        }
                    }

                    this.onCrafted(this.recipe, actualExtractedMin);
                }

                this.recipe = null;
                this.needsRecipeUpdate();
                this.countCraftPerRecipe = this.countCraft;

            }

        }
    }

    protected boolean canMatchRecipes() {
        return true;
    }

    protected boolean matchRecipe(T recipe, List<ItemStack> stacks) {
        return !RecipeHelper.matches(recipe, stacks, false);
    }

    protected void onCrafted(T recipe, int countItemCraft) {
    }

    protected List<ItemStack> resultItems(T recipe, List<ItemStack> stacks) {
        assert this.level != null;
        return recipe.getResultItem(this.level.registryAccess()).isEmpty() ? List.of() : List.of(recipe.getResultItem(this.level.registryAccess()).copy());
    }

    protected void putIntoOutputOrDrop(ItemStack stack) {
        if (this.level != null && !this.level.isClientSide) {
            IAdvancedItemHandlerModifiable inventory = this.getInventory().getUnrestricted();
            ItemStack left = stack.copy();

            for(int slot = this.firstOutputSlot; slot < inventory.getSlots(); ++slot) {
                left = inventory.insertItem(slot, left, false);
                if (left.isEmpty()) {
                    return;
                }
            }

            if (!left.isEmpty()) {


                ItemEntity ie = new ItemEntity(this.level, (double)this.worldPosition.getX() + 0.5, (double)this.worldPosition.getY() + 0.7, (double)this.worldPosition.getZ() + 0.5, left.copy());
                this.level.addFreshEntity(ie);
            }

        }
    }

    public void needsRecipeUpdate() {
        this.needsRecipeUpdate = true;
    }

    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);
        this.needsRecipeUpdate = true;
    }
}
