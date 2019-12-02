package controller;


import agents.Agent;
import agents.Cashier;
import agents.Director;
import agents.Supervisor;
import clients.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RandomGenerator;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dispatcher {
    Logger log = LoggerFactory.getLogger(Dispatcher.class);
    RandomGenerator random = new RandomGenerator();
    Comparator<Agent> agentsPriority = Comparator.comparingInt(firstAgent -> firstAgent.priority);
    PriorityQueue<Agent> availableAgents = new PriorityQueue<>(agentsPriority);
    List<Agent> unavailableAgents = new LinkedList<>();
    List<Client> waitingClients = new LinkedList<>();
    ExecutorService executorService;
    private int agentsTotal;

    public void createAgentCollection(int cashiers, int supervisors, int directors) {
        log.info("Total of cashiers: " + cashiers);
        log.info("Total of supervisors: " + supervisors);
        log.info("Total of directors: " + directors);
        agentsTotal = cashiers + supervisors + directors;
        int count = 1;
        while (cashiers > 0) {
            Cashier cashier = new Cashier(0, "Cashier " + count);
            availableAgents.add(cashier);
            cashiers--;
            count++;
        }

        count = 1;
        while (supervisors > 0) {
            Supervisor supervisor = new Supervisor(1, "Supervisor " + count);
            availableAgents.add(supervisor);
            supervisors--;
            count++;
        }
        count = 1;
        while (directors > 0) {
            Director director = new Director(2, "Director " + count);
            availableAgents.add(director);
            directors--;
            count++;
        }
    }

    public void attend(List unattendedClients) {
        waitingClients = unattendedClients;
        log.info("--------------> Total of Clients: " + unattendedClients.size() + " <-------------");
        executorService = Executors.newFixedThreadPool(agentsTotal);
        while (unattendedClients != null) {
            Runnable runnable = new Client(waitingClients.get(0).getInfo(), availableAgents.peek());
            executorService.execute(runnable);
            unattendedClients.remove(0);
            Agent agent = availableAgents.peek();
            availableAgents.remove(agent);
            unavailableAgents.add(agent);

            if (availableAgents.size() == 0) {
                int size = random.getRandomInt(0, unavailableAgents.size());
                Agent flag = unavailableAgents.get(size);
                availableAgents.add(flag);
                unavailableAgents.remove(flag);
            }

            if (unattendedClients.size() == 0) {
                unattendedClients = null;
            }
        }
        while (unavailableAgents.size() > 0) {
            int size = random.getRandomInt(0, unavailableAgents.size());
            Agent agent = unavailableAgents.get(size);
            log.info("------------------------------------------------------------BUSY AGENTS");
            availableAgents.add(agent);
            unavailableAgents.remove(agent);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        log.info("FINISHED PROCESS-------------------------------------------------------");
    }


}
