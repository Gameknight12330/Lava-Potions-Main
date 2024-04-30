package com.projectmushroom.lavapotions.screen;

import com.projectmushroom.lavapotions.block.entity.custom.LavaBrewingStationBlockEntity;
import com.projectmushroom.lavapotions.init.BlockInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class LavaBrewingCauldronMenu extends AbstractContainerMenu {
	
	private final LavaBrewingStationBlockEntity blockEntity;
	
	private final Level level;
	
	private final ContainerData data;

	public LavaBrewingCauldronMenu(int containerID, Inventory inv, FriendlyByteBuf extraData) {
		this(containerID, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(3));
	}
	
	public LavaBrewingCauldronMenu(int containerID, Inventory inv, BlockEntity entity, ContainerData data) {
		super(ModMenuTypes.LAVA_BREWING_CAULDRON_MENU.get(), containerID);
		checkContainerSize(inv, 6);
		blockEntity = ((LavaBrewingStationBlockEntity) entity);
		this.level = inv.player.level;
		this.data = data;
		
		addPlayerInventory(inv);
		addPlayerHotbar(inv);
		
		this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 61, 20));
            this.addSlot(new SlotItemHandler(handler, 1, 80, 20));
            this.addSlot(new SlotItemHandler(handler, 2, 99, 20));
            this.addSlot(new SlotItemHandler(handler, 3, 61, 70));
            this.addSlot(new SlotItemHandler(handler, 4, 80, 70));
            this.addSlot(new SlotItemHandler(handler, 5, 99, 70));
        });
		addDataSlots(data);
	}
	
	public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getArrowProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 29; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    
    public int getSteamProgress() {
        int steamProgress = this.data.get(2);
        int maxProgress = 15;
        int progressArrowSize = 34; // This is the height in pixels of your arrow

        return maxProgress != 0 && steamProgress != 0 ? steamProgress * progressArrowSize / maxProgress : 0;
    }
	
	private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 6;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, BlockInit.LAVA_BREWING_CAULDRON.get());
	}
	
	private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 103 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 161));
        }
    }

}
