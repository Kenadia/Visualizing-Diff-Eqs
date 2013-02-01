/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package differential;

/**
 *
 * @author kenschiller
 */
public class Window {
    private double [] parameters_;
    public Window(double [] parameters) {
        parameters_ = parameters;
    }
    public Window(Window w) {
        parameters_ = w.parameters_;
    }
    public double getXMin() {
        return parameters_[0];
    }
    public double getXMax() {
        return parameters_[1];
    }
    public double getXScale() {
        return parameters_[2];
    }
    public double getYMin() {
        return parameters_[3];
    }
    public double getYMax() {
        return parameters_[4];
    }
    public double getYScale() {
        return parameters_[5];
    }
    public double xRange() {
        return parameters_[1] - parameters_[0];
    }
    public String toString() {
        return "[" + parameters_[0] + ", " + parameters_[1] + ", " + parameters_[2] + ", " + parameters_[3] + ", " + parameters_[4] + ", " + parameters_[5] + "]";
    }
}
