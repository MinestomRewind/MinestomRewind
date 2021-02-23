package net.minestom.server.entity;

import net.minestom.server.network.packet.server.play.SpawnObjectPacket;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.NotNull;

public abstract class ObjectEntity extends Entity {

    public ObjectEntity(@NotNull EntityType entityType, @NotNull Position spawnPosition) {
        super(entityType, spawnPosition);
        setGravity(0.02f, 0.04f, 1.96f);
    }

    /**
     * Gets the data of this object entity.
     *
     * @return an object data
     * @see <a href="https://wiki.vg/Object_Data">here</a>
     */
    public abstract int getObjectData();

    @Override
    public void update(long time) {

    }

    @Override
    public void spawn() {

    }

    @Override
    public boolean addViewer(@NotNull Player player) {
        final boolean result = super.addViewer(player);
        if (!result)
            return false;

        final PlayerConnection playerConnection = player.getPlayerConnection();

        SpawnObjectPacket spawnObjectPacket = new SpawnObjectPacket();
        spawnObjectPacket.entityId = getEntityId();
        spawnObjectPacket.type = getEntityType().getProtocolId();
        spawnObjectPacket.position = getPosition();
        spawnObjectPacket.data = getObjectData();
        playerConnection.sendPacket(spawnObjectPacket);
        playerConnection.sendPacket(getVelocityPacket());
        playerConnection.sendPacket(getMetadataPacket());

        return true;
    }

}
