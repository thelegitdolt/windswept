package com.rosemods.windswept.core.data.server;

import com.rosemods.windswept.core.Windswept;
import com.teamabnormals.blueprint.common.world.modification.structure.SimpleStructureRepaletter;
import com.teamabnormals.blueprint.common.world.modification.structure.StructureRepaletterProvider;
import com.teamabnormals.blueprint.core.util.modification.selection.ConditionedResourceSelector;
import com.teamabnormals.blueprint.core.util.modification.selection.selectors.NamesResourceSelector;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.registries.ForgeRegistries;

import static com.rosemods.windswept.core.registry.WindsweptBlocks.*;
import static net.minecraft.world.level.levelgen.structure.BuiltinStructures.*;

public class WindsweptStructureRepaletterProvider extends StructureRepaletterProvider {

    public WindsweptStructureRepaletterProvider(GatherDataEvent event) {
        super(event.getGenerator(), Windswept.MOD_ID);
    }

    @Override
    protected void registerRepaletters() {
        // Igloo //
        this.register(IGLOO, Blocks.SNOW_BLOCK, SNOW_BRICKS.get());
        this.register(IGLOO, Blocks.OAK_WALL_SIGN, HOLLY_SIGNS.getSecond().get());
        this.register(IGLOO, Blocks.POTTED_CACTUS, POTTED_WHITE_ROSE.get());
        this.register(IGLOO, Blocks.SPRUCE_SLAB, HOLLY_SLAB.get());
        this.register(IGLOO, Blocks.SPRUCE_STAIRS, HOLLY_STAIRS.get());
        this.register(IGLOO, Blocks.MOSSY_STONE_BRICKS, HOLLY_PLANKS.get());
        this.register(IGLOO, Blocks.INFESTED_MOSSY_STONE_BRICKS, HOLLY_PLANKS.get());
        this.register(IGLOO, Blocks.OAK_TRAPDOOR, HOLLY_TRAPDOOR.get());
        this.register(IGLOO, Blocks.POLISHED_ANDESITE, Blocks.GOLD_BLOCK);

        // Villages //
        this.register(VILLAGE_TAIGA, Blocks.POTTED_SPRUCE_SAPLING, POTTED_BLUEBELLS.get());
        this.register(VILLAGE_TAIGA, Blocks.POTTED_POPPY, POTTED_RED_ROSE.get());
        this.register(VILLAGE_TAIGA, Blocks.POPPY, RED_ROSE.get());
        this.register(VILLAGE_SNOWY, Blocks.LIGHT_GRAY_WOOL, SNOW_BRICKS.get());
        this.register(VILLAGE_SNOWY, Blocks.BLUE_ICE, PACKED_ICE_BRICKS.get());

        // Ancient City //
        this.register(ANCIENT_CITY, Blocks.BLUE_ICE, PACKED_ICE_BRICKS.get());

        // Chestnut in Snowy Village //
        this.register(VILLAGE_SNOWY, Blocks.SPRUCE_PLANKS, CHESTNUT_PLANKS.get());
        this.register(VILLAGE_SNOWY, Blocks.SPRUCE_STAIRS, CHESTNUT_STAIRS.get());
        this.register(VILLAGE_SNOWY, Blocks.SPRUCE_SLAB, CHESTNUT_SLAB.get());
        this.register(VILLAGE_SNOWY, Blocks.STRIPPED_SPRUCE_LOG, STRIPPED_CHESTNUT_LOG.get());
        this.register(VILLAGE_SNOWY, Blocks.STRIPPED_SPRUCE_WOOD, STRIPPED_CHESTNUT_WOOD.get());
        this.register(VILLAGE_SNOWY, Blocks.SPRUCE_FENCE, CHESTNUT_FENCE.get());
        this.register(VILLAGE_SNOWY, Blocks.SPRUCE_FENCE_GATE, CHESTNUT_FENCE_GATE.get());
        this.register(VILLAGE_SNOWY, Blocks.SPRUCE_DOOR, CHESTNUT_DOOR.get());

        // Mod Compat //
        ICondition quarkOrWoodworks = new OrCondition(new ModLoadedCondition("quark"), new ModLoadedCondition("woodworks"));

        this.register(getLocalKey("grove_weathered_house"), Blocks.BOOKSHELF, HOLLY_BOOKSHELF.get(), quarkOrWoodworks);
        this.register(getLocalKey("grove_weathered_house"), Blocks.CHEST, HOLLY_CHEST.get(), quarkOrWoodworks);
        this.register(getLocalKey("chestnut_weathered_house"), Blocks.CHEST, CHESTNUT_CHEST.get(), quarkOrWoodworks);
        this.register(IGLOO, Blocks.CHEST, HOLLY_CHEST.get(), quarkOrWoodworks);
        this.register(IGLOO, Blocks.LADDER, HOLLY_LADDER.get(), quarkOrWoodworks);
        this.register(VILLAGE_SNOWY, Blocks.CHEST, CHESTNUT_CHEST.get(), quarkOrWoodworks);
        this.register(VILLAGE_SNOWY, Blocks.BOOKSHELF, CHESTNUT_BOOKSHELF.get(), quarkOrWoodworks);
        this.register(VILLAGE_SNOWY, Blocks.LADDER, CHESTNUT_LADDER.get(), quarkOrWoodworks);
    }

    private void register(ResourceKey<Structure> structure, Block replacesBlock, Block replacesWith, ICondition... conditions) {
        this.registerRepaletter(structure.location().getPath() + "/" + getName(replacesWith) + "_replaces_" + getName(replacesBlock),
                new ConditionedResourceSelector(new NamesResourceSelector(structure.location()), conditions), EventPriority.NORMAL, new SimpleStructureRepaletter(replacesBlock, replacesWith));
    }

    private static ResourceKey<Structure> getLocalKey(String name) {
        return ResourceKey.create(Registry.STRUCTURE_REGISTRY, Windswept.REGISTRY_HELPER.prefix(name));
    }

    private static String getName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

}
