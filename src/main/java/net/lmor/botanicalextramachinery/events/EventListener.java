package net.lmor.botanicalextramachinery.events;
import net.lmor.botanicalextramachinery.ExtraMachinery;
import net.lmor.botanicalextramachinery.blocks.flowersGreenhouse.GenFlowers;
import net.lmor.botanicalextramachinery.blocks.flowersGreenhouse.flowers.*;
import net.lmor.botanicalextramachinery.blocks.tiles.BlockEntityGreenhouse;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolUltimate;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolUpgraded;
import vazkii.botania.common.block.BotaniaFlowerBlocks;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.lmor.botanicalextramachinery.blocks.pattern.BlockEntityOrechidPattern;
import net.lmor.botanicalextramachinery.blocks.pattern.BlockEntityDaisyPattern;
import net.lmor.botanicalextramachinery.util.RecipeValidityCache;

@Mod.EventBusSubscriber(modid = ExtraMachinery.MOD_ID)
public class EventListener {
    public EventListener() {
    }

    @SubscribeEvent
    public static void resourcesReload(OnDatapackSyncEvent event) {
        BlockEntityManaPoolBase.invalidateCatalysts();
        BlockEntityManaPoolUpgraded.invalidateCatalysts();
        BlockEntityManaPoolAdvanced.invalidateCatalysts();
        BlockEntityManaPoolUltimate.invalidateCatalysts();
        RecipeValidityCache.invalidateAll();
        BlockEntityOrechidPattern.invalidateOrechidCaches();
        BlockEntityDaisyPattern.invalidatePureDaisyCache();
    }

    @SubscribeEvent
    public static void flowerInit(OnDatapackSyncEvent event){
        GenFlowers.addAllGenFlowers(BotaniaFlowerBlocks.endoflame.asItem(), new Endoflame());
        GenFlowers.addAllGenFlowers(BotaniaFlowerBlocks.entropinnyum.asItem(), new Entropinnyum());
        GenFlowers.addAllGenFlowers(BotaniaFlowerBlocks.gourmaryllis.asItem(), new Gourmaryllis());
        GenFlowers.addAllGenFlowers(BotaniaFlowerBlocks.kekimurus.asItem(), new Kekimurus());
        GenFlowers.addAllGenFlowers(BotaniaFlowerBlocks.munchdew.asItem(), new Munchdew());
        GenFlowers.addAllGenFlowers(BotaniaFlowerBlocks.narslimmus.asItem(), new Narslimmus());
        GenFlowers.addAllGenFlowers(BotaniaFlowerBlocks.rafflowsia.asItem(), new Rafflowsia());
        GenFlowers.addAllGenFlowers(BotaniaFlowerBlocks.rosaArcana.asItem(), new RosaArcana());

        if (ModList.get().isLoaded("mythicbotany")){
            try {
                // Use reflection to avoid hard dependency on MythicBotany classes
                Class<?> mythicModBlocks = Class.forName("mythicbotany.register.ModBlocks");
                java.lang.reflect.Field f = mythicModBlocks.getField("witherAconite");
                Object witherBlock = f.get(null);
                if (witherBlock != null) {
                    // Call asItem() reflectively
                    java.lang.reflect.Method asItem = witherBlock.getClass().getMethod("asItem");
                    Object item = asItem.invoke(witherBlock);
                    if (item instanceof net.minecraft.world.item.Item) {
                        GenFlowers.addAllGenFlowers((net.minecraft.world.item.Item)item, new WitherAconite());
                    } else if (item instanceof net.minecraft.world.item.ItemStack) {
                        GenFlowers.addAllGenFlowers(((net.minecraft.world.item.ItemStack)item).getItem(), new WitherAconite());
                    }
                }
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
                // MythicBotany not present or reflection failed; skip adding the flower
            }
        }

                if (val instanceof net.minecraft.world.level.block.Block) {
                    net.minecraft.world.level.block.Block block = (net.minecraft.world.level.block.Block) val;
                    GenFlowers.addAllGenFlowers(block.asItem(), new WitherAconite());
                }
            } catch (Throwable t) {
                // ignore: if reflection fails, skip adding the flower
            }
        }
    }
}