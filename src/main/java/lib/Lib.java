package lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lib.playerclass.DeterminePlayerClass;
import lib.playerclass.capability.PlayerClassCapability;
import lib.playerclass.network.NetworkHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = Lib.MODID)
@EventBusSubscriber(modid = Lib.MODID, bus = Bus.MOD)
public class Lib {

    public static final String MODID = "subcommonlib";
    public static final String NAME = "Subaraki's Common Library";
    public static final String VERSION = "2.0.0.0";

    public static Logger log = LogManager.getLogger(MODID);

    public Lib() {

        // Register doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        // Register commonSetup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        new PlayerClassCapability().register();
        new NetworkHandler();
        new DeterminePlayerClass();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

}
