package worldgen.utils.world.gen.placementmodifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.placementmodifier.AbstractCountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;




public class SeededNoiseThresholdCountPlacementModifier extends AbstractCountPlacementModifier {
    public static final Codec<SeededNoiseThresholdCountPlacementModifier> MODIFIER_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                SeededNoiseProvider.MODIFIER_CODEC.fieldOf("noise_provider").forGetter((seededNoiseThresholdCountPlacementModifier) -> {
                    return seededNoiseThresholdCountPlacementModifier.noiseProvider;
                }), Codec.DOUBLE.fieldOf("noise_threshold").forGetter((seededNoiseThresholdCountPlacementModifier) -> {
                    return seededNoiseThresholdCountPlacementModifier.noiseThreshold;
                }), Codec.INT.fieldOf("below_noise").forGetter((seededNoiseThresholdCountPlacementModifier) -> {
                    return seededNoiseThresholdCountPlacementModifier.belowNoise;
                }), Codec.INT.fieldOf("above_noise").forGetter((seededNoiseThresholdCountPlacementModifier) -> {
                    return seededNoiseThresholdCountPlacementModifier.aboveNoise;
        })).apply(instance, SeededNoiseThresholdCountPlacementModifier::new);
    });
    private final double noiseThreshold;
    private final int belowNoise;
    private final int aboveNoise;
    private final DoublePerlinNoiseSampler noiseSampler;
    private final SeededNoiseProvider noiseProvider;

    private SeededNoiseThresholdCountPlacementModifier(SeededNoiseProvider noiseProvider, double noiseThreshold, int belowNoise, int aboveNoise) {
        this.noiseProvider = noiseProvider;
        this.noiseThreshold = noiseThreshold;
        this.belowNoise = belowNoise;
        this.aboveNoise = aboveNoise;
        this.noiseSampler = DoublePerlinNoiseSampler.create(new ChunkRandom(new CheckedRandom(noiseProvider.seed)), noiseProvider.noiseParameters);
    }

    public static SeededNoiseThresholdCountPlacementModifier of(SeededNoiseProvider noiseProvider, double noiseThreshold, int belowNoise, int aboveNoise) {
        return new SeededNoiseThresholdCountPlacementModifier(noiseProvider, noiseThreshold, belowNoise, aboveNoise);
    }

    protected int getCount(Random random, BlockPos pos) {
        double d = this.noiseSampler.sample((double)pos.getX() * noiseProvider.scale, (double)pos.getY() * noiseProvider.scale, (double)pos.getZ() * noiseProvider.scale);
        return d < this.noiseThreshold ? this.belowNoise : this.aboveNoise;
    }

    public PlacementModifierType<?> getType() {
        return PlacementModifierType.NOISE_THRESHOLD_COUNT;
    }
}
