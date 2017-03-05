package common;

import java.util.ArrayList;

/**
 * Created by ghazal on 2/10/2017 AD.
 */
public class Flight {
    private String airline; // IR or W5
    private String flight_num;
    private String date;
    private String origin;
    private String dest;
    private String t_departure;
    private String t_arrival;
    private String airplane;

    private ArrayList<ChairClass> classes;

    public Flight(String[] finfo){
        airline=finfo[0];
        flight_num=finfo[1];
        date=finfo[2];
        origin=finfo[3];
        dest=finfo[4];
        t_departure=finfo[5];
        t_arrival=finfo[6];
        airplane=finfo[7];
        classes= new ArrayList<ChairClass>();
    }

    public void set_classes(String[] _classes,ArrayList<String> prices) {
        int i;
        for(i=0;i<_classes.length;i++){
            classes.add(new ChairClass(_classes[i].charAt(0),_classes[i].charAt(1),prices.get(i)));
        }
    }
    public String getOrigin() { return origin; }
    public String getDest() { return dest; }
    public String getFlight_num() { return flight_num; }
    public String getDate() { return date; }
    public String getAirline() { return airline; }
    public String getT_departure(){return t_departure;}
    public String getT_arrival(){return  t_arrival;}
    public String getAirplane(){return airplane;}
    public ArrayList<ChairClass> get_classes() { return classes; }
}
