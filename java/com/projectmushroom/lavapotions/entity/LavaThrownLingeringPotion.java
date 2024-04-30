package com.projectmushroom.lavapotions.entity;

import com.projectmushroom.lavapotions.init.EntityInit;
import com.projectmushroom.lavapotions.init.ItemInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class LavaThrownLingeringPotion extends ThrowableItemProjectile implements ItemSupplier {
	
	@Override
	public Packet<?> getAddEntityPacket() {
	    return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public static final double SPLASH_RANGE = 4.0D;

	public LavaThrownLingeringPotion(EntityType<? extends LavaThrownLingeringPotion> p_37527_, Level p_37528_) {
	   super(p_37527_, p_37528_);
	}

	public LavaThrownLingeringPotion(Level p_37535_, LivingEntity p_37536_) {
	   super(EntityInit.LAVA_THROWN_LINGERING_POTION.get(), p_37536_, p_37535_);
	}

	public LavaThrownLingeringPotion(Level p_37530_, double p_37531_, double p_37532_, double p_37533_) {
	   super(EntityInit.LAVA_THROWN_LINGERING_POTION.get(), p_37531_, p_37532_, p_37533_, p_37530_);
	}

	protected Item getDefaultItem() {
	   return ItemInit.LINGERING_FLAME_HEALING.get();
	}

	protected float getGravity() {
	   return 0.05F;
	}

	protected void onHitBlock(BlockHitResult p_37541_) {
	   super.onHitBlock(p_37541_);
	   if (!this.level.isClientSide) {
	      this.getItem();
	      Direction direction = p_37541_.getDirection();
	      BlockPos blockpos = p_37541_.getBlockPos();
	      blockpos.relative(direction);
	   }

       this.level.levelEvent(2002, this.blockPosition(), 16733695);
       this.discard();
	}

	protected void onHit(HitResult result) {
	   ItemStack itemstack = this.getItem();
	   super.onHit(result);
	   if (!this.level.isClientSide) {
		   this.makeLavaAreaOfEffectCloud(itemstack, new MobEffectInstance(MobEffects.ABSORPTION, 600, 1), 
		   new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600), 
		   new MobEffectInstance(MobEffects.HEAL, 20));
	   }
	   this.discard();
	}

	private void makeLavaAreaOfEffectCloud(ItemStack p_37538_, MobEffectInstance absorption, MobEffectInstance fire_res, MobEffectInstance heal) {
	   LavaAreaEffectCloud lavaareaeffectcloud = new LavaAreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
	   Entity entity = this.getOwner();
	   if (entity instanceof LivingEntity) {
	      lavaareaeffectcloud.setOwner((LivingEntity)entity);
	   }

	   lavaareaeffectcloud.setRadius(3.0F);
	   lavaareaeffectcloud.setRadiusOnUse(-0.5F);
	   lavaareaeffectcloud.setWaitTime(10);
	   lavaareaeffectcloud.setRadiusPerTick(-lavaareaeffectcloud.getRadius() / (float)lavaareaeffectcloud.getDuration());
	   lavaareaeffectcloud.setFixedColor(16733695);
	   lavaareaeffectcloud.addEffect(absorption);
	   lavaareaeffectcloud.addEffect(fire_res);
	   lavaareaeffectcloud.addEffect(heal);

	   this.level.addFreshEntity(lavaareaeffectcloud);
	}
}
