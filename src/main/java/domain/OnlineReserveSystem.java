import com.sun.org.apache.bcel.internal.generic.IXOR;
import common.Flight;
import common.Reserve;
import companies.ConnectFlightCompany;
import user.Person;


import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * Created by daddy on 2/11/17.
 */
public class OnlineReserveSystem {
    public ConnectFlightCompany c;
    public Socket socket;
    public PrintWriter writer;
    public BufferedReader reader;
    public ArrayList<Flight> ask_about_flight(String origin,String dest,String date) throws IOException{
        ArrayList<Flight> result=new ArrayList<Flight>();
//        ConnectFlightCompany c=new ConnectFlightCompany(comp_server_addr,comp_port);
        ArrayList<String> flights=c.QueryFlight(origin,dest,date);

        int i,j;
        Flight temp_flight;
        String[] temp_classes;

        for(i=0;i<flights.size();i=i+2){
            ArrayList<String> temp_prices=new ArrayList<String>();
            temp_flight=new Flight(flights.get(i).split(" "));
//            System.out.println("seat classes : "+flights.get(i+1));
            temp_classes=flights.get(i+1).split(" ");
            String s;
//            System.out.println()
            for(j=0;j<temp_classes.length;j++){
                temp_prices.add(s= c.QueryPrice(origin, dest, temp_flight.getAirline(), temp_classes[j].charAt(0)));
//                System.out.println(temp_classes[j].charAt(0) +" "+s);
            }

            temp_flight.set_classes(temp_classes,temp_prices);
            result.add(temp_flight);
        }

        return result;
    }
    public Reserve reserve(Flight f, char _class, ArrayList<Person> p)throws IOException{

        Reserve r=new Reserve(f,_class,p);

//        ConnectFlightCompany c=new ConnectFlightCompany(comp_server,comp_port);

        String[] result=c.QueryReserve(r).split(" ");
        r.set_token(result[0]);
        return r;
    }
    public boolean finalize_reserve(Reserve r){
        try {
//            ConnectFlightCompany c = new ConnectFlightCompany(comp_server, comp_port);
            ArrayList<String> result = c.QueryFin(r.getToken());
//            System.out.println("fuck : "+result.size());
            r.finalizeReserve(result.remove(0), result);
        }catch (IOException e){
            return false;
        }
        return true;
    }

    public void close()throws IOException{
        socket.close();
        writer.close();
        reader.close();
        c.close();

    }

