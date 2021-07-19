package com.darye.lootstoolsmc.events;

import com.darye.lootstoolsmc.utils.getRandomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class findBlockEvent {
    public static void startEvent(List<String> arguments){
        Plugin plugin = (Bukkit.getPluginManager().getPlugin("LootsToolsSpigot"));

        JSONObject playerList = new JSONObject();
        JSONObject playerBlock = new JSONObject();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Material randomBlock = getRandomBlock.getRandomBlock();
            String playerName = player.getDisplayName();
            JSONObject blockProps = new JSONObject();
            blockProps.put("block", randomBlock.name());
            blockProps.put("found", false);
            playerBlock.put(playerName, blockProps);
            player.sendMessage("Encuentra  " + randomBlock.name() + " en los proximos 5 minutos o morirás! >:)");

        }

        playerList.put("players", playerBlock);
        // {"players":{"Darye":{"block":"TALL_SEAGRASS", "found": false}, "dacaco046":{"block":"DIRT", found: true}}}

        try (FileWriter file = new FileWriter("findBlockEvent\\players.json")) {
            file.write(playerList.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }


        //System.out.println(arguments);
        //System.out.println(arguments.get(2));

        try (Reader reader = new FileReader("events.json")) {
            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(reader);
            JSONObject eventDict = (JSONObject) data.get("events");
            JSONObject event = (JSONObject) eventDict.get("findBlock");

            FileWriter file = new FileWriter("events.json");
            event.replace("enabled", true);
            eventDict.replace("findBlock", event);
            data.replace("events", eventDict);
            file.write(data.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                try (Reader reader = new FileReader("events.json")) {
                    JSONParser parser = new JSONParser();
                    JSONObject data = (JSONObject) parser.parse(reader);
                    JSONObject eventDict = (JSONObject) data.get("events");
                    JSONObject event = (JSONObject) eventDict.get("findBlock");
                    boolean enabled = (boolean) event.get("enabled");

                    JSONObject eventProps = new JSONObject();
                    eventProps.put("enabled", false);



                    @SuppressWarnings("resource")
                    FileWriter file = new FileWriter("events.json");
                    event.replace("enabled", false);
                    eventDict.replace("findBlock", event);
                    data.replace("events", eventDict);
                    file.write(data.toJSONString());
                    file.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                System.out.println("5 MINUTOS HAD BEEN PAST! ZA WAAARUDOOOO");
            }
        }, 6000L);
    }
}
