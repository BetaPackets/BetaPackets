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

package org.betapackets.betapackets.mapping;

import org.betapackets.betapackets.model.base.VersionEnum;
import org.tukaani.xz.XZInputStream;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MappingLoader {

    public static void load(InputStream mappingFile) throws IOException {
        XZInputStream xis = new XZInputStream(mappingFile);
        DataInputStream data = new DataInputStream(xis);

        VersionEnum versionEnum = VersionEnum.R1_20_1;
        versionEnum.setBlockMapping(BlockMapping.read(data));
        versionEnum.setBlockStateMapping(BlockStateMapping.read(versionEnum.getBlockMapping(), data));
        versionEnum.setItemMapping(ItemMapping.read(versionEnum.getBlockMapping(), data));
        versionEnum.setMetadataCodecMapping(MetadataCodecMapping.read(data));
        versionEnum.setParticleMapping(ParticleMapping.read(data));
        versionEnum.setPotionEffectMapping(PotionEffectMapping.read(data));
        versionEnum.setEntityMapping(EntityMapping.read(data));

        xis.close();
    }

}
