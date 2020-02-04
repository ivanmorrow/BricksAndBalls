import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class LaunchPanel extends JPanel implements VanishListener {

	ArrayList<Balls> ballArray, deadBallArray;
	ArrayList<Obstacle> bricks, deadBricks;
	Dot launchPoint;
	Point origin, tempPoint;
	private JLabel ballsRemaining;
	private int numObstacles;
	private static int balls = 150;


	public LaunchPanel() {
		setPreferredSize(new Dimension(2000,2000));
		ballArray = new ArrayList<Balls>();
		bricks = new ArrayList<Obstacle>();
		origin = new Point(1000,1700);
		numObstacles = 0;
		ballsRemaining = new JLabel(Integer.toString(balls));
		add(ballsRemaining);
		launchPoint =new Dot(origin);
		launchPoint.setColor(Color.GREEN);
		deadBallArray = new ArrayList<Balls>();
		deadBricks = new ArrayList<Obstacle>();
		addMouseListener(new MousePlay());
		addObstacles();
	}

	@Override
	public void update(VanishEvent e) {
		deadBricks.add((Obstacle) e.getSource());
		numObstacles--;
		if(numObstacles == 0) {
			JOptionPane.showMessageDialog(null, "Bricks Broke! Score "+balls);
			System.exit(0);
		}

	}

	private void addObstacles() {

		int x = 200, y = 200;
		int bricksGone = 0;
		int randomArray[] = {0,0,0,0,0,0,0,0,0,0,0,0};
		int index = 0;
		while(bricks.size() != 30)
		{
			Obstacle o = new Obstacle(new Point(x,y));
			o.addVanishListener(this);
			bricks.add(o);
			numObstacles++;
			x = x + 200;
			if(x >= 1800){
				y = y + 200;
				x = 200;
			}
		}
		while(index != 12) {
			randomArray[index] = (int)(Math.random()*12);
			index++;
		}
		for (int i : randomArray){
			bricks.remove(randomArray[i]);
			numObstacles--;
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (Balls d: ballArray) {
			if ((d.top() < 0)){
				d.reflectY();
			}
			if((d.bottom() > getHeight())){
				deadBallArray.add(d);
			}
			if ((d.left() < 0) || d.right() > getWidth()) {
				d.reflectX();
			}
			for(Obstacle o: bricks){
				if (d.getRegion().intersects(o.getRegion())) {
					o.hitBy(d);
				}

			}


			d.move();
			d.paint(g);

		}
		if(!deadBallArray.isEmpty()){
			for(Balls ball: deadBallArray){
				ballArray.remove(ball);
			}
			deadBallArray.clear();
		}
		if(!deadBricks.isEmpty()){
			for(Obstacle ball: deadBricks){
				bricks.remove(ball);
			}
			deadBricks.clear();
		}
		for(Obstacle brick: bricks){
			brick.paint(g);
		}

		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		repaint();
	}


	private class MousePlay implements MouseListener, Runnable{
		@Override
		public void run() {
			generateDot(tempPoint);
		}
		private void generateDot(Point p){
			Balls b = new Balls(origin, p, 5);
			ballArray.add(b);
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			tempPoint = event.getPoint();
			Thread t = new Thread(this);
			t.start();
			balls--;
			ballsRemaining.setText(Integer.toString(balls));
			if(balls == 0){
				JOptionPane.showMessageDialog(null, "Unable to break the bricks!");
				System.exit(0);
			}



		}

		@Override
		public void mousePressed(MouseEvent e) {
			//System.out.println("Invalid Mouse Command - try clicking");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//System.out.println("Invalid Mouse Command - try clicking");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			//System.out.println("Invalid Mouse Command - try clicking");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			//System.out.println("Invalid Mouse Command - try clicking");
		}
	}
}