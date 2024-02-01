import ir.sharif.math.bp02_1.hex_chess.graphics.Application;
import ir.sharif.math.bp02_1.hex_chess.graphics.listeners.SystemOutEventListener;
import ir.sharif.math.bp02_1.hex_chess.util.PieceName;

import java.awt.*;
import ir.sharif.math.bp02_1.hex_chess.graphics.models.StringColor;
import listeners.ChessListener;

public class Main {
    public static void main(String[] args) {
        Application application = new Application();
        application.registerEventListener(new ChessListener(application));
    }
}
