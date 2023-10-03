/*
 * This file is part of BetaPackets - https://github.com/BetaPackets/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD
 * Copyright (C) 2023 DietrichPaul and contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.betapackets.betapackets.spigot.netty;

import org.betapackets.betapackets.connection.UserConnection;
import io.netty.channel.*;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class BukkitInjector {

    private static final Object serverConnection;
    private static final Field serverConnectionChannelFutures;
    private static final List channelFutures;

    static {
        Object minecraftServer = findMinecraftServer();
        if (minecraftServer == null)
            throw new RuntimeException("Cannot find MinecraftServer.class");

        serverConnection = findServerConnection(minecraftServer);
        if (serverConnection == null)
            throw new RuntimeException("Cannot find ServerConnection.class");

        serverConnectionChannelFutures = findChannelFutures(serverConnection.getClass());
        if (serverConnectionChannelFutures == null)
            throw new RuntimeException("Cannot find List<NetworkManager> in ServerConnection.class");

        try {
            channelFutures = (List) serverConnectionChannelFutures.get(serverConnection);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Field findChannelFutures(Class<?> serverConnection) {
        Field[] declaredFields = serverConnection.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getType() == List.class) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

    private static Object findServerConnection(Object minecraftServer) {
        Method[] methods = minecraftServer.getClass().getSimpleName().equals("DedicatedServer")
                ? minecraftServer.getClass().getSuperclass().getMethods() : minecraftServer.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals("getServerConnection")) continue;
            if (method.getReturnType().getSimpleName().equals("ServerConnection")) {
                try {
                    return method.invoke(minecraftServer);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    private static Object findMinecraftServer() {
        Server server = Bukkit.getServer();
        Class<? extends Server> clazz = server.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().getSimpleName().equals("MinecraftServer")
                    || field.getType().getSimpleName().equals("DedicatedServer")) {
                field.setAccessible(true);
                try {
                    return field.get(server);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public static void inject() {
        ChannelPipeline pipeline = ((DefaultChannelPromise) channelFutures.get(0)).channel().pipeline();
        if (pipeline.names().contains("betapackets-bukkitinjector"))
            pipeline.remove("betapackets-bukkitinjector");
        pipeline.addFirst("betapackets-bukkitinjector", new ChannelInboundHandlerAdapter() {

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ((Channel) msg).pipeline().addFirst(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ctx.pipeline().addFirst(new SpigotBetaPacketsPipeline(new UserConnection(
                                ctx.channel()
                        )));
                        ctx.pipeline().remove(this);
                        super.channelRead(ctx, msg);
                    }
                });
                super.channelRead(ctx, msg);
            }
        });
    }
}