    public OnlineReserveSystem(int portNumber ) throws  IOException{
        ServerSocket listener = new ServerSocket(portNumber);
        socket = listener.accept();
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer.println("********************************************************");
        writer.println("*** Welcome to the online ticket reservation service ***");
        writer.println("********************************************************");


//        System.out.println(reader.readLine());

    }
    public void searchResponse(String[] in_splitted,ArrayList<Flight> flights){
        for (int i =0;i<flights.size();i++){
            writer.println("Flight : "+flights.get(i).getAirline()+" "+flights.get(i).getFlight_num()+" Departure : "
                    +flights.get(i).getT_departure().charAt(0)+flights.get(i).getT_departure().charAt(1)+":"
                    +flights.get(i).getT_departure().charAt(2)+flights.get(i).getT_departure().charAt(3)
                    +" Arrival : "+flights.get(i).getT_arrival().charAt(0)+flights.get(i).getT_arrival().charAt(1)+":"+
                    flights.get(i).getT_arrival().charAt(2)+flights.get(i).getT_arrival().charAt(3)
                    +" Airplane : "+flights.get(i).getAirplane());
            int adultcost,childcost,infantcost;
//                System.out.println(in_splitted[4]+in_splitted[5]+in_splitted[6]);
            for(int j=0;j<flights.get(i).get_classes().size();j++){
                adultcost=(flights.get(i).get_classes().get(j).getPrice_adult())*Integer.parseInt(in_splitted[4]);
                childcost=(flights.get(i).get_classes().get(j).getPrice_child())*Integer.parseInt(in_splitted[5]);
                infantcost=(flights.get(i).get_classes().get(j).getPrice_infant())*Integer.parseInt(in_splitted[6]);

                int totalcost = adultcost+childcost+infantcost;
                if(flights.get(i).get_classes().get(j).get_num_of_availables()!='C')
                    writer.println("Class : "+flights.get(i).get_classes().get(j).getName()+" Price : "+totalcost);


            }
            writer.println("***");
        }


    }
    public ArrayList<Person> getPeopleInfo(String[] in_splitted)throws  IOException{
        ArrayList<String> persons=new ArrayList<String>();
        String person;
        ArrayList<Person> people=new ArrayList<Person>();
        int adult= Integer.parseInt(in_splitted[7]);
        int child = Integer.parseInt(in_splitted[8]);
        int infant = Integer.parseInt(in_splitted[9]);
        int t = adult+child+infant;
//        System.out.println("PeopleNo. : "+t);
        for(int i=0 ; i<t;i++)
            persons.add(reader.readLine());
        for(int i =0 ; i<adult;i++){
//            System.out.println(persons.get(i));
            Person tmp=new Person(persons.get(i).split(" "),"adult");
            people.add(tmp);
        }
        for(int i =adult ; i<adult+child;i++){
//            System.out.println(persons.get(i));
            Person tmp=new Person(persons.get(i).split(" "),"child");
            people.add(tmp);
        }
        for(int i =adult+child ; i<t;i++){
//            System.out.println(persons.get(i));
            Person tmp=new Person(persons.get(i).split(" "),"infant");
            people.add(tmp);
        }

        return people;

    }
    public int getTotalPrice(String[] in_splitted,Flight flight){
        int adultprice=0;
        int childprice=0;
        int infantprice=0;
        for(int j=0;j<flight.get_classes().size();j++){
            if(flight.get_classes().get(j).getName()==in_splitted[6].charAt(0))
            {
                 adultprice=Integer.parseInt(in_splitted[7])*flight.get_classes().get(j).getPrice_adult();
                 childprice=Integer.parseInt(in_splitted[8])*flight.get_classes().get(j).getPrice_child();
                 infantprice=Integer.parseInt(in_splitted[9])*flight.get_classes().get(j).getPrice_infant();
            }
        }
        int totalCost = adultprice+childprice+infantprice;
        return totalCost;

    }
    public void printFinalResult(Reserve res){
        System.out.println(res.getTickets().size());
        for(int i =0 ; i<res.getTickets().size();i++){
            writer.println(res.getPersons().get(i).getFname()+" "+res.getPersons().get(i).getSname()
            +" "+ res.getReference_number()+" "+res.getTickets().get(i)+" "+res.getFlight().getOrigin()
            +" "+res.getFlight().getDest()+" "+res.getFlight().getAirline()+" "+res.getFlight().getFlight_num()
            +" "+res.getChair_class()+" "+res.getFlight().getT_departure().charAt(0)+res.getFlight().getT_departure().charAt(1)
            +":"+res.getFlight().getT_departure().charAt(2)+res.getFlight().getT_departure().charAt(3)
            +" "+res.getFlight().getT_arrival().charAt(0)+res.getFlight().getT_arrival().charAt(1)+":"
            +res.getFlight().getT_arrival().charAt(2)+res.getFlight().getT_arrival().charAt(3)
            +" "+res.getFlight().getAirplane());

        }

    }
    public  void userRequest(String in )throws IOException{

        String[] in_splitted = in.split(" ");

        if(in_splitted[0].equals("search")){
            ArrayList<Flight> flights= ask_about_flight(in_splitted[1], in_splitted[2], in_splitted[3]);
            searchResponse(in_splitted,flights);
        }
        else if (in_splitted[0].equals("reserve")){
            ArrayList<Person> persons =getPeopleInfo(in_splitted);
            ArrayList<Flight> flights= ask_about_flight(in_splitted[1], in_splitted[2], in_splitted[3]);

            for (int i =0 ; i<flights.size();i++){
                if(flights.get(i).getAirline().equals(in_splitted[4]) && flights.get(i).getFlight_num().equals(in_splitted[5])  )
                {
                    Reserve newReserve = reserve(flights.get(i),in_splitted[6].charAt(0),persons);
                    int totalCost =getTotalPrice(in_splitted,flights.get(i));
                    writer.println(newReserve.getToken()+" "+totalCost);
                    String tmp =reader.readLine();
                    String[] finalstring =tmp.split(" ");
                    if(finalstring[0].equals("finalize")){
                        if(finalize_reserve(newReserve)){
                            printFinalResult(newReserve);
                        }
                    }

                }
            }

        }
    }

    public static void main(String[] args) throws IOException {

        OnlineReserveSystem ourServer = new OnlineReserveSystem(Integer.parseInt(args[0]));
        ourServer.c = new ConnectFlightCompany(args[1], Integer.parseInt(args[2]));
        ourServer.writer.println("What's your request?");
       String in;
        while((in = ourServer.reader.readLine())!=null){

           ourServer.userRequest(in);
//           if(ourServer.reader.readLine())
//            if(!ourServer.reader.ready()){ break; }
        }

        ourServer.close();


    }



}
