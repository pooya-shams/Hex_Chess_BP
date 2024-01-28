package util;

import java.util.ArrayList;

public class IndexList<T>
{
	private final int n;
	private final ArrayList<T> arr;
	private final int offset;
	// uses offset for indexing so for example if offset is 2 and you do get(-1) it returns this.arr[-1+2]
	IndexList(int n, int offset)
	{
		this.n = n;
		this.offset = offset;
		this.arr = new ArrayList<T>(n);
		for(int i = 0; i < n; i++)
			this.arr.add(null);
	}
	public T get(int idx)
	{
		return this.arr.get(idx+offset);
	}
	public void set(int idx, T v)
	{
		this.arr.set(idx+offset, v);
	}
	public int size()
	{
		return n;
	}
	public int getOffset()
	{
		return offset;
	}
}
