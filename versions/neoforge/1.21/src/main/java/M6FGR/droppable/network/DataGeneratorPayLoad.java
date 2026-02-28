package M6FGR.droppable.network;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

public record DataGeneratorPayLoad(String entityId, Map<String, Float> skills, float chance) implements CustomPacketPayload {
    public static final Type<DataGeneratorPayLoad> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("droppable", "loot_modifier"));

    public static final StreamCodec<ByteBuf, DataGeneratorPayLoad> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, DataGeneratorPayLoad::entityId,
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.FLOAT), DataGeneratorPayLoad::skills,
            ByteBufCodecs.FLOAT, DataGeneratorPayLoad::chance,
            DataGeneratorPayLoad::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}