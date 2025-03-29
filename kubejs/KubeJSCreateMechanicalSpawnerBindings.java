package com.oierbravo.create_mechanical_spawner.compat.kubejs;

import dev.latvian.mods.kubejs.recipe.filter.RecipeFilter;

public class KubeJSCreateMechanicalSpawnerBindings {
    static {
        RecipeFilter.PARSE.register((ctx, filters, map) -> {
            var mob = map.get("mob");
            if (mob != null) {
                filters.add(new SpawnerMobFilter(mob.toString()));
            }
        });
    }

}
