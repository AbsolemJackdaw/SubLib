package lib.playerclass;

import lib.item.armor.ModeledArmor;
import lib.playerclass.capability.CapabilityPlayerClassProvider;
import lib.playerclass.capability.PlayerClass;
import lib.playerclass.network.NetworkHandler;
import lib.playerclass.network.SyncPlayerClassClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class DeterminePlayerClass {

    private static final int BOOTS = 0;
    private static final int LEGS = 1;
    private static final int CHEST = 2;
    private static final int HELM = 3;

    
    public DeterminePlayerClass() {

        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onAttach(AttachCapabilitiesEvent<Entity> event) {

        final Object object = event.getObject();

        if (object instanceof PlayerEntity)
            event.addCapability(CapabilityPlayerClassProvider.KEY,
                    new CapabilityPlayerClassProvider((PlayerEntity) object));

    }

    @SubscribeEvent
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event) {

        if (event.getEntityLiving() == null)
            return;
        if (!(event.getEntityLiving() instanceof PlayerEntity))
            return;

        if (!event.getEntityLiving().world.isRemote) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            determinePlayerClass(player);
        }
    }

    private void determinePlayerClass(PlayerEntity player) {

        String classname = "noClass";
        String shieldName = "_noShield";

        ItemStack offhand = player.getHeldItemOffhand();

        PlayerClass playerclass = PlayerClass.get(player);

        if (!offhand.isEmpty()) {
            if (offhand.getItem() instanceof ShieldItem) {
                shieldName = playerclass.vanillaShieldSuffix();
                playerclass.setPlayerClass(classname + shieldName);// noclass vanilla shield
            }
            // custom shields get handled later
        } else
            playerclass.setPlayerClass(classname + shieldName);// noclass noshield

        /* checking armor... */
        for (ItemStack is : player.inventory.armorInventory) {
            if (is == null) {
                playerclass.setPlayerClass(classname + shieldName);// no class (no)vanilla shield
                sync(player, classname, shieldName);
                return;// if one of the items is null, jump out. all items need to be worn
            } else // if there is one item that is no AbstractArmor, skip the setting of the player
                   // class
            if (!(is.getItem() instanceof ModeledArmor)) {
                playerclass.setPlayerClass(classname + shieldName);// no class (no)vanilla shield
                sync(player, classname, shieldName);
                return;// no need to check for the next if one item is not class armour
            }
        }
        // at this point, all armour is class armour ! no need to check for it.

        // check if all armor is from the same class
        String a = ((ModeledArmor) player.inventory.armorItemInSlot(HELM).getItem()).armorClassName();
        String b = ((ModeledArmor) player.inventory.armorItemInSlot(CHEST).getItem()).armorClassName();
        String c = ((ModeledArmor) player.inventory.armorItemInSlot(LEGS).getItem()).armorClassName();
        String d = ((ModeledArmor) player.inventory.armorItemInSlot(BOOTS).getItem()).armorClassName();

        if (a.equals(b) && a.equals(c) && a.equals(d))
            classname = a;
        else {
            playerclass.setPlayerClass(classname + shieldName);// noclass (no)vanilla shield
            sync(player, classname, shieldName);
            return; // if there is a difference, jump out
        }
        // check if any other shields
        ModeledArmor helm = ((ModeledArmor) player.inventory.getStackInSlot(HELM).getItem());
        if (!offhand.isEmpty() && shieldName.equals("_noShield") && helm.getLinkedShieldItem() != Items.AIR)
            if (offhand.getItem() == helm.getLinkedShieldItem())
                shieldName = PlayerClass.get(player).classShieldSuffix();

        if (!playerclass.getPlayerClass().equals(classname + shieldName))
            playerclass.setPlayerClass(classname + shieldName);

        sync(player, classname, shieldName);
    }

    private void sync(PlayerEntity player, String classname, String shieldName) {
        String name = classname + shieldName;
        if (player instanceof ServerPlayerEntity)
            NetworkHandler.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player),
                    new SyncPlayerClassClient(name));

    }
}
