package dev.phonis.sharedwaypoints.server.map;

import de.bluecolored.bluemap.api.markers.HtmlMarker;
import de.bluecolored.bluemap.api.markers.Marker;
import dev.phonis.sharedwaypoints.server.waypoints.Waypoint;
import dev.phonis.sharedwaypoints.server.waypoints.WaypointManager;

public
class BlueMapHelper
{

    private static final String markerHTML = """
                                             <div class="bm-marker-player">
                                                 <div class="bm-player-name">marker-label</div>
                                             </div>
                                             """;

    public static
    String getMapIDFromWorldID(String worldID)
    {
        switch (worldID)
        {
            case WaypointManager.netherIdentifier ->
            {
                return "nether";
            }
            case WaypointManager.endIdentifier ->
            {
                return "end";
            }
            default ->
            {
                return "overworld";
            }
        }
    }

    public static
    String getMarkerSetIDFromWorldID(String dimension)
    {
        return dimension + "-waypoints";
    }

    public static
    Marker getMarkerFromWaypoint(Waypoint waypoint)
    {
        return HtmlMarker.builder().html(BlueMapHelper.markerHTML.replace("marker-label", waypoint.getName()))
            .position(waypoint.getX(), waypoint.getY(), waypoint.getZ()).label(waypoint.getName()).build();
    }

}
