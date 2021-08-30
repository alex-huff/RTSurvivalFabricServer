package dev.phonis.sharedwaypoints.server.waypoints;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
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

    public void forEachWaypoint(BiConsumer<Waypoint, Boolean> consumer) {
        Collection<Waypoint> values = this.waypointMap.values();
        Iterator<Waypoint> iterator = values.iterator();

        while (iterator.hasNext()) {
            consumer.accept(iterator.next(), !iterator.hasNext());
        }
    }

    public Waypoint removeWaypoint(String name) {
        return this.waypointMap.remove(name);
    }

    public Waypoint addWaypoint(CommandContext<ServerCommandSource> source, String name) {
        Vec3d position = source.getSource().getPosition();
        Waypoint waypoint = new Waypoint(
            name,
            source.getSource().getWorld().getRegistryKey().getValue(),
            position.getX(),
            position.getY(),
            position.getZ()
        );

        this.waypointMap.put(name, waypoint);

        return waypoint;
    }

    public Waypoint updateWaypoint(String s, Vec3d position, ServerWorld world) {
        Waypoint toUpdate = this.waypointMap.get(s);

        toUpdate.update(position, world.getRegistryKey().getValue());

        return toUpdate;
    }

    public boolean hasWaypoint(String name) {
        return this.waypointMap.containsKey(name);
    }

    public int numWaypoints() {
        return this.waypointMap.size();
    }

}
