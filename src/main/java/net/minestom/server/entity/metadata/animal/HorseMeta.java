package net.minestom.server.entity.metadata.animal;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.animal.tameable.OcelotMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HorseMeta extends AnimalMeta {

    private final static byte MASK_INDEX = 16;

    private final static byte TAMED_BIT = 0x02;
    private final static byte SADDLED_BIT = 0x04;
    private final static byte HAS_CHEST_BIT = 0x08;
    private final static byte HAS_BRED_BIT = 0x10;
    private final static byte EATING_BIT = 0x20;
    private final static byte REARING_BIT = 0x40;
    private final static byte MOUTH_OPEN_BIT = (byte)0x80;

    public HorseMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isTamed() {
        return getMaskBit(MASK_INDEX, TAMED_BIT);
    }

    public void setTamed(boolean value) {
        setMaskBit(MASK_INDEX, TAMED_BIT, value);
    }

    public boolean isSaddled() {
        return getMaskBit(MASK_INDEX, SADDLED_BIT);
    }

    public void setSaddled(boolean value) {
        setMaskBit(MASK_INDEX, SADDLED_BIT, value);
    }

    public boolean isHasChest() {
        return getMaskBit(MASK_INDEX, HAS_CHEST_BIT);
    }

    public void setHasChest(boolean value) {
        setMaskBit(MASK_INDEX, HAS_CHEST_BIT, value);
    }

    public boolean isHasBred() {
        return getMaskBit(MASK_INDEX, HAS_BRED_BIT);
    }

    public void setHasBred(boolean value) {
        setMaskBit(MASK_INDEX, HAS_BRED_BIT, value);
    }

    public boolean isEating() {
        return getMaskBit(MASK_INDEX, EATING_BIT);
    }

    public void setEating(boolean value) {
        setMaskBit(MASK_INDEX, EATING_BIT, value);
    }

    public boolean isRearing() {
        return getMaskBit(MASK_INDEX, REARING_BIT);
    }

    public void setRearing(boolean value) {
        setMaskBit(MASK_INDEX, REARING_BIT, value);
    }

    public boolean isMouthOpen() {
        return getMaskBit(MASK_INDEX, MOUTH_OPEN_BIT);
    }

    public void setMouthOpen(boolean value) {
        setMaskBit(MASK_INDEX, MOUTH_OPEN_BIT, value);
    }

    @NotNull
    public Type getType() {
        return Type.VALUES[super.metadata.getIndex((byte) 19, (byte) 0)];
    }

    public void setType(@NotNull Type value) {
        super.metadata.setIndex((byte) 19, Metadata.Byte((byte) value.ordinal()));
    }

    public Variant getVariant() {
        return getVariantFromID(super.metadata.getIndex((byte) 20, 0));
    }

    public void setVariant(Variant variant) {
        super.metadata.setIndex((byte) 20, Metadata.Int(getVariantID(variant.marking, variant.color)));
    }

    public static int getVariantID(@NotNull Marking marking, @NotNull Color color) {
        return (marking.ordinal() << 8) + color.ordinal();
    }

    public static Variant getVariantFromID(int variantID) {
        return new Variant(
                Marking.VALUES[variantID >> 8],
                Color.VALUES[variantID & 0xFF]
        );
    }

    public String getOwner() {
        return super.metadata.getIndex((byte) 21, null);
    }

    public void setOwner(String value) {
        super.metadata.setIndex((byte) 21, Metadata.String(value));
    }

    @NotNull
    public Armor getArmor() {
        return Armor.VALUES[super.metadata.getIndex((byte) 22, 0)];
    }

    public void setArmor(@NotNull Armor value) {
        super.metadata.setIndex((byte) 22, Metadata.Int((byte) value.ordinal()));
    }

    public enum Type {
        HORSE,
        DONKEY,
        MULE,
        ZOMBIE,
        SKELETON;

        private final static Type[] VALUES = values();
    }

    public static class Variant {

        private Marking marking;
        private Color color;

        public Variant(@NotNull Marking marking, @NotNull Color color) {
            this.marking = marking;
            this.color = color;
        }

        @NotNull
        public Marking getMarking() {
            return this.marking;
        }

        public void setMarking(@NotNull Marking marking) {
            this.marking = marking;
        }

        @NotNull
        public Color getColor() {
            return this.color;
        }

        public void setColor(@NotNull Color color) {
            this.color = color;
        }

    }

    public enum Marking {
        NONE,
        WHITE,
        WHITE_FIELD,
        WHITE_DOTS,
        BLACK_DOTS;

        private final static Marking[] VALUES = values();
    }

    public enum Color {
        WHITE,
        CREAMY,
        CHESTNUT,
        BROWN,
        BLACK,
        GRAY,
        DARK_BROWN;

        private final static Color[] VALUES = values();
    }

    public enum Armor {
        NONE,
        IRON,
        GOLD,
        DIAMOND;

        private final static Armor[] VALUES = values();
    }
}
