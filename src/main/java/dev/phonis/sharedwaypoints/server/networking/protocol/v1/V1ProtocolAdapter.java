package dev.phonis.sharedwaypoints.server.networking.protocol.v1;

import dev.phonis.sharedwaypoints.server.networking.ProtocolAdapter;
import dev.phonis.sharedwaypoints.server.networking.protocol.action.SWAction;
import dev.phonis.sharedwaypoints.server.networking.protocol.action.SWWaypointInitializeAction;
import dev.phonis.sharedwaypoints.server.networking.protocol.persistant.SWPacket;
import dev.phonis.sharedwaypoints.server.waypoints.Waypoint;
import dev.phonis.sharedwaypoints.server.waypoints.WaypointManager;
import net.minecraft.util.Identifier;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

public class V1ProtocolAdapter implements ProtocolAdapter {

    public static final V1ProtocolAdapter INSTANCE = new V1ProtocolAdapter();

    @Override
    public SWAction toAction(DataInputStream dis) {
        return null;
    }

    @Override
    public SWPacket fromAction(SWAction action) {
        if (action instanceof SWWaypointInitializeAction) {
            List<V1SWWaypoint> waypoints = new ArrayList<>();

            WaypointManager.INSTANCE.forEachWaypoint(waypoint -> waypoints.add(this.fromWaypoint(waypoint)));

            return new V1SWWaypointInitialize(waypoints);
        }

        return null;
    }

    private V1SWWaypoint fromWaypoint(Waypoint waypoint) {
        return new V1SWWaypoint(
            waypoint.getName(),
            this.locationFromWaypoint(waypoint)
        );
    }

    private V1SWLocation locationFromWaypoint(Waypoint waypoint) {
        return new V1SWLocation(
            this.fromIdentifer(waypoint.getWorld()),
            waypoint.getX(),
            waypoint.getY(),
            waypoint.getZ()
        );
    }

    private V1SWDimension fromIdentifer(Identifier identifier) {
        if (identifier.equals(WaypointManager.worldIdentifier))
            return V1SWDimension.OVERWORLD;

        if (identifier.equals(WaypointManager.netherIdentifier))
            return V1SWDimension.NETHER;

        if (identifier.equals(WaypointManager.endIdentifier))
            return V1SWDimension.END;

        return V1SWDimension.OTHER;
    }

}
