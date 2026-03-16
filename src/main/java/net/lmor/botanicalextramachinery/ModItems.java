package net.lmor.botanicalextramachinery;

import net.lmor.botanicalextramachinery.Items.*;
import net.minecraft.world.item.Item;
import org.moddingx.libx.annotation.registration.RegisterClass;
import org.moddingx.libx.base.ItemBase;


@RegisterClass(registry = "ITEMS", priority = 1)
public class ModItems {

    public static final Item malachiteDragonstone;
    public static final Item malachiteIngot;

    public static final Item saffronDragonstone;
    public static final Item saffronIngot;

    public static final Item shadowDragonstone;
    public static final Item shadowIngot;

    public static final Item crimsonDragonstone;
    public static final Item crimsonIngot;

    public static final Item crystalDragonstone;
    public static final Item crystalIngot;
    public static final Item aureateDragonstone;
    public static final Item aureateIngot;
    public static final Item mazarineDragonstone;
    public static final Item mazarineIngot;

    public static final Item catalystPattern;
    public static final Item catalystManaInfinity;
    public static final Item catalystLivingRockInfinity;
    public static final Item catalystWaterInfinity;
    public static final Item catalystSeedInfinity;
    public static final Item catalystSpeed;
    public static final Item catalystStoneInfinity;
    public static final Item catalystWoodInfinity;
    public static final Item catalystPetal;
    public static final Item catalystPetalBlock;

    public static final Item baseSpark;
    public static final Item malachiteSpark;
    public static final Item saffronSpark;
    public static final Item shadowSpark;
    public static final Item crimsonSpark;
    public static final Item aureateSpark;
    public static final Item mazarineSpark;

    public static final Item upgradePattern;
    public static final ItemUpgrade upgradeCostEnergy;
    public static final ItemUpgrade upgradeFlower_4x;
    public static final ItemUpgrade upgradeFlower_16x;
    public static final ItemUpgrade upgradeFlower_32x;
    public static final ItemUpgrade upgradeFlower_64x;
    public static final ItemUpgrade upgradeGenMana;
    public static final ItemUpgrade upgradeHeatGreenhouse;
    public static final ItemUpgrade upgradeSlotAdd;
    public static final ItemUpgrade upgradeStorageMana_1;
    public static final ItemUpgrade upgradeStorageMana_2;
    public static final ItemUpgrade upgradeStorageMana_3;
    public static final ItemUpgrade upgradeStorageEnergy_1;
    public static final ItemUpgrade upgradeStorageEnergy_2;
    public static final ItemUpgrade upgradeStorageEnergy_3;
    public static final ItemUpgrade upgradeTickGenMana_1;
    public static final ItemUpgrade upgradeTickGenMana_2;
    public ModItems() {
    }

    static {

        baseSpark = new ItemBaseManaSpark(ExtraMachinery.getInstance(), new Item.Properties());
        malachiteSpark = new ItemMalachiteManaSpark(ExtraMachinery.getInstance(), new Item.Properties());
        saffronSpark = new ItemSaffronManaSpark(ExtraMachinery.getInstance(), new Item.Properties());
        shadowSpark = new ItemShadowManaSpark(ExtraMachinery.getInstance(), new Item.Properties());
        crimsonSpark = new ItemCrimsonManaSpark(ExtraMachinery.getInstance(), new Item.Properties());
        aureateSpark = new ItemAureateManaSpark(ExtraMachinery.getInstance(), new Item.Properties());
        mazarineSpark = new ItemMazarineManaSpark(ExtraMachinery.getInstance(), new Item.Properties());

        malachiteDragonstone = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        saffronDragonstone = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        shadowDragonstone = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        crimsonDragonstone = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        crystalDragonstone = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        aureateDragonstone = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        mazarineDragonstone = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());

        malachiteIngot = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        saffronIngot = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        shadowIngot = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        crimsonIngot = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        crystalIngot = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        aureateIngot = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        mazarineIngot = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());

        catalystPattern = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        catalystManaInfinity = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        catalystLivingRockInfinity = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        catalystWaterInfinity = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        catalystSeedInfinity = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        catalystSpeed = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        catalystStoneInfinity = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        catalystWoodInfinity = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        catalystPetal = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        catalystPetalBlock = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());

        upgradePattern = new ItemBase(ExtraMachinery.getInstance(), new Item.Properties());
        upgradeCostEnergy = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 0);
        upgradeFlower_4x = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 4);
        upgradeFlower_16x = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 16);
        upgradeFlower_32x = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 32);
        upgradeFlower_64x = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 64);
        upgradeGenMana = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 0);
        upgradeHeatGreenhouse = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 0);
        upgradeSlotAdd = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 0);
        upgradeStorageMana_1 = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 10);
        upgradeStorageMana_2 = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 100);
        upgradeStorageMana_3 = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 1000);
        upgradeStorageEnergy_1 = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 10);
        upgradeStorageEnergy_2 = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 100);
        upgradeStorageEnergy_3 = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 1000);
        upgradeTickGenMana_1 = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 25);
        upgradeTickGenMana_2 = new ItemUpgrade(ExtraMachinery.getInstance(), new Item.Properties(), 50);
    }
}