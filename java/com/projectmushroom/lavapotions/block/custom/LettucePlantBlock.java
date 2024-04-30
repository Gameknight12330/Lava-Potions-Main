package com.projectmushroom.lavapotions.block.custom;

import com.projectmushroom.lavapotions.init.ItemInit;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class LettucePlantBlock extends CropBlock {
	
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;

	public LettucePlantBlock(Properties p_52247_) {
		super(p_52247_);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getMaxAge() {
		return 2;
	}
	
	@Override
	protected ItemLike getBaseSeedId() {
		return ItemInit.LETTUCE_SEEDS.get();
	}

}
