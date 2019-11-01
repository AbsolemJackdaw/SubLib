package lib.block;

import net.minecraft.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {

    /**
     * registers block only. doesn't register itemblock. must be registered apart !
     */
    @Deprecated
    public static void registerBlock(Block block) {

        register(block);

    }

    /**
     * registers block only. doesn't register itemblock. must be registered apart !
     */
    public static void register(Block block) {

        ForgeRegistries.BLOCKS.register(block);
    }
}
