package net.minestom.server.item.metadata;

import net.minestom.server.potion.CustomPotionEffect;
import net.minestom.server.potion.PotionType;
import net.minestom.server.registry.Registries;
import net.minestom.server.utils.clone.CloneUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTList;
import org.jglrxavpok.hephaistos.nbt.NBTTypes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Item meta for
 * {@link net.minestom.server.item.Material#POTION},
 * {@link net.minestom.server.item.Material#LINGERING_POTION},
 * {@link net.minestom.server.item.Material#SPLASH_POTION},
 * {@link net.minestom.server.item.Material#TIPPED_ARROW}.
 */
public class PotionMeta extends ItemMeta {

    private PotionType potionType;

    // Not final because of #clone()
    private List<CustomPotionEffect> customPotionEffects = new CopyOnWriteArrayList<>();

    /**
     * Gets the potion type.
     *
     * @return the potion type
     */
    @Nullable
    public PotionType getPotionType() {
        return potionType;
    }

    /**
     * Changes the potion type.
     *
     * @param potionType the new potion type
     */
    public void setPotionType(@Nullable PotionType potionType) {
        this.potionType = potionType;
    }

    /**
     * Get a list of {@link CustomPotionEffect}.
     *
     * @return the custom potion effect list
     */
    @NotNull
    public List<CustomPotionEffect> getCustomPotionEffects() {
        return customPotionEffects;
    }

    @Override
    public boolean hasNbt() {
        return potionType != null ||
                !customPotionEffects.isEmpty();
    }

    @Override
    public boolean isSimilar(@NotNull ItemMeta itemMeta) {
        if (!(itemMeta instanceof PotionMeta))
            return false;
        PotionMeta potionMeta = (PotionMeta) itemMeta;
        return potionMeta.potionType == potionType &&
                potionMeta.customPotionEffects.equals(customPotionEffects);
    }

    @Override
    public void read(@NotNull NBTCompound compound) {
        if (compound.containsKey("Potion")) {
            this.potionType = Registries.getPotionType(compound.getString("Potion"));
        }

        if (compound.containsKey("CustomPotionEffects")) {
            NBTList<NBTCompound> customEffectList = compound.getList("CustomPotionEffects");
            for (NBTCompound potionCompound : customEffectList) {
                final byte id = potionCompound.getAsByte("Id");
                final byte amplifier = potionCompound.getAsByte("Amplifier");
                final int duration = potionCompound.getAsInt("Duration");
                final boolean ambient = potionCompound.getAsByte("Ambient") == 1;
                final boolean showParticles = potionCompound.getAsByte("ShowParticles") == 1;
                final boolean showIcon = potionCompound.getAsByte("ShowIcon") == 1;

                this.customPotionEffects.add(
                        new CustomPotionEffect(id, amplifier, duration, ambient, showParticles, showIcon));
            }
        }
    }

    @Override
    public void write(@NotNull NBTCompound compound) {
        if (potionType != null) {
            compound.setString("Potion", potionType.getNamespaceID());
        }
        if (!customPotionEffects.isEmpty()) {
            NBTList<NBTCompound> potionList = new NBTList<>(NBTTypes.TAG_Compound);

            for (CustomPotionEffect customPotionEffect : customPotionEffects) {
                NBTCompound potionCompound = new NBTCompound();
                potionCompound.setByte("Id", customPotionEffect.getId());
                potionCompound.setByte("Amplifier", customPotionEffect.getAmplifier());
                potionCompound.setInt("Duration", customPotionEffect.getDuration());
                potionCompound.setByte("Ambient", (byte) (customPotionEffect.isAmbient() ? 1 : 0));
                potionCompound.setByte("ShowParticles", (byte) (customPotionEffect.showParticles() ? 1 : 0));
                potionCompound.setByte("ShowIcon", (byte) (customPotionEffect.showIcon() ? 1 : 0));

                potionList.add(potionCompound);
            }

            compound.set("CustomPotionEffects", potionList);
        }

    }

    @NotNull
    @Override
    public ItemMeta clone() {
        PotionMeta potionMeta = (PotionMeta) super.clone();
        potionMeta.potionType = potionType;
        potionMeta.customPotionEffects = CloneUtils.cloneCopyOnWriteArrayList(customPotionEffects);

        return potionMeta;
    }
}
