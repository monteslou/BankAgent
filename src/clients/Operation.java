package clients;

import utils.RandomGenerator;

import java.util.Arrays;
import java.util.List;

public class Operation {
    RandomGenerator random = new RandomGenerator();
    List<String> operations = Arrays.asList("(Deposit)", "(Withdrawal)", "(Issue)");

    public String getOperation() {
        return operations.get(random.getRandomInt(0, operations.size()));
    }
}