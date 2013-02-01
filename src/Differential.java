/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package differential;

import java.util.*;

/**
 *
 * @author kenschiller
 */
public class Differential extends Graphable {
    public Differential(String string) {
        super(string); //theory
        String processed = new String();
        for(int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if(c != ' ') {
                processed += c;
            }
        }
        setString(processed);
    }
    public double operate(double input) {
        return 0.0;
    }
    public double operate(double x, double y) {
        //return x + y;
        //return 2 * x - 3 * (Math.sin(y) + x);
        //return (x + y) / (x - y) + x * y / (x + 2) + (y - x) / (y - 1);
        //return x / y; // hyperbola - use to debug vertical slope issues
        //return Math.pow(Math.E, -(x * x)) * y;
        //return Math.pow(Math.abs(x*y), .5) * (y - x);
        return process(getString(), x, y);
    }
    public double process(String s, double x, double y) {
        double sum = 0, base = 0;
        int start = 0, end;
        int length = s.length();
        for(int i = 0; i < length; i++) {
            start = i;
            int level = 0;
            while(i < length && !(level == 0 && (s.charAt(i) == '+' || s.charAt(i) == '-'))) {
                if(s.charAt(i) == '(') level++;
                else if(s.charAt(i) == ')') level--;
                i++;
            }
            end = i;
            if(start != end) {
                String a = s.substring(start, end);
                double product = product(a, x, y);
                if(start != 0 && s.charAt(start - 1) == '-') {
                    product *= -1;
                }
                sum += product;
            }
        }
        return sum;
    }
    public double product(String a, double x, double y) {
        double product = 1.0;
        Double base = null;
        int length = a.length();
        boolean inv = false;
        for(int j = 0; j < a.length(); j++) {
            int [] end = {0, 0}; //first int: is it in use?
            inv = false;
            double exp = 1.0;
            char c = a.charAt(j);
            if(c == '/') {
                inv = true;
                c = a.charAt(++j);
            }
            if(c == '*') {}
            else if(c == 'x') {
                exp = x;
            }
            else if(c == 'y') {
                exp = y;
            }
            else if(c == '(') {
                int jStart = j;
                int level2 = 1;
                while(j < length && level2 != 0) {
                    j++;
                    if(a.charAt(j) == '(') level2++;
                    else if(a.charAt(j) == ')') level2--;
                }
                j++;
                exp = process(a.substring(jStart + 1, j - 1), x, y);
            }
            else if((c >= 48 && c <= 57) || c == 46) {
                String constant = new String();
                constant += c;
                if(j + 1 < a.length()) {
                    char cNext = a.charAt(j + 1);
                    if((cNext >= 48 && cNext <= 57) || cNext == 46) {
                        constant += cNext;
                        j++;
                    }
                }
                exp = Double.parseDouble(constant);
            }
            else if(c == 'e') {
                exp = Math.E;
            }
            else if(c == 'π') {
                exp = Math.PI;
            }
            else if (j < a.length() - 2) {
                String trig = a.substring(j, j + 3);
                if(trig.equals("sin")) {
                    exp = Math.sin(subproduct(a.substring(j + 3), x, y, end));
                }
                else if(trig.equals("cos")) {
                    exp = Math.cos(subproduct(a.substring(j + 3), x, y, end));
                }
                else if(trig.equals("tan")) {
                    exp = Math.tan(subproduct(a.substring(j + 3), x, y, end));
                }
                else if(trig.equals("sec")) {
                    exp = 1.0 / Math.cos(subproduct(a.substring(j + 3), x, y, end));
                }
                else if(trig.equals("csc")) {
                    exp = 1.0 / Math.sin(subproduct(a.substring(j + 3), x, y, end));
                }
                else if(trig.equals("cot")) {
                    exp = 1.0 / Math.tan(subproduct(a.substring(j + 3), x, y, end));
                }
                else if(j < a.length() - 5) {
                    trig += a.substring(j + 3, j + 6);
                    if(trig.equals("arcsin")) {
                        exp = Math.asin(subproduct(a.substring(j + 6), x, y, end));
                    }
                    else if(trig.equals("arccos")) {
                        exp = Math.acos(subproduct(a.substring(j + 6), x, y, end));
                    }
                    else if(trig.equals("arctan")) {
                        exp = Math.atan(subproduct(a.substring(j + 6), x, y, end));
                    }
                }
            }
            if(end[0] != 0) {
                j = end[1];
            }
            if(base != null) {
                product *= Math.pow(base, exp);
                base = null;
            }
            else if(j + 1 < a.length() && a.charAt(j + 1) == '^') {
                base = exp;
                j++;
            }
            else {
                if(inv) {
                    product /= exp;
                }
                else {
                    product *= exp;
                }
            }
        }
        return product;
    }
    public double subproduct(String a, double x, double y, int [] end) {
        //changes: break on *, /; end2 replaces end;
        //  j declared outside of loop; end variable modified
        double product = 1.0;
        Double base = null;
        int length = a.length();
        boolean inv = false;
        int j;
        for(j = 0; j < a.length(); j++) {
            int [] end2 = {0, 0}; //first int: is it in use?
            inv = false;
            double exp = 1.0;
            char c = a.charAt(j);
            if(c == '*') break;
            if(c == '/') break;
            else if(c == 'x') {
                exp = x;
            }
            else if(c == 'y') {
                exp = y;
            }
            else if(c == '(') {
                int jStart = j;
                int level2 = 1;
                while(j < length && level2 != 0) {
                    j++;
                    if(a.charAt(j) == '(') level2++;
                    else if(a.charAt(j) == ')') level2--;
                }
                j++;
                exp = process(a.substring(jStart + 1, j - 1), x, y);
            }
            else if((c >= 48 && c <= 57) || c == 46) {
                String constant = new String();
                constant += c;
                if(j + 1 < a.length()) {
                    char cNext = a.charAt(j + 1);
                    if((cNext >= 48 && cNext <= 57) || cNext == 46) {
                        constant += cNext;
                        j++;
                    }
                }
                exp = Double.parseDouble(constant);
            }
            else if(c == 'e') {
                exp = Math.E;
            }
            else if(c == 'π') {
                exp = Math.PI;
            }
            else if (j < a.length() - 2) {
                String trig = a.substring(j, j + 3);
                if(trig.equals("sin")) {
                    exp = Math.sin(subproduct(a.substring(j + 3), x, y, end2));
                }
                else if(trig.equals("cos")) {
                    exp = Math.cos(subproduct(a.substring(j + 3), x, y, end2));
                }
                else if(trig.equals("tan")) {
                    exp = Math.tan(subproduct(a.substring(j + 3), x, y, end2));
                }
                else if(trig.equals("sec")) {
                    exp = 1.0 / Math.cos(subproduct(a.substring(j + 3), x, y, end2));
                }
                else if(trig.equals("csc")) {
                    exp = 1.0 / Math.sin(subproduct(a.substring(j + 3), x, y, end2));
                }
                else if(trig.equals("cot")) {
                    exp = 1.0 / Math.tan(subproduct(a.substring(j + 3), x, y, end2));
                }
                else if(j < a.length() - 5) {
                    trig += a.substring(j + 3, j + 6);
                    if(trig.equals("arcsin")) {
                        exp = Math.asin(subproduct(a.substring(j + 6), x, y, end2));
                    }
                    else if(trig.equals("arccos")) {
                        exp = Math.acos(subproduct(a.substring(j + 6), x, y, end2));
                    }
                    else if(trig.equals("arctan")) {
                        exp = Math.atan(subproduct(a.substring(j + 6), x, y, end2));
                    }
                }
            }
            if(end[0] != 0) {
                j = end[1];
            }
            if(base != null) {
                product *= Math.pow(base, exp);
                base = null;
            }
            else if(j + 1 < a.length() && a.charAt(j + 1) == '^') {
                base = exp;
                j++;
            }
            else {
                if(inv) {
                    product /= exp;
                }
                else {
                    product *= exp;
                }
            }
        }
        end[0] = 1;
        end[1] = j;
        return product;
    }
    public Graph eulerApproximation(GrapherPanel grapher, double initialX, double initialY, double step) {
        int xSize = grapher.getXSize();
        int ySize = grapher.getYSize();
        Window window = grapher.getWindow();
        double xMin = window.getXMin();
        double xMax = window.getXMax();
        double yMin = window.getYMin();
        double yMax = window.getYMax();
        boolean [] [] graphData = new boolean [xSize] [ySize];
        double pixelXScale = (xMax - xMin) / (xSize - 1);
        int mode = 1;
        while(mode != -3) {
            //System.out.println("mode = " + mode); // extrapolate left / right
            int currentPixel = grapher.xToPixel(initialX) + mode;
            step *= mode;
            int lastPixelX = grapher.xToPixel(initialX);
            int lastPixelY = grapher.yToPixel(initialY);
            for(double x = initialX, y = initialY; mode == 1? x <= xMax - step : x >= xMin + step; x += step) {
                double deltaY = operate(x, y) * step;
                //System.out.println("x=" + x + " y=" + y + " ∆y=" + deltaY);
                y += deltaY;
                if(Math.abs(step) < pixelXScale) {
                    double pointX = xMin + currentPixel * pixelXScale; // pixel to x
                    if(mode == 1? x > pointX : x < pointX) {
                        double pointY = GrapherPanel.interpolateLinear(pointX, x - step, y - deltaY, x, y);
                        int pixelY = grapher.yToPixel(pointY);
                        if(lastPixelX >= 0 && lastPixelX < xSize && lastPixelY >= 0 && lastPixelY < ySize &&
                                currentPixel >= 0 && currentPixel < xSize && pixelY >= 0 && pixelY < ySize) {
                            GrapherPanel.bresenham(graphData, lastPixelX, lastPixelY, currentPixel, pixelY);
                        }
                        lastPixelX = currentPixel;
                        lastPixelY = pixelY;
                        currentPixel += mode;
                    }
                }
                else {
                    int pixelX1 = grapher.xToPixel(x);
                    int pixelY1 = grapher.yToPixel(y - deltaY);
                    int pixelX2 = grapher.xToPixel(x + step);
                    int pixelY2 = grapher.yToPixel(y);
                    if(pixelX1 >= 0 && pixelX1 < xSize && pixelY1 >= 0 && pixelY1 < ySize &&
                            pixelX2 >= 0 && pixelX2 < xSize && pixelY2 >= 0 && pixelY2 < ySize) {
                        GrapherPanel.bresenham(graphData, pixelX1, pixelY1, pixelX2, pixelY2);
                    }
                }
            }
            mode -= 2;
        }
        Graph graph = new Graph();
        graph.setGraphData(graphData, window);
        return graph;
    }
}
