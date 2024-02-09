package fileIO;

import config.HexConfig;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadConfig // static stuff
{
	public static void load(File file)
	{
		Scanner sc;
		try
		{
			sc = new Scanner(file);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("can't find config file " + file);
			System.err.println("using defaults now");
			return;
		}
		while(sc.hasNextLine())
		{
			String l = sc.nextLine();
			try
			{
				String[] f = l.split(" ");
				if(f[0].equals("bpc")) // board panel color
				{
					HexConfig.board_panel_color = Color.decode(f[1]);
				}
				else if(f[0].equals("rppc")) // removed pieces panel color
				{
					HexConfig.removed_pieces_panel_color = Color.decode(f[1]);
				}
				else if(f[0].equals("mpc")) // main panel color
				{
					HexConfig.main_panel_color = Color.decode(f[1]);
				}
				else if(f[0].equals("bbc")) // big black color
				{
					HexConfig.board_back_color = Color.decode(f[1]);
				}
				else if(f[0].equals("bc")) // board colorS
				{
					for(int i = 0; i < 3; i++)
						HexConfig.board_colors[i] = Color.decode(f[i+1]);
				}
				else if(f[0].equals("rot")) // rotate_board
				{
					HexConfig.rotate_board = Boolean.parseBoolean(f[1]);
				}
				else if(f[0].equals("black_name"))
				{
					HexConfig.black_name = f[1];
				}
				else if(f[0].equals("white_name"))
				{
					HexConfig.white_name = f[1];
				}
				else
				{
					System.err.println("useless line");
				}
			}
			catch (Exception e)
			{
				System.err.println("bad line: "+l);
			}
		}
	}
}
