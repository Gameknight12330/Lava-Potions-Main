package com.projectmushroom.lavapotions.init;

import java.util.function.Function;

import com.google.common.base.Supplier;
import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.block.custom.DeadAcacia;
import com.projectmushroom.lavapotions.block.custom.DeadAcaciaStandingSignBlock;
import com.projectmushroom.lavapotions.block.custom.DeadAcaciaWallSignBlock;
import com.projectmushroom.lavapotions.block.custom.DeadBirch;
import com.projectmushroom.lavapotions.block.custom.DeadBirchStandingSignBlock;
import com.projectmushroom.lavapotions.block.custom.DeadBirchWallSignBlock;
import com.projectmushroom.lavapotions.block.custom.DeadDarkOak;
import com.projectmushroom.lavapotions.block.custom.DeadDarkOakStandingSignBlock;
import com.projectmushroom.lavapotions.block.custom.DeadDarkOakWallSignBlock;
import com.projectmushroom.lavapotions.block.custom.DeadJungle;
import com.projectmushroom.lavapotions.block.custom.DeadJungleStandingSignBlock;
import com.projectmushroom.lavapotions.block.custom.DeadJungleWallSignBlock;
import com.projectmushroom.lavapotions.block.custom.DeadOak;
import com.projectmushroom.lavapotions.block.custom.DeadOakStandingSignBlock;
import com.projectmushroom.lavapotions.block.custom.DeadOakWallSignBlock;
import com.projectmushroom.lavapotions.block.custom.DeadSpruce;
import com.projectmushroom.lavapotions.block.custom.DeadSpruceStandingSignBlock;
import com.projectmushroom.lavapotions.block.custom.DeadSpruceWallSignBlock;
import com.projectmushroom.lavapotions.block.custom.LettucePlantBlock;
import com.projectmushroom.lavapotions.block.custom.TomatoPlantBlock;
import com.projectmushroom.lavapotions.block.entity.ModWoodTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BambooBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.MelonBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.PumpkinBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LavaPotions.MOD_ID);
	
	public static final DeferredRegister<Item> ITEMS = ItemInit.ITEMS;
	
