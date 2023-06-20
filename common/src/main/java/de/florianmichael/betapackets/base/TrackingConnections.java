package de.florianmichael.betapackets.base;

import de.florianmichael.betapackets.BetaPackets;
import de.florianmichael.betapackets.api.UserConnection;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class TrackingConnections {
    private final Map<Channel, UserConnection> trackingUserConnections = new HashMap<>();

    public void joinServer(final UserConnection userConnection) {
        trackingUserConnections.put(userConnection.getChannel(), userConnection);
        BetaPackets.getPlatform().getLogging().info("Connected user: " + userConnection.getChannel().remoteAddress());
    }

    public void disconnect(final Channel channel) {
        trackingUserConnections.remove(channel);
        BetaPackets.getPlatform().getLogging().info("Disconnected user: " + channel.remoteAddress());
    }

    public Map<Channel, UserConnection> getTrackingUserConnections() {
        return trackingUserConnections;
    }
}
