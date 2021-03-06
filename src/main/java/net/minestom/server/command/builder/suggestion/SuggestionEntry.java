package net.minestom.server.command.builder.suggestion;

import org.jetbrains.annotations.NotNull;

public class SuggestionEntry {

    private final String entry;

    public SuggestionEntry(@NotNull String entry) {
        this.entry = entry;
    }

    @NotNull
    public String getEntry() {
        return entry;
    }
}
