package net.minestom.server.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a color in a text. You can either use one of the pre-made colors
 * or make your own using RGB. {@link RGBColor#fromRGB(byte, byte, byte)}.
 * <p>
 * Immutable class.
 */
public final class RGBColor {

    public static final RGBColor BLACK = fromRGB((byte) 0, (byte) 0, (byte) 0, "black");
    public static final RGBColor DARK_BLUE = fromRGB((byte) 0, (byte) 0, (byte) 170, "dark_blue");
    public static final RGBColor DARK_GREEN = fromRGB((byte) 0, (byte) 170, (byte) 0, "dark_green");
    public static final RGBColor DARK_CYAN = fromRGB((byte) 0, (byte) 170, (byte) 170, "dark_aqua");
    public static final RGBColor DARK_RED = fromRGB((byte) 170, (byte) 0, (byte) 0, "dark_red");
    public static final RGBColor PURPLE = fromRGB((byte) 170, (byte) 0, (byte) 170, "dark_purple");
    public static final RGBColor GOLD = fromRGB((byte) 255, (byte) 170, (byte) 0, "gold");
    public static final RGBColor GRAY = fromRGB((byte) 170, (byte) 170, (byte) 170, "gray");
    public static final RGBColor DARK_GRAY = fromRGB((byte) 85, (byte) 85, (byte) 85, "dark_gray");
    public static final RGBColor BLUE = fromRGB((byte) 85, (byte) 85, (byte) 255, "blue");
    public static final RGBColor BRIGHT_GREEN = fromRGB((byte) 85, (byte) 255, (byte) 85, "green");
    public static final RGBColor CYAN = fromRGB((byte) 85, (byte) 255, (byte) 255, "aqua");
    public static final RGBColor RED = fromRGB((byte) 255, (byte) 85, (byte) 85, "red");
    public static final RGBColor PINK = fromRGB((byte) 255, (byte) 85, (byte) 255, "light_purple");
    public static final RGBColor YELLOW = fromRGB((byte) 255, (byte) 255, (byte) 85, "yellow");
    public static final RGBColor WHITE = fromRGB((byte) 255, (byte) 255, (byte) 255, "white");

    private final byte red, green, blue;

    private final String codeName;

    private RGBColor(byte r, byte g, byte b, @Nullable String codeName) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.codeName = codeName;
    }

    /**
     * Creates an RGB color.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @return a chat color with the specified RGB color
     */
    @NotNull
    public static RGBColor fromRGB(byte r, byte g, byte b) {
        return fromRGB(r, g, b, null);
    }

    @NotNull
    private static RGBColor fromRGB(byte r, byte g, byte b, String codeName) {
        return new RGBColor(r, g, b, codeName);
    }

    /**
     * Gets the red component of the color.
     *
     * @return the red component of the color
     */
    public byte getRed() {
        return red;
    }

    /**
     * Gets the green component of the color.
     *
     * @return the green component of the color
     */
    public byte getGreen() {
        return green;
    }

    /**
     * Gets the blue component of the color.
     *
     * @return the blue component of the color
     */
    public byte getBlue() {
        return blue;
    }

    /**
     * Gets the code name.
     *
     * @return the color code name, null if not any
     */
    @Nullable
    public String getCodeName() {
        return codeName;
    }
}
