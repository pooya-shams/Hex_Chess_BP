package Chess;

import ir.sharif.math.bp02_1.hex_chess.util.PieceName;
import util.Coordinate;
import util.HexMat;

import java.util.ArrayList;

public class Bishop extends ChessPiece
{
	protected static final Coordinate[] moves = new Coordinate[]{
		new Coordinate(1, 2),
		new Coordinate(2, 1),
		new Coordinate(-1, 1),
		new Coordinate(1, -1),
		new Coordinate(-1, -2),
		new Coordinate(-2, -1),
	};

	public Bishop(boolean is_white, Coordinate pos)
	{
		super(is_white, pos);
		if(is_white)
			super.piece_name = PieceName.WHITE_BISHOP;
		else
			super.piece_name = PieceName.BLACK_BISHOP;
	}

	@Override
	public ArrayList<Coordinate> get_valid_moves(HexMat<BoardCell> board)
	{
		return MoveHelper.get_valid_moves(board, this, Bishop.moves);
	}
}