package uk.co.myleskirby.UniProject;

public class Node {
    private int pos;
    public int shape; // 1 = Circle, 2 = Square, 3 = Dash, 4 - triangle, 5 - cross
    public int canSee[];

    public int[] getCanSee() {
        return canSee;
    }

    public void setCanSee(int[] canSee) {
        this.canSee = canSee;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getShape() {
        return shape;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public Node(int position, int[] canSee) {
        setPos(position);
        setCanSee(canSee);
    }

    public void RandomiseShape(){
        double min =1;
        double max =5;
        double x = (int)(Math.random()*((max-min)+1))+min;
        this.shape = (int)x;
    }
}
