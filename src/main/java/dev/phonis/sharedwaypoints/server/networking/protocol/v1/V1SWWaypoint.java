package dev.phonis.sharedwaypoints.server.networking.protocol.v1;

import dev.phonis.sharedwaypoints.server.networking.protocol.persistant.SWSerializable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class V1SWWaypoint implements SWSerializable
{

    public final String name;
    public final V1SWLocation location;

    public V1SWWaypoint(String name, V1SWLocation location)
    {
        this.name = name;
        this.location = location;
    }

    @Override
    public void toBytes(DataOutputStream dos) throws IOException
    {
        dos.writeUTF(this.name);
        this.location.toBytes(dos);
    }

    public static V1SWWaypoint fromBytes(DataInputStream dis) throws IOException
    {
        return new V1SWWaypoint(dis.readUTF(), V1SWLocation.fromBytes(dis));
    }

}