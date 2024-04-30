package com.projectmushroom.lavapotions.block.entity.custom;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.projectmushroom.lavapotions.block.entity.ModBlockEntities;
import com.projectmushroom.lavapotions.init.ItemInit;
import com.projectmushroom.lavapotions.recipe.LavaBrewingCauldronRecipe;
import com.projectmushroom.lavapotions.screen.LavaBrewingCauldronMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class LavaBrewingStationBlockEntity extends BlockEntity implements MenuProvider {
	private final ItemStackHandler itemHandler = new ItemStackHandler(6) {
        
		@Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private int steamProgress = 0;


    public LavaBrewingStationBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.LAVA_BREWING_CAULDRON_ENTITY.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
            	int retVal = 0;
                switch (index) {
                    case 0: retVal = LavaBrewingStationBlockEntity.this.progress;
                    case 1: retVal = LavaBrewingStationBlockEntity.this.maxProgress;
                    case 2: retVal = LavaBrewingStationBlockEntity.this.steamProgress;
                    default: retVal = 0;
                }
                return retVal;
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: LavaBrewingStationBlockEntity.this.progress = value; break;
                    case 1: LavaBrewingStationBlockEntity.this.maxProgress = value; break;
                    case 2: LavaBrewingStationBlockEntity.this.steamProgress = value; break;
                }
            }

            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Lava Brewing Cauldron");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new LavaBrewingCauldronMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("lava_brewing_cauldron.progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("lava_brewing_cauldron.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, LavaBrewingStationBlockEntity pBlockEntity) {
    	if(hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            pBlockEntity.steamProgress++;
            pBlockEntity.steamProgress %= 15;
            setChanged(pLevel, pPos, pState);
            if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }
    
    private static void craftItem(LavaBrewingStationBlockEntity entity) {
    	Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<LavaBrewingCauldronRecipe> match = level.getRecipeManager()
                .getRecipeFor(LavaBrewingCauldronRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(3,1, false);
            entity.itemHandler.extractItem(4,1, false);
            entity.itemHandler.extractItem(5,1, false);

            for (int i = 0; i < 3; i++)
            {
                if (entity.itemHandler.getStackInSlot(i).getItem() == ItemInit.LAVA_BOTTLE.get())
                {
                	entity.itemHandler.setStackInSlot(i, new ItemStack(match.get().getResultItem().getItem()));
                }
            }

            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(LavaBrewingStationBlockEntity entity) {
    	Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<LavaBrewingCauldronRecipe> match = level.getRecipeManager()
                .getRecipeFor(LavaBrewingCauldronRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && (hasLavaBottleInSlot1(entity) || hasLavaBottleInSlot2(entity)
                || hasLavaBottleInSlot3(entity));
    }

    private static boolean hasLavaBottleInSlot1(LavaBrewingStationBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(0).getItem() == ItemInit.LAVA_BOTTLE.get();
    }
    
    private static boolean hasLavaBottleInSlot2(LavaBrewingStationBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(1).getItem() == ItemInit.LAVA_BOTTLE.get();
    }
    
    private static boolean hasLavaBottleInSlot3(LavaBrewingStationBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(2).getItem() == ItemInit.LAVA_BOTTLE.get();
    }
    
    private void resetProgress() {
        this.progress = 0;
    }

}
