package Chess;

import ir.sharif.math.bp02_1.hex_chess.util.PieceName;
import util.Coordinate;
import util.HexMat;

import java.util.ArrayList;

public class King extends ChessPiece
{
	public King(boolean is_white, Coordinate pos)
	{
		super(is_white, pos);
		if(is_white)
			super.piece_name = PieceName.WHITE_KING;
		else
			super.piece_name = PieceName.BLACK_KING;
	}

	@Override
	public ArrayList<Coordinate> get_valid_moves(HexMat<BoardCell> board)
	{
		ArrayList<Coordinate> out = MoveHelper.get_immediate_moves(board, this, Bishop.moves);
		out.addAll(MoveHelper.get_immediate_moves(board, this, Rook.moves));
		return out;
	}
}
