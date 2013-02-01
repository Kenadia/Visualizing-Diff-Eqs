/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package differential;

import java.awt.*;

/**
 *
 * @author kenschiller
 */
public class Point {
    public double x, y;
    private Color color_;
    public Point(double _x, double _y) {
        x = _x;
        y = _y;
        color_ = null;
    }
    public Point(double _x, double _y, Color color) {
        x = _x;
        y = _y;
        color_ = color;
    }
    public Color getColor() {
        return color_;
    }
    public void setColor(Color color) {
        color_ = color;
    }
}
