package lib.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends ServerProxy{

	@Override
	public Minecraft getMC() {
		return Minecraft.getMinecraft();
	}
	
	@Override
	public EntityPlayer clientPlayer() {
		return getMC().player;
	}
}