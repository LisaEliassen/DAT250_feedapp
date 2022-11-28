package no.hvl.feedapp.rabbitmq;

import java.util.Scanner;

public class MessagingService {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID of polls you want to sub (separate multiple IDs with blank space): ");
        String input = sc.nextLine();
        String[] pollIDs = input.split(" ");

        Receiver.main(pollIDs);
    }
}
