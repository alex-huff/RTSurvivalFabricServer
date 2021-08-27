package dev.phonis.sharedwaypoints.server.networking;

import static net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.PlayChannelHandler;

import dev.phonis.sharedwaypoints.server.networking.v1.Packets;
import dev.phonis.sharedwaypoints.server.networking.v1.SWPacket;
import dev.phonis.sharedwaypoints.server.networking.v1.SWRegister;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class SWPlayHandler implements PlayChannelHandler {

    public static final SWPlayHandler INSTANCE = new SWPlayHandler();

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        byte[] data = new byte[buf.readableBytes()];

        buf.getBytes(0, data);

        try {
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
            byte packetID = dis.readByte();
            SWPacket packet = switch (packetID) {
                case Packets.Out.SWRegisterID -> SWRegister.fromBytes(dis);
                default -> null;
            };

            System.out.println("Read Thread: " + Thread.currentThread().getName() + " " + handler.player.getName().asString());

            dis.close();
            server.send(
                new ServerTask(
                    100,
                    () -> this.handlePacket(server, responseSender, packet)
                )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePacket(MinecraftServer server, PacketSender responseSender, SWPacket packet) {
        if (packet instanceof SWRegister register) {
            System.out.println("Main Thread: " + Thread.currentThread().getName() + " " + register.protocolVersion);
        }
    }

}
