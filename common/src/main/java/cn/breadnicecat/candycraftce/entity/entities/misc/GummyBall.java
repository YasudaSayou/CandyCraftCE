package cn.breadnicecat.candycraftce.entity.entities.misc;

import cn.breadnicecat.candycraftce.entity.CEntityTypes;
import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class GummyBall extends ThrowableItemProjectile {
	public boolean isChargedShot;

	public GummyBall(EntityType<? extends GummyBall> entityType, Level level) {
		super(entityType, level);
	}

	public GummyBall(Level level, LivingEntity shooter) {
		super(CEntityTypes.GUMMY_BALL.get(), shooter, level);
	}

	public GummyBall(Level level, double x, double y, double z) {
		super(CEntityTypes.GUMMY_BALL.get(), x, y, z, level);
	}

	public void shootFromEntity(LivingEntity shooter) {
		shootFromEntity(shooter, 0, 0, 1.5F, 1.0F);
	}

	public void shootFromEntity(LivingEntity shooter, float pitchOffset, float yawOffset, float velocity, float inaccuracy) {
		this.shootFromRotation(shooter, shooter.getXRot() + pitchOffset, shooter.getYRot() + yawOffset, 0, velocity, inaccuracy);
	}

	@Override
	protected Item getDefaultItem() {
		return CItems.GUMMY_BALL.get();
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == 3 && level().isClientSide) {
			for (int i = 0; i < 8; i++) {
				level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, getItem()), getX(), getY(), getZ(),
				(random.nextFloat() - 0.5D) * 0.1D,
				(random.nextFloat() - 0.5D) * 0.1D,
				(random.nextFloat() - 0.5D) * 0.1D);
			}
			return;
		}
		super.handleEntityEvent(id);
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		if (!level().isClientSide && result.getEntity() instanceof LivingEntity living) {
			if (isChargedShot) living.invulnerableTime = 0;
			living.hurt(this.damageSources().thrown(this, this.getOwner()), 1.0F);
			living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
		}
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!level().isClientSide) {
			this.level().broadcastEntityEvent(this, (byte) 3);
			this.discard();
		}
	}
}