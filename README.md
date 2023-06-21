# BetaPackets
(WIP!) Standalone Minecraft protocol library for Spigot, Paper, Bukkit, BungeeCord and Velocity

### Note: This project is in very early development and not ready for production use!

## Why another protocol library?
The idea of BetaPackets is to develop a protocol library that is not based on any platform, the BetaPackets core is based only on Netty and can be implemented on anything, this ensures that this protocol library works on any server software with the same API.

## How to use?
BetaPackets uses DietrichEvents as event library, so you have to include it to use BetaPackets, so the API consists only of events listed here:

| Class Name                | When it is called                               | Parameter                                           | Cancellable |
|---------------------------|-------------------------------------------------|-----------------------------------------------------|-------------|
| ClientboundPacketListener | When the server sends a packet to a player      | UserConnection, NetworkState, Packet, Player object | yes         |
| ServerboundPacketListener | When the server receives a packet from a player | UserConnection, NetworkState, Packet, Player object | yes         |

### Example java code
```java
BetaPackets.getPlatform().getEventProvider().subscribe(ClientboundPacketListener.class, event -> {
    if (event.packet instanceof LoginSuccessS2CPacket) {
        final LoginSuccessS2CPacket loginSuccess = (LoginSuccessS2CPacket) event.packet;
        final Channel channel = event.userConnection.getChannel(); // User's netty channel
        final Object player = event.player; // User's player object (depends on the platform)
                
        if (loginSuccess.getUsername().equals("Notch")) {
            System.out.println("Notch joined the server!");
        } else {
            System.out.println("Someone else joined the server!");
        }
                
        if (loginSuccess.getUsername().equals("Kevin")) {
            event.cancel(); // Packet won't be processed
        }
    }
});
```

## Supported versions
- 1.8.x

## Note
BetaPackets uses for items and chat components [MCStructs](https://github.com/Lenni0451/MCStructs/tree/main)

## Contact
If you encounter any issues, please report them on the
[issue tracker](https://github.com/FlorianMichael/BetaPackets/issues).  
If you just want to talk or need help with BetaPackets feel free to join my
[Discord](https://discord.gg/BwWhCHUKDf).
