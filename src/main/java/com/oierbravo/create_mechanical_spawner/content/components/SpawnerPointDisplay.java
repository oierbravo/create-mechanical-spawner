package com.oierbravo.create_mechanical_spawner.content.components;

import com.mojang.datafixers.util.Pair;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllKeys;
import com.simibubi.create.AllSpecialTextures;
import com.simibubi.create.CreateClient;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.*;

public class SpawnerPointDisplay {
    private static final int DISPLAY_TIME = 200;
    private static GroupEntry lastHoveredGroup = null;
    private static class Entry {
        SpawnerBlockEntity be;
        int timer;

        public Entry(SpawnerBlockEntity be) {
            this.be = be;
            timer = DISPLAY_TIME;
            CreateClient.OUTLINER.showCluster(getOutlineKey(), createSelection(be))
                    .colored(0xFFFFFF)
                    //.disableNormals()
                    .lineWidth(1 / 16f)
                    .withFaceTexture(AllSpecialTextures.HIGHLIGHT_CHECKERED);
        }

        protected Object getOutlineKey() {
            return Pair.of(be.getBlockPos(), 1);
        }

        protected Set<BlockPos> createSelection(SpawnerBlockEntity spawner) {
            Set<BlockPos> positions = new HashSet<>();
            List<BlockPos> includedBlockPositions = spawner.getSpawnBlockPosition(null, true);
            if (includedBlockPositions == null)
                return Collections.emptySet();
            positions.addAll(includedBlockPositions);
            return positions;
        }

    }
    private static class GroupEntry extends Entry {

        List<SpawnerBlockEntity> includedBEs;

        public GroupEntry(SpawnerBlockEntity be) {
            super(be);
        }

        @Override
        protected Object getOutlineKey() {
            return this;
        }

        @Override
        protected Set<BlockPos> createSelection(SpawnerBlockEntity spawner) {
            Set<BlockPos> list = new HashSet<>();
            includedBEs = be.collectSpawnGroup();
            if (includedBEs == null)
                return list;
            for (SpawnerBlockEntity SpawnerBlockEntity : includedBEs)
                list.addAll(super.createSelection(SpawnerBlockEntity));
            return list;
        }

    }
    static Map<BlockPos, SpawnerPointDisplay.Entry> entries = new HashMap<>();
    static List<SpawnerPointDisplay.GroupEntry> groupEntries = new ArrayList<>();
    public static void tick() {
        Player player = Minecraft.getInstance().player;
        Level world = Minecraft.getInstance().level;
        boolean hasWrench = AllItems.WRENCH.isIn(player.getMainHandItem());

        for (Iterator<BlockPos> iterator = entries.keySet()
                .iterator(); iterator.hasNext();) {
            BlockPos pos = iterator.next();
            SpawnerPointDisplay.Entry entry = entries.get(pos);
            if (tickEntry(entry, hasWrench))
                iterator.remove();
            CreateClient.OUTLINER.keep(entry.getOutlineKey());
        }

        for (Iterator<SpawnerPointDisplay.GroupEntry> iterator = groupEntries.iterator(); iterator.hasNext();) {
            SpawnerPointDisplay.GroupEntry group = iterator.next();
            if (tickEntry(group, hasWrench)) {
                iterator.remove();
                if (group == lastHoveredGroup)
                    lastHoveredGroup = null;
            }
            CreateClient.OUTLINER.keep(group.getOutlineKey());
        }

        if (!hasWrench)
            return;

        HitResult over = Minecraft.getInstance().hitResult;
        if (!(over instanceof BlockHitResult))
            return;
        BlockHitResult ray = (BlockHitResult) over;
        BlockPos pos = ray.getBlockPos();
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity == null || tileEntity.isRemoved())
            return;
        if (!(tileEntity instanceof SpawnerBlockEntity))
            return;

        boolean ctrl = AllKeys.ctrlDown();
        SpawnerBlockEntity SpawnerBlockEntity = (SpawnerBlockEntity) tileEntity;

        if (ctrl) {
            SpawnerPointDisplay.GroupEntry existingGroupForPos = getExistingGroupForPos(pos);
            if (existingGroupForPos != null) {
                for (SpawnerBlockEntity included : existingGroupForPos.includedBEs)
                    entries.remove(included.getBlockPos());
                existingGroupForPos.timer = DISPLAY_TIME;
                return;
            }
        }

        if (!entries.containsKey(pos) || ctrl)
            display(SpawnerBlockEntity);
        else {
            if (!ctrl)
                entries.get(pos).timer = DISPLAY_TIME;
        }
    }

    private static boolean tickEntry(Entry entry, boolean hasWrench) {
        SpawnerBlockEntity SpawnerBlockEntity = entry.be;
        Level teWorld = SpawnerBlockEntity.getLevel();
        Level world = Minecraft.getInstance().level;

        if (SpawnerBlockEntity.isRemoved() || teWorld == null || teWorld != world
                || !world.isLoaded(SpawnerBlockEntity.getBlockPos())) {
            return true;
        }

        if (!hasWrench && entry.timer > 20) {
            entry.timer = 20;
            return false;
        }

        entry.timer--;
        if (entry.timer == 0)
            return true;
        return false;
    }

    public static void display(SpawnerBlockEntity spawner) {

        if (AllKeys.ctrlDown()) {
            GroupEntry hoveredGroup = new GroupEntry(spawner);

            for (SpawnerBlockEntity included : hoveredGroup.includedBEs)
                CreateClient.OUTLINER.remove(included.getBlockPos());

            groupEntries.forEach(entry -> CreateClient.OUTLINER.remove(entry.getOutlineKey()));
            groupEntries.clear();
            entries.clear();
            groupEntries.add(hoveredGroup);
            return;
        }

        BlockPos pos = spawner.getBlockPos();
        GroupEntry entry = getExistingGroupForPos(pos);
        if (entry != null)
            CreateClient.OUTLINER.remove(entry.getOutlineKey());

        groupEntries.clear();
        entries.clear();
        entries.put(pos, new Entry(spawner));

    }
    private static GroupEntry getExistingGroupForPos(BlockPos pos) {
        for (GroupEntry groupEntry : groupEntries)
            for (SpawnerBlockEntity spawner : groupEntry.includedBEs)
                if (pos.equals(spawner.getBlockPos()))
                    return groupEntry;
        return null;
    }

}
