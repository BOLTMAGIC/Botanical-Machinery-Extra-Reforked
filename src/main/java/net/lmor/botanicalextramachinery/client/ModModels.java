package net.lmor.botanicalextramachinery.client;

import net.lmor.botanicalextramachinery.ExtraMachinery;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class ModModels {

    public static final ModModels INSTANCE = new ModModels();

    public final Material baseSparkWorldIcon = mainAtlas("item/base_spark");
    public final Material malachiteSparkWorldIcon = mainAtlas("item/malachite_spark");
    public final Material saffronSparkWorldIcon = mainAtlas("item/saffron_spark");
    public final Material shadowSparkWorldIcon = mainAtlas("item/shadow_spark");
    public final Material crimsonSparkWorldIcon = mainAtlas("item/crimson_spark");
    public final Material aureateSparkWorldIcon = mainAtlas("item/aureate_spark");
    public final Material mazarineSparkWorldIcon = mainAtlas("item/mazarine_spark");

    private static Material mainAtlas(String name) {
        return new Material(InventoryMenu.BLOCK_ATLAS, res(name));
    }

    private static ResourceLocation res(String name){
        return new ResourceLocation(ExtraMachinery.MOD_ID, name);
    }

}
