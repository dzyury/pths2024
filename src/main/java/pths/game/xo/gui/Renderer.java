package pths.game.xo.gui;

import pths.game.xo.model.Mark;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Renderer {
    private final Image cross;
    private final Image nought;

    public Renderer() {
        cross = load("cross.png");
        nought = load("nought.png");
    }

    private Image load(String path) {
        try {
            var resource = Renderer.class.getClassLoader().getResourceAsStream(path);
            return resource == null ? null : ImageIO.read(resource);
        } catch (IOException e) {
            throw new RuntimeException("Can't load image: " + path, e);
        }
    }

    public void show(Graphics g, Mark mark, int width, int height, boolean isHint) {
        if (mark == null) return;

        Graphics2D g2d = (Graphics2D) g;
        var image = mark == Mark.X ? cross : nought;

        var size = Math.min(width, height);
        var pic = image.getScaledInstance(size, size, Image.SCALE_FAST);

        var x = (width - size) / 2;
        var y = (height - size) / 2;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, isHint ? 0.3f : 1f));
        g2d.drawImage(pic, x, y, null);
    }
}