/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package differential;

/**
 *
 * @author kenschiller
 */
public class Function extends Graphable {
    public Function(String string) {
        super(string); //theory
    }
    public double operate(double x) {
        int y = (int) x;
        return y;
    }
    /*public double operate(double input) {
        String string = getString();
        if(string.substring(0, 2).equals("y=")) {
            return evaluate(string.substring(2), input, '\0');
        }
        return Double.NaN;
    }
    public double evaluate(String formula, double input, char endChar) {
        int length = formula.length();
        for(int i = 0; i < length; i++) {
            char c = formula.charAt(i);
            switch(c) {
                case '(':
                    break;
                default:
                    return Double.NaN;
            }
        }
        return Double.NaN;
    }*/
}
