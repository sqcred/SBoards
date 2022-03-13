/*
 *
 *     MIT License
 *
 *     Copyright (c) 2022 sqcred
 *
 *     Permission is hereby granted, free of charge, to any person obtaining a copy
 *     of this software and associated documentation files (the "Software"), to deal
 *     in the Software without restriction, including without limitation the rights
 *     to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *     copies of the Software, and to permit persons to whom the Software is
 *     furnished to do so, subject to the following conditions:
 *
 *     The above copyright notice and this permission notice shall be included in all
 *     copies or substantial portions of the Software.
 *
 *     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *     IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *     FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *     AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *     LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *     OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *     SOFTWARE.
 *
 */

package com.sqcred.sboards;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.*;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.timer.TaskSchedule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TestServer {

    public static void main(String[] args){
        // Initialization
        MinecraftServer minecraftServer = MinecraftServer.init();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        // Create the instance
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
        // Set the ChunkGenerator
        instanceContainer.setChunkGenerator(new GeneratorDemo());

        SGlobalBoard board = null;

        try {
            board = new SGlobalBoard(() -> {
                return Component.text(instanceContainer.getTime());
            }, () -> {
                        return Arrays.asList(
                                Component.text("Pos1: " + instanceContainer.getTime()),
                                Component.text("Pos2: " + instanceContainer.getTime()),
                                Component.text("Pos3: " + instanceContainer.getTime()),
                                Component.text("Pos4: " + instanceContainer.getTime()),
                                Component.text("Pos5: " + instanceContainer.getTime()),
                                Component.text("Pos6: " + instanceContainer.getTime()),
                                Component.text("Pos7: " + instanceContainer.getTime()),
                                Component.text("Pos8: " + instanceContainer.getTime()),
                                Component.text("Pos9: " + instanceContainer.getTime()),
                                Component.text("Pos10: " + instanceContainer.getTime()),
                                Component.text("Pos11: " + instanceContainer.getTime()),
                                Component.text("Pos12: " + instanceContainer.getTime()),
                                Component.text("Pos13: " + instanceContainer.getTime()),
                                Component.text("Pos14: " + instanceContainer.getTime()),
                                Component.text("Pos15: " + instanceContainer.getTime()),
                                Component.text("Pos16: " + instanceContainer.getTime()),
                                Component.text("Pos17: " + instanceContainer.getTime())
                        );
                    });
        } catch (Exception e){

        }

        // Add an event callback to specify the spawning instance (and the spawn position)
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        SGlobalBoard finalBoard = board;
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
            MinecraftServer.getSchedulerManager().buildTask(() -> {
                finalBoard.addPlayer(player);
                MinecraftServer.getSchedulerManager().submitTask(() -> {
                    finalBoard.update();
                    return TaskSchedule.millis(100);
                });
            }).delay(TaskSchedule.millis(200)).schedule();
        });

        MojangAuth.init();

        // Start the server on port 25565
        minecraftServer.start("0.0.0.0", 25565);
    }

    private static class GeneratorDemo implements ChunkGenerator {

        @Override
        public void generateChunkData(@NotNull ChunkBatch batch, int chunkX, int chunkZ) {
            // Set chunk blocks
            for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
                for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                    for (byte y = 0; y < 40; y++) {
                        batch.setBlock(x, y, z, Block.STONE);
                    }
                }
            }
        }

        @Override
        public @Nullable List<ChunkPopulator> getPopulators() {
            return null;
        }
    }

}
