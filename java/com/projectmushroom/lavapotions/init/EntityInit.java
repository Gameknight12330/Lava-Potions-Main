package com.projectmushroom.lavapotions.init;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.entity.CustomFallingBlockEntity;
import com.projectmushroom.lavapotions.entity.LavaAreaEffectCloud;
import com.projectmushroom.lavapotions.entity.LavaThrownLingeringPotion;
import com.projectmushroom.lavapotions.entity.LavaThrownPotion;
import com.projectmushroom.lavapotions.entity.Reaper;
import com.projectmushroom.lavapotions.entity.ReaperSkull;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class EntityInit {
	
	private EntityInit() {}
	
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, 
    		LavaPotions.MOD_ID);
    
    public static final RegistryObject<EntityType<LavaThrownPotion>> LAVA_THROWN_POTION = ENTITIES.register("lava_thrown_potion",
    		() -> EntityType.Builder.of((EntityType.EntityFactory<LavaThrownPotion>) LavaThrownPotion::new, MobCategory.MISC)
    		.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
    		.build("lava_thrown_potion"));
    
    public static final RegistryObject<EntityType<LavaThrownLingeringPotion>> LAVA_THROWN_LINGERING_POTION = ENTITIES.register("lava_thrown_lingering_potion", 
    		() -> EntityType.Builder.of((EntityType.EntityFactory<LavaThrownLingeringPotion>) LavaThrownLingeringPotion::new, MobCategory.MISC)
    		.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
    		.build("lava_thrown_lingering_potion"));
    
    
    public static final RegistryObject<EntityType<LavaAreaEffectCloud>> LAVA_AREA_EFFECT_CLOUD = ENTITIES.register("lava_area_effect_cloud", 
    		() -> EntityType.Builder.of((EntityType.EntityFactory<LavaAreaEffectCloud>) LavaAreaEffectCloud::new, MobCategory.MISC)
    		.fireImmune().sized(6.0F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
    		.build("lava_area_effect_cloud"));
    
    public static final RegistryObject<EntityType<Reaper>> REAPER = ENTITIES.register("reaper", () -> 
            EntityType.Builder.of(Reaper::new, MobCategory.MONSTER)
            .fireImmune().immuneTo(Blocks.WITHER_ROSE).sized(1.8F, 5.25F).clientTrackingRange(10)
            .build(new ResourceLocation(LavaPotions.MOD_ID, "reaper").toString()));
    
    public static final RegistryObject<EntityType<CustomFallingBlockEntity>> CUSTOM_FALLING_BLOCK_ENTITY = ENTITIES.register("custom_falling_block_entity", () ->
            EntityType.Builder.of((EntityType.EntityFactory<CustomFallingBlockEntity>) CustomFallingBlockEntity::new, MobCategory.MISC)
            .setShouldReceiveVelocityUpdates(true).setTrackingRange(80).setUpdateInterval(3).sized(1.0F, 1.0F).build("custom_falling_block_entity"));
    
    public static final RegistryObject<EntityType<ReaperSkull>> REAPER_SKULL = ENTITIES.register("reaper_skull", () ->
            EntityType.Builder.of((EntityType.EntityFactory<ReaperSkull>) ReaperSkull::new, MobCategory.MISC).sized(0.3125F, 0.3125F)
            .clientTrackingRange(4).updateInterval(10).build("reaper_skull"));
    
}
