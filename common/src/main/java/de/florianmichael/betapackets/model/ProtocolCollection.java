package de.florianmichael.betapackets.model;

// Thanks to my 12-year-old self for this enum, I guess?
public enum ProtocolCollection {

    R1_20_1(763, "1.20/1.20.1"),

    R1_19_4(762, "1.19.4"),
    R1_19_3(761, "1.19.3"),
    R1_19_2(760, "1.19.2"),
    R1_19(759, "1.19"),

    R1_18_2(758, "1.18.2"),
    R1_18_1(757, "1.18-1.18.1"),

    R1_17_1(756, "1.17.1"),
    R1_17(755, "1.17"),

    R1_16_5(754, "1.16.4-1.16.5"),
    R1_16_3(753, "1.16.3"),
    R1_16_2(751, "1.16.2"),
    R1_16_1(736, "1.16.1"),
    R1_16(735, "1.16"),

    R1_15_2(578, "1.15.2"),
    R1_15_1(575, "1.15.1"),
    R1_15(573, "1.15"),

    R1_14_4(498, "1.14.4"),
    R1_14_3(490, "1.14.3"),
    R1_14_2(485, "1.14.2"),
    R1_14_1(480, "1.14.1"),
    R1_14(477, "1.14"),

    R1_13_2(404, "1.13.2"),
    R1_13_1(401, "1.13.1"),
    R1_13(393, "1.13"),

    R1_12_2(340, "1.12.2"),
    R1_12_1(338, "1.12.1"),
    R1_12(335, "1.12"),

    R1_11_1(316, "1.11.1-1.11.2"),
    R1_11(315, "1.11"),

    R1_10(210, "1.10.x"),

    R1_9_4(110, "1.9.3-1.9.4"),
    R1_9_2(109, "1.9.2"),
    R1_9_1(108, "1.9.1"),
    R1_9(107, "1.9"),

    R1_8(47, "1.8.x"),

    R1_7_10(5, "1.7.6/10"),
    R1_7_5(4, "1.7.2/5");

    private final int protocolId;
    private final String protocolName;

    ProtocolCollection(int protocolId, String protocolName) {
        this.protocolId = protocolId;
        this.protocolName = protocolName;
    }

    public int getProtocolId() {
        return protocolId;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public static ProtocolCollection fromProtocolId(final int protocolId) {
        for (ProtocolCollection protocol : ProtocolCollection.values()) {
            if (protocol.getProtocolId() == protocolId) {
                return protocol;
            }
        }
        return null;
    }

    public boolean isOlderThan(final ProtocolCollection other) {
        return this.ordinal() < other.ordinal();
    }

    public boolean isOlderThanOrEqualTo(final ProtocolCollection other) {
        return this.ordinal() <= other.ordinal();
    }

    public boolean isNewerThan(final ProtocolCollection other) {
        return this.ordinal() > other.ordinal();
    }

    public boolean isNewerThanOrEqualTo(final ProtocolCollection other) {
        return this.ordinal() >= other.ordinal();
    }

    public boolean isBetweenInclusive(final ProtocolCollection min, final ProtocolCollection max) {
        return this.isNewerThanOrEqualTo(min) && this.isOlderThanOrEqualTo(max);
    }

    public boolean isBetweenExclusive(final ProtocolCollection min, final ProtocolCollection max) {
        return this.isNewerThan(min) && this.isOlderThan(max);
    }
}
