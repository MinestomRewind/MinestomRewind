package net.minestom.server.network.packet.server.play;

import net.minestom.server.event.item.ArmorEquipEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

public class EntityEquipmentPacket implements ServerPacket {

    public int entityId;
    public Slot slot;
    public ItemStack itemStack;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);

        short slotEnum = (short) slot.ordinal();

        writer.writeShort(slotEnum);
        writer.writeItemStack(itemStack);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_EQUIPMENT;
    }

    public enum Slot {
        HAND,
        BOOTS,
        LEGGINGS,
        CHESTPLATE,
        HELMET;

        @NotNull
        public static Slot fromArmorSlot(ArmorEquipEvent.ArmorSlot armorSlot) {
            switch (armorSlot) {
                case HELMET:
                    return HELMET;
                case CHESTPLATE:
                    return CHESTPLATE;
                case LEGGINGS:
                    return LEGGINGS;
                case BOOTS:
                    return BOOTS;
            }
            throw new IllegalStateException("Something weird happened");
        }

    }

}
