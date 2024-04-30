package com.projectmushroom.lavapotions.recipe;

import com.projectmushroom.lavapotions.LavaPotions;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LavaPotions.MOD_ID);

    public static final RegistryObject<RecipeSerializer<LavaBrewingCauldronRecipe>> LAVA_BREWING_SERIALIZER =
            SERIALIZERS.register("lava_brewing", () -> LavaBrewingCauldronRecipe.Serializer.INSTANCE);
}