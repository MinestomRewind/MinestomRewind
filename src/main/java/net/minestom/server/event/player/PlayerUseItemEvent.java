package net.minestom.server.event.player;

import net.minestom.server.entity.Player;
import net.minestom.server.event.CancellableEvent;
import net.minestom.server.event.PlayerEvent;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Event when an item is used without clicking on a block.
 */
public class PlayerUseItemEvent extends PlayerEvent implements CancellableEvent {

    private final ItemStack itemStack;

    private boolean cancelled;

    public PlayerUseItemEvent(@NotNull Player player, @NotNull ItemStack itemStack) {
        super(player);
        this.itemStack = itemStack;
    }

    /**
     * Gets the item which have been used.
     *
     * @return the item
     */
    @NotNull
    public ItemStack getItemStack() {
        return itemStack;
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
