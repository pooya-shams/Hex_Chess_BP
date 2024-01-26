package util;

public interface Matrix<T>
{
	T get(int x, int y);
	void set(int x, int y, T a);
}
