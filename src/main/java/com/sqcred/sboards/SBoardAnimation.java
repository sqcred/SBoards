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
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;

import java.util.List;
import java.util.function.Supplier;

public class SBoardAnimation {

    private List<Component> frames;
    private int current = 0;

    private Task task;

    public SBoardAnimation(Supplier<List<Component>> title, int intervalInMillis){
        this.frames = title.get();
        task = MinecraftServer.getSchedulerManager().submitTask(() -> {
            current++;
            if(current == frames.size()) current = 0;
            return TaskSchedule.millis(intervalInMillis);
        });
    }

    public Component getComponent(){
        return this.frames.get(this.current);
    }

    public void destroy(){
        task.cancel();
        frames = null;
    }

}
