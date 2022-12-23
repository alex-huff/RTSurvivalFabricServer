package dev.phonis.sharedwaypoints.server;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import dev.phonis.sharedwaypoints.server.bluemap.BlueMapHelper;
import dev.phonis.sharedwaypoints.server.commands.CommandWaypoint;
import dev.phonis.sharedwaypoints.server.commands.internal.SWCommandManager;
import dev.phonis.sharedwaypoints.server.networking.SWNetworkManager;
import dev.phonis.sharedwaypoints.server.networking.SWPlayHandler;
import dev.phonis.sharedwaypoints.server.waypoints.WaypointManager;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public
class SharedWaypointsServer implements DedicatedServerModInitializer
{

    public static final Identifier sWIdentifier                = new Identifier("sharedwaypoints:main");
    public static final int        maxSupportedProtocolVersion = 1;
    public static final String     configDirectory             = "config/sharedwaypoints/";

    @Override
    public
    void onInitializeServer()
    {
        SWCommandManager.addCommand(new CommandWaypoint());
        SWCommandManager.register();
        ServerPlayNetworking.registerGlobalReceiver(sWIdentifier, SWPlayHandler.INSTANCE);
        ServerPlayConnectionEvents.DISCONNECT.register(
            (handler, server) -> SWNetworkManager.INSTANCE.unsubscribePlayer(handler.player.getUuid()));
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> BlueMapAPI.onEnable((api) -> server.execute(() ->
        {
            MarkerSet waypointSetOverworld = MarkerSet.builder().defaultHidden(true).label("Overworld Waypoints")
                .build();
            MarkerSet waypointSetNether = MarkerSet.builder().defaultHidden(true).label("Nether Waypoints").build();
            MarkerSet waypointSetEnd    = MarkerSet.builder().defaultHidden(true).label("End Waypoints").build();
            WaypointManager.INSTANCE.forEachWaypoint((waypoint) ->
            {
                MarkerSet markerSet;
                switch (waypoint.getWorld())
                {
                    case WaypointManager.netherIdentifier -> markerSet = waypointSetNether;
                    case WaypointManager.endIdentifier -> markerSet = waypointSetEnd;
                    default -> markerSet = waypointSetOverworld;
                }
                markerSet.getMarkers().put(waypoint.getName(), BlueMapHelper.getMarkerFromWaypoint(waypoint));
            });
            this.addMarkerSetToMap(api, WaypointManager.worldIdentifier, waypointSetOverworld);
            this.addMarkerSetToMap(api, WaypointManager.netherIdentifier, waypointSetNether);
            this.addMarkerSetToMap(api, WaypointManager.endIdentifier, waypointSetEnd);
        })));
    }

    private
    void addMarkerSetToMap(BlueMapAPI api, String worldID, MarkerSet markerSet)
    {
        api.getMap(BlueMapHelper.getMapIDFromWorldID(worldID))
            .ifPresent(map -> map.getMarkerSets().put(BlueMapHelper.getMarkerSetIDFromWorldID(worldID), markerSet));
    }

}
