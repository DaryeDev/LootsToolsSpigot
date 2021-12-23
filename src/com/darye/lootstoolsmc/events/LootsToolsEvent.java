package com.darye.lootstoolsmc.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.*;
import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import com.darye.lootstoolsmc.utils.commandInterpreter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LootsToolsEvent {
    static Thread sent;
    static Thread receive;
    static Socket socket;
    private static WebSocketClient cc;

    public static void main(String[] args) throws URISyntaxException {
        Plugin plugin = (Bukkit.getPluginManager().getPlugin("LootsToolsSpigot"));
        cc = new WebSocketClient(new URI("ws://localhost:4848")) {

            @Override
            public void onMessage(String message) {
                JSONParser parser = new JSONParser();
                JSONObject json = new JSONObject();
                try {
                    json = (JSONObject) parser.parse(message);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                for(Iterator iterator = json.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
//                    System.out.println(key + ": " + json.get(key));
                    if (key.startsWith("MINECRAFT")) {
                        JSONObject finalJson = json;
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            commandInterpreter.interpretameEstaXD(finalJson);
                        });
                    }
                    if (key.equals("newEvent")) {
                        JSONObject cardData = new JSONObject();
                        try {
                            cardData = (JSONObject) parser.parse(json.get(key).toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        JSONObject cardDataData = new JSONObject();
                        try {
                            cardDataData = (JSONObject) parser.parse(cardData.get("data").toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        message = MessageFormat.format("{0} ha canjeado {1}!", cardData.get("user"), cardDataData.get("cardName"));
                        Bukkit.broadcastMessage(message);
                    }
                }
            }

            @Override
            public void onOpen(ServerHandshake handshake) {
                System.out.println("You are connected");
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println(
                        "You have been disconnected from: " + getURI() + "; Code: " + code + " " + reason);
            }

            @Override
            public void onError(Exception ex) {
                System.out.println("Exception occurred ...\n" + ex);
                ex.printStackTrace();
            }
        };
        cc.connect();
    }

}