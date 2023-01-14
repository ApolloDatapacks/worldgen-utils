package worldgen.utils;

import worldgen.utils.world.gen.feature.WorldgenUtilsFeatures;
import worldgen.utils.world.gen.placementmodifier.SeededNoiseIntervalCountPlacementModifier;
import worldgen.utils.world.gen.placementmodifier.SeededNoiseThresholdCountPlacementModifier;
import worldgen.utils.world.gen.placementmodifier.OffsetPlacementModifier;
import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import worldgen.utils.world.gen.placementmodifier.WorldgenUtilsPlacementModifiers;

public class WorldgenUtilsMod implements ModInitializer {
	public static final String MOD_ID = "worldgen-utils";
	public static final Logger LOGGER = LoggerFactory.getLogger("worldgen-utils");

	public static PlacementModifierType<OffsetPlacementModifier> OFFSET =
		Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, new Identifier(WorldgenUtilsMod.MOD_ID, "offset"), () -> {
		return OffsetPlacementModifier.MODIFIER_CODEC;
	});

	public static PlacementModifierType<SeededNoiseThresholdCountPlacementModifier> SEEDED_NOISE_THRESHOLD_COUNT =
		Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, new Identifier(WorldgenUtilsMod.MOD_ID, "seeded_noise_threshold_count"), () -> {
			return SeededNoiseThresholdCountPlacementModifier.MODIFIER_CODEC;
	});

	public static PlacementModifierType<SeededNoiseIntervalCountPlacementModifier> SEEDED_NOISE_INTERVAL_COUNT =
			Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, new Identifier(WorldgenUtilsMod.MOD_ID, "seeded_noise_interval_count"), () -> {
				return SeededNoiseIntervalCountPlacementModifier.MODIFIER_CODEC;
			});

	@Override
	public void onInitialize() {
		WorldgenUtilsFeatures.register();
		WorldgenUtilsPlacementModifiers.register();
	}
}
