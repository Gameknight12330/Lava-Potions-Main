package com.projectmushroom.lavapotions.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.projectmushroom.lavapotions.LavaPotions;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LavaBrewingCauldronScreen extends AbstractContainerScreen<LavaBrewingCauldronMenu> {
	
	private static final ResourceLocation TEXTURE =
            new ResourceLocation(LavaPotions.MOD_ID, "textures/gui/lava_brewing_cauldron_gui.png");

	public LavaBrewingCauldronScreen(LavaBrewingCauldronMenu menu, Inventory inv, Component component) {
		super(menu, inv, component);
		this.imageHeight = 185;
		this.inventoryLabelY = 92;
	}

	@Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        
        if(menu.isCrafting()) {
            blit(pPoseStack, x + 138, y + 35, 208, 33 - menu.getArrowProgress(), 8, menu.getArrowProgress());
        }
        if(menu.isCrafting()) {
            blit(pPoseStack, x + 17, y + 34, 182, 34 - menu.getSteamProgress(), 22, menu.getSteamProgress());
        }
        if(menu.isCrafting()) {
            blit(pPoseStack, x + 56, y + 50, 179, 37, 67, 41);
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
