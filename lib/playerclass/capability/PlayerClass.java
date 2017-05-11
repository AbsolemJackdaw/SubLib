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

	public static PlayerClass armorClass(EntityPlayer player)
	{
		return player.getCapability(PlayerClassCapability.CAPABILITY, null);
	}

	public NBTBase writeData(){
		NBTTagCompound tag = new NBTTagCompound();
		return tag;
	}
	
	public void readData(NBTBase nbt){

	}
	
	public void set(String name){
		playerClass = name;
	}
	
	public String get(){
		return playerClass;
	}
	
	public boolean isInstanceOf(String className){
		return playerClass.contains(className);
	}
	
	public boolean isShielded(){
		return playerClass.contains(shielded_class);
	}
	
	public boolean isVanillaShielded(){
		return playerClass.contains(shielded_vanilla);
	}
	
	public String getClassShieldSuffix(){
		return shielded_class;
	}
	
	public String getVanillaShieldSuffix(){
		return shielded_vanilla;
	}
}
