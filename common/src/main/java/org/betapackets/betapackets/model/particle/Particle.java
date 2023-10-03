/*
 * This file is part of BetaPackets - https://github.com/BetaPackets/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD
 * Copyright (C) 2023 DietrichPaul and contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.betapackets.betapackets.model.particle;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Particle {

    private final int id;
    private final ParticleType type;

    private final Map<String, ParticleParameterType> factory;

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
