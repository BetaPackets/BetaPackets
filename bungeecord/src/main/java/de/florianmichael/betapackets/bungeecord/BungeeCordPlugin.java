package de.florianmichael.betapackets.bungeecord;

import de.florianmichael.betapackets.BetaPackets;
import de.florianmichael.betapackets.api.BetaPacketsPlatform;
import de.florianmichael.betapackets.bungeecord.netty.BetaPacketsKickStringWriter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.protocol.KickStringWriter;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class BungeeCordPlugin extends Plugin implements BetaPacketsPlatform {

    private final static Logger logging = new JLoggerToSLF4J(LoggerFactory.getLogger("BetaPackets (BungeeCord)"));
    private static Field pipelineUtilsKickStringWriter;

    @Override
    public void onLoad() {
        try {
            final Class<?> pipelineUtils = Class.forName("net.md_5.bungee.netty.PipelineUtils");
            pipelineUtilsKickStringWriter = pipelineUtils.getDeclaredField("legacyKicker");
            pipelineUtilsKickStringWriter.setAccessible(true);
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            BetaPackets.getPlatform().getLogging().severe("Failed to get kick string writer in platform BungeeCord");
            e.printStackTrace();
            onDisable();
        }

        this.loadPlatform();
        setKickStringWriter(new BetaPacketsKickStringWriter());
    }

    public static void setKickStringWriter(KickStringWriter writer) {
        try {
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(pipelineUtilsKickStringWriter, pipelineUtilsKickStringWriter.getModifiers() & ~Modifier.FINAL);

            pipelineUtilsKickStringWriter.set(null, writer);
            modifiersField.setAccessible(false);
        } catch (ReflectiveOperationException e) {
            BetaPackets.getPlatform().getLogging().severe("Failed to set kick string writer in platform BungeeCord");
            e.printStackTrace();
        }
    }

    @Override
    public String getPlatformName() {
        return "BungeeCord";
    }

    @Override
    public Logger getLogging() {
        return logging;
    }
}
