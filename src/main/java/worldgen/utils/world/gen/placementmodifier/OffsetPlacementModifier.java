package worldgen.utils.world.gen.placementmodifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public class OffsetPlacementModifier extends PlacementModifier {

    public static final Codec<OffsetPlacementModifier> MODIFIER_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(IntProvider.createValidatingCodec(-16, 16).fieldOf("x_offset").forGetter((offsetPlacementModifier) -> {
            return offsetPlacementModifier.spreadX;
        }), IntProvider.createValidatingCodec(-16, 16).fieldOf("y_offset").forGetter((offsetPlacementModifier) -> {
            return offsetPlacementModifier.spreadY;
        }), IntProvider.createValidatingCodec(-16, 16).fieldOf("z_offset").forGetter((offsetPlacementModifier) -> {
            return offsetPlacementModifier.spreadZ;
        })).apply(instance, OffsetPlacementModifier::new);
    });

    private final IntProvider spreadX;
    private final IntProvider spreadY;
    private final IntProvider spreadZ;

    private OffsetPlacementModifier(IntProvider xSpread, IntProvider ySpread, IntProvider zSpread) {
        this.spreadX = xSpread;
        this.spreadY = ySpread;
        this.spreadZ = zSpread;
    }

    public static OffsetPlacementModifier of(IntProvider spreadX, IntProvider spreadY, IntProvider spreadZ) {
        return new OffsetPlacementModifier(spreadX, spreadY, spreadZ);
    }

    public static OffsetPlacementModifier vertically(IntProvider spreadY) {
        return new OffsetPlacementModifier(ConstantIntProvider.create(0), spreadY, ConstantIntProvider.create(0));
    }

    public static OffsetPlacementModifier horizontally(IntProvider spreadX, IntProvider spreadZ) {
        return new OffsetPlacementModifier(spreadX, ConstantIntProvider.create(0), spreadZ);
    }



    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
        int i = pos.getX() + this.spreadX.get(random);
        int j = pos.getY() + this.spreadY.get(random);
        int k = pos.getZ() + this.spreadZ.get(random);
        return Stream.of(new BlockPos(i, j, k));
    }

    public PlacementModifierType<?> getType() {
        return PlacementModifierType.RANDOM_OFFSET;
    }
}
