package de.florianmichael.betapackets.api;

import de.florianmichael.betapackets.model.NetworkState;
import de.florianmichael.betapackets.model.ProtocolCollection;
import io.netty.channel.Channel;

public class UserConnection {
    private final Channel channel;
    private NetworkState state;
    private ProtocolCollection protocolVersion;

    private boolean loaded;

    public UserConnection(Channel channel) {
        this.channel = channel;
    }

    public void init(final NetworkState state, final ProtocolCollection protocolVersion) {
        this.state = state;
        this.protocolVersion = protocolVersion;
        this.loaded = true;
    }

    public boolean hasLoaded() {
        return loaded;
    }

    public Channel getChannel() {
        return channel;
    }

    public NetworkState getState() {
        return state;
    }

    public ProtocolCollection getProtocolVersion() {
        return protocolVersion;
    }
}
