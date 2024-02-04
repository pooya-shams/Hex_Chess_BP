package Chess;

import ir.sharif.math.bp02_1.hex_chess.graphics.models.StringColor;
import ir.sharif.math.bp02_1.hex_chess.util.PieceName;
import util.Coordinate;
import util.HexMat;

import java.awt.*;
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
	public static boolean check_can_move(HexMat<BoardCell> board, boolean is_white) // this is gonna run extremely slow
	{
		for(int i = -n+1; i < n; i++)
		{
			int l = board.getLen(i);
			int o = board.getOffset(i);
			for (int j = -o; j < l - o; j++)
			{
				ChessPiece p = board.get(i, j).getContent();
				if (p != null && p.is_white == is_white)
				{
					for(Coordinate c: p.get_valid_moves(board))
					{
						HexMat<BoardCell> nb = board.copy();
						nb.get(i, j).getContent().moveTo(c, nb);
						if(!check_check(nb, is_white))
							return true;
					}
				}
			}
		}
		return false;
	}
	public static boolean check_mate(HexMat<BoardCell> board, boolean is_white) // checks if it is check mate
	{
		return check_check(board, is_white) && (!check_can_move(board, is_white)) ;
	}
	public static boolean check_pot(HexMat<BoardCell> board, boolean is_white) // checks if it is check mate
	{
		return (!check_check(board, is_white)) && (!check_can_move(board, is_white)) ;
	}
	public static StringColor[] get_removed_array(ArrayList<ChessPiece> rm)
	{
		StringColor[] out = new StringColor[rm.size()];
		for(int i = 0; i < rm.size(); i++)
		{
			ChessPiece cp = rm.get(i);
			out[i] = new StringColor(cp.piece_name, (cp.is_white ? Color.WHITE : Color.BLACK));
		}
		return out;
	}
	public static ChessPiece interpretPiece(String[] f)
	{
		// holy hell this is awful how can I automate this please help
		int x = Integer.parseInt(f[3]);
		int y = Integer.parseInt(f[4]);
		boolean iw = Boolean.parseBoolean(f[2]);
		if (f[1].equals(PieceName.BLACK_KING) || f[1].equals(PieceName.WHITE_KING))
		{
			return new King(iw, new Coordinate(x, y));
		}
		else if (f[1].equals(PieceName.BLACK_BISHOP) || f[1].equals(PieceName.WHITE_BISHOP))
		{
			return new Bishop(iw, new Coordinate(x, y));
		}
		else if (f[1].equals(PieceName.BLACK_ROOK) || f[1].equals(PieceName.WHITE_ROOK))
		{
			return new Rook(iw, new Coordinate(x, y));
		}
		else if (f[1].equals(PieceName.BLACK_KNIGHT) || f[1].equals(PieceName.WHITE_KNIGHT))
		{
			return new Knight(iw, new Coordinate(x, y));
		}
		else if (f[1].equals(PieceName.BLACK_QUEEN) || f[1].equals(PieceName.WHITE_QUEEN))
		{
			return new Queen(iw, new Coordinate(x, y));
		}
		else if (f[1].equals(PieceName.BLACK_PAWN) || f[1].equals(PieceName.WHITE_PAWN))
		{
			Pawn pawn = new Pawn(iw, new Coordinate(x, y));
			pawn.setHas_moved(Boolean.parseBoolean(f[5]));
			return pawn;
		}
		return null;
	}
}
