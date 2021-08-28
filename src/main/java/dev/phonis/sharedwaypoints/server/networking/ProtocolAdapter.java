package dev.phonis.sharedwaypoints.server.networking;

import dev.phonis.sharedwaypoints.server.networking.protocol.action.SWAction;
import dev.phonis.sharedwaypoints.server.networking.protocol.persistant.SWPacket;

import java.io.DataInputStream;
import java.io.IOException;

public interface ProtocolAdapter {

    SWAction toAction(DataInputStream dis) throws IOException;

    SWPacket fromAction(SWAction action);

}
