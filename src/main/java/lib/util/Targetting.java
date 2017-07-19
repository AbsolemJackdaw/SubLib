package lib.util;

import java.util.List;

import com.google.common.base.Predicates;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author Richard Smith <rich1051414@gmail.com>
 */
public class Targetting {


	/**@params parDistance : distance of blocks to check
	 * return the entity you are looking at. null if no entity is looked at within parDistance blocks
	 * */
	@SideOnly(Side.CLIENT)
	public static EntityLivingBase rayTraceClientSide(double parDistance) {

		net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getMinecraft();

		if(! (mc.getRenderViewEntity() instanceof EntityLivingBase))
			return null;

		EntityLivingBase viewEntity = (EntityLivingBase)mc.getRenderViewEntity();
		World worldObj = viewEntity.world;
		Entity Return = null;

		if (viewEntity != null) {

			RayTraceResult objectMouseOver = viewEntity.rayTrace(parDistance, 0.5F);
			Vec3d playerPosition = viewEntity.getPositionEyes(1.0F);

			double farDist = parDistance;
			if (objectMouseOver != null) {
				farDist = objectMouseOver.hitVec.distanceTo(playerPosition);
			}
			double closest = farDist;

			Vec3d dirVec = viewEntity.getLookVec();
			Vec3d lookFar = playerPosition.addVector(dirVec.x
					* parDistance, dirVec.y * parDistance, dirVec.z
					* parDistance);

			List<EntityLivingBase> targettedEntities = worldObj.getEntitiesWithinAABB(
					EntityLivingBase.class,
					viewEntity.getEntityBoundingBox().
					grow(dirVec.x * parDistance,
							dirVec.y * parDistance,
							dirVec.z * parDistance).expand(0.1,0.1, 0.1));

			targettedEntities.remove(viewEntity);

			for (EntityLivingBase targettedEntity : targettedEntities) {
				if (targettedEntity != null) {
					double precheck = viewEntity.getDistanceToEntity(targettedEntity);

					RayTraceResult mopElIntercept = targettedEntity.getEntityBoundingBox().
							calculateIntercept(playerPosition, lookFar);

					if (mopElIntercept != null) {
						if (precheck < closest) {
							Return = targettedEntity;
							closest = precheck;
						}
					}
				}
			}
		}
		if ((Return != null) && (Return instanceof EntityLivingBase)) {
			return (EntityLivingBase) Return;
		}
		return null;
	}


	// Butchered from the player getMouseOver()
	private static RayTraceResult getEntityIntercept(EntityPlayer player, Vec3d start, Vec3d look, Vec3d end, RayTraceResult mop){
		double distance = end.distanceTo(start);

		if (mop != null)
		{
			distance = mop.hitVec.distanceTo(start);
		}

		Vec3d direction = new Vec3d(
				look.x * distance,
				look.y * distance,
				look.z * distance);

		end = start.add(direction);

		Vec3d hitPosition = null;
		List<Entity> list = player.world.getEntitiesInAABBexcluding(player,
				player.getEntityBoundingBox()
				.grow(direction.x, direction.y, direction.z)
				.expand(1.0, 1.0, 1.0), Predicates.and(EntitySelectors.NOT_SPECTATING,
						Entity::canBeCollidedWith));

		double distanceToEntity = distance;
		Entity pointedEntity = null;
		for (Entity entity : list)
		{
			double border = entity.getCollisionBorderSize();
			AxisAlignedBB bounds = entity.getEntityBoundingBox().expand(border, border, border);
			RayTraceResult intercept = bounds.calculateIntercept(start, end);

			if (bounds.contains(start))
			{
				if (distanceToEntity >= 0.0D)
				{
					pointedEntity = entity;
					hitPosition = intercept == null ? start : intercept.hitVec;
					distanceToEntity = 0.0D;
				}
			}
			else if (intercept != null)
			{
				double interceptDistance = start.distanceTo(intercept.hitVec);

				if (interceptDistance < distanceToEntity || distanceToEntity == 0.0D)
				{
					if (entity == player.getRidingEntity() && !player.canRiderInteract())
					{
						if (distanceToEntity == 0.0D)
						{
							pointedEntity = entity;
							hitPosition = intercept.hitVec;
						}
					}
					else
					{
						pointedEntity = entity;
						hitPosition = intercept.hitVec;
						distanceToEntity = interceptDistance;
					}
				}
			}
		}

		if (pointedEntity != null)
		{
			if (distanceToEntity < distance)
			{
				if (start.distanceTo(hitPosition) < distance)
				{
					return new RayTraceResult(pointedEntity, hitPosition);
				}
			}
		}

		return mop;
	}

	/**raytrace server side*/
	public static RayTraceResult rayTraceServerSide(EntityPlayer player, float partialTicks)
	{
		Vec3d start;
		Vec3d end;

		float maxDistance = 10;

		if (partialTicks < 1)
		{
			double sx = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
			double sy = player.prevPosY + (player.posY - player.prevPosY) * partialTicks + player.getEyeHeight();
			double sz = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
			start = new Vec3d(sx, sy, sz);
		}
		else
		{
			start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		}

		Vec3d look = player.getLook(partialTicks);
		end = start.addVector(look.x * maxDistance, look.y * maxDistance, look.z * maxDistance);

		RayTraceResult mop = player.world.rayTraceBlocks(start, end, false, true, false);

		mop = getEntityIntercept(player, start, look, end, mop);

		if (mop != null && mop.hitVec != null)
		{
			end = mop.hitVec;
		}

		return mop;
	}
}