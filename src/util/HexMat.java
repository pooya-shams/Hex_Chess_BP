package util;

import java.util.ArrayList;

public class HexMat<T> implements Matrix<T> // a hexagonal matrix
{
	final private int n; // length of the hex matrix
	final private int m; // length of the board
	final private IndexList<IndexList<T>> board; // emulates hexagonal behaviour
	// like glinski, we use two numbers for representing a position in a hexagonal plane
	// but unlike that these numbers represent coefficients of two vectors
	// these two vectors are like \ and / in the hexagonal plane with a 30-degree angle with the x-axis
	// first number is indexed from [-n+1 to n)
	// second one is indexed a weird way which can be represented using an offset (getOffset function)
	// a better explanation is available at https://www.redblobgames.com/grids/hexagons/#coordinates-axial
	// with a slight change that our vectors are +q and +s
	// also side note: why is java so stupid and can't create array of generic type?

	public HexMat(int n)
	{
		this.n = n;
		this.m = 2*n-1;
		this.board = new IndexList<IndexList<T>>(m, n-1); // adding capacity for a little bit of speed but not really anything important
		for(int i = -n+1; i < n; i++)
		{
			this.board.set(i, new IndexList<T>( getLen(i), getOffset(i) ));
			//System.err.println(i + " " + getLen(i) + " " + getOffset(i));
		}
	}

	public int size()
	{
		return n;
	}

	public int getLen(int x)
	{
		return m - Math.abs(x);
	}
	public int getOffset(int x)
	{
		return n-1 - Math.max(0, x);
	}

	public boolean is_valid_coord(int x, int y)
	{
		if(x <= -n || x >= n)
			return false;
		int l = getLen(x);
		int o = getOffset(x);
		return (-o <= y && y < l-o);
	}

	private void check_arguments(int x, int y)
	{
		if(!is_valid_coord(x, y))
			throw new ArrayIndexOutOfBoundsException(String.format("Arguments x,y: '%d, %d' are out bounds for length %d", x, y, m));
	}

	@Override
	public T get(int x, int y)
	{
		check_arguments(x, y);
		return this.board.get(x).get(y);
	}
	@Override
	public void set(int x, int y, T a)
	{
		check_arguments(x, y);
		this.board.get(x).set(y, a);
	}

	@Override
	public String toString() // not a really beautiful or straight up representation of hex matrix but just to include all components in printable string
	{
		StringBuilder s = new StringBuilder();
		for(int i = -n+1; i < n; i++)
		{
			int l = getLen(i);
			int o = getOffset(i);
			for(int j = -o; j < l-o; j++)
			{
				s.append(this.board.get(i).get(j));
				s.append(" ");
			}
			s.append("\n");
		}
		return s.toString();
	}
}
