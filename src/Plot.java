/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package differential;

import java.util.*;
import java.awt.*;

/**
 *
 * @author kenschiller
 */
public class Plot {
    private ArrayList<Point> points_;
    private Color color_;
    public Plot(Color color) {
        points_ = new ArrayList<Point>();
        color_ = color;
    }
    public ArrayList<Point> getPoints() {
        return points_;
    }
    public Color getColor() {
        return color_;
    }
    public void addPoint(Point p) {
        points_.add(p);
    }
}
