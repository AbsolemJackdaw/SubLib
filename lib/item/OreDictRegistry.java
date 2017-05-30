package lib.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictRegistry {

	public void addTo(){
		Item rawMeats[] = new Item[]{Items.BEEF, Items.PORKCHOP, Items.RABBIT, Items.MUTTON, Items.CHICKEN};
		Item cookedMeats[] = new Item[]{Items.COOKED_BEEF, Items.COOKED_PORKCHOP, Items.COOKED_RABBIT, Items.COOKED_MUTTON, Items.COOKED_CHICKEN};

		for(Item meat : rawMeats)
			if(meat != Items.CHICKEN) 
				OreDictionary.registerOre("foodRedMeatRaw", meat);
		for(Item meat : cookedMeats)
			if(meat != Items.COOKED_CHICKEN)
				OreDictionary.registerOre("foodRedMeatCooked", meat);

		for(Item meat : rawMeats)
			OreDictionary.registerOre("foodMeatRaw", meat);
		for(Item meat : cookedMeats)
			OreDictionary.registerOre("foodMeatCooked", meat);
		
		//fish is meat too !
		OreDictionary.registerOre("foodMeatRaw", new ItemStack(Items.FISH,1,0));//cod
		OreDictionary.registerOre("foodMeatRaw", new ItemStack(Items.FISH,1,1));//salmon
		OreDictionary.registerOre("foodMeatCooked", new ItemStack(Items.COOKED_FISH,1,0));//cod
		OreDictionary.registerOre("foodMeatCooked", new ItemStack(Items.COOKED_FISH,1,1));//salmon

		//fish is also fish
		OreDictionary.registerOre("foodFishRaw", new ItemStack(Items.FISH,1,0));//cod
		OreDictionary.registerOre("foodFishRaw", new ItemStack(Items.FISH,1,1));//salmon

		OreDictionary.registerOre("foodFishCooked", new ItemStack(Items.COOKED_FISH,1,0));//cod
		OreDictionary.registerOre("foodFishCooked", new ItemStack(Items.COOKED_FISH,1,1));//salmon


		OreDictionary.registerOre("cropVegetable", Items.POTATO);
		OreDictionary.registerOre("cropVegetable", Items.BEETROOT);
		OreDictionary.registerOre("cropVegetable", Items.CARROT);
		OreDictionary.registerOre("cropVegetable", Blocks.PUMPKIN);

		OreDictionary.registerOre("cropMushroom", Blocks.BROWN_MUSHROOM);
		OreDictionary.registerOre("cropMushroom", Blocks.RED_MUSHROOM);

		OreDictionary.registerOre("dustSugar", Items.SUGAR);

		OreDictionary.registerOre("cropFruit", Items.APPLE);
		OreDictionary.registerOre("cropFruit", Items.MELON);

		OreDictionary.registerOre("itemFish", Items.FISH);
	}
}
