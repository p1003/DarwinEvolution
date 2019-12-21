package agh.cs.project1;

public class Plant extends MapEntity{

    int food;
    Plant(Vector2d Position, int plantEnergy) {
        this.position = Position;
        this.food = plantEnergy;
    }

    public String toString () {
        return "*";
    }
}
