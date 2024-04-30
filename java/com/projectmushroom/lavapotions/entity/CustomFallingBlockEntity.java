package com.projectmushroom.lavapotions.entity;

import java.util.function.Predicate;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.projectmushroom.lavapotions.init.EntityInit;

import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class CustomFallingBlockEntity extends FallingBlockEntity {
	
	public static final Logger LOGGER = LogUtils.getLogger();
	  
	public int time;
	  
	public boolean dropitem = false;
	  
	public boolean canceldrop;
	  
	public boolean hurtentities;
	  
	public int falldamagemax = 40;
	  
	public float falldamageperdistance;
	
	public CompoundTag blockdata;
	  
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(CustomFallingBlockEntity.class, EntityDataSerializers.BLOCK_POS);
	
	private BlockState blockstate = Blocks.SAND.defaultBlockState();
	
    public CustomFallingBlockEntity(EntityType<? extends CustomFallingBlockEntity> type, Level world) 
    {
        super(type, world);
    }
	
	public CustomFallingBlockEntity(Level level, double x, double y, double z, BlockState state) 
	{
	    this(EntityInit.CUSTOM_FALLING_BLOCK_ENTITY.get(), level);
	    this.blockstate = state;
	    this.blocksBuilding = true;
	    this.setPos(x, y, z);
	    this.setDeltaMovement(Vec3.ZERO);
	    this.xo = x;
	    this.yo = y;
	    this.zo = z;
	    this.setStartPos(this.blockPosition());
	}
	
	public static CustomFallingBlockEntity fall(Level level, BlockPos pos, BlockState state) 
	{
	    CustomFallingBlockEntity fallingblockentity = new CustomFallingBlockEntity(level, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, state.hasProperty(BlockStateProperties.WATERLOGGED) ? state.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)) : state);
	    level.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);
	    level.addFreshEntity((Entity)fallingblockentity);
	    return fallingblockentity;
    }
	  
	public boolean isAttackable() 
	{
	    return false;
	}
	  
	public void setStartPos(BlockPos pos) 
	{
	    this.entityData.set(DATA_START_POS, pos);
	}
	  
	public BlockPos getStartPos() 
	{
	    return (BlockPos)this.entityData.get(DATA_START_POS);
	}
	  
	protected Entity.MovementEmission getMovementEmission() 
	{
	    return Entity.MovementEmission.NONE;
	}
	  
	protected void defineSynchedData() 
	{
	    this.entityData.define(DATA_START_POS, BlockPos.ZERO);
	}
	  
	public boolean isPickable() 
	{
	    return !isRemoved();
	}
	  
	public void tick() 
	{
	    if (this.blockstate.isAir()) 
	    {
	        discard();
	    } 
	    else 
	    {
	        Block block = this.blockstate.getBlock();
	        this.time++;
	        if (!isNoGravity())
	        {
	            setDeltaMovement(getDeltaMovement().add(0.0D, -0.04D, 0.0D)); 
	        }
	        move(MoverType.SELF, getDeltaMovement());
	        if (!this.level.isClientSide) 
	        {
	            BlockPos blockpos = blockPosition();
	            boolean flag = this.blockstate.getBlock() instanceof net.minecraft.world.level.block.ConcretePowderBlock;
	            boolean flag1 = (flag && this.level.getFluidState(blockpos).is(FluidTags.WATER));
	            double d0 = getDeltaMovement().lengthSqr();
	            if (flag && d0 > 1.0D) 
	            {
	                BlockHitResult blockhitresult = this.level.clip(new ClipContext(new Vec3(this.xo, this.yo, this.zo), position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, (Entity)this));
	                if (blockhitresult.getType() != HitResult.Type.MISS && this.level.getFluidState(blockhitresult.getBlockPos()).is(FluidTags.WATER)) 
	                {
	                    blockpos = blockhitresult.getBlockPos();
	                    flag1 = true;
	                } 
	            } 
	            if (!this.onGround && !flag1) 
	            {
	                if (!this.level.isClientSide && ((this.time > 100 && (blockpos.getY() <= this.level.getMinBuildHeight() || blockpos.getY() > this.level.getMaxBuildHeight())) || this.time > 600)) 
	                {
	                    if (this.dropitem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS))
	                    {
	                        spawnAtLocation((ItemLike)block); 
	                    } 
	                    discard();
	                } 
	            } 
	            else 
	            {
	                BlockState blockstate = this.level.getBlockState(blockpos);
	                setDeltaMovement(getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
	                if (!blockstate.is(Blocks.MOVING_PISTON))
	                {
	                    if (!this.canceldrop) 
	                    {
	                        boolean flag2 = blockstate.canBeReplaced((BlockPlaceContext)new DirectionalPlaceContext(this.level, blockpos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
	                        boolean flag3 = (FallingBlock.isFree(this.level.getBlockState(blockpos.below())) && (!flag || !flag1));
	                        boolean flag4 = (this.blockstate.canSurvive((LevelReader)this.level, blockpos) && !flag3);
	                        if (flag2 && flag4) 
	                        {
	                            if (this.blockstate.hasProperty((Property)BlockStateProperties.WATERLOGGED) && this.level.getFluidState(blockpos).getType() == Fluids.WATER)
	                            {
	                                this.blockstate = (BlockState)this.blockstate.setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)); 
	                            }
	                            if (this.level.setBlock(blockpos, this.blockstate, 3)) 
	                            {
	                                (((ServerLevel)this.level).getChunkSource()).chunkMap.broadcast((Entity)this, (Packet)new ClientboundBlockUpdatePacket(blockpos, this.level.getBlockState(blockpos)));
	                                discard();
	                                if (block instanceof Fallable)
	                                {
	                                    ((Fallable)block).onLand(this.level, blockpos, this.blockstate, blockstate, this); 
	                                }
	                                if (this.blockdata != null && this.blockstate.hasBlockEntity()) 
	                                {
	                                    BlockEntity blockentity = this.level.getBlockEntity(blockpos);
	                                    if (blockentity != null) 
	                                    {
	                                        CompoundTag compoundtag = blockentity.saveWithoutMetadata();
	                                        for (String s : this.blockdata.getAllKeys())
	                                        {
	                                            compoundtag.put(s, this.blockdata.get(s).copy()); 
	                                        }
	                                        try {
	                                            blockentity.load(compoundtag);
	                                        } catch (Exception exception) {
	                                            LOGGER.error("Failed to load block entity from falling block", exception);
	                                        } 
	                                        blockentity.setChanged();
	                                    } 
	                                } 
	                            } 
	                            else if (this.dropitem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) 
	                            {
	                                discard();
	                                callOnBrokenAfterFall(block, blockpos);
	                                spawnAtLocation((ItemLike)block);
	                            } 
	                        } 
	                        else 
	                        {
	                            discard();
	                            if (this.dropitem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) 
	                            {
	                                callOnBrokenAfterFall(block, blockpos);
	                                spawnAtLocation((ItemLike)block);
	                            } 
	                        } 
	                    } 
	                    else 
	                    {
	                        discard();
	                        callOnBrokenAfterFall(block, blockpos);
	                    }  
	                } 
	            } 
	            setDeltaMovement(getDeltaMovement().scale(0.98D));
	        } 
	    }
	}
	  
	public void callOnBrokenAfterFall(Block block, BlockPos pos) {
	    if (block instanceof Fallable)
	    {
	        ((Fallable)block).onBrokenAfterFall(this.level, pos, this);
	    }
	}
	  
	public boolean causeFallDamage(float p_149643_, float p_149644_, DamageSource p_149645_) {
	    Predicate<Entity> predicate;
	    DamageSource damagesource;
	    if (!this.hurtentities)
	    {
	        return false; 
	    }
	    int i = Mth.ceil(p_149643_ - 1.0F);
	    if (i < 0)
	    {
	        return false; 
	    }
	    if (this.blockstate.getBlock() instanceof Fallable) 
	    {
	        Fallable fallable = (Fallable)this.blockstate.getBlock();
	        predicate = fallable.getHurtsEntitySelector();
	        damagesource = fallable.getFallDamageSource();
	    } 
	    else 
	    {
	        predicate = EntitySelector.NO_SPECTATORS;
	        damagesource = DamageSource.FALLING_BLOCK;
	    } 
	    float f = Math.min(Mth.floor(i * this.falldamageperdistance), this.falldamagemax);
	    this.level.getEntities((Entity)this, getBoundingBox(), predicate).forEach(p_149649_ -> p_149649_.hurt(damagesource, f));
	    boolean flag = this.blockstate.is(BlockTags.ANVIL);
	    if (flag && f > 0.0F && this.random.nextFloat() < 0.05F + i * 0.05F) 
	    {
	        BlockState blockstate = AnvilBlock.damage(this.blockstate);
	        if (blockstate == null) 
	        {
	            this.canceldrop = true;
	        } 
	        else 
	        {
	            this.blockstate = blockstate;
	        } 
	    } 
	    return false;
	}
	  
	protected void addAdditionalSaveData(CompoundTag tag) {
	    tag.put("BlockState", (Tag)NbtUtils.writeBlockState(this.blockstate));
	    tag.putInt("Time", this.time);
	    tag.putBoolean("DropItem", this.dropitem);
	    tag.putBoolean("HurtEntities", this.hurtentities);
	    tag.putFloat("FallHurtAmount", this.falldamageperdistance);
	    tag.putInt("FallHurtMax", this.falldamagemax);
	    if (this.blockdata != null)
	    {
	        tag.put("TileEntityData", (Tag)this.blockdata);
	    }
	}
	  
	protected void readAdditionalSaveData(CompoundTag tag) {
	    this.blockstate = NbtUtils.readBlockState(tag.getCompound("BlockState"));
	    this.time = tag.getInt("Time");
	    if (tag.contains("HurtEntities", 99)) 
	    {
	        this.hurtentities = tag.getBoolean("HurtEntities");
	        this.falldamageperdistance = tag.getFloat("FallHurtAmount");
	        this.falldamagemax = tag.getInt("FallHurtMax");
	    } 
	    else if (this.blockstate.is(BlockTags.ANVIL)) 
	    {
	        this.hurtentities = true;
	    } 
	    if (tag.contains("DropItem", 99))
	    {
	        this.dropitem = tag.getBoolean("DropItem"); 
	    }
	    if (tag.contains("TileEntityData", 10))
	    {
	        this.blockdata = tag.getCompound("TileEntityData"); 
	    }
	    if (this.blockstate.isAir())
	    {
	        this.blockstate = Blocks.SAND.defaultBlockState(); 
	    }
	}
	  
	public void setHurtsEntities(float fdpd, int fdm) {
	    this.hurtentities = true;
	    this.falldamageperdistance = fdpd;
	    this.falldamagemax = fdm;
	}
	  
	public boolean displayFireAnimation() {
	    return false;
	}
	  
	public void fillCrashReportCategory(CrashReportCategory crash) {
	    super.fillCrashReportCategory(crash);
	    crash.setDetail("Immitating BlockState", this.blockstate.toString());
	}
	  
	public BlockState getBlockState() {
	    return this.blockstate;
	}
	  
    public boolean onlyOpCanSetNbt() {
	    return true;
	}
	  
	public Packet<?> getAddEntityPacket() {
	    return (Packet<?>)new ClientboundAddEntityPacket((Entity)this, Block.getId(getBlockState()));
	}
	  
	public void recreateFromPacket(ClientboundAddEntityPacket packet) {
	    super.recreateFromPacket(packet);
	    this.blockstate = Block.stateById(packet.getData());
	    this.blocksBuilding = true;
	    double d0 = packet.getX();
	    double d1 = packet.getY();
	    double d2 = packet.getZ();
	    setPos(d0, d1, d2);
	    setStartPos(blockPosition());
	}
}


