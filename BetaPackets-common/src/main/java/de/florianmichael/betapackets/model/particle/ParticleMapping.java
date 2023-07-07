package de.florianmichael.betapackets.model.particle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public class ParticleMapping {

    private Map<Integer, Particle> particles = new HashMap<>();

    public void addParticle(Particle particle) {
        particles.put(particle.getId(), particle);
    }

    public void read(InputStream in) throws IOException {
        DataInputStream data = new DataInputStream(in);
        int particleAmount = data.readByte();
        for (int i = 0; i < particleAmount; i++) {
            int id = data.readByte();
            ParticleType type = ParticleType.values()[data.readByte()];
            Map<String, ParticleParameterType> factory = new HashMap<>();
            int factoryParametersAmount = data.readByte();
            for (int j = 0; j < factoryParametersAmount; j++) {
                factory.put(data.readUTF(), ParticleParameterType.values()[data.readByte()]);
            }
            addParticle(new Particle(id, type, factory));
        }
    }

    public void write(OutputStream out) throws IOException {
        DataOutputStream data = new DataOutputStream(out);
        data.writeByte(particles.size());
        for (Particle particle : particles.values()) {
            data.writeByte(particle.getId());
            data.writeByte(particle.getType().ordinal());

            Map<String, ParticleParameterType> factory = particle.getFactory();
            data.writeByte(factory.size());
            for (Map.Entry<String, ParticleParameterType> entry : factory.entrySet()) {
                byte[] keyName = entry.getKey().getBytes(StandardCharsets.US_ASCII);
                data.writeByte(keyName.length);
                data.write(keyName);
                data.writeByte(entry.getValue().ordinal());
            }
        }
    }

}
