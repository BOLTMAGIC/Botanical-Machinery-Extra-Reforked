package net.lmor.botanicalextramachinery.events;

import mythicbotany.register.ModBlocks;
import net.lmor.botanicalextramachinery.ExtraMachinery;
import net.lmor.botanicalextramachinery.blocks.flowersGreenhouse.GenFlowers;
import net.lmor.botanicalextramachinery.blocks.flowersGreenhouse.flowers.*;
import net.lmor.botanicalextramachinery.blocks.tiles.BlockEntityGreenhouse;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolUltimate;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolUpgraded;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.common.block.BotaniaFlowerBlocks;

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
            GenFlowers.addAllGenFlowers(ModBlocks.witherAconite.asItem(), new WitherAconite());
        }

    }
}