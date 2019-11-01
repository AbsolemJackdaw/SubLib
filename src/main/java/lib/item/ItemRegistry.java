package lib.item;

import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {

    // /**basic registering for model*/
    // public static void registerVanillaRender(Item item, String location, int
    // metadata){
    // ModelLoader.setCustomModelResourceLocation(
    // item,
    // metadata,
    // new ModelResourceLocation(location));
    // }
    //
    // /**basic registering for model*/
    // public static void registerRender(Item item, String location, String ModId){
    // registerRender(item, location, ModId, 0);
    // }
    //
    // /**with meta*/
    // public static void registerRender(Item item, String location, String ModId,
    // int metadata){
    // ModelLoader.setCustomModelResourceLocation(item,metadata,new
    // ModelResourceLocation((ModId+":"+location), "inventory"));
    // }

    public void registerItem(Item item, String name) {

        item.setRegistryName(name);
        ForgeRegistries.ITEMS.register(item);
    }

    // private static String [][] armorRecipePaterns = new String[][] {
    // { "XXX", "X X" },
    // { "X X", "XXX", "XXX" },
    // { "XXX", "X X", "X X" },
    // { "X X", "X X" }
    // };
    //
    // public static void addArmorRecipe(String name, String group, Item result,
    // ItemStack armorItem, EquipmentSlotType slot){
    // int index = 3-slot.getIndex();
    // GameRegistry.addShapedRecipe(new ResourceLocation(name), new
    // ResourceLocation(group), new ItemStack(result), armorRecipePaterns[index],
    // 'X', armorItem);
    //
    // }
}
