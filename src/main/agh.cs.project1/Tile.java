package agh.cs.project1;

import javax.swing.*;

class Tile extends JLabel{

    private boolean isJungle;

    Tile(MapEntity entity, boolean Jungle) {
        this.isJungle = Jungle;
        if(entity == null)
            clearTile();
        else {
            if(entity.getClass() == Animal.class)
                drawAnimal((Animal) entity);
            else
                drawGrass();
        }
    }

    void clearTile () {
        if(this.isJungle)
            this.setIcon(new ImageIcon("JungleEmpty.png"));
        else
            this.setIcon(new ImageIcon("SavannaEmpty.png"));
    }

    void drawAnimal (Animal animal) {
        MapDirection direction = animal.getMapDirection();
        switch(direction.toInt()) {
            case 0:
                this.setIcon(new ImageIcon("Animal0.png"));
                break;
            case 1:
                this.setIcon(new ImageIcon("Animal1.png"));
                break;
            case 2:
                this.setIcon(new ImageIcon("Animal2.png"));
                break;
            case 3:
                this.setIcon(new ImageIcon("Animal3.png"));
                break;
            case 4:
                this.setIcon(new ImageIcon("Animal4.png"));
                break;
            case 5:
                this.setIcon(new ImageIcon("Animal5.png"));
                break;
            case 6:
                this.setIcon(new ImageIcon("Animal6.png"));
                break;
            case 7:
                this.setIcon(new ImageIcon("Animal7.png"));
                break;
        }
    }

    void drawGrass () {
        this.setIcon(new ImageIcon("Grass.png"));
    }
}