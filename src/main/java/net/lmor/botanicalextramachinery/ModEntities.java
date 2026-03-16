package net.lmor.botanicalextramachinery;

import net.lmor.botanicalextramachinery.core.LibNames;
import net.lmor.botanicalextramachinery.entities.manaSpark.*;
import net.lmor.botanicalextramachinery.core.LibResources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import vazkii.botania.api.block.WandHUD;

import java.util.function.BiConsumer;
import java.util.function.Function;


public class ModEntities {
    public static final EntityType<EntityBaseManaSpark> BASE_SPARK = EntityType.Builder.<EntityBaseManaSpark>of(EntityBaseManaSpark::new, MobCategory.MISC).sized(0.2F, 0.5F).fireImmune().clientTrackingRange(4).updateInterval(10).build(LibNames.BASE_SPARK);
    public static final EntityType<EntityMalachiteManaSpark> MALACHITE_SPARK = EntityType.Builder.<EntityMalachiteManaSpark>of(EntityMalachiteManaSpark::new, MobCategory.MISC).sized(0.2F, 0.5F).fireImmune().clientTrackingRange(4).updateInterval(10).build(LibNames.MALACHITE_SPARK);
    public static final EntityType<EntitySaffronManaSpark> SAFFRON_SPARK = EntityType.Builder.<EntitySaffronManaSpark>of(EntitySaffronManaSpark::new, MobCategory.MISC).sized(0.2F, 0.5F).fireImmune().clientTrackingRange(4).updateInterval(10).build(LibNames.SAFFRON_SPARK);
    public static final EntityType<EntityShadowManaSpark> SHADOW_SPARK = EntityType.Builder.<EntityShadowManaSpark>of(EntityShadowManaSpark::new, MobCategory.MISC).sized(0.2F, 0.5F).fireImmune().clientTrackingRange(4).updateInterval(10).build(LibNames.SHADOW_SPARK);
    public static final EntityType<EntityCrimsonManaSpark> CRIMSON_SPARK = EntityType.Builder.<EntityCrimsonManaSpark>of(EntityCrimsonManaSpark::new, MobCategory.MISC).sized(0.2F, 0.5F).fireImmune().clientTrackingRange(4).updateInterval(10).build(LibNames.CRIMSON_SPARK);
    public static final EntityType<EntityAureateManaSpark> AUREATE_SPARK = EntityType.Builder.<EntityAureateManaSpark>of(EntityAureateManaSpark::new, MobCategory.MISC).sized(0.2F, 0.5F).fireImmune().clientTrackingRange(4).updateInterval(10).build(LibNames.AUREATE_SPARK);
    public static final EntityType<EntityMazarineManaSpark> MAZARINE_SPARK = EntityType.Builder.<EntityMazarineManaSpark>of(EntityMazarineManaSpark::new, MobCategory.MISC).sized(0.2F, 0.5F).fireImmune().clientTrackingRange(4).updateInterval(10).build(LibNames.MAZARINE_SPARK);

    public static void registerWandHudCaps(ECapConsumer<WandHUD> consumer) {
        consumer.accept(e -> new EntityBaseManaSpark.WandHud((EntityBaseManaSpark) e), BASE_SPARK);
        consumer.accept(e -> new EntityMalachiteManaSpark.WandHud((EntityMalachiteManaSpark) e), MALACHITE_SPARK);
        consumer.accept(e -> new EntitySaffronManaSpark.WandHud((EntitySaffronManaSpark) e), SAFFRON_SPARK);
        consumer.accept(e -> new EntityShadowManaSpark.WandHud((EntityShadowManaSpark) e), SHADOW_SPARK);
        consumer.accept(e -> new EntityCrimsonManaSpark.WandHud((EntityCrimsonManaSpark) e), CRIMSON_SPARK);
        consumer.accept(e -> new EntityAureateManaSpark.WandHud((EntityAureateManaSpark) e), AUREATE_SPARK);
        consumer.accept(e -> new EntityMazarineManaSpark.WandHud((EntityMazarineManaSpark) e), MAZARINE_SPARK);
    }

    @FunctionalInterface
    public interface ECapConsumer<T> {
        void accept(Function<Entity, T> factory, EntityType<?>... types);
    }

    public static void registerEntities(BiConsumer<EntityType<?>, ResourceLocation> r) {
        r.accept(BASE_SPARK, LibResources.BASE_SPARK);
        r.accept(MALACHITE_SPARK, LibResources.MALACHITE_SPARK);
        r.accept(SAFFRON_SPARK, LibResources.SAFFRON_SPARK);
        r.accept(SHADOW_SPARK, LibResources.SHADOW_SPARK);
        r.accept(CRIMSON_SPARK, LibResources.CRIMSON_SPARK);
        r.accept(AUREATE_SPARK, LibResources.AUREATE_SPARK);
        r.accept(MAZARINE_SPARK, LibResources.MAZARINE_SPARK);
    }
}