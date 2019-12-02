package clients;

import agents.Agent;
import controller.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RandomGenerator;

public class Client implements Runnable {
    Logger log = LoggerFactory.getLogger(Dispatcher.class);
    RandomGenerator random = new RandomGenerator();
    private String name;
    Operation operation = new Operation();
    Agent agent;

    public Client(String name) {
        this.name = name;
    }

    public Client(String name, Agent agent) {
        this.name = name;
        this.agent = agent;
    }

    public String getInfo() {
        return name + operation.getOperation();
    }

    @Override
    public void run() {
        int time = random.getRandomTime(10, 15) * 100;
        log.info(name + " attended by " + agent.getName() + " during " + time / 100 + " sec...");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
