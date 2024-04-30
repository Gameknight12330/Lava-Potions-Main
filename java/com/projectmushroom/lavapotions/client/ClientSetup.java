package com.projectmushroom.lavapotions.client;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.block.entity.ModBlockEntities;
import com.projectmushroom.lavapotions.client.renderer.LavaAreaEffectCloudRenderer;
import com.projectmushroom.lavapotions.client.renderer.LavaThrownLingeringPotionRenderer;
import com.projectmushroom.lavapotions.client.renderer.LavaThrownPotionRenderer;
import com.projectmushroom.lavapotions.client.renderer.LavaThrownScaldingRenderer;
import com.projectmushroom.lavapotions.init.EntityInit;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
	
	@SubscribeEvent
    public static void doSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(EntityInit.LAVA_THROWN_POTION.get(), LavaThrownPotionRenderer::new);
        EntityRenderers.register(EntityInit.LAVA_THROWN_SCALDING.get(), LavaThrownScaldingRenderer::new);
        EntityRenderers.register(EntityInit.LAVA_THROWN_LINGERING_POTION.get(), LavaThrownLingeringPotionRenderer::new);
        EntityRenderers.register(EntityInit.LAVA_AREA_EFFECT_CLOUD.get(), LavaAreaEffectCloudRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.DEAD_OAK_SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.DEAD_BIRCH_SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.DEAD_ACACIA_SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.DEAD_SPRUCE_SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.DEAD_JUNGLE_SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.DEAD_DARK_OAK_SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
        
    }

}
