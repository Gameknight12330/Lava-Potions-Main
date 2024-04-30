package com.projectmushroom.lavapotions.item;

import com.projectmushroom.lavapotions.init.ItemInit;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class LavaPotion extends Item {

	public LavaPotion(Properties properties) {
		super(properties);
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {	
		return UseAnim.DRINK;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		return new ItemStack(ItemInit.REINFORCED_BOTTLE.get());
	}
	
	@Override
	public SoundEvent getDrinkingSound() {
		return super.getDrinkingSound();
	}
	
	public int getUseDuration(ItemStack p_43001_) {
	    return 32;
	}
	
	@Override
	 public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		 ItemStack itemstack = super.finishUsingItem(stack, level, entity);
		 level.gameEvent(entity, GameEvent.DRINKING_FINISH, entity.eyeBlockPosition());
		 return entity instanceof Player && ((Player)entity).getAbilities().instabuild ? itemstack : new ItemStack(ItemInit.REINFORCED_BOTTLE.get()); 
	   }
}
