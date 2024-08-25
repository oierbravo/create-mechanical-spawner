package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;

public class ModPonders {
    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(CreateMechanicalSpawner.MODID);

    public static void register() {

        /*HELPER.addStoryBoard(ModBlocks.MECHANICAL_SPAWNER, "spawner", PonderScenes::spawner, AllPonderTags.KINETIC_APPLIANCES);

        PonderRegistry.TAGS.forTag(AllPonderTags.KINETIC_APPLIANCES)
                .add(ModBlocks.MECHANICAL_SPAWNER);*/
    }
}
