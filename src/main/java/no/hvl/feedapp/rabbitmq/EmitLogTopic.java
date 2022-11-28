package no.hvl.feedapp.rabbitmq;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import no.hvl.feedapp.mongodb.MongoDBService;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        MongoDBService mongoDBService = new MongoDBService();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            //String routingKey = getRouting(argv);
            //String message = getMessage(argv);

            while(true) {
                Map<Long,String> pollResults = new HashMap<>();
                MongoCollection<Document> pollCollection = mongoDBService.readCollection("test");
                try (MongoCursor< Document > cur = pollCollection.find().iterator()) {
                    while (cur.hasNext()) {
                        var doc = cur.next();
                        var poll = new ArrayList<> (doc.values());
                        pollResults.put((Long) poll.get(1), String.format("PollID: %s, Title: %s, Result: %s", poll.get(1), poll.get(2), poll.get(3)));
                    }
                }
                for (Map.Entry<Long, String> set : pollResults.entrySet()) {
                    Long pollID = set.getKey();
                    String pollMessage = set.getValue();
                    channel.basicPublish(EXCHANGE_NAME, String.valueOf(pollID), null, pollMessage.getBytes("UTF-8"));
                    System.out.println(" [x] Sent '" + pollID + "':'" + pollMessage + "'");
                }
                Thread.sleep(10000);
            }
            //channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
        }
    }

    private static String getRouting(String[] strings) {
        if (strings.length < 1)
            return "anonymous.info";
        return strings[0];
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 2)
            return "Hello World!";
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) return "";
        if (length < startIndex) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
