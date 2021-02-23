package net.minestom.server.chat;


import com.google.gson.JsonSyntaxException;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * Class used to convert JSON string to proper chat message representation.
 */
public final class ChatParser {

    public static final char COLOR_CHAR = (char) 0xA7; // Represent the character '§'

    /**
     * Converts a simple colored message json (text/color) to a {@link Component}.
     *
     * @param json the json containing the text and color
     * @return a {@link Component} representing the text
     */
    @NotNull
    public static Component toComponent(@NotNull String json) {
        try {
            return Adventure.COMPONENT_SERIALIZER.deserialize(json);
        } catch (JsonSyntaxException e) {
            // Not a json text
            return Component.text(json);
        }
    }
}
