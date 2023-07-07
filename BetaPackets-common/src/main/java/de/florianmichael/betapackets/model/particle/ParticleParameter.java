package de.florianmichael.betapackets.model.particle;

public class ParticleParameter<T> {

    private final T value;
    private final ParticleParameterType type;

    public ParticleParameter(T value, ParticleParameterType type) {
        this.value = value;
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public ParticleParameterType getType() {
        return type;
    }
}
