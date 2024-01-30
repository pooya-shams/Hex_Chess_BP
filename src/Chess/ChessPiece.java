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

	protected boolean check_can_go(HexMat<BoardCell> board, Coordinate dest) // checks if dest is valid and empty
	{
		if(!board.is_valid_coord(dest)) return false;
		// assuming we don't get null
		return board.get(dest).getContent() == null;
	}
	protected boolean check_can_attack(HexMat<BoardCell> board, Coordinate dest) // checks if dest can be attacked
	{
		if(!board.is_valid_coord(dest)) return false;
		// assuming we don't get null
		ChessPiece p = board.get(dest).getContent();
		return p != null && this.is_white != p.is_white;
	}
	protected boolean check_can_move(HexMat<BoardCell> board, Coordinate dest)
	{
		return this.check_can_go(board, dest) || this.check_can_attack(board, dest);
	}
	public void moveTo(Coordinate pos, HexMat<BoardCell> board) // moves to the selected cell
	{
		board.get(this.pos).setContent(null);
		this.pos = pos;
		board.get(this.pos).setContent(this);
	}

	@Override
	public String toString()
	{
		return this.piece_name + " " + this.is_white + " " + this.pos.getX() + " " + this.pos.getY();
	}
}
