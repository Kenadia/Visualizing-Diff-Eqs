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
public abstract class Graphable {
    private String string_;
    private boolean isActive_;
    private Color color_;
    private boolean [] [] graphData_;
    private Window graphedWindow_;
    public Graphable(String string) {
        string_ = string;
        isActive_ = true;
        color_ = Color.black;
        graphData_ = null;
        graphedWindow_ = null;
    }
    protected String getString() {
        return string_;
    }
    public boolean isActive() {
        return isActive_;
    }
    public Color getColor() {
        return color_;
    }
    protected void setString(String string) {
        string_ = string;
    }
    public boolean isGraphed(Window window) {
        /*System.out.println(string_ + " isgraphed: " + (graphData_ != null && window.equals(graphedWindow_)));
        System.out.println(window);
        System.out.println(graphedWindow_);*/
        return graphData_ != null && window.equals(graphedWindow_);
    }
    public boolean [] [] getGraphData() {
        return graphData_;
    }
    public void activate() {
        isActive_ = true;
    }
    public void deactivate() {
        isActive_ = false;
    }
    public void setColor(Color color) {
        color_ = color;
    }
    public void setGraphData(boolean [] [] graphData, Window graphedWindow) {
        graphData_ = graphData;
        graphedWindow_ = graphedWindow;
    }
    public void setGraphedWindow(Window window) {
        graphedWindow_ = window;
    }
    public void renderGraph(GrapherPanel grapher) {
        Window window = grapher.getWindow();
        int xSize = grapher.getXSize();
        int ySize = grapher.getYSize();
        if(!isGraphed(window)) {
            boolean [] [] graphData = new boolean [xSize] [ySize];
            double leftBound = window.getXMin();
            double rightBound = window.getXMax();
            double pixelXScale = (rightBound - leftBound) / (xSize - 1);
            for(int i = 0; i < xSize; i++) {
                double x = leftBound + pixelXScale * i; // pixel to x
                double y = operate(x);
                if(!Double.isInfinite(y) && !Double.isNaN(y)) {
                    int pixelY = grapher.yToPixel(y);
                    if(pixelY >= 0 && pixelY < ySize) {
                        graphData[i][pixelY] = true;
                    }
                }
            }
            setGraphData(graphData, window);
        }
    }
    public abstract double operate(double input);
}
