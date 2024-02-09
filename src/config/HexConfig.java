package config;

import java.awt.*;
import java.io.File;

public class HexConfig // global configurations
{
	public static File config_file = new File("config/config");
	//
	public static Color board_panel_color = Color.decode("#52932f");
	public static Color removed_pieces_panel_color = Color.decode("#31591d");
	public static Color main_panel_color = Color.decode("#f7f7f7");
	public static Color board_back_color = Color.decode("#080808");
	public static Color[] board_colors = {Color.decode("#e8ab6f"), Color.decode("#ffce9e"), Color.decode("#d18b47")};
	public static boolean rotate_board = false;
	public static String white_name = "White";
	public static String black_name = "Black";
}
