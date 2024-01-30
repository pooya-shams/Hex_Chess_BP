package Chess;

import util.Coordinate;
import util.HexMat;

import java.util.ArrayList;

public class MoveHelper
{
	// helps with bishop and rook moves, so we could reuse their move methods without rewriting them in queen
	// pretty clever, huh?
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
			if(!board.is_valid_coord(c))
				continue;
			ChessPiece piece = board.get(c).getContent();
			if(piece == null || piece.is_white != in.is_white)
				out.add(c);
		}
		return out;
	}
}
