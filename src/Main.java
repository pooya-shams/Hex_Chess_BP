import ir.sharif.math.bp02_1.hex_chess.graphics.Application;
import ir.sharif.math.bp02_1.hex_chess.graphics.listeners.SystemOutEventListener;
import ir.sharif.math.bp02_1.hex_chess.util.PieceName;

import java.awt.*;
import ir.sharif.math.bp02_1.hex_chess.graphics.models.StringColor;
import listeners.ChessListener;
import util.ApplicationHolder;

public class Main {
    public static void main(String[] args) {
        ApplicationHolder.app = new Application();
        ApplicationHolder.app.registerEventListener(new ChessListener(ApplicationHolder.app));
    }
}
