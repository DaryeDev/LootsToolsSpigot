package com.darye.lootstoolsmc;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;


class Scratch {
    public static void main(String[] args) {
        JSONObject playerList = new JSONObject();
        playerList.put("players", "pito");
        try (FileWriter file = new FileWriter("findBlockEvent\\players.json")) {
            file.write(playerList.toJSONString());
    } catch (IOException e) {
            e.printStackTrace();
        }}}