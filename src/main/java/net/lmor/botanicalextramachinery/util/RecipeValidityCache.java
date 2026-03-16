package net.lmor.botanicalextramachinery.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.moddingx.libx.crafting.RecipeHelper;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lightweight cache for RecipeHelper.isItemValidInput to reduce repeated Ingredient.test calls.
 * Safe/cautious approach:
 *  - Do NOT cache ItemStacks that have NBT tags (to avoid incorrect positives for NBT-sensitive ingredients)
 *  - Cache by (RecipeType, Item) for stacks without NBT and without tags
 *  - Invalidate type-specific cache if the provided RecipeManager instance changes (weak ref)
 */
public final class RecipeValidityCache {

    private static final ConcurrentHashMap<RecipeType<?>, TypeCache> CACHE = new ConcurrentHashMap<>();

    private RecipeValidityCache() {}

    public static boolean isItemValidInput(RecipeManager manager, RecipeType<?> type, ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;

        // If stack contains NBT, don't cache — Ingredients may match based on NBT
        if (stack.hasTag()) {
            return RecipeHelper.isItemValidInput(manager, type, stack);
        }

        Item item = stack.getItem();

        TypeCache typeCache = CACHE.compute(type, (t, old) -> {
            if (old == null) {
                TypeCache tc = new TypeCache(manager);
                return tc;
            } else {
                RecipeManager ref = old.managerRef.get();
                if (ref == null || ref != manager) {
                    // recipe manager instance changed or was GC'd -> reset cache
                    return new TypeCache(manager);
                } else {
                    return old;
                }
            }
        });

        Boolean cached = typeCache.map.get(item);
        if (cached != null) return cached;

        boolean res = RecipeHelper.isItemValidInput(manager, type, stack);
        typeCache.map.put(item, res);
        return res;
    }

    /**
     * Clear all cached validity results. Call on recipe/datapack reload to avoid stale caches.
     */
    public static void invalidateAll() {
        CACHE.clear();
    }

    private static final class TypeCache {
        final WeakReference<RecipeManager> managerRef;
        final ConcurrentHashMap<Item, Boolean> map = new ConcurrentHashMap<>();

        TypeCache(RecipeManager manager) {
            this.managerRef = new WeakReference<>(Objects.requireNonNull(manager));
        }
    }
}

