/*
 * This file is part of BetaPackets - https://github.com/FlorianMichael/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD and contributors
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

package de.florianmichael.betapackets.base.registry;

import de.florianmichael.betapackets.base.registry.model.IGameStateType;
import de.florianmichael.betapackets.base.registry.model.IParticleType;
import de.florianmichael.betapackets.model.base.NetworkState;
import de.florianmichael.betapackets.base.registry.model.IPotionEffectType;
import de.florianmichael.betapackets.base.registry.model.IMetadataType;

public abstract class NoopPacketRegistry extends PacketRegistry {

    public NoopPacketRegistry(NetworkState networkState) {
        super(networkState);
    }

    @Override
    public IMetadataType getMetadataType() {
        return null;
    }

    @Override
    public IPotionEffectType getPotionEffectType() {
        return null;
    }

    @Override
    public IParticleType getParticleType() {
        return null;
    }

    @Override
    public IGameStateType getGameStateType() {
        return null;
    }
}
