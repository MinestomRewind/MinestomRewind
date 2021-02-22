package net.minestom.server.listener;

import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.client.play.ClientSteerVehiclePacket;

public class PlayerVehicleListener {

    public static void steerVehicleListener(ClientSteerVehiclePacket packet, Player player) {
        final byte flags = packet.flags;
        final boolean jump = (flags & 0x1) != 0;
        final boolean unmount = (flags & 0x2) != 0;
        player.refreshVehicleSteer(packet.sideways, packet.forward, jump, unmount);
    }

}
