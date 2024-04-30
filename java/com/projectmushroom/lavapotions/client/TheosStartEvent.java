package com.projectmushroom.lavapotions.client;

import java.util.UUID;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.client.event.InputEvent.MouseInputEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID)
public class TheosStartEvent extends Event 
{
	
	Player player = null;
	boolean active = false;
	boolean clicked = false;
	boolean setclicked = true;
	
	AttributeModifier theos = new AttributeModifier(UUID.fromString("2674d824-3468-4886-976c-ccbf781cef3e"),
			"theos", 25.0D, AttributeModifier.Operation.ADDITION);
	
	AttributeModifier reach = new AttributeModifier(UUID.fromString("62de41dc-6977-4c1e-9d18-1194fb5decd8"),
			"reach", 4.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	
	@SubscribeEvent
	public void onTheosStart(PotionAddedEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.THEOS.get()))
		{
			if(entity.hasEffect(LavaEffects.THEOS.get()) == false)
			{
				System.out.println("in if");
				player = (Player) entity;
				active = true;
				player.getAbilities().mayfly = true;
				entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(theos);
				entity.getAttribute(ForgeMod.REACH_DISTANCE.get()).addTransientModifier(reach);
				entity.getAttribute(ForgeMod.ATTACK_RANGE.get()).addTransientModifier(reach);
				player.getAbilities().setFlyingSpeed(0.2f);
        	    player.onUpdateAbilities();
				
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerClick(LeftClickBlock event)
	{
		if(player != null)
		{
			if(player.hasEffect(LavaEffects.THEOS.get()))
			{
				if (!clicked && setclicked)
				{
					try
					{
						player.level.explode(player, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), 3.0F, Explosion.BlockInteraction.BREAK);
					    LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(player.level);
					    bolt.setPos(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
					    player.level.addFreshEntity(bolt);
					    
					} finally
					{
						System.out.println("This is here");
					}
					clicked = true;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onTheosClick(MouseInputEvent event)
	{
		if (active)
		{
			if (event.getButton() == 0 && event.getAction() == 0)
			{
				clicked = false;
				setclicked = false;
			}
			if (event.getButton() == 0 && event.getAction() == 1)
			{
				setclicked = true;
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityHit(LivingHurtEvent event)
	{
        if (event.getSource().getDirectEntity() instanceof Player)
		{
		    LivingEntity entity = (LivingEntity) event.getEntityLiving();
		    Player source = (Player) event.getSource().getDirectEntity();
	    	if (source.hasEffect(LavaEffects.THEOS.get()))
	    	{
	    	    LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(player.level);
				bolt.setPos(event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ());
				player.level.addFreshEntity(bolt);
				if(entity.hasEffect(LavaEffects.INVINC.get()))
				{
					entity.removeEffect(LavaEffects.INVINC.get());
				}
				if(entity.hasEffect(MobEffects.DAMAGE_RESISTANCE))
				{
					entity.removeEffect(MobEffects.DAMAGE_RESISTANCE);
				}
				entity.hurt(new DamageSource("God"), 20000);
				if(entity instanceof EnderDragon)
				{
					entity.kill();
				}
				clicked = true;
	    	}
	    }
	}
	
	@SubscribeEvent
	public void onTheosEnd(PotionRemoveEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.THEOS.get())) 
		{
			active = false;
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(theos);
			entity.getAttribute(ForgeMod.REACH_DISTANCE.get()).removeModifier(reach);
			entity.getAttribute(ForgeMod.ATTACK_RANGE.get()).removeModifier(reach);
    	    player.getAbilities().mayfly = false;
    	    player.getAbilities().flying = false;
    	    player.getAbilities().setFlyingSpeed(0.02f);
    	    player.onUpdateAbilities();
			player = null;
			
			
		}
	}
	
	@SubscribeEvent
	public void onTheosExpire(PotionExpiryEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.THEOS.get())) 
		{
			
			active = false;
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(theos);
			entity.getAttribute(ForgeMod.REACH_DISTANCE.get()).removeModifier(reach);
			entity.getAttribute(ForgeMod.ATTACK_RANGE.get()).removeModifier(reach);
			player.getAbilities().mayfly = false;
    	    player.getAbilities().flying = false;
    	    player.getAbilities().setFlyingSpeed(0.02f);
    	    player.onUpdateAbilities();
			player = null;
		}
	}
	
	@SubscribeEvent
	public void onEntityHitEvent(LivingHurtEvent event)
	{
		if (event.getEntityLiving().hasEffect(LavaEffects.THEOS.get()))
		{
			event.setCanceled(true);
		}
	}
	
}