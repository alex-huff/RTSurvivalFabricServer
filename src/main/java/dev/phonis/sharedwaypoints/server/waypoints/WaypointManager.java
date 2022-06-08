package dev.phonis.sharedwaypoints.server.waypoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import dev.phonis.sharedwaypoints.server.SharedWaypointsServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WaypointManager
{

    private static final Gson            GSON             = new GsonBuilder().setPrettyPrinting().create();
    public static final  String          waypointFile     = SharedWaypointsServer.configDirectory + "waypoints.json";
    public static final  String          backupDirectory  = SharedWaypointsServer.configDirectory + "backup/";
    public static final  WaypointManager INSTANCE         = WaypointManager.load();
    public static final  String          worldIdentifier  = "overworld";
    public static final  String          netherIdentifier = "the_nether";
    public static final  String          endIdentifier    = "the_end";

    private final Map<String, Waypoint> waypointMap = new HashMap<>();

    public void forEachWaypoint(Consumer<Waypoint> consumer)
    {
        this.waypointMap.values().forEach(consumer);
    }

    public void forEachWaypoint(BiConsumer<Waypoint, Boolean> consumer)
    {
        Collection<Waypoint> values   = this.waypointMap.values();
        Iterator<Waypoint>   iterator = values.iterator();

        while (iterator.hasNext())
        {
            consumer.accept(iterator.next(), !iterator.hasNext());
        }
    }

    public Waypoint removeWaypoint(String name)
    {
        Waypoint waypoint = this.waypointMap.remove(name);

        this.trySave();

        return waypoint;
    }

    public Waypoint addWaypoint(CommandContext<ServerCommandSource> source, String name)
    {
        Vec3d position = source.getSource().getPosition();
        Waypoint waypoint = new Waypoint(
            name,
            source.getSource().getWorld().getRegistryKey().getValue().getPath(),
            position.getX(),
            position.getY(),
            position.getZ()
        );

        this.waypointMap.put(name, waypoint);
        this.trySave();

        return waypoint;
    }

    public Waypoint updateWaypoint(String s, Vec3d position, ServerWorld world)
    {
        Waypoint toUpdate = this.waypointMap.get(s);

        toUpdate.update(position, world.getRegistryKey().getValue().getPath());
        this.trySave();

        return toUpdate;
    }

    public boolean hasWaypoint(String name)
    {
        return this.waypointMap.containsKey(name);
    }

    public int numWaypoints()
    {
        return this.waypointMap.size();
    }

    private static WaypointManager load()
    {
        if (Files.exists(Path.of(WaypointManager.waypointFile)))
        {
            try (FileReader reader = new FileReader(WaypointManager.waypointFile))
            {
                WaypointManager.backup();

                return GSON.fromJson(reader, WaypointManager.class);
            }
            catch (IOException | JsonSyntaxException e)
            {
                System.out.println("Could not read waypoints.");
            }
        }

        return new WaypointManager();
    }

    public static void backup() throws IOException
    {
        Path path       = Path.of(WaypointManager.waypointFile);
        Path backupPath = Path.of(
            WaypointManager.backupDirectory + path.getFileName() + UUID.randomUUID().toString().replaceAll("-", "") +
            ".backup");
        Path parent     = backupPath.getParent();

        if (!Files.exists(parent))
        {
            Files.createDirectories(parent);
        }

        Files.copy(path, backupPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public void saveToFile() throws IOException
    {
        Path path   = Path.of(WaypointManager.waypointFile);
        Path parent = path.getParent();

        if (!Files.exists(parent))
        {
            Files.createDirectories(parent);
        }

        // Atomic file replace
        Path tempPath = path.resolveSibling(path.getFileName() + ".tmp");

        Files.writeString(tempPath, GSON.toJson(this));
        Files.move(tempPath, path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
    }

    public void trySave()
    {
        try
        {
            this.saveToFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
