package util;

import java.util.Objects;

public class Pair<T1, T2>
{
	private T1 x;
	private T2 y;

	public Pair(T1 x, T2 y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pair<?, ?> that = (Pair<?, ?>) o;
		return Objects.equals(this.x, that.x) && Objects.equals(this.y, that.y);
	}
	@Override
	public String toString()
	{
		return "<"+x+", "+y+">";
	}

	public T1 getX()
	{
		return x;
	}
	public void setX(T1 x)
	{
		this.x = x;
	}

	public T2 getY()
	{
		return y;
	}
	public void setY(T2 y)
	{
		this.y = y;
	}
}
