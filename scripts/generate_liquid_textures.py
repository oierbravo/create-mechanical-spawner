from PIL import Image, ImageColor
import os, shutil

paths = {
    "item": "output/item/",
    "fluid": "output/fluid/"
}

for path in paths:
    shutil.rmtree(paths[path])
    #if not os.path.exists(paths[path]):
    os.makedirs(paths[path])

entities = {
    "random": "#b400ff",
    "blaze": "#ff6c00",
    "creeper": "#11c900",
    "drowned": "#00ffd7",
    "enderman": "#006d50",
    "evoker": "#868686",
    "ghast": "#dadada",
    "magma_cube": "#7d0000",
    "pigling": "#ffa8e3",
    "skeleton": "#555555",
    "slime": "#16ff00",
    "spider": "#220000",
    "witch": "#095000",
    "wither_skeleton": "#393939",
    "zombie": "#0a7300",
    "bat": "#ff9acd",
    "bee": "#ffe600",
    "cow": "#382417",
    "chicken": "#f7f7f7",
    "fox": "#ff9700",
    "horse": "#804c00",
    "panda": "#e5e5e5",
    "pig": "#ff9acd",
    "rabbit": "#ff9acd",
    "villager": "#503600",
    "wolf": "#ff9acd"
}

os

for entity in entities:
    color = entities[entity]

    flow = Image.open('template/base_spawn_fluid_flow.png')
    still = Image.open('template/base_spawn_fluid_still.png')
    bucket_base = Image.open('template/base_bucket.png').convert('RGBA')
    bucket_tint = Image.open('template/base_bucket_tint.png').convert('RGBA')



    #create the coloured overlays
    flowColorOverlay = Image.new('RGB',flow.size,ImageColor.getrgb(color))
    stillColorOverlay = Image.new('RGB',still.size,ImageColor.getrgb(color))
    bucketColorOverlay = Image.new('RGB',bucket_base.size,ImageColor.getrgb(color))

    #create a mask using RGBA to define an alpha channel to make the overlay transparent
    flowMask = Image.new('RGBA',flow.size,(0,0,0,123))
    stillMask = Image.new('RGBA',still.size,(0,0,0,123))
    bucketTintMaskAll = Image.new('RGBA',bucket_tint.size,(0,0,0,123))
    bucketMaskAll = Image.new('RGBA',bucket_tint.size,(123,123,123,0))
    bucketTintMask = Image.open('template/base_bucket_tint_mask.png').convert('L')
    bucketMask = Image.open('template/base_bucket_mask.png').convert('RGBA')


    Image.composite(flow,flowColorOverlay,flowMask).convert('RGB').save(paths['fluid'] + 'spawn_fluid_%s_flow.png' % (entity,), optimize=True)
    Image.composite(still,stillColorOverlay,stillMask).convert('RGB').save(paths['fluid'] + 'spawn_fluid_%s_still.png' % (entity,), optimize=True)

    bucketTintImageColored = Image.composite(bucket_tint,bucketColorOverlay,bucketTintMaskAll).convert('RGBA')
    bucketTintImage = bucket_tint.copy()
    bucketTintImage.paste(bucketTintImageColored, None, bucketTintMask)
    #bucketTintImage.save('output/tint_%s_bucket.png' % (entity,))

    bucketImage = bucket_base.copy()
    bucketImage.paste(bucketTintImage, (0,0),bucketTintMask)
    bucketImage.save(paths['item'] + 'spawn_fluid_%s_bucket.png' % (entity,), optimize=True)

    shutil.copy("template/base_spawn_fluid_flow.png.mcmeta", "output/fluid/spawn_fluid_%s_flow.png.mcmeta" % (entity))
    shutil.copy("template/base_spawn_fluid_still.png.mcmeta", "output/fluid/spawn_fluid_%s_still.png.mcmeta" % (entity))