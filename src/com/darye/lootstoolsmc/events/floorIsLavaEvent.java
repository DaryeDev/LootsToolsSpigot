package com.darye.lootstoolsmc.events;
import com.darye.lootstoolsmc.utils.getRandomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
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


public class floorIsLavaEvent  {
    public static void startEvent(List<String> arguments){
        Plugin plugin = (Bukkit.getPluginManager().getPlugin("LootsToolsSpigot"));
        Material randomBlock = getRandomBlock.getRandomBlock();


        //System.out.println(arguments);
        //System.out.println(arguments.get(2));


        Bukkit.broadcastMessage("Si tocais " + randomBlock.name() + " en los proximos 5 minutos, morireis! >:)");

        try (Reader reader = new FileReader("events.json")) {
            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(reader);
            JSONObject eventDict = (JSONObject) data.get("events");
            JSONObject event = (JSONObject) eventDict.get("floorIsLava");

            @SuppressWarnings("resource")
            FileWriter file = new FileWriter("events.json");
            event.replace("block", randomBlock.toString());
            event.replace("enabled", true);
            eventDict.replace("floorIsLava", event);
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
                    JSONObject event = (JSONObject) eventDict.get("floorIsLava");
                    String block = (String) event.get("block");
                    boolean enabled = (boolean) event.get("enabled");

                    JSONObject eventProps = new JSONObject();
                    eventProps.put("enabled", false);
                    eventProps.put("block", block);



                    @SuppressWarnings("resource")
                    FileWriter file = new FileWriter("events.json");
                    event.replace("block", randomBlock.toString());
                    event.replace("enabled", false);
                    eventDict.replace("floorIsLava", event);
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
