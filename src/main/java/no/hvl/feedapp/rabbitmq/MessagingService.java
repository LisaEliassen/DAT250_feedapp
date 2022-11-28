package no.hvl.feedapp.rabbitmq;

import java.util.List;
import java.util.Scanner;

public class MessagingService {

    public static void main(String[] args) throws Exception {
        String[] argv = {"Hello", "World", "YOLO"};
        ReceiveLogsTopic.main(argv);
        EmitLogTopic.main(argv);
    }
}
