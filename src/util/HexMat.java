package util;

import java.util.ArrayList;

public class HexMat<T> implements Matrix<T> // a hexagonal matrix
{
	final private int n; // length of the hex matrix
	final private int m; // length of the board
	final private ArrayList<ArrayList<T>> board; // emulates hexagonal behaviour
	// also side note: why is java so stupid and can't create array of generic type?

	public HexMat(int n)
	{
		this.n = n;
		this.m = 2*n-1;
		this.board = new ArrayList<ArrayList<T>>(m); // adding capacity for a little bit of speed but not really anything important
		//System.err.println(this.board.size());
		for(int i = 0; i < m; i++)
		{
			//System.err.printf("len of %d is %d\n", i, getLen(i));
			int l = getLen(i);
			ArrayList<T> mf = new ArrayList<T>(l);
			for(int j = 0; j < l; j++)
				mf.add(null);
			this.board.add(mf);
		}
	}

	public int size()
	{
		return n;
	}

	public int getLen(int x) // return length of the xth file
	{
		return n + Math.min(x, m-1-x);
	}

	public boolean is_valid_coord(int x, int y)
	{
		if(x < 0 || x >= m)
			return false;
		int l = getLen(x);
		return (0 <= y && y < l);
	}

	private void check_arguments(int x, int y)
	{
		if(!is_valid_coord(x, y))
			throw new ArrayIndexOutOfBoundsException(String.format("Arguments x,y: '%d, %d' are out bounds for length [%d](%d)", x, y, m, getLen(x)));
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
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < m; i++)
		{
			for(int j = 0; j < getLen(i); j++)
			{
				s.append(this.board.get(i).get(j));
				s.append(" ");
			}
			s.append("\n");
		}
		return s.toString();
	}
}
