package com.projectmushroom.lavapotions.init;

import java.util.function.Function;

import com.google.common.base.Supplier;
import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.block.custom.DeadOak;
import com.projectmushroom.lavapotions.block.custom.DeadOakStandingSignBlock;
import com.projectmushroom.lavapotions.block.custom.DeadOakWallSignBlock;
import com.projectmushroom.lavapotions.block.custom.LavaBrewingCauldron;
import com.projectmushroom.lavapotions.block.custom.LettucePlantBlock;
import com.projectmushroom.lavapotions.block.custom.TomatoPlantBlock;
import com.projectmushroom.lavapotions.block.entity.ModWoodTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LavaPotions.MOD_ID);
	
	public static final DeferredRegister<Item> ITEMS = ItemInit.ITEMS;
	
	public static final RegistryObject<Block> LAVA_BREWING_CAULDRON = register("lava_brewing_cauldron", 
			() -> new LavaBrewingCauldron(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().sound(SoundType.METAL)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.LAVA_POTIONS)));
	
	public static final RegistryObject<Block> DEAD_DIRT_BLOCK = register("dead_dirt_block", 
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
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
			() -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)), 
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(LavaPotions.DEAD_WOOD)));
	
	public static final RegistryObject<Block> DEAD_OAK_FENCE_GATE = register("dead_oak_fence_gate", 
			() -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)), 
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
			() -> new DeadOak(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
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
	
	public static final RegistryObject<Block> LETTUCE_PLANT = registerBlockWithoutBlockItem("lettuce_plant",

			() -> new LettucePlantBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()));

	public static final RegistryObject<Block> TOMATO_PLANT = registerBlockWithoutBlockItem("tomato_plant",

			() -> new TomatoPlantBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()));
	
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
}
