package Chess;

import ir.sharif.math.bp02_1.hex_chess.util.PieceName;
import util.Coordinate;
import util.HexMat;

import java.util.ArrayList;

public class Rook extends ChessPiece
{
	protected static final Coordinate[] moves = new Coordinate[]{
		new Coordinate(0, 1),
		new Coordinate(1, 0),
		new Coordinate(1, 1),
		new Coordinate(0, -1),
		new Coordinate(-1, 0),
		new Coordinate(-1, -1),
	};

	public Rook(boolean is_white, Coordinate pos)
	{
		super(is_white, pos);
		if(is_white)
			super.piece_name = PieceName.WHITE_ROOK;
		else
			super.piece_name = PieceName.BLACK_ROOK;
	}

	@Override
	public ArrayList<Coordinate> get_valid_moves(HexMat<BoardCell> board)
	{
		return MoveHelper.get_valid_moves(board, this, Rook.moves);
	}
}
