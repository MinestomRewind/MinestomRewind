package net.minestom.server.extras.astar;

import com.google.common.net.InetAddresses;
import io.netty.buffer.ByteBuf;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.utils.binary.BinaryReader;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Support for Astar fallback modern forwarding, based on Velocity forwarding.
 * <p>
 * Can be enabled by simply calling {@link #enable(String)}.
 */
public final class AstarProxy {

    private static final int SUPPORTED_FORWARDING_VERSION = 1;

    private static volatile boolean enabled;
    private static byte[] secret;

    /**
     * Enables astar modern forwarding.
     *
     * @param secret the forwarding secret,
     *               be sure to do not hardcode it in your code but to retrieve it from a file or anywhere else safe
     */
    public static void enable(@NotNull String secret) {
        AstarProxy.enabled = true;
        AstarProxy.secret = secret.getBytes();
    }

    /**
     * Gets if astar modern forwarding is enabled.
     *
     * @return true if astar modern forwarding is enabled
     */
    public static boolean isEnabled() {
        return enabled;
    }

    public static boolean checkIntegrity(@NotNull BinaryReader reader) {
        if (!enabled) {
            return false;
        }

        final byte[] signature = reader.readBytes(32);

        ByteBuf buf = reader.getBuffer();
        final byte[] data = new byte[buf.readableBytes()];
        buf.getBytes(buf.readerIndex(), data);

        try {
            final Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret, "HmacSHA256"));
            final byte[] mySignature = mac.doFinal(data);
            if (!MessageDigest.isEqual(signature, mySignature)) {
                return false;
            }
        } catch (final InvalidKeyException | NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }

        final int version = reader.readVarInt();
        return version == SUPPORTED_FORWARDING_VERSION;
    }

    public static InetAddress readAddress(@NotNull BinaryReader reader) {
        return InetAddresses.forString(reader.readSizedString(Integer.MAX_VALUE));
    }

    public static PlayerSkin readSkin(@NotNull BinaryReader reader) {
        String skinTexture = null;
        String skinSignature = null;

        final int properties = reader.readVarInt();
        for (int i1 = 0; i1 < properties; i1++) {
            final String name = reader.readSizedString(Short.MAX_VALUE);
            final String value = reader.readSizedString(Short.MAX_VALUE);
            final String signature = reader.readBoolean() ? reader.readSizedString(Short.MAX_VALUE) : null;

            if (name.equals("textures")) {
                skinTexture = value;
                skinSignature = signature;
            }
        }

        if (skinTexture != null && skinSignature != null) {
            return new PlayerSkin(skinTexture, skinSignature);
        } else {
            return null;
        }
    }
}
