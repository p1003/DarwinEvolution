package agh.cs.project1;

import java.util.*;

class Plantdom {
    HashMap<Vector2d,Plant> plantsHash;
    private JungleSavannah map;

    Plantdom(JungleSavannah map, int n) {
        plantsHash = new HashMap<>();
        this.map = map;
        growPlants(map.mapMax,map.mapMin,n);
    }

    private void growPlants(Vector2d mapMax, Vector2d mapMin, int n) {
        int n1 = (mapMax.x - mapMin.x) * (mapMax.y - mapMin.y);
        if(n > n1)
            n = n1;

        for(int i=0; i<n; i++) {
            randomGrow(mapMax, mapMin, 1);
        }
    }

    void dailyGrow(){
        for(int i=0; i<map.dailyJungleGrow; i++)
            randomGrow(map.jungleMax, map.jungleMin, 1); //grow in jungle
        for(int i=0; i<map.dailySavannahGrow; i++)
            randomGrow(map.mapMax, map.mapMin, map.jungleMax, map.jungleMin, 1); //grow in savannah (grow outside jungle)
    }

    private void randomGrow(Vector2d max, Vector2d min, int count) {
        Random g = new Random();
        int x = g.nextInt(max.x - min.x) + min.x;
        int y = g.nextInt(max.y - min.y) + min.y;
        Vector2d tmp = new Vector2d (x,y);
        if(canGrow(tmp) || count*count > (max.x - min.x)*(max.y - min.y)/count ) return;
        randomGrow(max,min, count + 1);
    }
    private void randomGrow(Vector2d max, Vector2d min, Vector2d maxOut, Vector2d minOut, int count) {
        Random g = new Random();
        int x = g.nextInt(max.x - min.x) + min.x;
        int y = g.nextInt(max.y - min.y) + min.y;
        Vector2d tmp = new Vector2d (x,y);
        if(!(tmp.precedes(maxOut) && tmp.follows(minOut)) && (canGrow(tmp) || count*count > (max.x - min.x)*(max.y - min.y)/count )) return;
        if(!max.equals(maxOut) && !min.equals((minOut)))
            randomGrow(max,min,maxOut,minOut,count + 1);
    }
    private boolean canGrow(Vector2d tmp) {
        if(map.objectAt(tmp) == null) {
            Plant plant = new Plant(tmp,this.map.plantEnergy);
            this.plantsHash.put(tmp,plant);
            this.map.eventNoticed(tmp);
            this.map.numberOfPlants++;
            return true;
        }
        else return false;
    }

}
