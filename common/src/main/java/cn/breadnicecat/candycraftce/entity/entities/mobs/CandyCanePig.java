package cn.breadnicecat.candycraftce.entity.entities.mobs;

import cn.breadnicecat.candycraftce.entity.CEntityTypes;
import cn.breadnicecat.candycraftce.item.CItems;
import cn.breadnicecat.candycraftce.item.CItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2024/8/1 上午8:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CandyCanePig extends Pig {

	public CandyCanePig(EntityType<? extends Pig> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
		this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, Ingredient.of(CItems.DRAGIBUS_ON_A_STICK), false));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, i -> i.is(CItemTags.CANDY_CANE_PIG_FOOD), false));
		this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.is(CItemTags.CANDY_CANE_PIG_FOOD);
	}

	@Nullable
	@Override
	public CandyCanePig getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		return CEntityTypes.CANDY_CANE_PIG.get().create(level);
	}

	@Nullable
	@Override
	public LivingEntity getControllingPassenger() {
		Entity entity = this.getFirstPassenger();
		if (this.isSaddled() && entity instanceof Player player && player.isHolding(CItems.DRAGIBUS_ON_A_STICK.get())) {
			return player;
		}
		if (!this.isNoAi() && entity instanceof Mob mob && entity.canControlVehicle()) {
			return mob;
		}
		return null;
	}

	@Override
	public void thunderHit(ServerLevel level, LightningBolt lightning) {
		//no zombified_piglin
	}
}