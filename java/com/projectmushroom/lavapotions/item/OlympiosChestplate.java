package com.projectmushroom.lavapotions.item;

import com.projectmushroom.lavapotions.init.ItemInit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class OlympiosChestplate extends ArmorItem implements Wearable {
	
	public OlympiosChestplate(ArmorMaterial material, EquipmentSlot slot, Item.Properties properties) {
		super(material, slot, properties);
	    DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
	}

    public static boolean isFlyEnabled(ItemStack p_41141_) {
	    return p_41141_.getDamageValue() < p_41141_.getMaxDamage() - 1;
	}

	public boolean isValidRepairItem(ItemStack p_41134_, ItemStack p_41135_) {
	    return p_41135_.is(ItemInit.OLYMPIOS_INGOT.get());
	}

    public InteractionResultHolder<ItemStack> use(Level p_41137_, Player p_41138_, InteractionHand p_41139_) {
	    ItemStack itemstack = p_41138_.getItemInHand(p_41139_);
	    EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
	    ItemStack itemstack1 = p_41138_.getItemBySlot(equipmentslot);
	    if (itemstack1.isEmpty()) {
	        p_41138_.setItemSlot(equipmentslot, itemstack.copy());
	        itemstack.setCount(0);
	        return InteractionResultHolder.sidedSuccess(itemstack, p_41137_.isClientSide());
	    } else {
	        return InteractionResultHolder.fail(itemstack);
	    }
	}

	@Override
	public boolean canElytraFly(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
	    return ElytraItem.isFlyEnabled(stack);
	}

	@Override
	public boolean elytraFlightTick(ItemStack stack, net.minecraft.world.entity.LivingEntity entity, int flightTicks) {
	    if (!entity.level.isClientSide) {
	        int nextFlightTick = flightTicks + 1;
	        if (nextFlightTick % 10 == 0) {
	            if (nextFlightTick % 20 == 0) {
	                stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(net.minecraft.world.entity.EquipmentSlot.CHEST));
	            }
	            entity.gameEvent(net.minecraft.world.level.gameevent.GameEvent.ELYTRA_FREE_FALL);
	        }
	     }
	     return true;
	 }
}
