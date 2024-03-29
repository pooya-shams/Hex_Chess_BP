package Chess;

import util.Coordinate;
import util.CopyAble;

public class BoardCell implements CopyAble<BoardCell>
{
	private boolean highlighted; // now acts as indicator of totally valid places to go
	private final Coordinate position;
	private ChessPiece content; // can be null

	public BoardCell(boolean highlighted, Coordinate position, ChessPiece content)
	{
		this.highlighted = highlighted;
		this.position = position;
		this.content = content;
	}

	public Coordinate getPosition()
	{
		return position;
	}
	public ChessPiece getContent()
	{
		return content;
	}
	public void setContent(ChessPiece content)
	{
		this.content = content;
	}
	public boolean isHighlighted()
	{
		return highlighted;
	}
	public void setHighlighted(boolean highlighted)
	{
		this.highlighted = highlighted;
	}

	@Override
	public String toString()
	{
		return "{"+highlighted+", "+this.position+", "+this.content+"}";
	}

	@Override
	public BoardCell copy()
	{
		Coordinate c = (position == null ? null : position.copy());
		ChessPiece p = (content == null ? null : content.copy());
		return new BoardCell(highlighted, c, p);
	}
}
