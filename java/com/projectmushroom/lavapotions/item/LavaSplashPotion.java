package com.projectmushroom.lavapotions.item;

import java.util.List;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LavaSplashPotion extends LavaThrowablePotion {

	public LavaSplashPotion(Properties properties, List<MobEffect> effects, List<Integer> durs, List<Integer> amps, int color) {
		super(properties, effects, durs, amps, color);
		// TODO Auto-generated constructor stub
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
	      level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
	      return super.use(level, player, hand);
	   }
	
	
	
}
