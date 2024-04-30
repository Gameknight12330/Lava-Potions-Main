package com.projectmushroom.lavapotions.init;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Supplier;
import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.effect.LavaEffects;
import com.projectmushroom.lavapotions.item.CheeseBucket;
import com.projectmushroom.lavapotions.item.LavaLingeringPotion;
import com.projectmushroom.lavapotions.item.LavaPotion;
import com.projectmushroom.lavapotions.item.LavaSplashPotion;
import com.projectmushroom.lavapotions.item.ModArmorMaterials;
import com.projectmushroom.lavapotions.item.OlympiosChestplate;
import com.projectmushroom.lavapotions.item.Taco;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.SignItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LavaPotions.MOD_ID);
	
	public static List<MobEffect> flameHealEffects = Arrays.asList(MobEffects.ABSORPTION, MobEffects.HEAL, MobEffects.FIRE_RESISTANCE);
	public static List<Integer> flameHealDurs = Arrays.asList(600, 1, 600);
	public static List<Integer> flameHealAmps = Arrays.asList(1, 20, 1);
	
	public static List<MobEffect> scaldingEffects = Arrays.asList(MobEffects.HARM, MobEffects.BLINDNESS);
	public static List<Integer> scaldingDurs = Arrays.asList(1, 600);
	public static List<Integer> scaldingAmps = Arrays.asList(1, 1);
	
	public static List<MobEffect> soulFlameEffects = Arrays.asList(LavaEffects.SOUL_FLAME.get());
	public static List<Integer> soulFlameDurs = Arrays.asList(2400);
	public static List<Integer> soulFlameAmps = Arrays.asList(1);
