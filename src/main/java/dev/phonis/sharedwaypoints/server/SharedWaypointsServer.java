package dev.phonis.sharedwaypoints.server;

import dev.phonis.sharedwaypoints.server.commands.CommandWaypoint;
import dev.phonis.sharedwaypoints.server.commands.internal.SWCommandManager;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class SharedWaypointsServer implements DedicatedServerModInitializer {

	@Override
	public void onInitializeServer() {
		SWCommandManager.addCommand(new CommandWaypoint());
		CommandRegistrationCallback.EVENT.register(SWCommandManager::registerCommands);
	}

}
