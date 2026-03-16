package net.lmor.botanicalextramachinery.events;

import net.lmor.botanicalextramachinery.ExtraMachinery;
import net.lmor.botanicalextramachinery.ModBlocks;
import net.lmor.botanicalextramachinery.ModItems;
import net.lmor.botanicalextramachinery.entities.manaSpark.*;
import net.lmor.botanicalextramachinery.util.NumberFormatter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.lmor.botanicalextramachinery.ModBlocks.*;
import static net.lmor.botanicalextramachinery.ModItems.*;

@Mod.EventBusSubscriber(modid = ExtraMachinery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TooltipHandler {

    public static final List<Item> tooltipManaAllBlocks = new ArrayList<>();
    public static final List<Item> tooltipEnergyAllBlocks = new ArrayList<>();
    public static final List<Item> tooltipWaterAllBlocks = new ArrayList<>();
    public static final List<Item> tooltipSparks = new ArrayList<>();
    public static final Map<Item, String[]> catalystList = new HashMap<>();
    public static final Map<Item, String[]> upgradeGreenhouse = new HashMap<>();

    private static void addItem(){
        tooltipManaAllBlocks.add(baseManaPool.asItem());
        tooltipManaAllBlocks.add(upgradedManaPool.asItem());
        tooltipManaAllBlocks.add(advancedManaPool.asItem());
        tooltipManaAllBlocks.add(ultimateManaPool.asItem());

        tooltipManaAllBlocks.add(baseRunicAltar.asItem());
        tooltipManaAllBlocks.add(upgradedRunicAltar.asItem());
        tooltipManaAllBlocks.add(advancedRunicAltar.asItem());
        tooltipManaAllBlocks.add(ultimateRunicAltar.asItem());

        tooltipManaAllBlocks.add(baseIndustrialAgglomerationFactory.asItem());
        tooltipManaAllBlocks.add(upgradedIndustrialAgglomerationFactory.asItem());
        tooltipManaAllBlocks.add(advancedIndustrialAgglomerationFactory.asItem());
        tooltipManaAllBlocks.add(ultimateIndustrialAgglomerationFactory.asItem());

        tooltipManaAllBlocks.add(baseAlfheimMarket.asItem());
        tooltipManaAllBlocks.add(ultimateAlfheimMarket.asItem());
        tooltipManaAllBlocks.add(ultimateAlfheimMarket.asItem());
        tooltipManaAllBlocks.add(ultimateAlfheimMarket.asItem());

        tooltipManaAllBlocks.add(baseOrechid.asItem());
        tooltipManaAllBlocks.add(ultimateOrechid.asItem());
        tooltipManaAllBlocks.add(ultimateOrechid.asItem());
        tooltipManaAllBlocks.add(ultimateOrechid.asItem());

        tooltipManaAllBlocks.add(jadedAmaranthus.asItem());
        tooltipManaAllBlocks.add(ModBlocks.greenhouse.asItem());

        tooltipEnergyAllBlocks.add(ModBlocks.greenhouse.asItem());

        tooltipWaterAllBlocks.add(baseApothecary.asItem());
        tooltipWaterAllBlocks.add(upgradedApothecary.asItem());
        tooltipWaterAllBlocks.add(advancedApothecary.asItem());
        tooltipWaterAllBlocks.add(ultimateApothecary.asItem());

        tooltipSparks.add(baseSpark);
        tooltipSparks.add(malachiteSpark);
        tooltipSparks.add(saffronSpark);
        tooltipSparks.add(shadowSpark);
        tooltipSparks.add(crimsonSpark);
        tooltipSparks.add(aureateSpark);
        tooltipSparks.add(mazarineSpark);

        catalystList.put(catalystManaInfinity, new String[] { "botanicalextramachinery.tooltip.item.mana_infinity" });
        catalystList.put(catalystLivingRockInfinity, new String[] { "botanicalextramachinery.tooltip.item.livingrock" });
        catalystList.put(catalystWaterInfinity, new String[] { "botanicalextramachinery.tooltip.item.water" });
        catalystList.put(catalystSeedInfinity, new String[] { "botanicalextramachinery.tooltip.item.seed" });
        catalystList.put(catalystStoneInfinity, new String[] { "botanicalextramachinery.tooltip.item.stone" });
        catalystList.put(catalystWoodInfinity, new String[] { "botanicalextramachinery.tooltip.item.wood" });
        catalystList.put(catalystSpeed, new String[] { "botanicalextramachinery.tooltip.item.upgrade_speed" });
        catalystList.put(catalystPetal, new String[] { "botanicalextramachinery.tooltip.item.catalyst_petal_1", "botanicalextramachinery.tooltip.item.catalyst_petal_3" });
        catalystList.put(catalystPetalBlock, new String[] { "botanicalextramachinery.tooltip.item.catalyst_petal_2", "botanicalextramachinery.tooltip.item.catalyst_petal_3" });

        upgradeGreenhouse.put(upgradeCostEnergy, new String[] { "botanicalextramachinery.tooltip.item.upgradeCostEnergy" });
        upgradeGreenhouse.put(upgradeFlower_4x, new String[] { "botanicalextramachinery.tooltip.item.upgradeFlower_4x" });
        upgradeGreenhouse.put(upgradeFlower_16x, new String[] { "botanicalextramachinery.tooltip.item.upgradeFlower_16x" });
        upgradeGreenhouse.put(upgradeFlower_32x, new String[] { "botanicalextramachinery.tooltip.item.upgradeFlower_32x" });
        upgradeGreenhouse.put(upgradeFlower_64x, new String[] { "botanicalextramachinery.tooltip.item.upgradeFlower_64x" });
        upgradeGreenhouse.put(upgradeGenMana, new String[] { "botanicalextramachinery.tooltip.item.upgradeGenMana" });
        upgradeGreenhouse.put(upgradeSlotAdd, new String[] { "botanicalextramachinery.tooltip.item.upgradeSlotAdd" });
        upgradeGreenhouse.put(upgradeHeatGreenhouse, new String[] { "botanicalextramachinery.tooltip.item.upgradeHeatGreenhouse" });
        upgradeGreenhouse.put(upgradeStorageMana_1, new String[] { "botanicalextramachinery.tooltip.item.upgradeStorageMana_1" });
        upgradeGreenhouse.put(upgradeStorageMana_2, new String[] { "botanicalextramachinery.tooltip.item.upgradeStorageMana_2" });
        upgradeGreenhouse.put(upgradeStorageMana_3, new String[] { "botanicalextramachinery.tooltip.item.upgradeStorageMana_3" });
        upgradeGreenhouse.put(upgradeStorageEnergy_1, new String[] { "botanicalextramachinery.tooltip.item.upgradeStorageEnergy_1" });
        upgradeGreenhouse.put(upgradeStorageEnergy_2, new String[] { "botanicalextramachinery.tooltip.item.upgradeStorageEnergy_2" });
        upgradeGreenhouse.put(upgradeStorageEnergy_3, new String[] { "botanicalextramachinery.tooltip.item.upgradeStorageEnergy_3" });
        upgradeGreenhouse.put(upgradeTickGenMana_1, new String[] { "botanicalextramachinery.tooltip.item.upgradeTickGenMana_1" });
        upgradeGreenhouse.put(upgradeTickGenMana_2, new String[] { "botanicalextramachinery.tooltip.item.upgradeTickGenMana_2" });

    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        if (tooltipManaAllBlocks.size() == 0) addItem();

        ItemStack stack = event.getItemStack();
        if (!stack.isEmpty()){
            CompoundTag tag = stack.getTag();

            if (tooltipManaAllBlocks.contains(stack.getItem()) && stack.hasTag()){
                if (tag.getCompound("BlockEntityTag").contains("mana")){
                    int mana = tag.getCompound("BlockEntityTag").getInt("mana");

                    event.getToolTip().add(Component.translatable("botanicalextramachinery.tooltip.block.mana", NumberFormatter.formatInteger(mana)));
                }
            }

            if (tooltipWaterAllBlocks.contains(stack.getItem()) && stack.hasTag()){

                if (tag.getCompound("BlockEntityTag").contains("fluid")){
                    int water = tag.getCompound("BlockEntityTag").getCompound("fluid").getInt("Amount");

                    event.getToolTip().add(Component.translatable("botanicalextramachinery.tooltip.block.water", NumberFormatter.formatIntegerWater(water)));
                }
            }

            if (tooltipEnergyAllBlocks.contains(stack.getItem()) && stack.hasTag()){

                if (tag.getCompound("BlockEntityTag").contains("energyStorage")){
                    int energy = tag.getCompound("BlockEntityTag").getInt("energyStorage");

                    event.getToolTip().add(Component.translatable("botanicalextramachinery.tooltip.block.energy", NumberFormatter.formatInteger(energy)));
                }
            }

            if (tooltipSparks.contains(stack.getItem().asItem())) {
                int transfer = 0;
                if (stack.getItem() == baseSpark) {
                    transfer = EntityBaseManaSpark.getRate();
                } else if (stack.getItem() == malachiteSpark) {
                    transfer = EntityMalachiteManaSpark.getRate();
                } else if (stack.getItem() == saffronSpark) {
                    transfer = EntitySaffronManaSpark.getRate();
                } else if (stack.getItem() == shadowSpark) {
                    transfer = EntityShadowManaSpark.getRate();
                } else if (stack.getItem() == crimsonSpark) {
                    transfer = EntityCrimsonManaSpark.getRate();
                } else if (stack.getItem() == aureateSpark) {
                    transfer = EntityAureateManaSpark.getRate();
                } else if (stack.getItem() == mazarineSpark) {
                    transfer = EntityMazarineManaSpark.getRate();
                }

                if (transfer != 0) {
                    event.getToolTip().add(Component.translatable("botanicalextramachinery.tooltip.item.sparkTransfer", NumberFormatter.formatInteger(transfer)));
                }
            }

            if (catalystList.containsKey(stack.getItem())){
                for (String tooltip_add: catalystList.get(stack.getItem())){
                    event.getToolTip().add(Component.translatable(tooltip_add));
                }
            }

            if (upgradeGreenhouse.containsKey(stack.getItem())){
                for (String tooltip_add: upgradeGreenhouse.get(stack.getItem())){
                    event.getToolTip().add(Component.translatable(tooltip_add));
                }
            }

        }
    }
}