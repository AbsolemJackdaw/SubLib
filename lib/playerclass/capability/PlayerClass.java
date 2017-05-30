package lib.playerclass.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerClass {

	private EntityPlayer player;
	
	private String playerClass = "";
	/**suffix added when player is wearing a matching shield from the armor item class*/
	private String shielded_class = "_shielded";
	/**suffix added when the player is wearing any other shield*/
	private String shielded_vanilla = "_vanillaShield";
	
	public PlayerClass(){
	}

	public EntityPlayer getPlayer() { 
		return player; 
	}

	public void setPlayer(EntityPlayer newPlayer){
		this.player = newPlayer;
	}

	public static PlayerClass get(EntityPlayer player)
	{
		return player.getCapability(PlayerClassCapability.CAPABILITY, null);
	}

	public NBTBase writeData(){
		NBTTagCompound tag = new NBTTagCompound();
		return tag;
	}
	
	public void readData(NBTBase nbt){

	}
	
	public void setPlayerClass(String playerClass) {
		this.playerClass = playerClass;
	}
	
	public String getPlayerClass() {
		return playerClass;
	}
	
	public boolean isPlayerClass(String className){
		return playerClass.contains(className);
	}
	
	public boolean isShielded(){
		return playerClass.contains(shielded_class);
	}
	
	public boolean isVanillaShielded(){
		return playerClass.contains(shielded_vanilla);
	}
	
	/**suffix added when player is wearing a matching shield from the armor item class*/
	public String classShieldSuffix(){
		return shielded_class;
	}
	
	/**suffix added when the player is wearing any other shield*/
	public String vanillaShieldSuffix(){
		return shielded_vanilla;
	}
}
