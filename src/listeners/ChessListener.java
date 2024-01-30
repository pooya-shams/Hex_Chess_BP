package listeners;

import Chess.ChessBoard;
import ir.sharif.math.bp02_1.hex_chess.graphics.Application;
import ir.sharif.math.bp02_1.hex_chess.graphics.listeners.EventListener;
import util.Coordinate;
import util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class ChessListener implements EventListener // dummy listener honestly
{
	private ChessBoard board;
	private final Application app;
	public ChessListener(Application app)
	{
		this.board = new ChessBoard();
		this.app = app;
		this.board.draw(app);
	}

	@Override
	public void onClick(int row, char col)
	{
		Coordinate pos = Coordinate.fromGlinski(new Pair<>(col, row));
		board.click(pos);
		board.draw(app);
	}
	@Override
	public void onLoad(File file)
	{
		if(!file.getName().endsWith(".hxc"))
		{
			System.err.println("not a valid filename");
			return;
		}
		try
		{
			ChessBoard nb = new ChessBoard();
			nb.load(file);
			// assuming there is no error
			this.board = nb;
		}
		catch (FileNotFoundException e)
		{
			System.err.println("loading failed");
		}
		board.draw(app);
	}
	@Override
	public void onSave(File file)
	{
		if(!file.getName().endsWith(".hxc"))
		{
			System.err.println("not a valid filename");
			return;
		}
		try
		{
			PrintStream ps = new PrintStream(file);
			ps.print(this.board.write());
		}
		catch (FileNotFoundException e)
		{
			System.err.println("no such file directory");
		}
		board.draw(app);
	}
	@Override
	public void onNewGame()
	{
		this.board = new ChessBoard();
		// TODO: the rest of this
		board.draw(app);
	}
}
