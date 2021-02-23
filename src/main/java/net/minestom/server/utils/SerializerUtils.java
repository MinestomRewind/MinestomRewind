package net.minestom.server.utils;

public final class SerializerUtils {

    private SerializerUtils() {

    }

    public static long positionToLong(int x, int y, int z) {
        return (((long) x & 0x3FFFFFF) << 38) | (((long) y & 0xFFF) << 26) | ((long) z & 0x3FFFFFF);
    }

    public static BlockPosition longToBlockPosition(long value) {
        final int x = (int) (value >> 38);
        final int y = (int) ((value >> 26) & 0XFFF);
        final int z = (int) (value << 38 >> 38);
        return new BlockPosition(x, y, z);
    }

}
