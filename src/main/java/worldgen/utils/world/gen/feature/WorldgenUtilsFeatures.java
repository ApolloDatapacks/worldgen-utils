package worldgen.utils.world.gen.feature;

import worldgen.utils.WorldgenUtilsMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.SimpleRandomFeatureConfig;

public class WorldgenUtilsFeatures {
    public static final PlaceFeaturesFromListFeature PLACE_FEATURES_FROM_LIST = new PlaceFeaturesFromListFeature(SimpleRandomFeatureConfig.CODEC);

    public static void register() {
        Registry.register(Registries.FEATURE, new Identifier(WorldgenUtilsMod.MOD_ID, "place_features_from_list"), PLACE_FEATURES_FROM_LIST);
    }
}
