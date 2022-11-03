package com.rosemods.windswept.core.data.server.modifiers;

import java.util.Collections;

import com.rosemods.windswept.core.Windswept;
import com.rosemods.windswept.core.registry.WindsweptItems;
import com.teamabnormals.blueprint.common.loot.modification.LootModifierProvider;
import com.teamabnormals.blueprint.common.loot.modification.modifiers.LootPoolsModifier;

import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

public class WindsweptLootModifierProvider extends LootModifierProvider {

	public WindsweptLootModifierProvider(GatherDataEvent event) {
		super(event.getGenerator(), Windswept.MODID);
	}

	@Override
	protected void registerEntries() {
		// goat meat
		this.entry("goat_meat").selects("entities/goat").addModifier(new LootPoolsModifier(Collections.singletonList(LootPool.lootPool().name("windswept:goat").setRolls(ConstantValue.exactly(1f))
				.add(LootItem.lootTableItem(WindsweptItems.GOAT.get())
					.apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
					.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
					.apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, 
						EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true).build()))))
				).build()), false));
		
		// drowned rain disc
		this.entry("drowned_disc").selects("entities/drowned")
				.addModifier(new LootPoolsModifier(
						Collections.singletonList(LootPool.lootPool().name("windswept:rain_disc")
								.add(LootItem.lootTableItem(WindsweptItems.MUSIC_DISC_RAIN::get))
								.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER,
										EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS)))
								.build()),
						false));
		
		// chests
		this.chestEntry("village_taiga_house", "chests/village/village_taiga_house", WindsweptItems.MUTTON_PIE, UniformGenerator.between(-1, 2));
		this.chestEntry("village_snowy_house", "chests/village/village_snowy_house", WindsweptItems.MUTTON_PIE, UniformGenerator.between(-1, 2));
		this.chestEntry("shipwreck_treasure", "chests/shipwreck_treasure", WindsweptItems.WOODEN_BUCKET, UniformGenerator.between(-1, 1));
		this.chestEntry("underwater_ruin_small", "chests/underwater_ruin_small", WindsweptItems.WOODEN_BUCKET, UniformGenerator.between(-1, 1));
		this.chestEntry("village_fisher", "chests/village/village_fisher", WindsweptItems.WOODEN_BUCKET, UniformGenerator.between(-1, 1));

	}
	
	private void chestEntry(String name, String target, RegistryObject<Item> item, NumberProvider count) {
		this.entry(name).selects(target).addModifier(new LootPoolsModifier(Collections.singletonList(LootPool.lootPool().name("windswept:" + name).setRolls(ConstantValue.exactly(1f))
				.add(LootItem.lootTableItem(item.get())
						.apply(SetItemCountFunction.setCount(count))).apply(SetItemDamageFunction.setDamage(count)).build()), false));
	}

}
