package com.darye.lootstoolsmc.utils;

import com.darye.lootstoolsmc.events.floorIsLavaEvent;
import com.darye.lootstoolsmc.events.findBlockEvent;
import com.darye.lootstoolsmc.events.killMobEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class eventCaller {
    public static class callEvent {
        public callEvent(List<String> arguments) {
            String cmd = arguments.get(3);
            List<String> cmdParts = new ArrayList<String>();
            String[] cmdSplit = cmd.split(" ");
            cmdParts = Arrays.asList(cmdSplit);
            switch (cmdParts.get(1)){
                case "floorIsLava":
                    floorIsLavaEvent.startEvent(cmdParts);
                case "findBlock":
                    findBlockEvent.startEvent(cmdParts);
                case "killMob":
                    killMobEvent.startEvent(cmdParts);
            }
        }
    }
}
