package lib.item;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtil {

	//with thanks to :
	//https://github.com/Ordinastie/MalisisCore/blob/1.9.4/src/main/java/net/malisis/core/util/ItemUtils.java
	
	/** Regex pattern to convert a string into an {@link ItemStack}. Format : [modid:]item[@damage[xsize]] */
	public static final Pattern pattern = Pattern.compile("((?<modid>.*?):)?(?<item>[^@]*)(@(?<damage>\\d+|[*])(x(?<size>\\d+))?)?");

	/**
	 * Construct an {@link ItemStack} from a string in the format modid:itemName@damagexstackSize.
	 *
	 * @param str the string
	 * @return the item
	 */
	public static ItemStack getItemStack(String str)
	{
		Matcher matcher = pattern.matcher(str);
		if (!matcher.find())
			return ItemStack.EMPTY;

		String itemString = matcher.group("item");
		if (itemString == null)
			return ItemStack.EMPTY;

		String modid = matcher.group("modid");
		if (modid == null)
			modid = "minecraft";

		int damage = 0;
		String strDamage = matcher.group("damage");
		if (strDamage != null)
			damage = strDamage.equals("*") ? OreDictionary.WILDCARD_VALUE : parse(matcher.group("damage"));
		int size = matcher.group("size") == null ? 1 : parse(matcher.group("size"));
		if (size == 0)
			size = 1;

		Item item = Item.getByNameOrId(modid + ":" + itemString);
		if (item == null)
			return ItemStack.EMPTY;

		return new ItemStack(item, size, damage);
	}
	
	public static int parse(String i){
		try{
			return Integer.parseInt(i);
		}catch(NumberFormatException e){
			FMLLog.warning("Invalid number : " + i);
			e.printStackTrace();
			return 0;
		}
	}
}
