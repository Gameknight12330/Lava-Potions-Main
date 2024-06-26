package com.projectmushroom.lavapotions.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.projectmushroom.lavapotions.LavaPotions;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class LavaBrewingCauldronRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public LavaBrewingCauldronRecipe(ResourceLocation id, ItemStack output,
                                   NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
    	int matches = 0;
    	boolean retVal = false;
    	for (int i = 0; i < recipeItems.size(); i++)
    	{
    		boolean matched = false;
    		for (int x = 3; x < 6; x++)
    		{
    			System.out.println("Comparing" + recipeItems.get(i) + " and " + pContainer.getItem(x));
    			if (recipeItems.get(i).test(pContainer.getItem(x)) && !matched)
    			{
    				matches += 1;
    				matched = true;
    			}
    		}
    	}
    	System.out.println(recipeItems.size() + " - " + matches);
    	if (matches == recipeItems.size())
    	{
    		retVal = true;
    	} else
    	{
    		retVal = false;
    	}
        return retVal;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<LavaBrewingCauldronRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "lava_brewing";
    }

    public static class Serializer implements RecipeSerializer<LavaBrewingCauldronRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(LavaPotions.MOD_ID,"lava_brewing");

        @Override
        public LavaBrewingCauldronRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(3, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new LavaBrewingCauldronRecipe(id, output, inputs);
        }

        @Override
        public LavaBrewingCauldronRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new LavaBrewingCauldronRecipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, LavaBrewingCauldronRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}