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

	@Override
	public String toString()
	{
		return String.valueOf(this.board);
	}
}