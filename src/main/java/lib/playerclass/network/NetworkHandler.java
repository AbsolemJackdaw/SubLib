package lib.playerclass.network;

import lib.Lib;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod.EventBusSubscriber(modid = Lib.MODID, bus = Bus.MOD)
public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1.0";

    
    public NetworkHandler() {
        registerPackets();
    }
    
    public static SimpleChannel NETWORK = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Lib.MODID, "sublibnetwork")).clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals).networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public void registerPackets() {

        int messageNumber = 0;
        new SyncPlayerClassClient().register(messageNumber++);

    }
}
