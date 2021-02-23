package net.minestom.server.network.packet.client.play;

import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.client.ClientPlayPacket;
import net.minestom.server.utils.binary.BinaryReader;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ClientCreativeInventoryActionPacket extends ClientPlayPacket {

    public short slot;
    public ItemStack item;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.slot = reader.readShort();
        System.out.println(this.slot);
        this.item = reader.readSlot();
    }
}
