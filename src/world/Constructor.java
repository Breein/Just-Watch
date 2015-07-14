package world;

import main.Dimensions;
import objects.*;

import java.awt.*;
import java.util.*;

public class Constructor implements Dimensions {
    private Random r = new Random();
    private Cell[][] map;

    public Cell[][] map(){
        int id = 0;
        Cell map[][] = new Cell[WY][WX];

        for(int y = 0; y < WY; y++){
            for(int x = 0; x < WX; x++){
                map[y][x] = new Cell(x, y, id);
                id++;
            }
        }

        this.map = map;
        return map;
    }

    public void mobs(int count, HashMap<String, Mob> Mobs){
        int x, y;
        Mob mob;

        for(int i = Mobs.size(), len = count + i; i < len; i++){
            x = rnd(1, WX - 1);
            y = rnd(1, WY - 1);

            mob = new Mob(x, y, i, "mob_" + i);

            Mobs.put("mob_" + i, mob);
            map[y][x].beHere(mob);
        }
    }

    public void mobs(String tid, HashMap<String, Mob> Mobs){
        Mob mob = Mobs.get(tid);
        map[mob.y()][mob.x()].outHere(mob);
        Mobs.remove(tid);
    }

    public void eats(int count, HashMap<String, Eat> Eats){
        int x, y;
        Eat eat;

        for(int i = Eats.size(), len = count + i; i < len; i++){
            x = rnd(1, WX - 1);
            y = rnd(1, WY - 1);

            eat = new Eat(x, y, i, rnd(10, 100));

            Eats.put("eat_" + i, eat);
            map[y][x].beHere(eat);
        }
    }


    public void test(int count){
        int sx, sy;
        ArrayList<point> stack = new ArrayList<point>();
        ArrayList<point> res = new ArrayList<point>();
        HashMap<String, point> result = new HashMap<String, point>();
        HashMap<String, point> tmp_res = new HashMap<String, point>();

        int radius = 8;
        int tmp [] = new int [2];

        sx = 15;
        sy = 15;

        stack.add(new point(sx, sy));

        for(int i = 1; i < count; i++){
            stack.add(new point(rnd(sx - radius, sx + radius), rnd(sy - radius, sy + radius)));
        }

        for(int i = 1; i < count; i++){
            if(stack.get(0).y < stack.get(i).y){
                tmp[0] = stack.get(0).x; tmp[1] = stack.get(0).y;
                stack.get(0).x = stack.get(i).x; stack.get(0).y = stack.get(i).y;
                stack.get(i).x = tmp[0]; stack.get(i).y = tmp[1];
            }
        }

        res.add(new point(stack.get(0).x, stack.get(0).y));
        stack.remove(0);

        while(stack.size() > 0){
            int index = 0;
            double angle = 0;
            double angl = 180;

            for(int i = 0; i < stack.size(); i++){
                point a = new point(res.get(0).x, res.get(0).y);
                point b = stack.get(i);

                double x = b.x - a.x;
                double y = b.y - a.y;
                if(x == 0){
                    angle = y > 0 ? 0 : 90;
                }else{
                    angle = Math.atan(y/x) * 180/Math.PI;
                    if(y == 0 && x < 0) angle = 0;
                    if(x > 0 && y < 0) angle = 180 + angle;
                    if(y == 0 && x > 0) angle = 180;
                }

                if(angle < angl){
                    angl = angle;
                    index = i;
                }
            }

            res.add(new point(stack.get(index).x, stack.get(index).y));
            stack.remove(index);
        }

        sx = res.get(0).x;
        sy = res.get(0).y;

        int min_x = 10000, min_y = 10000, max_x = -1000, max_y = -1000;

        while(res.size() > 0){
            stack = res.size() != 1 ? drawBresenhamLine(res.get(0).x, res.get(0).y, res.get(1).x, res.get(1).y) : drawBresenhamLine(res.get(0).x, res.get(0).y, sx, sy);

            for(int i = 0; i < stack.size(); i++){
                result.put(stack.get(i).x + ":" + stack.get(i).y, new point(stack.get(i).x, stack.get(i).y));

                if(min_x > stack.get(i).x) min_x = stack.get(i).x;
                if(min_y > stack.get(i).y) min_y = stack.get(i).y;
                if(max_x < stack.get(i).x) max_x = stack.get(i).x;
                if(max_y < stack.get(i).y) max_y = stack.get(i).y;
            }
            stack.clear();
            res.remove(0);
        }

        min_x--; max_x++; min_y--; max_y++;

        // создаем новый холст для заливки
        for(int y = min_y; y < max_y; y++){
            for(int x = min_x; x < max_x; x++){
                tmp_res.put(x + ":" + y, new point(x, y));
            }
        }

        //построчная заливка слева на право, и с права на лево
        for(int y = min_y; y < max_y; y++){
            for(int x = min_x; x < max_x; x++){          // с лева на право
                if(tmp_res.containsKey(x + ":" + y)){    // если существует на холсте
                    if(result.containsKey(x + ":" + y)){ // если существует и на контуре
                        break;                           // переходим на новую строку
                    }
                }
                tmp_res.remove(x + ":" + y);             // не встретилось контура, вырезаем из заливки эту клетку
            }

            for(int x = max_x; x > min_x; x--){          // тоже самое, но с права на лево
                if(tmp_res.containsKey(x + ":" + y)){
                    if(result.containsKey(x + ":" + y)){
                        break;
                    }
                }
                tmp_res.remove(x + ":" + y);
            }
        }

        //построчная заливка сверху вниз
        for(int y = min_y; y < max_y; y++){
            for(int x = min_x; x < max_x; x++){
                if(!tmp_res.containsKey(x + ":" + y) && tmp_res.containsKey(x + ":" + (y + 1)) && !result.containsKey(x + ":" + (y + 1))){
                    tmp_res.remove(x + ":" + (y + 1));
                }
            }
        }



        for(point element : tmp_res.values()){
            map[element.y][element.x].codeType(2);
        }
    }

