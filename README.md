# Create Mechanical Spawner

A mechanical spawner block. Generates Mobs with rotation power.
This mod it's meant to be used in modpacks. 

## Disclaimer: Currently in active development.


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
- Tag support for recipes
- Nicer model?

## Spawn recipes
- `fluid` required.
- `mob` optional. `"random"` or any entity like `"minecraft:skeleton"`
- `processingTime` optional. Defaults: 200
- 

### Random generation (already in the mod)
```
{
  "type": "create_mechanical_spawner:spawner",
  "fluid": {
    "fluid": "create_mechanical_spawner:spawn_fluid_random",
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
//Random Mob (biome dependant)
event.recipes.createMechanicalSpawnerSpawner(Fluid.of('minecraft:water', 700)).processingTime(8000);
//Specific MOB
event.recipes.createMechanicalSpawnerSpawner(Fluid.of('minecraft:water', 700)).processingTime(8000).mob("minecraft:skeleton;
```

**Thanks to the Creators of Create.**

Code inspiration from the [Create](https://www.curseforge.com/minecraft/mc-mods/create "Create") mod itself & [Mrbysco](https://www.curseforge.com/members/mrbysco) 

