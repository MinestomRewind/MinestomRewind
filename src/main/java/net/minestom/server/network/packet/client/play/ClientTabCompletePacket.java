package net.minestom.server.network.packet.client.play;

import net.minestom.server.network.packet.client.ClientPlayPacket;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.binary.BinaryReader;
import org.jetbrains.annotations.NotNull;

public class ClientTabCompletePacket extends ClientPlayPacket {

    public String text;
    public boolean hasPosition;
    public BlockPosition lookedAtBlock;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.text = reader.readSizedString(Short.MAX_VALUE);
        this.hasPosition = reader.readBoolean();

        if (this.hasPosition) {
            this.lookedAtBlock = reader.readBlockPosition();
        }
    }
}
