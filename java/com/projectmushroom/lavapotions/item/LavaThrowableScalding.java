package com.projectmushroom.lavapotions.item;

import com.projectmushroom.lavapotions.entity.LavaThrownScalding;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LavaThrowableScalding extends Item {

	public LavaThrowableScalding(Properties properties) {
		super(properties);
	}

	   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		  ItemStack itemstack = player.getItemInHand(hand);
		  if (!level.isClientSide) {
		     LavaThrownScalding lavathrownscalding = new LavaThrownScalding(level, player);
		     lavathrownscalding.setItem(itemstack);
		     lavathrownscalding.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
		     level.addFreshEntity(lavathrownscalding);
		  }
		  
		  player.awardStat(Stats.ITEM_USED.get(this));
	      if (!player.getAbilities().instabuild) {
	         itemstack.shrink(1);
	      }

	      return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
	   }
	   
	   
}