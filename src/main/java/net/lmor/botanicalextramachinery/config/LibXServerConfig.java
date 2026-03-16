package net.lmor.botanicalextramachinery.config;

import org.moddingx.libx.annotation.config.RegisterConfig;
import org.moddingx.libx.config.Config;
import org.moddingx.libx.config.Group;
import org.moddingx.libx.config.validate.IntRange;

@RegisterConfig("server")
public class LibXServerConfig {
    public LibXServerConfig() {
    }

    @Group({"Spark rate"})
    public static class SparkTier {

        public static int baseSpark = 1000;
        public static int malachiteSpark = 25000;
        public static int saffronSpark = 100000;
        public static int shadowSpark = 500000;
        public static int crimsonSpark = 1000000;
        public static int aureateSpark = 2500000;
        public static int mazarineSpark = 5000000;

        public SparkTier() {}
    }

    @Group({"Mana pool settings for all types"})
    public static class ManaPoolSettings {

        public static class baseManaPool {
            @Config({"Crafting time in ticks"})
            @IntRange( min = 1 )
            public static int craftTime = 10;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 2500000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 4;
            public baseManaPool(){}
        }

        public static class upgradedManaPool {
            @Config({"Crafting time in ticks"})
            @IntRange( min = 1 )
            public static int craftTime = 10;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 10000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 8;
            public upgradedManaPool(){}
        }

        public static class advancedManaPool {
            @Config({"Crafting time in ticks"})
            @IntRange( min = 1 )
            public static int craftTime = 10;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 50000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 16;
            public advancedManaPool(){}
        }

        public static class ultimateManaPool {
            @Config({"Crafting time in ticks"})
            @IntRange( min = 1 )
            public static int craftTime = 10;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 100000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 32;
            public ultimateManaPool(){}
        }
        public ManaPoolSettings() {}
    }

    @Group({"Runic Altar for all types"})
    public static class RunicAltarSettings {

        @Config({"Mana cost per craft tick. Total speed is calculated as manaCost * craftTime"})
        @IntRange( min = 1 )
        public static int manaCost = 100;

        public static class baseRunicAltar {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 2500000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 4;
            public baseRunicAltar(){}
        }

        public static class upgradedRunicAltar {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 10000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 8;
            public upgradedRunicAltar(){}
        }

        public static class advancedRunicAltar {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 50000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 16;
            public advancedRunicAltar(){}
        }

        public static class ultimateRunicAltar {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 100000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 32;
            public ultimateRunicAltar(){}
        }
        public RunicAltarSettings() {}
    }

    @Group({"Daisy for all types"})
    public static class DaisySettings {

        @Config({"How many ticks until the next update"})
        @IntRange( min = 1 )
        public static int ticksToNextUpdate = 5;

        public static class baseDaisy {
            @Config({"Crafting duration. This number is multiplied by the crafting time in the recipe."})
            public static int durationTime = 1;
            @Config({"How much of an item can be stored in a slot. Affects how many items can be made at once."})
            @IntRange( min = 1, max = 64)
            public static int sizeItemSlots = 4;

            public baseDaisy(){}
        }

        public static class upgradedDaisy {
            @Config({"Crafting duration. This number is multiplied by the crafting time in the recipe."})
            public static int durationTime = 1;
            @Config({"How much of an item can be stored in a slot. Affects how many items can be made at once."})
            @IntRange( min = 1, max = 64)
            public static int sizeItemSlots = 8;
            public upgradedDaisy(){}
        }

        public static class advancedDaisy {
            @Config({"Crafting duration. This number is multiplied by the crafting time in the recipe."})
            public static int durationTime = 1;
            @Config({"How much of an item can be stored in a slot. Affects how many items can be made at once."})
            @IntRange( min = 1, max = 64)
            public static int sizeItemSlots = 16;
            public advancedDaisy(){}
        }

        public static class ultimateDaisy {
            @Config({"Crafting duration. This number is multiplied by the crafting time in the recipe."})
            public static int durationTime = 1;
            @Config({"How much of an item can be stored in a slot. Affects how many items can be made at once."})
            @IntRange( min = 1, max = 64)
            public static int sizeItemSlots = 32;
            public ultimateDaisy(){}
        }
        public DaisySettings() {}
    }

