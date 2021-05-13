package com.darye.lootstoolsmc.challenges.eatItem;
/*

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class eatItem {
    public static Hashtable<String, Boolean> canEatDict = new Hashtable<String, Boolean>();
    public static Hashtable<String, Integer> canEatValuesDict = new Hashtable<String, Integer>();
    public static JSONObject ateItem = new JSONObject();
    public static JSONObject itemToEat = new JSONObject();
    static ArrayList MaterialHungerPair = new ArrayList();
    public static ArrayList<String> eatItemDeaths = new ArrayList<String>();

    public static Material material;
    public static String materialName = "pito";

    public static void startEvent(String[] args) {
        try {
            Plugin plugin = (Bukkit.getPluginManager().getPlugin("LootsToolsSpigot"));
            if (args.length > 0) {
                int feedValue = 0;
                try {
                    feedValue = Integer.parseInt(args[2]);
                } catch (Exception e) {
                    feedValue = 0;
                }
                List<String> itemNames = new ArrayList<>();
                for (Material materialType : Material.values()) {
                    itemNames.add(materialType.toString().toLowerCase(Locale.ROOT));
                }
                switch (args[0]){
                    case "oneRandom":
                        Random rand = new Random();
                        String randomItem = itemNames.get(rand.nextInt(itemNames.size()));
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            try{
                                JSONObject obj = new JSONObject();

                                obj.put("hungerValue", feedValue);
                                obj.put("hasEaten", false);
                                LocalDateTime endsOn = LocalDateTime.now().plusSeconds(Integer.parseInt(args[1]));
                                obj.put("endsOn", endsOn);
                                JSONObject itemValues = new JSONObject();
                                itemValues.put(randomItem, obj);
                                itemToEat.put(player.getDisplayName(), itemValues);
                            }
                            catch (Exception ignored){}
                        }
                    case "allRandom":
                        for (Player player : Bukkit.getOnlinePlayers()) {

                            for (Player player1 : Bukkit.getOnlinePlayers()) {
                                try{
                                    rand = new Random();
                                    randomItem = itemNames.get(rand.nextInt(itemNames.size()));
                                    JSONObject obj = new JSONObject();
                                    obj.put("hungerValue", feedValue);
                                    obj.put("hasEaten", false);
                                    LocalDateTime endsOn = LocalDateTime.now().plusSeconds(Integer.parseInt(args[1]));
                                    obj.put("endsOn", endsOn);
                                    JSONObject itemValues = new JSONObject();
                                    itemValues.put(randomItem, obj);
                                    itemToEat.put(player1.getDisplayName(), itemValues);
                                }
                                catch (Exception ignored){}
                            }
                        }
                    default:
                        JSONObject obj = new JSONObject();
                        obj.put("hungerValue", feedValue);
                        obj.put("hasEaten", false);
                        LocalDateTime endsOn = LocalDateTime.now().plusSeconds(Integer.parseInt(args[1]));
                        obj.put("endsOn", endsOn);
                        JSONObject itemValues = new JSONObject();
                        itemValues.put(args[0], obj);
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            try{
                                itemToEat.put(player.getDisplayName(), itemValues);
                            }
                            catch (Exception ignored){}
                        }

                }
                Material material = Material.matchMaterial(args[0]);
                materialName = material.toString().toLowerCase(Locale.ROOT);
                try {
                    Integer time = Integer.parseInt(args[1]);
                    if (time != 0) {
                        canEatValuesDict.put(materialName, feedValue);
                        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                            @Override
                            public void run() {
                                int timeElapsed = 0;
                                LocalDateTime start = LocalDateTime.now();
                                while (timeElapsed < Integer.parseInt(args[1])) {
                                    canEatDict.put("canEat" + materialName, true);
                                    LocalDateTime now = LocalDateTime.now();
                                    timeElapsed = (int) Duration.between(start, now).getSeconds();
    //                            System.out.println(timeElapsed);
                                }
                                Bukkit.getScheduler().runTask(plugin, () -> {
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        try {
                                            // Arreglar el registrar que un jugador NO ha comido el item.
                                            // que llevas como 3 días con esto y no lo has terminado, pedazo de tonto
                                            // Por ahora el JSON es así:
                                            // {
                                            //  "Darye":{
                                            //    "diamond":{
                                            //      "HungerValue":5,
                                            //      "hasEaten":true,
                                            //      "endsOn": fecha
                                            //    },
                                            //    "brick":{
                                            //      "HungerValue":2,
                                            //      "hasEaten":false
                                            //      "endsOn": fecha
                                            //    }
                                            //  }
                                            //}
                                            // Tienes que mandar una lista de usuarios a eatItemDeaths, y luego matarlos, así, cuando mueran y esten en la lista, se mande el mensaje de muerte personalizado.
                                            JSONObject playerDict = (JSONObject) itemToEat.get(player.getDisplayName());
                                            JSONObject eatenItemDict = (JSONObject) playerDict.get(eatenItem.toString());
                                            if (){

                                            }
                                        } catch (Exception NullPointerException) {
                                            eatItemDeaths.add(player.getDisplayName());
                                            Bukkit.broadcastMessage(ChatColor.RED + player.getDisplayName() + " no comió " + materialName + "... ¡ELIMINADO! >:)");
                                            player.setHealth(0);
                                            System.out.println(player.getDisplayName());
                                        }
                                    }
                                });
                                canEatDict.put("canEat" + materialName, false);

                            }
                        });
                    } else {
                        Bukkit.broadcastMessage(ChatColor.RED + "Necesitas poner un tiempo en segundos y el item que quieres que sea comestible.");
                        Bukkit.broadcastMessage(ChatColor.RED + "Ej. /canEat <item> <time>");
                    }
                } catch (Exception e) {
                    Bukkit.broadcastMessage(ChatColor.RED + "Necesitas poner un tiempo en segundos y el item que quieres que sea comestible.");
                    Bukkit.broadcastMessage(ChatColor.RED + "Ej. /canEat <item> <time>");
                }
            } else {
                Bukkit.broadcastMessage(ChatColor.RED + "Necesitas poner un tiempo en segundos y el item que quieres que sea comestible.");
                Bukkit.broadcastMessage(ChatColor.RED + "Ej. /canEat <item> <time>");
            }
        } catch (Exception exception) {
            Bukkit.broadcastMessage(ChatColor.RED + "Necesitas poner un tiempo en segundos y el item que quieres que sea comestible.");
            Bukkit.broadcastMessage(ChatColor.RED + "Ej. /canEat <item> <time>");
        }
    }
}
*/
