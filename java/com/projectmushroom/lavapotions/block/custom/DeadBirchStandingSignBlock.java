package com.projectmushroom.lavapotions.block.custom;

import com.projectmushroom.lavapotions.block.entity.custom.DeadBirchSignBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class DeadBirchStandingSignBlock extends StandingSignBlock {
    public DeadBirchStandingSignBlock(Properties p_56990_, WoodType p_56991_) {
        super(p_56990_, p_56991_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DeadBirchSignBlockEntity(pPos, pState);
    }
}
