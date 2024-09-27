package dev.compasses.quinnsperms;

import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftbteams.api.event.TeamEvent;
import dev.ftb.mods.ftbteams.api.event.TeamManagerEvent;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import sereneseasons.init.ModConfig;
import sereneseasons.init.ModTags;

import java.util.HashMap;

public class CommonMain {
    private static HashMap<ResourceKey<Level>, Long2ObjectMap<ClaimedChunk>> CLAIMED_CHUNKS = null;
    private static HashMap<ResourceKey<Level>, LongSet> UNCLAIMED_CHUNKS = null;
    private static final Object2BooleanOpenHashMap<ResourceKey<Level>> WHITELISTED_DIMENSIONS = new Object2BooleanOpenHashMap<>();

    public static void init() {
        TeamEvent.COLLECT_PROPERTIES.register(event -> {
            event.add(Constants.ALLOW_SEASONS_SNOW_ICE);
        });

        TeamManagerEvent.CREATED.register(event -> {
            CLAIMED_CHUNKS = new HashMap<>();
            UNCLAIMED_CHUNKS = new HashMap<>();
        });

        TeamManagerEvent.DESTROYED.register(event -> {
            CLAIMED_CHUNKS = null;
            UNCLAIMED_CHUNKS = null;
        });
    }

    public static Boolean checkIfSeasonsSnowDisabled(boolean result, LevelReader reader, BlockPos pos) {
        if (result || !(reader instanceof Level level)) {
            return null;
        }

        if (!WHITELISTED_DIMENSIONS.containsKey(level.dimension())) {
            WHITELISTED_DIMENSIONS.put(level.dimension(), ModConfig.seasons.isDimensionWhitelisted(level.dimension()));
        }

        if (!WHITELISTED_DIMENSIONS.getBoolean(level.dimension())) {
            return null;
        }

        if (reader.getBiome(pos).is(ModTags.Biomes.BLACKLISTED_BIOMES)) {
            return null;
        }

        long chunkPos = ChunkPos.asLong(pos);

        if (UNCLAIMED_CHUNKS.computeIfAbsent(level.dimension(), __ -> new LongOpenHashSet()).contains(chunkPos)) {
            return null;
        }

        Long2ObjectMap<ClaimedChunk> claimedChunks = CLAIMED_CHUNKS.computeIfAbsent(level.dimension(), __ -> new Long2ObjectOpenHashMap<>());

        ClaimedChunk chunk;

        if (claimedChunks.containsKey(chunkPos)) {
            chunk = claimedChunks.get(chunkPos);
        } else {
            chunk = FTBChunksAPI.api().getManager().getChunk(new ChunkDimPos(level.dimension(), new ChunkPos(chunkPos)));

            if (chunk == null) {
                UNCLAIMED_CHUNKS.get(level.dimension()).add(chunkPos);

                return null;
            } else {
                claimedChunks.put(chunkPos, chunk);
            }
        }

        return !chunk.getTeamData().getTeam().getProperty(Constants.ALLOW_SEASONS_SNOW_ICE);
    }

    public static void removeChunk(ResourceKey<Level> dimension, long pos) {
        LongSet unclaimedMap = UNCLAIMED_CHUNKS.get(dimension);

        if (unclaimedMap != null) {
            unclaimedMap.remove(pos);
        }

        Long2ObjectMap<ClaimedChunk> claimedMap = CLAIMED_CHUNKS.get(dimension);

        if (claimedMap != null) {
            claimedMap.remove(pos);
        }
    }
}