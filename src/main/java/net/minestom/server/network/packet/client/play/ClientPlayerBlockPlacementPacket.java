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
    public float cursorPositionX, cursorPositionY, cursorPositionZ;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.blockPosition = reader.readBlockPosition();
        this.blockFace = BlockFace.values()[reader.readVarInt()];
        this.item = reader.readSlot();
        this.cursorPositionX = reader.readFloat();
        this.cursorPositionY = reader.readFloat();
        this.cursorPositionZ = reader.readFloat();
    }

}
