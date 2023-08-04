package de.florianmichael.betapackets.model.potioneffect;

import java.util.Objects;

public class PotionEffect {

    private int id;
    private PotionEffectType type;

    public PotionEffect(int id, PotionEffectType type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public PotionEffectType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PotionEffect that = (PotionEffect) o;
        return id == that.id && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
