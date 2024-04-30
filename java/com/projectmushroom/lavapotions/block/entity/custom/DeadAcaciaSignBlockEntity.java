package com.projectmushroom.lavapotions.block.entity.custom;

import com.projectmushroom.lavapotions.block.entity.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DeadAcaciaSignBlockEntity extends SignBlockEntity {
    public DeadAcaciaSignBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(pWorldPosition, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.DEAD_ACACIA_SIGN_BLOCK_ENTITY.get();
    }
}
