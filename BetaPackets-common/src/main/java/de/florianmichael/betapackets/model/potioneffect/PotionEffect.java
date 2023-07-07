package de.florianmichael.betapackets.model.potioneffect;

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
}
