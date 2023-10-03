package de.florianmichael.betapackets.model.particle;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Particle {

    private int id;
    private ParticleType type;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return id == particle.id && type == particle.type && Objects.equals(factory, particle.factory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, factory);
    }
}
