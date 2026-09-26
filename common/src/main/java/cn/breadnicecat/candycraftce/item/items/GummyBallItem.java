package cn.breadnicecat.candycraftce.item.items;

import cn.breadnicecat.candycraftce.entity.entities.misc.GummyBall;
import cn.breadnicecat.candycraftce.sound.CSoundEvents;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class GummyBallItem extends Item implements ProjectileItem {
	public GummyBallItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		level.playSound(null, player.getX(), player.getY(), player.getZ(), CSoundEvents.GUMMY_BALL_THROW.getHolder(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!level.isClientSide()) {
			GummyBall ball = new GummyBall(level, player);
			ball.setItem(stack);
			ball.shootFromEntity(player);
			level.addFreshEntity(ball);
		}
		player.awardStat(Stats.ITEM_USED.get(this));
		stack.consume(1, player);
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	@Override
	public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
		GummyBall ball = new GummyBall(level, pos.x(), pos.y(), pos.z());
		ball.setItem(stack);
		return ball;
	}
}