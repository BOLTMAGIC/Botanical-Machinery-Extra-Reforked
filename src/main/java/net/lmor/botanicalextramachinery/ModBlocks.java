package net.lmor.botanicalextramachinery;

import net.lmor.botanicalextramachinery.blocks.blockMachines.BlockGreenhouse;
import net.lmor.botanicalextramachinery.blocks.blockMachines.BlockJadedAmaranthus;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalAlfheimMarket.BlockAlfheimMarketAdvanced;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalAlfheimMarket.BlockAlfheimMarketBase;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalAlfheimMarket.BlockAlfheimMarketUltimate;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalAlfheimMarket.BlockAlfheimMarketUpgraded;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalApothecary.BlockApothecaryAdvanced;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalApothecary.BlockApothecaryBase;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalApothecary.BlockApothecaryUltimate;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalApothecary.BlockApothecaryUpgraded;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalDaisy.BlockDaisyAdvanced;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalDaisy.BlockDaisyBase;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalDaisy.BlockDaisyUltimate;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalDaisy.BlockDaisyUpgraded;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalIndustrialAgglomerationFactory.BlockIndustrialAgglomerationFactoryAdvanced;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalIndustrialAgglomerationFactory.BlockIndustrialAgglomerationFactoryBase;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalIndustrialAgglomerationFactory.BlockIndustrialAgglomerationFactoryUltimate;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalIndustrialAgglomerationFactory.BlockIndustrialAgglomerationFactoryUpgraded;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalManaInfuser.BlockManaInfuserAdvanced;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalManaInfuser.BlockManaInfuserBase;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalManaInfuser.BlockManaInfuserUltimate;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalManaInfuser.BlockManaInfuserUpgraded;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalManaPool.BlockManaPoolAdvanced;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalManaPool.BlockManaPoolBase;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalManaPool.BlockManaPoolUltimate;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalManaPool.BlockManaPoolUpgraded;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalOrechid.BlockOrechidAdvanced;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalOrechid.BlockOrechidBase;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalOrechid.BlockOrechidUltimate;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalOrechid.BlockOrechidUpgraded;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalRunicAltar.BlockRunicAltarAdvanced;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalRunicAltar.BlockRunicAltarBase;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalRunicAltar.BlockRunicAltarUltimate;
import net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalRunicAltar.BlockRunicAltarUpgraded;
import net.lmor.botanicalextramachinery.blocks.containers.ContainerGreenhouse;
import net.lmor.botanicalextramachinery.blocks.containers.ContainerJadedAmaranthus;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalAlfheimMarket.ContainerAlfheimMarketAdvanced;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalAlfheimMarket.ContainerAlfheimMarketBase;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalAlfheimMarket.ContainerAlfheimMarketUltimate;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalAlfheimMarket.ContainerAlfheimMarketUpgraded;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalApothecary.ContainerApothecaryAdvanced;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalApothecary.ContainerApothecaryBase;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalApothecary.ContainerApothecaryUltimate;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalApothecary.ContainerApothecaryUpgraded;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalDaisy.ContainerDaisyAdvanced;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalDaisy.ContainerDaisyBase;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalDaisy.ContainerDaisyUltimate;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalDaisy.ContainerDaisyUpgraded;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalIndustrialAgglomerationFactory.ContainerIndustrialAgglomerationFactoryAdvanced;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalIndustrialAgglomerationFactory.ContainerIndustrialAgglomerationFactoryBase;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalIndustrialAgglomerationFactory.ContainerIndustrialAgglomerationFactoryUltimate;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalIndustrialAgglomerationFactory.ContainerIndustrialAgglomerationFactoryUpgraded;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalManaInfuser.ContainerManaInfuserAdvanced;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalManaInfuser.ContainerManaInfuserBase;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalManaInfuser.ContainerManaInfuserUltimate;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalManaInfuser.ContainerManaInfuserUpgraded;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalManaPool.ContainerManaPoolAdvanced;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalManaPool.ContainerManaPoolBase;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalManaPool.ContainerManaPoolUltimate;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalManaPool.ContainerManaPoolUpgraded;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalOrechid.ContainerOrechidAdvanced;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalOrechid.ContainerOrechidBase;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalOrechid.ContainerOrechidUltimate;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalOrechid.ContainerOrechidUpgraded;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalRunicAltar.ContainerRunicAltarAdvanced;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalRunicAltar.ContainerRunicAltarBase;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalRunicAltar.ContainerRunicAltarUltimate;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalRunicAltar.ContainerRunicAltarUpgraded;
import net.lmor.botanicalextramachinery.blocks.tiles.BlockEntityGreenhouse;
import net.lmor.botanicalextramachinery.blocks.tiles.BlockEntityJadedAmaranthus;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalAlfheimMarket.BlockEntityAlfheimMarketAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalAlfheimMarket.BlockEntityAlfheimMarketBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalAlfheimMarket.BlockEntityAlfheimMarketUltimate;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalAlfheimMarket.BlockEntityAlfheimMarketUpgraded;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalApothecary.BlockEntityApothecaryAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalApothecary.BlockEntityApothecaryBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalApothecary.BlockEntityApothecaryUltimate;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalApothecary.BlockEntityApothecaryUpgraded;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalDaisy.BlockEntityDaisyAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalDaisy.BlockEntityDaisyBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalDaisy.BlockEntityDaisyUltimate;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalDaisy.BlockEntityDaisyUpgraded;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalIndustrialAgglomerationFactory.BlockEntityIndustrialAgglomerationFactoryAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalIndustrialAgglomerationFactory.BlockEntityIndustrialAgglomerationFactoryBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalIndustrialAgglomerationFactory.BlockEntityIndustrialAgglomerationFactoryUltimate;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalIndustrialAgglomerationFactory.BlockEntityIndustrialAgglomerationFactoryUpgraded;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaInfuser.BlockEntityManaInfuserAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaInfuser.BlockEntityManaInfuserBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaInfuser.BlockEntityManaInfuserUltimate;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaInfuser.BlockEntityManaInfuserUpgraded;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolUltimate;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalManaPool.BlockEntityManaPoolUpgraded;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalOrechid.BlockEntityOrechidAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalOrechid.BlockEntityOrechidBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalOrechid.BlockEntityOrechidUltimate;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalOrechid.BlockEntityOrechidUpgraded;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalRunicAltar.BlockEntityRunicAltarAdvanced;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalRunicAltar.BlockEntityRunicAltarBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalRunicAltar.BlockEntityRunicAltarUltimate;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalRunicAltar.BlockEntityRunicAltarUpgraded;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.moddingx.libx.annotation.registration.Reg;
import org.moddingx.libx.annotation.registration.RegisterClass;
import org.moddingx.libx.base.BlockBase;
import org.moddingx.libx.base.tile.MenuBlockBE;
import org.moddingx.libx.menu.BlockEntityMenu;
import net.minecraftforge.fml.ModList;

