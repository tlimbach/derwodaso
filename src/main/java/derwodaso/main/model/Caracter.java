/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derwodaso.main.model;

/**
 *
 * @author tlimbach
 */
public class Caracter
{
    Actor actor;
    String name;

    public Caracter( String name, Actor actor )
    {
        this.actor = actor;
        this.name = name;
    }

    public Actor getActor()
    {
        return actor;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
    
    
}
