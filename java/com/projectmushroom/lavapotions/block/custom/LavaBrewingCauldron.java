package com.projectmushroom.lavapotions.block.custom;

import javax.annotation.Nullable;

import com.projectmushroom.lavapotions.block.entity.ModBlockEntities;
import com.projectmushroom.lavapotions.block.entity.custom.LavaBrewingStationBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class LavaBrewingCauldron extends BaseEntityBlock {
	public LavaBrewingCauldron(Properties properties) {
	    super(properties);
	}
	
//	private static final VoxelShape SHAPE = Stream
//		.of(Block.box(0.8125, 0.5, 0.1875, 0.9375, 0.9375, 0.8125), 
//			 Block.box(0.0625, 0.5, 0.1875, 0.1875, 0.9375, 0.8125), 
//			 Block.box(0.1875, 0.5, 0.8125, 0.8125, 0.9375, 0.9375), 
//			 Block.box(0.1875, 0.5, 0.0625, 0.8125, 0.9375, 0.1875), 
//			 Block.box(0.125, 0.5, 0.125, 0.1875, 0.9375, 0.1875), 
//			 Block.box(0.8125, 0.5, 0.125, 0.875, 0.9375, 0.1875), 
//			 Block.box(0.8125, 0.5, 0.8125, 0.875, 0.9375, 0.875), 
//			 Block.box(0.125, 0.5, 0.8125, 0.1875, 0.9375, 0.875), 
//			 Block.box(0.75, 0.375, 0.25, 0.875, 0.5, 0.75), 
//			 Block.box(0.125, 0.375, 0.25, 0.25, 0.5, 0.75), 
//			 Block.box(0.1875, 0.375, 0.1875, 0.8125, 0.5625, 0.8125), 
//			 Block.box(0.25, 0.375, 0.125, 0.75, 0.5, 0.25), 
//			 Block.box(0.25, 0.375, 0.75, 0.75, 0.5, 0.875), 
//			 Block.box(0.8125, 0.9375, 0.25, 0.875, 1, 0.75), 
//			 Block.box(0.125, 0.9375, 0.25, 0.1875, 1, 0.75), 
//			 Block.box(0.25, 0.9375, 0.8125, 0.75, 1, 0.875), 
//			 Block.box(0.125, 0.9375, 0.125, 0.25, 1, 0.25), 
//			 Block.box(0.75, 0.9375, 0.125, 0.875, 1, 0.25), 
//			 Block.box(0.75, 0.9375, 0.75, 0.875, 1, 0.875), 
//			 Block.box(0.125, 0.9375, 0.75, 0.25, 1, 0.875), 
//			 Block.box(0.25, 0.9375, 0.125, 0.75, 1, 0.1875), 
//			 Block.box(0, 0.125, 0.6875, 1, 0.375, 0.9375), 
//			 Block.box(0, 0.125, 0.0625, 1, 0.375, 0.3125), 
//			 Block.box(0.75, 0, 0.0625, 1, 0.125, 0.9375), 
//			 Block.box(0.75, 0.1875, 0.3125, 1, 0.25, 0.6875), 
//			 Block.box(0.75, 0, 0.9375, 1, 0.25, 1), 
//			 Block.box(0.75, 0, 0, 1, 0.25, 0.0625), 
//			 Block.box(0, 0, 0.0625, 0.25, 0.125, 0.9375), 
//			 Block.box(0, 0.1875, 0.3125, 0.25, 0.25, 0.6875), 
//			 Block.box(0, 0, 0.9375, 0.25, 0.25, 1), 
//			 Block.box(0, 0, 0, 0.25, 0.25, 0.0625), 
//			 Block.box(0.25, 0, 0, 0.75, 0.0625, 1), 
//			 Block.box(0, 0.125, 0.3125, 0.25, 0.1875, 0.6875), 
//			 Block.box(0.75, 0.125, 0.3125, 1, 0.1875, 0.6875))
//			.reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));
	
	private static final VoxelShape SHAPE =  Block.box(0, 0, 0, 16, 16, 16);
	
	@Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
	
	@Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof LavaBrewingStationBlockEntity) {
                ((LavaBrewingStationBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof LavaBrewingStationBlockEntity) {
            	System.out.println("You touched the cauldron");
                NetworkHooks.openGui(((ServerPlayer)pPlayer), (LavaBrewingStationBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LavaBrewingStationBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.LAVA_BREWING_CAULDRON_ENTITY.get(),
                LavaBrewingStationBlockEntity::tick);
    }
}
