package com.darye.lootstoolsmc;

//import com.darye.lootstoolsmc.challenges.eatItem.canEatCommand;
import com.darye.lootstoolsmc.commands.CommandKit.CommandKit;
import com.darye.lootstoolsmc.challenges.challengesEventHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;


import com.darye.lootstoolsmc.events.LootsToolsEvent;

public class LootsToolsSpigotPlugin extends JavaPlugin implements Listener {
    FileConfiguration config = getConfig();
    public static String database;


    @Override
    public void onEnable() {
        database = getConfig().getString("DatabaseName");
        if (config.getBoolean("testModeEnabled")) {
            config.addDefault("testMode.noJump", false);
            config.addDefault("testMode.islam", false);
//	    	config.addDefault("testMode.testMode.pacifistWithPigs", false);
        }
        config.addDefault("events", true);
        config.options().copyDefaults(true);
        // eatItem Challenge
        //this.getCommand("caneat").setExecutor(new canEatCommand());
        this.getCommand("caneat").setTabCompleter(new canEatTabCompletion());
        this.getCommand("kit").setExecutor(new CommandKit());
        saveConfig();
        getServer().getPluginManager().registerEvents(new challengesEventHandler(), this);

        System.out.println("¡LootsToolsEX for Minecraft cargado!");


        // Enable our class to check for new players using onPlayerJoin()
        getServer().getPluginManager().registerEvents(this, this);

        Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                LootsToolsEvent.main(null);
            }
        });

    }
//    // Si muere alguien, se manda un mensaje
//    @EventHandler
//    public void onPlayerJoin(org.bukkit.event.entity.PlayerDeathEvent event) {
//        Player player = event.getEntity();
//        player.sendMessage(player.getName()+" se ha muerto!");
//    }

//    @EventHandler
//    public void canEatEvent(PlayerInteractEvent event) {
//        Player player = event.getPlayer();
//        if (event.getItem().getType().toString().contains("DIAMOND")){
//            player.setFoodLevel(player.getFoodLevel()-5);
//            ItemStack eatenItem = player.getInventory().getItemInMainHand();
//            if (eatenItem.getAmount() > 1){ eatenItem.setAmount(eatenItem.getAmount() - 1);player.getInventory().setItemInMainHand(eatenItem);}
//            else player.getInventory().removeItem(eatenItem);
//            player.sendMessage("No comas diamantes, "+player.getDisplayName());
//    }}

    final double STILL = -0.0784000015258789;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (config.getBoolean("testMode.noJump")) {
            Player player = event.getPlayer();
            boolean isJumping = player.getVelocity().getY() > STILL;
            if (isJumping) {
                player.setHealth(0);
                Bukkit.broadcastMessage("¡" + player.getName() + " ha saltado! ¡Eliminado!");
            }
        }

    }

    // Pegar a cerdos está mal
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (config.getBoolean("testMode.pacifistWithPigs")) {
            if (e.getDamager() instanceof Player) {
                Entity whoWasHit = e.getEntity();
                Player whoHit = (Player) e.getDamager();
                Bukkit.broadcastMessage(whoWasHit.getType().toString());
                if (whoWasHit.getType().toString() == "PIG") {
                    whoHit.setHealth(0);
                    Bukkit.broadcastMessage("¡" + whoHit.getName() + " ha cometido HARAM! ¡Eliminado!");
                }
            }
        }
    }

    // Está prohibido por el Islam comer cerdo, por ello es Haram
    @EventHandler
    public void halam(PlayerItemConsumeEvent event) {
        if (config.getBoolean("testMode.islam")) {
            Player player = event.getPlayer();
            if (event.getItem().getType().toString().contains("PORKCHOP")) {
                player.setHealth(0);
                Bukkit.broadcastMessage("¡" + player.getName() + " ha cometido HARAM! ¡Eliminado!");
//    			player.chat("/give @a porkchop");
            }

            // Esto sirve para saber que item estoy consumiendo
//    		Bukkit.broadcastMessage(event.getItem().getType().toString());
        }
    }

}