package dev.phonis.sharedwaypoints.server.networking;

import dev.phonis.sharedwaypoints.server.networking.protocol.action.SWAction;
import dev.phonis.sharedwaypoints.server.networking.protocol.action.SWWaypointInitializeAction;
import dev.phonis.sharedwaypoints.server.networking.protocol.action.SWWaypointRemoveAction;
import dev.phonis.sharedwaypoints.server.networking.protocol.action.SWWaypointUpdateAction;
import dev.phonis.sharedwaypoints.server.networking.protocol.persistant.SWPacket;

import java.io.DataInputStream;
import java.io.IOException;

public interface ProtocolAdapter
{

    SWAction toAction(DataInputStream dis) throws IOException;

    SWPacket fromWaypointInitialize(SWWaypointInitializeAction action);

    SWPacket fromWaypointUpdate(SWWaypointUpdateAction action);

    SWPacket fromWaypointRemove(SWWaypointRemoveAction action);

    default SWPacket fromAction(SWAction action)
    {
        if (action instanceof SWWaypointInitializeAction swWaypointInitializeAction)
        {
            return this.fromWaypointInitialize(swWaypointInitializeAction);
        }
        else if (action instanceof SWWaypointUpdateAction swWaypointUpdateAction)
        {
            return this.fromWaypointUpdate(swWaypointUpdateAction);
        }
        else if (action instanceof SWWaypointRemoveAction swWaypointRemoveAction)
        {
            return this.fromWaypointRemove(swWaypointRemoveAction);
        }

        return null;
    }

}
