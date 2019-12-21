package agh.cs.project1;

public class Vector2d {
    final int x;
    final int y;

    public Vector2d (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString () { return "("+this.x+", "+this.y+")"; }

    boolean precedes(Vector2d other) { return (this.x <= other.x && this.y <= other.y); }

    boolean follows(Vector2d other) { return (this.x >= other.x && this.y >= other.y); }

    public Vector2d upperRight(Vector2d other) {
        int xx, yy;
        xx = this.x; yy = this.y;
        if(xx < other.x) xx = other.x;
        if(yy < other.y) yy = other.y;
        return new Vector2d (xx,yy);
    }

    public Vector2d lowerLeft(Vector2d other) {
        int xx, yy;
        xx = this.x; yy = this.y;
        if(xx > other.x) xx = other.x;
        if(yy > other.y) yy = other.y;
        return new Vector2d (xx,yy);
    }

    Vector2d add(Vector2d other)
    {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other)
    {
        return new Vector2d (this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null || other.getClass() != this.getClass())
            return false;
        Vector2d o = (Vector2d) other;
        return this.x == o.x && this.y == o.y;
    }

    public Vector2d opposite()
    {
        return new Vector2d (-this.x,-this.y);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }
}
