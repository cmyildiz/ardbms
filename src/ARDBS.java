import java.util.*;
import javax.swing.JPanel;

public class ARDBS 
{        static MainFrame m = new MainFrame();
    public static Flight ff = new Flight();
    
     public static void setDisplayPanel(JPanel panel) {
        m.setPanel(panel);
    }   
     
    public static void main(String[] args) 
    {
        
        // f =new AvailableFlights(flightList, arrivalTimes, flightToReserve, 0)
        // TODO code application logic here
        DatabaseController db = new DatabaseController();
        ArrayList<Flight> flightList = db.createFlightListCustomer("RDD", "BVA", "2016-06-16");
        ArrayList<String> a = new ArrayList<String>();
        
/*
        for(int i = 0; i < flightList.size() ; i++)
        {
            flightList.get(i).displayForCustomer();
        }*/
        
        for(int i = 0; i < flightList.size() ; i++){
            String sa = "" +flightList.get(i).getFlight_id();
            a.add(db.arrivalTime(sa));
        }
     
        m.setVisible(true);
        JPanel current = new AvailableFlights(flightList, a, ff);
        setDisplayPanel(current);
        System.out.println(ff.getFlight_id());
        while(ff.getFlight_id() == 0){
            System.out.println("id = " + ff.getFlight_id());
            if(ff.getFlight_id() != 0){
                System.out.println("new id = " + ff.getFlight_id());
                ff.displayForCustomer();
                System.out.println("No NULL");
            }
        }
        
        System.out.println("Finished");
       // AvailableFlights f = new AvailableFlights(flightList, a, ff);
        /*Clerk[] C = db.mailsLike("example");
        for(int i= 0; i < C.length; i++){
            C[i].display();
        }
        /*Pilot[] p = db.mostFlyers(1);
        ArrayList<Integer> A = db.getFlightCounts();
        /*for(int i= 0; i < p.length; i++){
            p[i].displayPilot();
        }
        for(int i= 0; i < p.length; i++){
            System.out.println(A.get(i));
        }*///aa
        /*Customer c = db.getCustomer("Dejesus7@example.com");
        /*ArrayList<Reservation> R = db.reservationListCustomer(c);
        for(int i = 0; i < R.size(); i++){
            Reservation r1 = R.get(i);
            db.deleteRes(r1);
        }
        /*Customer c = db.getCustomer("Lyda.K_Pipkin@example.com");
        Reservation r = db.insertReservation(3, 12188, "bok", c);
        Clerk cl = db.getClerk("FineX@example.com");
        r = db.setClerkToReserv(r, cl);
        r.display();*/
        //db.setClerkToReserv();
        /*SystemManager m = db.getManager("JeffereyYSteel1@nowhere.com");
        m.display();
        /*Clerk c = db.getClerk("Fultz@example.com");
        c.display();
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
