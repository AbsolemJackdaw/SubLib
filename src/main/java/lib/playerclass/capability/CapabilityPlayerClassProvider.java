package lib.playerclass.capability;

import lib.Lib;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityPlayerClassProvider implements ICapabilitySerializable<CompoundNBT> {

    /**
     * Unique key to identify the attached provider from others
     */
    public static final ResourceLocation KEY = new ResourceLocation(Lib.MODID, "determinator");

    /**
     * The instance that we are providing
     */
    final PlayerClass data = new PlayerClass();

    /**
     * gets called before world is initiated. player.worldObj will return null here!
     */
    public CapabilityPlayerClassProvider(PlayerEntity player) {

        data.setPlayer(player);
    }

    @Override
    public CompoundNBT serializeNBT() {

        return (CompoundNBT) PlayerClassCapability.CAPABILITY.writeNBT(data, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

        PlayerClassCapability.CAPABILITY.readNBT(data, null, nbt);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {

        if (cap == PlayerClassCapability.CAPABILITY)
            return (LazyOptional<T>) LazyOptional.of(this::getImpl);

        return LazyOptional.empty();
    }

    private PlayerClass getImpl() {

        if (data != null) {
            return data;
        }
        return new PlayerClass();
    }

}
