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


public class SeededNoiseIntervalCountPlacementModifier extends AbstractCountPlacementModifier {
    public static final Codec<SeededNoiseIntervalCountPlacementModifier> MODIFIER_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
            SeededNoiseProvider.MODIFIER_CODEC.fieldOf("noise_provider").forGetter((seededNoiseIntervalCountPlacementModifier) -> {
                return seededNoiseIntervalCountPlacementModifier.noiseProvider;
            }), Codec.DOUBLE.fieldOf("lower_bound").forGetter((seededNoiseIntervalCountPlacementModifier) -> {
                return seededNoiseIntervalCountPlacementModifier.lowerBound;
            }), Codec.DOUBLE.fieldOf("upper_bound").forGetter((seededNoiseIntervalCountPlacementModifier) -> {
                return seededNoiseIntervalCountPlacementModifier.upperBound;
            }), Codec.INT.fieldOf("inside_bounds").forGetter((seededNoiseIntervalCountPlacementModifier) -> {
                return seededNoiseIntervalCountPlacementModifier.insideBounds;
            }), Codec.INT.fieldOf("outside_bounds").forGetter((seededNoiseIntervalCountPlacementModifier) -> {
                return seededNoiseIntervalCountPlacementModifier.outsideBounds;
        })).apply(instance, SeededNoiseIntervalCountPlacementModifier::new);
    });

    private final SeededNoiseProvider noiseProvider;
    private final double lowerBound;
    private final double upperBound;
    private final int insideBounds;
    private final int outsideBounds;
    private final DoublePerlinNoiseSampler noiseSampler;

    private SeededNoiseIntervalCountPlacementModifier(SeededNoiseProvider noiseProvider, double lowerBound, double upperBound, int insideBounds, int outsideBounds) {
        this.noiseProvider = noiseProvider;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.insideBounds = insideBounds;
        this.outsideBounds = outsideBounds;
        this.noiseSampler = DoublePerlinNoiseSampler.create(new ChunkRandom(new CheckedRandom(noiseProvider.seed)), noiseProvider.noiseParameters);
    }

    public static SeededNoiseIntervalCountPlacementModifier of(SeededNoiseProvider noiseProvider, double lowerBound, double upperBound, int insideBounds, int outsideBounds) {
        return new SeededNoiseIntervalCountPlacementModifier(noiseProvider, lowerBound, upperBound, insideBounds, outsideBounds);
    }

    protected int getCount(Random random, BlockPos pos) {
        double d = this.noiseSampler.sample((double)pos.getX() * noiseProvider.scale, (double)pos.getY() * noiseProvider.scale, (double)pos.getZ() * noiseProvider.scale);
        return d < this.upperBound && d > this.lowerBound ? this.insideBounds : this.outsideBounds;
    }

    public PlacementModifierType<?> getType() {
        return PlacementModifierType.NOISE_THRESHOLD_COUNT;
    }
}
