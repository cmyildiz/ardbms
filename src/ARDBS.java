import java.sql.*;
import java.util.ArrayList;
public class ARDBS 
{
    public static void main(String[] args) 
    {
        
        // TODO code application logic here
        DatabaseController db = new DatabaseController();
        //ystem.out.println(db.getIdNonString("reservation", "reservation_no", "customer_id", "92449", "flight_id", "42696"));
        //aa
        /*Customer c = db.getCustomer("Wilkinson1@nowhere.com");
        Reservation r = db.insertReservation(15, 2186, "Biftek", c);
        r.display();*/
        /*ArrayList<String> att = db.makeArrayList("ama ", "sen ", "malsın");
        for(int i = 0; i < att.size(); i++){
            System.out.print(att.get(i));
        }
        */
       /* if(c == null){
            System.out.println("Customer is NULL");
        }
        else{
            c.display();
        }*/
        //Customer c = db.registerCustomer("tatatatata", "55555555555", "Müşteri", "Ballı", "2007-07-07", "luckylucky@lucky.com", "07777777777");
        /*ArrayList<String> citys = new ArrayList<String>();
        citys.add("Istanbul");
        citys.add("Turkey");
        citys.add("GT3");
        db.insertToQuery("city", citys);*/
        //Class.forName("com.mysql.jdbc.Driver");
        //db.addCustomerUnreg("12312431","Aliss", "Maaa", "1998-05-03", "jojojoj@nono.com", "05454115");
        //Class.forName("com.mysql.jdbc.Driver");
        //db.createAllTables();

        //System.out.println("AS"); 
        /*ArrayList<Flight> f = db.createFlightListCustomer("NO478", "YI102", "2016-08-06");
        
        for(int i = 0; i < f.size() ; i++){
            f.get(i).displayForCustomer();
        }*/
        
        
        
    }
}
