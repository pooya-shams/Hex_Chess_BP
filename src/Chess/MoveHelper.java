package Chess;

import util.Coordinate;
import util.HexMat;

import java.util.ArrayList;

public class MoveHelper
{
	// helps with bishop and rook moves, so we could reuse their move methods without rewriting them in queen
	// pretty clever, huh?
	private static final int n = 6;
	public static ArrayList<Coordinate> get_valid_moves(HexMat<BoardCell> board, ChessPiece in, Coordinate[] moves)
	{
		// gets moves as basic vectors of direction for moving
		ArrayList<Coordinate> out = new ArrayList<Coordinate>();
		for(Coordinate d: moves)
		{
			for(Coordinate c = in.pos.add(d); board.is_valid_coord(c); c = c.add(d))
			{
				ChessPiece piece = board.get(c).getContent();
				if(piece != null)
				{
					if(piece.is_white != in.is_white)
						out.add(c);
					break;
				}
				out.add(c);
			}
		}
		return out;
	}
	public static ArrayList<Coordinate> get_immediate_moves(HexMat<BoardCell> board, ChessPiece in, Coordinate[] moves)
	{
		// like get_valid_moves but just one level, used for king
		ArrayList<Coordinate> out = new ArrayList<Coordinate>();
		for(Coordinate d: moves)
		{
			Coordinate c = in.pos.add(d);
			if(in.check_can_move(board, c))
				out.add(c);
		}
		return out;
	}
	private static King get_king(HexMat<BoardCell> board, boolean iw)
	{
		King out = null;
		for(int i = -n+1; i < n; i++)
		{
			int l = board.getLen(i);
			int o = board.getOffset(i);
			for(int j = -o; j < l-o; j++)
			{
				ChessPiece p = board.get(i, j).getContent();
				if(p instanceof King && p.is_white == iw)
					out = (King)p;
			}
		}
		if(out == null)
			throw new IllegalArgumentException("No King of desired color");
		return out;
	}
	public static boolean check_check(HexMat<BoardCell> board, boolean is_white) // gets board and king and returns true if king is in check
	{
		King king = get_king(board, is_white);
		for(int i = -n+1; i < n; i++)
		{
			int l = board.getLen(i);
			int o = board.getOffset(i);
			for(int j = -o; j < l-o; j++)
			{
				ChessPiece p = board.get(i, j).getContent();
				if(p != null && p.is_white != king.is_white)
				{
					if(p.get_valid_moves(board).contains(king.pos))
						return true;
				}
			}
		}
		return false;
	}
}
