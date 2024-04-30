package com.projectmushroom.lavapotions.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class CheeseBucket extends Item implements DispensibleContainerItem 
{

	public CheeseBucket(Properties pProperties) {
		super(pProperties);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean emptyContents(Player pPlayer, Level pLevel, BlockPos pPos, BlockHitResult pResult) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean hasCraftingRemainingItem() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		// TODO Auto-generated method stub
		return new ItemStack(Items.BUCKET);
	}
}