package com.projectmushroom.lavapotions.event;

import javax.annotation.Nonnull;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.entity.Reaper;
import com.projectmushroom.lavapotions.event.loot.EatenFishFromOcelotAdditionModifier;
import com.projectmushroom.lavapotions.event.loot.GuardianEyeFromElderGuardianAdditionModifier;
import com.projectmushroom.lavapotions.event.loot.RavagerHornFromRavagerAdditionModifier;
import com.projectmushroom.lavapotions.event.loot.StriderFootFromStriderAdditionModifier;
import com.projectmushroom.lavapotions.init.EntityInit;
import com.projectmushroom.lavapotions.recipe.LavaBrewingCauldronRecipe;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

	@SubscribeEvent
	public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>>
	                                                        event) {
		event.getRegistry().registerAll(
				new GuardianEyeFromElderGuardianAdditionModifier.Serializer().setRegistryName
				(new ResourceLocation(LavaPotions.MOD_ID, "guardian_eye_from_elder_guardian")),
				
				new RavagerHornFromRavagerAdditionModifier.Serializer().setRegistryName
				(new ResourceLocation(LavaPotions.MOD_ID, "ravager_horn_from_ravager")),
				
				new EatenFishFromOcelotAdditionModifier.Serializer().setRegistryName
				(new ResourceLocation(LavaPotions.MOD_ID, "eaten_fish_from_ocelot")),
				
				new StriderFootFromStriderAdditionModifier.Serializer().setRegistryName
				(new ResourceLocation(LavaPotions.MOD_ID, "strider_foot_from_strider"))
		);
		
	}
	
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event)
	{
		event.put(EntityInit.REAPER.get(), Reaper.createAttributes().build());
	}
	
	@SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, LavaBrewingCauldronRecipe.Type.ID, LavaBrewingCauldronRecipe.Type.INSTANCE);
    }
}
