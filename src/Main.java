/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package differential;

/**
 *
 * @author kenschiller
 */
public class Main {

    /**
     * @param args the command line arguments
     */

    public static void test(Integer a) {
        a = new Integer(3);
    }

    public static void main(String[] args) {
        Integer mine = 1;
        test(mine);
        System.out.println(mine);
    }

}
