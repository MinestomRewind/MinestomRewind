package net.minestom.server.entity.type.decoration;

import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.ObjectEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.item.ArmorEquipEvent;
import net.minestom.server.inventory.EquipmentHandler;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.server.play.EntityEquipmentPacket;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;
import net.minestom.server.utils.binary.BitmaskUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @deprecated Use {@link net.minestom.server.entity.metadata.other.ArmorStandMeta} instead.
 */
@Deprecated
public class EntityArmorStand extends ObjectEntity implements EquipmentHandler {

    // Equipments
    private ItemStack mainHandItem;

    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;

    public EntityArmorStand(Position spawnPosition) {
        super(EntityType.ARMOR_STAND, spawnPosition);

        // Refresh BoundingBox
        setSmall(false);

        setHeadRotation(new Vector(0, 0, 0));
        setBodyRotation(new Vector(0, 0, 0));
        setLeftArmRotation(new Vector(-10f, 0, -10f));
        setRightArmRotation(new Vector(-15f, 0, -10f));
        setLeftLegRotation(new Vector(-1f, 0, -1f));
        setRightLegRotation(new Vector(1, 0, 1));

        this.mainHandItem = ItemStack.getAirItem();

        this.helmet = ItemStack.getAirItem();
        this.chestplate = ItemStack.getAirItem();
        this.leggings = ItemStack.getAirItem();
        this.boots = ItemStack.getAirItem();

    }

    @Override
    public boolean addViewer0(@NotNull Player player) {
        if (!super.addViewer0(player)) {
            return false;
        }
        for (EntityEquipmentPacket entityEquipmentPacket : getEquipmentsPacket()) {
            player.getPlayerConnection().sendPacket(entityEquipmentPacket);
        }
        return true;
    }

    @Override
    public int getObjectData() {
        return 0;
    }

    @NotNull
    @Override
    public ItemStack getItemInHand() {
        return mainHandItem;
    }

    @Override
    public void setItemInHand(@NotNull ItemStack itemStack) {
        this.mainHandItem = itemStack;
        syncEquipment(EntityEquipmentPacket.Slot.HAND);
    }

    @NotNull
    @Override
    public ItemStack getHelmet() {
        return helmet;
    }

    @Override
    public void setHelmet(@NotNull ItemStack itemStack) {
        this.helmet = getEquipmentItem(itemStack, ArmorEquipEvent.ArmorSlot.HELMET);
        syncEquipment(EntityEquipmentPacket.Slot.HELMET);
    }

    @NotNull
    @Override
    public ItemStack getChestplate() {
        return chestplate;
    }

    @Override
    public void setChestplate(@NotNull ItemStack itemStack) {
        this.chestplate = getEquipmentItem(itemStack, ArmorEquipEvent.ArmorSlot.CHESTPLATE);
        syncEquipment(EntityEquipmentPacket.Slot.CHESTPLATE);
    }

    @NotNull
    @Override
    public ItemStack getLeggings() {
        return leggings;
    }

    @Override
    public void setLeggings(@NotNull ItemStack itemStack) {
        this.leggings = getEquipmentItem(itemStack, ArmorEquipEvent.ArmorSlot.LEGGINGS);
        syncEquipment(EntityEquipmentPacket.Slot.LEGGINGS);
    }

    @NotNull
    @Override
    public ItemStack getBoots() {
        return boots;
    }

    @Override
    public void setBoots(@NotNull ItemStack itemStack) {
        this.boots = getEquipmentItem(itemStack, ArmorEquipEvent.ArmorSlot.BOOTS);
        syncEquipment(EntityEquipmentPacket.Slot.BOOTS);
    }

    public boolean isSmall() {
        return (getStateMeta() & 0x01) != 0;
    }

    public void setSmall(boolean small) {
        final byte state = BitmaskUtil.changeBit(getStateMeta(), (byte) 0x01, (byte) (small ? 1 : 0), (byte) 0);
        this.metadata.setIndex((byte) 10, Metadata.Byte(state));

        if (small) {
            setBoundingBox(0.25f, 0.9875f, 0.25f);
        } else {
            setBoundingBox(0.5f, 1.975f, 0.5f);
        }
    }

