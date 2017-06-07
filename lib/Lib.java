package lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lib.item.OreDictRegistry;
import lib.playerclass.DeterminePlayerClass;
import lib.playerclass.capability.PlayerClassCapability;
import lib.playerclass.network.NetworkHandler;
import lib.proxy.ServerProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Lib.MODID, name = Lib.NAME, version = Lib.VERSION)
public class Lib {

	public static final String MODID = "subcommonlib";
	public static final String NAME = "Subaraki's Common Library";
	public static final String VERSION = "1.2.0.1";
	
	public static Logger log = LogManager.getLogger(MODID);

	@SidedProxy(clientSide = "lib.proxy.ClientProxy" , serverSide = "lib.proxy.ServerProxy")
	public static ServerProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
		new NetworkHandler();
		
		new PlayerClassCapability().register();
		
		new DeterminePlayerClass();
		
		new OreDictRegistry().addTo();
	}
}
