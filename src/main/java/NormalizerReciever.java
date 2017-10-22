import com.rabbitmq.client.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NormalizerReciever {

    private final static String QUEUE_NAME = "normalizer";
    private static JSONArray jArray = new JSONArray();

    public static void main(String[] argv) throws Exception {
        Thread t = new Thread(new NormalizerRunnable());
        t.start();

       /* ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
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
                jArray.put(json);


                if (jArray.length()>=2){
                    System.out.println("JsonArr with responses: " + jArray.toString());
                    NormalizerSender ns = new NormalizerSender();
                    try {
                        ns.sendToAggregator(jArray.toString());
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumer);*/
    }

}
