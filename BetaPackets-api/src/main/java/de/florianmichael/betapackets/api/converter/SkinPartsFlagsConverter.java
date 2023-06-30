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

package de.florianmichael.betapackets.api.converter;

/**
 * This class adds wrapper methods for the modalPartFlags from ClientSettingsC2SPacket
 *
 * @see de.florianmichael.betapackets.packet.play.c2s.ClientSettingsC2SPacket
 */
public class SkinPartsFlagsConverter {

    private byte flags;

    public SkinPartsFlagsConverter(byte flags) {
        this.flags = flags;
    }

    public boolean isCapeEnabled() {
        return (flags & (1 << 0)) == (1 << 0);
    }

    public void enableCape() {
        flags |= (1 << 0);
    }

    public boolean isJacketEnabled() {
        return (flags & (1 << 1)) == (1 << 1);
    }

    public void enableJacket() {
        flags |= (1 << 1);
    }

    public boolean isLeftSleeveEnabled() {
        return (flags & (1 << 2)) == (1 << 2);
    }

    public void enableLeftSleeve() {
        flags |= (1 << 2);
    }

    public boolean isRightSleeveEnabled() {
        return (flags & (1 << 3)) == (1 << 3);
    }

    public void enableRightSleeve() {
        flags |= (1 << 3);
    }

    public boolean isLeftPantsLegEnabled() {
        return (flags & (1 << 4)) == (1 << 4);
    }

    public void enableLeftPantsLeg() {
        flags |= (1 << 4);
    }

    public boolean isRightPantsLegEnabled() {
        return (flags & (1 << 5)) == (1 << 5);
    }

    public void enableRightPantsLeg() {
        flags |= (1 << 5);
    }

    public boolean isHatEnabled() {
        return (flags & (1 << 6)) == (1 << 6);
    }

    public void enableHat() {
        flags |= (1 << 6);
    }

    public byte getFlags() {
        return flags;
    }
}
