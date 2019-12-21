package agh.cs.project1;

import java.util.*;

public class Animal extends MapEntity {

    private MapDirection direct;
    private JungleSavannah map;
    private List<IPositionChangeObserver> observers;
    private DNA dna;
    int energy;
    int age;
    boolean vitality;
    boolean followed;
    List<Animal> children;
    List<Animal> parents;

    Animal(JungleSavannah map, Vector2d initialPosition, int startEnergy) {
        this.map = map;
        this.position = initialPosition;
        this.observers = new ArrayList<>();
        this.energy = startEnergy;
        this.age = 0;
        this.dna = new DNA();
        Random g = new Random();
        MapDirection tmp = MapDirection.NORTH;
        this.direct = tmp.toMapDirection(this.dna.genome[g.nextInt(this.dna.numberOfGenes)]);
        this.vitality = true;
        this.followed = false;
    }

    private Animal(JungleSavannah map, Vector2d initialPosition, int energy, Animal parent1, Animal parent2) {
        this.map = map;
        this.position = initialPosition;
        this.observers = new ArrayList<>();
        this.energy = energy;
        this.age = 0;
        this.dna = new DNA(parent1.dna, parent2.dna);
        Random g = new Random();
        MapDirection tmp = MapDirection.NORTH;
        this.direct = tmp.toMapDirection(this.dna.genome[g.nextInt(this.dna.numberOfGenes)]);
        this.vitality = true;
        this.followed = false;
        if(parent1.followed || parent2.followed || parent1.parents != null || parent2.parents != null)
            addNewFamilyMember (parent1,parent2);
    }
    
    void animalBreeding(Animal other) {
        if(other == null) return;
        if(this.energy < 25 || other.energy < 25 || pedophiliaPrevention(other)) return;
        int e1 = this.energy/4;
        int e2 = other.energy/4;
        this.energy -= e1;
        other.energy -= e2;
        Animal animal = new Animal(map, this.getPosition(),e1 + e2,this, other);
        this.map.animalBorn(animal);
    }

    private void addNewFamilyMember (Animal parent1, Animal parent2) {
        this.parents = new ArrayList<>();
        if(parent1.followed || parent1.parents != null)
            addRelations(parent1);
        if(parent2.followed || parent2.parents != null)
            addRelations(parent2);
    }
    private void addRelations (Animal parent) {
        if(parent.children == null)
            parent.children = new ArrayList<>();
        parent.children.add(this);
        this.parents.add(parent);
    }

    public void setAsFollowed () {
        this.followed = true;
        this.children = new ArrayList<>();
    }

    private boolean pedophiliaPrevention (Animal other){
        return other.age < 18 || this.age < 18;
    }

    public MapDirection getMapDirection() {
        return this.direct;
    }

    void addObserver(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        this.observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver iter : this.observers) {
            iter.positionChanged(oldPosition, newPosition, this);
        }
    }
    void wasItYourFinalJourney() {
        if(this.energy <= 0 || this.age > 1500)
            this.vitality = false;
    }

    void move(Vector2d max, Vector2d min) {
        Random g = new Random();
        int move = (this.dna.genome[g.nextInt(this.dna.numberOfGenes)] + this.direct.toInt())%8;
        this.direct = this.direct.toMapDirection(move);
        Vector2d tmp = this.position.add(this.direct.toUnitVector());
        Vector2d mover = new Vector2d((tmp.x + max.x - min.x) % (max.x - min.x) + min.x,(tmp.y + max.y - min.y) % (max.y - min.y) + min.y);
        if (map.canMoveTo(mover,this)) {
            Vector2d previousPosition = this.position;
            this.position = mover;
            this.positionChanged(previousPosition, this.position);
            this.energy -= this.map.moveEnergy;
            this.map.eventNoticed(previousPosition);
            this.map.eventNoticed(this.position);
        }
    }

    void eatPlant(Plant plant) {
        if(plant == null) return;
        this.energy = Math.min(this.energy + plant.food,this.map.startEnergy*2);
    }
}
