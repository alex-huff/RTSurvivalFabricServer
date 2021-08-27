package dev.phonis.sharedwaypoints.server.networking.v1;

import java.io.DataInputStream;
import java.io.IOException;

public enum SWDimension {

    OVERWORLD,
    NETHER,
    END,
    OTHER;

    public static SWDimension fromBytes(DataInputStream dis) throws IOException {
        return SWDimension.values()[dis.readByte()];
    }

}
