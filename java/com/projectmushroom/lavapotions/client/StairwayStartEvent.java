package com.projectmushroom.lavapotions.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID)
public class StairwayStartEvent extends Event
{
	
	AttributeModifier stairway = new AttributeModifier(UUID.fromString("792eea79-4657-42ea-9f1d-c27329f62df6"),
			"stairway", 1.5D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	
	Player player = null;
	
	int healcount = 0;
	
	boolean landed = false;
	
	List<BlockPos> list = new ArrayList<BlockPos> ();
	
//	AttributeModifier floaty = new AttributeModifier(UUID.fromString("096ca9f8-3dbc-4223-8fb3-fff4dec5921d"),
//			"floaty", -0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
	
	@SubscribeEvent
	public void onStairwayStart(PotionAddedEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.STAIRWAY.get()))
		{
			if(entity.hasEffect(LavaEffects.STAIRWAY.get()) == false)
			{
				entity.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(stairway);
//				entity.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).addTransientModifier(floaty);
				if (entity instanceof Player)
				{
					player = (Player) entity;
				}
			}
		}
	}
	
	
	@SubscribeEvent
	public void onStairwayJump(LivingJumpEvent event)
	{
		LivingEntity entity = event.getEntityLiving();
		if (entity.hasEffect(LavaEffects.STAIRWAY.get()))
		{
			entity.setDeltaMovement(entity.getDeltaMovement().add(entity.getDeltaMovement()));
		}
	}
	
	
	@SubscribeEvent
	public void onStairwayAirJump(KeyInputEvent event)
	{
		if (player != null)
		{
			if (player.hasEffect(LavaEffects.STAIRWAY.get()))
			{
				Level level = player.getLevel();
				
				if (event.getKey() == 32 && event.getAction() == 1)
				{
					System.out.println("The player jumped");
					for (int x = -2; x <= 2; x++)
					{
						for (int z = -2; z <= 2; z++)
						{
							if (level.getBlockState(new BlockPos(player.getBlockX() + x, player.getBlockY() - 2, player.getBlockZ() + z)) == Blocks.AIR.defaultBlockState())
							{
								int yMod = -2;
								if (player.getDeltaMovement().y() > 0)
								{
									yMod = -1;
								}
								list.add(new BlockPos(player.getBlockX() + x, player.getBlockY() + yMod, player.getBlockZ() + z));
								level.setBlock(new BlockPos(player.getBlockX() + x, player.getBlockY() + yMod, player.getBlockZ() + z), Blocks.WHITE_WOOL.defaultBlockState(), Block.UPDATE_ALL);
							}
						}
					}
				}
			}
		}
	}
	
	
	@SubscribeEvent
	public void onStairwayTick(TickEvent event)
	{
		if (event.phase == Phase.END)
		{
		    if (player != null)
		    {
			    if (player.hasEffect(LavaEffects.STAIRWAY.get()))
		    	{
			    	if (healcount < 10)
			    	{
			    		healcount += 1;
			    	}
			    	else
			    	{
			    		player.heal(0.1F);
			    		healcount = 0;
			    	}
				    if (player.isOnGround())
				    {
				        landed = true;
				    }
				    {
					    if (!player.isOnGround() && landed)
				    	{
						    System.out.println("Jumped from cloud");
					        Level level = player.getLevel();
						    for (int r = 0; r < list.size(); r++)
						    {
							    level.setBlock(list.get(r), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
						    }
						    for (int r = 0; r < list.size(); r++)
						    {
							    list.get(0).equals(null);
						    }
						    landed = false;
					    }
				    }
				}
			}
		}
	}
	
	
	@SubscribeEvent
	public void onStairwayFall(LivingHurtEvent event)
	{
		LivingEntity entity = event.getEntityLiving();
		if (entity.hasEffect(LavaEffects.STAIRWAY.get()) && !entity.hasEffect(LavaEffects.HIGHWAY.get()))
		{
			if (event.getSource().equals(DamageSource.FALL))
			{
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onStairwayEnd(PotionExpiryEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.STAIRWAY.get()))
		{
			entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(stairway);
//			entity.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).removeModifier(floaty);
			player = null;
		}
	}
	
	@SubscribeEvent
	public void onStairwayRemove(PotionRemoveEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.STAIRWAY.get()))
		{
			entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(stairway);
//			entity.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).removeModifier(floaty);
			player = null;
		}
	}
}
