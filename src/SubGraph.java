/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package differential;

/**
 *
 * @author kenschiller
 */
public class SubGraph extends Graphable{
    Parent parent_;
    double a_;
    double b_;
    public SubGraph(Parent parent, double a, double b) {
        super("N/A");
        parent_ = parent;
        a_ = a;
        b_ = b;
    }
    public double operate(double input) {
        return parent_.subGraphOperate(input, a_, b_);
    }
}
