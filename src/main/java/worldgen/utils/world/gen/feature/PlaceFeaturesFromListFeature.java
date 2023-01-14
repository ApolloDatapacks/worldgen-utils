package worldgen.utils.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.SimpleRandomFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class PlaceFeaturesFromListFeature extends Feature<SimpleRandomFeatureConfig> {
    public PlaceFeaturesFromListFeature(Codec<SimpleRandomFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleRandomFeatureConfig> context) {
        int i = 0;
        Random random = context.getRandom();
        SimpleRandomFeatureConfig simpleRandomFeatureConfig = (SimpleRandomFeatureConfig)context.getConfig();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        ChunkGenerator chunkGenerator = context.getGenerator();
        for(int j = 0; j < simpleRandomFeatureConfig.features.size(); ++j) {
            //simpleRandomFeatureConfig.features.get(j).value().generateUnregistered(structureWorldAccess, chunkGenerator, random, blockPos);
            if (((PlacedFeature)simpleRandomFeatureConfig.features.get(j).value()).generateUnregistered(structureWorldAccess, chunkGenerator, random, blockPos)) {
                ++i;
            }
        }

        return i > 0;
    }
}
