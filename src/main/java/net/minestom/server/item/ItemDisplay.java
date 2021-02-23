package net.minestom.server.item;

import net.kyori.adventure.text.Component;

public class ItemDisplay {

    private Component displayName;
    private Component[] lore;

    public ItemDisplay(Component displayName, Component[] lore) {
        this.displayName = displayName;
        this.lore = lore;
    }

    /**
     * Gets the item display name.
     *
     * @return the item display name
     */
    public Component getDisplayName() {
        return displayName;
    }

    /**
     * Gets the item lore.
     *
     * @return the item lore
     */
    public Component[] getLore() {
        return lore;
    }
}
