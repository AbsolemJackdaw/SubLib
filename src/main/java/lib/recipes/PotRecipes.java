package lib.recipes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PotRecipes {

    private static final PotRecipes INSTANCE = new PotRecipes();
    private Map<RecipeEntry, RecipeEntry> recipes = new HashMap<RecipeEntry, RecipeEntry>();

    private class RecipeEntry {

        int amount;
        Item item;

        public RecipeEntry(ItemStack stack) {

            amount = stack.getCount();
            item = stack.getItem();
        }

        public ItemStack getStack() {

            return new ItemStack(item, amount);
        }

        @Override
        public boolean equals(Object obj) {

            if (!(obj instanceof RecipeEntry))
                return false;

            if (!areEntriesEqual((RecipeEntry) obj, this))
                return false;

            return true;
        }
    }

    public static PotRecipes getInstance() {

        return INSTANCE;
    }

    public void addRecipe(ItemStack entry, ItemStack output) {

        RecipeEntry reEntry = new RecipeEntry(entry);
        RecipeEntry reOutput = new RecipeEntry(output);
        recipes.put(reEntry, reOutput);
    }

    public ItemStack getCookingResult(ItemStack stack) {

        RecipeEntry entry = new RecipeEntry(stack);

        for (Entry<RecipeEntry, RecipeEntry> entry_lookup : recipes.entrySet()) {
            RecipeEntry key = entry_lookup.getKey();
            if (areEntriesEqual(entry, key)) {
                return entry_lookup.getValue().getStack().copy();
            }
        }
        return ItemStack.EMPTY;
    }

    private boolean areEntriesEqual(RecipeEntry a, RecipeEntry b) {

        return a.item == b.item;

    }
}
