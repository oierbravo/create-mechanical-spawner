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

## Pending features
- Configurable Mob Deny List
- Specific liquid for each Mob
- KubeJS integration
- Ponder scene
- Nicer model?

## Spawn recipes
- `fluid` required.
- `processingTime` optional. Defaults: 200
- `mob` optional. Defaults: biome dependant.
  Random generation (already in the mod)
```
{
  "type": "create_mechanical_spawner:spawner",
  "fluid": {
    "fluid": "create_mechanical_spawner:spawn_fluid",
    "amount": 1000
  },
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

**Thanks to the Creators of Create.**

Code inspiration from the [Create](https://www.curseforge.com/minecraft/mc-mods/create "Create") mod itself.

