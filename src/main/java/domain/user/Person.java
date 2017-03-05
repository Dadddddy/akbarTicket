package user;

/**
 * Created by ghazal on 2/11/2017 AD.
 */
public class Person {
    private String fname;
    private String sname;
    private String nID;
    private String age; // adult,child,infant

    public Person(String[] personinfo,String _age) {
        fname=personinfo[0];
        sname=personinfo[1];
        nID=personinfo[2];
        age=_age;
    }
    public String getAge() { return age; }
    public String getFname() { return fname; }
    public String getSname() { return sname; }
    public String getnID() { return nID; }
}