@RegisterClass(registry = "BLOCKS", priority = 1)
public class ModBlocks {
    public static final BlockBase malachiteDragonstoneBlock;
    public static final BlockBase malachiteIngotBlock;
    public static final BlockBase saffronDragonstoneBlock;
    public static final BlockBase saffronIngotBlock;
    public static final BlockBase shadowDragonstoneBlock;
    public static final BlockBase shadowIngotBlock;
    public static final BlockBase crimsonDragonstoneBlock;
    public static final BlockBase crimsonIngotBlock;
    public static final BlockBase crystalDragonstoneBlock;
    public static final BlockBase crystalIngotBlock;
    public static final BlockBase aureateDragonstoneBlock;
    public static final BlockBase aureateIngotBlock;
    public static final BlockBase mazarineDragonstoneBlock;
    public static final BlockBase mazarineIngotBlock;

    public static final MenuBlockBE<BlockEntityManaPoolBase, ContainerManaPoolBase> baseManaPool;
    public static final MenuBlockBE<BlockEntityManaPoolUpgraded, ContainerManaPoolUpgraded> upgradedManaPool;
    public static final MenuBlockBE<BlockEntityManaPoolAdvanced, ContainerManaPoolAdvanced> advancedManaPool;
    public static final MenuBlockBE<BlockEntityManaPoolUltimate, ContainerManaPoolUltimate> ultimateManaPool;

    public static final MenuBlockBE<BlockEntityRunicAltarBase, ContainerRunicAltarBase> baseRunicAltar;
    public static final MenuBlockBE<BlockEntityRunicAltarUpgraded, ContainerRunicAltarUpgraded> upgradedRunicAltar;
    public static final MenuBlockBE<BlockEntityRunicAltarAdvanced, ContainerRunicAltarAdvanced> advancedRunicAltar;
    public static final MenuBlockBE<BlockEntityRunicAltarUltimate, ContainerRunicAltarUltimate> ultimateRunicAltar;

