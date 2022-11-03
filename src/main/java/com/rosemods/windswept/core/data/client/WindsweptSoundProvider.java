package com.rosemods.windswept.core.data.client;

import java.util.function.Consumer;

import com.rosemods.windswept.core.Windswept;
import com.rosemods.windswept.core.registry.WindsweptSounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.common.data.SoundDefinition.Sound;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WindsweptSoundProvider extends SoundDefinitionsProvider {

	public WindsweptSoundProvider(GatherDataEvent event) {
		super(event.getGenerator(), Windswept.MODID, event.getExistingFileHelper());
	}

	@Override
	public void registerSounds() {
		this.register(WindsweptSounds.MUSIC_DISC_RAIN, "records/rain", s -> s.stream());
		this.register(WindsweptSounds.MUSIC_DISC_SNOW, "records/snow", s -> s.stream());
	}
	
	private void register(RegistryObject<SoundEvent> soundEvent, String location, Consumer<Sound> consumer) {
		Sound sound = sound(Windswept.REGISTRY_HELPER.prefix(location));
		if (consumer != null)
			consumer.accept(sound);
		
		this.add(soundEvent.get(), definition().with(sound));
	}
	
}

