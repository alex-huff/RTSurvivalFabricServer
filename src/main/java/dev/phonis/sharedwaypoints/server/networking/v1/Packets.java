package dev.phonis.sharedwaypoints.server.networking.v1;

public class Packets {

    public static class Out {

        public static final byte SWRegisterID = 0x00;

    }

    public static class In {

        public static final byte SWUnsupportedID = 0x01;
        public static final byte SWWaypointInitializeID = 0x02;
        public static final byte SWWaypointUpdateID = 0x03;
        public static final byte SWWaypointRemoveID = 0x04;

    }

}
