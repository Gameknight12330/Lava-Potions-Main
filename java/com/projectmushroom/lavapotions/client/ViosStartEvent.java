package com.projectmushroom.lavapotions.client;

import java.util.Random;
import java.util.UUID;

import com.projectmushroom.lavapotions.effect.LavaEffects;
import com.projectmushroom.lavapotions.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.RootedDirtBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.WitherRoseBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ViosStartEvent
{

	boolean dead = false;
	
	Random rnd = new Random();
	
	AttributeModifier vios = new AttributeModifier(UUID.fromString("27ceda06-45b2-42b3-b5af-2589e140e7da"),
			"vios", 5.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	AttributeModifier vios_speed = new AttributeModifier(UUID.fromString("912f3b0a-2b54-4140-a458-ca2d73af54bc"),
			"vios_speed", 0.03D, AttributeModifier.Operation.ADDITION);
	int timer = 0;
	int timer2 = 0;
	LivingEntity entity = null;
	
	@SubscribeEvent
	public void onViosStart (PotionAddedEvent event)
	{
		
		if(event.getPotionEffect().getEffect().equals(LavaEffects.VIOS.get()))
		{
			entity = event.getEntityLiving();
			if(!event.getEntityLiving().getAttribute(Attributes.MAX_HEALTH).hasModifier(vios))
			{
				event.getEntityLiving().getAttribute(Attributes.MAX_HEALTH).addTransientModifier(vios);
				event.getEntityLiving().heal(100);
			}
			if(!event.getEntityLiving().getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(vios_speed))
			{
				event.getEntityLiving().getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(vios_speed);
			}
			if(event.getEntityLiving() instanceof Player)
			{
				dead = false;
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerClick(LeftClickBlock event)
	{
		Player player = event.getPlayer();
		if(player != null)
		{
			if(player.hasEffect(LavaEffects.VIOS.get()))
			{
				Block block = player.getLevel().getBlockState(event.getPos()).getBlock();
				block.playerDestroy(player.getLevel(), player, event.getPos(), block.defaultBlockState(), null, event.getItemStack());
				player.getLevel().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
			}
		}
	}
	
	@SubscribeEvent
	public void viosTick(TickEvent event)
	{
		if(entity != null && event.phase.equals(TickEvent.Phase.END))
		{
			timer += 1;
			timer2 += 1;
			if(!entity.hasEffect(LavaEffects.VIOS.get()))
			{
				dead = true;
			}
			if(timer >= 1200)
			{
				viosHeal(entity);
				timer = 0;
			}
			if(timer2 >= 100)
			{
				viosBone(entity);
				timer2 = 0;
			}
		}
	}
	
	public void viosBone(LivingEntity entity)
	{
		if(entity.hasEffect(LavaEffects.VIOS.get()) && dead == false)
		{
			int limit = 0 ;
			if(!entity.getAttribute(Attributes.MAX_HEALTH).hasModifier(vios))
			{
				entity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(vios);
				entity.heal(100);
			}
			if(entity.getLevel() instanceof ServerLevel)
			{
				ServerLevel level = (ServerLevel) entity.getLevel();
				for(int x = -5; x <= 5; x += 1)
				{
					
					for(int y = -20; y <= 5; y += 1)
					{
						for(int z = -5; z <= 5; z += 1)
						{
							Block block = level.getBlockState(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z)).getBlock();
							boolean grass = false;
							if(block instanceof GrassBlock && !(block instanceof TallGrassBlock))
							{
								if(rnd.nextInt(50) == 0)
								{
									BonemealableBlock growblock = (BonemealableBlock) block;
									growblock.performBonemeal(level, rnd, (new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z)),
									level.getBlockState(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z)));
								}
								grass = true;
							}
							if(block instanceof BonemealableBlock && !grass && !(block instanceof TallGrassBlock))
							{
								BonemealableBlock growblock = (BonemealableBlock) block;
								growblock.performBonemeal(level, rnd, (new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z)),
								level.getBlockState(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z)));
							}
						}
					}
				}
			}
		}
	}
	
	public void viosHeal(LivingEntity entity)
	{
		if(entity.hasEffect(LavaEffects.VIOS.get()) && dead == false)
		{
			int limit = 0 ;
			if(!entity.getAttribute(Attributes.MAX_HEALTH).hasModifier(vios))
			{
				entity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(vios);
				entity.heal(100);
			}
			if(entity.getLevel() instanceof ServerLevel)
			{
				ServerLevel level = (ServerLevel) entity.getLevel();
				for(int x = -20; x <= 20; x += 1)
				{
					
					for(int y = -20; y <= 5; y+= 1)
					{
						for(int z = -20; z <= 20; z+= 1)
						{
							Block block = level.getBlockState(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z)).getBlock();
							
							if(block == Blocks.DIRT || block instanceof RootedDirtBlock)
							{
								level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.GRASS_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
							}
							if(block instanceof WitherRoseBlock )
							{
								level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
						        System.out.println("there is a wither rose at " + x  +y + z);
							}
							if(block instanceof RotatedPillarBlock )
							{
								RotatedPillarBlock wood = ((RotatedPillarBlock)block);
								if(wood == BlockInit.DEAD_OAK_LOG.get())
								{
								    level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.AIR.defaultBlockState(),
								    Block.UPDATE_ALL);
							        System.out.println("there is a peice of dead wood at " + x + y + z);
								}
								if(wood == Blocks.STRIPPED_BIRCH_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.BIRCH_LOG.defaultBlockState(),
								    Block.UPDATE_ALL);
								    System.out.println("there is a peice of dead wood at " + x + y + z);
								}
								if(wood == Blocks.STRIPPED_ACACIA_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.ACACIA_LOG.defaultBlockState(),
								    Block.UPDATE_ALL);
								    System.out.println("there is a peice of dead wood at " + x + y + z);
								}
								if(wood == Blocks.STRIPPED_DARK_OAK_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.STRIPPED_DARK_OAK_LOG.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + x +y+ z);
								}
								if(wood == Blocks.STRIPPED_JUNGLE_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.JUNGLE_LOG.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + x + y + z);
								}
								if(wood == Blocks.STRIPPED_SPRUCE_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.SPRUCE_LOG.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + x + y + z);
								}
							}
							BlockPos blockpos = new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z);
							if(block == Blocks.GRASS_BLOCK && level.getBlockState(blockpos).getBlock() == Blocks.AIR)
							{
							    if(rnd.nextInt(25) == 0)
							    {
							    	System.out.println("random thing happened ");
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.FOREST).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.MEADOW).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.FLOWER_FOREST).get().unwrap()))
							    	{

							    		
							    		if(rnd.nextInt(4) == 0)
							    		{
							    			level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.BIRCH_SAPLING.defaultBlockState(),
							    			Block.UPDATE_ALL);
							    		}
							    		else
							    		{
							    			level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.OAK_SAPLING.defaultBlockState(),
										    Block.UPDATE_ALL);
							    		}
							    	}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.WINDSWEPT_HILLS).get().unwrap())
									|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.WINDSWEPT_FOREST).get().unwrap())
									|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.WINDSWEPT_GRAVELLY_HILLS).get().unwrap()))
									{
									    System.out.println("you're in a forest ");
									    
									    if(rnd.nextInt(2) == 0)
									    {
									    	level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.SPRUCE_SAPLING.defaultBlockState(),
									    	Block.UPDATE_ALL);
									    }
									    else
									    {
									    	level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.OAK_SAPLING.defaultBlockState(),
											Block.UPDATE_ALL);
									    }
									}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.GROVE).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.TAIGA).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.OLD_GROWTH_PINE_TAIGA).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.OLD_GROWTH_SPRUCE_TAIGA).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SNOWY_TAIGA).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SNOWY_PLAINS).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.FROZEN_RIVER).get().unwrap()))
							    	{
							    		
							    		level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.SPRUCE_SAPLING.defaultBlockState(), Block.UPDATE_ALL);

							    	}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.DARK_FOREST).get().unwrap()))
							    	{
							    		
							    		level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.DARK_OAK_SAPLING.defaultBlockState(), Block.UPDATE_ALL);

							    	}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.BIRCH_FOREST).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.OLD_GROWTH_BIRCH_FOREST).get().unwrap()))
							    	{
							    		
							    		level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.BIRCH_SAPLING.defaultBlockState(), Block.UPDATE_ALL);

							    	}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.JUNGLE).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SPARSE_JUNGLE).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.BAMBOO_JUNGLE).get().unwrap()))
							    	{
							    		
							    		level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.JUNGLE_SAPLING.defaultBlockState(), Block.UPDATE_ALL);

							    	}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SWAMP).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.PLAINS).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SUNFLOWER_PLAINS).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.RIVER).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.WOODED_BADLANDS).get().unwrap()))
							    	{
							    		
							    		level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.OAK_SAPLING.defaultBlockState(), Block.UPDATE_ALL);
							    	}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SAVANNA).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SAVANNA_PLATEAU).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.WINDSWEPT_SAVANNA).get().unwrap()))
							    	{
							    		
							    		level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.ACACIA_SAPLING.defaultBlockState(), Block.UPDATE_ALL);
							    	}
							    }
							}
						}
					}
				}
			}
		}
	}
	
	
	@SubscribeEvent
	public void onViosAttack (LivingAttackEvent event)
	{
		if(event.getSource().getDirectEntity() instanceof LivingEntity)
		{
			LivingEntity vios = (LivingEntity) event.getSource().getDirectEntity();
			if(vios.hasEffect(LavaEffects.VIOS.get()))
			{
				if(!event.getEntityLiving().hasEffect(LavaEffects.SOLDIER.get()))
				{
					event.getEntityLiving().addEffect(new MobEffectInstance(LavaEffects.SOLDIER.get(), 1200, 1));
				    System.out.println("Applied Soldier ");
				}
				event.setCanceled(true);
			}
		}
	}
	
	 @SubscribeEvent
	    public void onEntityUpdate(LivingUpdateEvent event)
	    {
	    	LivingEntity entity = event.getEntityLiving();
	    	if (entity instanceof Mob)
	    	{
	    		Mob enemy = (Mob) entity;
	    		if (enemy.getTarget() instanceof  Player && enemy.getTarget().hasEffect(LavaEffects.VIOS.get()))
	    		{
	    			enemy.setTarget(null);
	    		}
	    	}
	    }
	 
	 @SubscribeEvent
		public void onEntityTarget(LivingChangeTargetEvent event)
		{
			System.out.println("This was triggered");
			LivingEntity target = event.getNewTarget();
			if (target instanceof Player && target.hasEffect(LavaEffects.VIOS.get()))
			{
				event.setCanceled(true);
			}
		}
	
	@SubscribeEvent
	public void onViosEnd (PotionRemoveEvent event)
	{
		
		if(event.getPotionEffect().getEffect().equals(LavaEffects.VIOS.get()))
		{
			dead = true;
			if(event.getEntityLiving().getAttribute(Attributes.MAX_HEALTH).hasModifier(vios))
			{
				event.getEntityLiving().getAttribute(Attributes.MAX_HEALTH).removeModifier(vios);
			}
			if(event.getEntityLiving().getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(vios_speed))
			{
				event.getEntityLiving().getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(vios_speed);
			}
		}
	}
}

