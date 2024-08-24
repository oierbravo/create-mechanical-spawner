import os, shutil

baseOrigin = "output/"
baseDestination = "../src/main/resources/assets/create_mechanical_spawner/textures/"

paths = ["item/", "fluid/"]

for path in paths:
    if not os.path.exists(baseDestination + path):
        os.makedirs(baseDestination + path)
    shutil.copytree(baseOrigin + path, baseDestination + path, dirs_exist_ok=True)
