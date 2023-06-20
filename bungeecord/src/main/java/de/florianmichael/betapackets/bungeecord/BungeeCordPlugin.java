package de.florianmichael.betapackets.bungeecord;

import de.florianmichael.betapackets.api.BetaPacketsPlatform;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

public class BungeeCordPlugin extends Plugin implements BetaPacketsPlatform {

    @Override
    public void onLoad() {
        this.loadPlatform();
    }

    @Override
    public String getPlatformName() {
        return "BungeeCord";
    }

    @Override
    public Logger getLogging() {
        return getLogger();
    }
}
