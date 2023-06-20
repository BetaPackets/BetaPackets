package de.florianmichael.betapackets.api;

import de.florianmichael.betapackets.BetaPackets;

import java.util.logging.Logger;

public interface BetaPacketsPlatform {

    default void loadPlatform() {
        BetaPackets.init(this);

        final Logger logger = getLogging();
        logger.info("Injecting BetaPackets platform into " + getPlatformName());
    }

    Logger getLogging();

    String getPlatformName();
}
