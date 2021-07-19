package com.darye.lootstoolsmc.utils;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class getRandomBlock {
    public static Material getRandomBlock(){
        List<Material> materialList = new ArrayList<Material>();
        for (Material block : Material.values()) {
            if (block.isBlock()) {
                materialList.add(block);
            }
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(materialList.size());
        //System.out.println(randomIndex);
        Material randomBlock = materialList.get(randomIndex);
        return randomBlock;
    }
}
