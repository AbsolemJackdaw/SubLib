package lib.playerclass.capability;

import javax.annotation.Nullable;

import lib.Lib;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityPlayerClassProvider implements ICapabilitySerializable<NBTTagCompound>
{
    /**
     * Unique key to identify the attached provider from others
     */
    public static final ResourceLocation KEY = new ResourceLocation(Lib.MODID, "determinator");

    /**
     * The instance that we are providing
     */
    final PlayerClass data = new PlayerClass();

    /**gets called before world is initiated. player.worldObj will return null here !*/
    public CapabilityPlayerClassProvider(EntityPlayer player){
        data.setPlayer(player);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if (capability == PlayerClassCapability.CAPABILITY)
            return true;
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing){
        if (capability == PlayerClassCapability.CAPABILITY)
            return (T)data;
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT(){
        return (NBTTagCompound) PlayerClassCapability.CAPABILITY.writeNBT(data, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt){
    	PlayerClassCapability.CAPABILITY.readNBT(data, null, nbt);
    }
}