    public static final MenuBlockBE<BlockEntityDaisyBase, ContainerDaisyBase> baseDaisy;
    public static final MenuBlockBE<BlockEntityDaisyUpgraded, ContainerDaisyUpgraded> upgradedDaisy;
    public static final MenuBlockBE<BlockEntityDaisyAdvanced, ContainerDaisyAdvanced> advancedDaisy;
    public static final MenuBlockBE<BlockEntityDaisyUltimate, ContainerDaisyUltimate> ultimateDaisy;

    public static final MenuBlockBE<BlockEntityApothecaryBase, ContainerApothecaryBase> baseApothecary;
    public static final MenuBlockBE<BlockEntityApothecaryUpgraded, ContainerApothecaryUpgraded> upgradedApothecary;
    public static final MenuBlockBE<BlockEntityApothecaryAdvanced, ContainerApothecaryAdvanced> advancedApothecary;
    public static final MenuBlockBE<BlockEntityApothecaryUltimate, ContainerApothecaryUltimate> ultimateApothecary;

    public static final MenuBlockBE<BlockEntityIndustrialAgglomerationFactoryBase, ContainerIndustrialAgglomerationFactoryBase> baseIndustrialAgglomerationFactory;
    public static final MenuBlockBE<BlockEntityIndustrialAgglomerationFactoryUpgraded, ContainerIndustrialAgglomerationFactoryUpgraded> upgradedIndustrialAgglomerationFactory;
    public static final MenuBlockBE<BlockEntityIndustrialAgglomerationFactoryAdvanced, ContainerIndustrialAgglomerationFactoryAdvanced> advancedIndustrialAgglomerationFactory;
    public static final MenuBlockBE<BlockEntityIndustrialAgglomerationFactoryUltimate, ContainerIndustrialAgglomerationFactoryUltimate> ultimateIndustrialAgglomerationFactory;

    public static final MenuBlockBE<BlockEntityAlfheimMarketBase, ContainerAlfheimMarketBase> baseAlfheimMarket;
    public static final MenuBlockBE<BlockEntityAlfheimMarketUpgraded, ContainerAlfheimMarketUpgraded> upgradedAlfheimMarket;
    public static final MenuBlockBE<BlockEntityAlfheimMarketAdvanced, ContainerAlfheimMarketAdvanced> advancedAlfheimMarket;
    public static final MenuBlockBE<BlockEntityAlfheimMarketUltimate, ContainerAlfheimMarketUltimate> ultimateAlfheimMarket;

    public static final MenuBlockBE<BlockEntityOrechidBase, ContainerOrechidBase> baseOrechid;
    public static final MenuBlockBE<BlockEntityOrechidUpgraded, ContainerOrechidUpgraded> upgradedOrechid;
    public static final MenuBlockBE<BlockEntityOrechidAdvanced, ContainerOrechidAdvanced> advancedOrechid;
    public static final MenuBlockBE<BlockEntityOrechidUltimate, ContainerOrechidUltimate> ultimateOrechid;

    public static final MenuBlockBE<BlockEntityJadedAmaranthus, ContainerJadedAmaranthus> jadedAmaranthus;
    public static final MenuBlockBE<BlockEntityGreenhouse, ContainerGreenhouse> greenhouse;

    @Reg.Exclude
    public static final MenuBlockBE<BlockEntityManaInfuserBase, ContainerManaInfuserBase> baseManaInfuser;
    @Reg.Exclude
    public static final MenuBlockBE<BlockEntityManaInfuserUpgraded, ContainerManaInfuserUpgraded> upgradedManaInfuser;
    @Reg.Exclude
    public static final MenuBlockBE<BlockEntityManaInfuserAdvanced, ContainerManaInfuserAdvanced> advancedManaInfuser;
    @Reg.Exclude
    public static final MenuBlockBE<BlockEntityManaInfuserUltimate, ContainerManaInfuserUltimate> ultimateManaInfuser;

    public ModBlocks() {

    }

