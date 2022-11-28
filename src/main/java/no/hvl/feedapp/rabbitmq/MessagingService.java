package no.hvl.feedapp.rabbitmq;

public class MessagingService {

    public static void main(String[] args) throws Exception {
        String[] argv = {"Hello", "World", "YOLO"};
        ReceiveLogsTopic.main(argv);
        EmitLogTopic.main(argv);
    }
}
