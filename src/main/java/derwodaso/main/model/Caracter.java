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
public class Caracter implements Comparable<Caracter> {

    Actor actor;
    String name;

    public Caracter(String name, Actor actor) {
        this.actor = actor;
        this.name = name;
    }

    public Actor getActor() {
        return actor;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public int compareTo(Caracter o) {

        int res = 0;

        try {
            res = this.getName().compareTo(o.getName());
        } catch (Exception e) {
            // dont care
        }

        return res;
    }

}