    static {

        malachiteDragonstoneBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));
        saffronDragonstoneBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));
        shadowDragonstoneBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));
        crimsonDragonstoneBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));
        crystalDragonstoneBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));
        aureateDragonstoneBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));
        mazarineDragonstoneBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));

        malachiteIngotBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));
        saffronIngotBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));
        shadowIngotBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));
        crimsonIngotBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));
        crystalIngotBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));
        aureateIngotBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));
        mazarineIngotBlock = new BlockBase(ExtraMachinery.getInstance(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.0F, 6.0F).sound(SoundType.METAL));

        baseManaPool = new BlockManaPoolBase(ExtraMachinery.getInstance(), BlockEntityManaPoolBase.class, BlockEntityMenu.createMenuType(ContainerManaPoolBase::new));
        upgradedManaPool = new BlockManaPoolUpgraded(ExtraMachinery.getInstance(), BlockEntityManaPoolUpgraded.class, BlockEntityMenu.createMenuType(ContainerManaPoolUpgraded::new));
        advancedManaPool = new BlockManaPoolAdvanced(ExtraMachinery.getInstance(), BlockEntityManaPoolAdvanced.class, BlockEntityMenu.createMenuType(ContainerManaPoolAdvanced::new));
        ultimateManaPool = new BlockManaPoolUltimate(ExtraMachinery.getInstance(), BlockEntityManaPoolUltimate.class, BlockEntityMenu.createMenuType(ContainerManaPoolUltimate::new));

        baseRunicAltar = new BlockRunicAltarBase(ExtraMachinery.getInstance(), BlockEntityRunicAltarBase.class, BlockEntityMenu.createMenuType(ContainerRunicAltarBase::new));
        upgradedRunicAltar = new BlockRunicAltarUpgraded(ExtraMachinery.getInstance(), BlockEntityRunicAltarUpgraded.class, BlockEntityMenu.createMenuType(ContainerRunicAltarUpgraded::new));
        advancedRunicAltar = new BlockRunicAltarAdvanced(ExtraMachinery.getInstance(), BlockEntityRunicAltarAdvanced.class, BlockEntityMenu.createMenuType(ContainerRunicAltarAdvanced::new));
        ultimateRunicAltar = new BlockRunicAltarUltimate(ExtraMachinery.getInstance(), BlockEntityRunicAltarUltimate.class, BlockEntityMenu.createMenuType(ContainerRunicAltarUltimate::new));

        baseDaisy = new BlockDaisyBase(ExtraMachinery.getInstance(), BlockEntityDaisyBase.class, BlockEntityMenu.createMenuType(ContainerDaisyBase::new));
        upgradedDaisy = new BlockDaisyUpgraded(ExtraMachinery.getInstance(), BlockEntityDaisyUpgraded.class, BlockEntityMenu.createMenuType(ContainerDaisyUpgraded::new));
        advancedDaisy = new BlockDaisyAdvanced(ExtraMachinery.getInstance(), BlockEntityDaisyAdvanced.class, BlockEntityMenu.createMenuType(ContainerDaisyAdvanced::new));
        ultimateDaisy = new BlockDaisyUltimate(ExtraMachinery.getInstance(), BlockEntityDaisyUltimate.class, BlockEntityMenu.createMenuType(ContainerDaisyUltimate::new));

        baseApothecary = new BlockApothecaryBase(ExtraMachinery.getInstance(), BlockEntityApothecaryBase.class, BlockEntityMenu.createMenuType(ContainerApothecaryBase::new));
        upgradedApothecary = new BlockApothecaryUpgraded(ExtraMachinery.getInstance(), BlockEntityApothecaryUpgraded.class, BlockEntityMenu.createMenuType(ContainerApothecaryUpgraded::new));
        advancedApothecary = new BlockApothecaryAdvanced(ExtraMachinery.getInstance(), BlockEntityApothecaryAdvanced.class, BlockEntityMenu.createMenuType(ContainerApothecaryAdvanced::new));
        ultimateApothecary = new BlockApothecaryUltimate(ExtraMachinery.getInstance(), BlockEntityApothecaryUltimate.class, BlockEntityMenu.createMenuType(ContainerApothecaryUltimate::new));

        baseIndustrialAgglomerationFactory = new BlockIndustrialAgglomerationFactoryBase(ExtraMachinery.getInstance(), BlockEntityIndustrialAgglomerationFactoryBase.class, BlockEntityMenu.createMenuType(ContainerIndustrialAgglomerationFactoryBase::new));
        upgradedIndustrialAgglomerationFactory = new BlockIndustrialAgglomerationFactoryUpgraded(ExtraMachinery.getInstance(), BlockEntityIndustrialAgglomerationFactoryUpgraded.class, BlockEntityMenu.createMenuType(ContainerIndustrialAgglomerationFactoryUpgraded::new));
        advancedIndustrialAgglomerationFactory = new BlockIndustrialAgglomerationFactoryAdvanced(ExtraMachinery.getInstance(), BlockEntityIndustrialAgglomerationFactoryAdvanced.class, BlockEntityMenu.createMenuType(ContainerIndustrialAgglomerationFactoryAdvanced::new));
        ultimateIndustrialAgglomerationFactory = new BlockIndustrialAgglomerationFactoryUltimate(ExtraMachinery.getInstance(), BlockEntityIndustrialAgglomerationFactoryUltimate.class, BlockEntityMenu.createMenuType(ContainerIndustrialAgglomerationFactoryUltimate::new));

        baseAlfheimMarket = new BlockAlfheimMarketBase(ExtraMachinery.getInstance(), BlockEntityAlfheimMarketBase.class, BlockEntityMenu.createMenuType(ContainerAlfheimMarketBase::new));
        upgradedAlfheimMarket = new BlockAlfheimMarketUpgraded(ExtraMachinery.getInstance(), BlockEntityAlfheimMarketUpgraded.class, BlockEntityMenu.createMenuType(ContainerAlfheimMarketUpgraded::new));
        advancedAlfheimMarket = new BlockAlfheimMarketAdvanced(ExtraMachinery.getInstance(), BlockEntityAlfheimMarketAdvanced.class, BlockEntityMenu.createMenuType(ContainerAlfheimMarketAdvanced::new));
        ultimateAlfheimMarket = new BlockAlfheimMarketUltimate(ExtraMachinery.getInstance(), BlockEntityAlfheimMarketUltimate.class, BlockEntityMenu.createMenuType(ContainerAlfheimMarketUltimate::new));

        baseOrechid = new BlockOrechidBase(ExtraMachinery.getInstance(), BlockEntityOrechidBase.class, BlockEntityMenu.createMenuType(ContainerOrechidBase::new));
        upgradedOrechid = new BlockOrechidUpgraded(ExtraMachinery.getInstance(), BlockEntityOrechidUpgraded.class, BlockEntityMenu.createMenuType(ContainerOrechidUpgraded::new));
        advancedOrechid = new BlockOrechidAdvanced(ExtraMachinery.getInstance(), BlockEntityOrechidAdvanced.class, BlockEntityMenu.createMenuType(ContainerOrechidAdvanced::new));
        ultimateOrechid = new BlockOrechidUltimate(ExtraMachinery.getInstance(), BlockEntityOrechidUltimate.class, BlockEntityMenu.createMenuType(ContainerOrechidUltimate::new));

        // Initialize MythicBotany mana infuser blocks only if the mod is present
        if (ModList.get().isLoaded("mythicbotany")) {
            baseManaInfuser = new BlockManaInfuserBase(ExtraMachinery.getInstance(), BlockEntityManaInfuserBase.class, BlockEntityMenu.createMenuType(ContainerManaInfuserBase::new));
            upgradedManaInfuser = new BlockManaInfuserUpgraded(ExtraMachinery.getInstance(), BlockEntityManaInfuserUpgraded.class, BlockEntityMenu.createMenuType(ContainerManaInfuserUpgraded::new));
            advancedManaInfuser = new BlockManaInfuserAdvanced(ExtraMachinery.getInstance(), BlockEntityManaInfuserAdvanced.class, BlockEntityMenu.createMenuType(ContainerManaInfuserAdvanced::new));
            ultimateManaInfuser = new BlockManaInfuserUltimate(ExtraMachinery.getInstance(), BlockEntityManaInfuserUltimate.class, BlockEntityMenu.createMenuType(ContainerManaInfuserUltimate::new));
        } else {
            baseManaInfuser = null;
            upgradedManaInfuser = null;
            advancedManaInfuser = null;
            ultimateManaInfuser = null;
        }

        jadedAmaranthus = new BlockJadedAmaranthus(ExtraMachinery.getInstance(), BlockEntityJadedAmaranthus.class, BlockEntityMenu.createMenuType(ContainerJadedAmaranthus::new));
        greenhouse = new BlockGreenhouse(ExtraMachinery.getInstance(), BlockEntityGreenhouse.class, BlockEntityMenu.createMenuType(ContainerGreenhouse::new));
    }
}
