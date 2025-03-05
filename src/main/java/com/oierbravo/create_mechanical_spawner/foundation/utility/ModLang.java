package com.oierbravo.create_mechanical_spawner.foundation.utility;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.lang.LangBuilder;

public class ModLang extends Lang {
    public ModLang() {
        super();
    }
    public static LangBuilder builder() {
        return new LangBuilder(CreateMechanicalSpawner.MODID);
    }
    public static LangBuilder translate(String langKey, Object... args) {
        return builder().translate(langKey, args);
    }

}
