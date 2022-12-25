package dev.phonis.sharedwaypoints.server.networking.protocol.action;

import dev.phonis.sharedwaypoints.server.waypoints.Waypoint;

public
class SWWaypointUpdateAction implements SWAction
{

    public final Waypoint waypoint;

    public
    SWWaypointUpdateAction(Waypoint waypoint)
    {
        this.waypoint = waypoint;
    }

}
