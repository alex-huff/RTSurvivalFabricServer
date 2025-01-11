package dev.phonis.sharedwaypoints.server.networking.protocol.v1;

import dev.phonis.sharedwaypoints.server.networking.protocol.persistant.SWPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class V1SWWaypointInitialize implements SWPacket
{

    public final List<V1SWWaypoint> waypoints;

    public V1SWWaypointInitialize(List<V1SWWaypoint> waypoints)
    {
        this.waypoints = waypoints;
    }

    @Override
    public byte getID()
    {
        return V1Packets.In.SWWaypointInitializeID;
    }

    @Override
    public void toBytes(DataOutputStream dos) throws IOException
    {
        dos.writeInt(this.waypoints.size());

        for (V1SWWaypoint waypoint : this.waypoints)
        {
            waypoint.toBytes(dos);
        }
    }

    public static V1SWWaypointInitialize fromBytes(DataInputStream dis) throws IOException
    {
        int length = dis.readInt();
        List<V1SWWaypoint> waypoints = new ArrayList<>(length);

        for (int i = 0; i < length; i++)
        {
            waypoints.add(V1SWWaypoint.fromBytes(dis));
        }

        return new V1SWWaypointInitialize(waypoints);
    }

}