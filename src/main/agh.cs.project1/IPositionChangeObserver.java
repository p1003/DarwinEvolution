package agh.cs.project1;

public interface IPositionChangeObserver {

    void positionChanged (Vector2d oldPosition, Vector2d newPosition, Animal animal);

}
