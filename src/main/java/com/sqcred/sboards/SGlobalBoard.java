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
import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.Sidebar;

import java.util.List;
import java.util.function.Supplier;

public class SGlobalBoard {

    private static final int MAX_LINES = 15;

    private Supplier<Component> title;
    private Supplier<List<Component>> lines;

    private Sidebar sidebar;

    public SGlobalBoard(Supplier<Component> title, Supplier<List<Component>> lines){
        this.title = title;
        this.lines = lines;

        sidebar = new Sidebar(title.get());
        List<Component> lineList = lines.get().stream().limit(MAX_LINES).toList();

        for (int index = 0; index < lineList.size(); index++) {
            Component component = lineList.get(index);
            sidebar.createLine(new Sidebar.ScoreboardLine("line" + index, component, lineList.size() - index));
        }

    }

    public boolean addPlayer(Player player){
        if(sidebar == null) {
            return false;
        }

        sidebar.addViewer(player);

        return true;
    }

    public boolean removePlayer(Player player){
        if(sidebar == null) {
            return false;
        }

        sidebar.removeViewer(player);

        return true;
    }

    public void removeAll(){
        for(Player player : sidebar.getViewers()){
            removePlayer(player);
        }
    }

    public boolean update(){
        if(title == null || lines == null){
            return false;
        }

        sidebar.setTitle(title.get());
        List<Component> linesList = lines.get().stream().limit(MAX_LINES).toList();

        for(Sidebar.ScoreboardLine line : sidebar.getLines()){
            int number = Integer.parseInt(line.getId().split("line")[1]);
            sidebar.updateLineContent(line.getId(), linesList.get(number));
        }

        return true;
    }

}
