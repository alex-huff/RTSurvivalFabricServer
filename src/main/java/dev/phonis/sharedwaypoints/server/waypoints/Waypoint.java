package dev.phonis.sharedwaypoints.server.waypoints;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class Waypoint {

    private final String name;
    private Identifier world;
    private double xPos;
    private double yPos;
    private double zPos;

    public Waypoint(String name, Identifier world, double xPos, double yPos, double zPos) {
        this.name = name;
        this.world = world;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public String getName() {
        return this.name;
    }

    public Identifier getWorld() {
        return this.world;
    }

    public double getX() {
        return this.xPos;
    }

    public double getY() {
        return this.yPos;
    }

    public double getZ() {
        return this.zPos;
    }

    public void update(Vec3d position, Identifier world) {
        this.xPos = position.x;
        this.yPos = position.y;
        this.zPos = position.z;
        this.world = world;
    }

}
