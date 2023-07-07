package de.florianmichael.betapackets.model.potioneffect;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class PotionEffectMapping {

    private final Map<Integer, PotionEffect> idToEffect = new HashMap<>();

    public void addPotionEffect(PotionEffect effect) {
        idToEffect.put(effect.getId(), effect);
    }

    public void write(OutputStream out) throws IOException {
        DataOutputStream data = new DataOutputStream(out);
        data.writeByte(idToEffect.size());
        for (PotionEffect potionEffect : idToEffect.values()) {
            data.writeByte((potionEffect.getId() << 4) | (potionEffect.getType().ordinal() & 0xF)); // nibble für halbe dateigröße
        }
    }

}
