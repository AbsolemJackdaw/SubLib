package lib.util;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryHelper {

    private static final Random RANDOM = new Random();

    public static void dropInventoryItems(World worldIn, BlockPos pos, ItemStackHandler inventory) {

        dropInventoryItems(worldIn, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), inventory);
    }

    public static void dropInventoryItems(World worldIn, Entity entityAt, ItemStackHandler inventory) {

        dropInventoryItems(worldIn, entityAt.posX, entityAt.posY, entityAt.posZ, inventory);
    }

    private static void dropInventoryItems(World worldIn, double x, double y, double z, ItemStackHandler inventory) {

        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack itemstack = inventory.getStackInSlot(i);

            if (!itemstack.isEmpty()) {
                spawnItemStack(worldIn, x, y, z, itemstack);
            }
        }
    }

    public static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack) {

        float f = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;

        while (!stack.isEmpty()) {
            ItemEntity entityitem = new ItemEntity(worldIn, x + (double) f, y + (double) f1, z + (double) f2,
                    stack.split(RANDOM.nextInt(21) + 10));
            double motX = RANDOM.nextGaussian() * 0.05000000074505806D;
            double motY = RANDOM.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D;
            double motZ = RANDOM.nextGaussian() * 0.05000000074505806D;

            entityitem.setMotion(new Vec3d(motX, motY, motZ));

            worldIn.addEntity(entityitem);
        }
    }
}