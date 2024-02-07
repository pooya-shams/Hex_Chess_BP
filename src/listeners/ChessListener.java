package listeners;

import Chess.ChessBoard;
import Chess.ChessGame;
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
	private final ChessGame game;

	public ChessListener(Application application)
	{
		this.game = new ChessGame(application);
	}

	@Override
	public void onClick(int row, char col)
	{
		this.game.click(row, col);
	}

	@Override
	public void onLoad(File file)
	{
		this.game.load(file);
	}

	@Override
	public void onSave(File file)
	{
		this.game.save(file);
	}

	@Override
	public void onNewGame()
	{
		this.game.newGame();
	}

	@Override
	public void undo()
	{
		this.game.undo();
	}
}
