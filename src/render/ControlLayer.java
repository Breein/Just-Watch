package render;

import main.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControlLayer extends JPanel implements Dimensions, MouseMotionListener, MouseWheelListener {
    private int mx = 0, my = 0, nx, ny;
    private int tileSize;
    private RenderLayer renPlane;
    private int windowSize[] = {0, 0, SRX, SRY, SRX - 20, SRY - 20};
    private int srx = SRX - 20;
    private int sry = SRY - 20 - 29; // 29 px - высота верхней рамки окна

    public ControlLayer(int tileSize){
        this.tileSize = tileSize;

        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    public void setRenPlane(RenderLayer renPlane){
        this.renPlane = renPlane;
    }

    public void scrollMap(RenderLayer mapLayer){
        Rectangle boundsLayer = mapLayer.getBounds();

        if(mx < 20 || mx > srx || my < 20 || my > sry){
            if(mx < 20){
                if(boundsLayer.x < 0){
                    nx = boundsLayer.x + SCROLL_SPEED;
                }
            }
            if(mx > srx){
                if((boundsLayer.width + boundsLayer.x) > SRX){
                    nx = boundsLayer.x - SCROLL_SPEED;
                }
            }

            if(my < 20){
                if(boundsLayer.y < 0){
                    ny = boundsLayer.y + SCROLL_SPEED;
                }
            }
            if(my > sry){
                if((boundsLayer.height + boundsLayer.y) > SRY){
                    ny = boundsLayer.y - SCROLL_SPEED;
                }
            }
            mapLayer.setLocation(nx, ny);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int mouse = e.getWheelRotation();
        boolean key = false;

        if(mouse > 0){
            tileSize = tileSize - 1; // уменьшить размер тайла
            tileSize = tileSize < P_SIZE ? P_SIZE : tileSize;       // если он меньше минимального, то ставим минимальный
            renPlane.setSize(WX * tileSize, WY * tileSize);   // установка размеров по новому тайлу

            int x, y, w, h; Rectangle bounds = renPlane.getBounds();    // переменные и получение размера нового полотна (это формально будущий размер, а не текущий)

            x = bounds.x + (WX / 2);   // сдвигаем слой по х, на половину клуток рамера мира по Х, таким сдвигом, мы сдвигаем пропорцинально, и в лево и вправо
            y = bounds.y + (WY / 2);   // также по у
            w = bounds.width + x;           // расчет координаты нижнего угла, "конечный-Х"
            h = bounds.height + y;          // полуем "конечный-У"

            // Блок для углов, правый верхний, левый нижний.
            if(w <= windowSize[2] && y >= 0){ //  "конечный-Х" меньше чем размер окна (карта вылезла в видимость) и при этом, по "у" тоже вылезла (правый верхний угол)
                x = x + (windowSize[2] - w);  // вернуть "конечный-Х" в координаты окна
                key = true;                   // ключ что мы прошли блок углов
            }
            if(h <= windowSize[3] && x >= 0){ // "конечный-У" меньше чем размер окна (карта вылезла в видимость) и при этом, по "х" тоже вылезла (левый нижний угол)
                y = y + (windowSize[3] - h);  // вернуть "конечный-У" в координаты окна
                key = true;                   // ключ что мы прошли блок углов
            }

            // Блок для касания одной стороны, он же будет и при нижнем правом углу, сработают оба условия
            if(w <= windowSize[2] && !key){  // если вылез только "конечный-Х", и при этом, мы не были в блоке углов
                x = x + (windowSize[2] - w); // вернуть "конечный-Х" в координаты окна
            }
            if(h <= windowSize[3] && !key){  // если вылез только "конечный-У", и при этом, мы не были в блоке углов
                y = y + (windowSize[3] - h); // вернуть "конечный-У" в координаты окна
            }

            // Блок универсальный
            if(x >= 0){  // если "х" вылез за 0, вернуть "х" в 0
                x = 0;
            }
            if(y >= 0){ // если "у" вылез за 0, вернуть "у" в 0
                y = 0;
            }

            renPlane.setLocation(x, y); // установить получившиеся координаты сдвига слою
        }else{ //////////////////////////////////////
            int x, y; Rectangle bounds = renPlane.getBounds();

            x = e.getX() / (windowSize[2] / WX); // вычилсяем на сколько двигать слой, по координате мыши Х
            y = e.getY() / (windowSize[3] / WY); // по координате мыши У

            tileSize = tileSize + 1;
            renPlane.setSize(WX * tileSize, WY * tileSize);

            renPlane.setLocation(bounds.x - x, bounds.y - y);
        }
        renPlane.setTileSize(tileSize);
    }
}
