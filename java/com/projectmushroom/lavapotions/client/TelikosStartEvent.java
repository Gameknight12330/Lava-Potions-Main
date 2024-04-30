package com.projectmushroom.lavapotions.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.effect.LavaEffects;
import com.projectmushroom.lavapotions.init.BlockInit;
import com.projectmushroom.lavapotions.init.ItemInit;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RootedDirtBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.WitherRoseBlock;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.event.InputEvent.MouseInputEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID)
public class TelikosStartEvent extends Event 
{
	
	Player player = null;
	boolean clicked = false;
	boolean setclicked = true;
	int swap = 1;
	boolean ison = true;
	Random rnd = new Random();
	
	// Thanatos variables
	int timer = 0;
	boolean dead = false;
	
	// Vios variables
	int timer2 = 0;
	int timer3 = 0;
	
	// Telos variables
	List<EnderMan> men = new ArrayList<EnderMan> ();
	int timer4 = 0;
	
	// Khamos variables
	List<Blaze> guards = new ArrayList<Blaze> ();
	List<SmallFireball> fireballs = new ArrayList<SmallFireball> ();
	List<LivingEntity> undeads = new ArrayList<LivingEntity> ();
	double[][] guardpos = new double[8][2];
	int timer5 = 0;
	int timer6 = 0;
	LivingEntity guardtarg = null;
	boolean knockcancel = false;
	Vec3 velocity = null;
	
	AttributeModifier telikos = new AttributeModifier(UUID.fromString("2674d824-3468-4886-976c-ccbf781cef3e"),
			"Telikos", 25.0D, AttributeModifier.Operation.ADDITION);
	
