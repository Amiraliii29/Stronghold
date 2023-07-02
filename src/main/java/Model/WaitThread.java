package Model;

public class WaitThread extends Thread {
    
    @Override public void run(){
        try {
            this.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

