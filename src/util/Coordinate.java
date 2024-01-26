package util;

public class Coordinate
{
	static char[] chars = "abcdefghikl".toCharArray();
	static Pair<Character, Integer> convert(Coordinate a)
	{
		return new Pair<>(chars[a.x], a.y);
	}

	int x, y;
	public Coordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return x;
	}
	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}
	public void setY(int y)
	{
		this.y = y;
	}
}
