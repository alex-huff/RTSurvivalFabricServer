package dev.phonis.sharedwaypoints.server;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import dev.phonis.sharedwaypoints.server.commands.CommandWaypoint;
import dev.phonis.sharedwaypoints.server.commands.internal.SWCommandManager;
import dev.phonis.sharedwaypoints.server.map.BlueMapHelper;
import dev.phonis.sharedwaypoints.server.map.DynmapHelper;
import dev.phonis.sharedwaypoints.server.networking.SWNetworkManager;
import dev.phonis.sharedwaypoints.server.networking.SWPlayHandler;
import dev.phonis.sharedwaypoints.server.networking.payload.SWPayload;
import dev.phonis.sharedwaypoints.server.waypoints.WaypointManager;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.markers.MarkerAPI;

import java.util.Optional;

public
class SharedWaypointsServer implements DedicatedServerModInitializer
{

    public static final int             maxSupportedProtocolVersion = 1;
    public static final String          configDirectory             = "config/sharedwaypoints/";
    private static      DynmapCommonAPI dynmapAPI                   = null;

    public static
    Optional<DynmapCommonAPI> getDynmapAPI()
    {
        return Optional.ofNullable(SharedWaypointsServer.dynmapAPI);
    }

    @Override
    public
    void onInitializeServer()
    {
        SWCommandManager.addCommand(new CommandWaypoint());
        SWCommandManager.register();
        PayloadTypeRegistry.playC2S().register(SWPayload.id, SWPayload.codec);
        PayloadTypeRegistry.playS2C().register(SWPayload.id, SWPayload.codec);
        ServerPlayNetworking.registerGlobalReceiver(SWPayload.id, SWPlayHandler.INSTANCE);
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> SWNetworkManager.INSTANCE.unsubscribePlayer(handler.player.getUuid()));
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> BlueMapAPI.onEnable((api) -> server.execute(() -> this.blueMapCreateWaypoints(api))));
        ServerLifecycleEvents.SERVER_STARTED.register(
            // Dynmap might always call apiEnabled on the main thread, in
            // which case I don't actually need to user MinecraftServer::execute to run the task later on the main
            // thread.
            (server) -> DynmapCommonAPIListener.register(new DynmapCommonAPIListener()
            {
                @Override
                public
                void apiEnabled(DynmapCommonAPI api)
                {
                    server.execute(() -> SharedWaypointsServer.this.dynmapInitializeAndCreateWaypoints(api));
                }
            }));
    }

    private
    void dynmapInitializeAndCreateWaypoints(DynmapCommonAPI api)
    {
        SharedWaypointsServer.dynmapAPI = api;
        MarkerAPI markerAPI = SharedWaypointsServer.dynmapAPI.getMarkerAPI();
        org.dynmap.markers.MarkerSet waypoints
            = markerAPI.createMarkerSet(DynmapHelper.markerSetID, DynmapHelper.markerSetLabel, null, false);
        waypoints.setHideByDefault(true);
        waypoints.setDefaultMarkerIcon(markerAPI.getMarkerIcon("blueflag"));
        WaypointManager.INSTANCE.forEachWaypoint((waypoint) -> DynmapHelper.createMarkerFromWaypoint(waypoint, waypoints));
    }

    private
    void blueMapCreateWaypoints(BlueMapAPI api)
    {
        MarkerSet waypointSetOverworld = MarkerSet.builder().defaultHidden(true)
            .label(this.getMarkerSetLabelFromWorldID(WaypointManager.overworldIdentifier)).build();
        MarkerSet waypointSetNether = MarkerSet.builder().defaultHidden(true)
            .label(this.getMarkerSetLabelFromWorldID(WaypointManager.netherIdentifier)).build();
        MarkerSet waypointSetEnd = MarkerSet.builder().defaultHidden(true)
            .label(this.getMarkerSetLabelFromWorldID(WaypointManager.endIdentifier)).build();
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
        this.addBlueMapMarkerSetToWorld(api, WaypointManager.overworldIdentifier, waypointSetOverworld);
        this.addBlueMapMarkerSetToWorld(api, WaypointManager.netherIdentifier, waypointSetNether);
        this.addBlueMapMarkerSetToWorld(api, WaypointManager.endIdentifier, waypointSetEnd);
    }

    private
    void addBlueMapMarkerSetToWorld(BlueMapAPI api, String worldID, MarkerSet markerSet)
    {
        api.getMap(BlueMapHelper.getMapIDFromWorldID(worldID))
            .ifPresent(map -> map.getMarkerSets().put(BlueMapHelper.getMarkerSetIDFromWorldID(worldID), markerSet));
    }

    private
    String getMarkerSetLabelFromWorldID(String worldID)
    {
        switch (worldID)
        {
            case WaypointManager.netherIdentifier ->
            {
                return "Nether Waypoints";
            }
            case WaypointManager.endIdentifier ->
            {
                return "End Waypoints";
            }
            default ->
            {
                return "Overworld Waypoints";
            }
        }
    }

}
