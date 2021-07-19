package com.darye.lootstoolsmc.challenges;

import com.darye.lootstoolsmc.events.floorIsLavaEvent;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static org.bukkit.Material.getMaterial;
//import com.darye.lootstoolsmc.challenges.eatItem.eatItem;


public class challengesEventHandler implements Listener {

    public boolean eventEnabled(String eventName){
        try (Reader reader = new FileReader("events.json")) {
            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(reader);
            JSONObject eventDict = (JSONObject) data.get("events");
            JSONObject event = (JSONObject) eventDict.get(eventName);
            return (boolean) event.get("enabled");

        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getEventProp(String eventName, String eventPropName) {
        try (Reader reader = new FileReader("events.json")) {
            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(reader);
            JSONObject eventDict = (JSONObject) data.get("events");
            JSONObject floorIsLavaEvent = (JSONObject) eventDict.get(eventName);
            return (String) floorIsLavaEvent.get(eventPropName);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /*
    //eatItem Challenge
    @EventHandler
    public void canEatEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ArrayList playerList = new ArrayList();
        ItemStack eatenItem = player.getInventory().getItemInMainHand();
        try {
            JSONObject playerDict = (JSONObject) eatItem.itemToEat.get(player.getDisplayName());
            if (playerDict.containsKey(eatenItem)){
                JSONObject eatenItemDict = (JSONObject) playerDict.get(eatenItem.toString());
                eatenItemDict.put("hasEaten", true);
                Integer hungerValue = (Integer) eatenItemDict.get("hungerValue");
                int foodLevel = (player.getFoodLevel() + hungerValue);
                if (foodLevel > 20) {
                    foodLevel = 20;
                }
                if (player.getFoodLevel() < 20 || hungerValue <= 0) {
                    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 1f, 1f);

                    if (eatenItem.getAmount() > 1) {
                        eatenItem.setAmount(eatenItem.getAmount() - 1);
                        player.getInventory().setItemInMainHand(eatenItem);
                    } else player.getInventory().removeItem(eatenItem);
                    Bukkit.broadcastMessage(player.getDisplayName() + " ha comido " + event.getItem().getType().toString().toLowerCase(Locale.ROOT));
                }
                player.setFoodLevel(foodLevel);

            } else {
                playerList.clear();
            }
        } catch (Exception ignored) {
        }
    }

    @EventHandler
    public void eatItemDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (eatItem.eatItemDeaths.contains(player.getDisplayName())) {
            event.setDeathMessage(ChatColor.RED + player.getDisplayName() + " ha muerto por no comer " + eatItem.materialName);
            eatItem.eatItemDeaths.remove(player.getDisplayName());
        }
    }*/

    //onTopOfBlock
    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (eventEnabled("floorIsLava")) {
            if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == getMaterial(getEventProp("floorIsLava", "block"))) {
                player.setHealth(0);
                Bukkit.broadcastMessage(player.getDisplayName() + " ha muerto por tocar "+ getEventProp("floorIsLava", "block") +"!");
            }
        }
        if (eventEnabled("findBlock")) {
            if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == getMaterial(getEventProp("findBlock", "block"))) {


                Bukkit.broadcastMessage(player.getDisplayName() + " ha encontrado "+ getEventProp("findBlock", "block") +"!");
            }
        }
}
}

