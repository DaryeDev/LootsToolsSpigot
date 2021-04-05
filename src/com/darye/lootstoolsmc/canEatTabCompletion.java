package com.darye.lootstoolsmc;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.FileSystems;
import java.util.*;

public class canEatTabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> itemNames = new ArrayList<>();
            itemNames.add("oneRandom");
            itemNames.add("allRandom");
            for (Material materialType : Material.values()) {
                itemNames.add(materialType.toString().toLowerCase(Locale.ROOT));
            }
            return itemNames;
        }

        return null;
    }
}
