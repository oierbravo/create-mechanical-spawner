package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collections;

import static com.oierbravo.create_mechanical_spawner.registrate.ModTags.NameSpace.MOD;

public class ModTags {
    public static <T> TagKey<T> optionalTag(IForgeRegistry<T> registry,
                                            ResourceLocation id) {
        return registry.tags()
                .createOptionalTagKey(id, Collections.emptySet());
    }

    public static <T> TagKey<T> forgeTag(IForgeRegistry<T> registry, String path) {
        return optionalTag(registry, new ResourceLocation("forge", path));
    }

    public static TagKey<Fluid> forgeFluidTag(String path) {
        return forgeTag(ForgeRegistries.FLUIDS, path);
    }
    public enum NameSpace {

        MOD(CreateMechanicalSpawner.MODID, false, true),
        CREATE(Create.ID, true, false),
        FORGE("forge");

        public final String id;
        public final boolean optionalDefault;
        public final boolean alwaysDatagenDefault;


        NameSpace(String id) {
            this(id, true, false);
        }

        NameSpace(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
            this.id = id;
            this.optionalDefault = optionalDefault;
            this.alwaysDatagenDefault = alwaysDatagenDefault;
        }
    }

    public enum ModFluidTags {


            SPANW_LIQUID

            ;

            public final TagKey<Fluid> tag;
            public final boolean alwaysDatagen;

            ModFluidTags() {
                this(MOD);
            }

            ModFluidTags(NameSpace namespace) {
                this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
            }

            ModFluidTags(NameSpace namespace, String path) {
                this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
            }

            ModFluidTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
                this(namespace, null, optional, alwaysDatagen);
            }

            ModFluidTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
                ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
                if (optional) {
                    tag = optionalTag(ForgeRegistries.FLUIDS, id);
                } else {
                    tag = FluidTags.create(id);
                }
                this.alwaysDatagen = alwaysDatagen;
            }

            @SuppressWarnings("deprecation")
            public boolean matches(Fluid fluid) {
                return fluid.is(tag);
            }

            public boolean matches(FluidState state) {
                return state.is(tag);
            }

            private static void init() {}

        }
    public static void init() {
        ModTags.ModFluidTags.init();
    }

}
