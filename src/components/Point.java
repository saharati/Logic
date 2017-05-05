package components;

public class Point {
	private int x;
	private int y;
	public Point(int xValue, int yValue){
		x = xValue;
		y = yValue;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int newX){
		x = newX;
	}
	public void setY(int newY){
		y = newY;
	}
}
