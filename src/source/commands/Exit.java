/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source.commands;

import source.StockParser;

/**
 *
 * @author Said Akhrarov
 */
public class Exit extends Command {

    @Override
    public void execute() {
        StockParser.Terminate();
    }

    @Override
    public void help() {
        
    }
    
    @Override
    public String getIdentifier() { return "exit"; }

    @Override
<<<<<<< HEAD
    public void parse(String args) {
=======
    public void parse(String[] args) {
>>>>>>> a4314ba024b38feb74000628753f3cc79647ecdd
        execute();
    }
    
}
