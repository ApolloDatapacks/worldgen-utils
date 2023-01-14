package worldgen.utils.world.gen.placementmodifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;

public class SeededNoiseProvider {
    public static final Codec<SeededNoiseProvider> MODIFIER_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                Codec.LONG.fieldOf("seed").forGetter((seededNoiseProvider) -> {
                    return seededNoiseProvider.seed;
                }), DoublePerlinNoiseSampler.NoiseParameters.CODEC.fieldOf("noise").forGetter((seededNoiseProvider) -> {
                    return seededNoiseProvider.noiseParameters;
                }), Codecs.POSITIVE_FLOAT.fieldOf("scale").forGetter((seededNoiseProvider) -> {
                    return seededNoiseProvider.scale;
                })).apply(instance, SeededNoiseProvider::new);
    });
    public final long seed;
    public final DoublePerlinNoiseSampler.NoiseParameters noiseParameters;
    public final float scale;

    private SeededNoiseProvider(long seed, DoublePerlinNoiseSampler.NoiseParameters noiseParameters, float scale) {
        this.seed = seed;
        this.noiseParameters = noiseParameters;
        this.scale = scale;
    }
}
