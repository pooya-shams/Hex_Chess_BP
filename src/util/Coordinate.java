package util;

public class Coordinate
{
	private final static String chars = "abcdefghikl";
	public static Pair<Character, Integer> toGlinski(Coordinate a) // assumes a 6 by 6 hex mat of glinski chess
	{
		char ch = chars.charAt(a.x - a.y + 5);
		int i = Math.min(a.x, a.y)+6;
		return new Pair<Character, Integer>(ch, i);
	}
	public static Coordinate fromGlinski(Pair<Character, Integer> a)
	{
		int ch = chars.indexOf(a.getX());
		int i = a.getY() - 1;
		if(ch < 5)
			return new Coordinate(-5 + i, -ch + i);
		else
			return new Coordinate(ch - 10 + i, -5 + i);
	}

	int x, y;
	public Coordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public Coordinate add(Coordinate o)
	{
		return new Coordinate(this.x + o.x, this.y + o.y);
	}
	public Coordinate sub(Coordinate o)
	{
		return new Coordinate(this.x - o.x, this.y - o.y);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Coordinate that = (Coordinate) o;
		return this.x == that.x && this.y == that.y;
	}
	@Override
	public String toString()
	{
		return "("+x+", "+y+")";
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
