package Chess;

import ir.sharif.math.bp02_1.hex_chess.graphics.Application;
import ir.sharif.math.bp02_1.hex_chess.util.PieceName;
import util.Coordinate;
import util.HexMat;
import util.Pair;

import java.awt.*;
import java.util.ArrayList;

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
		this.board.get(0, 0).setContent(new Pawn(true, new Coordinate(0, 0)));
		this.board.get(0, 1).setContent(new Pawn(false, new Coordinate(0, 1))); // just for testing purposes
		// TODO: fill board properly
		// TODO: also define a standard for a saving file
		// maybe each line representing a coordinate and its content? null or a piecename or not writing empty ones
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
	public void load(String s)
	{
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
			for(String l: s.trim().split("\n"))
			{
				if(l.isEmpty()) continue;
				String[] f = l.split(" ");
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
			System.err.println("loading failed");
		}
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
