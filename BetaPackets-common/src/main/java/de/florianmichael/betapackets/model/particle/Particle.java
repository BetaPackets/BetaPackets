package de.florianmichael.betapackets.model.particle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Particle {

    private int id;
    private ParticleType type;

    private Map<String, Object> parameters = new HashMap<>();
    private Map<String, ParticleParameterType> factory;

    public Particle(int id, ParticleType type, Map<String, ParticleParameterType> factory) {
        this.id = id;
        this.type = type;
        this.factory = factory;
    }

    public Map<String, ParticleParameterType> getFactory() {
        return Collections.unmodifiableMap(factory);
    }

    public int getId() {
        return id;
    }

    public ParticleType getType() {
        return type;
    }
}
