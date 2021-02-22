package net.minestom.server.inventory;

import net.minestom.server.Viewable;
import net.minestom.server.entity.Entity;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.server.play.EntityEquipmentPacket;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an {@link Entity} which can have {@link ItemStack} in hands and armor slots.
 */
public interface EquipmentHandler {

    /**
     * Gets the {@link ItemStack} in main hand.
     *
     * @return the {@link ItemStack} in main hand
     */
    @NotNull
    ItemStack getItemInHand();

    /**
     * Changes the main hand {@link ItemStack}.
     *
     * @param itemStack the main hand {@link ItemStack}
     */
    void setItemInHand(@NotNull ItemStack itemStack);

    /**
     * Gets the helmet.
     *
     * @return the helmet
     */
    @NotNull
    ItemStack getHelmet();

    /**
     * Changes the helmet.
     *
     * @param itemStack the helmet
     */
    void setHelmet(@NotNull ItemStack itemStack);

    /**
     * Gets the chestplate.
     *
     * @return the chestplate
     */
    @NotNull
    ItemStack getChestplate();

    /**
     * Changes the chestplate.
     *
     * @param itemStack the chestplate
     */
    void setChestplate(@NotNull ItemStack itemStack);

    /**
     * Gets the leggings.
     *
     * @return the leggings
     */
    @NotNull
    ItemStack getLeggings();

    /**
     * Changes the leggings.
     *
     * @param itemStack the leggings
     */
    void setLeggings(@NotNull ItemStack itemStack);

    /**
     * Gets the boots.
     *
     * @return the boots
     */
    @NotNull
    ItemStack getBoots();

    /**
     * Changes the boots.
     *
     * @param itemStack the boots
     */
    void setBoots(@NotNull ItemStack itemStack);

    /**
     * Gets the equipment in a specific slot.
     *
     * @param slot the equipment to get the item from
     * @return the equipment {@link ItemStack}
     */
    @NotNull
    default ItemStack getEquipment(@NotNull EntityEquipmentPacket.Slot slot) {
        switch (slot) {
            case HAND:
                return getItemInHand();
            case HELMET:
                return getHelmet();
            case CHESTPLATE:
                return getChestplate();
            case LEGGINGS:
                return getLeggings();
            case BOOTS:
                return getBoots();
        }
        throw new IllegalStateException("Something weird happened");
    }

    /**
     * Sends all the equipments to a {@link PlayerConnection}.
     *
     * @param connection the connection to send the equipments to
     */
    default void syncEquipments(@NotNull PlayerConnection connection) {
        connection.sendPacket(getEquipmentsPacket());
    }

    /**
     * Sends all the equipments to all viewers.
     */
    default void syncEquipments() {
        Check.stateCondition(!(this instanceof Viewable), "Only accessible for Entity");

        Viewable viewable = (Viewable) this;
        viewable.sendPacketToViewersAndSelf(getEquipmentsPacket());
    }

    /**
     * Sends a specific equipment to viewers.
     *
     * @param slot the slot of the equipment
     */
    default void syncEquipment(@NotNull EntityEquipmentPacket.Slot slot) {
        Check.stateCondition(!(this instanceof Entity), "Only accessible for Entity");

        Entity entity = (Entity) this;

        final ItemStack itemStack = getEquipment(slot);

        EntityEquipmentPacket entityEquipmentPacket = new EntityEquipmentPacket();
        entityEquipmentPacket.entityId = entity.getEntityId();
        entityEquipmentPacket.slots = new EntityEquipmentPacket.Slot[]{slot};
        entityEquipmentPacket.itemStacks = new ItemStack[]{itemStack};

        entity.sendPacketToViewers(entityEquipmentPacket);
    }

    /**
     * Gets the packet with all the equipments.
     *
     * @return the packet with the equipments
     * @throws IllegalStateException if 'this' is not an {@link Entity}
     */
    @NotNull
    default EntityEquipmentPacket getEquipmentsPacket() {
        Check.stateCondition(!(this instanceof Entity), "Only accessible for Entity");

        final Entity entity = (Entity) this;

        final EntityEquipmentPacket.Slot[] slots = EntityEquipmentPacket.Slot.values();

        List<ItemStack> itemStacks = new ArrayList<>(slots.length);

        // Fill items
        for (EntityEquipmentPacket.Slot slot : slots) {
            final ItemStack equipment = getEquipment(slot);
            itemStacks.add(equipment);
        }

        // Create equipment packet
        EntityEquipmentPacket equipmentPacket = new EntityEquipmentPacket();
        equipmentPacket.entityId = entity.getEntityId();
        equipmentPacket.slots = slots;
        equipmentPacket.itemStacks = itemStacks.toArray(new ItemStack[0]);
        return equipmentPacket;
    }

}
