/*
    MIT License

    Copyright (c) 2022 sqcred

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
 */

package com.sqcred.sboards;

import net.kyori.adventure.text.Component;

import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.Sidebar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SBoard {

    private static final int MAX_LINES = 15;

    private final Map<Player, Sidebar> boards = new HashMap<>();

    private Function<Player, Component> title;
    private final Function<Player, List<Component>> lines;

    public SBoard(Function<Player, Component> title, Function<Player, List<Component>> lines) {
        this.title = title;
        this.lines = lines;
    }

    public SBoard(SBoardAnimation title, Function<Player, List<Component>> lines) {
        this.title = player -> title.getComponent();
        this.lines = lines;
    }

    public boolean addPlayer(Player player){
        if(boards.containsKey(player)) {
            return false;
        }

        Sidebar sidebar = new Sidebar(title.apply(player));
        List<Component> lineList = lines.apply(player).stream().limit(MAX_LINES).toList();

        for (int index = 0; index < lineList.size(); index++) {
            Component component = lineList.get(index);
            sidebar.createLine(new Sidebar.ScoreboardLine("line" + index, component, lineList.size() - index));
        }

        sidebar.addViewer(player);
        boards.put(player, sidebar);
        return true;
    }

    public boolean removePlayer(Player player){
        Sidebar sidebar = boards.get(player);

        if(sidebar == null) {
            return false;
        }

        sidebar.removeViewer(player);
        boards.remove(player);
        return true;
    }

    public void removeAll(){
        for (Player player : boards.keySet()){
            removePlayer(player);
        }
    }

    public boolean update(Player player){
        Sidebar sidebar = boards.get(player);

        if(sidebar == null){
            return false;
        }

        sidebar.setTitle(title.apply(player));
        List<Component> linesList = lines.apply(player);

        for(Sidebar.ScoreboardLine line : sidebar.getLines()){
            int number = Integer.parseInt(line.getId().split("line")[1]);
            sidebar.updateLineContent(line.getId(), linesList.get(number));
        }

        return true;
    }

    public void updateAll(){
        for(Player player : boards.keySet()){
            update(player);
        }
    }
}
