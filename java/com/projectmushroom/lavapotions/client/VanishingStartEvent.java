package com.projectmushroom.lavapotions.client;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID)
public class VanishingStartEvent extends Event
{
	
    @SubscribeEvent
    public void pre(RenderPlayerEvent.Pre event)
	{
	    if (event.getPlayer().hasEffect(LavaEffects.VANISHING.get()))
	    {
		    event.setCanceled(true);
         }
	}
    
    @SubscribeEvent
    public void onEntityUpdate(LivingUpdateEvent event)
    {
    	LivingEntity entity = event.getEntityLiving();
    	if (entity instanceof Mob)
    	{
    		Mob enemy = (Mob) entity;
    		if (enemy.getTarget() instanceof  Player && enemy.getTarget().hasEffect(LavaEffects.VANISHING.get()))
    		{
    			enemy.setTarget(null);
    		}
    	}
    }
	
	@SubscribeEvent
    public void onEntityHit(LivingHurtEvent event)
     {
    	if (event.getEntityLiving().hasEffect(LavaEffects.VANISHING.get()))
    	{
    		if (event.getSource().getDirectEntity() instanceof LivingEntity)
    		{
              event.getSource().getDirectEntity().hurt(DamageSource.GENERIC, (float) (event.getAmount() * 0.2));
            }
        }
    }
	
	@SubscribeEvent
	public void onEntityTarget(LivingChangeTargetEvent event)
	{
		System.out.println("This was triggered");
		LivingEntity target = event.getNewTarget();
		if (target instanceof Player && target.hasEffect(LavaEffects.VANISHING.get()))
		{
			event.setCanceled(true);
		}
	}
}