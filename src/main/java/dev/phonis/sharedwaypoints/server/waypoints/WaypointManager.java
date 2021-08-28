package dev.phonis.sharedwaypoints.server.waypoints;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WaypointManager {

    public static final WaypointManager INSTANCE = new WaypointManager();
    public static final Identifier worldIdentifier = new Identifier("minecraft", "overworld");
    public static final Identifier netherIdentifier = new Identifier("minecraft", "the_nether");
    public static final Identifier endIdentifier = new Identifier("minecraft", "the_end");

    private final Map<String, Waypoint> waypointMap = new HashMap<>();

    static {
        WaypointManager.INSTANCE.waypointMap.put(
            "test",
            new Waypoint(
                "test",
                WaypointManager.worldIdentifier,
                0,
                0,
                0
            )
        );
    }

    public void forEachWaypoint(Consumer<Waypoint> consumer) {
        this.waypointMap.values().forEach(consumer);
    }

    public void addWaypoint(CommandContext<ServerCommandSource> source, String name) {
        Vec3d position = source.getSource().getPosition();

        System.out.println(source.getSource().getWorld().getRegistryKey().getValue());
        this.waypointMap.put(
            name,
            new Waypoint(
                name,
                source.getSource().getWorld().getRegistryKey().getValue(),
                position.getX(),
                position.getY(),
                position.getZ()
            )
        );
    }

}