    @Group({"Apothecary for all types"})
    public static class ApothecarySettings {

        @Config({"Speed of crafting. Total speed is calculated as workingDuration * craftTime"})
        @IntRange( min = 1 )
        public static int workingDuration = 20;

        public static class baseApothecary {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Water storage"})
            @IntRange( min = 1000 )
            public static int fluidStorage = 16000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 4;
            public baseApothecary(){}
        }

        public static class upgradedApothecary {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Water storage"})
            @IntRange( min = 1000 )
            public static int fluidStorage = 32000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 8;
            public upgradedApothecary(){}
        }

        public static class advancedApothecary {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Water storage"})
            @IntRange( min = 1000 )
            public static int fluidStorage = 64000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 16;
            public advancedApothecary(){}
        }

        public static class ultimateApothecary {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Water storage"})
            @IntRange( min = 1000 )
            public static int fluidStorage = 128000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 32;
            public ultimateApothecary(){}
        }
        public ApothecarySettings() {}
    }

    @Group({"Industrial Agglomeration Factory for all types"})
    public static class IndustrialAgglomerationFactorySettings {

        @Config({"Maximum mana output per recipe per tick. Total speed is calculated as workingDuration * craftTime"})
        @IntRange( min = 1 )
        public static int workingDuration = 5000;

        public static class baseAgglomeration {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 2500000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 4;
            public baseAgglomeration(){}
        }

        public static class upgradedAgglomeration {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 10000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 8;
            public upgradedAgglomeration(){}
        }

        public static class advancedAgglomeration {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1000 )
            public static int manaStorage = 50000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 16;
            public advancedAgglomeration(){}
        }

