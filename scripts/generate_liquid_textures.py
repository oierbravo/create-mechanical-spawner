from PIL import Image, ImageColor
import os, shutil

entities = {
    "random": "#b400ff",
    "blaze": "#ff6c00",
    "creeper": "#11c900",
    "enderman": "#00ba88",
    "magma_cube": "#7d0000",
    "skeleton": "#555555",
    "slime": "#16ff00",
    "spider": "#220000",
    "zombie": "#0a7300"
}

for entity in entities:
    color = entities[entity]

    flow = Image.open('template/base_spawn_fluid_flow.png')
    still = Image.open('template/base_spawn_fluid_still.png')


    #create the coloured overlays
    flowColorOverlay = Image.new('RGB',flow.size,ImageColor.getrgb(color))
    stillColorOverlay = Image.new('RGB',still.size,ImageColor.getrgb(color))

    #create a mask using RGBA to define an alpha channel to make the overlay transparent
    flowMask = Image.new('RGBA',flow.size,(0,0,0,123))
    stillMask = Image.new('RGBA',still.size,(0,0,0,123))

    Image.composite(flow,flowColorOverlay,flowMask).convert('RGB').save('output/spawn_fluid_%s_flow.png' % (entity,))
    Image.composite(still,stillColorOverlay,stillMask).convert('RGB').save('output/spawn_fluid_%s_still.png' % (entity,))

    shutil.copy("template/base_spawn_fluid_flow.png.mcmeta", "output/spawn_fluid_%s_flow.png.mcmeta" % (entity))
    shutil.copy("template/base_spawn_fluid_still.png.mcmeta", "output/spawn_fluid_%s_still.png.mcmeta" % (entity))