package common;

import user.Person;

import java.util.ArrayList;

/**
 * Created by ghazal on 2/11/2017 AD.
 */
public class Reserve {
    private Flight flight;
    private char chair_class;
    private ArrayList<Person> persons;
    private String token;
    private String reference_number;
    private ArrayList<String> tickets;

    public Reserve(Flight f,char _class,ArrayList<Person> p){
        flight=f;
        persons=p;
        chair_class=_class;
    }

    public void finalizeReserve(String ref_num,ArrayList<String> _tickets){
        tickets=_tickets;
        reference_number=ref_num;
    }

    public Flight getFlight() { return flight; }
    public ArrayList<String> getTickets() { return tickets; }
    public char getChair_class() { return chair_class; }
    public String getToken() { return token; }
    public String getReference_number() { return reference_number; }
    public ArrayList<Person> getPersons() { return persons; }
    public int get_num_of_adults() {
        int i,result=0;
        for(i=0;i<persons.size();i++)
            if(persons.get(i).getAge().equals("adult"))
                result++;
        return result;
    }
    public int get_num_of_childs() {
        int i,result=0;
        for(i=0;i<persons.size();i++)
            if(persons.get(i).getAge().equals("child"))
                result++;
        return result;
    }
    public int get_num_of_infants() {
        int i,result=0;
        for(i=0;i<persons.size();i++)
            if(persons.get(i).getAge().equals("infant"))
                result++;
        return result;
    }
    public void set_token(String t){ token=t; }

    public void setReference_number(String rn) { reference_number=rn; }
}
