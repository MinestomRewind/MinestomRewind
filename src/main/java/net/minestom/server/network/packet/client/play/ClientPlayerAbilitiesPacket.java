package net.minestom.server.network.packet.client.play;

import net.minestom.server.network.packet.client.ClientPlayPacket;
import net.minestom.server.utils.binary.BinaryReader;
import org.jetbrains.annotations.NotNull;

public class ClientPlayerAbilitiesPacket extends ClientPlayPacket {

    public byte flags;
    public float flyingSpeed;
    public float walkingSpeed;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.flags = reader.readByte();
        this.flyingSpeed = reader.readFloat();
        this.walkingSpeed = reader.readFloat();
    }
}
