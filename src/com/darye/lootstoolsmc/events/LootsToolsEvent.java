package com.darye.lootstoolsmc.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.darye.lootstoolsmc.utils.commandInterpreter;

public class LootsToolsEvent {
    static Thread sent;
    static Thread receive;
    static Socket socket;


    public static void main(String[] args) {
        Plugin plugin = (Bukkit.getPluginManager().getPlugin("LootsToolsSpigot"));
        try {
            socket = new Socket("localhost", 47474);
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        sent = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    while (true) {
                        System.out.println("Trying to read...");
                        String in = stdIn.readLine();
                        String[] str = in.split("\\|");
                        List<String> arguments = new ArrayList<String>();
                        arguments = Arrays.asList(str);
                        if (arguments.get(0).contains("MINECRAFT")) {
                            List<String> finalArguments = arguments;
                            Bukkit.getScheduler().runTask(plugin, () -> {
                                commandInterpreter.interpretameEstaXD(finalArguments);
                            });
                        }
//                                System.out.println(in);
                        out.print("Try" + "\r\n");
                        out.flush();
                        System.out.println("Message sent");
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
        });
        sent.start();
        try {
            sent.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}