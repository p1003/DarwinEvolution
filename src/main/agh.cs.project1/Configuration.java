package agh.cs.project1;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class Configuration {
    private int width;
    private int height;

    private int startEnergy;
    private int moveEnergy;
    private int plantEnergy;
    private float jungleRatio;

    private int animalAmount;
    private int plantsAmount;
    private int sleepingTime;

    private int dailyJungleGrow;
    private int dailySavannahGrow;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getJungleRatio() {
        return jungleRatio;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public int getAnimalAmount() {
        return animalAmount;
    }

    public int getPlantsAmount() {
        return plantsAmount;
    }

    public int getSleepingTime() {
        return sleepingTime;
    }

    public int getDailyJungleGrow() {
        return dailyJungleGrow;
    }

    public int getDailySavannahGrow() {
        return dailySavannahGrow;
    }

    private void validateValues() {
        final String errorPrefix = "InvalidConfigOption:";

        if(width <= 0) {
            throw new IllegalArgumentException(errorPrefix + "Width must be a positive integer");
        }
        if(height <= 0) {
            throw new IllegalArgumentException(errorPrefix + "Height must be a positive integer");
        }
        if(0.0 >= jungleRatio && jungleRatio > 1.0) {
            throw new IllegalArgumentException(errorPrefix + "Jungle ration must be in range: 0.0 - 1.0");
        }
        if(startEnergy <= 0) {
            throw new IllegalArgumentException(errorPrefix + "Start energy must be positive");
        }
        if(plantEnergy <= 0) {
            throw new IllegalArgumentException(errorPrefix + "Plant energy must be positive");
        }
        if(moveEnergy < 0) {
            throw new IllegalArgumentException(errorPrefix + "Move energy mustn't be negative");
        }
        if(animalAmount <= 1) {
            throw new IllegalArgumentException(errorPrefix + "You should create at least 2 animals");
        }
        if(plantsAmount < 0) {
            throw new IllegalArgumentException(errorPrefix + "I can't generate anti-matter plants (their number must be positive or equals zero)");
        }
        if(dailyJungleGrow < 0) {
            throw new IllegalArgumentException(errorPrefix + "I can't daily delete plants from Jungle");
        }
        if(dailySavannahGrow < 0) {
            throw new IllegalArgumentException(errorPrefix + "I can't daily delete plants from Savannah");
        }
    }
    public Configuration () {

    }
    public Configuration loadConfig (File filename) {
        Gson gson = new Gson();
        Configuration config;
        try {
            config = gson.fromJson(new FileReader(filename), Configuration.class);
        } catch (IOException | JsonParseException e) {
            throw new RuntimeException(e);
        }
        if(config == null) {
            throw new IllegalArgumentException("Invalid config file");
        }
        config.validateValues();
        return config;
    }
}
