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

package de.florianmichael.betapackets.mapping;

import de.florianmichael.betapackets.model.base.ProtocolCollection;
import org.tukaani.xz.XZInputStream;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MappingLoader {

    public static void load(InputStream mappingFile) throws IOException {
        XZInputStream xis = new XZInputStream(mappingFile);
        DataInputStream data = new DataInputStream(xis);

        ProtocolCollection protocolCollection = ProtocolCollection.R1_20_1;
        protocolCollection.setBlockMapping(BlockMapping.read(data));
        protocolCollection.setBlockStateMapping(BlockStateMapping.read(protocolCollection.getBlockMapping(), data));
        protocolCollection.setItemMapping(ItemMapping.read(protocolCollection.getBlockMapping(), data));
        protocolCollection.setMetadataCodecMapping(MetadataCodecMapping.read(data));
        protocolCollection.setParticleMapping(ParticleMapping.read(data));
        protocolCollection.setPotionEffectMapping(PotionEffectMapping.read(data));
        protocolCollection.setEntityMapping(EntityMapping.read(data));

        xis.close();
    }

}
