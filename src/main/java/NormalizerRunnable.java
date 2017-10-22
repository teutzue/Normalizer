import com.rabbitmq.client.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NormalizerRunnable implements Runnable {
    private final static String QUEUE_NAME = "normalizer";
    private static JSONArray jArray = new JSONArray();
    public void run() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        Channel channel = null;
        try {
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(" [*] Waiting for loan responses from the banks :). To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received loan response: '" + message + "' correlationId: " + properties.getCorrelationId());

                NormalizerService nz = new NormalizerService();
                message = nz.normalizeMessage(message);


                JSONObject json = new JSONObject(message);
                //jArray.put(json);
                ArrayOfMessages.addElement(json);


//                if (jArray.length()>=2){
//                    System.out.println("JsonArr with responses: " + jArray.toString());
//                    NormalizerSender ns = new NormalizerSender();
//                    try {
//                        ns.sendToAggregator(jArray.toString());
//                    } catch (TimeoutException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        };

        try {
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
