package Chess;

import ir.sharif.math.bp02_1.hex_chess.graphics.Application;
import ir.sharif.math.bp02_1.hex_chess.util.PieceName;
import util.Coordinate;
import util.HexMat;
import util.Pair;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChessBoard
{
	private final HexMat<BoardCell> board;
	final int n = 6;
	Coordinate selected = null; // if it is null we are in highlighted mode o.w not
	ArrayList<Coordinate> cango = null; // saves the possible points selected can go because I dont want to recalculate it
	boolean is_white = true; // turn
	public ChessBoard()
	{
		board = new HexMat<BoardCell>(n);
		for(int i = -n+1; i < n; i++)
		{
			int l = board.getLen(i);
			int o = board.getOffset(i);
			for(int j = -o; j < l-o; j++)
			{
				board.set(i, j, new BoardCell(false, new Coordinate(i, j), null));
			}
		}
		try
		{
			load(new File("config/default.hxc"));
		}
		catch (FileNotFoundException e)
		{
			System.err.println("couldn't find default file. loading manually");
			set_default_config_manually();
		}
	}

	public void set_default_config_manually()
	{
		for(int i = -n+1; i < n; i++)
		{
			int l = board.getLen(i);
			int o = board.getOffset(i);
			for(int j = -o; j < l-o; j++)
			{
				board.set(i, j, new BoardCell(false, new Coordinate(i, j), null));
			}
		}
		for(int i = 1; i <= 5; i++)
		{
			this.board.get(-1, -i).setContent(new Pawn(true, new Coordinate(-1, -i)));
			if(i != 1)
				this.board.get(-i, -1).setContent(new Pawn(true, new Coordinate(-i, -1)));
		}
		for(int i = 3; i <= 5; i++)
			this.board.get(-i, -i).setContent(new Bishop(true, new Coordinate(-i, -i)));
		this.board.get(-5, -4).setContent(new Queen(true, new Coordinate(-5, -4)));
		this.board.get(-4, -5).setContent(new King(true, new Coordinate(-4, -5)));
		this.board.get(-3, -5).setContent(new Knight(true, new Coordinate(-3, -5)));
		this.board.get(-5, -3).setContent(new Knight(true, new Coordinate(-5, -3)));
		this.board.get(-2, -5).setContent(new Rook(true, new Coordinate(-2, -5)));
		this.board.get(-5, -2).setContent(new Rook(true, new Coordinate(-5, -2)));
		// now black
		for(int i = 1; i <= 5; i++)
		{
			this.board.get(+1, +i).setContent(new Pawn(false, new Coordinate(+1, +i)));
			if(i != 1)
				this.board.get(+i, +1).setContent(new Pawn(false, new Coordinate(+i, +1)));
		}
		for(int i = 3; i <= 5; i++)
			this.board.get(+i, +i).setContent(new Bishop(false, new Coordinate(+i, +i)));
		this.board.get(+5, +4).setContent(new Queen(false, new Coordinate(+5, +4)));
		this.board.get(+4, +5).setContent(new King(false, new Coordinate(+4, +5)));
		this.board.get(+3, +5).setContent(new Knight(false, new Coordinate(+3, +5)));
		this.board.get(+5, +3).setContent(new Knight(false, new Coordinate(+5, +3)));
		this.board.get(+2, +5).setContent(new Rook(false, new Coordinate(+2, +5)));
		this.board.get(+5, +2).setContent(new Rook(false, new Coordinate(+5, +2)));
	}

	public void click(Coordinate pos)
	{
		// two major modes: selected or not
		if(selected == null) // not selected
		{
			ChessPiece piece = this.board.get(pos).getContent();
			if(piece != null && piece.is_white == this.is_white)
			{
				selected = pos;
				cango = piece.get_valid_moves(this.board);
				for(Coordinate c: cango)
					this.board.get(c).setHighlighted(true);
			}
		}
		else // selected so 'selected' and 'cango' aren't null (hopefully fingers crossed inshaallah)
		{
			if(cango.contains(pos))
			{
				// TODO add to removed pieces and handle them
				this.board.get(selected).getContent().moveTo(pos, this.board); // chi shod ke be inja residim
				this.is_white = !this.is_white;
			}
			for(Coordinate c: cango)
				this.board.get(c).setHighlighted(false);
			selected = null;
			cango = null;
		}
	}
	// TODO this three methods
	public boolean check_check()
	{
		return true;
	}
	public boolean check_mate()
	{
		return true;
	}
	public boolean check_pot()
	{
		return true;
	}

	public void draw(Application app) // draws the whole board
	{
		for(int i = -n+1; i < n; i++)
		{
			int l = this.board.getLen(i);
			int o = this.board.getOffset(i);
			for(int j = -o; j < l-o; j++)
			{
				//board.set(i, j, new BoardCell(false, new Coordinate(i, j), null));
				BoardCell cell = this.board.get(i, j);
				ChessPiece piece = cell.getContent();
				Pair<Character, Integer> g = Coordinate.toGlinski(new Coordinate(i, j));
				Color back = (cell.isHighlighted() ? Color.CYAN : (cell.getPosition().equals(selected) ? Color.YELLOW : null) );
				if(piece == null)
					app.setCellProperties(g.getY(), g.getX(), "", back, null);
				else
					app.setCellProperties(g.getY(), g.getX(), piece.piece_name, back, (piece.is_white ? Color.WHITE : Color.BLACK));
			}
		}
	}

	public String write()
	{
		return this.toString();
	}
	public boolean load(File file) throws FileNotFoundException
	{
		Scanner sc = new Scanner(file);
		try // assuming any error is caused by an invalid file
		{
			for(int i = -n+1; i < n; i++)
			{
				int l = board.getLen(i);
				int o = board.getOffset(i);
				for (int j = -o; j < l - o; j++)
				{
					board.set(i, j, new BoardCell(false, new Coordinate(i, j), null));
				}
			}
			while(sc.hasNextLine())
			{
				String[] f = sc.nextLine().split(" ");
				if(f[0].equals("turn"))
				{
					this.is_white = Boolean.parseBoolean(f[1]);
				}
				else if(f[0].equals("piece"))
				{
					// holy hell this is awful how can I automate this please help
					int x = Integer.parseInt(f[3]);
					int y = Integer.parseInt(f[4]);
					boolean iw = Boolean.parseBoolean(f[2]);
					if (f[1].equals(PieceName.BLACK_KING) || f[1].equals(PieceName.WHITE_KING))
					{
						this.board.get(x, y).setContent(new King(iw, new Coordinate(x, y)));
					}
					else if(f[1].equals(PieceName.BLACK_BISHOP) || f[1].equals(PieceName.WHITE_BISHOP))
					{
						this.board.get(x, y).setContent(new Bishop(iw, new Coordinate(x, y)));
					}
					else if(f[1].equals(PieceName.BLACK_ROOK) || f[1].equals(PieceName.WHITE_ROOK))
					{
						this.board.get(x, y).setContent(new Rook(iw, new Coordinate(x, y)));
					}
					else if(f[1].equals(PieceName.BLACK_KNIGHT) || f[1].equals(PieceName.WHITE_KNIGHT))
					{
						this.board.get(x, y).setContent(new Knight(iw, new Coordinate(x, y)));
					}
					else if(f[1].equals(PieceName.BLACK_QUEEN) || f[1].equals(PieceName.WHITE_QUEEN))
					{
						this.board.get(x, y).setContent(new Queen(iw, new Coordinate(x, y)));
					}
					else if(f[1].equals(PieceName.BLACK_PAWN) || f[1].equals(PieceName.WHITE_PAWN))
					{
						Pawn pawn = new Pawn(iw, new Coordinate(x, y));
						pawn.setHas_moved(Boolean.parseBoolean(f[5]));
						this.board.get(x, y).setContent(pawn);
					}
					else
					{
						throw new Exception("bad line");
					}
				}
			}
			selected = null;
			cango = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		// systematic string creation to be used in save files
		// first line is the turn in boolean form and then it's the pieces
		StringBuilder s = new StringBuilder("turn " + is_white+"\n");
		for(int i = -n+1; i < n; i++)
		{
			int l = this.board.getLen(i);
			int o = this.board.getOffset(i);
			for (int j = -o; j < l - o; j++)
			{
				ChessPiece p = this.board.get(i, j).getContent();
				if(p != null)
				{
					s.append("piece ");
					s.append(p);
					s.append("\n");
				}
			}
		}
		return s.toString();
	}
}
