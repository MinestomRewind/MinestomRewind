package net.minestom.server.network.packet.client.play;

import net.minestom.server.network.packet.client.ClientPlayPacket;
import net.minestom.server.resourcepack.ResourcePackStatus;
import net.minestom.server.utils.binary.BinaryReader;
import org.jetbrains.annotations.NotNull;

public class ClientResourcePackStatusPacket extends ClientPlayPacket {

    public String hash;
    public ResourcePackStatus result;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.hash = reader.readSizedString(40);
        this.result = ResourcePackStatus.values()[reader.readVarInt()];
    }

}
