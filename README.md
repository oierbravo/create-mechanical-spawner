# Create Mechanical Spawner

A mechanical spawner block. Generates Mobs with rotation power.

## [Dedicated wiki](https://wiki.mechanicalmods.net/mods/mechanical-spawner/)

## 1.21.1-2.x Version Requires Mechanicals Lib
- [Curseforge](https://www.curseforge.com/minecraft/mc-mods/mechanicals-lib "Curseforge")
- [Modrinth](https://modrinth.com/mod/mechanicals-lib "Modrinth")

## Version support & documentation
- 1.21.1: Supported. Documentation refers to this version.
- 1.20.1: Only critical issues: [Documentation](https://github.com/oierbravo/create-mechanical-spawner/tree/mc1.20.1/dev "Documentation")

## Features
- Random Spawn fluid
  - Biome dependant random generation
- Specific liquid for Mobs
- Configurable:
  - Stress
  - Minimum speed
  - Fluid capacity
  - Spawn point max range
  - Processing time multiplier (or dividier). Allows to modify the time for all recipes without editing them.
- Spawn fluid mixer recipes.
- JEI & Jade integration
- KubeJS integration
- Ponder scene
- Loot collector block
  - Configurable to allow any container or Create Item Vault.
  - Can be disabled.
  - The spawner can be configured to work only with loot collectors.
  - Enchantable
- Wither & explosion immune glass & casing.
- Wither recipe.


## Spawn recipes
- `input` required.
- `output` optional. Any entity like `"minecraft:skeleton"`. Default: random mob from biome.
- `processingTime` optional. Defaults: 200
- `customLoot` optional. Allows to generate custom loot when used with loot collector blocks.

### Random generation (already in the mod)
```
{
  "type": "create_mechanical_spawner:spawner",
  "input": {
    "type": "fluid_stack",
    "amount": 100,
    "fluid": "create_mechanical_spawner:spawn_fluid_random"
  },
  "processingTime": 1500
}
```
Specific Mob example
```
{
  "type": "create_mechanical_spawner:spawner",
  "input": {
    "type": "fluid_stack",
    "amount": 100,
    "fluid": "create_mechanical_spawner:spawn_fluid_pigling"
  },
  "output": "minecraft:pig",
  "processingTime": 2500
}
```
Custom loot example
```
{
  "type": "create_mechanical_spawner:spawner",
  "customLoot": [
    {
      "id": "minecraft:nether_star"
    },
    {
      "count": 16,
      "id": "create:experience_nugget"
    },
    {
      "chance": 0.5,
      "count": 8,
      "id": "create:experience_nugget"
    }
  ],
  "input": {
    "type": "fluid_stack",
    "amount": 300,
    "fluid": "create_mechanical_spawner:spawn_fluid_wither"
  },
  "output": "minecraft:wither",
  "processingTime": 5000
}
```


## KubeJS support (Server script)


### Remove all default recipes:
```js
event.remove({ type: 'create_mechanical_spawner:spawner' })
```
### Remove specific mob recipes:
```js
event.remove({id:"create_mechanical_spawner:spawner/skeleton"})
```

### Bindings
- Chanced output
```js
Output.of('minecraft:clay', 0.5)
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
event.remove({ output: 'minecraft:wither_skeleton'}); //recommended
event.recipes.create_mechanical_spawner.spawner("minecraft:wither_skeleton", Fluid.of('minecraft:lava', 1000))
    .customLoot(
        [
            Output.of("minecraft:redstone",0.5),Output.of("minecraft:iron_ingot",0.1),
            Output.of("minecraft:redstone",0.5),Output.of("minecraft:iron_ingot",0.1),
        ]
    )
	.processingTime(2000);
```
- It's recommended to disable the default recipe before adding the recipe with custom loot.

**Thanks to the Creators of Create.**

Code inspiration from the [Create](https://www.curseforge.com/minecraft/mc-mods/create "Create") mod itself & [Mrbysco](https://www.curseforge.com/members/mrbysco) 

