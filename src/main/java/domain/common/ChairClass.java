package common;

/**
 * Created by ghazal on 2/10/2017 AD.
 */
public class ChairClass {
    private char name;
    private int price_adult;
    private int price_child;
    private int price_infant;
    private char available; // A: more than 8 chairs available
                            // C: full
                            // 0 < number <= 8
    public ChairClass(char _name,char _available,String prices){
        name=_name;
        available=_available;
        String[] prices_splitted=prices.split(" ");
        price_adult=Integer.parseInt(prices_splitted[0]);
        price_child=Integer.parseInt(prices_splitted[1]);
        price_infant=Integer.parseInt(prices_splitted[2]);
    }
    public char get_num_of_availables() { return available; }
    public int getPrice_adult() {return price_adult;}
    public int getPrice_child() {return price_child;}
    public int getPrice_infant() {return price_infant;}
    public char getName(){return name;}
}
