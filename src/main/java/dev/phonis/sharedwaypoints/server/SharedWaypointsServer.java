package dev.phonis.sharedwaypoints.server;

import dev.phonis.sharedwaypoints.server.commands.CommandWaypoint;
import dev.phonis.sharedwaypoints.server.commands.internal.SWCommandManager;
import dev.phonis.sharedwaypoints.server.networking.SWNetworkManager;
import dev.phonis.sharedwaypoints.server.networking.SWPlayHandler;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class SharedWaypointsServer implements DedicatedServerModInitializer {

	public static final Identifier sWIdentifier = new Identifier("sharedwaypoints:main");

	@Override
	public void onInitializeServer() {
		SWCommandManager.addCommand(new CommandWaypoint());
		SWCommandManager.register();
		ServerPlayNetworking.registerGlobalReceiver(sWIdentifier, SWPlayHandler.INSTANCE);
		ServerPlayConnectionEvents.DISCONNECT.register(
			(handler, server) -> SWNetworkManager.INSTANCE.unsubscribePlayer(handler.player.getUuid())
		);
	}

}
