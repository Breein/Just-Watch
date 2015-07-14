package main;

import objects.*;
import render.Render;
import world.*;

import java.util.HashMap;

public class Executable implements Dimensions {
    public static void main(String[] args) {

        Cell[][] map;

        HashMap<String, Mob> mobs = new HashMap<String, Mob>();
        HashMap<String, Eat> eats = new HashMap<String, Eat>();

        Constructor constructor = new Constructor();

        map = constructor.map();

        constructor.test(20);


        new Render(map, mobs, eats);
    }
}
