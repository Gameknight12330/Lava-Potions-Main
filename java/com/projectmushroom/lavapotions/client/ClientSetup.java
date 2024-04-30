package com.projectmushroom.lavapotions.client;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.block.entity.ModBlockEntities;
import com.projectmushroom.lavapotions.block.entity.ModWoodTypes;
import com.projectmushroom.lavapotions.client.renderer.LavaAreaEffectCloudRenderer;
import com.projectmushroom.lavapotions.client.renderer.LavaThrownLingeringPotionRenderer;
import com.projectmushroom.lavapotions.client.renderer.LavaThrownPotionRenderer;
import com.projectmushroom.lavapotions.client.renderer.ReaperRenderer;
import com.projectmushroom.lavapotions.client.renderer.ReaperSkullRenderer;
import com.projectmushroom.lavapotions.init.BlockInit;
import com.projectmushroom.lavapotions.init.EntityInit;
import com.projectmushroom.lavapotions.screen.LavaBrewingCauldronScreen;
import com.projectmushroom.lavapotions.screen.ModMenuTypes;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
	
	@SubscribeEvent
    public static void doSetup(final FMLClientSetupEvent event) {
		EntityRenderers.register(EntityInit.LAVA_THROWN_POTION.get(), LavaThrownPotionRenderer::new);
		EntityRenderers.register(EntityInit.LAVA_THROWN_LINGERING_POTION.get(), LavaThrownLingeringPotionRenderer::new);
        EntityRenderers.register(EntityInit.LAVA_AREA_EFFECT_CLOUD.get(), LavaAreaEffectCloudRenderer::new);
//        EntityRenderers.register(EntityInit.LAVA_THROWN_STAIRWAY.get(), LavaThrownStairwayRenderer::new);
        ItemBlockRenderTypes.setRenderLayer(BlockInit.LAVA_BREWING_CAULDRON.get(), RenderType.cutout());
        MenuScreens.register(ModMenuTypes.LAVA_BREWING_CAULDRON_MENU.get(), LavaBrewingCauldronScreen::new);
        WoodType.register(ModWoodTypes.DEAD_OAK);
        BlockEntityRenderers.register(ModBlockEntities.DEAD_OAK_SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
    }

	
	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerEntityRenderer(EntityInit.REAPER.get(), ReaperRenderer::new);
		event.registerEntityRenderer(EntityInit.REAPER_SKULL.get(), ReaperSkullRenderer::new);
	}
}
