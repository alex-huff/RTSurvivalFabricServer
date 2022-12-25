package dev.phonis.sharedwaypoints.server.networking;

import dev.phonis.sharedwaypoints.server.networking.protocol.persistant.Packets;
import dev.phonis.sharedwaypoints.server.networking.protocol.persistant.SWRegister;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.PlayChannelHandler;

public
class SWPlayHandler implements PlayChannelHandler
{

    public static final SWPlayHandler INSTANCE = new SWPlayHandler();

    @Override
    public
    void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf,
                 PacketSender responseSender)
    {
        byte[] data = new byte[buf.readableBytes()];

        buf.getBytes(0, data);

        // can receive for the same player be called from multiple netty threads?
        // if so, theoretically packets could be lost if registering has not been completed by
        // the SWRegister receiver, synchronizing on player fixes this assuming SWRegister is the
        // first packet sent to receive....
        // this is probably not necessary but should not cause too much overhead
        synchronized (player.getUuid())
        {
            try
            {
                DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));

                if (!SWNetworkManager.INSTANCE.handleIfSubscribed(server, player.getUuid(), dis))
                {
                    byte packetID = dis.readByte();

                    if (Packets.Out.SWRegisterID == packetID)
                    {
                        SWRegister register = SWRegister.fromBytes(dis);

                        dis.close();
                        SWNetworkManager.INSTANCE.subscribePlayer(server, player, responseSender,
                            register.protocolVersion);
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
