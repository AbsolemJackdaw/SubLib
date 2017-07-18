package lib.playerclass.network;

import lib.playerclass.network.CPacketSyncPlayerClass.CPacketSyncPlayerClassHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

	public static final SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper("sublibnetwork");

	public NetworkHandler() {
		NETWORK.registerMessage(CPacketSyncPlayerClassHandler.class, CPacketSyncPlayerClass.class, 0, Side.CLIENT);
	}
}
