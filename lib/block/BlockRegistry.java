package lib.block;

import lib.item.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRegistry {
	/**registers block only. doesn't register itemblock. must be registered apart !*/
	public static void registerBlock(Block block){
		GameRegistry.register(block);
	}
}
