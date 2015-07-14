package objects;

public abstract class Object implements Objects{
    private int x, y, id;
    private String type;

    public Object(int x, int y, int id, String type){
        this.x = x;
        this.y = y;
        this.id = id;
        this.type = type;
    }

    @Override
    public int x(){
        return x;
    }
    @Override
    public int y(){
        return y;
    }
    @Override
    public int id(){
        return id;
    }
    @Override
    public String tid() {
        return type + "_" + id;
    }
    @Override
    public Objects link() {
        return this;
    }

    /////////////////////////////

    @Override
    public void x(int x){
        this.x = x;
    }
    @Override
    public void y(int y){
        this.y = y;
    }
}
