/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package differential;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author kenschiller
 */

import java.util.*;
import java.awt.*;

public class GrapherPanel extends JPanel {
    private int xSize_;
    private int ySize_;
    private ArrayList<Graphable> equations_;
    private ArrayList<Plot> plots_;
    private Window window_;
    private Color bgColor_;
    private Color axesColor_;
    private boolean axesOn_;
    boolean plotsActive_ = false;
    //
    private Differential differential_;
    double pointX_;
    double centerY_;
    double rangeY_;
    int repeatY_;
    double eulerStep_;
    Color eulerColor_;
    boolean fieldActive_;
    int colorRes_;
    boolean colorActive_;
    boolean colorLogMode_;
    boolean colorArctanMode_;
    float colorStart_;
    float colorEnd_;
    public GrapherPanel(Window window, int xSize, int ySize) {
        xSize_ = xSize;
        ySize_ = ySize;
        equations_ = new ArrayList<Graphable>();
        plots_ = new ArrayList<Plot>();
        if(window == null) setWindow(standardWindow());
        else setWindow(window);
        bgColor_ = Color.white;
        axesColor_ = Color.black;
        axesOn_ = true;
        initializeDifferential();
    }
    public void initializeDifferential() {
        differential_ = new Differential("sintanx"); //"x(y^π-2)/5x"
        System.out.println(differential_.operate(.1, Math.PI));
        differential_.setColor(new Color(140, 140, 255));
        differential_.setColor(Color.cyan);
        /*pointX_ = 0.0;
        centerY_ = 0.0;
        rangeY_ = 14;
        repeatY_ = 57;
        eulerStep_ = 1.0;
        eulerColor_ = Color.blue;
        fieldActive_ = true;
        colorRes_ = 4;
        colorActive_ = false;
        colorLogMode_ = false;
        colorArctanMode_ = false;
        colorStart_ = 0.0f;
        colorEnd_ = 1.0f;
        refreshEuler();*/
    }
    public void setDifferential(String equation) {
        differential_.setString(equation);
    }
    public void setBGColor(Color bgColor) {
        bgColor_ = bgColor;
    }
    public void setAxesColor(Color axesColor) {
        axesColor_ = axesColor;
    }
    public void activateAxes() {
        axesOn_ = true;
    }
    public void deactivateAxes() {
        axesOn_ = false;
    }
    public void setPointX(double pointX) {
        pointX_ = pointX;
    }
    public void setCenterY(double centerY) {
        centerY_ = centerY;
    }
    public void setRangeY(double rangeY) {
        rangeY_ = rangeY;
    }
    public void setRepeatY(int repeatY) {
        repeatY_ = repeatY;
    }
    public void setEulerStep(double eulerStep) {
        eulerStep_ = eulerStep;
    }
    public void setEulerColor(Color eulerColor) {
        eulerColor_ = eulerColor;
    }
    public void setFieldColor(Color fieldColor) {
        differential_.setColor(fieldColor);
    }
    public void setColorRes(int colorRes) {
        colorRes_ = colorRes;
    }
    public void setColorStart(float colorStart) {
        if(colorStart >= 0.0f && colorStart <= 1.0f) {
            colorStart_ = colorStart;
        }
    }
    public void setColorEnd(float colorEnd) {
        if(colorEnd >= 0.0f && colorEnd <= 1.0f) {
            colorEnd_ = colorEnd;
        }
    }
    public void activateEuler() {
        for(Graphable e : equations_) {
            e.activate();
        }
    }
    public void deactivateEuler() {
        for(Graphable e : equations_) {
            e.deactivate();
        }
    }
    public void activateField() {
        fieldActive_ = true;
    }
    public void deactivateField() {
        fieldActive_ = false;
    }
    public void activateColor() {
        colorActive_ = true;
    }
    public void deactivateColor() {
        colorActive_ = false;
    }
    public void setColorMode(int mode) {
        switch(mode) {
            case 0:
                colorLogMode_ = false;
                colorArctanMode_ = false;
                break;
            case 1:
                colorLogMode_ = true;
                colorArctanMode_ = false;
                break;
            case 2:
                colorLogMode_ = false;
                colorArctanMode_ = true;
                break;
            default:
                break;
        }
    }
    public void refreshEuler() {
        /*Graphable removeMe = null;
        for(Graphable graph : equations_) {
            if(graph instanceof Graph) {
                removeMe = graph;
            }
        }
        equations_.remove(removeMe);
        Graph graph = differential_.eulerApproximation(this, x, y, n);
        graph.setColor(Color.red);
        equations_.add(graph);
        System.out.println("NUM OF GRPHS : " + equations_.size());*/
        boolean active;
        if(equations_.size() > 0) {
            active = equations_.get(0).isActive();
        }
        else {
            active = true;
        }
        equations_.clear();
        double yMin = centerY_ - rangeY_ / 2;
        double yMax = centerY_ + rangeY_ / 2;
        if(repeatY_ == 1) {
            yMin = centerY_;
        }
        int count = 0;
        for(double y = yMin; y <= yMax; y += rangeY_ / (repeatY_ - 1)) {
            Graph graph = differential_.eulerApproximation(this, pointX_, y, eulerStep_);
            graph.setColor(eulerColor_);
            if(!active) {
                graph.deactivate();
            }
            equations_.add(graph);
            count++;
        }
    }
    public int getXSize() {
        return xSize_;
    }
    public int getYSize() {
        return ySize_;
    }
    public Window getWindow() {
        return window_;
    }
    public void activatePlots() {
        plotsActive_ = true;
    }
    public void deactivatePlots() {
        plotsActive_ = false;
    }
    public void setWindow(Window window) {
        window_ = window;
    }
    public void addFunction(Graphable function) {
        equations_.add(function);
    }
    public static Window standardWindow() {
        double [] parameters = {-10.0, 10.0, 1.0, -10.0, 10.0, 1.0};
        return new Window(parameters);
    }
    public int round(double a) {
        return (int) (a + 0.5);
    }
    public int xToPixel(double x) {
        double leftBound = window_.getXMin();
        double pixelXScale = (window_.getXMax() - leftBound) / (xSize_ - 1);
        return round((x - leftBound) / pixelXScale);
    }
    public int yToPixel(double y) {
        double lowerBound = window_.getYMin();
        double pixelYScale = (window_.getYMax() - lowerBound) / (ySize_ - 1);
        return round((y - lowerBound) / pixelYScale);
    }
    public void paintComponent(Graphics g) {
        g.setColor(bgColor_);
        g.fillRect(0, 0, xSize_, ySize_);
        graphEquations(g);
        if(plotsActive_) graphPlots(g);
        if(axesOn_) {
            g.setColor(axesColor_);
            drawAxes(g);
        }
    }
    public void graphEquations(Graphics g) {
        if(differential_.isActive()) {
            if(colorActive_) {
                drawColorField(g, differential_, colorLogMode_, colorArctanMode_, colorStart_, colorEnd_); // log, arctan
            }
            if(fieldActive_) {
                g.setColor(differential_.getColor());
                drawSlopeField(g, differential_);
            }
        }
        for(Graphable equation : equations_) {
            if(equation.isActive()) {
                g.setColor(equation.getColor());
                equation.renderGraph(this);
                drawGraph(g, equation.getGraphData());
            }
        }
    }
    public void graphPlots(Graphics g) {
        for(Plot plot : plots_) {
            g.setColor(plot.getColor());
            ArrayList<Point> points = plot.getPoints();
            for(Point point : points) {
                if(point.getColor() != null) {
                    g.setColor(point.getColor());
                }
                graphicsBox(g, xToPixel(point.x) - 1, yToPixel(point.y) - 1, 2, 2);
            }
        }
    }
    public void drawSlopeField(Graphics g, Differential differential) {
        double xMin = window_.getXMin();
        double xMax = window_.getXMax();
        double xScale = window_.getXScale();
        double yMin = window_.getYMin();
        double yMax = window_.getYMax();
        double yScale = window_.getYScale();
        for(double xStart = xMin; xStart < xMax; xStart += xScale) {
            for(double yStart = yMin; yStart < yMax; yStart += yScale) {
                double x = xStart + xScale / 2.0;
                double y = yStart + yScale / 2.0;
                pointSlopeSegment(g, x, y, differential.operate(x, y), xStart + xScale * 0.2, xStart + xScale * 0.8, yStart + yScale * 0.2, yStart + yScale * 0.8);
            }
        }
    }
    public void drawColorField(Graphics g, Differential differential, boolean logMode, boolean arctanMode, float startHue, float endHue) {
        // slopes
        System.out.println("slopes");
        double [] [] slopes = new double [xSize_] [ySize_];
        double leftBound = window_.getXMin();
        double rightBound = window_.getXMax();
        double upperBound = window_.getYMax();
        double lowerBound = window_.getYMin();
        double pixelXScale = (rightBound - leftBound) / (xSize_ - 1);
        double pixelYScale = (upperBound - lowerBound) / (ySize_ - 1);
        for(int i = 0; i < xSize_; i+= colorRes_) {
            for(int j = 0; j < ySize_; j+= colorRes_) {
                double x = leftBound + pixelXScale * i; // pixel to x
                double y = lowerBound + pixelYScale * j;
                double slope = differential.operate(x, y);
                if(!Double.isInfinite(slope) && !Double.isNaN(slope)) {
                    int pixelX = xToPixel(x);
                    int pixelY = yToPixel(y);
                    if(arctanMode) {
                        slopes[pixelX][pixelY] = Math.atan(slope);
                    }
                    else {
                        slopes[pixelX][pixelY] = logMode? Math.copySign(Math.log(Math.abs(slope) + 1), slope) : slope;
                    }
                }
            }
        }
        // max and min
        double slopeMin, slopeMax, slopeRange;
        float hueRange = endHue - startHue;
        if(arctanMode) {
            slopeMin = -Math.PI / 2;
            slopeMax = Math.PI / 2;
            slopeRange = Math.PI;
        }
        else {
            System.out.println("max and min");
            slopeMin = 1.0/0.0;
            slopeMax = -1.0/0.0;
            for(int i = 0; i < xSize_; i++) {
                for(int j = 0; j < ySize_; j++) {
                    double slope = slopes[i][j];
                    if(slopeMin > slope) slopeMin = slope;
                    else if(slopeMax < slope) slopeMax = slope;
                }
            }
            slopeRange = slopeMax - slopeMin;
            System.out.println("max = " + slopeMax + " min = " + slopeMin + " range = " + slopeRange);
        }
        // colors
        System.out.println("colors");
        int [] [] colors = new int [xSize_] [ySize_];
        for(int i = 0; i < xSize_; i+= colorRes_) {
            for(int j = 0; j < ySize_; j+= colorRes_) {
                colors[i][j] = Color.HSBtoRGB((float) ((slopes[i][j] - slopeMin) / slopeRange) * hueRange + startHue, 1.0f, 1.0f);
            }
        }
        // draw
        System.out.println("draw");
        for(int i = 0; i < xSize_; i+= colorRes_) {
            for(int j = 0; j < ySize_; j+= colorRes_) {
                g.setColor(new Color(colors[i][j]));
                this.graphicsBoxFill(g, i, j, colorRes_, colorRes_);
            }
        }
    }
    public void drawLine(Graphics g, double x, double y, double slope) {
        if(slope == 0.0) {
            graphicsLine(g, 0, yToPixel(y), xSize_ - 1, yToPixel(y));
        }
        else if (slope == 1.0/0.0) {
            graphicsLine(g, xToPixel(x), 0, xToPixel(x), ySize_ - 1);
        }
        else {
            double xMin = window_.getXMin();
            double xMax = window_.getXMax();
            double yMin = window_.getYMin();
            double yMax = window_.getYMax();
            pointSlopeSegment(g, x, y, slope, xMin, xMax, yMin, yMax);
        }
    }
    public void pointSlopeSegment(Graphics g, double x, double y, double slope, double xMin, double xMax, double yMin, double yMax) {
        if(slope >=0) {
            double x1 = x + (yMin - y) / slope;
            double y1 = y + (xMin - x) * slope;
            if(x1 < xMin) x1 = xMin;
            else if(y1 < yMin) y1 = yMin;
            double x2 = x + (yMax - y) / slope;
            double y2 = y + (xMax - x) * slope;
            if(x2 > xMax) x2 = xMax;
            else if(y2 > yMax) y2 = yMax;
            int pixX1 = xToPixel(x1);
            int pixY1 = yToPixel(y1);
            int pixX2 = xToPixel(x2);
            int pixY2 = yToPixel(y2);
            graphicsLine(g, pixX1, pixY1, pixX2, pixY2);
        }
        else {
            double x1 = x + (yMin - y) / slope;
            double y1 = y + (xMax - x) * slope;
            if(x1 > xMax) x1 = xMax;
            else if(y1 < yMin) y1 = yMin;
            double x2 = x + (yMax - y) / slope;
            double y2 = y + (xMin - x) * slope;
            if(x2 < xMin) x2 = xMin;
            else if(y2 > yMax) y2 = yMax;
            int pixX1 = xToPixel(x1);
            int pixY1 = yToPixel(y1);
            int pixX2 = xToPixel(x2);
            int pixY2 = yToPixel(y2);
            graphicsLine(g, pixX1, pixY1, pixX2, pixY2);
        }
    }
    public void drawAxes(Graphics g) {
        drawLine(g, 0, 0, 0.0);
        drawLine(g, 0, 0, 1.0/0.0);
    }
    public void drawGraph(Graphics g, boolean [] [] graphData) {
        if(xSize_ != graphData.length || ySize_ != graphData[0].length) {
            System.out.println("Error: graph data dimension mismatch");
        }
        else {
            for(int i = 0; i < xSize_; i++)
                for(int j = 0; j < ySize_; j++) {
                    if(graphData[i][j]) {
                        graphicsPoint(g, i, j);
                    }
                }
        }
    }
    public void graphicsPoint(Graphics g, int x, int y) {
        int drawX = x;
        int drawY = ySize_ - y - 1;
        g.drawLine(drawX, drawY, drawX, drawY);
    }
    public void graphicsLine(Graphics g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1, ySize_ - y1 - 1, x2, ySize_ - y2 - 1);
    }
    public void graphicsBox(Graphics g, int x, int y, int w, int h) {
        g.drawRect(x, ySize_ - y - 1 - h, w, h);
    }
    public void graphicsBoxFill(Graphics g, int x, int y, int w, int h) {
        g.fillRect(x, ySize_ - y - 1 - h, w, h);
    }
    public static double interpolateLinear(double x, double x1, double y1, double x2, double y2) {
        double b = (y2 - y1) / (x2 - x1);
        double y = (x - x1) * b + y1;
        return y;
    }
    public static void bresenham(boolean [] [] graph, int x1, int y1, int x2, int y2) {
        int dx, dy, sx, sy;
        if(x1 < x2) {
            dx = x2 - x1;
            sx = 1;
        }
        else {
            dx = x1 - x2;
            sx = -1;
        }
        if(y1 < y2) {
            dy = y2 - y1;
            sy = 1;
        }
        else {
            dy = y1 - y2;
            sy = -1;
        }
        int error = dx - dy;
        while(!(x1 == x2 && y1 == y2)) {
            graph[x1][y1] = true;
            int e2 = 2 * error;
            if(e2 > -dy) {
                error -= dy;
                x1 += sx;
            }
            if(e2 < dx) {
                error += dx;
                y1 += sy;
            }
        }
        graph[x2][y2] = true;
    }
}
