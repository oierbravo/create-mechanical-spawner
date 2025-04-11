package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.ModConstants;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;

public class ModSpriteShifts {
    public static final CTSpriteShiftEntry FRAMED_DARK_GLASS =
            getCT(AllCTTypes.OMNIDIRECTIONAL, "framed_dark_glass", "framed_dark_glass"),
            HORIZONTAL_FRAMED_DARK_GLASS =
                    getCT(AllCTTypes.HORIZONTAL_KRYPPERS, "framed_dark_glass", "horizontal_framed_dark_glass"),
            VERTICAL_FRAMED_DARK_GLASS = getCT(AllCTTypes.VERTICAL, "framed_dark_glass", "vertical_framed_dark_glass");

    public static final CTSpriteShiftEntry REINFORCED_BRASS_CASING = omni("reinforced_brass_casing");


    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(type, ModConstants.asResource("block/" + blockTextureName),
                ModConstants.asResource("block/" + connectedTextureName + "_connected"));
    }
    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }
    private static CTSpriteShiftEntry vertical(String name) {
        return getCT(AllCTTypes.VERTICAL, name);
    }
    private static CTSpriteShiftEntry omni(String name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, name);
    }
}
