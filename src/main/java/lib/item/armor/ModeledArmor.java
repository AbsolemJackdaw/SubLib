package lib.item.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ModeledArmor extends ArmorItem {

    private String tex_loc;

    public ModeledArmor(EquipmentSlotType slot, ArmorMaterial mats, String texture_name) {

        super(mats, slot, null);

        setRegistryName(texture_name);
        tex_loc = "armor/" + texture_name;
    }

    /**
     * String containing the folder location in which the texture file for the armor
     * is located as : armor/-texture_name-
     */
    public String getModeltextureLocation() {

        return tex_loc;
    }

    /** returns the name of the class from this full set of armor */
    public abstract String armorClassName();

    /**
     * This returns the item that is ought to be used as shield.
     * When full armor is worn and this item is in the offhandslot, then the player will get
     * the status of shielded class, rather then just the classname
     */
    public abstract Item getLinkedShieldItem();

    /**
     * Called to get the correct armor model for each
     * {@link net.minecraft.inventory.EquipmentSlotType#HEAD HEAD},
     * {@link net.minecraft.inventory.EquipmentSlotType#CHEST CHEST},
     * {@link net.minecraft.inventory.EquipmentSlotType#LEGS LEGS},
     * {@link net.minecraft.inventory.EquipmentSlotType#FEET FEET}. It is advised to
     * use a switch for {@link net.minecarft.inventory.EquipementSlotType
     * EquipementSlotType} and set a BipedModel trough a client proxy (server likes
     * to complain about the import) trough {@link #setArmorModel(BipedModel)
     * setArmorModel}.
     */
    protected abstract void get3DArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot);

    private BipedModel<?> armorModel;

    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack,
            EquipmentSlotType armorSlot, A _default) {

        if (itemStack != null) {
            /* set armor model */
            get3DArmorModel(entityLiving, itemStack, armorSlot);

            if (armorModel != null) {
                armorModel.bipedHead.showModel = armorSlot == EquipmentSlotType.HEAD;
                armorModel.bipedHeadwear.showModel = armorSlot == EquipmentSlotType.HEAD;
                armorModel.bipedBody.showModel = (armorSlot == EquipmentSlotType.CHEST)
                        || (armorSlot == EquipmentSlotType.CHEST);
                armorModel.bipedRightArm.showModel = armorSlot == EquipmentSlotType.CHEST;
                armorModel.bipedLeftArm.showModel = armorSlot == EquipmentSlotType.CHEST;
                armorModel.bipedRightLeg.showModel = (armorSlot == EquipmentSlotType.LEGS)
                        || (armorSlot == EquipmentSlotType.FEET);
                armorModel.bipedLeftLeg.showModel = (armorSlot == EquipmentSlotType.LEGS)
                        || (armorSlot == EquipmentSlotType.FEET);

                armorModel.isSneak = _default.isSneak;
                armorModel.isChild = _default.isChild;
                armorModel.rightArmPose = _default.rightArmPose;
                armorModel.leftArmPose = _default.leftArmPose;

                return (A)armorModel;
            }
        }
        return _default;
    }

    public void setArmorModel(BipedModel<?> armorModel) {

        // do not add a null check here.
        this.armorModel = armorModel;
    }
}
