package dev.phonis.sharedwaypoints.server.networking.protocol.v1;

public
class V1Packets
{

    public static
    class In
    {

        public static final byte SWWaypointInitializeID = 0x02;
        public static final byte SWWaypointUpdateID     = 0x03;
        public static final byte SWWaypointRemoveID     = 0x04;

    }

}
