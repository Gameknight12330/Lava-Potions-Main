package com.projectmushroom.lavapotions.screen;

import com.projectmushroom.lavapotions.LavaPotions;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
	
	public static final DeferredRegister<MenuType<?>> MENUS = 
			DeferredRegister.create(ForgeRegistries.CONTAINERS, LavaPotions.MOD_ID);
	
	public static final RegistryObject<MenuType<LavaBrewingCauldronMenu>> LAVA_BREWING_CAULDRON_MENU =
            registerMenuType(LavaBrewingCauldronMenu::new, "lava_brewing_cauldron_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                 String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
}
