package lib.item.shield;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemCustomShield extends Item {

    public ItemCustomShield(int dmg, ItemGroup tab) {

        this(new Item.Properties().maxStackSize(1).maxDamage(dmg).group(tab));
    }

    public ItemCustomShield(Item.Properties props) {

        super(props);
        this.addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter() {

            @Override
            public float call(ItemStack stack, World world, LivingEntity entity) {

                return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
        DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {

        return super.onItemUse(context);
    }

    /**
     * returns the action that specifies what animation to play when the items is
     * being used
     */
    @Override
    public UseAction getUseAction(ItemStack stack) {

        return UseAction.BLOCK;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getUseDuration(ItemStack stack) {

        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);
    }
}
