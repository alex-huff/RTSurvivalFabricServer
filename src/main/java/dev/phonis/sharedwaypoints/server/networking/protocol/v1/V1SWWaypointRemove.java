package dev.phonis.sharedwaypoints.server.networking.protocol.v1;

import dev.phonis.sharedwaypoints.server.networking.protocol.persistant.SWPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class V1SWWaypointRemove implements SWPacket {

    public final String toRemove;

    public V1SWWaypointRemove(String toRemove) {
        this.toRemove = toRemove;
    }

    @Override
    public byte getID() {
        return V1Packets.In.SWWaypointRemoveID;
    }

    @Override
    public void toBytes(DataOutputStream dos) throws IOException {
        dos.writeUTF(this.toRemove);
    }

    public static V1SWWaypointRemove fromBytes(DataInputStream dis) throws IOException {
        return new V1SWWaypointRemove(dis.readUTF());
    }

}