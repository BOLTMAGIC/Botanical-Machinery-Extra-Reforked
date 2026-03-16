package net.lmor.botanicalextramachinery.Items;

import net.lmor.botanicalextramachinery.entities.manaSpark.EntityMazarineManaSpark;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.moddingx.libx.base.ItemBase;
import org.moddingx.libx.mod.ModX;
import vazkii.botania.api.mana.spark.SparkAttachable;
import vazkii.botania.xplat.XplatAbstractions;

public class ItemMazarineManaSpark extends ItemBase {

    public ItemMazarineManaSpark(ModX mod, Properties properties) {
        super(mod, properties);
    }

    public @NotNull InteractionResult useOn(UseOnContext ctx) {
        return attachSpark(ctx.getLevel(), ctx.getClickedPos(), ctx.getItemInHand()) ? InteractionResult.sidedSuccess(ctx.getLevel().isClientSide) : InteractionResult.PASS;
    }

    public static boolean attachSpark(Level world, BlockPos pos, ItemStack stack) {
        SparkAttachable attach = XplatAbstractions.INSTANCE.findSparkAttachable(world, pos, world.getBlockState(pos), world.getBlockEntity(pos), Direction.UP);
        if (attach != null && attach.canAttachSpark(stack) && attach.getAttachedSpark() == null) {
            if (!world.isClientSide) {
                stack.shrink(1);
                EntityMazarineManaSpark spark = new EntityMazarineManaSpark(world);
                spark.setPos((double)pos.getX() + 0.5, (double)pos.getY() + 1.25, (double)pos.getZ() + 0.5);
                world.addFreshEntity(spark);
                attach.attachSpark(spark);
            }

            return true;
        } else {
            return false;
        }
    }
}

