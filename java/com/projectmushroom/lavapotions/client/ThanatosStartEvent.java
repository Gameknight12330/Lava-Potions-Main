package com.projectmushroom.lavapotions.client;

import java.util.Random;
import java.util.UUID;

import com.projectmushroom.lavapotions.effect.LavaEffects;
import com.projectmushroom.lavapotions.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ThanatosStartEvent
{
	boolean dead = false;
	
	Random rnd = new Random();
	
	AttributeModifier thanatos = new AttributeModifier(UUID.fromString("b48ce840-2916-4c28-acab-f6693bf3cf58"),
			"thanatos", 5.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	AttributeModifier thanatos_speed = new AttributeModifier(UUID.fromString("04ceb1a3-516f-4489-940f-24c8da10462c"),
			"thanatos_speed", -0.03D, AttributeModifier.Operation.ADDITION);
	AttributeModifier thanatos_attack = new AttributeModifier(UUID.fromString("f2800bf9-d692-4068-b672-f4ecba0a1452"),
			"thanatos_attack", 3.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	int timer = 0;
	LivingEntity entity = null;
	
	@SubscribeEvent
	public void onThanatosStart (PotionAddedEvent event)
	{
		
		if(event.getPotionEffect().getEffect().equals(LavaEffects.THANATOS.get()))
		{
			entity = event.getEntityLiving();
			if(!event.getEntityLiving().getAttribute(Attributes.MAX_HEALTH).hasModifier(thanatos))
			{
				event.getEntityLiving().getAttribute(Attributes.MAX_HEALTH).addTransientModifier(thanatos);
				event.getEntityLiving().heal(100);
			}
			if(!event.getEntityLiving().getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(thanatos_speed))
			{
				event.getEntityLiving().getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(thanatos_speed);
			}
			if(!event.getEntityLiving().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(thanatos_attack))
			{
				event.getEntityLiving().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(thanatos_attack);
			}
			if(!event.getEntityLiving().getAttribute(Attributes.ATTACK_KNOCKBACK).hasModifier(thanatos_attack))
			{
				event.getEntityLiving().getAttribute(Attributes.ATTACK_KNOCKBACK).addTransientModifier(thanatos_attack);
			}
			if(event.getEntityLiving() instanceof Player)
			{
				Player player = ((Player)event.getEntityLiving());
				dead = false;
			}
		}
	}	
	
	@SubscribeEvent
	public void thanatosTick(TickEvent event)
	{
		if(entity != null && event.phase.equals(TickEvent.Phase.END))
		{
			timer += 1;
			if(!entity.hasEffect(LavaEffects.THANATOS.get()))
			{
				dead  = true;
			}
			if(timer >= 1200)
			{
				thanatosKill(entity);
				timer = 0;
			}
		}
	}
	
	public void thanatosKill(LivingEntity entity)
	{
		if(entity.hasEffect(LavaEffects.THANATOS.get()) && dead == false)
		{
			int limit = 0 ;
			if(!entity.getAttribute(Attributes.MAX_HEALTH).hasModifier(thanatos))
			{
				entity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(thanatos);
				entity.heal(100);
			}
			if(entity.getLevel() instanceof ServerLevel)
			{
				ServerLevel level = (ServerLevel) entity.getLevel();
				for(int i = -20; i <= 20; i+= 1)
				{
					
					for(int x = -20; x <= 5; x+= 1)
					{
						for(int z = -20; z <= 20; z+= 1)
						{
							Block block = level.getBlockState(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z)).getBlock();
							
							if(block instanceof GrassBlock)
							{
								if (rnd.nextInt(2) == 0)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.DIRT.defaultBlockState(), Block.UPDATE_ALL);
							        System.out.println("there is a grass block at " + i + x + z);
								} else {
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.ROOTED_DIRT.defaultBlockState(), Block.UPDATE_ALL);
							        System.out.println("there is a grass block at " + i + x + z);
								}
							}
							if(block instanceof LeavesBlock || block instanceof TallGrassBlock)
							{
								level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.AIR.defaultBlockState(), 
								Block.UPDATE_ALL);
							    System.out.println("there is a leaf or tall grass block at " + i + x + z);
							}
							if(block instanceof FlowerBlock || block instanceof TallFlowerBlock )
							{
								level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.WITHER_ROSE.defaultBlockState(), Block.UPDATE_ALL);
						        System.out.println("there is a flower at " + i + x + z);
							}
							if(block instanceof RotatedPillarBlock )
							{
								RotatedPillarBlock wood = ((RotatedPillarBlock)block);
								if(wood == Blocks.OAK_LOG)
								{
								    level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), BlockInit.DEAD_OAK_LOG.get().defaultBlockState(),
								    Block.UPDATE_ALL);
							        System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.BIRCH_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.STRIPPED_BIRCH_LOG.defaultBlockState(),
								    Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.ACACIA_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.STRIPPED_ACACIA_LOG.defaultBlockState(),
								    Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.DARK_OAK_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.STRIPPED_DARK_OAK_LOG.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.JUNGLE_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.STRIPPED_JUNGLE_LOG.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.SPRUCE_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.STRIPPED_SPRUCE_LOG.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
							}
							
							
						}
					}
				}
				
				for(int i = 0; i < 100; i+= 5)
				{
				
					
					for (Entity entities : level.getAllEntities())
					{
						if(!(entities == entity) && entities instanceof LivingEntity)
						{
							LivingEntity target = ((LivingEntity)entities);
							
							if((!(target.getBlockX() > (entity.getBlockX() + i)) && !(target.getBlockX() < (entity.getBlockX() - i)))
									&& (!(target.getBlockY() > (entity.getBlockY() + i)) && !(target.getBlockY() < (entity.getBlockY() - i)))
									&& (!(target.getBlockZ() > (entity.getBlockZ() + i)) && !(target.getBlockZ() < (entity.getBlockZ() - i)))
									&& limit <= 200)
							{
								if(target.hasEffect(LavaEffects.INVINC.get()))
								{
									target.removeEffect(LavaEffects.INVINC.get());
								}
								if(target.hasEffect(MobEffects.DAMAGE_RESISTANCE))
								{
									target.removeEffect(MobEffects.DAMAGE_RESISTANCE);
								}
								if(target.hasEffect(LavaEffects.VIOS.get()) || target.hasEffect(LavaEffects.SOLDIER.get()))
								{
									target.hurt(new DamageSource("Devil"), 10);
									limit += 1;
								}
								else
								{
									target.hurt(new DamageSource("Devil"), 20000);
								    System.out.println(i + " Killed " + target);
								    limit += 1;
								}
								
								
							}
						}
					}
				}
				System.out.println("total killed " + limit);
			}
		}
	}
	@SubscribeEvent
	public void onThanatosEnd (PotionRemoveEvent event)
	{
		
		if(event.getPotionEffect().getEffect().equals(LavaEffects.THANATOS.get()))
		{
			dead = true;
			if(event.getEntityLiving().getAttribute(Attributes.MAX_HEALTH).hasModifier(thanatos))
			{
				event.getEntityLiving().getAttribute(Attributes.MAX_HEALTH).removeModifier(thanatos);
			}
			if(event.getEntityLiving().getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(thanatos_speed))
			{
				event.getEntityLiving().getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(thanatos_speed);
			}
			if(event.getEntityLiving().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(thanatos_attack))
			{
				event.getEntityLiving().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(thanatos_attack);
			}
			if(event.getEntityLiving().getAttribute(Attributes.ATTACK_KNOCKBACK).hasModifier(thanatos_attack))
			{
				event.getEntityLiving().getAttribute(Attributes.ATTACK_KNOCKBACK).removeModifier(thanatos_attack);
			}
			timer = 0;
		    entity = null;
		}
	}
	
	@SubscribeEvent
	public void onEntityHitEvent(LivingHurtEvent event)
	{
		if (event.getEntityLiving().hasEffect(LavaEffects.THANATOS.get()))
		{
			event.setAmount((float) (event.getAmount() * 0.5));
		}
	}
}

