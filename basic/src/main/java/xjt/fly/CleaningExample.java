package xjt.fly;


import sun.misc.Cleaner;

public class CleaningExample implements AutoCloseable {
    // A cleaner, preferably one shared within a library
    private  Cleaner cleaner;


    @Override
    public void close() throws Exception {

    }
}
