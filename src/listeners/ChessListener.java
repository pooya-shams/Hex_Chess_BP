package listeners;

import Chess.ChessBoard;
import ir.sharif.math.bp02_1.hex_chess.graphics.Application;
import ir.sharif.math.bp02_1.hex_chess.graphics.listeners.EventListener;
import util.Coordinate;
import util.Pair;

import java.io.File;

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
		// TODO
	}
	@Override
	public void onSave(File file)
	{
		// TODO
	}
	@Override
	public void onNewGame()
	{
		this.board = new ChessBoard();
		// TODO: the rest of this
	}
}
