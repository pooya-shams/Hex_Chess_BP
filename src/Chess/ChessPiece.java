package Chess;

import util.Coordinate;
import util.HexMat;

import java.util.ArrayList;

public abstract class ChessPiece
{
	final boolean is_white; // color of the piece
	Coordinate pos;
	String piece_name;
	public ChessPiece(boolean is_white, Coordinate pos)
	{
		this.is_white = is_white;
		this.pos = pos;
	}
	public abstract ArrayList<Coordinate> get_valid_moves(HexMat<BoardCell> board); // returns all the positions this piece can go to
	public void moveTo(Coordinate pos, HexMat<BoardCell> board) // moves to the selected cell
	{
		board.get(this.pos).setContent(null);
		this.pos = pos;
		board.get(this.pos).setContent(this);
	}

	@Override
	public String toString()
	{
		return this.piece_name;
	}
}
