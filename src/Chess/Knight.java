package Chess;

import ir.sharif.math.bp02_1.hex_chess.util.PieceName;
import util.Coordinate;
import util.HexMat;

import java.util.ArrayList;

public class Knight extends ChessPiece
{
	protected static final Coordinate[] moves = new Coordinate[]{
		new Coordinate(1, 1),
		new Coordinate(1, 0),
		new Coordinate(0, -1),
		new Coordinate(-1, -1),
		new Coordinate(-1, 0),
		new Coordinate(0, 1),
	};
	// this is the same as rook with a slight twist
	// the vectors are in a circular order, so we can create a knight move
	// by adding two vectors of m[i] and a vector of either m[i-1] or m[i+1]

	public Knight(boolean is_white, Coordinate pos)
	{
		super(is_white, pos);
		if(is_white)
			super.piece_name = PieceName.WHITE_KNIGHT;
		else
			super.piece_name = PieceName.BLACK_KNIGHT;
	}

	@Override
	public ArrayList<Coordinate> get_valid_moves(HexMat<BoardCell> board)
	{
		ArrayList<Coordinate> out = new ArrayList<Coordinate>();
		int l = moves.length;
		for(int i = 0; i < l; i++)
		{
			Coordinate c = this.pos.add(moves[i]).add(moves[i]);
			Coordinate d = c.add(moves[ (i+1)%l ]);
			Coordinate e = c.add(moves[ (i-1+l)%l ]);
			if(this.check_can_move(board, d))
				out.add(d);
			if(this.check_can_move(board, e))
				out.add(e);
		}
		return out;
	}

	@Override
	public Knight copy()
	{
		return new Knight(is_white, pos.copy());
	}
}