        public static class ultimateAgglomeration {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1000 )
            public static int manaStorage = 100000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 32;
            public ultimateAgglomeration(){}
        }
        public IndustrialAgglomerationFactorySettings() {}
    }

    @Group({"ManaInfuser for all types (For request mod Mythical Botany)"})
    public static class ManaInfuserSettings {

        @Config({"Maximum mana output per recipe per tick. Total speed is calculated as workingDuration * craftTime"})
        @IntRange( min = 1 )
        public static int workingDuration = 5000;

        public static class baseManaInfuser {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 2500000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 4;
            public baseManaInfuser(){}
        }

        public static class upgradedManaInfuser {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 10000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 8;
            public upgradedManaInfuser(){}
        }

        public static class advancedManaInfuser {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1000 )
            public static int manaStorage = 50000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 16;
            public advancedManaInfuser(){}
        }

        public static class ultimateManaInfuser {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1000 )
            public static int manaStorage = 100000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 32;
            public ultimateManaInfuser(){}
        }
        public ManaInfuserSettings() {}
    }

    @Group({"Alfheim Market for all types"})
    public static class AlfheimMarketSettings {

        @Config({"Maximum mana output per recipe per tick. Total speed is calculated as workingDuration * craftTime"})
        @IntRange( min = 1 )
        public static int workingDuration = 25;

        @Config({"How much mana does it take to create."})
        @IntRange( min = 1 )
        public static int recipeCost = 500;

        public static class baseAlfheimMarket {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 2500000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 4;
            public baseAlfheimMarket(){}
        }

        public static class upgradedAlfheimMarket {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 10000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 8;
            public upgradedAlfheimMarket(){}
        }

        public static class advancedAlfheimMarket {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1000 )
            public static int manaStorage = 50000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 16;
            public advancedAlfheimMarket(){}
        }

        public static class ultimateAlfheimMarket {
            @Config({"Crafting speed increase factor"})
            @IntRange( min = 1 )
            public static int craftTime = 1;
            @Config({"Mana Storage"})
            @IntRange( min = 1000 )
            public static int manaStorage = 100000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 32;
            public ultimateAlfheimMarket(){}
        }
        public AlfheimMarketSettings() {}
    }

    @Group({"Orechid for all types"})
    public static class OrechidSettings {
        @Config({"How much mana is required to create 1 ore."})
        @IntRange( min = 1 )
        public static int recipeCost = 10000;

        public static class baseOrechid {
            @Config({"'Rest' time after crafting. In ticks"})
            @IntRange( min = 1 )
            public static int cooldown = 10;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 2500000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 4;
            public baseOrechid(){}
        }

        public static class upgradedOrechid {
            @Config({"'Rest' time after crafting. In ticks"})
            @IntRange( min = 1 )
            public static int cooldown = 10;
            @Config({"Mana Storage"})
            @IntRange( min = 1 )
            public static int manaStorage = 10000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 8;
            public upgradedOrechid(){}
        }

        public static class advancedOrechid {
            @Config({"'Rest' time after crafting. In ticks"})
            @IntRange( min = 1 )
            public static int cooldown = 10;
            @Config({"Mana Storage"})
            @IntRange( min = 1000 )
            public static int manaStorage = 50000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 16;
            public advancedOrechid(){}
        }

        public static class ultimateOrechid {
            @Config({"'Rest' time after crafting. In ticks"})
            @IntRange( min = 1 )
            public static int cooldown = 10;
            @Config({"Mana Storage"})
            @IntRange( min = 1000 )
            public static int manaStorage = 100000000;
            @Config({"Max amount of crafting at a time"})
            @IntRange( min = 1, max = 64)
            public static int countCraft = 32;
            public ultimateOrechid(){}
        }

        public OrechidSettings() {}
    }

    @Group({"Jaded Amaranthus settings"})
    public static class JadedAmaranthusSettings {
        @Config({"Mana Storage"})
        @IntRange( min = 1 )
        public static int manaStorage = 1000000;

        @Config({"Cooldown per recipe. For all color petal/petal block/flower"})
        @IntRange( min = 1 )
        public static int cooldown = 30;

        @Config({"Count craft. For petal countCraft * 2. For petal block = countCraft as for a flower"})
        @IntRange( min = 1 )
        public static int countCraft = 1;

        @Config({"Cost mana per one craft for petal/petal block/flower"})
        @IntRange(min = 1)
        public static int costMana = 200;

        public JadedAmaranthusSettings() {}
    }

    @Group({"Greenhouse settings"})
    public static class GreenhouseSettings {
        @Config({"Base mana Storage"})
        @IntRange( min = 1 )
        public static int manaStorage = 1000000;

        @Config({"Energy Storage"})
        @IntRange( min = 1 )
        public static int energyCapacity = 1000000;

        @Config({"Max add energy"})
        @IntRange( min = 1, max = 2147483647 )
        public static int energyTransfer = 2147483647;

        @Config({"Cost energy for one interaction"})
        @IntRange( min = 1 )
        public static int energyCost = 2000;

        @Config({"Sleep greenhouse"})
        @IntRange( min = 1 )
        public static int sleep = 20;

        @Group({"Mana for some flowers"})
        public static class Flowers {
            @Config({"Entropinnyum (For one tnt)"})
            @IntRange( min = 1 )
            public static int entropinnyum = 6500;

            @Config({"Kekimurus (For one full cake)"})
            @IntRange( min = 1 )
            public static int kekimurus = 7200;

            @Config({"Munchdew (For one foliage)"})
            @IntRange( min = 1 )
            public static int munchdew = 160;

            @Config({"Narslimmus (For one slime ball)"})
            @IntRange( min = 1 )
            public static int narslimmus = 100;

            @Config({"Narslimmus (For one flower)"})
            @IntRange( min = 1 )
            public static int rafflowsia = 100;

            @Config({"Narslimmus (For one experience point)"})
            @IntRange( min = 1 )
            public static int rosaArcana = 50;
            public static int witherAconite = 1200000;

            public Flowers() {}
        }

        public GreenhouseSettings() {}
    }

    @Config({"How long will it take for a request to send resources from output slots to ME? (In ticks)"})
    @IntRange( min = 1 )
    public static int tickOutputSlots = 20;

    @Config({"Show/hide mechanism name in GUI. Priority over client."})
    public static boolean nameMechanism = true;

}

