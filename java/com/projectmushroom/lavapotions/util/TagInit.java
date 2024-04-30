package com.projectmushroom.lavapotions.util;

import com.projectmushroom.lavapotions.LavaPotions;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class TagInit {
	public static class Blocks {
        public static final TagKey<Block> DEAD_OAK_LOGS
            = tag("dead_oak_logs");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(LavaPotions.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }
	
	public static class Items {
        public static final TagKey<Item> DEAD_OAK_LOGS
                = tag("dead_oak_logs");
        
        public static final TagKey<Item> LAVA_POTIONS
        = tag("lava_potions");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(LavaPotions.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}