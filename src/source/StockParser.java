/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import source.commands.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import source.commands.Exit;

/**
 *
 * @author Seventh
 */
public class StockParser {
    
    public ArrayList<Stock> stocks;
    private static final String LINE = "_$ ";
    private static final Map<String, Command> commands = new HashMap<String, Command>();
    private static boolean terminate = false;
    
    public static void main(String[] args) {
        WebReader reader = new WebReader();
        StockTemplate template = new StockTemplate();
        loadCommands();
        run();
    }
    
    public static void run() {
        String[] tokens;
        String arguments;
        String command;
        while(!getStatus()) {
            displayCommandLine();
            Scanner reader = new Scanner(System.in);
            arguments = reader.nextLine();
            tokens = arguments.split("\\s");
            command = tokens[0];
            Command c = commands.get(command);
<<<<<<< HEAD
            c.parse(arguments);
=======
            if (c == null) {
                System.out.println("Invalid Command. Please try again...");
            } else {
                c.parse(tokens);
            }
>>>>>>> a4314ba024b38feb74000628753f3cc79647ecdd
        }
    }
    
    public static boolean getStatus() { return terminate; }
    public static void Terminate() { terminate = true; }
    
    private static void displayCommandLine() {
        System.out.print(LINE);
    }
    
    private static void loadCommands() {
        Command cmd;
        cmd = new Exit();
        commands.put(cmd.getIdentifier(), cmd);
        cmd = new Help();
        commands.put(cmd.getIdentifier(), cmd);
        cmd = new ModifyTemplate();
        commands.put(cmd.getIdentifier(), cmd);
        cmd = new ViewTemplate();
        commands.put(cmd.getIdentifier(), cmd);
        cmd = new RetrieveStocks();
        commands.put(cmd.getIdentifier(), cmd);
        cmd = new OutputPath();
        commands.put(cmd.getIdentifier(), cmd);
        cmd = new Echo();
        commands.put(cmd.getIdentifier(), cmd);
    }
}
