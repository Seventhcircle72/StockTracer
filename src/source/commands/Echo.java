/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Said Akhrarov
 */
public class Echo extends Command {

    @Override
    public void execute() {
        System.out.println("");
    }
    
    public void execute(String message) {
        System.out.println(message);
    }

    @Override
    public void help() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getIdentifier() { return "echo"; }

    @Override
<<<<<<< HEAD
    public void parse(String args) {
        // Take arguments and find String message
        if (hasMessage(args)) {
            String out = findMessage(args);
            execute(out);
        } else {
            execute();
        }
    }
    
    public String findMessage(String pattern) {
        // Start at index of 1, because 0 is the command reference
        // Loop through all of the tokens and append them into one string
        String message = "";
        Pattern regex = Pattern.compile("\"(.*)\"");
        Matcher matcher = regex.matcher(pattern);
        if (matcher.find() == true) {
            message = matcher.group(1);
        }
        return message;
    }
    
    public boolean hasMessage(String pattern) {
        boolean result = false;
        Pattern regex = Pattern.compile("\"(.*)\"");
        Matcher matcher = regex.matcher(pattern);
        if (matcher.find() == true) {
            result = true;
        }
        return result;
=======
    public void parse(String[] args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
>>>>>>> a4314ba024b38feb74000628753f3cc79647ecdd
    }
    
}
