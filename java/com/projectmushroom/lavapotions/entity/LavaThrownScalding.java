package com.projectmushroom.lavapotions.entity;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.projectmushroom.lavapotions.init.EntityInit;
import com.projectmushroom.lavapotions.init.ItemInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class LavaThrownScalding extends ThrowableItemProjectile implements ItemSupplier {
	
	@Override
	public Packet<?> getAddEntityPacket() {
	    return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public static final double SPLASH_RANGE = 4.0D;
	public static final Predicate<LivingEntity> WATER_SENSITIVE = LivingEntity::isSensitiveToWater;

	public LavaThrownScalding(EntityType<? extends LavaThrownScalding> p_37527_, Level p_37528_) {
	   super(p_37527_, p_37528_);
	}

	public LavaThrownScalding(Level p_37535_, LivingEntity p_37536_) {
	   super(EntityInit.LAVA_THROWN_SCALDING.get(), p_37536_, p_37535_);
	}

	public LavaThrownScalding(Level p_37530_, double p_37531_, double p_37532_, double p_37533_) {
	   super(EntityInit.LAVA_THROWN_SCALDING.get(), p_37531_, p_37532_, p_37533_, p_37530_);
	}

	protected Item getDefaultItem() {
	   return ItemInit.SPLASH_SCALDING.get();
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		return createAttributes();
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
	   super.onHit(result);
	   if (!this.level.isClientSide) {
	      this.applySplash(new MobEffectInstance(MobEffects.BLINDNESS, 600, 1), 
	    	 MobEffects.HARM, 1,
	    	 result.getType() == HitResult.Type.ENTITY ? ((EntityHitResult)result).getEntity() : null);
	   }
	   this.discard();
	}

	private void applySplash(MobEffectInstance blindness, MobEffect harming, int amplifier, @Nullable Entity p_37549_) {
	   AABB aabb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
	   List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, aabb);
	   if (!list.isEmpty()) {
	      Entity entity = this.getEffectSource();

	      for(LivingEntity livingentity : list) {
	         if (livingentity.isAffectedByPotions()) {
	            double d0 = this.distanceToSqr(livingentity);
	            if (d0 < 16.0D) {
	               double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
	               if (livingentity == p_37549_) {
	                  d1 = 1.0D;
	               }
	               harming.applyInstantenousEffect(this, this.getOwner(), livingentity, amplifier, d1);
	               int i = (int)(d1 * (double)blindness.getDuration() + 0.5D);
	               if (i > 20) {
	                  livingentity.addEffect(new MobEffectInstance(blindness), entity);
	               }
	            }
	         }
	      }
	   }
	}

}
