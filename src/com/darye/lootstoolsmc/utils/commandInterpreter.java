package com.darye.lootstoolsmc.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.text.MessageFormat;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.json.simple.parser.ParseException;

public class commandInterpreter {
    // Create a class constructor for the Main class
    public commandInterpreter() {

    }
    public static void executeCmd(String cmd){
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }

    public static void interpretameEstaXD(List<String> arguments){
        JSONParser parser = new JSONParser();
        String message = "";
        String user = arguments.get(1);
        String cardName = arguments.get(2);
        Plugin plugin = (Bukkit.getPluginManager().getPlugin("LootsToolsSpigot"));
        System.out.println(arguments);
        switch (arguments.get(0)) {
            case "MINECRAFTCMD":
                String cmd = arguments.get(3);
                System.out.println(cmd);
                cmd = cmd.replace("MINECRAFTCMD ", "");
                System.out.println(cmd);
                if (cmd.contains("%PLAYER%")){
                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                        String player = onlinePlayers.getDisplayName();
                        cmd = cmd.replace("%PLAYER%", player);
                        commandInterpreter.executeCmd(cmd);
                    }}
                else{
                    commandInterpreter.executeCmd(cmd);
                }

                break;

            case "MINECRAFTPET":
                List<String> collarColorlist = new ArrayList<>();
                collarColorlist.add("0");collarColorlist.add("1");collarColorlist.add("2");collarColorlist.add("3");collarColorlist.add("4");collarColorlist.add("5");collarColorlist.add("6");collarColorlist.add("7");collarColorlist.add("8");collarColorlist.add("9");collarColorlist.add("10");collarColorlist.add("11");collarColorlist.add("12");collarColorlist.add("13");collarColorlist.add("14");collarColorlist.add("15");
                Random rand = new Random();
                String collarColor = collarColorlist.get(rand.nextInt(collarColorlist.size()));

                JSONObject minecraftPetJSON = new JSONObject();
                System.out.println(arguments.get(3));
                try {
                    minecraftPetJSON = (JSONObject) parser.parse(arguments.get(3));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String petName = minecraftPetJSON.get("Name").toString();

                try {
                    String petowner = minecraftPetJSON.get("Owner").toString();
                    String petType = minecraftPetJSON.get("Type").toString();
                    String petCMD = "";
                    switch(petType){
                        case "DOG":
                            rand = new Random();
                            collarColor = (collarColorlist.get(rand.nextInt(collarColorlist.size())));
                            petCMD = "execute at %PLAYER% run summon wolf ~ ~ ~ {Glowing:1b,CustomNameVisible:1b,Sitting:1b,CollarColor:%COLLARCOLOR%,CustomName:'{\"text\":\"%NAME%\",\"color\":\"dark_purple\",\"bold\":true}',Owner: %PLAYER%}";
                            petCMD = petCMD.replace("%COLLARCOLOR%", collarColor);
                            break;

                        case "CAT":
                            rand = new Random();
                            collarColor = (collarColorlist.get(rand.nextInt(collarColorlist.size())));
                            petCMD = "execute at %PLAYER% run summon cat ~ ~ ~ {CollarColor:%COLLARCOLOR%,Sitting:1,Glowing:1b,CustomNameVisible:1b,Owner:%PLAYER%,CustomName:'{\"text\":\"%NAME%\",\"color\":\"dark_purple\",\"bold\":true}',CatType:random}";
                            petCMD = petCMD.replace("%COLLARCOLOR%", collarColor);
                            break;

                        case "PARROT":
                            List<String> parrotColorlist = new ArrayList<>();
                            parrotColorlist.add("0");parrotColorlist.add("1");parrotColorlist.add("2");parrotColorlist.add("3");parrotColorlist.add("4");
                            rand = new Random();
                            String parrotColor= (parrotColorlist.get(rand.nextInt(parrotColorlist.size())));
                            petCMD = "execute at %PLAYER% run summon parrot ~ ~ ~ {Sitting:1,Glowing:1b,CustomNameVisible:1b,Owner:%PLAYER%, CustomName:'{\"text\":\"%NAME%\",\"color\":\"dark_purple\",\"bold\":true}',Variant:%PARROTCOLOR%,Sitting:1}";
                            petCMD = petCMD.replace("%PARROTCOLOR%", parrotColor);
                            break;

                        default:
                            break;

                    }
                    if (petowner.equals("ALL")) {
                        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                            String player = onlinePlayers.getDisplayName();
                            System.out.println(player);
                            commandInterpreter.executeCmd(petCMD.replace("%PLAYER%", player).replace("%NAME%", petName));
                        }
                    } else {
                        commandInterpreter.executeCmd(petCMD.replace("%PLAYER%", petowner).replace("%NAME%", petName));
                    }
                } catch (Exception exception) {
//                Pos no hay perro xd
                    System.out.println("No hay perro xd: " + exception);
                }
                message = MessageFormat.format("{0} ha canjeado {1}!.", user, cardName);
                Bukkit.broadcastMessage(message);
                break;

            case "MINECRAFTSOUND":


            default:
                break;
        }}}