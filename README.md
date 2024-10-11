# Create Mechanical Spawner

A mechanical spawner block. Generates Mobs with rotation power.
This mod it's meant to be used in modpacks. 

## Disclaimer: Currently in active development.


## Features
- Random Spawn fluid
  - Biome dependant random generation
- Specific liquid for Mobs
- Configurable:
  - Stress
  - Minimum speed
  - Fluid capacity
  - Spawn point max range
- Spawn fluid mixer recipes.
- JEI & Jade integration
- KubeJS integration
- Ponder scene
- Loot collector block
  - Configurable to allow any container or Create Item Vault.
  - Can be disabled.
  - The spawner can be configured to work only with loot collectors.


## Pending features
- Nicer model?
- Compat mobs.

## Spawn recipes
- `fluid` required.
- `mob` optional. `"random"` or any entity like `"minecraft:skeleton"`
- `processingTime` optional. Defaults: 200
- `customLoot` optional. Allows to generate custom loot when used with loot collector blocks.

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

### Disable all default recipes:
```
event.remove({ mod: 'create_mechanical_spawner' })
```
### Disable specific mob recipes:
```
event.remove({ mob: 'minecraft:wolf'});
```

### Add random generation recipe:
```
//Random Mob (biome dependant)
event.recipes.createMechanicalSpawnerSpawner(Fluid.of('minecraft:water', 700)).processingTime(8000);
```
### Add specific mob recipe:
```
//Specific MOB
event.recipes.createMechanicalSpawnerSpawner(Fluid.of('minecraft:water', 700)).processingTime(8000).mob("minecraft:skeleton;
```

### Add recipe with custom loot:
```
event.remove({ mob: 'minecraft:enderman'}); //recommended
event.recipes.createMechanicalSpawnerSpawner(Fluid.of('minecraft:water', 100)).mob("minecraft:enderman").processingTime(1000).customLoot(
	[Item.of('ender_pearl').withChance(0.5),Item.of('diamond').withChance(0.5)]);
```
- It's recommended to disable the default recipe before adding the recipe with custom loot.

**Thanks to the Creators of Create.**

Code inspiration from the [Create](https://www.curseforge.com/minecraft/mc-mods/create "Create") mod itself & [Mrbysco](https://www.curseforge.com/members/mrbysco) 

