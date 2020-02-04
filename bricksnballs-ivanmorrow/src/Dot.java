import java.awt.*;

public class Dot {

    protected Point center;
    protected int radius;
    protected Color color;

    public Dot(Point center) {
        this.center = center;
        radius = 25;
        color = Color.BLUE;
    }


    void paint(Graphics g){
        g.setColor(color);
        g.fillOval(center.x-radius,center.y-radius, radius*2, radius*2 );
    }

    public Point getCenter() {
        return center;
    }

    public boolean isInside(Point p){
        return p.distance(center)<radius;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

}
