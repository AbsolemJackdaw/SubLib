package lib.playerclass.network;

import java.util.function.Supplier;

import lib.playerclass.capability.PlayerClass;
import lib.util.ClientReferences;
import lib.util.networking.IPacketBase;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SyncPlayerClassClient implements IPacketBase {

    public String playstylename = "empty";

    
    public SyncPlayerClassClient() {

    }
    
    public SyncPlayerClassClient(String name) {

        this.playstylename = name;
    }

    public SyncPlayerClassClient(PacketBuffer buf) {

        decode(buf);
    }

    @Override
    public void encode(PacketBuffer buf) {

        buf.writeString(playstylename);
    }

    @Override
    public void decode(PacketBuffer buf) {

        playstylename = buf.readString(128);

    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {

        context.get().enqueueWork(() -> {
            if (playstylename != null) {
                PlayerClass.get(ClientReferences.getClientPlayer()).setPlayerClass(playstylename);
            }
        });
        context.get().setPacketHandled(true);
    }

    @Override
    public void register(int id) {
        NetworkHandler.NETWORK.registerMessage(id, SyncPlayerClassClient.class, SyncPlayerClassClient::encode,
                SyncPlayerClassClient::new, SyncPlayerClassClient::handle);
    }
}
