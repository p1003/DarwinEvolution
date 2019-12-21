package agh.cs.project1;

import java.util.*;

abstract public class AbstractWorldMap implements IWorldMap,IPositionChangeObserver {

    HashMap<Vector2d, List<Animal>> animalsHash;
    List<Animal> animals;

    AbstractWorldMap () {
        this.animalsHash = new HashMap<>();
        this.animals = new ArrayList<>();
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if (objectAt(position) == null) return false;

        return objectAt(position).getClass() == Animal.class;
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {

        animalAdder (newPosition,animal);

        this.animalsHash.get(oldPosition).remove(animal);
        if(this.animalsHash.get(oldPosition).isEmpty())
            this.animalsHash.remove(oldPosition);
    }

    void animalAdder (Vector2d position, Animal animal) {
        if(this.animalsHash.get(position) == null) {
            List<Animal> tmp = new ArrayList<>();
            tmp.add(animal);
            this.animalsHash.put(position,tmp);
        }
        else {
            this.animalsHash.get(position).add(animal);
        }
    }
}

