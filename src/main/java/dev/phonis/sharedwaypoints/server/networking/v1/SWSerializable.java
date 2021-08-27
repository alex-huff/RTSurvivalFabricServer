package dev.phonis.sharedwaypoints.server.networking.v1;

import java.io.DataOutputStream;
import java.io.IOException;

public interface SWSerializable {

    void toBytes(DataOutputStream dos) throws IOException;

}
