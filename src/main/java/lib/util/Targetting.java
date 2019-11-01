package lib.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 *
 * @author Richard Smith <rich1051414@gmail.com>
 */
public class Targetting {

    /**
     * @params parDistance : distance of blocks to check return the entity you are
     *         looking at. null if no entity is looked at within parDistance blocks
     */
    @OnlyIn(Dist.CLIENT)
    public static LivingEntity rayTraceClientSide(double parDistance) {

        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();

        if (!(mc.getRenderViewEntity() instanceof LivingEntity))
            return null;

        LivingEntity viewEntity = (LivingEntity) mc.getRenderViewEntity();
        World worldObj = viewEntity.world;
        Entity Return = null;

        if (viewEntity != null) {

            RayTraceResult objectMouseOver = viewEntity.func_213324_a(parDistance, 0.5f, true); // this true may be
            // false. idk yet
            // rayTrace(parDistance,
            // 0.5F);
            Vec3d playerPosition = viewEntity.getEyePosition(1.0F);

            double farDist = parDistance;
            if (objectMouseOver != null) {
                farDist = objectMouseOver.getHitVec().distanceTo(playerPosition);
            }
            double closest = farDist;

            Vec3d dirVec = viewEntity.getLookVec();
            Vec3d lookFar = playerPosition.add(dirVec.x * parDistance, dirVec.y * parDistance, dirVec.z * parDistance);

            List<LivingEntity> targettedEntities = worldObj.getEntitiesWithinAABB(LivingEntity.class,
                    viewEntity.getBoundingBox()
                            .grow(dirVec.x * parDistance, dirVec.y * parDistance, dirVec.z * parDistance)
                            .expand(0.1, 0.1, 0.1));

            targettedEntities.remove(viewEntity);

            for (LivingEntity targettedEntity : targettedEntities) {
                if (targettedEntity != null) {
                    double precheck = viewEntity.getDistance(targettedEntity);

                    RayTraceResult mopElIntercept = objectMouseOver; // TODO fix this

                    // targettedEntity.getBoundingBox().
                    // calculateIntercept(playerPosition, lookFar);

                    if (mopElIntercept != null) {
                        if (precheck < closest) {
                            Return = targettedEntity;
                            closest = precheck;
                        }
                    }
                }
            }
        }
        if ((Return != null) && (Return instanceof LivingEntity)) {
            return (LivingEntity) Return;
        }
        return null;
    }

    // Butchered from the player getMouseOver()
    private static RayTraceResult getEntityIntercept(PlayerEntity player, Vec3d start, Vec3d look, Vec3d end,
            RayTraceResult mop) {

        // double distance = end.distanceTo(start);
        //
        // if (mop != null) {
        // distance = mop.getHitVec().distanceTo(start);
        // }
        //
        // Vec3d direction = new Vec3d(look.x * distance, look.y * distance, look.z *
        // distance);
        //
        // end = start.add(direction);
        //
        // Vec3d hitPosition = null;
        // List<Entity> list = player.world.getEntitiesInAABBexcluding(player,
        // player.getBoundingBox().grow(direction.x, direction.y,
        // direction.z).expand(1.0, 1.0, 1.0),
        // Predicates.and(EntitySelector.NOT_SPECTATING, Entity::canBeCollidedWith));
        //
        // double distanceToEntity = distance;
        // Entity pointedEntity = null;
        // for (Entity entity : list) {
        // double border = entity.getCollisionBorderSize();
        // AxisAlignedBB bounds = entity.getBoundingBox().expand(border, border,
        // border);
        // RayTraceResult intercept = bounds.calculateIntercept(start, end);
        //
        // if (bounds.contains(start)) {
        // if (distanceToEntity >= 0.0D) {
        // pointedEntity = entity;
        // hitPosition = intercept == null ? start : intercept.getHitVec();
        // distanceToEntity = 0.0D;
        // }
        // } else if (intercept != null) {
        // double interceptDistance = start.distanceTo(intercept.getHitVec());
        //
        // if (interceptDistance < distanceToEntity || distanceToEntity == 0.0D) {
        // if (entity == player.getRidingEntity() && !player.canRiderInteract()) {
        // if (distanceToEntity == 0.0D) {
        // pointedEntity = entity;
        // hitPosition = intercept.getHitVec();
        // }
        // } else {
        // pointedEntity = entity;
        // hitPosition = intercept.getHitVec();
        // distanceToEntity = interceptDistance;
        // }
        // }
        // }
        // }
        //
        // if (pointedEntity != null) {
        // if (distanceToEntity < distance) {
        // if (start.distanceTo(hitPosition) < distance) {
        // return new RayTraceResult(pointedEntity, hitPosition);
        // }
        // }
        // }

        return mop;
    }

    /** raytrace server side */
    public static RayTraceResult rayTraceServerSide(PlayerEntity player, float partialTicks) {

        //
        // Vec3d start;
        // Vec3d end;
        //
        // float maxDistance = 10;
        //
        // if (partialTicks < 1) {
        // double sx = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
        // double sy = player.prevPosY + (player.posY - player.prevPosY) * partialTicks
        // + player.getEyeHeight();
        // double sz = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
        // start = new Vec3d(sx, sy, sz);
        // } else {
        // start = new Vec3d(player.posX, player.posY + player.getEyeHeight(),
        // player.posZ);
        // }
        //
        // Vec3d look = player.getLook(partialTicks);
        // end = start.add(look.x * maxDistance, look.y * maxDistance, look.z *
        // maxDistance);
        //
        // RayTraceResult mop = player.world.rayTraceBlocks(start, end, false, true,
        // false);
        //
        // mop = getEntityIntercept(player, start, look, end, mop);
        //
        // if (mop != null && mop.getHitVec() != null) {
        // end = mop.getHitVec();
        // }
        //
        // return mop;
        return null;
    }
}