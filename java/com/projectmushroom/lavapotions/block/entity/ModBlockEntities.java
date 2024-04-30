package com.projectmushroom.lavapotions.block.entity;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.block.entity.custom.DeadAcaciaSignBlockEntity;
import com.projectmushroom.lavapotions.block.entity.custom.DeadBirchSignBlockEntity;
import com.projectmushroom.lavapotions.block.entity.custom.DeadDarkOakSignBlockEntity;
import com.projectmushroom.lavapotions.block.entity.custom.DeadJungleSignBlockEntity;
import com.projectmushroom.lavapotions.block.entity.custom.DeadOakSignBlockEntity;
import com.projectmushroom.lavapotions.block.entity.custom.DeadSpruceSignBlockEntity;
import com.projectmushroom.lavapotions.init.BlockInit;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
	
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = 
			DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, LavaPotions.MOD_ID);
	
	public static final RegistryObject<BlockEntityType<DeadOakSignBlockEntity>> DEAD_OAK_SIGN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("dead_oak_sign_block_entity", () ->
                    BlockEntityType.Builder.of(DeadOakSignBlockEntity::new,
                            BlockInit.DEAD_OAK_WALL_SIGN.get(),
                            BlockInit.DEAD_OAK_SIGN.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<DeadBirchSignBlockEntity>> DEAD_BIRCH_SIGN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("dead_birch_sign_block_entity", () ->
                    BlockEntityType.Builder.of(DeadBirchSignBlockEntity::new,
                            BlockInit.DEAD_BIRCH_WALL_SIGN.get(),
                            BlockInit.DEAD_BIRCH_SIGN.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<DeadAcaciaSignBlockEntity>> DEAD_ACACIA_SIGN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("dead_acacia_sign_block_entity", () ->
                    BlockEntityType.Builder.of(DeadAcaciaSignBlockEntity::new,
                            BlockInit.DEAD_ACACIA_WALL_SIGN.get(),
                            BlockInit.DEAD_ACACIA_SIGN.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<DeadSpruceSignBlockEntity>> DEAD_SPRUCE_SIGN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("dead_spruce_sign_block_entity", () ->
                    BlockEntityType.Builder.of(DeadSpruceSignBlockEntity::new,
                            BlockInit.DEAD_SPRUCE_WALL_SIGN.get(),
                            BlockInit.DEAD_SPRUCE_SIGN.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<DeadJungleSignBlockEntity>> DEAD_JUNGLE_SIGN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("dead_jungle_sign_block_entity", () ->
                    BlockEntityType.Builder.of(DeadJungleSignBlockEntity::new,
                            BlockInit.DEAD_JUNGLE_WALL_SIGN.get(),
                            BlockInit.DEAD_JUNGLE_SIGN.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<DeadDarkOakSignBlockEntity>> DEAD_DARK_OAK_SIGN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("dead_dark_oak_sign_block_entity", () ->
                    BlockEntityType.Builder.of(DeadDarkOakSignBlockEntity::new,
                            BlockInit.DEAD_DARK_OAK_WALL_SIGN.get(),
                            BlockInit.DEAD_DARK_OAK_SIGN.get()).build(null));
}
