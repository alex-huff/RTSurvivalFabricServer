package dev.phonis.sharedwaypoints.server.networking.protocol.v1;

import java.io.DataInputStream;
import java.io.IOException;

public enum V1SWDimension {

    OVERWORLD,
    NETHER,
    END,
    OTHER;

    public static V1SWDimension fromBytes(DataInputStream dis) throws IOException {
        return V1SWDimension.values()[dis.readByte()];
    }

}
