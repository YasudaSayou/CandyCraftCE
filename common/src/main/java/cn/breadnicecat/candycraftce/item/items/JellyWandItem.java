package cn.breadnicecat.candycraftce.item.items;

import cn.breadnicecat.candycraftce.entity.entities.misc.GummyBall;
import cn.breadnicecat.candycraftce.sound.CSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class JellyWandItem extends Item {
	public JellyWandItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return InteractionResultHolder.success(stack);
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity shooter, int timeLeft) {
		int usedTicks = getUseDuration(stack, shooter) - timeLeft;
		if (!level.isClientSide()) {
			level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), CSoundEvents.GUMMY_BALL_THROW.getHolder(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
			if (usedTicks < 60) {
				GummyBall ball = new GummyBall(level, shooter);
				ball.shootFromEntity(shooter);
				level.addFreshEntity(ball);
			} else {
				int count = level.getRandom().nextInt(4) + 3;
				for (int i = 0; i < count; i++) {
					GummyBall ball = new GummyBall(level, shooter);
					ball.isChargedShot = true;
					ball.shootFromEntity(shooter, (level.getRandom().nextFloat() - 0.5F) * 10F, (level.getRandom().nextFloat() - 0.5F) * 10F, 1.5F, 2.15F);
					level.addFreshEntity(ball);
				}
			}
			if (shooter instanceof Player player) player.awardStat(Stats.ITEM_USED.get(this));
		}
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity user) {
		return 72000;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}
}