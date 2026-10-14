package cn.breadnicecat.candycraftce.entity.renderers;

import cn.breadnicecat.candycraftce.entity.entities.misc.GummyBall;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class RendererGummyBall extends ThrownItemRenderer<GummyBall> {
	public RendererGummyBall(EntityRendererProvider.Context context) {
		super(context, 1.0F, true);
	}
}