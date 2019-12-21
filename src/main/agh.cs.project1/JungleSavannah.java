package agh.cs.project1;

import java.util.*;
import static java.lang.Thread.sleep;

public class JungleSavannah extends AbstractWorldMap{
    Plantdom plantdom;
    int numberOfPlants;
    public SwingMapVisualizer visualizer;
    Vector2d mapMax;
    Vector2d mapMin;
    Vector2d jungleMax;
    Vector2d jungleMin;
    private List<Animal> bornAnimals;
    private int bornCounter;
    private List<Vector2d> noticedEvents;
    int plantEnergy;
    int moveEnergy;
    int startEnergy;
    int dailyJungleGrow;
    int dailySavannahGrow;

    JungleSavannah(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, int plantsAmount, double jungleRatio, int dailyJungleGrow, int dailySavannahGrow) {
        this.noticedEvents = new ArrayList<>();
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.startEnergy = startEnergy;
        this.numberOfPlants = 0;
        this.dailyJungleGrow = dailyJungleGrow;
        this.dailySavannahGrow = dailySavannahGrow;
        this.mapMax = new Vector2d (width,height);
        this.mapMin = new Vector2d (0,0);
        jungleGenerator(jungleRatio);
        this.plantdom = new Plantdom(this,plantsAmount);
        this.bornAnimals = new ArrayList<>();
        this.bornCounter = 0;
    }

    private void jungleGenerator (double jungleRatio) {
        if(jungleRatio >= 1) {
            this.jungleMax = new Vector2d(this.mapMax.x,this.mapMax.y);
            this.jungleMin = new Vector2d(0,0);
            return;
        }
        double jungleArea = mapMax.x * mapMax.y * jungleRatio;
        int radius = (int) (Math.sqrt(jungleArea)/2);
        int maxX = (this.mapMax.x / 2 + radius);
        if(maxX > this.mapMax.x) maxX = this.mapMax.x;
        int maxY = (this.mapMax.y / 2 + radius);
        if(maxY > this.mapMax.y) maxY = this.mapMax.y;
        int minX = (this.mapMax.x / 2 - radius);
        if(minX < this.mapMin.x) minX = this.mapMin.x;
        int minY = (this.mapMax.y / 2 - radius);
        if(minY < this.mapMin.y) minY = this.mapMin.y;

        this.jungleMax = new Vector2d(maxX,maxY);
        this.jungleMin = new Vector2d(minX,minY);
    }

    private float animalsAVGEnergy() {
        if(animals.isEmpty()) return 0;
        int counter = 0;
        for(Animal animal : animals)
            counter += animal.energy;
        return (float)counter/animals.size();
    }

    private float animalsAVGAge() {
        if(animals.isEmpty()) return 0;
        int counter = 0;
        for(Animal animal : animals)
            counter += animal.age;
        return (float)(counter/animals.size());
    }

     void addBornAnimals () {
        ListIterator<Animal> iterator = this.bornAnimals.listIterator();
        this.bornCounter += bornAnimals.size();
        while(iterator.hasNext()){
            Animal animal = iterator.next();
            iterator.remove();
            this.animals.add(animal);
            animalAdder(animal.getPosition(),animal);
        }
    }

     void deadAnimalsRemover() {
        ListIterator<Animal> iterator = animals.listIterator();
        while(iterator.hasNext()){
            Animal animal = iterator.next();
            if(!animal.vitality){
                eventNoticed(animal.getPosition());
                iterator.remove();
                animalsHash.get(animal.getPosition()).remove(animal);
                if(animalsHash.get(animal.getPosition()).isEmpty())
                    animalsHash.remove(animal.getPosition());
            }
        }
    }

    private float animalsAVGOnField () {
        int counter = 0;
        for(int i = mapMin.x; i < mapMax.x; i++) {
            for(int j = mapMin.y; j < mapMax.y; j++) {
                Object tmp = objectAt(new Vector2d(i,j));
                if(tmp != null && tmp.getClass() == Animal.class) counter++;
            }
        }
        return (float)animals.size()/counter;
    }

    void animalFieldOperations () {
        ListIterator<Vector2d> iterator = this.noticedEvents.listIterator();
        while(iterator.hasNext()) {
            Vector2d i = iterator.next();
            if (animalsHash.get(i) != null) {
                List<Animal> array = animalsHash.get(i);
                Animal a1 = getDominant(array);
                if (plantdom != null) {
                    if(plantdom.plantsHash != null) {
                        Plant plant = plantdom.plantsHash.get(i);
                        a1.eatPlant(plant);
                        plantdom.plantsHash.remove(i);
                        numberOfPlants--;
                    }
                }
                if (animalsHash.get(i).size() >= 2) {
                    animalsHash.get(i).remove(a1);
                    Animal a2 = getDominant(array);
                    animalsHash.get(i).add(a1);
                    a1.animalBreeding(a2);
                }
            }
            this.visualizer.updateTile(i);
            iterator.remove();
        }
    }

    private Animal getDominant (List<Animal> array) {
        Animal result = array.get(0);
        for(Animal iterator : array) {
            if(result.energy < iterator.energy)
                result = iterator;
        }
        return result;
    }

    void eventNoticed (Vector2d position) {
        if(!noticedEvents.isEmpty()) {
            for (Vector2d iterator : noticedEvents) {
                if (position.equals(iterator))
                    return;
            }
        }
        this.noticedEvents.add(position);
    }

    @Override
    public void place(Animal animal) {
        if(isOccupied(animal.getPosition())) return;
        animal.addObserver(this);
        this.animals.add(animal);
        animalAdder(animal.getPosition(),animal);
    }

    @Override
    public void animalBorn(Animal animal) {
        animal.addObserver(this);
        this.bornAnimals.add(animal);
    }

    @Override
    public void run(Vector2d max,Vector2d min) {
        if (animals.isEmpty()) return;
        for (Animal animal : animals) {
            animal.age++;
            animal.move(max,min);
            animal.wasItYourFinalJourney();
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position, Animal animal) {
        Object tmp = objectAt(position);

        if(tmp == null) return true;

        if(tmp.getClass() == Plant.class) {
            eventNoticed(position);
            return true;
        }

        if(animalsHash.get(position) == null) return true;
        if(animalsHash.get(position).size() > 2) return false;
        eventNoticed(position);
        return true;
    }

    @Override
    public MapEntity objectAt(Vector2d position) {
        MapEntity result = null;
        if(this.plantdom != null) {
            if (this.plantdom.plantsHash != null)
                result = this.plantdom.plantsHash.get(position);
        }
        if(result != null) return result;

        if(this.animals != null) {
            if(animalsHash.get(position) != null)
                result = getDominant(animalsHash.get(position));
        }
        return result;
    }
}
