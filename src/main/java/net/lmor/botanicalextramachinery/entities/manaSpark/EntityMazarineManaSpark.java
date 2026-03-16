package net.lmor.botanicalextramachinery.entities.manaSpark;

import net.lmor.botanicalextramachinery.ModEntities;
import net.lmor.botanicalextramachinery.ModItems;
import net.lmor.botanicalextramachinery.config.LibXServerConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;


public class EntityMazarineManaSpark extends EntityManaSparkPattern {

    private static int RATE = LibXServerConfig.SparkTier.mazarineSpark;

    public EntityMazarineManaSpark(EntityType<?> entityEntityType, Level level) {
        super(entityEntityType, level);
        this.TRANSFER_RATE = RATE;
    }

    public static int getRate(){
        return RATE;
    }

    public EntityMazarineManaSpark(Level level){
        this(ModEntities.MAZARINE_SPARK, level);
    }

    @Override
    protected Item getSparkItem() {
        return ModItems.mazarineSpark;
    }
}

