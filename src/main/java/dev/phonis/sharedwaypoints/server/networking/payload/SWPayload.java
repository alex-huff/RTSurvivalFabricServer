package dev.phonis.sharedwaypoints.server.networking.payload;

import dev.phonis.sharedwaypoints.server.networking.codec.SWPayloadCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SWPayload(byte[] bytes) implements CustomPayload
{

    private static final Identifier sWIdentifier = Identifier.of("sharedwaypoints:main");
    public static final Id<SWPayload> id = new Id<>(SWPayload.sWIdentifier);
    public static final SWPayloadCodec codec = new SWPayloadCodec();

    @Override
    public Id<? extends CustomPayload> getId()
    {
        return SWPayload.id;
    }

}
