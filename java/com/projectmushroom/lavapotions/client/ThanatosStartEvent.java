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
import net.minecraft.world.level.block.BambooBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.MelonBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.PumpkinBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
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
							BlockPos pos = (new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z));
							
							if(block instanceof GrassBlock)
							{
								if (rnd.nextInt(2) == 0)
								{
									level.setBlock(pos, Blocks.DIRT.defaultBlockState(), Block.UPDATE_ALL);
							        System.out.println("there is a grass block at " + i + x + z);
								} else {
									level.setBlock(pos, Blocks.ROOTED_DIRT.defaultBlockState(), Block.UPDATE_ALL);
							        System.out.println("there is a grass block at " + i + x + z);
								}
							}
							if(block instanceof LeavesBlock || block instanceof TallGrassBlock)
							{
								level.setBlock(pos, Blocks.AIR.defaultBlockState(), 
								Block.UPDATE_ALL);
							    System.out.println("there is a leaf or tall grass block at " + i + x + z);
							}
							if(block instanceof FlowerBlock || block instanceof TallFlowerBlock )
							{
								level.setBlock(pos, Blocks.WITHER_ROSE.defaultBlockState(), Block.UPDATE_ALL);
						        System.out.println("there is a flower at " + i + x + z);
							}
							if(block instanceof MelonBlock)
							{
								level.setBlock(pos, BlockInit.DEAD_MELON.get().defaultBlockState(), 
								Block.UPDATE_ALL);
						        System.out.println("there is a melon at " + i + x + z);
							}
							if(block instanceof PumpkinBlock)
							{
								level.setBlock(pos, BlockInit.DEAD_PUMPKIN.get().defaultBlockState(), 
								Block.UPDATE_ALL);
						        System.out.println("there is a melon at " + i + x + z);
							}
							if(block instanceof BambooBlock)
							{
								level.setBlock(pos, BlockInit.DEAD_BAMBOO.get().defaultBlockState(), 
								Block.UPDATE_ALL);
						        System.out.println("there is a melon at " + i + x + z);
							}
							if(block instanceof SugarCaneBlock)
							{
								level.setBlock(pos, BlockInit.DEAD_SUGAR_CANE.get().defaultBlockState(), 
								Block.UPDATE_ALL);
						        System.out.println("there is a melon at " + i + x + z);
							}
							if(block instanceof CactusBlock)
							{
								level.setBlock(pos, BlockInit.DEAD_CACTUS.get().defaultBlockState(), 
								Block.UPDATE_ALL);
						        System.out.println("there is a melon at " + i + x + z);
							}
							if(block instanceof VineBlock)
							{
								level.setBlock(pos, BlockInit.DEAD_VINES.get().defaultBlockState(), 
								Block.UPDATE_ALL);
						        System.out.println("there is a melon at " + i + x + z);
							}
							if(block instanceof MushroomBlock)
							{
								level.setBlock(pos, BlockInit.DEAD_RED_MUSHROOM.get().defaultBlockState(), 
								Block.UPDATE_ALL);
						        System.out.println("there is a melon at " + i + x + z);
							}
							
							if(block instanceof MushroomBlock)
							{
								level.setBlock(pos, BlockInit.DEAD_BROWN_MUSHROOM.get().defaultBlockState(), 
								Block.UPDATE_ALL);
						        System.out.println("there is a melon at " + i + x + z);
							}
							if(block == Blocks.SPRUCE_PLANKS)
							{
								level.setBlock(pos, BlockInit.DEAD_SPRUCE_PLANKS.get().defaultBlockState(),
								Block.UPDATE_ALL);
							    System.out.println("there is a rotted plank at " + i + x + z);
							}
							if(block == Blocks.JUNGLE_PLANKS)
							{
								level.setBlock(pos, BlockInit.DEAD_JUNGLE_PLANKS.get().defaultBlockState(),
								Block.UPDATE_ALL);
							    System.out.println("there is a rotted plank at " + i + x + z);
							}
							if(block == Blocks.DARK_OAK_PLANKS)
							{
								level.setBlock(pos, BlockInit.DEAD_DARK_OAK_PLANKS.get().defaultBlockState(),
								Block.UPDATE_ALL);
							    System.out.println("there is a rotted plank at " + i + x + z);
							}
							if(block == Blocks.ACACIA_PLANKS)
							{
								level.setBlock(pos, BlockInit.DEAD_ACACIA_PLANKS.get().defaultBlockState(),
							    Block.UPDATE_ALL);
							    System.out.println("there is a rotted plank at " + i + x + z);
							}
							if(block == Blocks.BIRCH_PLANKS)
							{
								level.setBlock(pos, BlockInit.DEAD_BIRCH_PLANKS.get().defaultBlockState(),
							    Block.UPDATE_ALL);
							    System.out.println("there is a rotted plank at " + i + x + z);
							}
							if(block == Blocks.OAK_PLANKS)
							{
							    level.setBlock(pos, BlockInit.DEAD_OAK_PLANKS.get().defaultBlockState(),
							    Block.UPDATE_ALL);
						        System.out.println("there is a rotted plank at " + i + x + z);
							}
							if(block instanceof ButtonBlock )
							{
								ButtonBlock button = (ButtonBlock) block;
								if(button == Blocks.OAK_BUTTON)
								{
									ButtonBlock addblock = (ButtonBlock) (BlockInit.DEAD_OAK_BUTTON.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(button == Blocks.BIRCH_BUTTON)
								{
									ButtonBlock addblock = (ButtonBlock) (BlockInit.DEAD_BIRCH_BUTTON.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(button == Blocks.ACACIA_BUTTON)
								{
									ButtonBlock addblock = (ButtonBlock) (BlockInit.DEAD_ACACIA_BUTTON.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(button == Blocks.JUNGLE_BUTTON)
								{
									ButtonBlock addblock = (ButtonBlock) (BlockInit.DEAD_JUNGLE_BUTTON.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(button == Blocks.SPRUCE_BUTTON)
								{
									ButtonBlock addblock = (ButtonBlock) (BlockInit.DEAD_SPRUCE_BUTTON.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(button == Blocks.DARK_OAK_BUTTON)
								{
									ButtonBlock addblock = (ButtonBlock) (BlockInit.DEAD_DARK_OAK_BUTTON.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
							}
							
							if(block instanceof DoorBlock )
							{
								DoorBlock door = (DoorBlock) block;
								if(door == Blocks.OAK_DOOR)
								{
									DoorBlock addblock = (DoorBlock) (BlockInit.DEAD_OAK_DOOR.get().defaultBlockState()).getBlock();
								    if (level.getBlockState((pos)).getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER)
								    {
								    	BlockState state = addblock.withPropertiesOf(level.getBlockState(pos.below()));
								    	level.removeBlock((pos.below()), false);
									    level.setBlock(pos.below(), state,
									    Block.UPDATE_ALL);
									    state.getBlock().setPlacedBy(level, pos.below(), state, entity, null);
								    }
								}
								if(door == Blocks.BIRCH_DOOR)
								{
									DoorBlock addblock = (DoorBlock) (BlockInit.DEAD_BIRCH_DOOR.get().defaultBlockState()).getBlock();
								    if (level.getBlockState((pos)).getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER)
								    {
								    	BlockState state = addblock.withPropertiesOf(level.getBlockState(pos.below()));
								    	level.removeBlock((pos.below()), false);
									    level.setBlock(pos.below(), state,
									    Block.UPDATE_ALL);
									    state.getBlock().setPlacedBy(level, pos.below(), state, entity, null);
								    }
								}
								if(door == Blocks.ACACIA_DOOR)
								{
									DoorBlock addblock = (DoorBlock) (BlockInit.DEAD_ACACIA_DOOR.get().defaultBlockState()).getBlock();
								    if (level.getBlockState((pos)).getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER)
								    {
								    	BlockState state = addblock.withPropertiesOf(level.getBlockState(pos.below()));
								    	level.removeBlock((pos.below()), false);
									    level.setBlock(pos.below(), state,
									    Block.UPDATE_ALL);
									    state.getBlock().setPlacedBy(level, pos.below(), state, entity, null);
								    }
								}
								if(door == Blocks.JUNGLE_DOOR)
								{
									DoorBlock addblock = (DoorBlock) (BlockInit.DEAD_JUNGLE_DOOR.get().defaultBlockState()).getBlock();
								    if (level.getBlockState((pos)).getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER)
								    {
								    	BlockState state = addblock.withPropertiesOf(level.getBlockState(pos.below()));
								    	level.removeBlock((pos.below()), false);
									    level.setBlock(pos.below(), state,
									    Block.UPDATE_ALL);
									    state.getBlock().setPlacedBy(level, pos.below(), state, entity, null);
								    }
								}
								if(door == Blocks.SPRUCE_DOOR)
								{
									DoorBlock addblock = (DoorBlock) (BlockInit.DEAD_SPRUCE_DOOR.get().defaultBlockState()).getBlock();
								    if (level.getBlockState((pos)).getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER)
								    {
								    	BlockState state = addblock.withPropertiesOf(level.getBlockState(pos.below()));
								    	level.removeBlock((pos.below()), false);
									    level.setBlock(pos.below(), state,
									    Block.UPDATE_ALL);
									    state.getBlock().setPlacedBy(level, pos.below(), state, entity, null);
								    }
								}
								if(door == Blocks.DARK_OAK_DOOR)
								{
									DoorBlock addblock = (DoorBlock) (BlockInit.DEAD_DARK_OAK_DOOR.get().defaultBlockState()).getBlock();
								    if (level.getBlockState((pos)).getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER)
								    {
								    	BlockState state = addblock.withPropertiesOf(level.getBlockState(pos.below()));
								    	level.removeBlock((pos.below()), false);
									    level.setBlock(pos.below(), state,
									    Block.UPDATE_ALL);
									    state.getBlock().setPlacedBy(level, pos.below(), state, entity, null);
								    }
								}
							}
							
							if(block instanceof FenceBlock )
							{
								FenceBlock fence = (FenceBlock) block;
								if(fence == Blocks.OAK_FENCE)
								{
									FenceBlock addblock = (FenceBlock) (BlockInit.DEAD_OAK_FENCE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(fence == Blocks.BIRCH_FENCE)
								{
									FenceBlock addblock = (FenceBlock) (BlockInit.DEAD_BIRCH_FENCE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(fence == Blocks.ACACIA_FENCE)
								{
									FenceBlock addblock = (FenceBlock) (BlockInit.DEAD_ACACIA_FENCE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(fence == Blocks.JUNGLE_FENCE)
								{
									FenceBlock addblock = (FenceBlock) (BlockInit.DEAD_JUNGLE_FENCE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(fence == Blocks.SPRUCE_FENCE)
								{
									FenceBlock addblock = (FenceBlock) (BlockInit.DEAD_SPRUCE_FENCE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(fence == Blocks.DARK_OAK_FENCE)
								{
									FenceBlock addblock = (FenceBlock) (BlockInit.DEAD_DARK_OAK_FENCE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
							}
							
							if(block instanceof FenceGateBlock )
							{
								FenceGateBlock fencegate = (FenceGateBlock) block;
								if(fencegate == Blocks.OAK_FENCE_GATE)
								{
									FenceGateBlock addblock = (FenceGateBlock) (BlockInit.DEAD_OAK_FENCE_GATE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(fencegate == Blocks.BIRCH_FENCE_GATE)
								{
									FenceGateBlock addblock = (FenceGateBlock) (BlockInit.DEAD_BIRCH_FENCE_GATE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(fencegate == Blocks.ACACIA_FENCE_GATE)
								{
									FenceGateBlock addblock = (FenceGateBlock) (BlockInit.DEAD_ACACIA_FENCE_GATE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(fencegate == Blocks.JUNGLE_FENCE_GATE)
								{
									FenceGateBlock addblock = (FenceGateBlock) (BlockInit.DEAD_JUNGLE_FENCE_GATE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(fencegate == Blocks.SPRUCE_FENCE_GATE)
								{
									FenceGateBlock addblock = (FenceGateBlock) (BlockInit.DEAD_SPRUCE_FENCE_GATE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(fencegate == Blocks.DARK_OAK_FENCE_GATE)
								{
									FenceGateBlock addblock = (FenceGateBlock) (BlockInit.DEAD_DARK_OAK_FENCE_GATE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
							}
							
							if(block instanceof PressurePlateBlock )
							{
								PressurePlateBlock pressureplate = (PressurePlateBlock) block;
								if(pressureplate == Blocks.OAK_PRESSURE_PLATE)
								{
									PressurePlateBlock addblock = (PressurePlateBlock) (BlockInit.DEAD_OAK_PRESSURE_PLATE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(pressureplate == Blocks.BIRCH_PRESSURE_PLATE)
								{
									PressurePlateBlock addblock = (PressurePlateBlock) (BlockInit.DEAD_BIRCH_PRESSURE_PLATE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(pressureplate == Blocks.ACACIA_PRESSURE_PLATE)
								{
									PressurePlateBlock addblock = (PressurePlateBlock) (BlockInit.DEAD_ACACIA_PRESSURE_PLATE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(pressureplate == Blocks.JUNGLE_PRESSURE_PLATE)
								{
									PressurePlateBlock addblock = (PressurePlateBlock) (BlockInit.DEAD_JUNGLE_PRESSURE_PLATE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(pressureplate == Blocks.SPRUCE_PRESSURE_PLATE)
								{
									PressurePlateBlock addblock = (PressurePlateBlock) (BlockInit.DEAD_SPRUCE_PRESSURE_PLATE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(pressureplate == Blocks.DARK_OAK_PRESSURE_PLATE)
								{
									PressurePlateBlock addblock = (PressurePlateBlock) (BlockInit.DEAD_DARK_OAK_PRESSURE_PLATE.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
							}
							
							if(block instanceof SlabBlock )
							{
								SlabBlock slab = (SlabBlock) block;
								if(slab == Blocks.OAK_SLAB)
								{
									SlabBlock addblock = (SlabBlock) (BlockInit.DEAD_OAK_SLAB.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(slab == Blocks.BIRCH_SLAB)
								{
									SlabBlock addblock = (SlabBlock) (BlockInit.DEAD_BIRCH_SLAB.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(slab == Blocks.ACACIA_SLAB)
								{
									SlabBlock addblock = (SlabBlock) (BlockInit.DEAD_ACACIA_SLAB.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(slab == Blocks.JUNGLE_SLAB)
								{
									SlabBlock addblock = (SlabBlock) (BlockInit.DEAD_JUNGLE_SLAB.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(slab == Blocks.SPRUCE_SLAB)
								{
									SlabBlock addblock = (SlabBlock) (BlockInit.DEAD_SPRUCE_SLAB.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(slab == Blocks.DARK_OAK_SLAB)
								{
									SlabBlock addblock = (SlabBlock) (BlockInit.DEAD_DARK_OAK_SLAB.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
							}
							
							if(block instanceof StairBlock )
							{
								StairBlock stair = (StairBlock) block;
								if(stair == Blocks.OAK_STAIRS)
								{
									StairBlock addblock = (StairBlock) (BlockInit.DEAD_OAK_STAIRS.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(stair == Blocks.BIRCH_STAIRS)
								{
									StairBlock addblock = (StairBlock) (BlockInit.DEAD_BIRCH_STAIRS.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(stair == Blocks.ACACIA_STAIRS)
								{
									StairBlock addblock = (StairBlock) (BlockInit.DEAD_ACACIA_STAIRS.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(stair == Blocks.JUNGLE_STAIRS)
								{
									StairBlock addblock = (StairBlock) (BlockInit.DEAD_JUNGLE_STAIRS.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(stair == Blocks.SPRUCE_STAIRS)
								{
									StairBlock addblock = (StairBlock) (BlockInit.DEAD_SPRUCE_STAIRS.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(stair == Blocks.DARK_OAK_STAIRS)
								{
									StairBlock addblock = (StairBlock) (BlockInit.DEAD_DARK_OAK_STAIRS.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
							}
							
							if(block instanceof TrapDoorBlock )
							{
								TrapDoorBlock trapdoor = (TrapDoorBlock) block;
								if(trapdoor == Blocks.OAK_TRAPDOOR)
								{
									TrapDoorBlock addblock = (TrapDoorBlock) (BlockInit.DEAD_OAK_TRAPDOOR.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(trapdoor == Blocks.BIRCH_TRAPDOOR)
								{
									TrapDoorBlock addblock = (TrapDoorBlock) (BlockInit.DEAD_BIRCH_TRAPDOOR.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(trapdoor == Blocks.ACACIA_TRAPDOOR)
								{
									TrapDoorBlock addblock = (TrapDoorBlock) (BlockInit.DEAD_ACACIA_TRAPDOOR.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(trapdoor == Blocks.JUNGLE_TRAPDOOR)
								{
									TrapDoorBlock addblock = (TrapDoorBlock) (BlockInit.DEAD_JUNGLE_TRAPDOOR.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(trapdoor == Blocks.SPRUCE_TRAPDOOR)
								{
									TrapDoorBlock addblock = (TrapDoorBlock) (BlockInit.DEAD_SPRUCE_TRAPDOOR.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(trapdoor == Blocks.DARK_OAK_TRAPDOOR)
								{
									TrapDoorBlock addblock = (TrapDoorBlock) (BlockInit.DEAD_DARK_OAK_TRAPDOOR.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
							}
							
							if(block instanceof SignBlock )
							{
								SignBlock sign = (SignBlock) block;
								if(sign == Blocks.OAK_SIGN)
								{
									SignBlock addblock = (SignBlock) (BlockInit.DEAD_OAK_SIGN.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(sign == Blocks.BIRCH_SIGN)
								{
									SignBlock addblock = (SignBlock) (BlockInit.DEAD_BIRCH_SIGN.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(sign == Blocks.ACACIA_SIGN)
								{
									SignBlock addblock = (SignBlock) (BlockInit.DEAD_ACACIA_SIGN.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(sign == Blocks.JUNGLE_SIGN)
								{
									SignBlock addblock = (SignBlock) (BlockInit.DEAD_JUNGLE_SIGN.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(sign == Blocks.SPRUCE_SIGN)
								{
									SignBlock addblock = (SignBlock) (BlockInit.DEAD_SPRUCE_SIGN.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
								if(sign == Blocks.DARK_OAK_SIGN)
								{
									SignBlock addblock = (SignBlock) (BlockInit.DEAD_DARK_OAK_SIGN.get().defaultBlockState()).getBlock();
									addblock.withPropertiesOf(level.getBlockState(pos));
									level.setBlock(pos, addblock.withPropertiesOf(level.getBlockState(pos)),
									Block.UPDATE_ALL);
								}
							}
							
							if(block instanceof RotatedPillarBlock )
							{
								RotatedPillarBlock wood = ((RotatedPillarBlock)block);
								if(wood == Blocks.OAK_LOG)
								{
								    level.setBlock(pos, BlockInit.DEAD_OAK_LOG.get().defaultBlockState(),
								    Block.UPDATE_ALL);
							        System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.BIRCH_LOG)
								{
									level.setBlock(pos, BlockInit.DEAD_BIRCH_LOG.get().defaultBlockState(),
								    Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.ACACIA_LOG)
								{
									level.setBlock(pos, BlockInit.DEAD_ACACIA_LOG.get().defaultBlockState(),
								    Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.DARK_OAK_LOG)
								{
									level.setBlock(pos, BlockInit.DEAD_OAK_LOG.get().defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.JUNGLE_LOG)
								{
									level.setBlock(pos, BlockInit.DEAD_JUNGLE_LOG.get().defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.SPRUCE_LOG)
								{
									level.setBlock(pos, BlockInit.DEAD_SPRUCE_LOG.get().defaultBlockState(),
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