    /*
    public void test(int count){
        ArrayList<Point> array = new ArrayList<Point>();

        int sx, sy;
        sx = 15;
        sy = 15;

        int radius = 8;

        for(int i = 1; i < count; i++){
            array.add(new Point(
                    rnd(sx - radius, sx + radius),
                    rnd(sy - radius, sy + radius)
            ));
        }


        Point min = array.get(0);
        Point max = array.get(0);


        for (Point point : array) {
            if (min.y > point.y) min = point;
            if (min.y < point.y) max = point;
        }

        int middle = (max.y - min.y) / 2;

        Vector<Point> top = new Vector<Point>();
        Vector<Point> bottom = new Vector<Point>();

        for (Point point : array) {
            if (point.y <= middle)
                top.add(point);

            else bottom.add(point);
        }

        Collections.sort(top, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return p1.x - p2.x;
            }
        });

        Collections.sort(bottom, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return p2.x - p1.x;
            }
        });

        Vector<Point> result = top;
        result.addAll(bottom);

        for (Point point : top) {
            map[point.y][point.x].codeType(2);
            System.out.println("::" + point);
        }
    }

    private boolean rotate(Point A, Point B, Point C) {
        return ((B.x - A.x) * (C.y - B.y) - (B.y - A.y) * (C.x - B.x)) < 0;
    }
    */

    private int sign (int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
        //возвращает 0, если аргумент (x) равен нулю; -1, если x < 0 и 1, если x > 0.
    }

    public ArrayList<point> drawBresenhamLine (int xstart, int ystart, int xend, int yend){
        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;
        ArrayList<point> result = new ArrayList<point>();

        dx = xend - xstart;
        dy = yend - ystart;

        incx = sign(dx);
        incy = sign(dy);

        if (dx < 0) dx = -dx;
        if (dy < 0) dy = -dy;


        if (dx > dy){
            pdx = incx;	pdy = 0;
            es = dy;	el = dx;

        }else{
            pdx = 0;	pdy = incy;
            es = dx;	el = dy;
        }

        x = xstart;
        y = ystart;
        err = el/2;

        result.add(new point(x, y));

        for (int t = 0; t < el; t++){
            err -= es;
            if (err < 0){
                err += el;
                x += incx;//сдвинуть прямую (сместить вверх или вниз, если цикл проходит по иксам)
                y += incy;//или сместить влево-вправо, если цикл проходит по y
            }else{
                x += pdx;//продолжить тянуть прямую дальше, т.е. сдвинуть влево или вправо, если
                y += pdy;//цикл идёт по иксу; сдвинуть вверх или вниз, если по y
            }

            result.add(new point(x, y));
        }

        return result;
    }


    private int rnd(int min, int max){
        return r.nextInt(max - min) + min;
    }

    class point{
        int x;
        int y;
        point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
