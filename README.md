# BetaPackets
(WIP!) Standalone Minecraft protocol library for Spigot, Paper, Bukkit, BungeeCord and Velocity

### Note: This project is in very early development and not ready for production use!

## Why another protocol library?
The idea of BetaPackets is to develop a protocol library that is not based on any platform, the BetaPackets core is based only on Netty and can be implemented on anything, this ensures that this protocol library works on any server software with the same API.

## Design ideology
BetaPackets reads all packets and writes them exactly as they were read, which means that BetaPackets does not intercept invalid packets itself, but simply passes them on in the pipeline. BetaPackets tries to map everything possible into Java models, and gives the user the raw but also the model data.

Example:
![Design1](.github/images/design1.png)
Where **Animation.java** is an enum with all possible animations of the respective version, and the **Short** is simply the raw type that is in the vanilla package.

## How to use?
The BetaPacketsAPI has **3** listeners, **ClientboundPacketListener**, **ServerboundPacketListener** and **PlayerEarlyJoinPacketListener**.

| Class Name                    | When it is called                                                                         | Parameter                                           | API-Access                                     |
|-------------------------------|-------------------------------------------------------------------------------------------|-----------------------------------------------------|------------------------------------------------|
| ClientboundPacketListener     | When the server sends a packet to a player                                                | UserConnection, NetworkState, Packet, Player object | BetaPacketsAPI#registerIncomingPacketListener  |
| ServerboundPacketListener     | When the server receives a packet from a player                                           | UserConnection, NetworkState, Packet, Player object | BetaPacketsAPI#registerOutgoingPacketListener  |
| PlayerEarlyJoinPacketListener | When a player has gone through the login process and the server sends the JoinGame packet | Player UUID, lLayer Name, Protocol Version          | BetaPacketsAPI#registerPlayerEarlyJoinListener |
The API access methods return the listener as instance and can be called via **BetaPackets.getAPI()**. The BetaPackets API also has a remove method for all listeners where you have to specify the instance of the respective listener.

### Example java code
```java
BetaPackets.getAPI().registerIncomingPacketListener(event -> {
    if (event.packet instanceof LoginSuccessS2CPacket) {
        final LoginSuccessS2CPacket packet = (LoginSuccessS2CPacket) event.packet;

        if (packet.username.equals("Kevin")) {
            event.cancel(); // Packet won't be sent to the server/next element in the netty pipeline
        }
    }
});
```

## Dependencies
| Dependency     | Download                                         |
|----------------|--------------------------------------------------|
| MC-Structs     | https://github.com/Lenni0451/MCStructs           |
| DietrichEvents | https://github.com/FlorianMIchael/DietrichEvents |

## Supported versions
- 1.8.x

## Contact
If you encounter any issues, please report them on the
[issue tracker](https://github.com/FlorianMichael/BetaPackets/issues).  
If you just want to talk or need help with BetaPackets feel free to join my
[Discord](https://discord.gg/BwWhCHUKDf).
