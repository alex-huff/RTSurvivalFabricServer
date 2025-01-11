package dev.phonis.sharedwaypoints.server.map;

import dev.phonis.sharedwaypoints.server.waypoints.Waypoint;
import dev.phonis.sharedwaypoints.server.waypoints.WaypointManager;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerSet;

public class DynmapHelper
{

    public static final String markerSetID = "dynmap-waypoints";

    public static final String markerSetLabel = "Waypoints";

    public static String getDynmapWorldIDFromSWWorldID(String swWorldID)
    {
        switch (swWorldID)
        {
            case WaypointManager.netherIdentifier ->
            {
                return "DIM-1";
            }
            case WaypointManager.endIdentifier ->
            {
                return "DIM1";
            }
            default ->
            {
                return "world";
            }
        }
    }

    public static void createMarkerFromWaypoint(Waypoint waypoint, MarkerSet markerSet)
    {
        Marker existingMarker = markerSet.findMarker(waypoint.getName());
        if (existingMarker != null)
        {
            existingMarker.deleteMarker();
        }
        markerSet.createMarker(waypoint.getName(), waypoint.getName(), DynmapHelper.getDynmapWorldIDFromSWWorldID(waypoint.getWorld()), waypoint.getX(), waypoint.getY(), waypoint.getZ(), markerSet.getDefaultMarkerIcon(), false);
    }

}
