package util;

public interface CopyAble<T> // what the fuck am I doing with my life
{
	// T is supposed to be the class that implements this interface but apparently java has no way to force it
	// thanks for nothing
	T copy();
}
