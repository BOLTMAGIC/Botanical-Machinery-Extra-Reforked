package net.lmor.botanicalextramachinery.data;

import net.lmor.botanicalextramachinery.ModBlocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.tags.CommonTagsProviderBase;

public class CommonTags extends CommonTagsProviderBase {
    public CommonTags(DatagenContext context) {
        super(context);
    }

    public void setup() {
        this.block(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.malachiteDragonstoneBlock);
        this.block(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.saffronDragonstoneBlock);
        this.block(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.crystalDragonstoneBlock);
        this.block(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.malachiteIngotBlock);
        this.block(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.saffronIngotBlock);
        this.block(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.crystalIngotBlock);

        this.block(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.shadowDragonstoneBlock);
        this.block(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.crimsonDragonstoneBlock);
        this.block(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.aureateDragonstoneBlock);
        this.block(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.mazarineDragonstoneBlock);
        this.block(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.shadowIngotBlock);
        this.block(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.crimsonIngotBlock);
        this.block(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.aureateIngotBlock);
        this.block(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.mazarineIngotBlock);
    }

    public void defaultBlockTags(Block block) {
        this.block(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
    }
}
