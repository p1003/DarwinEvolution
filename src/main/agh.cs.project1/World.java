package agh.cs.project1;

import java.io.File;
import java.util.*;

public class World {
    public static void main(String[] args) throws InterruptedException {
        Configuration config = new Configuration();
        config = config.loadConfig(new File("parameters.json"));
        SimulationRunner simulationRunner = new SimulationRunner(config.getWidth(),config.getHeight(),
                config.getStartEnergy(),config.getMoveEnergy(),config.getPlantEnergy(),config.getAnimalAmount(),
                config.getPlantsAmount(), config.getJungleRatio(), config.getSleepingTime(),config.getDailyJungleGrow(), config.getDailySavannahGrow());

            while (!simulationRunner.map.animals.isEmpty()) {
                simulationRunner.runEvolution();
            }
    }
}
