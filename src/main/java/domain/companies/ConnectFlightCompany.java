package companies;

import common.ChairClass;
import common.Flight;
import common.Reserve;
import user.Person;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by ghazal on 2/9/2017 AD.
 */
public class ConnectFlightCompany {
    Socket client;
    PrintWriter writer;
    BufferedReader reader;



    public ConnectFlightCompany(String server_addr, int port) throws IOException {
        client = new Socket(server_addr,port);
        writer = new PrintWriter(client.getOutputStream(),true);
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public ArrayList<String> QueryFlight(String from,String to,String date)throws IOException{
        writer.println("AV "+from+" "+to+" "+date);
        ArrayList<String> result=new ArrayList<String>();
        String flight_info;
        while ((flight_info=reader.readLine())!=null){
//            System.out.println(flight_info);
            result.add(flight_info);
            result.add(reader.readLine());
            if(!reader.ready()){ break; }
        }
        System.out.println("result : "+result.size());
        return result;
    }

    public String QueryPrice(String from,String to,String airline,char _class)throws IOException{
        writer.println("PRICE " + from + " " + to + " " + airline + " " + _class);
        return reader.readLine();
    }

    public String QueryReserve(Reserve r)throws IOException{
        String query="RES "+r.getFlight().getOrigin()+" "+r.getFlight().getDest()+" "+r.getFlight().getDate()+
                " "+r.getFlight().getAirline()+" "+r.getFlight().getFlight_num()+" "+r.getChair_class()+" "+
                r.get_num_of_adults()+" "+r.get_num_of_childs()+" "+r.get_num_of_infants();
        writer.println(query);
        //---> I have assumed that array of Person is in order 1)adult 2)child 3)infant
        ArrayList<Person> persons=r.getPersons();
        for(int i=0;i<persons.size();i++)
            writer.println(persons.get(i).getFname()+" "+persons.get(i).getSname()+" "+persons.get(i).getnID());
        return reader.readLine();
    }

    public ArrayList<String> QueryFin(String token)throws IOException{
        writer.println("FIN " + token);
        ArrayList<String> result=new ArrayList<String>();
        String temp;
        while((temp=reader.readLine())!=null){
            result.add(temp);
            if(!reader.ready()){ break; }
        }

        return result;
    }

    public void close() throws IOException{
        writer.close();
        reader.close();
        client.close();
    }
}
