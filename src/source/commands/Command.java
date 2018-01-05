/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source.commands;

/**
 *
 * @author Said Akhrarov
 */
public abstract class Command {

    public abstract void parse(String args);
    
    public abstract void parse(String[] args);
    
    public abstract void execute();
    
    public abstract void help();
    
    public abstract String getIdentifier();
    
}
