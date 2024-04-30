package com.projectmushroom.lavapotions.event;

import javax.annotation.Nonnull;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.event.loot.EatenFishFromOcelotAdditionModifier;
import com.projectmushroom.lavapotions.event.loot.GuardianEyeFromElderGuardianAdditionModifier;
import com.projectmushroom.lavapotions.event.loot.RavagerHornFromRavagerAdditionModifier;
import com.projectmushroom.lavapotions.event.loot.StriderFootFromStriderAdditionModifier;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
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
}
