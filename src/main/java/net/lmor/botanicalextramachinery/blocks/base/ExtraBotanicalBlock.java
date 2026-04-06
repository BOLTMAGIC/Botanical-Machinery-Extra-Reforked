package net.lmor.botanicalextramachinery.blocks.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.moddingx.libx.base.tile.MenuBlockBE;
import org.moddingx.libx.menu.BlockEntityMenu;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.SetupContext;
import org.moddingx.libx.render.ItemStackRenderer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class ExtraBotanicalBlock<T extends ExtraBotanicalTile, C extends BlockEntityMenu<T>> extends MenuBlockBE<T, C> {
    public static final VoxelShape FRAME_SHAPE = Shapes.or(box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0), box(0.0, 0.0, 0.0, 1.0, 16.0, 1.0), box(15.0, 0.0, 0.0, 16.0, 16.0, 1.0), box(0.0, 0.0, 15.0, 1.0, 16.0, 16.0), box(15.0, 0.0, 15.0, 16.0, 16.0, 16.0), box(0.0, 15.0, 0.0, 1.0, 16.0, 16.0), box(0.0, 15.0, 0.0, 16.0, 16.0, 1.0), box(15.0, 15.0, 0.0, 16.0, 16.0, 16.0), box(0.0, 15.0, 15.0, 16.0, 16.0, 16.0));
    public final boolean fullCube;
    public final boolean specialRender;

    public ExtraBotanicalBlock(ModX mod, Class<T> teClass, MenuType<C> menu, boolean fullCube, boolean specialRender) {
        super(mod, teClass, menu, fullCube ? Properties.copy(Blocks.STONE).strength(2.0F, 10.0F) : Properties.copy(Blocks.STONE).strength(2.0F, 10.0F).dynamicShape().noOcclusion().forceSolidOn(), new Item.Properties());
        this.fullCube = fullCube;
        this.specialRender = specialRender;
    }

    public void registerClient(SetupContext ctx) {
        if (this.specialRender) {
            ItemStackRenderer.addRenderBlock(this.getBlockEntityType(), true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void initializeItemClient(@Nonnull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(ItemStackRenderer.createProperties());
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ExtraBotanicalTile blockEntity = (ExtraBotanicalTile)level.getBlockEntity(pos);
        if (blockEntity == null) {
            return null;
        } else {
            CompoundTag tag = blockEntity.serializeNBT();
            ItemStack stack = new ItemStack(this);
            stack.setTag(tag);
            return stack;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public boolean hasAnalogOutputSignal(@Nonnull BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos) {
        return this.getBlockEntity(level, pos).getComparatorOutput();
    }

    @Override
    public int getLightBlock(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos) {
        return !this.fullCube ? 0 : super.getLightBlock(state, level, pos);
    }

    public boolean propagatesSkylightDown(@Nonnull BlockState state, @Nonnull BlockGetter reader, @Nonnull BlockPos pos) {
        return !this.fullCube;
    }

    @Override
    public boolean useShapeForLightOcclusion(@Nonnull BlockState state) {
        return !this.fullCube;
    }

    @Override
    @Nonnull
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return !this.fullCube ? FRAME_SHAPE : super.getShape(state, level, pos, context);
    }

    @Override
    protected boolean shouldDropInventory(Level level, BlockPos pos, BlockState state) {
        return true;
    }
}
