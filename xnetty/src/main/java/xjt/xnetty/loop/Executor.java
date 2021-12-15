package xjt.xnetty.loop;

public abstract class Executor {
    public abstract void execute();
    public void doExecute(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                execute();
            }
        }).start();
    }
}
