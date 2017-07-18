package lib.playerclass.network;

import io.netty.buffer.ByteBuf;
import lib.Lib;
import lib.playerclass.capability.PlayerClass;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketSyncPlayerClass implements IMessage{

	String classname;
	
	public CPacketSyncPlayerClass() {
	}

	public CPacketSyncPlayerClass(String playerClassname) {
		classname = playerClassname;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		classname = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, classname);
	}
	
	public static class CPacketSyncPlayerClassHandler implements IMessageHandler<CPacketSyncPlayerClass, IMessage>
	{

		@Override
		public IMessage onMessage(CPacketSyncPlayerClass message, MessageContext ctx) {
				Lib.proxy.getMC().addScheduledTask(()->{
					if(message.classname != null)
					{
						PlayerClass.get(Lib.proxy.clientPlayer()).setPlayerClass(message.classname);
					}
				});
			return null;
		}
	}
}
