package net.minestom.server.network.packet.client.play;

import net.minestom.server.network.packet.client.ClientPlayPacket;
import net.minestom.server.utils.binary.BinaryReader;
import org.jetbrains.annotations.NotNull;

public class ClientEnchantItemPacket extends ClientPlayPacket {

    public byte windowId;
    public byte enchantment;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.windowId = reader.readByte();
        this.enchantment = reader.readByte();
    }
}
