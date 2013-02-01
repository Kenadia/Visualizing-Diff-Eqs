/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package differential;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 *
 * @author kenschiller
 */
public class MainPanel extends JPanel {
    private int xSize_;
    private int ySize_;
    private GrapherPanel grapher_;
    private int n;
    public MainPanel(int xSize, int ySize) {
        xSize_ = xSize;
        ySize_ = ySize;
        double [] parameters = {-5.0, 5.0, 0.25, -5.0, 5.0, 0.25};
        grapher_ = new GrapherPanel(new Window(parameters), 601, 601);
        //grapher_.addFunction(new Function("y=x"));
        //graph_.cobwebMain(a, b, c, n);
        this.add(grapher_);
        grapher_.setBounds(this.getX() + 10, this.getY() + 10, 601, 601);
        grapher_.setPointX(0.0);
        grapher_.setCenterY(0.0);
        grapher_.setRangeY(14);
        grapher_.setRepeatY(57);
        grapher_.setEulerStep(1.0);
        grapher_.setEulerColor(Color.blue);
        grapher_.activateField();
        grapher_.setColorRes(4);
        grapher_.deactivateColor();
        grapher_.setColorMode(0);
        grapher_.setColorStart(0.0f);
        grapher_.setColorEnd(1.0f);
        grapher_.refreshEuler();
        repaint();
    }

    /*public void changeA(double a) {
        //graph_.cobwebMain(a, b, c, n);
        grapher_.setPointX(a);
        grapher_.refreshEuler();
        repaint();
    }
    public void changeB(double b) {
        //graph_.cobwebMain(a, b, c, n);
        grapher_.setCenterY(b);
        grapher_.refreshEuler();
        repaint();
    }
    public void changeC(double c) {
        //graph_.cobwebMain(a, b, c, n);
        grapher_.setEulerStep(1.0 / c);
        grapher_.refreshEuler();
        repaint();
    }
    public void changeN(int _n) {
        n = _n;
        //graph_.cobwebMain(a, b, c, n);
        repaint();
    }*/

    public void setDifferential(String equation) {
        grapher_.setDifferential(equation);
        grapher_.refreshEuler();
        grapher_.repaint();
    }
    public void setBGColor(Color bgColor) {
        grapher_.setBGColor(bgColor);
        repaint();
    }
    public void setAxesColor(Color axesColor) {
        grapher_.setAxesColor(axesColor);
        repaint();
    }
    public void activateAxes() {
        grapher_.activateAxes();
        repaint();
    }
    public void deactivateAxes() {
        grapher_.deactivateAxes();
        repaint();
    }
    public void setPointX(double pointX) {
        grapher_.setPointX(pointX);
        grapher_.refreshEuler();
        repaint();
    }
    public void setCenterY(double centerY) {
        grapher_.setCenterY(centerY);
        grapher_.refreshEuler();
        repaint();
    }
    public void setRangeY(double rangeY) {
        grapher_.setRangeY(rangeY);
        grapher_.refreshEuler();
        repaint();
    }
    public void setRepeatY(int repeatY) {
        grapher_.setRepeatY(repeatY);
        grapher_.refreshEuler();
        repaint();
    }
    public void setEulerStep(double eulerStep) {
        grapher_.setEulerStep(eulerStep);
        grapher_.refreshEuler();
        repaint();
    }
    public void setEulerColor(Color eulerColor) {
        grapher_.setEulerColor(eulerColor);
        grapher_.refreshEuler();
        repaint();
    }
    public void setFieldColor(Color fieldColor) {
        grapher_.setFieldColor(fieldColor);
        repaint();
    }
    public void setColorRes(int colorRes) {
        grapher_.setColorRes(colorRes);
        repaint();
    }
    public void setColorStart(float colorStart) {
        grapher_.setColorStart(colorStart);
        repaint();
    }
    public void setColorEnd(float colorEnd) {
        grapher_.setColorEnd(colorEnd);
        repaint();
    }
    public void activateEuler() {
        grapher_.activateEuler();
        repaint();
    }
    public void deactivateEuler() {
        grapher_.deactivateEuler();
        repaint();
    }
    public void activateField() {
        grapher_.activateField();
        repaint();
    }
    public void deactivateField() {
        grapher_.deactivateField();
        repaint();
    }
    public void activateColor() {
        grapher_.activateColor();
        repaint();
    }
    public void deactivateColor() {
        grapher_.deactivateColor();
        repaint();
    }
    public void setColorMode(int mode) {
        grapher_.setColorMode(mode);
        repaint();
    }

    public void setPlotVisibility(boolean w) {
        if(w) {
            grapher_.activatePlots();
        }
        else {
            grapher_.deactivatePlots();
        }
        repaint();
    }
    public void paintComponent(Graphics g) {
    }
}
