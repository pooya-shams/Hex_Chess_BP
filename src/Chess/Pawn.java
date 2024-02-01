package Chess;

import ir.sharif.math.bp02_1.hex_chess.util.PieceName;
import util.ApplicationHolder;
import util.Coordinate;
import util.HexMat;

import java.util.ArrayList;

public class Pawn extends ChessPiece
{
	private static final Coordinate white_move = new Coordinate(1, 1);
	private static final Coordinate black_move = new Coordinate(-1, -1);
	private static final Coordinate[] white_attack = new Coordinate[]{
		new Coordinate(0, 1),
		new Coordinate(1, 0),
	};
	private static final Coordinate[] black_attack = new Coordinate[]{
		new Coordinate(0, -1),
		new Coordinate(-1, 0),
	};

	boolean has_moved;

	public Pawn(boolean is_white, Coordinate pos)
	{
		super(is_white, pos);
		this.has_moved = false;
		if(is_white)
			super.piece_name = PieceName.WHITE_PAWN;
		else
			super.piece_name = PieceName.BLACK_PAWN;
	}
	public void setHas_moved(boolean has_moved)
	{
		this.has_moved = has_moved;
	}

	@Override
	public ArrayList<Coordinate> get_valid_moves(HexMat<BoardCell> board)
	{
		ArrayList<Coordinate> out = new ArrayList<Coordinate>();
		Coordinate move  = (is_white ?  white_move : black_move);
		Coordinate[] attack = (is_white ?  white_attack : black_attack);
		Coordinate tmp = this.pos.add(move);
		if(this.check_can_go(board, tmp))
		{
			out.add(tmp);
			if (!this.has_moved)
			{
				tmp = tmp.add(move);
				if (this.check_can_go(board, tmp))
					out.add(tmp);
			}
		}
		for(Coordinate a: attack)
		{
			Coordinate x = this.pos.add(a);
			if(this.check_can_attack(board, x))
				out.add(x);
		}
		return out;
	}
	@Override
	public void moveTo(Coordinate pos, HexMat<BoardCell> board)
	{
		super.moveTo(pos, board);
		this.has_moved = true;
	}
	public void check_and_promote(HexMat<BoardCell> board)
	{
		int z = 5 * (is_white ? 1 : -1);
		if (this.pos.getX() == z || this.pos.getY() == z)
		{
			String s = ApplicationHolder.app.showPromotionPopup();
			if (s.equals("Queen"))
				board.get(this.pos).setContent(new Queen(this.is_white, this.pos.copy()));
			else if (s.equals("Rook"))
				board.get(this.pos).setContent(new Rook(this.is_white, this.pos.copy()));
			else if (s.equals("Bishop"))
				board.get(this.pos).setContent(new Bishop(this.is_white, this.pos.copy()));
			else if (s.equals("Knight"))
				board.get(this.pos).setContent(new Knight(this.is_white, this.pos.copy()));
			else
				throw new IllegalArgumentException("sadat kharab kardi ke");
		}
	}

	@Override
	public String toString()
	{
		return super.toString() + " " + this.has_moved;
	}

	@Override
	public Pawn copy()
	{
		Pawn out = new Pawn(this.is_white, this.pos.copy());
		out.has_moved = this.has_moved;
		return out;
	}
}
