public class Timer extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Main.invoke();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayOfMessages.setNotBusy(true);
        ArrayOfMessages.clear();

    }
}
