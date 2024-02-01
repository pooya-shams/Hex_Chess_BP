package util;

import ir.sharif.math.bp02_1.hex_chess.graphics.Application;

public class ApplicationHolder // just holds a static variable that is the main application
	// because java is stupid and I don't have good way to share it all the way down to
	// a class like Pawn other than passing it method by method which I'm too lazy to do
{
	public static Application app;
}
