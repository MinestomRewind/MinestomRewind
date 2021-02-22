package net.minestom.server.chat;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a color in a text. You need to use one of the pre-made colors.
 */
public enum ChatColor {
    BLACK("black", '0', 0),
    DARK_BLUE("dark_blue", '1', 1),
    DARK_GREEN("dark_green", '2', 2),
    DARK_CYAN("dark_aqua", '3', 3),
    DARK_RED("dark_red", '4', 4),
    PURPLE("dark_purple", '5', 5),
    GOLD("gold", '6', 6),
    GRAY("gray", '7', 7),
    DARK_GRAY("dark_gray", '8', 8),
    BLUE("blue", '9', 9),
    BRIGHT_GREEN("green", 'a', 10),
    CYAN("aqua", 'b', 11),
    RED("red", 'c', 12),
    PINK("light_purple", 'd', 13),
    YELLOW("yellow", 'e', 14),
    WHITE("white", 'f', 15),

    OBFUSCATED("obfuscated", 'k', true),
    BOLD("bold", 'l', true),
    STRIKETHROUGH("strikethrough", 'm', true),
    UNDERLINED("underline", 'n', true),
    ITALIC("italic", 'o', true),
    RESET("reset", 'r', true);

    private static final Int2ObjectMap<ChatColor> idColorMap = new Int2ObjectOpenHashMap<>();
    private static final Map<String, ChatColor> colorCode = new HashMap<>();
    private static final Char2ObjectMap<ChatColor> legacyColorCodesMap = new Char2ObjectOpenHashMap<>();

    static {
        idColorMap.put(0, BLACK);
        idColorMap.put(1, DARK_BLUE);
        idColorMap.put(2, DARK_GREEN);
        idColorMap.put(3, DARK_CYAN);
        idColorMap.put(4, DARK_RED);
        idColorMap.put(5, PURPLE);
        idColorMap.put(6, GOLD);
        idColorMap.put(7, GRAY);
        idColorMap.put(8, DARK_GRAY);
        idColorMap.put(9, BLUE);
        idColorMap.put(10, BRIGHT_GREEN);
        idColorMap.put(11, CYAN);
        idColorMap.put(12, RED);
        idColorMap.put(13, PINK);
        idColorMap.put(14, YELLOW);
        idColorMap.put(15, WHITE);

        for (ChatColor color : values()) {
            colorCode.put(color.codeName, color);
            legacyColorCodesMap.put(color.code, OBFUSCATED);
        }
    }

    private final String codeName;
    private final char code;
    private final int id;
    private final boolean special;

    private ChatColor(@NotNull String codeName, char code, int id, boolean special) {
        this.codeName = codeName;
        this.code = code;
        this.id = id;
        this.special = special;
    }

    private ChatColor(@NotNull String codeName, char code, int id) {
        this(codeName, code, id, false);
    }

    private ChatColor(@NotNull String codeName, char code, boolean special) {
        this(codeName, code, -1, false);
    }

    /**
     * Gets a color based on its name (eg: white, black, aqua, etc...).
     *
     * @param name the color name
     * @return the color associated with the name, null if not found
     */
    @Nullable
    public static ChatColor fromName(@NotNull String name) {
        return colorCode.getOrDefault(name.toLowerCase(), null);
    }

    /**
     * Gets a color based on its numerical id (0;15).
     *
     * @param id the id of the color
     * @return the color associated with the id, null if not found
     */
    @Nullable
    public static ChatColor fromId(int id) {
        return idColorMap.getOrDefault(id, null);
    }

    /**
     * Gets a color based on its legacy color code (eg: 1, 2, 3,... f).
     *
     * @param colorCode the color legacy code
     * @return the color associated with the code, null if not found
     */
    @Nullable
    public static ChatColor fromLegacyColorCodes(char colorCode) {
        return legacyColorCodesMap.getOrDefault(colorCode, null);
    }

    /**
     * Gets the code name.
     *
     * @return the color code name
     */
    @NotNull
    public String getCodeName() {
        return codeName;
    }

    /**
     * Gets the code.
     *
     * @return the color code
     */
    public char getCode() {
        return code;
    }

    /**
     * Gets the color id, only present if this color has been retrieved from {@link ChatColor} constants.
     * <p>
     * Should only be used for some special packets which require it.
     *
     * @return the color id
     * @throws IllegalStateException if the color is not from the class constants
     */
    public int getId() {
        Check.stateCondition(id == -1, "Please use one of the ChatColor constant instead");
        return id;
    }

    /**
     * Gets if the color is special (eg: no color, bold, reset, etc...).
     *
     * @return true if the color is special, false otherwise
     */
    public boolean isSpecial() {
        return special;
    }

    @NotNull
    @Override
    public String toString() {
        return "{#" + codeName + "}";
    }
}