	AttributeModifier reach = new AttributeModifier(UUID.fromString("62de41dc-6977-4c1e-9d18-1194fb5decd8"),
			"reach", 12.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	
	AttributeModifier thanatos = new AttributeModifier(UUID.fromString("a37c5964-af14-4456-a1ce-93b45aee4524"),
			"thanatos", 5.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	
	AttributeModifier thanatos_speed = new AttributeModifier(UUID.fromString("ec51640e-82e7-45a5-a20a-97ba6e6a4e0d"),
			"thanatos_speed", -0.03D, AttributeModifier.Operation.ADDITION);
	
	AttributeModifier thanatos_attack = new AttributeModifier(UUID.fromString("bc634b66-23f5-4b67-917d-473b840a60a4"),
			"thanatos_attack", 3.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	
	AttributeModifier vios = new AttributeModifier(UUID.fromString("613fdacf-b170-4c12-8def-90a88b9ffd56"),
			"vios", 5.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	
	AttributeModifier vios_speed = new AttributeModifier(UUID.fromString("ef38d767-33aa-4a78-beff-fef37494b65e"),
			"vios_speed", 0.03D, AttributeModifier.Operation.ADDITION);
	
	AttributeModifier end_reach = new AttributeModifier(UUID.fromString("cdf2022b-0de3-4ded-9aa5-62e2235a6fa1"),
			"end_reach", 500.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	
	AttributeModifier khamos = new AttributeModifier(UUID.fromString("766240ff-3350-421d-996d-bdfb9845692b"),
			"khamos", 100.0D, AttributeModifier.Operation.ADDITION);
	
	AttributeModifier khamos_speed = new AttributeModifier(UUID.fromString("871a71f8-e66e-48dc-abbe-c47b6bdb332a"),
			"khamos_speed", 3.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	
	
	/*-------------------------------------------------
	         Generic Telikos Events and Methods
	--------------------------------------------------
	
	= = = = = = = = = = = = = = = = = = = = = = = = = = 
	These methods and events are those that either
	apply to more than one of the five effects, or act
	as anchors for all five at once. There are 12
	events and one method in this section.
	= = = = = = = = = = = = = = = = = = = = = = = = = = 
	
    ___________________________________________________
    event onTelikosStart(PotionAddedEvent event)
        This event tracks when Telikos is applied, and
        then checks to see if it is being reapplied
        after already having the effect. If this is
        the first application, the player gets the set
        of Olympios armor that allows flight as well as
        applies all default Telikos attribute modifiers.
    
    event onPlayerClick(LeftClickBlock event)
        This event checks when the player has left
        clicked a block, then checks which effect they
        have applied, as well as whether they have the
        effect turned on. 
            -Tsilis: The clicked block will be struck 
                     by lightning and explode.
                     
            -Vios:   The block will instantly break
                     and drop whatever the assigned
                     block drops are.
                     
            -Telos:  The player will teleport
                     directly to the block they 
                     clicked on.
    
    event onTelikosClick(MouseInputEvent event)
        This event is responsible for ensuring the
        player can't hold click to summon lightning
        constantly. It checks to see whether the player
        has released the mouse button before allowing
        another click.
        
    event onTelikosSwap(KeyInputEvent event)
        This event is responsible for detecting when
        the player presses R to switch effects, and
        calls the appropriate methods. It also
        checks for the player pressing B to deactivate
        or activate the effect, in which case it
        toggles the variable ison.
        
    event onTelikosTick(TickEvent event)
        This event is called each tick, and keeps track
        of all respective timers relating to the effects,
        calling the appropriate methods when the timer
        reaches the set limit.
            -Thanatos: Times the death pulse to happen
                       once every 1200 ticks, calls 
                       thanatosKill(player).
                       
            -Vios:     Times the healing pulse to
                       happen once every 1200 ticks,
                       calls viosHeal(player). Times
                       the bonemealing pulse to
                       happen once every 100 ticks,
                       calls viosBone(player).
                       
            -Telos:    Times the end pulse to happen
                       once every 1200 ticks, calls
                       telosSpread(player).
                       
            -Khamos:   Times the fire to spawn once
                       every 10 ticks, calls
                       khamosFlame(player). Times the
                       guards to attack once every 75 
                       ticks, calls guardsAttackTarget
                       (guardtarg, guards.get(e)).
        
    event onTelikosAttack(LivingAttackEvent event)
        This event checks to see when the player
        attacks an entity, then checks which effect
        the player has active.
            -Vios:      Applies Soldier effect.
            
            -Khamos:    When shifting, applies
                        Undead effect.
        
    event onEntityUpdate(LivingUpdateEvent event)
        This event checks to see when a hostile entity
        targets the player, then checks which effect
        the player has active.
            -Vios:      Makes the entity's target
                        null.
                        
            -Telos:     If the hostile entity is an
                        enderman, makes the entity's
                        target null.
        
    event onEntityTarget(LivingChangeEvent event)
        This event checks to see when a hostile
        entity changes its target from one entity 
        to the player, then checks which effect
        the player has active.
            -Vios:      Cancels the target change.
                        
            -Telos:     If the hostile entity is an
                        enderman, cancels the target
                        change.
        
    event onEntityHit(LivingHurtEvent event)
        This event detects when the player hurts a
        living entity, then checks which effect the
        player has active.
            -Tsilis:    Summons a lightning bolt,
                        instantly kills the entity.
                        
            -Telos:     Calls the method spawnGoons
                        (entity, source).
                        
            -Khamos:    Calls the method 
                        makeGuardsMad(entity, source).
        
    event onEntityDeath(LivingDeathEvent event)
        This event detects when a living entity
        dies, then checks which effect the
        player has active.
            -Telos:     If the entity is one of the
                        endermen spawned by Telos,
                        it removes this entity from
                        the list.
                        
            -Khamos:    If the entity is the current
                        target of the guards, set
                        the guards target to null.
        
    event onTelikosEnd(PotionRemoveEvent event)
        This event detects when the effect is
        removed for any reason, then removes all
        attribute modifiers from the player as
        well as calls the method removeKhamosGuards()
        and takes the Olympios Armor off.
        
    event onTelikosExpire(PotionExpiryEvent event)
        This event detects when the effect expires,
        then removes all attribute modifiers from
        the player as well as calls the method
        removeKhamosGuards() and takes the Olympios
        Armor off.
        
    method swapEffectMods(int effect)
        This method adds and removes the appropriate
        attribute modifiers based on the effect the
        player is switching to.
            -Tsilis:   Removes the Khamos increased
                       movement speed and increased
                       attack knockback modifiers.
                       Calls the method
                       removeKhamosGuards()
                       
            -Thanatos: Adds Thanatos increased max
                       health, decreased movement
                       speed, increased attack
                       damage, and increased attack
                       knockback modifiers.
                       
            -Vios:     Removes Thanatos increased max
                       health, decreased movement
                       speed, increased attack
                       damage, and increased attack
                       knockback modifiers. Adds
                       Vios increased max health and
                       increased movement speed
                       modifiers.
                       
            -Telos:    Removes Vios increased max
                       health and increased movement
                       speed modifiers, as well as
                       Telikos standard increased
                       reach distance and increased
                       attack range modifiers. Adds
                       Telos extremely increased
                       reach distance and extremely
                       increased attack range
                       modifiers.
                       
            -Khamos:   Removes Telos extremely
                       increased reach distance and
                       extremely increased attack
                       range modifiers. Adds Telikos
                       standard increased reach
                       distance and increased attack
                       range modifiers, as well as
                       Khamos increased movement
                       speed and increased attack
                       knockback modifiers. Calls
                       the method khamosGuards(player)
    ___________________________________________________*/
	
	
	@SubscribeEvent
	public void onTelikosStart(PotionAddedEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.TELIKOS.get()))
		{
			if(entity.hasEffect(LavaEffects.TELIKOS.get()) == false)
			{
				System.out.println("in if");
				player = (Player) entity;
				entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(telikos);
				entity.getAttribute(ForgeMod.REACH_DISTANCE.get()).addTransientModifier(reach);
				entity.getAttribute(ForgeMod.ATTACK_RANGE.get()).addTransientModifier(reach);
				player.getInventory().armor.set(3, ItemInit.OLYMPIOS_HELMET.get().getDefaultInstance());
				player.getInventory().armor.set(2, ItemInit.OLYMPIOS_CHESTPLATE.get().getDefaultInstance());
				player.getInventory().armor.set(1, ItemInit.OLYMPIOS_LEGGING.get().getDefaultInstance());
				player.getInventory().armor.set(0, ItemInit.OLYMPIOS_BOOTS.get().getDefaultInstance());
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerClick(LeftClickBlock event)
	{
		if(player != null)
		{
			if(player.hasEffect(LavaEffects.TELIKOS.get()) && ison && swap == 1)
			{
				if (!clicked && setclicked)
				{
					try
					{
						player.level.explode(player, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), 3.0F, Explosion.BlockInteraction.BREAK);
					    LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(player.level);
					    bolt.setPos(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
					    player.level.addFreshEntity(bolt);
					    
					} finally
					{
						System.out.println("This is here");
					}
					clicked = true;
				}
			}
			else if (player.hasEffect(LavaEffects.TELIKOS.get()) && ison && swap == 3)
			{
				Block block = player.getLevel().getBlockState(event.getPos()).getBlock();
				block.playerDestroy(player.getLevel(), player, event.getPos(), block.defaultBlockState(), null, event.getItemStack());
				player.getLevel().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
			}
			else if (player.hasEffect(LavaEffects.TELIKOS.get()) && ison && swap == 4)
			{
				if (!clicked && setclicked)
				{
					player.teleportTo(event.getPos().getX(), event.getPos().getY() + 1, event.getPos().getZ());
					event.setCanceled(true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onTelikosClick(MouseInputEvent event)
	{
		if (event.getButton() == 0 && event.getAction() == 0)
		{
			clicked = false;
			setclicked = false;
		}
		if (event.getButton() == 0 && event.getAction() == 1)
		{
			setclicked = true;
		}
	}

	@SubscribeEvent
	public void onTelikosSwap(KeyInputEvent event)
	{
		if (player != null)
		{
			if (player.hasEffect(LavaEffects.TELIKOS.get()))
			{
				if (event.getKey() == 82 && event.getAction() == 1)
				{
					System.out.println("Pressed R");
					if (swap != 5)
					{
						swap += 1;
						swapEffectMods(swap);
					} else
					{
						swap = 1;
						swapEffectMods(swap);
					}
				}
				if (event.getKey() == 66 && event.getAction() == 1)
				{
					if (ison)
					{
						if (swap == 5)
						{
							removeKhamosGuards();
						}
						ison = false;
					}
					else if (!ison)
					{
						if (swap == 5)
						{
							khamosGuards(player);
						}
						
						ison = true;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onTelikosTick(TickEvent event)
	{
		if(player != null && event.phase.equals(TickEvent.Phase.END))	
		{
			if (ison)
			{
				if(swap == 2)
				{
					timer += 1;
			        if(!player.hasEffect(LavaEffects.TELIKOS.get()))
			        {
				        dead = true;
			        }
			        if(timer >= 1200)
			        {
			            thanatosKill(player);
				        timer = 0;
			        }
				}
				else if (swap == 3)
				{
					timer3 += 1;
					timer2 += 1;
					if(!player.hasEffect(LavaEffects.TELIKOS.get()))
					{
						dead = true;
					}
					if(timer3 >= 1200)
					{
						viosHeal(player);
						timer3 = 0;
					}
					if(timer2 >= 100)
					{
						viosBone(player);
						timer2 = 0;
					}
				}
				else if (swap == 4)
				{
					timer4 += 1;
					if(!player.hasEffect(LavaEffects.TELIKOS.get()))
					{
						dead = true;
					}
					if(timer4 >= 1200)
					{
						telosSpread(player);
						timer4 = 0;
					}
				}
				else if (swap == 5)
				{
					timer5 += 1;
					timer6 += 1;
					if(!player.hasEffect(LavaEffects.TELIKOS.get()))
					{
						dead = true;
					}
					if(timer5 >= 10)
					{
						khamosFlame(player);
						timer5 = 0;
					}
					if (timer6 >= 75)
					{
						if (guardtarg != null)
						{
							for (int e = 0; e < guards.size(); e++)
							{
								guardsAttackTarget(guardtarg, guards.get(e));
							}		
						}
						timer6 = 0;
					}
					for (int g = 0; g < guards.size(); g++)
					{
						guards.get(g).setPos(player.getX() + guardpos[g][0], player.getY() + 4, player.getZ() + guardpos[g][1]);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onTelikosAttack(LivingAttackEvent event)
	{
		if(event.getSource().getDirectEntity() instanceof LivingEntity)
		{
			LivingEntity vios = (LivingEntity) event.getSource().getDirectEntity();
			if(vios.hasEffect(LavaEffects.TELIKOS.get()) && ison && swap == 3)
			{
				if(!event.getEntityLiving().hasEffect(LavaEffects.SOLDIER.get()))
				{
					event.getEntityLiving().addEffect(new MobEffectInstance(LavaEffects.SOLDIER.get(), 1200, 1));
				    System.out.println("Applied Soldier");
				}
				event.setCanceled(true);
			}
			else if(vios.hasEffect(LavaEffects.TELIKOS.get()) && ison && swap == 5)
			{
				if (player.isCrouching())
				{
					if(!event.getEntityLiving().hasEffect(LavaEffects.UNDEAD.get()))
					{
						event.getEntityLiving().addEffect(new MobEffectInstance(LavaEffects.UNDEAD.get(), 1200, 1));
						undeads.add(event.getEntityLiving());
					    System.out.println("Applied Undead");
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
	    LivingEntity entity = event.getEntityLiving();
	    if (entity instanceof Mob)
	    {
	    	Mob enemy = (Mob) entity;
	    	if (enemy.getTarget() instanceof Player && enemy.getTarget().hasEffect(LavaEffects.TELIKOS.get()) && ison && swap == 3)
	    	{
	    		enemy.setTarget(null);
	    	}
	    	else if (enemy.getTarget() instanceof Player && enemy.getTarget().hasEffect(LavaEffects.TELIKOS.get()) && event.getEntityLiving() instanceof EnderMan && swap == 4)
			{
				enemy.setTarget(null);
			}
	    	else if (enemy.getTarget() instanceof Player && enemy.getTarget().hasEffect(LavaEffects.TELIKOS.get()) && event.getEntityLiving().hasEffect(LavaEffects.UNDEAD.get()) && swap == 5)
			{
				enemy.setTarget(null);
			}
	    }
	}
	 
	@SubscribeEvent
	public void onEntityTarget(LivingChangeTargetEvent event)
	{
	    LivingEntity target = event.getNewTarget();
		if (target instanceof Player && target.hasEffect(LavaEffects.TELIKOS.get()) && ison && swap == 3)
		{
			event.setCanceled(true);
		}
		else if (target instanceof Player && target.hasEffect(LavaEffects.TELIKOS.get()) && event.getEntityLiving() instanceof EnderMan && swap == 4)
		{
			event.setCanceled(true);
		}
		else if (target instanceof Player && target.hasEffect(LavaEffects.TELIKOS.get()) && event.getEntityLiving().hasEffect(LavaEffects.UNDEAD.get()) && swap == 5)
		{
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onEntityHit(LivingHurtEvent event)
	{
        if (event.getSource().getDirectEntity() instanceof Player)
		{
		    LivingEntity entity = (LivingEntity) event.getEntityLiving();
		    Player source = (Player) event.getSource().getDirectEntity();
	    	if (source.hasEffect(LavaEffects.TELIKOS.get()) && ison && swap == 1)
	    	{
	    	    LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(player.level);
				bolt.setPos(event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ());
				source.level.addFreshEntity(bolt);
				if(entity.hasEffect(LavaEffects.INVINC.get()))
				{
					entity.removeEffect(LavaEffects.INVINC.get());
				}
				if(entity.hasEffect(MobEffects.DAMAGE_RESISTANCE))
				{
					entity.removeEffect(MobEffects.DAMAGE_RESISTANCE);
				}
				entity.hurt(new DamageSource("God"), 20000);
				if(entity instanceof EnderDragon)
				{
					entity.kill();
				}
				clicked = true;
	    	}
	    	else if (source.hasEffect(LavaEffects.TELIKOS.get()) && ison && swap == 4)
	    	{
	    		spawnGoons(source, entity);
	    	}
	    	else if (source.hasEffect(LavaEffects.TELIKOS.get()) && ison && swap == 5)
	    	{
	    		makeGuardsMad(source, entity);
	    		for (int g = 0; g < undeads.size(); g++)
	    		{
	    			if (undeads.get(g) instanceof Monster && !entity.hasEffect(LavaEffects.UNDEAD.get()))
	    			{
	    				System.out.println("Found a target for undeads");
	    				Monster attacker = (Monster) undeads.get(g);
	    				attacker.setTarget(entity);
	    			}
	    		}
	    	}
		}
	    if (event.getSource().getDirectEntity() instanceof LivingEntity && event.getEntityLiving() instanceof Player 
	    	&& event.getEntityLiving().hasEffect(LavaEffects.TELIKOS.get()))
	    {
	    	LivingEntity source = (LivingEntity) event.getSource().getDirectEntity();
	    	Player entity = (Player) event.getEntityLiving();
	    	if (ison && swap == 4)
	    	{
	    		spawnGoons(entity, source);
	    	}
	    	else if (ison && swap == 5)
	        {
	    	    makeGuardsMad(entity, source);
	    	    for (int g = 0; g < undeads.size(); g++)
	    		{
	    			if (undeads.get(g) instanceof Monster && !source.hasEffect(LavaEffects.UNDEAD.get()))
	    			{
	    				Monster attacker = (Monster) undeads.get(g);
	    				attacker.setTarget(source);
	    			}
	    		}
	        }
	    }
	    if (event.getEntityLiving().hasEffect(LavaEffects.TELIKOS.get()))
	    {
	    	event.setCanceled(true);
	    }
	}

	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		if (player != null)
		{
			if (men.contains(event.getEntityLiving()))
		    {
			    men.remove(event.getEntityLiving());
	     	}
			if (event.getEntityLiving() == guardtarg)
			{
				guardtarg = null;
			}
			if (undeads.contains(event.getEntityLiving()))
			{
				undeads.remove(event.getEntityLiving());
			}
		}
	}
	
	@SubscribeEvent
	public void onTelikosEnd(PotionRemoveEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.TELIKOS.get())) 
		{
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(telikos);
			if(player.getAttribute(ForgeMod.REACH_DISTANCE.get()).hasModifier(reach))
			{
				player.getAttribute(ForgeMod.REACH_DISTANCE.get()).removeModifier(reach);
			}
			if(player.getAttribute(ForgeMod.ATTACK_RANGE.get()).hasModifier(reach))
			{
				player.getAttribute(ForgeMod.ATTACK_RANGE.get()).removeModifier(reach);
			}
			if(player.getAttribute(Attributes.MAX_HEALTH).hasModifier(thanatos))
			{
		        player.getAttribute(Attributes.MAX_HEALTH).removeModifier(thanatos);
		    }
			if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(thanatos_speed))
			{
				player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(thanatos_speed);
			}
			if(player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(thanatos_attack))
			{
				player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(thanatos_attack);
			}
			if(player.getAttribute(Attributes.ATTACK_KNOCKBACK).hasModifier(thanatos_attack))
			{
				player.getAttribute(Attributes.ATTACK_KNOCKBACK).removeModifier(thanatos_attack);
			}
			if(player.getAttribute(Attributes.MAX_HEALTH).hasModifier(vios))
			{
				player.getAttribute(Attributes.MAX_HEALTH).removeModifier(vios);
			}
			if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(vios_speed))
			{
				player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(vios_speed);
			}
			if(player.getAttribute(ForgeMod.REACH_DISTANCE.get()).hasModifier(end_reach))
			{
				player.getAttribute(ForgeMod.REACH_DISTANCE.get()).removeModifier(end_reach);
			}
			if(player.getAttribute(ForgeMod.ATTACK_RANGE.get()).hasModifier(end_reach))
			{
				player.getAttribute(ForgeMod.ATTACK_RANGE.get()).removeModifier(end_reach);
			}
			if(player.getAttribute(Attributes.ATTACK_KNOCKBACK).hasModifier(khamos))
			{
				player.getAttribute(Attributes.ATTACK_KNOCKBACK).removeModifier(khamos);
			}
			if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(khamos_speed))
			{
				player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(khamos_speed);
			}
			if (guards.size() > 0)
			{
				removeKhamosGuards();
			}
			player.getInventory().armor.clear();
			player = null;
		}
	}
	
	@SubscribeEvent
	public void onTelikosExpire(PotionExpiryEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.TELIKOS.get())) 
		{
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(telikos);
			if(player.getAttribute(ForgeMod.REACH_DISTANCE.get()).hasModifier(reach))
			{
				player.getAttribute(ForgeMod.REACH_DISTANCE.get()).removeModifier(reach);
			}
			if(player.getAttribute(ForgeMod.ATTACK_RANGE.get()).hasModifier(reach))
			{
				player.getAttribute(ForgeMod.ATTACK_RANGE.get()).removeModifier(reach);
			}
			if(player.getAttribute(Attributes.MAX_HEALTH).hasModifier(thanatos))
			{
		        player.getAttribute(Attributes.MAX_HEALTH).removeModifier(thanatos);
		    }
			if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(thanatos_speed))
			{
				player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(thanatos_speed);
			}
			if(player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(thanatos_attack))
			{
				player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(thanatos_attack);
			}
			if(player.getAttribute(Attributes.ATTACK_KNOCKBACK).hasModifier(thanatos_attack))
			{
				player.getAttribute(Attributes.ATTACK_KNOCKBACK).removeModifier(thanatos_attack);
			}
			if(player.getAttribute(Attributes.MAX_HEALTH).hasModifier(vios))
			{
				player.getAttribute(Attributes.MAX_HEALTH).removeModifier(vios);
			}
			if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(vios_speed))
			{
				player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(vios_speed);
			}
			if(player.getAttribute(ForgeMod.REACH_DISTANCE.get()).hasModifier(end_reach))
			{
				player.getAttribute(ForgeMod.REACH_DISTANCE.get()).removeModifier(end_reach);
			}
			if(player.getAttribute(ForgeMod.ATTACK_RANGE.get()).hasModifier(end_reach))
			{
				player.getAttribute(ForgeMod.ATTACK_RANGE.get()).removeModifier(end_reach);
			}
			if(player.getAttribute(Attributes.ATTACK_KNOCKBACK).hasModifier(khamos))
			{
				player.getAttribute(Attributes.ATTACK_KNOCKBACK).removeModifier(khamos);
			}
			if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(khamos_speed))
			{
				player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(khamos_speed);
			}
			if (guards.size() > 0)
			{
				removeKhamosGuards();
			}
			player.getInventory().armor.clear();
			player = null;
		}
	}
	
	public void swapEffectMods(int effect)
	{
		if (player != null)
		{
			if (player.hasEffect(LavaEffects.TELIKOS.get()) && ison)
			{
				if (effect == 1)
				{
					// remove effects from case 5
					if(player.getAttribute(Attributes.ATTACK_KNOCKBACK).hasModifier(khamos))
					{
						player.getAttribute(Attributes.ATTACK_KNOCKBACK).removeModifier(khamos);
					}
					if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(khamos_speed))
					{
						player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(khamos_speed);
					}
					removeKhamosGuards();
				    // add effects for case 1
				}
				else if (effect == 2)
				{
					// remove effects from case 1
					if(!player.getAttribute(Attributes.MAX_HEALTH).hasModifier(thanatos))
					{
						player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(thanatos);
						player.heal(100);
					}
					if(!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(thanatos_speed))
					{
						player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(thanatos_speed);
					}
					if(!player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(thanatos_attack))
					{
						player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(thanatos_attack);
					}
					if(!player.getAttribute(Attributes.ATTACK_KNOCKBACK).hasModifier(thanatos_attack))
					{
						player.getAttribute(Attributes.ATTACK_KNOCKBACK).addTransientModifier(thanatos_attack);
					}
				}
				else if (effect == 3)
			    {
				    // removing Thanatos modifiers
					if(player.getAttribute(Attributes.MAX_HEALTH).hasModifier(thanatos))
					{
				        player.getAttribute(Attributes.MAX_HEALTH).removeModifier(thanatos);
				    }
					if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(thanatos_speed))
					{
						player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(thanatos_speed);
					}
					if(player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(thanatos_attack))
					{
						player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(thanatos_attack);
					}
					if(player.getAttribute(Attributes.ATTACK_KNOCKBACK).hasModifier(thanatos_attack))
					{
						player.getAttribute(Attributes.ATTACK_KNOCKBACK).removeModifier(thanatos_attack);
					}
						
					// adding Vios modifiers
					if(!player.getAttribute(Attributes.MAX_HEALTH).hasModifier(vios))
					{
						player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(vios);
						player.heal(100);
					}
					if(!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(vios_speed))
					{
						player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(vios_speed);
					}
			    }
			    else if (effect == 4)
			    {
				    // removing Vios modifiers
					if(player.getAttribute(Attributes.MAX_HEALTH).hasModifier(vios))
					{
						player.getAttribute(Attributes.MAX_HEALTH).removeModifier(vios);
					}
					if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(vios_speed))
					{
						player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(vios_speed);
					}
					if(player.getAttribute(ForgeMod.REACH_DISTANCE.get()).hasModifier(reach))
					{
						player.getAttribute(ForgeMod.REACH_DISTANCE.get()).removeModifier(reach);
					}
					if(player.getAttribute(ForgeMod.ATTACK_RANGE.get()).hasModifier(reach))
					{
						player.getAttribute(ForgeMod.ATTACK_RANGE.get()).removeModifier(reach);
					}
				    // add effects for case 4
					if(!player.getAttribute(ForgeMod.REACH_DISTANCE.get()).hasModifier(end_reach))
					{
						player.getAttribute(ForgeMod.REACH_DISTANCE.get()).addTransientModifier(end_reach);
					}
					if(!player.getAttribute(ForgeMod.ATTACK_RANGE.get()).hasModifier(end_reach))
					{
						player.getAttribute(ForgeMod.ATTACK_RANGE.get()).addTransientModifier(end_reach);
					}

			    }
			    else if (effect == 5)
			    {
			    	// remove effects from case 4
			    	if(player.getAttribute(ForgeMod.REACH_DISTANCE.get()).hasModifier(end_reach))
					{
						player.getAttribute(ForgeMod.REACH_DISTANCE.get()).removeModifier(end_reach);
					}
					if(player.getAttribute(ForgeMod.ATTACK_RANGE.get()).hasModifier(end_reach))
					{
						player.getAttribute(ForgeMod.ATTACK_RANGE.get()).removeModifier(end_reach);
					}
				    // add effects for case 5
					if(!player.getAttribute(ForgeMod.REACH_DISTANCE.get()).hasModifier(reach))
					{
						player.getAttribute(ForgeMod.REACH_DISTANCE.get()).addTransientModifier(reach);
					}
					if(!player.getAttribute(ForgeMod.ATTACK_RANGE.get()).hasModifier(reach))
					{
						player.getAttribute(ForgeMod.ATTACK_RANGE.get()).addTransientModifier(reach);
					}
					if(!player.getAttribute(Attributes.ATTACK_KNOCKBACK).hasModifier(khamos))
					{
						player.getAttribute(Attributes.ATTACK_KNOCKBACK).addTransientModifier(khamos);
					}
					if(!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(khamos_speed))
					{
						player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(khamos_speed);
					}
					khamosGuards(player);
				}
			}
		}
	}
	
	
	/*-------------------------------------------------
                  Tsilis Events and Methods
      -------------------------------------------------

    = = = = = = = = = = = = = = = = = = = = = = = = = = 
    These methods and events are those that apply only to
    the first of the five effects, Tsilis. There is one
    event in this section.
    = = = = = = = = = = = = = = = = = = = = = = = = = = 
	
    ___________________________________________________
    event onTsilisAirJump(KeyInputEvent event)
        Checks to see if the player is fall flying, has 
        the effect Telikos, and is swapped to Tsilis.
        If this is true, then when the player presses
        space, a silent and invisible rocket spawns
        that propells the player forward.
    ___________________________________________________*/

	
	@SubscribeEvent
	public void onTsilisAirJump(KeyInputEvent event)
	{
		if (player != null)
		{
			if (player.hasEffect(LavaEffects.TELIKOS.get()) && ison && swap == 1)
			{
				System.out.println("Has Telikos");
//				Level level = player.getLevel();
				
				if (!player.isOnGround() && event.getKey() == 32 && event.getAction() == 1)
				{
					System.out.println("Pressed Space");
					if (player.isFallFlying())
					{
						FireworkRocketEntity firework = new FireworkRocketEntity(player.getLevel(), Items.FIREWORK_ROCKET.getDefaultInstance(), player);
						firework.setInvisible(true);
						firework.setSilent(true);
						player.getLevel().addFreshEntity(firework);
					}
				}
			}
		}
	}
	
	
	/*-------------------------------------------------
                 Thanatos Events and Methods
      -------------------------------------------------

    = = = = = = = = = = = = = = = = = = = = = = = = = = 
    These methods and events are those that apply only 
    to the second of the five effects, Thanatos. There 
    is one method in this section.
    = = = = = = = = = = = = = = = = = = = = = = = = = =
	
    ___________________________________________________
    method thanatosKill(LivingEntity entity)
        Finds any blocks considered living in a
        20-block radius, and replaces them with their
        dead versions. In the case of blocks such as
        tall grass and leaves, it removes the block.
        Finds and kills up to 100 living entities in
        the same radius. 
    ___________________________________________________*/
	
	
	public void thanatosKill(LivingEntity entity)
	{
		if(entity.hasEffect(LavaEffects.TELIKOS.get()) && dead == false)
		{
			int limit = 0 ;
			if(entity.getLevel() instanceof ServerLevel)
			{
				ServerLevel level = (ServerLevel) entity.getLevel();
				for(int i = -20; i <= 20; i+= 1)
				{
					for(int x = -20; x <= 5; x+= 1)
					{
						for(int z = -20; z <= 20; z+= 1)
						{
							Block block = level.getBlockState(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z)).getBlock();
							
							if(block instanceof GrassBlock)
							{
								if (rnd.nextInt(2) == 0)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.DIRT.defaultBlockState(), Block.UPDATE_ALL);
							        System.out.println("there is a grass block at " + i + x + z);
								} else {
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.ROOTED_DIRT.defaultBlockState(), Block.UPDATE_ALL);
							        System.out.println("there is a grass block at " + i + x + z);
								}
							}
							if(block instanceof LeavesBlock || block instanceof TallGrassBlock)
							{
								level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.AIR.defaultBlockState(), 
								Block.UPDATE_ALL);
							    System.out.println("there is a leaf or tall grass block at " + i + x + z);
							}
							if(block instanceof FlowerBlock || block instanceof TallFlowerBlock )
							{
								level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.WITHER_ROSE.defaultBlockState(), Block.UPDATE_ALL);
						        System.out.println("there is a flower at " + i + x + z);
							}
							if(block instanceof RotatedPillarBlock)
							{
								RotatedPillarBlock wood = ((RotatedPillarBlock)block);
								if(wood == Blocks.OAK_LOG)
								{
								    level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), BlockInit.DEAD_OAK_LOG.get().defaultBlockState(),
								    Block.UPDATE_ALL);
							        System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.BIRCH_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.STRIPPED_BIRCH_LOG.defaultBlockState(),
								    Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.ACACIA_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.STRIPPED_ACACIA_LOG.defaultBlockState(),
								    Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.DARK_OAK_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.STRIPPED_DARK_OAK_LOG.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.JUNGLE_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.STRIPPED_JUNGLE_LOG.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.SPRUCE_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.STRIPPED_SPRUCE_LOG.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
							}
						}
					}
				}
				
				for(int i = 0; i < 100; i+= 5)
				{
					for (Entity entities : level.getAllEntities())
					{
						if(!(entities == entity) && entities instanceof LivingEntity)
						{
							LivingEntity target = ((LivingEntity)entities);
							
							if((!(target.getBlockX() > (entity.getBlockX() + i)) && !(target.getBlockX() < (entity.getBlockX() - i)))
									&& (!(target.getBlockY() > (entity.getBlockY() + i)) && !(target.getBlockY() < (entity.getBlockY() - i)))
									&& (!(target.getBlockZ() > (entity.getBlockZ() + i)) && !(target.getBlockZ() < (entity.getBlockZ() - i)))
									&& limit <= 200)
							{
								if(target.hasEffect(LavaEffects.INVINC.get()))
								{
									target.removeEffect(LavaEffects.INVINC.get());
								}
								if(target.hasEffect(MobEffects.DAMAGE_RESISTANCE))
								{
									target.removeEffect(MobEffects.DAMAGE_RESISTANCE);
								}
								if(target.hasEffect(LavaEffects.SOLDIER.get()))
								{
									target.hurt(new DamageSource("Devil"), 10);
									limit += 1;
								}
								else
								{
									target.hurt(new DamageSource("Devil"), 20000);
								    System.out.println(i + " Killed " + target);
								    limit += 1;
								}
							}
						}
					}
				}
				System.out.println("total killed " + limit);
			}
		}
	}
	
	
	/*-------------------------------------------------
                   Vios Events and Methods
      -------------------------------------------------

    = = = = = = = = = = = = = = = = = = = = = = = = = = 
    These methods and events are those that apply only
    to the third of the five effects, Vios. There are 
    two methods in this section.
    = = = = = = = = = = = = = = = = = = = = = = = = = = 
    
    ___________________________________________________
    method viosBone(LivingEntity entity)
        Finds any blocks that are able to be
        bonemealed within a five-block radius, and
        bonemeals one out of every 50 of them. Always
        bonemeals saplings.
        
    method viosHeal(LivingEntity entity)
        Finds any dead versions of blocks in a 20-block
        radius, and reverts them to their living
        versions. Has a one out of 25 chance of placing
        a sapling down. These saplings are reflective
        of the player's current biome.
    ___________________________________________________*/
	
	
	public void viosBone(LivingEntity entity)
	{
		if(entity.hasEffect(LavaEffects.TELIKOS.get()) && dead == false)
		{
			if(entity.getLevel() instanceof ServerLevel)
			{
				ServerLevel level = (ServerLevel) entity.getLevel();
				for(int x = -5; x <= 5; x += 1)
				{
					
					for(int y = -20; y <= 5; y += 1)
					{
						for(int z = -5; z <= 5; z += 1)
						{
							Block block = level.getBlockState(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z)).getBlock();
							boolean grass = false;
							if(block instanceof GrassBlock && !(block instanceof TallGrassBlock))
							{
								if(rnd.nextInt(50) == 0)
								{
									BonemealableBlock growblock = (BonemealableBlock) block;
									growblock.performBonemeal(level, rnd, (new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z)),
									level.getBlockState(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z)));
								}
								grass = true;
							}
							if(block instanceof BonemealableBlock && !grass && !(block instanceof TallGrassBlock))
							{
								BonemealableBlock growblock = (BonemealableBlock) block;
								growblock.performBonemeal(level, rnd, (new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z)),
								level.getBlockState(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z)));
							}
						}
					}
				}
			}
		}
	}
	
	public void viosHeal(LivingEntity entity)
	{
		if(entity.hasEffect(LavaEffects.TELIKOS.get()) && dead == false)
		{
			if(entity.getLevel() instanceof ServerLevel)
			{
				ServerLevel level = (ServerLevel) entity.getLevel();
				for(int x = -20; x <= 20; x += 1)
				{
					for(int y = -20; y <= 5; y+= 1)
					{
						for(int z = -20; z <= 20; z+= 1)
						{
							Block block = level.getBlockState(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z)).getBlock();
							
							if(block == Blocks.DIRT || block instanceof RootedDirtBlock)
							{
								level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.GRASS_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
							}
							if(block instanceof WitherRoseBlock )
							{
								level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
						        System.out.println("there is a wither rose at " + x  +y + z);
							}
							if(block instanceof RotatedPillarBlock )
							{
								RotatedPillarBlock wood = ((RotatedPillarBlock)block);
								if(wood == BlockInit.DEAD_OAK_LOG.get())
								{
								    level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.AIR.defaultBlockState(),
								    Block.UPDATE_ALL);
							        System.out.println("there is a peice of dead wood at " + x + y + z);
								}
								if(wood == Blocks.STRIPPED_BIRCH_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.BIRCH_LOG.defaultBlockState(),
								    Block.UPDATE_ALL);
								    System.out.println("there is a peice of dead wood at " + x + y + z);
								}
								if(wood == Blocks.STRIPPED_ACACIA_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.ACACIA_LOG.defaultBlockState(),
								    Block.UPDATE_ALL);
								    System.out.println("there is a peice of dead wood at " + x + y + z);
								}
								if(wood == Blocks.STRIPPED_DARK_OAK_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.STRIPPED_DARK_OAK_LOG.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + x +y+ z);
								}
								if(wood == Blocks.STRIPPED_JUNGLE_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.JUNGLE_LOG.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + x + y + z);
								}
								if(wood == Blocks.STRIPPED_SPRUCE_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.SPRUCE_LOG.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + x + y + z);
								}
							}
							BlockPos blockpos = new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y + 1, entity.getBlockZ() - z);
							if(block == Blocks.GRASS_BLOCK && level.getBlockState(blockpos).getBlock() == Blocks.AIR)
							{
							    if(rnd.nextInt(25) == 0)
							    {
							    	System.out.println("random thing happened ");
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.FOREST).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.MEADOW).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.FLOWER_FOREST).get().unwrap()))
							    	{

							    		
							    		if(rnd.nextInt(4) == 0)
							    		{
							    			level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.BIRCH_SAPLING.defaultBlockState(),
							    			Block.UPDATE_ALL);
							    		}
							    		else
							    		{
							    			level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.OAK_SAPLING.defaultBlockState(),
										    Block.UPDATE_ALL);
							    		}
							    	}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.WINDSWEPT_HILLS).get().unwrap())
									|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.WINDSWEPT_FOREST).get().unwrap())
									|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.WINDSWEPT_GRAVELLY_HILLS).get().unwrap()))
									{
									    System.out.println("you're in a forest ");
									    
									    if(rnd.nextInt(2) == 0)
									    {
									    	level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.SPRUCE_SAPLING.defaultBlockState(),
									    	Block.UPDATE_ALL);
									    }
									    else
									    {
									    	level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.OAK_SAPLING.defaultBlockState(),
											Block.UPDATE_ALL);
									    }
									}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.GROVE).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.TAIGA).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.OLD_GROWTH_PINE_TAIGA).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.OLD_GROWTH_SPRUCE_TAIGA).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SNOWY_TAIGA).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SNOWY_PLAINS).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.FROZEN_RIVER).get().unwrap()))
							    	{
							    		
							    		level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.SPRUCE_SAPLING.defaultBlockState(), Block.UPDATE_ALL);

							    	}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.DARK_FOREST).get().unwrap()))
							    	{
							    		
							    		level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.DARK_OAK_SAPLING.defaultBlockState(), Block.UPDATE_ALL);

							    	}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.BIRCH_FOREST).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.OLD_GROWTH_BIRCH_FOREST).get().unwrap()))
							    	{
							    		
							    		level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.BIRCH_SAPLING.defaultBlockState(), Block.UPDATE_ALL);

							    	}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.JUNGLE).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SPARSE_JUNGLE).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.BAMBOO_JUNGLE).get().unwrap()))
							    	{
							    		
							    		level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.JUNGLE_SAPLING.defaultBlockState(), Block.UPDATE_ALL);

							    	}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SWAMP).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.PLAINS).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SUNFLOWER_PLAINS).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.RIVER).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.WOODED_BADLANDS).get().unwrap()))
							    	{
							    		
							    		level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.OAK_SAPLING.defaultBlockState(), Block.UPDATE_ALL);
							    	}
							    	if(level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SAVANNA).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.SAVANNA_PLATEAU).get().unwrap())
							    	|| level.getBiome(blockpos).unwrap().equals(ForgeRegistries.BIOMES.getHolder(Biomes.WINDSWEPT_SAVANNA).get().unwrap()))
							    	{
							    		
							    		level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() -y + 1, entity.getBlockZ() - z), Blocks.ACACIA_SAPLING.defaultBlockState(), Block.UPDATE_ALL);
							    	}
							    }
							}
						}
					}
				}
			}
		}
	}
	
	
	/*-------------------------------------------------
                  Telos Events and Methods
      -------------------------------------------------

    = = = = = = = = = = = = = = = = = = = = = = = = = = 
    These methods and events are those that apply only
    to the fourth of the five effects, Telos. There are 
    two methods in this section.
    = = = = = = = = = = = = = = = = = = = = = = = = = = 
    
    ___________________________________________________
    method telosSpread(LivingEntity entity)
        Finds blocks that are on the ground and
        replaces them with end stone. Then, it finds
        locations to place chorus trees. This also
        teleports any entities in the area away
        randomly
        
    method spawnGoons(Player source, LivingEntity entity)
        Spawns eight endermen that target the entity
        passed in as "entity." These eight endermen
        are kept in a list. No new endermen are
        spawned unless one dies or is over 200 blocks
        away.
    ___________________________________________________*/
	
	public void telosSpread(LivingEntity entity)
	{
		if(entity.hasEffect(LavaEffects.TELIKOS.get()) && dead == false)
		{
			int limit = 0 ;
			if(entity.getLevel() instanceof ServerLevel)
			{
				ServerLevel level = (ServerLevel) entity.getLevel();
				for(int i = -10; i <= 10; i+= 1)
				{
					for(int x = -10; x <= 5; x+= 1)
					{
						for(int z = -10; z <= 10; z+= 1)
						{
							Block block = level.getBlockState(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z)).getBlock();
							Block blockAbove = level.getBlockState(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x + 1, entity.getBlockZ() - z)).getBlock();
							if (!(block == Blocks.AIR || block == Blocks.FIRE || block == Blocks.SOUL_FIRE || block == Blocks.TALL_GRASS || 
							    block instanceof FlowerBlock || block == Blocks.FERN || block == Blocks.LARGE_FERN || block == Blocks.GRASS || block instanceof RotatedPillarBlock) && 
							    ((blockAbove == Blocks.AIR || blockAbove == Blocks.GRASS || blockAbove instanceof FlowerBlock || blockAbove == Blocks.FERN || blockAbove == Blocks.LARGE_FERN 
										|| blockAbove == Blocks.GRASS)))
							{
								level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.END_STONE.defaultBlockState(), Block.UPDATE_ALL);
							}
							if(block instanceof LeavesBlock || block == Blocks.TALL_GRASS || block == Blocks.GRASS)
							{
								level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.AIR.defaultBlockState(), 
								Block.UPDATE_ALL);
							    System.out.println("there is a leaf or tall grass block at " + i + x + z);
							}
							if(block instanceof RotatedPillarBlock)
							{
								RotatedPillarBlock wood = ((RotatedPillarBlock)block);
								if(wood == Blocks.OAK_LOG)
								{
								    level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.AIR.defaultBlockState(),
								    Block.UPDATE_ALL);
							        System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.BIRCH_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.AIR.defaultBlockState(),
								    Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.DARK_OAK_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.AIR.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.JUNGLE_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.AIR.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.SPRUCE_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.AIR.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
								if(wood == Blocks.ACACIA_LOG)
								{
									level.setBlock(new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x, entity.getBlockZ() - z), Blocks.AIR.defaultBlockState(),
									Block.UPDATE_ALL);
								    System.out.println("there is a peice of wood at " + i + x + z);
								}
							}
							BlockPos blockpos = new BlockPos(entity.getBlockX() - i, entity.getBlockY() - x + 1, entity.getBlockZ() - z);
							if(block == Blocks.END_STONE && level.getBlockState(blockpos).getBlock() == Blocks.AIR)
							{
							    if(rnd.nextInt(50) == 0)
							    {
							    	System.out.println("random thing happened ");
							    	ChorusFlowerBlock.generatePlant(level, blockpos, rnd, 8);
							    }
							}
						}	
					}
				}
				
				for(int i = 0; i < 100; i+= 5)
				{
					for (Entity entities : level.getAllEntities())
					{
						if(!(entities == entity) && entities instanceof LivingEntity)
						{
							LivingEntity target = ((LivingEntity)entities);
							
							if(((!(target.getBlockX() > (entity.getBlockX() + i)) && !(target.getBlockX() < (entity.getBlockX() - i)))
									&& (!(target.getBlockY() > (entity.getBlockY() + i)) && !(target.getBlockY() < (entity.getBlockY() - i)))
									&& (!(target.getBlockZ() > (entity.getBlockZ() + i)) && !(target.getBlockZ() < (entity.getBlockZ() - i)))
									&& limit <= 200) && !men.contains(target))
							{
								if (!level.isClientSide) {
							         double d0 = target.getX();
							         double d1 = target.getY();
							         double d2 = target.getZ();

							         for(int b = 0; b < 16; ++b) {
							            double d3 = target.getX() + (target.getRandom().nextDouble() - 0.5D) * 16.0D;
							            double d4 = Mth.clamp(target.getY() + (double)(target.getRandom().nextInt(16) - 8), (double)level.getMinBuildHeight(), (double)(level.getMinBuildHeight() + ((ServerLevel)level).getLogicalHeight() - 1));
							            double d5 = target.getZ() + (target.getRandom().nextDouble() - 0.5D) * 16.0D;
							            if (target.isPassenger()) {
							               target.stopRiding();
							            }
							            net.minecraftforge.event.entity.EntityTeleportEvent.ChorusFruit event = net.minecraftforge.event.ForgeEventFactory.onChorusFruitTeleport(target, d3, d4, d5);
							            if (target.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
							               SoundEvent soundevent = target instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
							               level.playSound((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
							               target.playSound(soundevent, 1.0F, 1.0F);
							            }
							        }
								}
							}
						}
					}
				}
			}
		}
	}
	
    public void spawnGoons(Player source, LivingEntity entity)
	{
		List<EnderMan> remove = new ArrayList<EnderMan> ();
		for (int e = 0; e < men.size(); e++)
		{
			if (men.get(e).position().distanceTo(source.position()) > 200)
			{
				System.out.println("Enderman is more than 200 blocks away. Teleporting");
				remove.add(men.get(e));
			}
		}
		for (int r = 0; r < remove.size(); r++)
		{
			men.remove(remove.get(r));
			remove.get(r).hurt(DamageSource.GENERIC, 1000000);
		}
		for (int x = -1; x <= 1; x++)
		{
			for (int z = -1; z <= 1; z++)
			{
				if (!(x == 0 && z == 0))
				{
					if (men.size() < 8)
					{
						EnderMan enderman = EntityType.ENDERMAN.create(source.getLevel());
		    		    enderman.setAggressive(true);
		    		    enderman.setTarget(entity);
					    double y = source.getY();
				        while (source.getLevel().getBlockState(new BlockPos(source.getX() + x, y, source.getZ() + z)).getBlock() != Blocks.AIR)
				        {
					        y += 1;
				        }
				        enderman.setPos(source.getX() + x, y, source.getZ() + z);
				        source.getLevel().addFreshEntity(enderman);
				        men.add(0, enderman);
					}
				}
			}
		}
		for (int e = 0; e < men.size(); e++)
		{
			men.get(e).setAggressive(true);
			if (entity != men.get(e))
			{
				men.get(e).setTarget(entity);
			}
		}
	}
	
    
    /*-------------------------------------------------
                 Khamos Events and Methods
      -------------------------------------------------

    = = = = = = = = = = = = = = = = = = = = = = = = = = 
    These methods and events are those that apply only
    to the fourth of the five effects, Khamos. There
    are three events and five methods in this section.
    = = = = = = = = = = = = = = = = = = = = = = = = = = 

    ___________________________________________________
    event onEntityBurnEvent(LivingAttackEvent event)
        -Description Here

    event onFireballImpact(ProjectileImpactEvent event)
        -Description here
        
    event onExplosionHitPlayer(LivingKnockBackEvent event)
        -Description Here
        
    method khamosFlame(LivingEntity entity)
        -Description Here
        
    method khamosGuards(LivingEntity entity)
        -Description Here
        
    method removeKhamosGuards()
        -Description Here
        
    method makeGuardsMad(Player source,
    LivingEntity entity)
        -Description Here
        
    method guardsAttackTarget(LivingEntity entity,
    Blaze blaze)
        -Description Here
    ___________________________________________________*/
   
    
    @SubscribeEvent
	public void onEntityBurnEvent(LivingAttackEvent event)
	{
		CollisionContext collisioncontext = CollisionContext.of(event.getEntityLiving());
		if (event.getEntityLiving().hasEffect(LavaEffects.TELIKOS.get()) && (event.getSource().equals(DamageSource.LAVA) || 
				event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.ON_FIRE) || 
				event.getSource().equals(DamageSource.HOT_FLOOR)) || (event.getSource().equals(DamageSource.FALL) && 
				collisioncontext.isAbove(LiquidBlock.STABLE_SHAPE, event.getEntityLiving().blockPosition(), true)) && ison && swap == 5)
		{
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onFireballImpact(ProjectileImpactEvent event)
	{
		if (fireballs.contains(event.getProjectile()))
		{
			if (player != null)
			{
				if (player.hasEffect(LavaEffects.TELIKOS.get()) && ison && swap == 5)
				{
					velocity = player.getDeltaMovement();
					if ((event.getRayTraceResult() instanceof EntityHitResult result && result.getEntity() instanceof LivingEntity living && guards.contains(living)) || event.getRayTraceResult().distanceTo(player) <= 10)
					{
						event.setCanceled(true);
					}
					else
					{
						Fireball fireball = (Fireball) event.getProjectile();
					    fireball.getLevel().explode(fireball, fireball.getX(), fireball.getY(), fireball.getZ(), 5, Explosion.BlockInteraction.NONE);
					    fireballs.remove(event.getProjectile());
					}
				}
			}
		}
	}

	@SubscribeEvent	
	public void onExplosionHitPlayer(LivingKnockBackEvent event)
	{
		if (event.getEntityLiving().hasEffect(LavaEffects.TELIKOS.get()) && ison && swap == 5)
		{
			System.out.println("Cancelling knockback");
			event.getEntityLiving().setDeltaMovement(0, 0, 0);		
			knockcancel = false;
		}
	}
	
    public void khamosFlame(LivingEntity entity)
	{
		if(entity.hasEffect(LavaEffects.TELIKOS.get()) && dead == false)
		{
			if(entity.getLevel() instanceof ServerLevel)
			{
				ServerLevel level = (ServerLevel) entity.getLevel();
				for(int x = -1; x <= 1; x += 1)
				{
					
					for(int y = 1; y >= 0; y -= 1)
					{
						for(int z = -1; z <= 1; z += 1)
						{
							Block block = level.getBlockState(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z)).getBlock();
							Block blockAbove = level.getBlockState(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y + 1, entity.getBlockZ() - z)).getBlock();
							if (block != Blocks.AIR && block != Blocks.FIRE && block != Blocks.SOUL_FIRE && !(block == Blocks.TALL_GRASS || 
							    block instanceof FlowerBlock || block == Blocks.FERN || block == Blocks.LARGE_FERN || block == Blocks.GRASS) && 
							    ((blockAbove == Blocks.AIR || blockAbove == Blocks.GRASS || blockAbove instanceof FlowerBlock || blockAbove == Blocks.FERN || blockAbove == Blocks.LARGE_FERN 
										|| blockAbove == Blocks.GRASS)))
							{
								level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y, entity.getBlockZ() - z), Blocks.SOUL_SAND.defaultBlockState(), Block.UPDATE_ALL);
								level.setBlock(new BlockPos(entity.getBlockX() - x, entity.getBlockY() - y + 1, entity.getBlockZ() - z), Blocks.SOUL_FIRE.defaultBlockState(), Block.UPDATE_ALL);
								System.out.println("there is a grass block at " + x + y + z);
							}
						}
					}
				}
			}
		}
	}

	public void khamosGuards(LivingEntity entity)
	{
		
		int p = -1;
		for (int x = -2; x <= 2; x += 2)
		{
			for (int z = -2; z <= 2; z += 2)
			{
				if (!(x == 0 && z == 0))
				{
					Blaze guard = EntityType.BLAZE.create(entity.getLevel());
			        guard.setInvulnerable(true);
			        guard.setNoAi(true);
			        p += 1;
					guard.setPos(entity.getX() + x, entity.getY() + 4, entity.getZ() + z);
					entity.getLevel().addFreshEntity(guard);
					guards.add(guard);
					guardpos[p][0] = x;
					guardpos[p][1] = z;
				}
			}
		}
	}

	public void removeKhamosGuards()
	{
		for (int r = 0; r < guards.size(); r++)
		{
			guards.get(r).kill();
		}
		guards.clear();
	}
	
	public void makeGuardsMad(Player source, LivingEntity entity)
	{
		for (int e = 0; e < guards.size(); e++)
		{
			if (!guards.contains(entity) && !entity.hasEffect(LavaEffects.UNDEAD.get()))
			{
				guardtarg = entity;
			}
		}
	}
	
	public void guardsAttackTarget(LivingEntity entity, Blaze blaze)
	{
		double d0 = blaze.distanceToSqr(entity);
		double d1 = entity.getX() - blaze.getX();
        double d2 = entity.getY(0.5D) - blaze.getY(0.5D);
        double d3 = entity.getZ() - blaze.getZ();
        double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;
        if (!blaze.isSilent()) {
           blaze.level.levelEvent((Player)null, 1018, blaze.blockPosition(), 0);
        }

        for(int i = 0; i < 1; ++i) {
           SmallFireball smallfireball = new SmallFireball(blaze.level, blaze, d1 * d4, d2, d3 * d4);
           smallfireball.setPos(smallfireball.getX(), blaze.getY(0.5D) + 0.5D, smallfireball.getZ());
           fireballs.add(smallfireball);
           blaze.level.addFreshEntity(smallfireball);
        }
	}

}