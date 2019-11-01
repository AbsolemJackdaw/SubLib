package lib.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;

public class EntityRegistry {

    public EntityRegistry() {

    }

    public <T extends Entity> EntityType<T> createEntity(EntityType.IFactory<T> entityFactory, String MOD_ID,
            String entityName, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int sizeX,
            int sizeY) {

        EntityType<T> e = EntityType.Builder.create(entityFactory, EntityClassification.CREATURE)
                .setTrackingRange(trackingRange).setUpdateInterval(updateFrequency).size(sizeX, sizeY)
                .setShouldReceiveVelocityUpdates(sendsVelocityUpdates).build(entityName);

        e.setRegistryName(MOD_ID, entityName);

        return e;
    }

    public Item createEgg(EntityType<?> entityEntry, int primaryColor, int secondaryColor) {

        return new SpawnEggItem(entityEntry, primaryColor, secondaryColor, new Item.Properties().group(ItemGroup.MISC));
    }

    // @Deprecated
    // public void addSpawn(EntityType<?> entityEntry, EnumCreatureType type, int
    // weight, int min, int max,
    // Iterable<Biome> biomes) {
    // entityEntry.spawn(type, weight, min, max, biomes);
    // }
}
