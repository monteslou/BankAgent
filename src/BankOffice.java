import clients.Client;
import controller.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RandomGenerator;

import java.util.LinkedList;
import java.util.List;

public class BankOffice {
    public static void main(String[] args) {

        Logger log = LoggerFactory.getLogger(BankOffice.class);
        List<Client> unattendedClients = new LinkedList<>();
        RandomGenerator randomGenerator = new RandomGenerator();

        int clientQuantity = 10;

        if (clientQuantity == 0) {
            log.info("None clients for attend");
        } else {
            int id = 1;
            while (clientQuantity > 0) {
                Client client = new Client("Client " + id);
                unattendedClients.add(client);
                clientQuantity--;
                id++;
            }

            int cashierQuantity = randomGenerator.getRandomInt(1, 3);
            int supervisorsQuantity = randomGenerator.getRandomInt(1, 3);
            int directorsQuantity = randomGenerator.getRandomInt(1, 3);

            Dispatcher dispatcher = new Dispatcher();
            dispatcher.createAgentCollection(cashierQuantity, supervisorsQuantity, directorsQuantity);
            dispatcher.attend(unattendedClients);
        }
    }
}
