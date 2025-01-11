package dev.phonis.sharedwaypoints.server.networking.protocol.v1;

import dev.phonis.sharedwaypoints.server.networking.ProtocolAdapter;
import dev.phonis.sharedwaypoints.server.networking.protocol.action.SWAction;
import dev.phonis.sharedwaypoints.server.networking.protocol.action.SWWaypointInitializeAction;
import dev.phonis.sharedwaypoints.server.networking.protocol.action.SWWaypointRemoveAction;
import dev.phonis.sharedwaypoints.server.networking.protocol.action.SWWaypointUpdateAction;
import dev.phonis.sharedwaypoints.server.networking.protocol.persistant.SWPacket;
import dev.phonis.sharedwaypoints.server.waypoints.Waypoint;
import dev.phonis.sharedwaypoints.server.waypoints.WaypointManager;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

public class V1ProtocolAdapter implements ProtocolAdapter
{

    public static final V1ProtocolAdapter INSTANCE = new V1ProtocolAdapter();

    @Override
    public SWAction toAction(DataInputStream dis)
    {
        return null;
    }

    @Override
    public SWPacket fromWaypointInitialize(SWWaypointInitializeAction action)
    {
        List<V1SWWaypoint> waypoints = new ArrayList<>();

        WaypointManager.INSTANCE.forEachWaypoint(waypoint -> waypoints.add(this.fromWaypoint(waypoint)));

        return new V1SWWaypointInitialize(waypoints);
    }

    @Override
    public SWPacket fromWaypointUpdate(SWWaypointUpdateAction action)
    {
        return new V1SWWaypointUpdate(this.fromWaypoint(action.waypoint));
    }

    @Override
    public SWPacket fromWaypointRemove(SWWaypointRemoveAction action)
    {
        return new V1SWWaypointRemove(action.name);
    }

    private V1SWWaypoint fromWaypoint(Waypoint waypoint)
    {
        return new V1SWWaypoint(waypoint.getName(), this.locationFromWaypoint(waypoint));
    }

    private V1SWLocation locationFromWaypoint(Waypoint waypoint)
    {
        return new V1SWLocation(this.fromIdentifier(waypoint.getWorld()), waypoint.getX(), waypoint.getY(), waypoint.getZ());
    }

    private V1SWDimension fromIdentifier(String identifier)
    {
        if (identifier.equals(WaypointManager.overworldIdentifier))
        {
            return V1SWDimension.OVERWORLD;
        }

        if (identifier.equals(WaypointManager.netherIdentifier))
        {
            return V1SWDimension.NETHER;
        }

        if (identifier.equals(WaypointManager.endIdentifier))
        {
            return V1SWDimension.END;
        }

        return V1SWDimension.OTHER;
    }

}
