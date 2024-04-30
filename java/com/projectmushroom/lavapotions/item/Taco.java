package com.projectmushroom.lavapotions.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class Taco extends Item{
	public Taco(Properties p_41383_) {
		super(p_41383_);
		// TODO Auto-generated constructor stub
	}

	public static final FoodProperties TACO = (new FoodProperties.Builder()).nutrition(6).saturationMod(1.2F).build();
	
	public static final FoodProperties BURRITO = (new FoodProperties.Builder()).nutrition(6).saturationMod(1.2F).build();
	
	public static final FoodProperties COOKED_TORTILLA = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).build();
	
	public static final FoodProperties LETTUCE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.2F).build();
	
	public static final FoodProperties TOMATO = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).build();
	
}
