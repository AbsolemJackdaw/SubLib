package lib.playerclass;

import lib.item.armor.ModeledArmor;
import lib.playerclass.capability.CapabilityPlayerClassProvider;
import lib.playerclass.capability.PlayerClass;
import lib.playerclass.network.CPacketSyncPlayerClass;
import lib.playerclass.network.NetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DeterminePlayerClass {

	private static final int BOOTS = 36;
	private static final int LEGS = 37;
	private static final int CHEST = 38;
	private static final int HELM = 39;

	public DeterminePlayerClass() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onAttach(AttachCapabilitiesEvent.Entity event){
		final Entity entity = event.getEntity();

		if (entity instanceof EntityPlayer)
			event.addCapability(CapabilityPlayerClassProvider.KEY, new CapabilityPlayerClassProvider((EntityPlayer)entity)); 

	}

	@SubscribeEvent
	public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event){
		if( event.getEntityLiving() == null)
			return;
		if (!(event.getEntityLiving() instanceof EntityPlayer))
			return;

		if(!event.getEntityLiving().world.isRemote)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			determinePlayerClass(player);
		}
	}

	private void determinePlayerClass(EntityPlayer player){

		String classname = "noClass";
		String shieldName = "_noShield";

		ItemStack offhand = player.getHeldItemOffhand();

		PlayerClass playerclass = PlayerClass.get(player);

		if (!offhand.isEmpty())
			if(offhand.getItem() instanceof ItemShield){
				shieldName = playerclass.vanillaShieldSuffix();
				playerclass.setPlayerClass(classname+shieldName);//noclass vanilla shield
			}else
				playerclass.setPlayerClass(classname+shieldName);//noclass noshield

		/*checking armor...*/
		for (ItemStack is : player.inventory.armorInventory) {
			if (is == null) {
				playerclass.setPlayerClass(classname+shieldName);//no class (no)vanilla shield
				sync(player, classname, shieldName);
				return;//if one of the items is null, jump out. all items need to be worn
			}
			else // if there is one item that is no AbstractArmor, skip the setting of the player class
				if (!(is.getItem() instanceof ModeledArmor)){
					playerclass.setPlayerClass(classname+shieldName);//no class (no)vanilla shield
					sync(player, classname, shieldName);
					return;//no need to check for the next if one item is not class armour
				}
		}
		//at this point, all armour is class armour ! no need to check for it.

		//check if all armor is from the same class
		String a = ((ModeledArmor) player.inventory.getStackInSlot(HELM).getItem()).armorClassName();
		String b = ((ModeledArmor) player.inventory.getStackInSlot(CHEST).getItem()).armorClassName();
		String c = ((ModeledArmor) player.inventory.getStackInSlot(LEGS).getItem()).armorClassName();
		String d = ((ModeledArmor) player.inventory.getStackInSlot(BOOTS).getItem()).armorClassName();

		if(a.equals(b) && a.equals(c) && a.equals(d))
			classname = a;
		else{
			playerclass.setPlayerClass(classname+shieldName);//noclass (no)vanilla shield
			sync(player, classname, shieldName);
			return; //if there is a difference, jump out
		}
		//check if any other shields
		ModeledArmor helm = ((ModeledArmor) player.inventory.getStackInSlot(HELM).getItem());
		if (!offhand.isEmpty() && !shieldName.equals(playerclass.get(player).vanillaShieldSuffix())) 
			if (offhand.getItem().equals(helm.getLinkedShieldItem()))
				shieldName = playerclass.get(player).classShieldSuffix();

		if(!playerclass.getPlayerClass().equals(classname+shieldName))
			playerclass.setPlayerClass(classname+shieldName);

		sync(player, classname, shieldName);
	}
	
	private void sync(EntityPlayer player, String classname, String shieldName){
		if(player instanceof EntityPlayerMP)
			NetworkHandler.NETWORK.sendTo(new CPacketSyncPlayerClass(classname+shieldName), (EntityPlayerMP)player);
	
	}
}
