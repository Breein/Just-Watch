package objects;

public class Eat extends Object{
    private int amount;

    public Eat(int x, int y, int id, int amount){
        super(x, y, id, "eat");
        this.amount = amount;
    }

    public int amount(){
        return amount;
    }

    @Override
    public void check() {
        System.out.print(" Food amount: " +  amount());
    }
}
