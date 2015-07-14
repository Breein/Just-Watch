package world;

import objects.Objects;

import java.util.HashMap;

public class Cell {
    private int x, y, id;
    private HashMap<String, Objects> content = new HashMap<String, Objects>();
    private int codeType = -1;

    public Cell(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int codeType(){
        return codeType;
    }
    public void codeType(int codeType){
        this.codeType = codeType;
    }

    public void beHere(Objects object) {
        content.put(object.tid(), object);
    }

    public void outHere(Objects object) {
        content.remove(object.tid());
    }

    public void whoHere() {
        System.out.print("Content in cell[" + y + ":" + x + "]: ");

        for(Objects object : content.values()){
            object.check();

//            System.out.print(objects.getClass().getName() + " ");
//            if(objects.tid().equals("mob")){
//                System.out.print("Mob: " + objects.link() + ", ");
//                //System.out.print("Mob:" + mobs.get("mobs_" + objects.ID()).gName() + ", ");
//            }else{
//                System.out.print("Eat: " + objects.link() + ", ");
//            }

        }

        System.out.println("");
    }
}
