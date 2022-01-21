package xjt.kafka.stream;

import xjt.kafka.KConfig;
import xjt.kafka.clients.KProducerClients;

import java.util.Scanner;

public class ProduceCase {
    public static void main(String[] args) {
        KProducerClients kProducerClients = new KProducerClients(KConfig.BROKERS);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            kProducerClients.produce("streams-plaintext-input", null, scanner.nextLine());
        }
    }
}
