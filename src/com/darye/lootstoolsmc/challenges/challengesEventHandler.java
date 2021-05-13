package com.darye.lootstoolsmc.challenges;

import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
//import com.darye.lootstoolsmc.challenges.eatItem.eatItem;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class challengesEventHandler implements Listener {

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
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.STONE) {
            Bukkit.broadcastMessage(player.getDisplayName() + " ha encontrado su bloque!");
        }
    }
}
