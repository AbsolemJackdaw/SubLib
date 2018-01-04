package lib.entity;

import java.util.function.Function;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

public class EntityRegistry {

	public EntityEntryBuilder registerEntity(Class entityClass, Function<World, Entity> entityFactory,
			String MOD_ID, String entityName, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		return EntityEntryBuilder.create()
				.entity(entityClass)
				.factory(entityFactory)
				.id(new ResourceLocation(MOD_ID, entityName), id)
				.name(entityName)
				.tracker(trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	public void addEgg(EntityEntryBuilder entityEntry,
			int primaryColor, int secondaryColor)
	{
		entityEntry.egg(primaryColor, secondaryColor);
	}
	
	public void addSpawn(EntityEntryBuilder entityEntry, EnumCreatureType type, int weight, int min, int max, Iterable<Biome> biomes){
		entityEntry.spawn(type, weight, min, max, biomes);
	}
}
