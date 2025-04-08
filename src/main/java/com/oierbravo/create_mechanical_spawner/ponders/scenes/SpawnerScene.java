package com.oierbravo.create_mechanical_spawner.ponders.scenes;

import com.oierbravo.create_mechanical_spawner.content.components.SpawnerBlockEntity;
import com.oierbravo.create_mechanical_spawner.registrate.ModFluids;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.ParrotElement;
import net.createmod.ponder.api.element.ParrotPose;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.fluids.FluidStack;

public class SpawnerScene {
    public static void spawner(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("spawner", "Spawning living entities");
        scene.configureBasePlate(0, 2, 6);
        scene.showBasePlate();

        BlockPos spawnerPos = util.grid().at(2, 3, 2);
        Selection spawnerSelection = util.select().position(2, 3, 2);
        Selection spawnerShaftSelection = util.select().position(2, 1, 2)
                .add(util.select().position(2, 2, 2));

        Selection mixerPlateShaftSelection = util.select().position(1, 1, 5);


        scene.world().showSection(util.select().layer(1).substract(spawnerShaftSelection).substract(mixerPlateShaftSelection),Direction.DOWN);


        scene.world().showSection(spawnerSelection,Direction.UP);


        Selection allElements = util.select().everywhere();


        Selection mixerSelection = util.select().fromTo(0, 2, 3,3,5 ,5).add(mixerPlateShaftSelection);

        scene.addKeyframe();
        scene.idle(10);
        scene.overlay().showOutlineWithText(spawnerSelection, 50)
                .text("The Spawner uses rotational force and special fluids to spawn entities")
                .pointAt(util.vector().blockSurface(spawnerPos, Direction.WEST)
                        .add(-.5, .4, 0))
                .placeNearTarget();
        scene.idle(60);

        scene.addKeyframe();
        scene.idle(35);

        scene.idle(5);

        scene.world().showSection(spawnerShaftSelection,Direction.DOWN);
        scene.overlay().showText(50)
                .text("Its powered from the bottom")
                .pointAt(util.vector().blockSurface(spawnerPos, Direction.DOWN)
                        .add(-.5, .4, 0))
                .placeNearTarget();
        scene.world().setKineticSpeed(allElements, -64);
        scene.idle(60);

        scene.addKeyframe();
        scene.world().showSection(mixerSelection,Direction.EAST);
        scene.idle(35);
        scene.overlay().showText(50)
                .text("Fluid input cab go in fro any horizontal side")
                .pointAt(util.vector().blockSurface(spawnerPos, Direction.NORTH)
                        .add(-.5, .4, 0))
                .placeNearTarget();
        FluidStack spawnFluid = new FluidStack(ModFluids.ENDERMAN.get().getSource(),1000);
        scene.world().modifyBlockEntity(spawnerPos, SpawnerBlockEntity.class,
                ms -> ms.getInputTank().setFluid(spawnFluid));
        scene.idle(60);

        scene.addKeyframe();
        scene.overlay().showText(50)
                .text("Spawn point can be configured")
                .pointAt(util.vector().blockSurface(spawnerPos, Direction.WEST)
                        .add(-.5, .4, 0))
                .placeNearTarget();
        scene.overlay().showCenteredScrollInput(spawnerPos, Direction.NORTH, 60);
        scene.idle(10);
        scene.overlay().showOutline(PonderPalette.WHITE, new Object(), util.select().position(spawnerPos.relative(Direction.UP)), 5);
        scene.idle(10);
        scene.overlay().showOutline(PonderPalette.WHITE, new Object(), util.select().position(spawnerPos.relative(Direction.UP).relative(Direction.UP)), 100);
        scene.idle(10);
        ElementLink<ParrotElement> flappyBirb = scene.special().createBirb(util.vector().topOf(spawnerPos.relative(Direction.UP)), ParrotPose.FacePointOfInterestPose::new);

        scene.idle(2);
        scene.special().moveParrot(flappyBirb, util.vector().of(0, -1, 0), 20);


        Class<SpawnerBlockEntity> type = SpawnerBlockEntity.class;
        scene.world().modifyBlockEntity(spawnerPos, type, pte -> pte.getScrollValueBehavior().setValue(2));

        scene.idle(80);
        scene.addKeyframe();
        scene.special().hideElement(flappyBirb,Direction.DOWN);
        BlockPos lootCollector = util.grid().at(2, 5, 2);
        Selection lootCollectorSelect = util.select().position(lootCollector);
        Selection lootCollectorSectionSelection = util.select().fromTo(2, 2, 0,2,5 ,2);

        scene.world().showSection(lootCollectorSectionSelection,Direction.NORTH);
        scene.idle(5);
        scene.overlay().showOutlineWithText(lootCollectorSelect,100)
                .text("A loot collector can be placed in the spawn point to automatically collect loot without spawning the entity")
                .pointAt(util.vector().blockSurface(lootCollector, Direction.WEST)
                        .add(-.5, .4, 0))
                .placeNearTarget();
        scene.idle(60);

        scene.markAsFinished();

    }
}
