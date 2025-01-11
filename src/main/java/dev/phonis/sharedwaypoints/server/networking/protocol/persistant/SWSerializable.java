package dev.phonis.sharedwaypoints.server.networking.protocol.persistant;

import java.io.DataOutputStream;
import java.io.IOException;

public interface SWSerializable
{

    void toBytes(DataOutputStream dos) throws IOException;

}
