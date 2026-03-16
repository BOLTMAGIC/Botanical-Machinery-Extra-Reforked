package net.lmor.botanicalextramachinery.data;

import net.lmor.botanicalextramachinery.ModBlocks;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.model.BlockStateProviderBase;

public class BlockStates extends BlockStateProviderBase {
    public BlockStates(DatagenContext context) {
        super(context);
    }

    protected void setup() {
        this.manualModel(ModBlocks.malachiteDragonstoneBlock);
        this.manualModel(ModBlocks.saffronDragonstoneBlock);
        this.manualModel(ModBlocks.shadowDragonstoneBlock);
        this.manualModel(ModBlocks.crimsonDragonstoneBlock);
        this.manualModel(ModBlocks.aureateDragonstoneBlock);
        this.manualModel(ModBlocks.mazarineDragonstoneBlock);

        this.manualModel(ModBlocks.baseAlfheimMarket);
        this.manualModel(ModBlocks.upgradedAlfheimMarket);
        this.manualModel(ModBlocks.advancedAlfheimMarket);
        this.manualModel(ModBlocks.ultimateAlfheimMarket);

        this.manualModel(ModBlocks.baseApothecary);
        this.manualModel(ModBlocks.upgradedApothecary);
        this.manualModel(ModBlocks.advancedApothecary);
        this.manualModel(ModBlocks.ultimateApothecary);

        this.manualModel(ModBlocks.baseDaisy);
        this.manualModel(ModBlocks.upgradedDaisy);
        this.manualModel(ModBlocks.advancedDaisy);
        this.manualModel(ModBlocks.ultimateDaisy);

        this.manualModel(ModBlocks.baseManaPool);
        this.manualModel(ModBlocks.upgradedManaPool);
        this.manualModel(ModBlocks.advancedManaPool);
        this.manualModel(ModBlocks.ultimateManaPool);

        this.manualModel(ModBlocks.baseRunicAltar);
        this.manualModel(ModBlocks.upgradedRunicAltar);
        this.manualModel(ModBlocks.advancedRunicAltar);
        this.manualModel(ModBlocks.ultimateRunicAltar);

        this.manualModel(ModBlocks.baseIndustrialAgglomerationFactory);
        this.manualModel(ModBlocks.upgradedIndustrialAgglomerationFactory);
        this.manualModel(ModBlocks.advancedIndustrialAgglomerationFactory);
        this.manualModel(ModBlocks.ultimateIndustrialAgglomerationFactory);

        this.manualModel(ModBlocks.baseOrechid);
        this.manualModel(ModBlocks.upgradedOrechid);
        this.manualModel(ModBlocks.advancedOrechid);
        this.manualModel(ModBlocks.ultimateOrechid);

        this.manualModel(ModBlocks.jadedAmaranthus);
    }
}
