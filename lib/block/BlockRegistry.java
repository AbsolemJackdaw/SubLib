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
	
	@Deprecated
	public static void registerBlockWithItem(Block block){
		GameRegistry.register(block);
		ItemBlock item = new ItemBlock(block);
		ItemRegistry.registerItem(item);
		//TODO register model for item to same name as block
	}
	
	@Deprecated
	public void registerBlockWithItem(Block block, ItemBlock item){
		GameRegistry.register(block);
		ItemRegistry.registerItem(item);
		//TODO register model for item to same name as block
	}
}