//	
//	public static List<MobEffect> fieryRegenEffects = Arrays.asList(LavaEffects.FIERY_REGEN.get());
//	public static List<Integer> fieryRegenDurs = Arrays.asList(1800);
//	public static List<Integer> fieryRegenAmps = Arrays.asList(1);
//	
//	public static List<MobEffect> vanishingEffects = Arrays.asList(LavaEffects.VANISHING.get());
//	public static List<Integer> vanishingDurs = Arrays.asList(2400);
//	public static List<Integer> vanishingAmps = Arrays.asList(-1);
//	
//	public static List<MobEffect> burningSpeedEffects = Arrays.asList(LavaEffects.BURNING_SPEED.get());
//	public static List<Integer> burningSpeedDurs = Arrays.asList(2400);
//	public static List<Integer> burningSpeedAmps = Arrays.asList(1);
	
	public static final RegistryObject<Item> REINFORCED_BOTTLE = ITEMS.register("reinforced_bottle", 
			() -> new Item(new Item.Properties().stacksTo(64).fireResistant().tab(LavaPotions.LAVA_POTIONS)));
	
	public static final RegistryObject<Item> LAVA_BOTTLE = register("lava_bottle", 
			() -> new Item(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)));
	
	public static final RegistryObject<Item> FLAME_HEALING = register("flame_healing",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(MobEffects.ABSORPTION, 600, 1), 1f).effect(
					() -> new MobEffectInstance(MobEffects.HEAL, 20), 1f).effect(
					() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600), 1f).build())));
	
	public static final RegistryObject<Item> SPLASH_FLAME_HEALING = register("splash_flame_healing", 
			() -> new LavaSplashPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS), flameHealEffects, flameHealDurs, flameHealAmps, 16733695));
	
	public static final RegistryObject<Item> LINGERING_FLAME_HEALING = register("lingering_flame_healing", 
			() -> new LavaLingeringPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS), flameHealEffects, flameHealDurs, flameHealAmps, 16733695));
	
	public static final RegistryObject<Item> SCALDING = register("scalding",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(MobEffects.HARM, 1, 1), 1f).effect(
					() -> new MobEffectInstance(MobEffects.BLINDNESS, 600), 1f).build())));

	public static final RegistryObject<Item> SPLASH_SCALDING = register("splash_scalding", 
			() -> new LavaSplashPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS), scaldingEffects, scaldingDurs, scaldingAmps, 11019543));
	
	public static final RegistryObject<Item> MARKING = register("marking",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3600, 3), 1f).effect(
					() -> new MobEffectInstance(MobEffects.GLOWING, 3600), 1f).build())));
	
	public static final RegistryObject<Item> STAIRWAY = register("stairway",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.STAIRWAY.get(), 6000), 1f).build())));
	
	public static final RegistryObject<Item> HIGHWAY = register("highway",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.HIGHWAY.get(), 2400), 1f).build())));

	public static final RegistryObject<Item> CRIPPLING = register("crippling",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.CRIPPLING.get(), 2400), 1f).build())));
	
    public static final RegistryObject<Item> SOUL_FLAME = register("soul_flame",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.SOUL_FLAME.get(), 2400), 1f).build())));
    
    public static final RegistryObject<Item> FIERY_REGEN = register("fiery_regen",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.FIERY_REGEN.get(), 1800), 1f).build())));
    
	public static final RegistryObject<Item> BURNING_SPEED = register("burning_speed",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.BURNING_SPEED.get(), 2400), 1f).build())));
	
	public static final RegistryObject<Item> INVINC = register("invinc",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.INVINC.get(), 300), 1f).build())));
	
	public static final RegistryObject<Item> VANISHING = register("vanishing",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.VANISHING.get(), 2400, -1), 1f).build())));
	
	public static final RegistryObject<Item> VOLCANIC = register("volcanic",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.VOLCANIC.get(), 2400), 1f).build())));
	
	public static final RegistryObject<Item> STRIDING = register("striding",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.STRIDING.get(), 3600), 1f).build())));
	
	public static final RegistryObject<Item> HELLISH = register("hellish",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.HELLISH.get(), 2400), 1f).build())));
	
	public static final RegistryObject<Item> TELIKOS = register("telikos",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.TELIKOS.get(), 999999999), 1f).build())));
	
	public static final RegistryObject<Item> THANATOS = register("thanatos",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.THANATOS.get(), 999999999), 1f).build())));
	
	public static final RegistryObject<Item> VIOS = register("vios",
			() -> new LavaPotion(new Item.Properties().stacksTo(1).fireResistant().tab(LavaPotions.LAVA_POTIONS)
					.food(new FoodProperties.Builder().alwaysEat().effect(
					() -> new MobEffectInstance(LavaEffects.VIOS.get(), 999999999), 1f).build())));
	
	public static final RegistryObject<Item> DEAD_OAK_SIGN = ITEMS.register("dead_oak_sign",
            () -> new SignItem(new Item.Properties().tab(LavaPotions.DEAD_WOOD).stacksTo(16),
                    BlockInit.DEAD_OAK_SIGN.get(), BlockInit.DEAD_OAK_WALL_SIGN.get()));
	
	public static final RegistryObject<Item> GUARDIAN_EYE = register("guardian_eye",
			() -> new Item(new Item.Properties().stacksTo(64).tab(LavaPotions.LAVA_POTIONS)));
	
	public static final RegistryObject<Item> RAVAGER_HORN = register("ravager_horn",
			() -> new Item(new Item.Properties().stacksTo(64).tab(LavaPotions.LAVA_POTIONS)));
	
	public static final RegistryObject<Item> EATEN_FISH = register("eaten_fish",
			() -> new Item(new Item.Properties().stacksTo(64).tab(LavaPotions.LAVA_POTIONS)));
	
	public static final RegistryObject<Item> STRIDER_FOOT = register("strider_foot",
			() -> new Item(new Item.Properties().stacksTo(64).fireResistant().tab(LavaPotions.LAVA_POTIONS)));
	
	public static final RegistryObject<Item> OLYMPIOS_INGOT = register("olympios_ingot",
			() -> new Item(new Item.Properties().stacksTo(64).fireResistant()));
	
	public static final RegistryObject<Item> DOUGH = register("dough",
			() -> new Item(new Item.Properties().stacksTo(64).tab(LavaPotions.TACO)));
	
	public static final RegistryObject<Item> UNCOOKED_TORTILLA = register("uncooked_tortilla",
			() -> new Item(new Item.Properties().stacksTo(64).tab(LavaPotions.TACO)));
	
	public static final RegistryObject<Item> COOKED_TORTILLA = register("cooked_tortilla",
			() -> new Item(new Item.Properties().stacksTo(64).tab(LavaPotions.TACO).food(Taco.COOKED_TORTILLA)));
	
	public static final RegistryObject<Item> TACO_SHELL = register("taco_shell",
			() -> new Item(new Item.Properties().stacksTo(64).tab(LavaPotions.TACO).food(Taco.COOKED_TORTILLA)));
	
	public static final RegistryObject<Item> CHEESE_BUCKET = register("cheese_bucket",
			() -> new CheeseBucket(new Item.Properties().stacksTo(1).tab(LavaPotions.TACO)));
	
	public static final RegistryObject<Item> TACO = ITEMS.register("taco",
			() -> new Item(new Item.Properties().stacksTo(1).tab(LavaPotions.TACO).food(Taco.TACO)));
	
	public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato",
			() -> new Item(new Item.Properties().stacksTo(64).tab(LavaPotions.TACO).food(Taco.TOMATO)));
	
	public static final RegistryObject<Item> LETTUCE = ITEMS.register("lettuce",
			() -> new Item(new Item.Properties().stacksTo(64).tab(LavaPotions.TACO).food(Taco.LETTUCE)));
	
	public static final RegistryObject<Item> BURRITO = ITEMS.register("burrito",
			() -> new Item(new Item.Properties().stacksTo(1).tab(LavaPotions.TACO).food(Taco.TACO)));
	
	public static final RegistryObject<Item> LETTUCE_SEEDS = register("lettuce_seeds",
			() -> new ItemNameBlockItem(BlockInit.LETTUCE_PLANT.get(), new Item.Properties().stacksTo(64).tab(LavaPotions.TACO)));

	public static final RegistryObject<Item> TOMATO_SEEDS = register("tomato_seeds",
			() -> new ItemNameBlockItem(BlockInit.TOMATO_PLANT.get(), new Item.Properties().stacksTo(64).tab(LavaPotions.TACO)));
	
	public static final RegistryObject<Item> OLYMPIOS_HELMET = ITEMS.register("olympios_helmet",
            () -> new ArmorItem(ModArmorMaterials.OLYMPIOS, EquipmentSlot.HEAD,
                    new Item.Properties()));
    public static final RegistryObject<Item> OLYMPIOS_CHESTPLATE = ITEMS.register("olympios_chestplate",
            () -> new OlympiosChestplate(ModArmorMaterials.OLYMPIOS, EquipmentSlot.CHEST,
                    new Item.Properties()));
    public static final RegistryObject<Item> OLYMPIOS_LEGGING = ITEMS.register("olympios_leggings",
            () -> new ArmorItem(ModArmorMaterials.OLYMPIOS, EquipmentSlot.LEGS,
                    new Item.Properties()));
    public static final RegistryObject<Item> OLYMPIOS_BOOTS = ITEMS.register("olympios_boots",
            () -> new ArmorItem(ModArmorMaterials.OLYMPIOS, EquipmentSlot.FEET,
                    new Item.Properties()));
	
	private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
		return ITEMS.register(name, item);
	}

}
