package com.sonicscholar.diviningrods;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DiviningRodsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RightClickEventSubscriber {

    //todo: divining underground. will we need rightclickitem AND rightclickblock?

    @SubscribeEvent
    public static void rightClickItem(PlayerInteractEvent.RightClickItem rightClickItem) {
        var side = rightClickItem.getSide();
        if(!side.isClient()) {
            return;
        }
        Player p = rightClickItem.getEntity();
        handlePlayerRightClicked(p);
    }

    @SubscribeEvent
    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock rightClickBlock) {
        var side = rightClickBlock.getSide();
        if(!side.isClient()) {
            return;
        }
        Player p = rightClickBlock.getEntity();
        handlePlayerRightClicked(p);
    }


    public static void handlePlayerRightClicked(Player player) {
        //get the player's held item
        //if it's a divining rod (blazerod, do the thing
        //if it's not, do nothing

        var heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        //if held item is a blaze rod, do the thing
        if (heldItem.getItem() != Items.BLAZE_ROD) {
            return;
        }

        // get player's position, and check the surrounding blocks in an 8 block radius for diamond ore
        Vec3 eyePosition = player.getEyePosition();
        var world = player.level();

        for(int x = -8; x <= 8; x++) {
            for (int y = -8; y <= 8; y++) {
                for (int z = -8; z <= 8; z++) {
                    //get the block at position x,y,z
                    // search the world for the block at position x,y,z
                    // if it's diamond ore, do the thing
                    BlockPos blockPos = new BlockPos(player.getBlockX() + x,
                            player.getBlockY() + y,
                            player.getBlockZ() + z);
                    BlockState blockState = world.getBlockState(blockPos);
                    if (blockState.is(Blocks.DIAMOND_ORE)) {
                        //shoot particles in the direction of the diamond ore

                        double px = player.getX();
                        double py = player.getEyeY();
                        double pz = player.getZ();

                        double dx = blockPos.getX() - px;
                        double dy = blockPos.getY() - py + 1;
                        double dz = blockPos.getZ() - pz;

                        world.addParticle(ParticleTypes.ELECTRIC_SPARK, px, py, pz, dx, dy, dz);


                    }

                }
            }
        }

    }
}
