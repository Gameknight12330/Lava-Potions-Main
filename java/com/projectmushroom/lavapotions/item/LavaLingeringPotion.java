package com.projectmushroom.lavapotions.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LavaLingeringPotion extends LavaThrowableLingeringPotion{
	
	public LavaLingeringPotion(Item.Properties p_42836_) {
	      super(p_42836_);
	   }

	   public InteractionResultHolder<ItemStack> use(Level p_42843_, Player p_42844_, InteractionHand p_42845_) {
	      p_42843_.playSound((Player)null, p_42844_.getX(), p_42844_.getY(), p_42844_.getZ(), SoundEvents.LINGERING_POTION_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (p_42843_.getRandom().nextFloat() * 0.4F + 0.8F));
	      return super.use(p_42843_, p_42844_, p_42845_);
	   }

}
