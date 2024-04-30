package com.projectmushroom.lavapotions.client;

import java.util.Random;
import java.util.UUID;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID)
public class VolcanicStartEvent extends Event 
{
	

	AttributeModifier volcanic = new AttributeModifier(UUID.fromString("c04cfa77-ac48-48ad-9933-c4b3cd72f606"),
			"volcanic", 5.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	
	Random rand = new Random();
	
	@SubscribeEvent
	public void onVolcanicStart(PotionAddedEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.VOLCANIC.get()))
		{
			if(entity.hasEffect(LavaEffects.VOLCANIC.get())== false)
			{
				entity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(volcanic);
				
			}
		}
	}
	
	@SubscribeEvent
	public void onVolcanicEnd(PotionRemoveEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.VOLCANIC.get())) 
		{
			entity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(volcanic);
			
		}
	}
	
	@SubscribeEvent
	public void onVolcanicExpire(PotionExpiryEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.VOLCANIC.get())) 
		{
			entity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(volcanic);
		}
	}
	
	  @SubscribeEvent
	    public void onEntityHit(LivingHurtEvent event)
	   {
		    if (event.getSource().getDirectEntity() instanceof LivingEntity)
		    {
		    	LivingEntity entity = (LivingEntity) event.getSource().getDirectEntity();
	    	    if (entity.hasEffect(LavaEffects.VOLCANIC.get()))
	    	    {
	    			if (rand.nextFloat() > 0.95) 
	    			{
	    				event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 1));
	    			}
	    			
	    			if (rand.nextFloat() > 0.9) 
	    			{
	    				event.getEntityLiving().setSecondsOnFire(5);
	    			}
	    		}
	    	}
	    }
	
}