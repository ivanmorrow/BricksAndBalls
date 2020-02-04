import java.awt.*;
import java.util.ArrayList;

public class Obstacle {

    private Point center;
    private Rectangle region;
    private int size;
    private Color color;
    private int hitPoints;
    private ArrayList<VanishListener> onScreenListener;

    public Obstacle(Point center) {
        this.center = center;
        size = 50;
        region = new Rectangle(center.x-size/2,center.y-size/2, size, size);
        new Color((int)(Math.random() * 0x1000000));
        hitPoints = 10;
        onScreenListener = new ArrayList<VanishListener>();
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.fillRect(region.x,region.y, region.width,region.height);
        g.setColor(Color.black);
        g.drawString(Integer.toString(this.hitPoints), center.x, center.y);
    }

    public void addVanishListener(VanishListener v) {
        onScreenListener.add(v);
    }
    public void removeVanishListener(VanishListener v) {
        onScreenListener.remove(v);
    }
    public Rectangle getRegion() {
        return region;
    }

    public void hitBy(Balls d) {
        if ((d.top() > top()) && (d.bottom() < bottom())) {
            d.reflectX();
        } else {
            if ((d.left() > left()) && (d.right() < right())) {
                d.reflectY();
            }
            else{
                d.reflectX();
                d.reflectY();
            }
        }
        hitPoints--;
        if(hitPoints == 0) {
            for (VanishListener v : onScreenListener) {
                v.update(new VanishEvent(this));
            }
        }
    }

    public int top(){
        return region.y;
    }
    public int bottom(){
        return region.y+region.height;
    }
    public int left(){
        return region.x;
    }
    public int right(){
        return region.x +region.width;
    }
}
