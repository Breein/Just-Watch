package render;

import main.Dimensions;
import objects.*;
import world.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Render implements Dimensions {

    public Render(Cell[][] map, HashMap<String, Mob> mobs, HashMap<String, Eat> eats){

        int size[] = {WX * P_SIZE, (WY + 1) * P_SIZE, 0, 0};
        Rectangle userWindow = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        size[2] = (userWindow.width / 2) - (SRX / 2);
        size[3] = (userWindow.height / 2) - (SRY / 2);

        int sizeTile[] = {WX, WY, P_SIZE};

        JFrame window = new JFrame("Just watch 0.1");
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, size[0], size[1]);

        ControlLayer sp = new ControlLayer(P_SIZE);
        sp.setBounds(0, 0, size[0], size[1]);
        sp.setOpaque(false);

        RenderLayer rw = new RenderLayer(map, mobs, eats, sizeTile, sp);
        sp.setRenPlane(rw);
        rw.setBounds(0, 0, size[0], size[1]);
        rw.setOpaque(true);

        layeredPane.add(rw, 0);
        layeredPane.add(sp, 1);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(size[2], size[3]);
        window.setSize(SRX, SRY);
        window.setResizable(false);

        window.add(layeredPane);
        window.setVisible(true);
    }
}