    public boolean hasGravity() {
        return (getStateMeta() & 0x02) != 0;
    }

    public void setHasGravity(boolean hasGravity) {
        final byte state = BitmaskUtil.changeBit(getStateMeta(), (byte) 0x02, (byte) (hasGravity ? 1 : 0), (byte) 2);
        this.metadata.setIndex((byte) 10, Metadata.Byte(state));
    }

    @Override
    public boolean hasNoGravity() {
        return !hasGravity();
    }

    public void setNoGravity(boolean hasNoGravity) {
        setHasGravity(!hasNoGravity);
    }

    public boolean hasArms() {
        return (getStateMeta() & 0x04) != 0;
    }

    public void setHasArms(boolean hasArms) {
        final byte state = BitmaskUtil.changeBit(getStateMeta(), (byte) 0x04, (byte) (hasArms ? 1 : 0), (byte) 2);
        this.metadata.setIndex((byte) 10, Metadata.Byte(state));
    }

    public boolean hasNoBasePlate() {
        return (getStateMeta() & 0x08) != 0;
    }

    public void setNoBasePlate(boolean noBasePlate) {
        final byte state = BitmaskUtil.changeBit(getStateMeta(), (byte) 0x08, (byte) (noBasePlate ? 1 : 0), (byte) 3);
        this.metadata.setIndex((byte) 10, Metadata.Byte(state));
    }

    public boolean hasMarker() {
        return (getStateMeta() & 0x10) != 0;
    }

    public void setMarker(boolean setMarker) {
        final byte state = BitmaskUtil.changeBit(getStateMeta(), (byte) 0x10, (byte) (setMarker ? 1 : 0), (byte) 4);
        this.metadata.setIndex((byte) 10, Metadata.Byte(state));
    }

    @NotNull
    public Vector getHeadRotation() {
        return metadata.getIndex((byte) 11, new Vector(0, 0, 0));
    }

    public void setHeadRotation(@NotNull Vector headRotation) {
        this.metadata.setIndex((byte) 11, Metadata.Vector(headRotation));
    }

    @NotNull
    public Vector getBodyRotation() {
        return metadata.getIndex((byte) 12, new Vector(0, 0, 0));
    }

    public void setBodyRotation(@NotNull Vector bodyRotation) {
        this.metadata.setIndex((byte) 12, Metadata.Vector(bodyRotation));
    }

    @NotNull
    public Vector getLeftArmRotation() {
        return metadata.getIndex((byte) 13, new Vector(-10, 0, -10));
    }

    public void setLeftArmRotation(@NotNull Vector leftArmRotation) {
        this.metadata.setIndex((byte) 13, Metadata.Vector(leftArmRotation));
    }

    @NotNull
    public Vector getRightArmRotation() {
        return metadata.getIndex((byte) 14, new Vector(-15, 0, 10));
    }

    public void setRightArmRotation(@NotNull Vector rightArmRotation) {
        this.metadata.setIndex((byte) 14, Metadata.Vector(rightArmRotation));
    }

    @NotNull
    public Vector getLeftLegRotation() {
        return metadata.getIndex((byte) 15, new Vector(-1, 0, -1));
    }

    public void setLeftLegRotation(@NotNull Vector leftLegRotation) {
        this.metadata.setIndex((byte) 15, Metadata.Vector(leftLegRotation));
    }

    @NotNull
    public Vector getRightLegRotation() {
        return metadata.getIndex((byte) 16, new Vector(1, 0, 1));
    }

    public void setRightLegRotation(@NotNull Vector rightLegRotation) {
        this.metadata.setIndex((byte) 16, Metadata.Vector(rightLegRotation));
    }

    private byte getStateMeta() {
        return metadata.getIndex((byte) 10, (byte) 0);
    }

    // Equipments

    private ItemStack getEquipmentItem(@NotNull ItemStack itemStack, @NotNull ArmorEquipEvent.ArmorSlot armorSlot) {
        ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(this, itemStack, armorSlot);
        callEvent(ArmorEquipEvent.class, armorEquipEvent);
        return armorEquipEvent.getArmorItem();
    }
}
