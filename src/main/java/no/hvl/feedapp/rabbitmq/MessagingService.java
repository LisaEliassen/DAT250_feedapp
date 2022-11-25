package no.hvl.feedapp.rabbitmq;

public class MessagingService {

    public static void main(String[] args) throws Exception {
        ReceiveLogs.main(args);
        EmitLog.main(args);
    }
}
