package net.lmor.botanicalextramachinery.entities.manaSpark;

import net.lmor.botanicalextramachinery.ModEntities;
import net.lmor.botanicalextramachinery.ModItems;
import net.lmor.botanicalextramachinery.config.LibXServerConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;


public class EntityAureateManaSpark extends EntityManaSparkPattern {

    private static int RATE = LibXServerConfig.SparkTier.aureateSpark;

    public EntityAureateManaSpark(EntityType<?> entityEntityType, Level level) {
        super(entityEntityType, level);
        this.TRANSFER_RATE = RATE;
    }

    public static int getRate(){
        return RATE;
    }

    public EntityAureateManaSpark(Level level){
        this(ModEntities.AUREATE_SPARK, level);
    }

    @Override
    protected Item getSparkItem() {
        return ModItems.aureateSpark;
    }
}

