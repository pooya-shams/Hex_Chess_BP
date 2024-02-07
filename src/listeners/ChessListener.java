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
	private final static File save_file = new File("config/current.hxc");
	public ChessListener(Application app)
	{
		try
		{
			load_from(save_file);
		}
		catch (FileNotFoundException e)
		{
			this.board = new ChessBoard();
		}
		this.app = app;
		if(this.board.draw(app))
			reset_save_file();
	}

	private void reset_save_file()
	{
		ChessBoard ncb = new ChessBoard();
		save_board_to_file(save_file, ncb);
	}
	private void save_board_to_file(File file, ChessBoard cb)
	{
		if(file.getName().contains(".") && (!file.getName().endsWith(".hxc")) )
		{
			System.err.println("not a valid filename");
			return;
		}
		else if(!file.getName().contains("."))
		{
			file = new File(file.getAbsolutePath()+".hxc");
		}
		System.err.println(file.getAbsolutePath());
		try
		{
			PrintStream ps = new PrintStream(file);
			ps.print(cb.write());
		}
		catch (FileNotFoundException e)
		{
			System.err.println("no such file directory");
		}
	}
	private void save_to(File file)
	{
		save_board_to_file(file, this.board);
	}
	private void load_from(File file) throws FileNotFoundException
	{
		if(!file.getName().endsWith(".hxc"))
		{
			System.err.println("not a valid filename");
			return;
		}
		ChessBoard nb = new ChessBoard();
		nb.load(file);
		// assuming there is no error
		this.board = nb;
	}
	@Override
	public void onClick(int row, char col)
	{
		Coordinate pos = Coordinate.fromGlinski(new Pair<>(col, row));
		board.click(pos, app);
		save_to(save_file);
		if(board.draw(app))
		{
			// resetting like an idiot
			reset_save_file();
		}
	}
	@Override
	public void onLoad(File file)
	{
		try
		{
			load_from(file);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("loading failed");
		}
		save_to(save_file);
		board.draw(app);
	}
	@Override
	public void onSave(File file)
	{
		save_to(file);
		board.draw(app);
	}
	@Override
	public void onNewGame()
	{
		this.board = new ChessBoard();
		save_to(save_file);
		board.draw(app);
	}

	@Override
	public void undo()
	{
		// TODO
	}
}
