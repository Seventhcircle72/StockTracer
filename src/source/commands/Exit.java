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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String getIdentifier() { return "exit"; }
    
}
