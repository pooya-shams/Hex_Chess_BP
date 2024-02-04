package Chess;

import ir.sharif.math.bp02_1.hex_chess.graphics.Application;
import ir.sharif.math.bp02_1.hex_chess.graphics.models.StringColor;
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
	// TODO show the board inverted when it's the turn of black
	// maybe change the colors? no ? ok
	private final HexMat<BoardCell> board;
	final int n = 6;
	Coordinate selected = null; // if it is null we are in highlighted mode o.w not
	ArrayList<Coordinate> cango = null; // saves the possible points selected can go because I dont want to recalculate it
	boolean is_white = true; // turn
	ArrayList<ChessPiece> removed = new ArrayList<>();
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
		this.board.get(+4, +5).setContent(new Queen(false, new Coordinate(+4, +5)));
		this.board.get(+5, +4).setContent(new King(false, new Coordinate(+5, +4)));
		this.board.get(+3, +5).setContent(new Knight(false, new Coordinate(+3, +5)));
		this.board.get(+5, +3).setContent(new Knight(false, new Coordinate(+5, +3)));
		this.board.get(+2, +5).setContent(new Rook(false, new Coordinate(+2, +5)));
		this.board.get(+5, +2).setContent(new Rook(false, new Coordinate(+5, +2)));
	}

	public void click(Coordinate pos, Application app)
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
				{
					HexMat<BoardCell> nb = this.board.copy(); // haha this is so fun... probably.
					ChessPiece p2 = nb.get(pos).getContent();
					p2.moveTo(c, nb);
					if(!MoveHelper.check_check(nb, is_white))
						this.board.get(c).setHighlighted(true);
				}
			}
		}
		else // selected so 'selected' and 'cango' aren't null (hopefully fingers crossed inshaallah)
		{
			//if(cango.contains(pos)) // I can't use this anymore because I have to check for checks on king
			if(this.board.get(pos).isHighlighted())
			{
				// Adding to removed pieces
				ChessPiece piece = this.board.get(pos).getContent();
				if(piece != null)
					removed.add(piece);
				this.board.get(selected).getContent().moveTo(pos, this.board); // chi shod ke be inja residim
				// handling of the promotion
				ChessPiece moved = this.board.get(pos).getContent(); // now the new one is here
				if(moved instanceof Pawn)
					((Pawn)(moved)).check_and_promote(board);
				// handling of the turn
				this.is_white = !this.is_white;
			}
			for(Coordinate c: cango)
				this.board.get(c).setHighlighted(false);
			selected = null;
			cango = null;
		}
	}

	public void draw(Application app) // draws the whole board
	{
		boolean chck = MoveHelper.check_check(this.board, this.is_white);
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
				{
					if(piece instanceof King && piece.is_white == this.is_white && chck)
						back = Color.RED;
					app.setCellProperties(g.getY(), g.getX(), piece.piece_name, back, (piece.is_white ? Color.WHITE : Color.BLACK));
				}
			}
		}
		String name = (is_white ? "White" : "Black");
		if(!MoveHelper.check_can_move(this.board, is_white))
		{
			String end_message;
			if(chck)
				end_message = name + " is a loser";
			else
				end_message = "it's a draw";
			app.setMessage(end_message);
			app.showMessagePopup(end_message);
		}
		else
		{
			app.setMessage(name + "'s turn");
		}
		app.setRemovedPieces(MoveHelper.get_removed_array(removed));
	}

	public String write()
	{
		return this.toString();
	}
	public void load(File file) throws FileNotFoundException
	{
		Scanner sc = new Scanner(file);
		for(int i = -n+1; i < n; i++)
		{
			int l = board.getLen(i);
			int o = board.getOffset(i);
			for (int j = -o; j < l - o; j++)
			{
				board.set(i, j, new BoardCell(false, new Coordinate(i, j), null));
			}
		}
		selected = null;
		cango = null;
		removed.clear();
		while(sc.hasNextLine())
		{
			String[] f = sc.nextLine().split(" ");
			if(f[0].equals("turn"))
			{
				this.is_white = Boolean.parseBoolean(f[1]);
			}
			else if(f[0].equals("piece"))
			{
				ChessPiece cp = MoveHelper.interpretPiece(f);
				if(cp != null)
					this.board.get(cp.pos).setContent(cp);
			}
			else if(f[0].equals("removed"))
			{
				ChessPiece cp = MoveHelper.interpretPiece(f);
				if(cp != null)
					removed.add(cp);
			}
		}
		selected = null;
		cango = null;
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
		for(ChessPiece cp: removed)
		{
			s.append("removed ");
			s.append(cp);
			s.append("\n");
		}
		return s.toString();
	}
}
