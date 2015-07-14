package render;

import main.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.HashMap;

import objects.*;
import world.Cell;

public class RenderLayer extends JPanel implements ActionListener, Dimensions{
    private HashMap<String, Mob> mobs;
    private HashMap<String, Eat> eats;
    private Cell[][] map;
    private int[] tileSize;
    private ControlLayer sp;

    Timer mainTimer = new Timer(40, this);

    public RenderLayer(Cell[][] map, HashMap<String, Mob> mobs, HashMap<String, Eat> eats, int[] tileSize, ControlLayer sp){
        this.map = map;
        this.mobs = mobs;
        this.eats = eats;
        this.sp = sp;
        this.tileSize = tileSize;

        mainTimer.start();
    }

    @Override
    public void paint(Graphics g){

        g.setColor(Color.white);
        g.fillRect(0, 0 , WX * P_SIZE, WX * P_SIZE);

        for(int y = 0; y < tileSize[1]; y++){
            for(int x = 0; x < tileSize[0]; x++){

                switch (map[y][x].codeType()){
                    case 0:
                        g.setColor(new Color(0, 0, 0));
                        break;

                    case 1:
                        g.setColor(new Color(151, 223, 108));
                        break;

                    case 2:
                        g.setColor(new Color(250, 238, 54));
                        break;

                    case 3:
                        g.setColor(new Color(113, 163, 109));
                        break;

                    default:
                        g.setColor(new Color(232, 232, 232));
                        break;
                }
                g.fillRect(x *  tileSize[2], y * tileSize[2], tileSize[2], tileSize[2]);
                g.setColor(Color.black);
                g.drawRect(x * tileSize[2], y * tileSize[2], tileSize[2], tileSize[2]);
                g.setFont(new Font("Arial", 0, 10));
                g.drawString(x + ":" + y, (x *  tileSize[2]) + 2, ((y + 1) * tileSize[2] - 10));
            }
        }
        /*

        for(Mobs mob : mobs.values()){
            int x, y;
            //long _x, _y;

            x = ((mob.x() / POLYSIZE) * tileSize[2]) + mob.x() % POLYSIZE;
            y = ((mob.y() / POLYSIZE) * tileSize[2]) + mob.y() % POLYSIZE;

            //System.out.println(_x + " : " + _y + " | " + mob.x() % POLYSIZE + " : " + mob.y());

            //x = (int) _x; y = (int) _y;
            /*
            patch = mob.gPatch();
            for(int i = 0, len = patch.size(); i < len; i++){
                g.setColor(Color.gray);
                g.fillRect(patch.get(i).X() * POLYSIZE, patch.get(i).Y() * POLYSIZE, POLYSIZE, POLYSIZE);
            }


            g.setColor(Color.blue);
            g.fillRect(mob.gTarget().x() *  tileSize[2], mob.gTarget().y() * tileSize[2], tileSize[2], tileSize[2]);

            g.setColor(Color.cyan);
            g.fillRect(x, y, tileSize[2], tileSize[2]);
            g.setColor(Color.black);
            g.drawRect(x, y, tileSize[2], tileSize[2]);
            g.drawString(mob.gHunger() + "", x, y + ((tileSize[2] / 2) + 4));
        }

        for(Eat eat : eats.values()){
            g.setColor(Color.red);
            g.fillRect(eat.X() *  tileSize[2], eat.Y() * tileSize[2], tileSize[2], tileSize[2]);
            g.setColor(Color.white);
            g.drawString(eat.Amount() + "", eat.X() *  tileSize[2], (eat.Y() * tileSize[2]) + ((tileSize[2] / 2) + 4));
        }
        */
    }

    public void setTileSize(int tileSize){
        this.tileSize[2] = tileSize;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        //repaint();
        sp.scrollMap(this);
    }

}
