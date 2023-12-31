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

package org.betapackets.betapackets.model.auth;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class GameProfile {

    public UUID uuid;
    public String name;
    public List<ProfileProperty> profileProperties;

    public GameProfile(UUID uuid, String name) {
        this(uuid, name, new LinkedList<>());
    }

    public GameProfile(UUID uuid, String name, List<ProfileProperty> profileProperties) {
        this.uuid = uuid;
        this.name = name;
        this.profileProperties = profileProperties;
    }

    @Override
    public String toString() {
        return "GameProfile{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", profileProperties=" + profileProperties +
                '}';
    }
}
