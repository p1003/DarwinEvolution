package agh.cs.project1;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.lang.Thread.sleep;

public class SimulationRunner {
    JungleSavannah map;
    private int sleepingTime;
    SwingMapVisualizer visualizer;
    private OperationsPanel operator;
    boolean paused;

    public SimulationRunner(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, int animalAmount, int plantsAmount,
                            double jungleRatio, int sleepingTime, int dailyJungleGrow, int dailySavannahGrow) {
        this.map = new JungleSavannah(width, height, startEnergy, moveEnergy, plantEnergy, plantsAmount, jungleRatio, dailyJungleGrow, dailySavannahGrow);
        this.sleepingTime = sleepingTime;
        this.visualizer = new SwingMapVisualizer(map);
        this.map.visualizer = this.visualizer;

        JFrame frame = new JFrame();
        this.visualizer.setPreferredSize(new Dimension(map.mapMax.x * 16 - 1, map.mapMax.y * 16 - 1));
        this.operator = new OperationsPanel(this);
        this.operator.setPreferredSize(new Dimension(300, map.mapMax.y * 16 - 1));
        Box mainBox = Box.createHorizontalBox();
        mainBox.add(this.visualizer);
        mainBox.add(this.operator);
        frame.add(mainBox);
        frame.pack();

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Random g = new Random();
        for (int i = 0; i < animalAmount; i++) {
            map.place(new Animal(map, new Vector2d(g.nextInt(map.mapMax.x), g.nextInt(map.mapMax.y)), startEnergy));
        }
    }

    public void runEvolution() throws InterruptedException {
        map.deadAnimalsRemover();
        map.run(map.mapMax, map.mapMin);
        map.animalFieldOperations();
        map.addBornAnimals();
        map.plantdom.dailyGrow();

        while (paused) {
            sleep(30);
        }

        sleep(this.sleepingTime);
    }
}
