package objects;

public class Mob extends Object implements Runnable{
    private String name;
    private Thread thread = new Thread(this);

    // флаг для работы временного усыпления
    private boolean suspendFlag = false;
    // флаг для полной остановки потока
    private boolean stop = false;

    @Override
    public void check() {
        name();
    }

    public Mob(int x, int y, int id, String name){
        super(x, y, id, "mob");
        this.name = name;

        thread.setName(this.name);
        start();
    }

    // установка приоритета потока, как много он будет требовать
    // ресурсов(точнее сказать как часто) у проца
    // относительно других потоков в программе
    void setPriority(int priority){ thread.setPriority(priority); }

    // метод приостановки потока
    synchronized void suspend(){ suspendFlag = true; }

    // возобновение работы после приостановки
    synchronized void resume(){ suspendFlag = false; notify(); }

    // умертвление насовсем
    void stop(){ stop = true; }

    // запуск потока(можно этот метод запускать в конструкторе), тогда он сразу начинает работать вызываея метод run()
    void start(){ thread.start(); }

    @Override
    public void run() {
        try{
            // В этом цикле будут вычислятся шаги моба
            while (!stop){
                // конструкция для работы временного усыпления моба
                synchronized (this){
                    while(suspendFlag){
                        wait();
                    }
                }
                // место для описания действия моба
                // ====================================

                //moveToPatch();
                //System.out.println(speed);

                // ====================================
                Thread.sleep(1000); // спать n м.секунд
            }
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void name(){
        System.out.print(" Mob :" + name);
    }
}
