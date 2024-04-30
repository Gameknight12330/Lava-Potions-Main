package com.projectmushroom.lavapotions.client;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID)
public class StridingStartEvent extends Event
{
	@SubscribeEvent
	public void onWalkOnLava(PlayerTickEvent event)
	{
		Player player = event.player;
		if (player.hasEffect(LavaEffects.STRIDING.get()))
		{
			if (player.isOnFire())
			{
				player.clearFire();
			}
			if (player.isInLava()) {
		        CollisionContext collisioncontext = CollisionContext.of(player);
		        if (collisioncontext.isAbove(LiquidBlock.STABLE_SHAPE, player.blockPosition(), true) && !player.level.getFluidState(player.blockPosition().above()).is(FluidTags.LAVA)) {
		           player.setOnGround(true);
		        } else {
		           player.setDeltaMovement(player.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
		        }
		    }
		}
	}
	
	@SubscribeEvent
	public void onEntityBurnEvent(LivingAttackEvent event)
	{
		CollisionContext collisioncontext = CollisionContext.of(event.getEntityLiving());
		if (event.getEntityLiving().hasEffect(LavaEffects.STRIDING.get()) && (event.getSource().equals(DamageSource.LAVA) || 
				event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.ON_FIRE) || 
				event.getSource().equals(DamageSource.HOT_FLOOR)) || (event.getSource().equals(DamageSource.FALL) && 
				collisioncontext.isAbove(LiquidBlock.STABLE_SHAPE, event.getEntityLiving().blockPosition(), true)))
		{
			event.setCanceled(true);
		}
	}
}

