package de.florianmichael.betapackets;

import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.NetworkSide;
import de.florianmichael.betapackets.model.NetworkState;

public class DebugMode {
    public final static boolean ENABLED = true;

    public static void printPacket(final NetworkState state, final NetworkSide side, final Packet model) {
        if (!ENABLED || model == null) return;

        System.out.println(side + " -> " + state + ": " + model);
    }
}
