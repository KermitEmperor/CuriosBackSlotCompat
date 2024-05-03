package com.kermitemperor.curiosbackslotcompat.mixin;

import com.kermitemperor.curiosbackslot.render.BackWeaponRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@Mixin(BackWeaponRenderer.class)
public abstract class BackWeaponRendererMixin {
    @Shadow
    protected abstract boolean hasArmor(AbstractClientPlayer livingEntity);

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V",
            at = @At(value = "INVOKE", ordinal = 3 ,target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderStatic(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;IILcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    public void beforeNormalItemRender(PoseStack matrixStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer livingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci, Minecraft mc, ItemStack stack, Item item, PlayerModel playerModel, ItemRenderer itemRenderer, BakedModel model) {
        String itemName = Objects.requireNonNull(item.getRegistryName()).toString();
        switch (itemName) {
            case "tconstruct:cleaver" -> {
                matrixStack.translate(0.125, -0.25, 0);
            }
            case "tconstruct:plate_shield" -> {
                matrixStack.mulPose(Vector3f.ZN.rotationDegrees(90.0F));
                //matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
                matrixStack.translate(0, -0.15, 0);
                matrixStack.scale(0.65F, 0.65F, 0.65F);
                if (this.hasArmor(livingEntity)) {
                    matrixStack.translate(-0.01, 0.0, 0.0);
                }
            }
        }
    }

}
