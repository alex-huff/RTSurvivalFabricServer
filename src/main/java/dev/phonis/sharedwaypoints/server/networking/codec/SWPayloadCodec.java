package dev.phonis.sharedwaypoints.server.networking.codec;

import dev.phonis.sharedwaypoints.server.networking.payload.SWPayload;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

public class SWPayloadCodec implements PacketCodec<RegistryByteBuf, SWPayload>
{

    @Override
    public SWPayload decode(RegistryByteBuf buf)
    {
        return new SWPayload(buf.readByteArray());
    }

    @Override
    public void encode(RegistryByteBuf buf, SWPayload value)
    {
        buf.writeByteArray(value.bytes());
    }

}
