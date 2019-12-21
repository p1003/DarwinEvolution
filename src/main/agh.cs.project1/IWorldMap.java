package agh.cs.project1;

public interface IWorldMap {
    boolean canMoveTo(Vector2d position, Animal animal);

    void place(Animal animal);

    void run(Vector2d p1,Vector2d p2);

    boolean isOccupied(Vector2d position);

    void animalBorn(Animal animal);

    MapEntity objectAt(Vector2d position);
}
