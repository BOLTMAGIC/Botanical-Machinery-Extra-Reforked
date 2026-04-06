package net.lmor.botanicalextramachinery.blocks.blockMachines.mechanicalApothecary;

import net.lmor.botanicalextramachinery.ModBlocks;
import net.lmor.botanicalextramachinery.blocks.base.ExtraBotanicalBlock;
import net.lmor.botanicalextramachinery.blocks.containers.mechanicalApothecary.ContainerApothecaryBase;
import net.lmor.botanicalextramachinery.blocks.screens.mechanicalApothecary.ScreenApothecaryBase;
import net.lmor.botanicalextramachinery.blocks.tesr.mechanicalApothecary.RenderApothecaryBase;
import net.lmor.botanicalextramachinery.blocks.tiles.mechanicalApothecary.BlockEntityApothecaryBase;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import org.moddingx.libx.base.tile.BlockEntityBase;
import org.moddingx.libx.base.tile.MenuBlockBE;
import org.moddingx.libx.block.RotationShape;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.SetupContext;
import org.moddingx.libx.render.ItemStackRenderer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class BlockApothecaryBase extends MenuBlockBE<BlockEntityApothecaryBase, ContainerApothecaryBase> {
    public static final RotationShape SHAPE;

    public BlockApothecaryBase(ModX mod, Class<BlockEntityApothecaryBase> teClass, MenuType<ContainerApothecaryBase> menu) {
        super(mod, teClass, menu, Properties.copy(Blocks.STONE).strength(2.0F, 10.0F).dynamicShape().forceSolidOn().noOcclusion(), new Item.Properties());
    }

    @OnlyIn(Dist.CLIENT)
    public void registerClient(SetupContext ctx) {
        ItemStackRenderer.addRenderBlock(this.getBlockEntityType(), true);
        MenuScreens.register(ModBlocks.baseApothecary.menu, ScreenApothecaryBase::new);
        BlockEntityRenderers.register(this.getBlockEntityType(), (context) -> new RenderApothecaryBase());
    }

    @OnlyIn(Dist.CLIENT)
    public void initializeItemClient(@Nonnull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(ItemStackRenderer.createProperties());
    }

    @Nonnull
    public InteractionResult use(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity tile = level.getBlockEntity(pos);
            ItemStack held = player.getMainHandItem();
            assert tile != null;
            @SuppressWarnings("DataFlowIssue") FluidActionResult fluidActionResult = FluidUtil.tryEmptyContainer(held, tile.getCapability(ForgeCapabilities.FLUID_HANDLER, null).orElse( null), 1000, player, true);
            if (fluidActionResult.isSuccess()) {
                if (tile instanceof BlockEntityBase) {
                    ((BlockEntityBase)tile).setDispatchable();
                }

                if (!player.isCreative()) {
                    player.setItemInHand(hand, fluidActionResult.getResult());
                }

                return InteractionResult.SUCCESS;
            }

            super.use(state, level, pos, player, hand, hit);
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    public int getLightBlock(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos) {
        return 0;
    }

    public boolean propagatesSkylightDown(@Nonnull BlockState state, @Nonnull BlockGetter reader, @Nonnull BlockPos pos) {
        return true;
    }

    public boolean useShapeForLightOcclusion(@Nonnull BlockState state) {
        return true;
    }

    @Nonnull
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE.getShape(state.getValue(BlockStateProperties.HORIZONTAL_FACING));
    }

    static {
        SHAPE = new RotationShape(Shapes.or(ExtraBotanicalBlock.FRAME_SHAPE, box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0), box(0.0, 1.0, 0.0, 1.0, 15.0, 1.0), box(15.0, 1.0, 0.0, 16.0, 15.0, 1.0), box(0.0, 1.0, 15.0, 1.0, 15.0, 16.0), box(15.0, 1.0, 15.0, 16.0, 15.0, 16.0), box(0.0, 15.0, 1.0, 1.0, 16.0, 15.0), box(15.0, 15.0, 1.0, 16.0, 16.0, 15.0), box(0.0, 15.0, 0.0, 16.0, 16.0, 1.0), box(0.0, 15.0, 15.0, 16.0, 16.0, 16.0), box(12.0, 10.0, 4.0, 13.0, 13.0, 12.0), box(3.0, 1.0, 3.0, 13.0, 2.0, 13.0), box(5.0, 2.0, 5.0, 11.0, 9.0, 11.0), box(3.0, 9.0, 3.0, 13.0, 10.0, 13.0), box(3.0, 10.0, 12.0, 13.0, 13.0, 13.0), box(3.0, 10.0, 3.0, 13.0, 13.0, 4.0), box(3.0, 10.0, 4.0, 4.0, 13.0, 12.0)));
    }
}
