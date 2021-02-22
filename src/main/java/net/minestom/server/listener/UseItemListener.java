package net.minestom.server.listener;

import net.minestom.server.entity.Player;
import net.minestom.server.event.item.ArmorEquipEvent;
import net.minestom.server.event.player.PlayerItemAnimationEvent;
import net.minestom.server.event.player.PlayerPreEatEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.packet.client.play.ClientPlayerBlockPlacementPacket;

public class UseItemListener {

    public static boolean useItemListener(ClientPlayerBlockPlacementPacket packet, Player player) {
        // TODO(koesie10): Verify
        if (packet.blockPosition.getX() != -1 || packet.blockPosition.getY() != -1 || packet.blockPosition.getZ() != -1 || packet.blockFace != null) {
            return false;
        }

        final PlayerInventory inventory = player.getInventory();
        final ItemStack itemStack = inventory.getItemInHand();
        itemStack.onRightClick(player);
        PlayerUseItemEvent useItemEvent = new PlayerUseItemEvent(player, itemStack);
        player.callEvent(PlayerUseItemEvent.class, useItemEvent);

        final Material material = itemStack.getMaterial();

        // Equip armor with right click
        if (material.isArmor()) {
            final PlayerInventory playerInventory = player.getInventory();
            if (useItemEvent.isCancelled()) {
                playerInventory.update();
                return true;
            }

            final ArmorEquipEvent.ArmorSlot armorSlot;
            if (material.isHelmet()) {
                armorSlot = ArmorEquipEvent.ArmorSlot.HELMET;
            } else if (material.isChestplate()) {
                armorSlot = ArmorEquipEvent.ArmorSlot.CHESTPLATE;
            } else if (material.isLeggings()) {
                armorSlot = ArmorEquipEvent.ArmorSlot.LEGGINGS;
            } else {
                armorSlot = ArmorEquipEvent.ArmorSlot.BOOTS;
            }
            ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(player, itemStack, armorSlot);
            player.callEvent(ArmorEquipEvent.class, armorEquipEvent);
            final ItemStack armorItem = armorEquipEvent.getArmorItem();

            playerInventory.setItemInHand(ItemStack.getAirItem());

            switch (armorSlot) {
                case HELMET:
                    playerInventory.setHelmet(armorItem);
                    break;
                case CHESTPLATE:
                    playerInventory.setChestplate(armorItem);
                    break;
                case LEGGINGS:
                    playerInventory.setLeggings(armorItem);
                    break;
                case BOOTS:
                    playerInventory.setBoots(armorItem);
                    break;
            }
        }

        PlayerItemAnimationEvent.ItemAnimationType itemAnimationType = null;
        boolean riptideSpinAttack = false;

        if (material == Material.BOW) {
            itemAnimationType = PlayerItemAnimationEvent.ItemAnimationType.BOW;
        } else if (material.isFood()) {
            itemAnimationType = PlayerItemAnimationEvent.ItemAnimationType.EAT;

            // Eating code, contains the eating time customisation
            PlayerPreEatEvent playerPreEatEvent = new PlayerPreEatEvent(player, itemStack, player.getDefaultEatingTime());
            player.callCancellableEvent(PlayerPreEatEvent.class, playerPreEatEvent, () -> player.refreshEating(true, playerPreEatEvent.getEatingTime()));
        }

        if (itemAnimationType != null) {
            PlayerItemAnimationEvent playerItemAnimationEvent = new PlayerItemAnimationEvent(player, itemAnimationType);
            player.callCancellableEvent(PlayerItemAnimationEvent.class, playerItemAnimationEvent, () -> {
                player.sendPacketToViewers(player.getMetadataPacket());
            });
        }

        return true;
    }

}
