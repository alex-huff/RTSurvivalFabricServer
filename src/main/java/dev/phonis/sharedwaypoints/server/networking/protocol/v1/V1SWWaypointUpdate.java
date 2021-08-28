package dev.phonis.sharedwaypoints.server.networking.protocol.v1;

import dev.phonis.sharedwaypoints.server.networking.protocol.persistant.SWPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class V1SWWaypointUpdate implements SWPacket {

    public final V1SWWaypoint newWaypoint;

    public V1SWWaypointUpdate(V1SWWaypoint newWaypoint) {
        this.newWaypoint = newWaypoint;
    }

    @Override
    public byte getID() {
        return V1Packets.In.SWWaypointUpdateID;
    }

    @Override
    public void toBytes(DataOutputStream dos) throws IOException {
        this.newWaypoint.toBytes(dos);
    }

    public static V1SWWaypointUpdate fromBytes(DataInputStream dis) throws IOException {
        return new V1SWWaypointUpdate(V1SWWaypoint.fromBytes(dis));
    }

}