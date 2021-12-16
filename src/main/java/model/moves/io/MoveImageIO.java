package model.moves.io;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MoveImageIO {

    private static final Map<Icon, Map<State, Image>> imagesMap;

    static {
        imagesMap = new HashMap<>();
        for (Icon icon : Icon.values()) {
            imagesMap.put(icon, new HashMap<>());
        }

        putImage(Icon.ARROW, State.POSSIBLE);
        putImage(Icon.ARROW, State.HOVERED);
        putImage(Icon.ARROW, State.LAST);

        putImage(Icon.CROSS, State.POSSIBLE);
        putImage(Icon.CROSS, State.HOVERED);

        putImage(Icon.LINE, State.HOVERED);
        putImage(Icon.LINE, State.LAST);
    }

    public static Image getImage(Icon icon, State state) {
        return imagesMap.get(icon).get(state);
    }

    public static void putImage(Icon icon, State state) {
        imagesMap.get(icon).put(state, readImage(icon, state));
    }

    public static Image readImage(Icon icon, State state) {
        try {
            return ImageIO.read(new File(
                    String.format("res/img/moves/%s_%s.png", icon.name().toLowerCase(), state.name().toLowerCase())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public enum Icon {
        ARROW, LINE, CROSS
    }
    public enum State {
        HOVERED, POSSIBLE, LAST
    }
}
