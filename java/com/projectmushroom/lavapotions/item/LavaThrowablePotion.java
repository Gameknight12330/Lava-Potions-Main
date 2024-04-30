package com.projectmushroom.lavapotions.item;

import java.util.Arrays;
import java.util.List;

import com.projectmushroom.lavapotions.entity.LavaThrownPotion;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LavaThrowablePotion extends Item {
	
	private List<MobEffect> effects;
	private List<Integer> durs;
	private List<Integer> amps;
	private int color;

	public LavaThrowablePotion(Properties properties, List<MobEffect> effects, List<Integer> durs, List<Integer> amps, int color) {
		super(properties);
		this.effects = effects;
		this.durs = durs;
		this.amps = amps;
		this.color = color;
	}

	   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		  ItemStack itemstack = player.getItemInHand(hand);
		  if (!level.isClientSide) {
		     LavaThrownPotion lavathrownpotion = new LavaThrownPotion(level, player, effects, durs, amps, color);
		     lavathrownpotion.setItem(itemstack);
		     lavathrownpotion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
		     level.addFreshEntity(lavathrownpotion);
		  }
		  
		  player.awardStat(Stats.ITEM_USED.get(this));
	      if (!player.getAbilities().instabuild) {
	         itemstack.shrink(1);
	      }

	      return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
	   }
	   
	   
}
