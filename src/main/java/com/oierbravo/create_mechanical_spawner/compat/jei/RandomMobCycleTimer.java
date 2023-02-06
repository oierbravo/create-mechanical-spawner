package com.oierbravo.create_mechanical_spawner.compat.jei;

import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.Optional;

public class RandomMobCycleTimer {
    private static final int cycleTime = 1000;
    private long startTime;
    private long drawTime;
    private long pausedDuration = 0;

    public RandomMobCycleTimer(int offset) {
        long time = System.currentTimeMillis();
        this.startTime = time - ((long) offset * cycleTime);
        this.drawTime = time;
    }

    public LivingEntity getCycledLivingEntity(List<LivingEntity> list) {
        /*if (list.isEmpty()) {
            return Optional.empty();
        }*/
        long index = ((drawTime - startTime) / cycleTime) % list.size();
        return list.get(Math.toIntExact(index));
    }

    public void onDraw() {
        if (!Screen.hasShiftDown()) {
            if (pausedDuration > 0) {
                startTime += pausedDuration;
                pausedDuration = 0;
            }
            drawTime = System.currentTimeMillis();
        } else {
            pausedDuration = System.currentTimeMillis() - drawTime;
        }
    }
}
