package agh.cs.project1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SwingMapVisualizer extends JPanel{
    private List<List<Tile>> tileSet;
    JungleSavannah map;

    SwingMapVisualizer (JungleSavannah importedMap){
        this.map = importedMap;

        setBackground(Color.white);
        setSize(map.mapMax.x*18-1,map.mapMax.y*18-1);
        GridLayout tilesGrid = new GridLayout(map.mapMax.y, map.mapMax.x,1,1);
        setLayout(tilesGrid);

        this.tileSet = new ArrayList<>();
        for(int i=0; i<map.mapMax.y; i++) {
            this.tileSet.add(new ArrayList<>());
            for(int j=0; j<map.mapMax.x; j++) {
                Tile tile;
                boolean isJungle;

                MapEntity entity = map.objectAt(new Vector2d(j,i));

                isJungle = i < map.jungleMax.y && i >= map.jungleMin.y && j < map.jungleMax.x && j >= map.jungleMin.x;
                tile = new Tile(entity,isJungle);
                tile.setBounds(j*15,i*15,15,15);
                add(tile);
                tileSet.get(i).add(tile);
            }
        }
        setVisible(true);
    }

    public void updateTile(Vector2d position) {
        MapEntity entity = this.map.objectAt(position);
        if(entity == null) {
            tileSet.get(position.y).get(position.x).clearTile();
            return;
        }
        if(entity.getClass() == Animal.class) {
            tileSet.get(position.y).get(position.x).drawAnimal((Animal) entity);
            return;
        }
        tileSet.get(position.y).get(position.x).drawGrass();
    }

}
