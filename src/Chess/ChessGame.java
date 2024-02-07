package Chess;

import Chess.ChessBoard;
import ir.sharif.math.bp02_1.hex_chess.graphics.Application;
import ir.sharif.math.bp02_1.hex_chess.graphics.listeners.EventListener;
import util.Coordinate;
import util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ChessGame
{
	private ChessBoard board;
	private final Application app;
	private final static File save_file = new File("config/current.hxc");
	private final ArrayList<ChessBoard> buffer = new ArrayList<>(); // undo buffer/stack. will be used in undo
	public ChessGame(Application app)
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
		buffer.add(this.board.copy()); // Now that I think about it, I *could* just add the normal board without copying but I'm too scared to mess with that
		this.board = nb;
	}
	public void click(int row, char col)
	{
		Coordinate pos = Coordinate.fromGlinski(new Pair<>(col, row));
		board.click(pos, app);
		// TODO handle undo in this state
		save_to(save_file);
		if(board.draw(app))
		{
			// resetting like an idiot
			reset_save_file();
		}
	}
	public void load(File file)
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
	public void save(File file)
	{
		save_to(file);
		board.draw(app);
	}
	public void newGame()
	{
		this.board = new ChessBoard();
		save_to(save_file);
		board.draw(app);
	}
	public void undo()
	{
		if(buffer.isEmpty())
			return;
		this.board = buffer.get(buffer.size() - 1);
		buffer.remove(buffer.size() - 1);
	}
}
