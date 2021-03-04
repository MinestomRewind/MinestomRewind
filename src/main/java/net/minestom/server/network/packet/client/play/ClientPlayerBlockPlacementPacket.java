package net.minestom.server.network.packet.client.play;

import net.minestom.server.instance.block.BlockFace;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.client.ClientPlayPacket;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.binary.BinaryReader;
import org.jetbrains.annotations.NotNull;

public class ClientPlayerBlockPlacementPacket extends ClientPlayPacket {

    public BlockPosition blockPosition;
    public BlockFace blockFace;
    public ItemStack item;
    public byte cursorPositionX, cursorPositionY, cursorPositionZ;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.blockPosition = reader.readBlockPosition();

        byte blockFaceValue = reader.readByte();
        if (blockFaceValue == -1) {
            this.blockFace = null;
        } else {
            this.blockFace = BlockFace.values()[blockFaceValue];
        }

        this.item = reader.readSlot();
        this.cursorPositionX = reader.readByte();
        this.cursorPositionY = reader.readByte();
        this.cursorPositionZ = reader.readByte();
    }

}
