import org.json.JSONObject;

import javax.json.Json;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) {
        Thread t = new Thread(new NormalizerRunnable());
        t.start();
    }

    public static void printArray(){
        System.out.println("-------------- "+ArrayOfMessages.getList().toString());;
    }

    public static void invoke() throws InterruptedException {
        NormalizerSender ns = new NormalizerSender();
        try {
            ns.sendToAggregator(ArrayOfMessages.getList().toString());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printArray();
    }

}
