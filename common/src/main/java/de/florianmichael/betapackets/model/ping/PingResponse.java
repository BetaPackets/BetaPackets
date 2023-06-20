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

package de.florianmichael.betapackets.model.ping;

public class PingResponse {

    public Version version;
    public Players players;
    public String description;
    public String favicon;

    public Boolean enforcesSecureChat;
    public Boolean previewsChat;

    public PingResponse(Version version, Players players, String description, String favicon, Boolean enforcesSecureChat, Boolean previewsChat) {
        this.version = version;
        this.players = players;
        this.description = description;
        this.favicon = favicon;
        this.enforcesSecureChat = enforcesSecureChat;
        this.previewsChat = previewsChat;
    }

    @Override
    public String toString() {
        return "PingResponse{" +
                "version=" + version +
                ", players=" + players +
                ", description='" + description + '\'' +
                ", favicon='" + favicon + '\'' +
                ", enforcesSecureChat=" + enforcesSecureChat +
                ", previewsChat=" + previewsChat +
                '}';
    }
}
