package lib.playerclass.capability;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

@Nonnull
public class PlayerClass {

    private PlayerEntity player;

    private String playerClass = "";
    /**
     * suffix added when player is wearing a matching shield from the armor item
     * class
     */
    private String shielded_class = "_shielded";
    /** suffix added when the player is wearing any other shield */
    private String shielded_vanilla = "_vanillaShield";

    public PlayerClass() {

    }

    public PlayerEntity getPlayer() {

        return player;
    }

    public void setPlayer(PlayerEntity newPlayer) {

        this.player = newPlayer;
    }

    public static PlayerClass get(PlayerEntity player) {

        return player.getCapability(PlayerClassCapability.CAPABILITY, null).orElse(null);
    }

    public INBT writeData() {

        CompoundNBT tag = new CompoundNBT();
        return tag;
    }

    public void readData(INBT nbt) {

    }

    public void setPlayerClass(String playerClass) {

        this.playerClass = playerClass;
    }

    public String getPlayerClass() {

        return playerClass;
    }

    public boolean isPlayerClass(String className) {

        return playerClass.contains(className);
    }

    public boolean isShielded() {

        return playerClass.contains(shielded_class);
    }

    public boolean isVanillaShielded() {

        return playerClass.contains(shielded_vanilla);
    }

    /**
     * suffix added when player is wearing a matching shield from the armor item
     * class
     */
    public String classShieldSuffix() {

        return shielded_class;
    }

    /** suffix added when the player is wearing any other shield */
    public String vanillaShieldSuffix() {

        return shielded_vanilla;
    }
}
