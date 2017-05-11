package lib.playerclass.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketSyncPlayerClass implements IMessage{

	public CPacketSyncPlayerClass() {
	}

	public CPacketSyncPlayerClass(String playerClassname) {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
	}
	
	public static class CPacketSyncPlayerClassHandler implements IMessageHandler<CPacketSyncPlayerClass, IMessage>
	{

		@Override
		public IMessage onMessage(CPacketSyncPlayerClass message, MessageContext ctx) {
				Minecraft.getMinecraft().addScheduledTask(()->{
					
				});
			return null;
		}
	}
}
