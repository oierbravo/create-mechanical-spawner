# Create Mechanical Spawner

A mechanical spawner block. Generates Mobs with rotation power.
This mod it's meant to be used in modpacks. 

## Disclaimer: Currently in active development. 1.18.2 version may come after finishing pending features.


## Features
- Spawn liquid
- Biome dependant random generation
- Configurable stress
- Configurable minimum speed
- Configurable fluid capacity
- Spawn liquid recipe.
- Spawner block recipe.
- JEI integration
- KubeJS integration
- Specific liquid for Mobs


## Pending features
- Ponder scene
- Nicer model?
- Liquid textures (still, flowing)
- Tag support for recipes
- 
## Spawn recipes
- `fluid` required.
- `mob` required. `"random"` or any entity like `"minecraft:skeleton"`
- `processingTime` optional. Defaults: 200
- 

### Random generation (already in the mod)
```
{
  "type": "create_mechanical_spawner:spawner",
  "fluid": {
    "fluid": "create_mechanical_spawner:spawn_fluid",
    "amount": 1000
  },
  "mob": "random",
  "processingTime": 10000
}
```
Specific Mob example
```
{
  "type": "create_mechanical_spawner:spawner",
  "fluid": {
    "fluid": "minecraft:water",
    "amount": 10
  },
  "mob": "minecraft:skeleton",
  "processingTime": 10000
}
```

## KubeJS support (Server script)

```
//Random Mob (WIP) wierd things happen for now.
event.recipes.createMechanicalSpawnerSpawner("random",Fluid.of('minecraft:water', 700)).processingTime(8000);
//Specific MOB
event.recipes.createMechanicalSpawnerSpawner("minecraft:skeleton",Fluid.of('minecraft:water', 700)).processingTime(8000);
```

- Alternative (custom recipe)
```
event.custom({
    type:"create_mechanical_spawner:spawner",
    mob: "minecraft:zombie",
    fluid: {
        fluid: "minecraft:lava",
        amount: 500,
    },
    processingTime: 20000
});
```
**Thanks to the Creators of Create.**

Code inspiration from the [Create](https://www.curseforge.com/minecraft/mc-mods/create "Create") mod itself & [Mrbysco](https://www.curseforge.com/members/mrbysco) 

