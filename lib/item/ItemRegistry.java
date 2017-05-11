package lib.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegistry {

	/**basic registering for model*/
	public static void registerVanillaRender(Item item, String location, int metadata){
		ModelLoader.setCustomModelResourceLocation(
				item,
				metadata,
				new ModelResourceLocation(location));
	}

	/**basic registering for model*/
	public static void registerRender(Item item, String location, String ModId){
		registerRender(item, location, ModId, 0);
	}

	/**with meta*/
	public static void registerRender(Item item, String location, String ModId, int metadata){
		ModelLoader.setCustomModelResourceLocation(item,metadata,new ModelResourceLocation((ModId+":"+location), "inventory"));
	}

	public static void registerItem(Item item){
		GameRegistry.register(item);
	}

	private static String [][] recipePatterns = new String[][] { 
		{ "XXX", "X X" },
		{ "X X", "XXX", "XXX" }, 
		{ "XXX", "X X", "X X" },
		{ "X X", "X X" } 
	};

	public static void addArmorRecipe(Item result, ItemStack armorItem, EntityEquipmentSlot slot){
		int index = 3-slot.getIndex();

		GameRegistry.addRecipe(new ItemStack(result), new Object[] {
				recipePatterns[index], 'X', armorItem });
	}
	
	public static void addArmorRecipe(Item result, Item armorItem, EntityEquipmentSlot slot){
		int index = 3-slot.getIndex();

		GameRegistry.addRecipe(new ItemStack(result), new Object[] {
				recipePatterns[index], 'X', armorItem });
	}
}
