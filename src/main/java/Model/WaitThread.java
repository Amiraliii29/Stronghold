package Model;

public class WaitThread extends Thread {
    
    @Override public void run(){
        try {
            this.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

