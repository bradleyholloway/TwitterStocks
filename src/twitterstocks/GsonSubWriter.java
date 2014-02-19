/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitterstocks;

/**
 *
 * @author roberto
 */
public class GsonSubWriter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Database.writeGSON("REDDIT");
        Database.writeGSON("YahooFinance");
        //Database.writeGSON("FoxNews");
        //Database.writeGSON("CnnMoney");
    }
}
