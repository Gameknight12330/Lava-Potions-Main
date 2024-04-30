package com.projectmushroom.lavapotions.init;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.entity.LavaAreaEffectCloud;
import com.projectmushroom.lavapotions.entity.LavaThrownLingeringPotion;
import com.projectmushroom.lavapotions.entity.LavaThrownPotion;
import com.projectmushroom.lavapotions.entity.LavaThrownScalding;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class EntityInit {
	
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, 
    		LavaPotions.MOD_ID);
    
    public static final RegistryObject<EntityType<LavaThrownPotion>> LAVA_THROWN_POTION = ENTITIES.register("lava_thrown_potion",
    		() -> EntityType.Builder.of((EntityType.EntityFactory<LavaThrownPotion>) LavaThrownPotion::new, MobCategory.MISC)
    		.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
    		.build("lava_thrown_potion"));
    
    public static final RegistryObject<EntityType<LavaThrownScalding>> LAVA_THROWN_SCALDING = ENTITIES.register("lava_thrown_scalding",
    		() -> EntityType.Builder.of((EntityType.EntityFactory<LavaThrownScalding>) LavaThrownScalding::new, MobCategory.MISC)
    		.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
    		.build("lava_thrown_scalding"));
    
    public static final RegistryObject<EntityType<LavaThrownLingeringPotion>> LAVA_THROWN_LINGERING_POTION = ENTITIES.register("lava_thrown_lingering_potion", 
    		() -> EntityType.Builder.of((EntityType.EntityFactory<LavaThrownLingeringPotion>) LavaThrownLingeringPotion::new, MobCategory.MISC)
    		.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
    		.build("lava_thrown_lingering_potion"));
    
    
    public static final RegistryObject<EntityType<LavaAreaEffectCloud>> LAVA_AREA_EFFECT_CLOUD = ENTITIES.register("lava_area_effect_cloud", 
    		() -> EntityType.Builder.of((EntityType.EntityFactory<LavaAreaEffectCloud>) LavaAreaEffectCloud::new, MobCategory.MISC)
    		.fireImmune().sized(6.0F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
    		.build("lava_area_effect_cloud"));
    
}
