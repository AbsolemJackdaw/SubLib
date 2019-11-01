package lib.util;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientReferences {

    private BipedModel<?> armorModel;

    public static PlayerEntity getClientPlayer() {

        return Minecraft.getInstance().player;
    }

    public static PlayerEntity getClientPlayerByUUID(UUID uuid) {

        return Minecraft.getInstance().world.getPlayerByUuid(uuid);
    }

    public static EntityRendererManager getRenderManager() {

        return Minecraft.getInstance().getRenderManager();
    }

    public static World getClientWorld() {

        return Minecraft.getInstance().world;
    }

    public void setArmorModel(BipedModel<?> armorModel) {

        this.armorModel = armorModel;
    }

    public BipedModel<?> getArmorModel() {

        return armorModel;
    }
}
