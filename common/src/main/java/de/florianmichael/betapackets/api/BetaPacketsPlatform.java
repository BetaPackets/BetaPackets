package de.florianmichael.betapackets.api;

import de.florianmichael.betapackets.BetaPackets;

import java.util.logging.Logger;

public interface BetaPacketsPlatform {

    default void onLoad() {
        BetaPackets.init(this);

        final Logger logger = getLogger();
        logger.info("Injecting BetaPackets platform into " + getPlatformName());
    }

    Logger getLogger();

    String getPlatformName();
}