//	-----------------------------------------------------------------------------------------------------------
//	                                            Dead Oak Wood
//	-----------------------------------------------------------------------------------------------------------
	public static final RegistryObject<Block> DEAD_OAK_LOG = register("dead_oak_log", 
			() -> new DeadOak(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_OAK_WOOD = register("dead_oak_wood", 
			() -> new DeadOak(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> STRIPPED_DEAD_OAK_LOG = register("stripped_dead_oak_log", 
			() -> new DeadOak(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> STRIPPED_DEAD_OAK_WOOD = register("stripped_dead_oak_wood", 
			() -> new DeadOak(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_OAK_STAIRS = register("dead_oak_stairs", 
			() -> new StairBlock(() -> BlockInit.DEAD_OAK_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_OAK_SLAB = register("dead_oak_slab", 
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_OAK_FENCE = register("dead_oak_fence", 
			() -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_OAK_FENCE_GATE = register("dead_oak_fence_gate", 
			() -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_OAK_BUTTON = register("dead_oak_button", 
			() -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_OAK_PRESSURE_PLATE = register("dead_oak_pressure_plate", 
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_OAK_DOOR = register("dead_oak_door", 
			() -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)), 
			object -> () -> new DoubleHighBlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_OAK_TRAPDOOR = register("dead_oak_trapdoor", 
			() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_OAK_WALL_SIGN = registerBlockWithoutBlockItem("dead_oak_wall_sign",
            () -> new DeadOakWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), ModWoodTypes.DEAD_OAK));

    public static final RegistryObject<Block> DEAD_OAK_SIGN = registerBlockWithoutBlockItem("dead_oak_sign",
            () -> new DeadOakStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), ModWoodTypes.DEAD_OAK));
	
	public static final RegistryObject<Block> DEAD_OAK_PLANKS = register("dead_oak_planks", 
			() -> new DeadOak(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) 
			{
	            @Override
	            public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return true;
	            }

	            @Override
	            public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 20;
	            }

	            @Override
	            public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 5;
	            }
			}, 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	

	public static final RegistryObject<Block> DEAD_BIRCH_LOG = register("dead_birch_log", 
			() -> new DeadBirch(BlockBehaviour.Properties.copy(Blocks.BIRCH_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_BIRCH_WOOD = register("dead_birch_wood", 
			() -> new DeadBirch(BlockBehaviour.Properties.copy(Blocks.BIRCH_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> STRIPPED_DEAD_BIRCH_LOG = register("stripped_dead_birch_log", 
			() -> new DeadBirch(BlockBehaviour.Properties.copy(Blocks.STRIPPED_BIRCH_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> STRIPPED_DEAD_BIRCH_WOOD = register("stripped_dead_birch_wood", 
			() -> new DeadBirch(BlockBehaviour.Properties.copy(Blocks.STRIPPED_BIRCH_WOOD)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_BIRCH_STAIRS = register("dead_birch_stairs", 
			() -> new StairBlock(() -> BlockInit.DEAD_BIRCH_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.BIRCH_STAIRS)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_BIRCH_SLAB = register("dead_birch_slab", 
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_SLAB)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_BIRCH_FENCE = register("dead_birch_fence", 
			() -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_FENCE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_BIRCH_FENCE_GATE = register("dead_birch_fence_gate", 
			() -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_FENCE_GATE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_BIRCH_BUTTON = register("dead_birch_button", 
			() -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_BUTTON)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_BIRCH_PRESSURE_PLATE = register("dead_birch_pressure_plate", 
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.BIRCH_PRESSURE_PLATE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_BIRCH_DOOR = register("dead_birch_door", 
			() -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_DOOR)), 
			object -> () -> new DoubleHighBlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_BIRCH_TRAPDOOR = register("dead_birch_trapdoor", 
			() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_TRAPDOOR)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_BIRCH_WALL_SIGN = registerBlockWithoutBlockItem("dead_birch_wall_sign",
            () -> new DeadBirchWallSignBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_WALL_SIGN), ModWoodTypes.DEAD_BIRCH));

    public static final RegistryObject<Block> DEAD_BIRCH_SIGN = registerBlockWithoutBlockItem("dead_birch_sign",
            () -> new DeadBirchStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_SIGN), ModWoodTypes.DEAD_BIRCH));
	
	public static final RegistryObject<Block> DEAD_BIRCH_PLANKS = register("dead_birch_planks", 
			() -> new DeadBirch(BlockBehaviour.Properties.copy(Blocks.BIRCH_PLANKS)) 
			{
	            @Override
	            public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return true;
	            }

	            @Override
	            public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 20;
	            }

	            @Override
	            public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 5;
	            }
			}, 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_ACACIA_LOG = register("dead_acacia_log", 
			() -> new DeadAcacia(BlockBehaviour.Properties.copy(Blocks.ACACIA_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_ACACIA_WOOD = register("dead_acacia_wood", 
			() -> new DeadAcacia(BlockBehaviour.Properties.copy(Blocks.ACACIA_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> STRIPPED_DEAD_ACACIA_LOG = register("stripped_dead_acacia_log", 
			() -> new DeadAcacia(BlockBehaviour.Properties.copy(Blocks.STRIPPED_ACACIA_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> STRIPPED_DEAD_ACACIA_WOOD = register("stripped_dead_acacia_wood", 
			() -> new DeadAcacia(BlockBehaviour.Properties.copy(Blocks.STRIPPED_ACACIA_WOOD)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_ACACIA_STAIRS = register("dead_acacia_stairs", 
			() -> new StairBlock(() -> BlockInit.DEAD_ACACIA_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.ACACIA_STAIRS)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_ACACIA_SLAB = register("dead_acacia_slab", 
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_SLAB)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_ACACIA_FENCE = register("dead_acacia_fence", 
			() -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_FENCE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_ACACIA_FENCE_GATE = register("dead_acacia_fence_gate", 
			() -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_FENCE_GATE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_ACACIA_BUTTON = register("dead_acacia_button", 
			() -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_BUTTON)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_ACACIA_PRESSURE_PLATE = register("dead_acacia_pressure_plate", 
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.ACACIA_PRESSURE_PLATE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_ACACIA_DOOR = register("dead_acacia_door", 
			() -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_DOOR)), 
			object -> () -> new DoubleHighBlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_ACACIA_TRAPDOOR = register("dead_acacia_trapdoor", 
			() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_TRAPDOOR)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_ACACIA_WALL_SIGN = registerBlockWithoutBlockItem("dead_acacia_wall_sign",
            () -> new DeadAcaciaWallSignBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_WALL_SIGN), ModWoodTypes.DEAD_ACACIA));

    public static final RegistryObject<Block> DEAD_ACACIA_SIGN = registerBlockWithoutBlockItem("dead_acacia_sign",
            () -> new DeadAcaciaStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_SIGN), ModWoodTypes.DEAD_ACACIA));
	
	public static final RegistryObject<Block> DEAD_ACACIA_PLANKS = register("dead_acacia_planks", 
			() -> new DeadAcacia(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS)) 
			{
	            @Override
	            public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return true;
	            }

	            @Override
	            public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 20;
	            }

	            @Override
	            public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 5;
	            }
			}, 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_SPRUCE_LOG = register("dead_spruce_log", 
			() -> new DeadSpruce(BlockBehaviour.Properties.copy(Blocks.SPRUCE_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_SPRUCE_WOOD = register("dead_spruce_wood", 
			() -> new DeadSpruce(BlockBehaviour.Properties.copy(Blocks.SPRUCE_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> STRIPPED_DEAD_SPRUCE_LOG = register("stripped_dead_spruce_log", 
			() -> new DeadSpruce(BlockBehaviour.Properties.copy(Blocks.STRIPPED_SPRUCE_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> STRIPPED_DEAD_SPRUCE_WOOD = register("stripped_dead_spruce_wood", 
			() -> new DeadSpruce(BlockBehaviour.Properties.copy(Blocks.STRIPPED_SPRUCE_WOOD)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_SPRUCE_STAIRS = register("dead_spruce_stairs", 
			() -> new StairBlock(() -> BlockInit.DEAD_SPRUCE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SPRUCE_STAIRS)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_SPRUCE_SLAB = register("dead_spruce_slab", 
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_SLAB)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_SPRUCE_FENCE = register("dead_spruce_fence", 
			() -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_SPRUCE_FENCE_GATE = register("dead_spruce_fence_gate", 
			() -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE_GATE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_SPRUCE_BUTTON = register("dead_spruce_button", 
			() -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_BUTTON)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_SPRUCE_PRESSURE_PLATE = register("dead_spruce_pressure_plate", 
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_SPRUCE_DOOR = register("dead_spruce_door", 
			() -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_DOOR)), 
			object -> () -> new DoubleHighBlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_SPRUCE_TRAPDOOR = register("dead_spruce_trapdoor", 
			() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_TRAPDOOR)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_SPRUCE_WALL_SIGN = registerBlockWithoutBlockItem("dead_spruce_wall_sign",
            () -> new DeadSpruceWallSignBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_WALL_SIGN), ModWoodTypes.DEAD_SPRUCE));

    public static final RegistryObject<Block> DEAD_SPRUCE_SIGN = registerBlockWithoutBlockItem("dead_spruce_sign",
            () -> new DeadSpruceStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_SIGN), ModWoodTypes.DEAD_SPRUCE));
	
	public static final RegistryObject<Block> DEAD_SPRUCE_PLANKS = register("dead_spruce_planks", 
			() -> new DeadSpruce(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS)) 
			{
	            @Override
	            public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return true;
	            }

	            @Override
	            public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 20;
	            }

	            @Override
	            public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 5;
	            }
			}, 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_JUNGLE_LOG = register("dead_jungle_log", 
			() -> new DeadJungle(BlockBehaviour.Properties.copy(Blocks.JUNGLE_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_JUNGLE_WOOD = register("dead_jungle_wood", 
			() -> new DeadJungle(BlockBehaviour.Properties.copy(Blocks.JUNGLE_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> STRIPPED_DEAD_JUNGLE_LOG = register("stripped_dead_jungle_log", 
			() -> new DeadJungle(BlockBehaviour.Properties.copy(Blocks.STRIPPED_JUNGLE_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> STRIPPED_DEAD_JUNGLE_WOOD = register("stripped_dead_jungle_wood", 
			() -> new DeadJungle(BlockBehaviour.Properties.copy(Blocks.STRIPPED_JUNGLE_WOOD)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_JUNGLE_STAIRS = register("dead_jungle_stairs", 
			() -> new StairBlock(() -> BlockInit.DEAD_JUNGLE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.JUNGLE_STAIRS)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_JUNGLE_SLAB = register("dead_jungle_slab", 
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_SLAB)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_JUNGLE_FENCE = register("dead_jungle_fence", 
			() -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_FENCE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_JUNGLE_FENCE_GATE = register("dead_jungle_fence_gate", 
			() -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_FENCE_GATE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_JUNGLE_BUTTON = register("dead_jungle_button", 
			() -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_BUTTON)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_JUNGLE_PRESSURE_PLATE = register("dead_jungle_pressure_plate", 
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.JUNGLE_PRESSURE_PLATE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_JUNGLE_DOOR = register("dead_jungle_door", 
			() -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_DOOR)), 
			object -> () -> new DoubleHighBlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_JUNGLE_TRAPDOOR = register("dead_jungle_trapdoor", 
			() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_TRAPDOOR)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_JUNGLE_WALL_SIGN = registerBlockWithoutBlockItem("dead_jungle_wall_sign",
            () -> new DeadJungleWallSignBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_WALL_SIGN), ModWoodTypes.DEAD_JUNGLE));

    public static final RegistryObject<Block> DEAD_JUNGLE_SIGN = registerBlockWithoutBlockItem("dead_jungle_sign",
            () -> new DeadJungleStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_SIGN), ModWoodTypes.DEAD_JUNGLE));
	
	public static final RegistryObject<Block> DEAD_JUNGLE_PLANKS = register("dead_jungle_planks", 
			() -> new DeadJungle(BlockBehaviour.Properties.copy(Blocks.JUNGLE_PLANKS)) 
			{
	            @Override
	            public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return true;
	            }

	            @Override
	            public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 20;
	            }

	            @Override
	            public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 5;
	            }
			}, 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_DARK_OAK_LOG = register("dead_dark_oak_log", 
			() -> new DeadDarkOak(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_DARK_OAK_WOOD = register("dead_dark_oak_wood", 
			() -> new DeadDarkOak(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> STRIPPED_DEAD_DARK_OAK_LOG = register("stripped_dead_dark_oak_log", 
			() -> new DeadDarkOak(BlockBehaviour.Properties.copy(Blocks.STRIPPED_DARK_OAK_LOG)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> STRIPPED_DEAD_DARK_OAK_WOOD = register("stripped_dead_dark_oak_wood", 
			() -> new DeadDarkOak(BlockBehaviour.Properties.copy(Blocks.STRIPPED_DARK_OAK_WOOD)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_DARK_OAK_STAIRS = register("dead_dark_oak_stairs", 
			() -> new StairBlock(() -> BlockInit.DEAD_DARK_OAK_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DARK_OAK_STAIRS)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_DARK_OAK_SLAB = register("dead_dark_oak_slab", 
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_SLAB)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_DARK_OAK_FENCE = register("dead_dark_oak_fence", 
			() -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_FENCE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_DARK_OAK_FENCE_GATE = register("dead_dark_oak_fence_gate", 
			() -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_FENCE_GATE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_DARK_OAK_BUTTON = register("dead_dark_oak_button", 
			() -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_BUTTON)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_DARK_OAK_PRESSURE_PLATE = register("dead_dark_oak_pressure_plate", 
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.DARK_OAK_PRESSURE_PLATE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_DARK_OAK_DOOR = register("dead_dark_oak_door", 
			() -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_DOOR)), 
			object -> () -> new DoubleHighBlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_DARK_OAK_TRAPDOOR = register("dead_dark_oak_trapdoor", 
			() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_TRAPDOOR)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_DARK_OAK_WALL_SIGN = registerBlockWithoutBlockItem("dead_dark_oak_wall_sign",
            () -> new DeadDarkOakWallSignBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_WALL_SIGN), ModWoodTypes.DEAD_DARK_OAK));

    public static final RegistryObject<Block> DEAD_DARK_OAK_SIGN = registerBlockWithoutBlockItem("dead_dark_oak_sign",
            () -> new DeadDarkOakStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_SIGN), ModWoodTypes.DEAD_DARK_OAK));
	
	public static final RegistryObject<Block> DEAD_DARK_OAK_PLANKS = register("dead_dark_oak_planks", 
			() -> new DeadDarkOak(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_PLANKS)) 
			{
	            @Override
	            public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return true;
	            }

	            @Override
	            public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 20;
	            }

	            @Override
	            public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 5;
	            }
			}, 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
	
	private static <T extends Block> RegistryObject<T> registerBlock(final String name, final Supplier<? extends T> block) {
		return BLOCKS.register(name, block);
	}
	
	private static <T extends Block> RegistryObject<T> register(final String name, final Supplier<? extends T> block, 
			Function<RegistryObject<T>, Supplier<? extends Item>> item) {
		RegistryObject<T> obj = registerBlock(name, block);
		ITEMS.register(name, item.apply(obj));
		return obj;
	}
	
	
//	-----------------------------------------------------------------------------------------------------------
//	                                            New Plant Blocks
//	-----------------------------------------------------------------------------------------------------------
	
	public static final RegistryObject<Block> LETTUCE_PLANT = registerBlockWithoutBlockItem("lettuce_plant",
			() -> new LettucePlantBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()));
	
	public static final RegistryObject<Block> TOMATO_PLANT = registerBlockWithoutBlockItem("tomato_plant",
			() -> new TomatoPlantBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()));
	
	
//	-----------------------------------------------------------------------------------------------------------
//   											 Other Dead Stuff
//-------------------------------------------------------------------------------------------------------------
	
	
	public static final RegistryObject<Block> DEAD_PUMPKIN = register("dead_pumpkin", 
			() -> new PumpkinBlock(BlockBehaviour.Properties.copy(Blocks.PUMPKIN)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_STUFF)));
	
	public static final RegistryObject<Block> DEAD_SUGAR_CANE = register("dead_sugar_cane", 
			() -> new SugarCaneBlock(BlockBehaviour.Properties.copy(Blocks.SUGAR_CANE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_STUFF)));
	
	public static final RegistryObject<Block> DEAD_BAMBOO = register("dead_bamboo", 
			() -> new BambooBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_STUFF)));
	
	public static final RegistryObject<Block> DEAD_CACTUS = register("dead_cactus", 
			() -> new CactusBlock(BlockBehaviour.Properties.copy(Blocks.CACTUS)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_STUFF)));
	
	public static final RegistryObject<Block> DEAD_VINES = register("dead_vines", 
			() -> new VineBlock(BlockBehaviour.Properties.copy(Blocks.VINE)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_STUFF)));
	
	public static final RegistryObject<Block> DEAD_RED_MUSHROOM = register("dead_red_mushroom", 
			() -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM), null), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_STUFF)));
	
	public static final RegistryObject<Block> DEAD_BROWN_MUSHROOM = register("dead_brown_mushroom", 
			() -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_STUFF)));
	
	public static final RegistryObject<Block> DEAD_MELON = register("dead_melon", 
			() -> new MelonBlock(BlockBehaviour.Properties.copy(Blocks.MELON)) 
			{
	            @Override
	            public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return true;
	            }

	            @Override
	            public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 20;
	            }

	            @Override
	            public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
	                return 5;
	            }
			}, 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_STUFF)));
	
}
