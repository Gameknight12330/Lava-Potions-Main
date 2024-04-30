package com.projectmushroom.lavapotions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.projectmushroom.lavapotions.block.entity.ModBlockEntities;
import com.projectmushroom.lavapotions.block.entity.ModWoodTypes;
import com.projectmushroom.lavapotions.client.BurningSpeedStartEvent;
import com.projectmushroom.lavapotions.client.CripplingStartEvent;
import com.projectmushroom.lavapotions.client.FieryRegenStartEvent;
import com.projectmushroom.lavapotions.client.HellishStartEvent;
import com.projectmushroom.lavapotions.client.HighwayStartEvent;
import com.projectmushroom.lavapotions.client.InvincStartEvent;
import com.projectmushroom.lavapotions.client.SoldierStartEvent;
import com.projectmushroom.lavapotions.client.SoulFlameStartEvent;
import com.projectmushroom.lavapotions.client.StairwayStartEvent;
import com.projectmushroom.lavapotions.client.StridingStartEvent;
import com.projectmushroom.lavapotions.client.ThanatosStartEvent;
import com.projectmushroom.lavapotions.client.TheosStartEvent;
import com.projectmushroom.lavapotions.client.VanishingStartEvent;
import com.projectmushroom.lavapotions.client.ViosStartEvent;
import com.projectmushroom.lavapotions.client.VolcanicStartEvent;
import com.projectmushroom.lavapotions.effect.LavaEffects;
import com.projectmushroom.lavapotions.init.BlockInit;
import com.projectmushroom.lavapotions.init.EntityInit;
import com.projectmushroom.lavapotions.init.ItemInit;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("lavapotions")
public class LavaPotions {
	
	public static final String MOD_ID = "lavapotions";
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	public static final CreativeModeTab LAVA_POTIONS = new CreativeModeTab(MOD_ID) {
		
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(ItemInit.LAVA_BOTTLE.get());
		}
	};
	
    public static final CreativeModeTab DEAD_WOOD = new CreativeModeTab("dead_wood") {
		
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(BlockInit.DEAD_OAK_LOG.get());
		}
	};
	
	 public static final CreativeModeTab TACO = new CreativeModeTab("taco") {

		    @Override
			@OnlyIn(Dist.CLIENT)
			public ItemStack makeIcon() {
				return new ItemStack(ItemInit.TACO.get());
			}
		};
		
		public static final CreativeModeTab DEAD_STUFF = new CreativeModeTab("dead_stuff") {
			
			@Override
			@OnlyIn(Dist.CLIENT)
			public ItemStack makeIcon() {
				return new ItemStack(BlockInit.DEAD_MELON.get());
			}
		};

	public LavaPotions() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
	    IEventBus forgeBus = MinecraftForge.EVENT_BUS;
	    
	    bus.addListener(this::setup);
        bus.addListener(this::clientSetup);
		
	    forgeBus.register(new HighwayStartEvent());
	    forgeBus.register(new BurningSpeedStartEvent());
	    forgeBus.register(new SoulFlameStartEvent());
	    forgeBus.register(new FieryRegenStartEvent());
	    forgeBus.register(new InvincStartEvent());
	    forgeBus.register(new VanishingStartEvent());
	    forgeBus.register(new VolcanicStartEvent());
	    forgeBus.register(new StridingStartEvent());
	    forgeBus.register(new CripplingStartEvent());
	    forgeBus.register(new HellishStartEvent());
	    forgeBus.register(new TheosStartEvent());
	    forgeBus.register(new ThanatosStartEvent());
	    forgeBus.register(new ViosStartEvent());
	    forgeBus.register(new StairwayStartEvent());
	    forgeBus.register(new SoldierStartEvent());



		
		ItemInit.ITEMS.register(bus);
		
		BlockInit.BLOCKS.register(bus);

		ModBlockEntities.BLOCK_ENTITIES.register(bus);
		
		LavaEffects.LAVA_EFFECTS.register(bus);
		
		EntityInit.ENTITIES.register(bus);
		
		MinecraftForge.EVENT_BUS.register(this);
		
		WoodType.register(ModWoodTypes.DEAD_OAK);
		WoodType.register(ModWoodTypes.DEAD_BIRCH);
		WoodType.register(ModWoodTypes.DEAD_ACACIA);
		WoodType.register(ModWoodTypes.DEAD_JUNGLE);
		WoodType.register(ModWoodTypes.DEAD_SPRUCE);
		WoodType.register(ModWoodTypes.DEAD_DARK_OAK);
		
		
	}
	
	private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEAD_OAK_DOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEAD_OAK_TRAPDOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEAD_BIRCH_DOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEAD_BIRCH_TRAPDOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEAD_ACACIA_DOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEAD_ACACIA_TRAPDOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEAD_JUNGLE_DOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEAD_JUNGLE_TRAPDOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEAD_SPRUCE_DOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEAD_SPRUCE_TRAPDOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEAD_DARK_OAK_DOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEAD_DARK_OAK_TRAPDOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.LETTUCE_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.TOMATO_PLANT.get(), RenderType.cutout());
    }
	
	private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}