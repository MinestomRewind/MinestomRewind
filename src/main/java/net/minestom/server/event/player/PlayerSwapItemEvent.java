package net.minestom.server.event.player;

import net.minestom.server.entity.Player;
import net.minestom.server.event.CancellableEvent;
import net.minestom.server.event.PlayerEvent;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player is trying to swap his main and off hand item.
 */
public class PlayerSwapItemEvent extends PlayerEvent implements CancellableEvent {

    private ItemStack handItem;

    private boolean cancelled;

    public PlayerSwapItemEvent(@NotNull Player player, @NotNull ItemStack handItem) {
        super(player);
        this.handItem = handItem;
    }

    /**
     * Gets the item which will be in player main hand after the event.
     *
     * @return the item in main hand
     */
    @NotNull
    public ItemStack getHandItem() {
        return handItem;
    }

    /**
     * Changes the item which will be in the player main hand.
     *
     * @param handItem the main hand item
     */
    public void setHandItem(@NotNull ItemStack handItem) {
        this.handItem = handItem;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
