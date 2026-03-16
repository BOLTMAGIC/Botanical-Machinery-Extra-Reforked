package net.lmor.botanicalextramachinery.data;

import net.lmor.botanicalextramachinery.ModBlocks;
import net.lmor.botanicalextramachinery.ModItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.recipe.RecipeProviderBase;
import org.moddingx.libx.datagen.provider.recipe.crafting.CompressionExtension;
import org.moddingx.libx.datagen.provider.recipe.crafting.CraftingExtension;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.BotaniaFlowerBlocks;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.lib.BotaniaTags;

public class Recipes extends RecipeProviderBase implements CraftingExtension, CompressionExtension  {
    public Recipes(DatagenContext context) {
        super(context);
    }

    @Override
    protected void setup() {
        this.compress(ModItems.malachiteDragonstone, ModBlocks.malachiteDragonstoneBlock);
        this.compress(ModItems.saffronDragonstone, ModBlocks.saffronDragonstoneBlock);
        this.compress(ModItems.shadowDragonstone, ModBlocks.shadowDragonstoneBlock);
        this.compress(ModItems.crimsonDragonstone, ModBlocks.crimsonDragonstoneBlock);
        this.compress(ModItems.crystalDragonstone, ModBlocks.crystalDragonstoneBlock);
        this.compress(ModItems.aureateDragonstone, ModBlocks.aureateDragonstoneBlock);
        this.compress(ModItems.mazarineDragonstone, ModBlocks.mazarineDragonstoneBlock);

        this.compress(ModItems.malachiteIngot, ModBlocks.malachiteIngotBlock);
        this.compress(ModItems.saffronIngot, ModBlocks.saffronIngotBlock);
        this.compress(ModItems.shadowIngot, ModBlocks.shadowIngotBlock);
        this.compress(ModItems.crimsonIngot, ModBlocks.crimsonIngotBlock);
        this.compress(ModItems.crystalIngot, ModBlocks.crystalIngotBlock);
        this.compress(ModItems.aureateIngot, ModBlocks.aureateIngotBlock);
        this.compress(ModItems.mazarineIngot, ModBlocks.mazarineIngotBlock);

        this.machine(ModBlocks.jadedAmaranthus, BotaniaItems.manaRingGreater, ModItems.crystalIngot, BotaniaFlowerBlocks.jadedAmaranthus, ModBlocks.crystalDragonstoneBlock);

        this.machine(ModBlocks.baseIndustrialAgglomerationFactory, BotaniaItems.manaRingGreater, ModItems.malachiteIngot, de.melanx.botanicalmachinery.ModBlocks.industrialAgglomerationFactory, ModBlocks.malachiteDragonstoneBlock);
        this.machine(ModBlocks.baseManaPool, BotaniaItems.manaRingGreater, ModItems.malachiteIngot, de.melanx.botanicalmachinery.ModBlocks.mechanicalManaPool, ModBlocks.malachiteDragonstoneBlock);
        this.machine(ModBlocks.baseRunicAltar, BotaniaItems.manaRingGreater, ModItems.malachiteIngot, de.melanx.botanicalmachinery.ModBlocks.mechanicalRunicAltar, ModBlocks.malachiteDragonstoneBlock);
        this.machine(ModBlocks.baseDaisy, BotaniaItems.manaRingGreater, ModItems.malachiteIngot, de.melanx.botanicalmachinery.ModBlocks.mechanicalDaisy, ModBlocks.malachiteDragonstoneBlock);
        this.machine(ModBlocks.baseApothecary, BotaniaItems.manaRingGreater, ModItems.malachiteIngot, de.melanx.botanicalmachinery.ModBlocks.mechanicalApothecary, ModBlocks.malachiteDragonstoneBlock);
        this.machine(ModBlocks.baseAlfheimMarket, BotaniaItems.manaRingGreater, ModItems.malachiteIngot, de.melanx.botanicalmachinery.ModBlocks.alfheimMarket, ModBlocks.malachiteDragonstoneBlock);
        this.machine(ModBlocks.baseOrechid, BotaniaItems.manaRingGreater, ModItems.malachiteIngot, BotaniaFlowerBlocks.orechid, ModBlocks.malachiteDragonstoneBlock);

        this.machine(ModBlocks.upgradedIndustrialAgglomerationFactory, BotaniaBlocks.terrasteelBlock, ModItems.saffronIngot, ModBlocks.baseIndustrialAgglomerationFactory, ModBlocks.saffronDragonstoneBlock);
        this.machine(ModBlocks.upgradedManaPool, BotaniaBlocks.terrasteelBlock, ModItems.saffronIngot, ModBlocks.baseManaPool, ModBlocks.saffronDragonstoneBlock);
        this.machine(ModBlocks.upgradedRunicAltar, BotaniaBlocks.terrasteelBlock, ModItems.saffronIngot, ModBlocks.baseRunicAltar, ModBlocks.saffronDragonstoneBlock);
        this.machine(ModBlocks.upgradedDaisy, BotaniaBlocks.terrasteelBlock, ModItems.saffronIngot, ModBlocks.baseDaisy, ModBlocks.saffronDragonstoneBlock);
        this.machine(ModBlocks.upgradedApothecary, BotaniaBlocks.terrasteelBlock, ModItems.saffronIngot, ModBlocks.baseApothecary, ModBlocks.saffronDragonstoneBlock);
        this.machine(ModBlocks.upgradedAlfheimMarket, BotaniaBlocks.terrasteelBlock, ModItems.saffronIngot, ModBlocks.baseAlfheimMarket, ModBlocks.saffronDragonstoneBlock);
        this.machine(ModBlocks.upgradedOrechid, BotaniaBlocks.terrasteelBlock, ModItems.saffronIngot, ModBlocks.baseOrechid, ModBlocks.saffronDragonstoneBlock);

        this.machine(ModBlocks.advancedIndustrialAgglomerationFactory, ModBlocks.malachiteIngotBlock, ModItems.shadowIngot, ModBlocks.upgradedIndustrialAgglomerationFactory, ModBlocks.shadowDragonstoneBlock);
        this.machine(ModBlocks.advancedManaPool, ModBlocks.malachiteIngotBlock, ModItems.shadowIngot, ModBlocks.upgradedManaPool, ModBlocks.shadowDragonstoneBlock);
        this.machine(ModBlocks.advancedRunicAltar, ModBlocks.malachiteIngotBlock, ModItems.shadowIngot, ModBlocks.upgradedRunicAltar, ModBlocks.shadowDragonstoneBlock);
        this.machine(ModBlocks.advancedDaisy, ModBlocks.malachiteIngotBlock, ModItems.shadowIngot, ModBlocks.upgradedDaisy, ModBlocks.shadowDragonstoneBlock);
        this.machine(ModBlocks.advancedApothecary, ModBlocks.malachiteIngotBlock, ModItems.shadowIngot, ModBlocks.upgradedApothecary, ModBlocks.shadowDragonstoneBlock);
        this.machine(ModBlocks.advancedAlfheimMarket, ModBlocks.malachiteIngotBlock, ModItems.shadowIngot, ModBlocks.upgradedAlfheimMarket, ModBlocks.shadowDragonstoneBlock);
        this.machine(ModBlocks.advancedOrechid, ModBlocks.malachiteIngotBlock, ModItems.shadowIngot, ModBlocks.upgradedOrechid, ModBlocks.shadowDragonstoneBlock);

        this.machine(ModBlocks.ultimateIndustrialAgglomerationFactory, ModBlocks.saffronIngotBlock, ModItems.crimsonIngot, ModBlocks.advancedIndustrialAgglomerationFactory, ModBlocks.crimsonDragonstoneBlock);
        this.machine(ModBlocks.ultimateManaPool, ModBlocks.saffronIngotBlock, ModItems.crimsonIngot, ModBlocks.advancedManaPool, ModBlocks.crimsonDragonstoneBlock);
        this.machine(ModBlocks.ultimateRunicAltar, ModBlocks.saffronIngotBlock, ModItems.crimsonIngot, ModBlocks.advancedRunicAltar, ModBlocks.crimsonDragonstoneBlock);
        this.machine(ModBlocks.ultimateDaisy, ModBlocks.saffronIngotBlock, ModItems.crimsonIngot, ModBlocks.advancedDaisy, ModBlocks.crimsonDragonstoneBlock);
        this.machine(ModBlocks.ultimateApothecary, ModBlocks.saffronIngotBlock, ModItems.crimsonIngot, ModBlocks.advancedApothecary, ModBlocks.crimsonDragonstoneBlock);
        this.machine(ModBlocks.ultimateAlfheimMarket, ModBlocks.saffronIngotBlock, ModItems.crimsonIngot, ModBlocks.advancedAlfheimMarket, ModBlocks.crimsonDragonstoneBlock);
        this.machine(ModBlocks.ultimateOrechid, ModBlocks.saffronIngotBlock, ModItems.crimsonIngot, ModBlocks.advancedOrechid, ModBlocks.crimsonDragonstoneBlock);

        this.shaped(ModItems.catalystPattern, " a ", "bcb", " a ", 'a', Items.IRON_INGOT, 'b', Items.DIAMOND, 'c', BotaniaTags.Items.PETALS);
        this.shaped(ModItems.catalystManaInfinity, "cbc", "bab", "cbc", 'a', ModItems.catalystPattern, 'b', ModBlocks.crimsonDragonstoneBlock, 'c', ModItems.crimsonSpark);
        this.shaped(ModItems.catalystSpeed, " c ", "dad", "bbb", 'a', ModItems.catalystPattern, 'b', Blocks.REDSTONE_BLOCK, 'c', ModItems.saffronSpark, 'd', Items.IRON_INGOT);
        this.shaped(ModItems.catalystSeedInfinity, " c ", "bab", " c ", 'a', ModItems.catalystPattern, 'b', Tags.Items.SEEDS, 'c', Items.DIAMOND);
        this.shaped(ModItems.catalystLivingRockInfinity, "bdb", "bab", "bcb", 'a', ModItems.catalystPattern, 'b', BotaniaBlocks.livingrock, 'c', BotaniaBlocks.runeAltar, 'd', BotaniaItems.runeEnvy);
        this.shaped(ModItems.catalystWaterInfinity, " d ", "dad", "bbb", 'a', ModItems.catalystPattern, 'b', Items.WATER_BUCKET, 'd', BotaniaTags.Items.PETALS);
        this.shaped(ModItems.catalystStoneInfinity, "bcb", "bab", "bcb", 'a', ModItems.catalystPattern, 'b', Blocks.STONE, 'c', BotaniaItems.runePride);
        this.shaped(ModItems.catalystWoodInfinity, "bcb", "bab", "bcb", 'a', ModItems.catalystPattern, 'b', Blocks.OAK_LOG, 'c', BotaniaItems.runeSloth);

        this.shaped( ModItems.catalystPetal, "aba", "bcb", "aba", 'a', Items.DIAMOND, 'b', BotaniaTags.Items.PETALS, 'c', ModItems.catalystPattern);
        this.shaped(ModItems.catalystPetalBlock, "aaa", "aba", "aaa", 'a', ModItems.catalystPetal, 'b', Blocks.DIAMOND_BLOCK);

        this.shaped(ModItems.upgradePattern, " a ", "bcb", " a ", 'a', Items.DIAMOND, 'b', Items.NETHERITE_INGOT, 'c', BotaniaTags.Items.PETALS);

        this.shaped(ModItems.upgradeStorageEnergy_1, "aba", "cdc", "aba", 'a', ModItems.crystalIngot, 'b', Blocks.REDSTONE_BLOCK, 'c', Items.IRON_INGOT, 'd', ModItems.upgradePattern);
        this.shaped(ModItems.upgradeStorageEnergy_2, "aba", "bcb", "aba", 'a', ModItems.malachiteIngot, 'b', ModItems.upgradeStorageEnergy_1, 'c', BotaniaItems.manaSteel);
        this.shaped(ModItems.upgradeStorageEnergy_3, "aba", "bcb", "aba", 'a', ModItems.saffronIngot, 'b', ModItems.upgradeStorageEnergy_2, 'c', ModItems.malachiteIngot);

        this.shaped(ModItems.upgradeStorageMana_1, "aba", "cdc", "aba", 'a', ModItems.crystalIngot, 'b', Blocks.REDSTONE_BLOCK, 'c', Items.DIAMOND, 'd', ModItems.upgradePattern);
        this.shaped(ModItems.upgradeStorageMana_2, "aba", "bcb", "aba", 'a', ModItems.malachiteIngot, 'b', ModItems.upgradeStorageMana_1, 'c', BotaniaItems.manaSteel);
        this.shaped(ModItems.upgradeStorageMana_3, "aba", "bcb", "aba", 'a', ModItems.saffronIngot, 'b', ModItems.upgradeStorageMana_2, 'c', ModItems.malachiteIngot);

        this.shaped(ModItems.upgradeTickGenMana_2, "dbd", "aca", "dbd", 'a', ModItems.upgradeTickGenMana_1, 'b', ModItems.crimsonIngot, 'c', ModItems.upgradePattern, 'd', ModItems.crystalIngot);
        this.shaped(ModItems.upgradeSlotAdd, "cbc", "bab", "cbc", 'a', ModItems.upgradePattern, 'b', BotaniaItems.craftingHalo, 'c', Blocks.CHEST);

        this.shaped(ModItems.upgradeHeatGreenhouse, "cec", "bab", "ddd", 'a', ModItems.upgradePattern, 'b', ModItems.upgradeStorageEnergy_2, 'c', ModItems.saffronIngot, 'd', Blocks.REDSTONE_BLOCK, 'e', Blocks.GLASS);

        this.shaped(ModItems.upgradeGenMana, "beb", "faf", "dcd", 'a', ModItems.upgradePattern, 'b', ModItems.upgradeCostEnergy, 'c', ModItems.upgradeHeatGreenhouse, 'd', ModItems.upgradeStorageEnergy_1, 'e', Blocks.GLASS, 'f', Items.BLAZE_POWDER);

        this.shaped(ModItems.upgradeFlower_64x, "ddd", "bab", "ece", 'a', ModItems.upgradePattern, 'b', ModItems.upgradeFlower_32x, 'c', ModItems.upgradeSlotAdd, 'd', ModItems.crimsonIngot, 'e', ModBlocks.crimsonDragonstoneBlock);
        this.shaped(ModItems.upgradeFlower_32x, "ddd", "bab", "ece", 'a', ModItems.upgradePattern, 'b', ModItems.upgradeFlower_16x, 'c', ModItems.upgradeSlotAdd, 'd', ModItems.shadowIngot, 'e', ModBlocks.shadowDragonstoneBlock);
        this.shaped(ModItems.upgradeFlower_16x, "ddd", "bab", "ece", 'a', ModItems.upgradePattern, 'b', ModItems.upgradeFlower_4x, 'c', ModItems.upgradeSlotAdd, 'd', ModItems.saffronIngot, 'e', ModBlocks.saffronDragonstoneBlock);
        this.shaped(ModItems.upgradeFlower_4x, "ddd", "bab", "ece", 'a', ModItems.upgradePattern, 'b', Blocks.CHEST, 'c', ModItems.upgradeSlotAdd, 'd', ModItems.malachiteIngot, 'e', ModBlocks.malachiteDragonstoneBlock);

        this.shaped(ModItems.upgradeCostEnergy, "bcb", "dad", "efe", 'a', ModItems.upgradePattern, 'b', ModItems.upgradeStorageEnergy_1, 'c', ModItems.upgradeStorageEnergy_2, 'd', Blocks.REDSTONE_BLOCK, 'e', Items.DIAMOND, 'f', Items.NETHERITE_INGOT);

        this.shaped(ModItems.baseSpark, " a ", "bcb", " a ", 'a', BotaniaTags.Items.PETALS, 'b', Items.BLAZE_POWDER, 'c', Items.GOLD_NUGGET);

        this.shaped(ModBlocks.greenhouse, "aea", "dbd", "fcf", 'a', ModItems.crimsonIngot, 'b', Blocks.DIRT, 'c', ModBlocks.crimsonIngotBlock, 'd', ModItems.crimsonDragonstone, 'e', Blocks.GLASS, 'f', Blocks.NETHERITE_BLOCK);
    }

    private void machine(Object output, Object special1, Object special2, Object special3, Object special4) {
        this.shaped(output, "aba", "bcb", "ddd", 'a', special1, 'b', special2, 'c', special3, 'd', special4);
    }
}
