package me.yourname.farlands;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class FarLandsChunkGenerator extends ChunkGenerator {

    private static final int FARLANDS_START = 30_000;

    @Override
    public void generateNoise(
            WorldInfo worldInfo,
            Random random,
            int chunkX,
            int chunkZ,
            ChunkData chunkData) {

        int worldX = chunkX << 4;
        int worldZ = chunkZ << 4;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {

                int absoluteX = worldX + x;
                int absoluteZ = worldZ + z;

                double height;

                if (Math.abs(absoluteX) > FARLANDS_START ||
                    Math.abs(absoluteZ) > FARLANDS_START) {

                    double noise = Math.sin(absoluteX * 0.00001)
                                 * Math.cos(absoluteZ * 0.00001);

                    height = Math.abs(noise * noise * noise) * 255;

                } else {
                    height = 64 + random.nextDouble() * 20;
                }

                int maxY = Math.min((int) height, worldInfo.getMaxHeight());

                for (int y = worldInfo.getMinHeight(); y < maxY; y++) {
                    chunkData.setBlock(x, y, z, Material.STONE);
                }

                chunkData.setBlock(x, maxY, z, Material.GRASS_BLOCK);
            }
        }
    }
}

