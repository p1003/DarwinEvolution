package agh.cs.project1;

public enum MapDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NE,
    NW,
    SE,
    SW;
    public String toString() {
        switch(this) {

            case SOUTH:
                return "S";
            case WEST:
                return "W";
            case EAST:
                return "E";
            case NE:
                return "NE";
            case NW:
                return "NW";
            case SE:
                return "SE";
            case SW:
                return "SW";
            default:
                return "N";
        }
    }
    public int toInt() {
        switch(this) {

            case NE:
                return 1;
            case EAST:
                return 2;
            case SE:
                return 3;
            case SOUTH:
                return 4;
            case SW:
                return 5;
            case WEST:
                return 6;
            case NW:
                return 7;
            default:
                return 0;
        }
    }
    public MapDirection toMapDirection(int move) {
        switch (move) {

            case 1:
                return NE;
            case 2:
                return EAST;
            case 3:
                return SE;
            case 4:
                return SOUTH;
            case 5:
                return SW;
            case 6:
                return WEST;
            case 7:
                return NW;
            default:
                return NORTH;
        }
    }
    public Vector2d toUnitVector () {
        switch(this) {

            case NORTH:
                return new Vector2d(0,1);
            case NE:
                return new Vector2d(1,1);
            case EAST:
                return new Vector2d(1,0);
            case SE:
                return new Vector2d(1,-1);
            case SOUTH:
                return new Vector2d(0,-1);
            case SW:
                return new Vector2d(-1,-1);
            case WEST:
                return new Vector2d(-1,0);
            case NW:
                return new Vector2d(-1,1);
            default:
                return new Vector2d(0,0);
        }
    }
}
