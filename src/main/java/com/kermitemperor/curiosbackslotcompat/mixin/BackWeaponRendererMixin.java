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

import static com.kermitemperor.curiosbackslotcompat.CuriosBackSlotCompat.LOGGER;

@OnlyIn(Dist.CLIENT)
@Mixin(BackWeaponRenderer.class)
public abstract class BackWeaponRendererMixin {
    @Shadow(remap = false)
    protected abstract boolean hasArmor(AbstractClientPlayer livingEntity);

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V",
            at = @At(value = "INVOKE", ordinal = 3 ,target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderStatic(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;IILcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    public void beforeNormalItemRender(PoseStack matrixStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer livingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci, Minecraft mc, ItemStack stack, Item item, PlayerModel playerModel, ItemRenderer itemRenderer, BakedModel model) {
        String itemName = Objects.requireNonNull(item.getRegistryName()).toString();
        switch (itemName) {
            case "tconstruct:cleaver" -> matrixStack.translate(0.125, -0.25, 0);
            case "tconstruct:plate_shield" -> {
                matrixStack.mulPose(Vector3f.ZN.rotationDegrees(90.0F));
                //matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
                matrixStack.translate(0, -0.15, 0);
                matrixStack.scale(0.65F, 0.65F, 0.65F);
                if (this.hasArmor(livingEntity)) {
                    matrixStack.translate(-0.01, 0.0, 0.0);
                }
            }
            case "tetra:modular_greatsword" -> {
                matrixStack.scale(0.65f,0.65f,0.65f);
                matrixStack.translate(0.25,-0.575,0);
            }
            case "create:wrench" -> {
                matrixStack.mulPose(Vector3f.ZN.rotationDegrees(45.0F));
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(15.0f));
                matrixStack.translate(0,0,0.05);
            }
            case "create:potato_cannon" -> {
                matrixStack.mulPose(Vector3f.ZN.rotationDegrees(45.0F));
                matrixStack.translate(0,0,0.12);
            }
            case "create:extendo_grip" -> {
                matrixStack.mulPose(Vector3f.ZN.rotationDegrees(45.0F));
                matrixStack.translate(0,0,0.10);
            }
            case "create:handheld_worldshaper" -> {
                matrixStack.mulPose(Vector3f.ZN.rotationDegrees(45.0F));
                matrixStack.translate(0,0,0.15);
            }
            case "create:wand_of_symmetry" -> {
                matrixStack.mulPose(Vector3f.ZN.rotationDegrees(45.0F));
                matrixStack.scale(0.5f,0.5f,0.5f);
                matrixStack.translate(0,0,0.05);
            }
            case "create_things_and_misc:blaze_balloon_fire",
                    "create_things_and_misc:spout_gun",
                    "create_things_and_misc:spout_gun_water",
                    "create_things_and_misc:spout_gun_lava",
                    "create_things_and_misc:spout_gun_chocolate",
                    "create_things_and_misc:spout_gun_honey",
                    "create_things_and_misc:spout_gun_slime",
                    "create_sa:flamethrower"
                    -> {
                matrixStack.scale(1.2f,1.2f,1.2f);
                matrixStack.translate(0,0,0.10);
                matrixStack.mulPose(Vector3f.ZN.rotationDegrees(45.0F));
            }
            case "create_sa:portable_drill",
                    "create_sa:block_picker"
                    -> {
                matrixStack.scale(0.5f,0.5f,0.5f);
                matrixStack.mulPose(Vector3f.ZN.rotationDegrees(45.0F));
            }
            case "create_sa:grapplin_whisk" -> {
                matrixStack.translate(0,0,0.1);
                matrixStack.mulPose(Vector3f.ZN.rotationDegrees(45.0F));
            }
        }
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V",
            at = @At(value = "INVOKE", ordinal = 2,target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderStatic(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;IILcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    public void beforeNormalBlockItemRender(PoseStack matrixStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer livingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci, Minecraft mc, ItemStack stack, Item item, PlayerModel playerModel, ItemRenderer itemRenderer, BakedModel model) {
        String itemName = Objects.requireNonNull(item.getRegistryName()).toString();
        switch (itemName) {
            case "farmersdelight:skillet" -> {
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(45.0F));
                matrixStack.translate(-0.1,-0.1,0.25);
                matrixStack.scale(0.8f,0.8f,0.8f);
            }
        }
    }
}
