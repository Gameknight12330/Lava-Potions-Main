package com.projectmushroom.lavapotions.entity;

import java.util.List;

import com.projectmushroom.lavapotions.effect.LavaEffects;
import com.projectmushroom.lavapotions.init.EntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ReaperSkull extends Fireball {
	private static final EntityDataAccessor<Boolean> DATA_DANGEROUS = SynchedEntityData.defineId(ReaperSkull.class, EntityDataSerializers.BOOLEAN);

	   public ReaperSkull(EntityType<? extends ReaperSkull> pEntityType, Level pLevel) {
	      super(pEntityType, pLevel);
	   }

	   public ReaperSkull(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
	      super(EntityInit.REAPER_SKULL.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
	   }

	   /**
	    * Return the motion factor for this projectile. The factor is multiplied by the original motion.
	    */
	   protected float getInertia() {
	      return this.isDangerous() ? 0.73F : super.getInertia();
	   }

	   /**
	    * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
	    */
	   public boolean isOnFire() {
	      return false;
	   }

	   /**
	    * Explosion resistance of a block relative to this entity
	    */
	   public float getBlockExplosionResistance(Explosion pExplosion, BlockGetter pLevel, BlockPos pPos, BlockState pBlockState, FluidState pFluidState, float pExplosionPower) {
	      return this.isDangerous() && pBlockState.canEntityDestroy(pLevel, pPos, this) ? Math.min(0.8F, pExplosionPower) : pExplosionPower;
	   }

	   /**
	    * Called when the arrow hits an entity
	    */
	   protected void onHitEntity(EntityHitResult pResult) {
	      super.onHitEntity(pResult);
	      if (!this.level.isClientSide) {
	         Entity entity = pResult.getEntity();
	         Entity entity1 = this.getOwner();
	         boolean flag;
	         if (entity1 instanceof LivingEntity && !(entity instanceof Reaper)) {
	            LivingEntity livingentity = (LivingEntity)entity1;
	            flag = entity.hurt(new DamageSource("Reaper"), 8.0F);
	            if (flag) {
	               if (entity.isAlive()) {
	                  this.doEnchantDamageEffects(livingentity, entity);
	               } else {
	                  livingentity.heal(5.0F);
	               }
	            }
	         } else {
	            flag = entity.hurt(DamageSource.MAGIC, 5.0F);
	         }

	         if (flag && entity instanceof LivingEntity) {
	            int i = 0;
	            if (this.level.getDifficulty() == Difficulty.NORMAL) {
	               i = 10;
	            } else if (this.level.getDifficulty() == Difficulty.HARD) {
	               i = 40;
	            }

	            if (i > 0) {
	               ((LivingEntity)entity).addEffect(new MobEffectInstance(LavaEffects.SOUL_FLAME.get(), 20 * i, 1), this.getEffectSource());
	            }
	         }

	      }
	   }

	   /**
	    * Called when this EntityFireball hits a block or entity.
	    */
	   protected void onHit(HitResult pResult) {
	      super.onHit(pResult);
	      if (!this.level.isClientSide) {
	         Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner()) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
	         this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, explosion$blockinteraction);
	         this.discard();
	         if (this.getTags().size() > 0) 
       	     {
       		     List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
	             AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
	             Entity entity = this.getOwner();
	             if (entity instanceof LivingEntity) 
	             {
	                areaeffectcloud.setOwner((LivingEntity)entity);
	             }
	             areaeffectcloud.setParticle(ParticleTypes.LARGE_SMOKE);
	             areaeffectcloud.setRadius(3.0F);
	             areaeffectcloud.setDuration(600);
	             areaeffectcloud.setRadiusPerTick((7.0F - areaeffectcloud.getRadius()) / (float)areaeffectcloud.getDuration());
	             areaeffectcloud.addEffect(new MobEffectInstance(LavaEffects.SOUL_FLAME.get(), 120, 1));
	             if (!list.isEmpty()) {
	                for(LivingEntity livingentity : list) {
	                   double d0 = this.distanceToSqr(livingentity);
	                   if (d0 < 16.0D) {
	                      areaeffectcloud.setPos(livingentity.getX(), livingentity.getY(), livingentity.getZ());
	                      break;
	                   }
	                }
	             }
	             this.level.levelEvent(2006, this.blockPosition(), this.isSilent() ? -1 : 1);
	             this.level.addFreshEntity(areaeffectcloud);
	             this.discard();
       	      }
	      }
	   }

	   /**
	    * Returns true if other Entities should be prevented from moving through this Entity.
	    */
	   public boolean isPickable() {
	      return false;
	   }

	   /**
	    * Called when the entity is attacked.
	    */
	   public boolean hurt(DamageSource pSource, float pAmount) {
	      return false;
	   }

	   protected void defineSynchedData() {
	      this.entityData.define(DATA_DANGEROUS, false);
	   }

	   /**
	    * Return whether this skull comes from an invulnerable (aura) reaper boss.
	    */
	   public boolean isDangerous() {
	      return this.entityData.get(DATA_DANGEROUS);
	   }

	   /**
	    * Set whether this skull comes from an invulnerable (aura) reaper boss.
	    */
	   public void setDangerous(boolean pInvulnerable) {
	      this.entityData.set(DATA_DANGEROUS, pInvulnerable);
	   }

	   protected boolean shouldBurn() {
	      return false;
	   }
	}