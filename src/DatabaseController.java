import java.util.*;
import java.sql.*;

public class DatabaseController 
{
    private static final String JDBC_DRIVER ="com.mysql.jdbc.Driver";
    private static final  String URL = "jdbc:mysql://localhost:3306/ardbs";
    private static final String USERNAME = "root";
    private static final  String PASSWORD = "cs353";
    private  Connection conn;
    protected  String sqlCommand;
    protected  Statement statement;
   // protected  ResultSet rs = null;
    protected  ResultSet rs;
    //private  PreparedStatement preStt;
   // private  PreparedStatement preStt;
    private static DatabaseController dbInstance = null;

    //CREATE QUERIES


    /*private static final String DROP_STUDENT_TABLE	= "DROP TABLE IF EXISTS student";
    private static final String DROP_COMPANY_TABLE	= "DROP TABLE IF EXISTS company";
    private static final String DROP_APPLY_TABLE	= "DROP TABLE IF EXISTS apply";*/
    private static final String GET_AIRPLANE_TABLE	= "SELECT * FROM airplane";
    private static final String GET_AIRPORT_TABLE	= "SELECT * FROM airport";
    private static final String GET_BRANCH_TABLE	= "SELECT * FROM branch";
    private static final String GET_CITY_TABLE	= "SELECT * FROM city";
    private static final String GET_CLERK_TABLE	= "SELECT * FROM clerk";
    private static final String GET_CREW_TABLE	= "SELECT * FROM crew";
    private static final String GET_CREW_ASSIGNMENT_TABLE	= "SELECT * FROM crew_assignment";
    private static final String GET_CUSTOMER_TABLE	= "SELECT * FROM customer";
    private static final String GET_FLIGHT_TABLE	= "SELECT * FROM flight";
    private static final String GET_FLIGHT_ATTENDANT_TABLE	= "SELECT * FROM flight_attendant";
    private static final String GET_PILOT_TABLE	= "SELECT * FROM pilot";
    private static final String GET_PROMOTION_TABLE	= "SELECT * FROM promotoin";
    private static final String GET_RESERVATION_TABLE	= "SELECT * FROM reservation";
    private static final String GET_RESERVED_SEAT_TABLE	= "SELECT * FROM reserved_seat";
    private static final String GET_SEAT_PLAN_TABLE	= "SELECT * FROM seat_plan";
    private static final String GET_STAFF_TABLE	= "SELECT * FROM staff";
    private static final String GET_SYSTEM_MANAGER_TABLE	= "SELECT * FROM system_manager";

    private static final String SHOW_AIRPORTS_AS_CUSTOMER = "SELECT a_name, c_name FROM airport";
    private static final String ARRIVAL_TIME_CALCULATION_DEPART	= "SELECT time_zone FROM city, airport, flight " +
                                                                      "WHERE flight.iata_depart = airport.iata_code " +
                                                                      "AND airport.city = city.name " +
                                                                      "AND aiport.country = city.country ";
    private static final String ARRIVAL_TIME_CALCULATION_DEST = "SELECT time_zone FROM city, airport, flight " +
                                                                "WHERE iata_destination = airport.iata_code " +
                                                                "AND airport.city = city.name " +
                                                                "AND aiport.country = city.country";

    /*private static final String V = "BEGIN \n" +
                                    "IF NOT EXISTS        (SELECT TCKN " + "FROM customer " +
                                    "WHERE TCKN = @TCKN)\n" +
                                    "BEGIN\n" +
                                    "INSERT INTO customer(TCKN, cname, csurname, birthday, mail, mob_phone)\n" +
                                    "VARIABLE (@TCKN, @cname, @csurname, @birthday, @mail, @mob_phone);\n" +
                                    "END\n" +
                                    "END\n" +
                                    "\n" +
                                    "INSERT INTO reservation(customer_id, flight_id)";*/
    DatabaseController()
    {
        connect();
    }

    public static DatabaseController getInstance()
    {
        if(dbInstance == null)
        {
                dbInstance= new DatabaseController();
        }
        return dbInstance;
    }

    public boolean connect()
    {
        try 
        {
            Class.forName(JDBC_DRIVER).newInstance();
            this.conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            if(conn != null)
                    System.out.println("Connection established.");
            return true;
        }
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        catch (SQLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection()
    {
        PreparedStatement preStt;
        try
        {
            
            conn.close ();
            statement.close ();
            //preStt.close ();
        }
        catch(SQLException ex)
        {
            System.out.println("Connection could not be closed");
            System.exit(1);
        }
    }

    private boolean checkConnection()
    {
        try
        {
            if(conn.isClosed() || conn == null)
                    return connect();		
            else 
                    return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    protected Connection getConnection()
    {
        try
        {
            checkConnection();
            return conn;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    //General purpose executeUpdate() method
    public void queryExecuteUpdate(String query)
    {
        try
        {
            checkConnection();

            if(query!=null && query!="")
                statement.executeUpdate(query);
            else
                System.out.println("The given query is not valid.");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("ardbs.DatabaseController.queryExecuteUpdate() EXCEPTION!");
        }
    }
    
    private ArrayList<String> makeArrayList(String ... strings)
    {
        ArrayList<String> att = new ArrayList<String>();
        for(String s : strings)
        {
            att.add(s);
        }
        return att;
    }
    public User login(String mail, String password){
        User u = null;
        int x = loginSelect(mail, password);
        if( x == 0){            
            u = getCustomer(mail);
        }
        else if( x == 1){
            u = getClerk(mail);
        }
        else if( x == 2){
            u = getManager(mail);
        }
        else{
            //TODO 
        }
        return u;
    }
    public int loginSelect(String mail, String password)
    {
        int x = -1;
        if(dataExists("staff", "mail", mail))
        {
            if(dataExists("staff", "password", password))
            {                    
                if(dataExists("system_manager", "user_id", getIdString("staff", "user_id", "mail", mail)))
                {
                    x = 2;
                }
                else if(dataExists("clerk", "user_id", getIdString("staff", "user_id", "mail", mail)))
                {
                    x = 1;
                }
            }
        }
        else if(dataExists("customer", "mail", mail))
        {
             if(dataExists("customer", "password", password))
             {
                 x = 0;
             }
        }              
        return x;    
    }
    public Clerk insertClerk(Branch branch, String name, String surname, double salary, String mail, String password, String mobilePhone, String companyName)
    {
        Clerk c = null;

        String b = "" + branch.getBranch_id();
        String s = "" + salary;


        String id = "";
        if(dataExists("staff", "mail", mail))
        {
             id = getIdString("staff", "user_id", "mail", mail);
            if(dataExists("clerk", "user_id", id))
            {
               System.out.println("Already exists => " + id);
               int idd = Integer.parseInt(id);
               c =  new Clerk(idd,  branch, name, surname, salary, mail, password, mobilePhone, companyName);
            }
            else
            {
                ArrayList<String> attC = makeArrayList(id, b, companyName);
                insertToQuery("clerk", attC);
                int idd = Integer.parseInt(id);
                c =  new Clerk(idd, branch, name, surname, salary, mail, password, mobilePhone, companyName);
            }
        }
        else
        {
            ArrayList<String> att = makeArrayList(name, surname, s, mail, password, mobilePhone);
            insertToQuery("staff", att);
            id = getIdString("staff", "user_id", "mail", mail);
            ArrayList<String> attC = makeArrayList(id, b, companyName);
            insertToQuery("clerk", attC);
            int idd = Integer.parseInt(id);
            c =  new Clerk(idd, branch, name, surname, salary, mail, password, mobilePhone, companyName);
        }
        //INSERT INTO `staff`(`user_id`, `name`, `surname`, `salary`, `mail`, `password`, `mob_phone`) VALUES 
        return c;
    }
    
    private void insertToQuery(String table_name, ArrayList<String> attributesToAdd)
    {
        PreparedStatement preStt;
        ResultSet rs = null;
        try
        {
            checkConnection();
            ArrayList<String> attNames = new ArrayList<String>();
            rs = statement.executeQuery("SELECT * FROM " + table_name);
            ResultSetMetaData rsmd = rs.getMetaData();
            int expectedAttributeNumber = rsmd.getColumnCount();

            for(int i = 1; i <= expectedAttributeNumber; i++)
            {
                String s = rsmd.getColumnName(i);
                attNames.add(s);
            }
            
            if(expectedAttributeNumber == attributesToAdd.size())
            {

                String q = "INSERT INTO " + table_name + "( ";
                for(int i = 0; i < attNames.size(); i++)
                {
                    if(i == 0)
                        q += attNames.get(i) + " ";
                    else
                        q += ", " + attNames.get(i);
                }
                
                q += ") VALUES ( ";
                for(int i = 0; i < expectedAttributeNumber; i++)
                {
                    if(i == 0)
                        q += "?";
                    else
                        q += ", ?";
                }
                
                q += ")";
                preStt = conn.prepareStatement(q);
                for(int i = 0; i < expectedAttributeNumber; i++)
                {

                    preStt.setString(i + 1, attributesToAdd.get(i));
                }                    
                
                int result = preStt.executeUpdate();
                System.out.println(result);
                //preStt = conn.prepareStatement("INSERT INTO ? (sid, sname, bdate, address, scity, year, gpa ) values (?,?,?,?,?,?,?)
            }
            else if( expectedAttributeNumber - 1 == attributesToAdd.size())
            {
                String q = "INSERT INTO " + table_name + "( ";
                for(int i = 0; i < attNames.size(); i++)
                {
                    if(i == 0)
                    {
                        
                    }
                    else if(i == 1)
                        q += attNames.get(i) + " ";
                    else
                        q += ", " + attNames.get(i);
                }
                
                q += ") VALUES ( ";
                for(int i = 0; i < expectedAttributeNumber; i++)
                {
                    if(i == 0)
                    {
                        
                    }
                    else if(i == 1)
                        q += " ?";
                    else
                        q += ", ?";
                }
                
                q += ")";

                preStt = conn.prepareStatement(q);
                for(int i = 0; i < expectedAttributeNumber - 1; i++)
                {
                    preStt.setString(i + 1, attributesToAdd.get(i));
                }
                int result = preStt.executeUpdate();
                System.out.println(result + " row(s)");
            }
        }
        catch(SQLException e)
        {
            System.out.println("SQLException InsertToQuery");  
            e.printStackTrace();
            if(e instanceof SQLIntegrityConstraintViolationException)
            {
                System.out.println("You can't insert. It is a duplicate entry for " + table_name + " table.");
            }                
        }
    }

    //General purpose executeQuery() method
    public String queryExecute(String query)
    {
        ResultSet rs = null;
        try
        {
            checkConnection();
            if(query!=null && query!="")
            {
                String resultSet="";
                rs = statement.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                int expectedAttributeNumber = rsmd.getColumnCount();
                while(rs.next())
                {
                    for(int i=1; i<=expectedAttributeNumber; i++)
                    {
                        if(i==expectedAttributeNumber)
                            resultSet+=rs.getString(i) + "\n";
                        else
                            resultSet+=rs.getString(i) + "\t";
                    }
                }
                return resultSet;
            }
            else
            {
                    System.out.println("The given query is not valid.");
                    return "";
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("ardbs.DatabaseController.queryExecute() Eception!");
            return "";
        }
    }
    
    public String queryExecute(PreparedStatement ps)
    {
        ResultSet rs = null;
        try
        {
            checkConnection();

            String resultSet="";
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int expectedAttributeNumber = rsmd.getColumnCount();
            while(rs.next())
            {
                for(int i=1; i<=expectedAttributeNumber; i++)
                {
                    if(i==expectedAttributeNumber)
                            resultSet+=rs.getString(i) + "\n";
                    else
                            resultSet+=rs.getString(i) + "\t";
                }
            }
            return resultSet;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("ardbs.DatabaseController.queryExecute() Eception!");
            return "";
        }
    }
    
    public ArrayList<Flight> createFlightListCustomer(String dest_code, String depart_code, String fDate)
    {
        ResultSet rs = null;
        PreparedStatement preStt = null;
        ArrayList<Flight> flights = new ArrayList<Flight>();
        try
        {
            checkConnection();

            try
            {
                checkConnection();
                preStt = conn.prepareStatement("SELECT depart_time, duration, flight_no, bus_price, econ_price\n" 
                                                                        +"FROM flight \n"
                                                                        +"WHERE iata_destination = ? \n"
                                                                        +"AND iata_depart = ? \n"
                                                                        +"AND fdate = ?" );
                preStt.setString(1, dest_code);
                preStt.setString(2, depart_code);
                preStt.setString(3, fDate);
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }   
            rs = preStt.executeQuery();

            String departT;
            int duration;
            String flightNo;
            double busP;
            double econP;
            while(rs.next())
            {      
                //depart_time, duration, flight_no, bus_price, econ_price
                departT = rs.getString(1);
                duration = rs.getInt(2);
                flightNo = rs.getString(3);
                busP = rs.getDouble(4);
                econP = rs.getDouble(5);                                
                Flight f = new Flight(departT, duration, flightNo, busP,econP);
                flights.add(f);
            }            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("ardbs.DatabaseController.queryExecute() Eception!");
        }
        return flights;
    }
    //CREATE TABLE METHODS


    //SELECT * FROM pilot
    public String getAllPilotTable()
    {
        checkConnection();
        String resultSet="";
        resultSet = queryExecute(GET_PILOT_TABLE);
        return resultSet;		
    }

    public String getAllCustomerTable()
    {
        String resultSet="";
        resultSet = queryExecute(GET_CUSTOMER_TABLE);
        return resultSet;		
    }

    public String showAirportsForCustomer()
    {
        checkConnection();
        String resultSet="";
        resultSet = queryExecute(SHOW_AIRPORTS_AS_CUSTOMER);
        return resultSet;
    }
    
    public String showFlightsForCustomer(String dest_code, String depart_code, String fDate)
    {
        PreparedStatement preStt = null;
        try
        {
            checkConnection();
            preStt = conn.prepareStatement("SELECT depart_time, duration, flight_no, bus_price, econ_price\n" 
                                                                    +"FROM flight \n"
                                                                    +"WHERE iata_destination = ? \n"
                                                                    +"AND iata_depart = ? \n"
                                                                    +"AND fdate = ?" );
            preStt.setString(1, dest_code);
            preStt.setString(2, depart_code);
            preStt.setString(3, fDate);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return queryExecute(preStt);                
    }
    
    //Worked
    public Customer addCustomerReg(String password, 
                                    String TCKN, String cname, String csurname, 
                                    String birthday, String mail, String mob_phone)
    {
        ResultSet rs = null;
        PreparedStatement preStt = null;
        //Customer c = new Customer(0, is_register, password, TCKN, cname, csurname, birthday, mail, mob_phone);
        if(!(customerExists(mail)))
        {               
            try
            {
                checkConnection();
                preStt = conn.prepareStatement("INSERT INTO customer(is_register, password, TCKN, cname, csurname, birthday, mail, mob_phone)\n" +
                                               "VALUE (1, ?, ?, ?, ?, ?, ?, ?)");
                preStt.setString(1, password);
                preStt.setString(2, TCKN);
                preStt.setString(3, cname);
                preStt.setString(4, csurname);
                preStt.setString(5, birthday);
                preStt.setString(6, mail);
                preStt.setString(7, mob_phone);

                int resultt = preStt.executeUpdate();
                if(resultt == 1)
                {
                    System.out.println("Registered Customer succesfully added.\n");
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
                System.out.println("ardbs.DatabaseController.addCustomerReg() sqlException");
            }
        }
        else
        {
            System.out.println("This customer already exists!\n");
        }
        
        int id = -1;
        try
        {
            checkConnection();
            preStt = conn.prepareStatement("SELECT customer_id\n" +
                                            "FROM customer\n" +
                                        "WHERE mail = " + mail );
            rs = preStt.executeQuery();
            while(rs.next())
            {
                id= rs.getInt(1);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("ardbs.DatabaseController.addCustumerReg() SQLException prestt");
        }
        
        boolean is_register = true;
        Customer c = new Customer(id, is_register, password, TCKN, cname, csurname, birthday, mail, mob_phone);
        System.out.println( queryExecute("SELECT customer_id, is_register, password, TCKN, cname, csurname, birthday, mail, mob_phone\n" +
                                   "FROM customer\n" +
                                    "WHERE TCKN = " + TCKN ));
        return c;
    }
    
    //Worked
    public Customer addCustomerUnreg(String TCKN, String cname, 
            String csurname, String birthday, String mail,
            String mob_phone)
    {
        PreparedStatement preStt = null;
        Customer c = new Customer(TCKN, cname, csurname, birthday, mail, mob_phone);

        if(customerExists(mail))
        {
            System.out.println("This customer already exists.\n" + TCKN + "\t" + cname + "\t" + csurname);              
        }
        else
        {
            try
            {
                
                checkConnection();
                preStt = conn.prepareStatement("INSERT INTO customer(is_register, TCKN, cname, csurname, birthday, mail, mob_phone)\n" +
                                               "VALUE (0, ?, ?, ?, ?, ?, ?)");
                preStt.setString(1, TCKN);
                preStt.setString(2, cname);
                preStt.setString(3, csurname);
                preStt.setString(4, birthday);
                preStt.setString(5, mail);
                preStt.setString(6, mob_phone);

                int resultt = preStt.executeUpdate();
                if(resultt == 1)
                {
                    System.out.println("Unregistered customer succesfully added.\n");
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
                System.out.println("ardbs.DatabaseController.addCustomerUnreg() sqlException");
            }
        }
        return c;    
    }
    public SystemManager getManager(String mail){
        ResultSet rs = null;
        PreparedStatement preStt = null;
        SystemManager c = null;
        int id = Integer.parseInt(getIdString("staff", "user_id", "mail", mail));
        String name = "", surname = "", password = "", mob_phone = "";
        int pL = -1;
        double salary = -1;
        try{
            preStt = conn.prepareStatement("SELECT * " +
                                                "FROM system_manager NATURAL JOIN staff WHERE system_manager.user_id = ?");
            preStt.setInt(1, id);
            rs = preStt.executeQuery();
            while(rs.next()){
            pL = rs.getInt("permission_level");
            name = rs.getString("name");
            surname = rs.getString("surname");
            password = rs.getString("password");
            mob_phone = rs.getString("mob_phone");
            salary = rs.getDouble("salary");
            }
            c = new SystemManager(id, name, surname, salary, mail, password, mob_phone, pL);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("ardbs.DatabaseController.getManager() SQLException");
        }
        if(c == null)
            System.out.println("Manager is NULL");
        return c;
    }
    //Worked
    public Clerk getClerk(int id){
        PreparedStatement preStt = null;
        ResultSet rs = null;
        Clerk c = null;
        int bId = 0;
        String comName = "", bName = "", name = "", mail = "",
                surname = "", password = "", mob_phone = "";
        double salary = -1;
        try{
            preStt = conn.prepareStatement("SELECT clerk.branch_id, company_name, branch_name " +
                                                "FROM clerk NATURAL JOIN branch WHERE clerk.user_id = ?");
            preStt.setInt(1, id);
            rs = preStt.executeQuery();
            while(rs.next()){
            bId = rs.getInt("branch_id");
            comName = rs.getString("company_name");
            bName = rs.getString("branch_name");
            }
            preStt = conn.prepareStatement("SELECT * " +
                                           "FROM staff" +
                                           " WHERE user_id = ?");
            preStt.setInt(1, id);
            rs = preStt.executeQuery();
            while(rs.next()){
            name = rs.getString("name");
            surname = rs.getString("surname");
            password = rs.getString("password");
            mob_phone = rs.getString("mob_phone");
            salary = rs.getDouble("salary");
            mail = rs.getString("mail");
            }
            Branch b1 = new Branch(bId, bName);
            c = new Clerk(id, b1, name, surname, salary, mail, password, mob_phone, comName);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("ardbs.DatabaseController.getClerk() SQLException");
        }
        return c;
    }
    public Clerk getClerk(String mail){
        PreparedStatement preStt;
        Clerk c = null;
        int id = Integer.parseInt(getIdString("staff", "user_id", "mail", mail));
        int bId = 0;
        String comName = "", bName = "", name = "", 
                surname = "", password = "", mob_phone = "";
        double salary = -1;
        try{
            preStt = conn.prepareStatement("SELECT clerk.branch_id, company_name, branch_name " +
                                                "FROM clerk NATURAL JOIN branch WHERE clerk.user_id = ?");
            preStt.setInt(1, id);
            rs = preStt.executeQuery();
            while(rs.next()){
            bId = rs.getInt("branch_id");
            comName = rs.getString("company_name");
            bName = rs.getString("branch_name");
            }
            preStt = conn.prepareStatement("SELECT * " +
                                           "FROM staff" +
                                           " WHERE user_id = ?");
            preStt.setInt(1, id);
            rs = preStt.executeQuery();
            while(rs.next()){
            name = rs.getString("name");
            surname = rs.getString("surname");
            password = rs.getString("password");
            mob_phone = rs.getString("mob_phone");
            salary = rs.getDouble("salary");
            }
            Branch b1 = new Branch(bId, bName);
            c = new Clerk(id, b1, name, surname, salary, mail, password, mob_phone, comName);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("ardbs.DatabaseController.getClerk() SQLException");
        }
        return c;
    }
    public Customer getCustomer(String mail)
    {
        ResultSet rs = null;
        PreparedStatement preStt = null;
        Customer c = null;
        if(customerExists(mail))
        {
            try
            {
                checkConnection();                    
                try
                {
                    checkConnection();
                    preStt = conn.prepareStatement("SELECT customer_id, is_register, password, TCKN,"+
                                                " cname, csurname, birthday, mail, mob_phone " +
                                                "FROM customer WHERE mail = ?");
                    preStt.setString(1, mail);
                    rs = preStt.executeQuery();
                    boolean isRegBoo = false;
                    while(rs.next())
                    {
                        int isReg = rs.getInt(2);
                        if(isReg == 1)
                        {
                            isRegBoo = true;
                        }
                        c = new Customer(rs.getInt(1), isRegBoo, rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),  rs.getString(9));

                    }
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                    System.out.println("ardbs.DatabaseController.getCustomer() SQLException");
                }
           }
           catch(Exception e)
           {
                e.printStackTrace();
                System.out.println("ardbs.DatabaseController.getCustomer connection Exception!");
           }
        }
        else
        {
            System.out.println("Customer does not exist  with mail = " + mail );
        }
        return c;
    }
    
    private Customer registerNonCustomer(String password, String TCKN, String cname, 
        String csurname, String birthday, String mail,
        String mob_phone)
    {
        Customer c = addCustomerUnreg(TCKN, cname, csurname, birthday, mail, mob_phone);
        if(customerExists(mail))
        {
            c = registerCustomer( password, TCKN, cname, csurname, birthday, mail, mob_phone);
        }
        return c;
    }
    
    public Customer registerCustomer(String password, String TCKN, String cname, 
        String csurname, String birthday, String mail,
        String mob_phone)
    {
        PreparedStatement preStt = null;
        Customer c = null;

        if(customerExists(mail))
        {
            try
            {
               preStt = conn.prepareStatement("UPDATE customer SET password = ?, \n"+
                                               "is_register = 1 WHERE mail = ?");
               preStt.setString(1, password);
               preStt.setString(2, mail);
               preStt.executeUpdate();
               c = getCustomer(mail);
            }
            catch(SQLException e)
            {
               e.printStackTrace();
               System.out.println("ardbs.DatabaseController.register() SQLException");
            }
        }
        else
        {
            c = registerNonCustomer(password, TCKN, cname, csurname, birthday, mail, mob_phone);                
        }
        return c;
    }
    //getid for string ids WORKED
    private String getIdNonString(String table_name, String idName, String ... uniqueAttributes)
    {
        ResultSet rs = null;
        PreparedStatement preStt = null;
        String result = "";
        try
        {
            int counter = 0;
            String stt = "SELECT " + idName + " FROM " + table_name + " WHERE ";
            for(String s : uniqueAttributes)
            {
                if(counter == 0)
                {
                    stt += s + " = ";
                }
                else if(counter % 2 == 1 )
                {
                    stt += s;
                }
                else if(counter % 2 == 0)
                {
                    stt += " AND " + s + " = ";
                }
                counter++;
            }

            preStt = conn.prepareStatement(stt);
            rs = preStt.executeQuery();
            while(rs.next())
            {
                result = rs.getString(1);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("ardbs.DatabaseController.getId() SQLException " + table_name);
        }
        return result;
    }
    
    private String getIdString(String table_name, String idName, String ... uniqueAttributes)
    {
        ResultSet rs = null;
        PreparedStatement preStt = null;
        String result = "";
        try
        {
            int counter = 0;
            String stt = "SELECT " + idName + " FROM " + table_name + " WHERE ";
            for(String s : uniqueAttributes)
            {
                if(counter == 0)
                {
                    stt += s + " = ";
                }
                else if(counter % 2 == 1 )
                {
                    stt += "\"" + s  + "\"";
                }
                else if(counter % 2 == 0)
                {
                    stt += " AND " + s + " = ";
                }
                counter++;
            }

            preStt = conn.prepareStatement(stt);
            rs = preStt.executeQuery();
            while(rs.next())
            {
                result = rs.getString(1);
            }
            //for testing+++
            /*System.out.println("The data that has ");
            int q = 0;
            for(String s : uniqueAttributes){
                if(q == 0){
                    System.out.print(s);
                }
                else if (q == 1){
                    System.out.print(" = " + s);
                }
                else if(q % 2 == 0){
                    System.out.print(", " + s + " = ");
                }
                else{
                    System.out.print(s);
                }
                q++;
            }
            if((result.equals(""))){                    

                System.out.println("Does not exist!");
            }     
            else{
                System.out.println(" has been found for " + idName + " = " + result);
            }*/

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("ardbs.DatabaseController.getId() SQLException " + table_name);
        }
        return result;

    }
    private boolean dataExists(String table_name, String uniqueAttributeName, String uniqueAttribute)
    {
        ResultSet rs = null;
        PreparedStatement preStt = null;
        try
        {
            String result = "";
            preStt = conn.prepareStatement("SELECT " +  uniqueAttributeName + " FROM " + table_name + " WHERE " + uniqueAttributeName + " = ?");
            preStt.setString(1, uniqueAttribute);
            System.out.println(preStt);
            rs = preStt.executeQuery();
            while(rs.next())
            {
                result = rs.getString(1);
            }
            if(!(result.equals("")))
            {                    
                return true;
            }                
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("ardbs.DatabaseController.dataExists() SQLException" + table_name + " and " + uniqueAttribute);
        }
        return false;
    }

    private boolean customerExists(String mail)
    {
        ResultSet rs = null;
        PreparedStatement preStt = null;
        try
        {
            String result = "";
            preStt = conn.prepareStatement("SELECT customer_id FROM customer WHERE mail = ?");
            preStt.setString(1, mail);
            rs = preStt.executeQuery();
            while(rs.next())
            {
                result = rs.getString(1);
            }
            if(!(result.equals("")))
            {
                return true;
            }                
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("ardbs.DatabaseController.customerExists() SQLException");
        }
        return false;
    }
    
    private boolean pilotExists(int pilotId)
    {
        String result = "";
        result = queryExecute("SELECT crew_id\n" +
                                "FROM pilot\n" +
                                "WHERE crew_id = " + pilotId + "");
        if(!(result.equals("")))
        {
            return true;
        }
        return false;
    }

    public Pilot getPilot(int pilotId)
    {
        ResultSet rs = null;
        PreparedStatement preStt = null;
        Pilot p = null;
        if(pilotExists(pilotId))
        {
            try
            {
                checkConnection();       
                try
                {
                    checkConnection(); 
                    preStt = conn.prepareStatement("SELECT crew_id, name, surname, rank, certificate_type " +
                                                "FROM pilot NATURAL JOIN crew WHERE crew_id = ?");
                    preStt.setInt(1, pilotId);
                    rs = preStt.executeQuery();
                    while(rs.next())
                    {
                        p = new Pilot(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                    }    
                }
                catch(SQLException e)
                {
                  e.printStackTrace();
                 System.out.println("ardbs.DatabaseController.getPilot() SQLException");
                }
            }
            catch(Exception s)
            {
                s.printStackTrace();
                System.out.println("ardbs.DatabaseController.getPilot() connection Exception!");
            }
        }
        else
        {
            System.out.println("Pilot does not exist with crew id = " + pilotId);
        }
        return p; 
    }
    
    public String arrivalTime(String flight_id)
    {
        String departureTZstr="";
        String destinationTZstr="";
        int departureTZ=0;
        int destinationTZ=0;
        int duration=0;
        String departTime="";
        java.util.Date fDate=null;
        Calendar cal = Calendar.getInstance();
        
        try
        {
            checkConnection();
            preStt = conn.prepareStatement( "SELECT city.time_zone, flight.duration, flight.depart_time, flight.fdate " 
                                            +"FROM city, airport, flight "
                                            +"WHERE flight.iata_depart = airport.iata_code "
                                            +"AND airport.c_name = city.name "
                                            +"AND airport.country = city.country " 
                                            +"AND flight_id = ?");

            preStt.setString(1, flight_id);
            rs = preStt.executeQuery();
            
            while(rs.next())
            {
                departureTZstr = rs.getString(1);
                duration = rs.getInt(2);
                departTime = rs.getString(3);
                fDate = rs.getDate(4);
            }
            
            if(departureTZstr.length()>3)
            {
                departureTZ = Integer.parseInt(departureTZstr.substring(3));
            }
            
            String flightDate = fDate.toString();

            int year = Integer.parseInt(flightDate.substring(0, 4));
            int month = Integer.parseInt(flightDate.substring(5, 7));
            int day = Integer.parseInt(flightDate.substring(8));
            int hour = Integer.parseInt(departTime.substring(0, 2));
            int minutes = Integer.parseInt(departTime.substring(3, 5));
            int seconds = Integer.parseInt(departTime.substring(6, 8));
            System.out.println(year+" "+month+" "+day+" "+hour +" "+minutes+" "+seconds);

            cal.set(year, month, day, hour, minutes, seconds);
            
            cal.add(Calendar.MINUTE, duration);
            System.out.println(duration);
            System.out.println(departureTZ +" " +duration +" "+" " +fDate+" ");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("SQLException arrivaTime() depart");
        }
        
        try
        {
            checkConnection();
            preStt = conn.prepareStatement( "SELECT city.time_zone " 
                                            +"FROM city, airport, flight "
                                            +"WHERE flight.iata_destination = airport.iata_code "
                                            +"AND airport.c_name = city.name "
                                            +"AND airport.country = city.country " 
                                            +"AND flight_id = ?");

            preStt.setString(1, flight_id);
            rs = preStt.executeQuery();
            
            while(rs.next())
            {
                destinationTZstr = rs.getString(1);                            
            }
            if(departureTZstr.length()>3)
            {
                destinationTZ = Integer.parseInt(destinationTZstr.substring(3));
            }
            cal.add(Calendar.HOUR_OF_DAY, departureTZ-destinationTZ);
            System.out.println(destinationTZ);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("SQLException arrivaTime() dest");
        }
        
        java.util.Date t = cal.getTime();

        return t.toString().substring(0, t.toString().length()-9);
    }
    
    public String readSeatPlan()
    {
        String seatPlan="";
        try
        {
            checkConnection();
            preStt = conn.prepareStatement("SELECT * FROM seatPlan");

            rs = preStt.executeQuery();

            while(rs.next())
            {
                for(int i=1; i<11; i++)
                {
                    if(i==10)
                        seatPlan+=rs.getString(i)+"\n";
                    else
                        seatPlan+=rs.getString(i)+" ";                      
                }        
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("SQLException arrivaTime() dest");
        }
        return seatPlan;
    }
    
    
    private boolean reservationExists(int flightId, int customerId)
    {
        String result = "";
        result = queryExecute("SELECT reservation_no\n" +
                                "FROM reservation\n" +
                                "WHERE flight_id = " + flightId + " and customer_id = " + customerId);
        if(!(result.equals("")))
        {
            return true;
        }
        return false;
    }
    public Reservation insertReservation(int seatCount, int flightId, String mealChoice, Customer customer, Clerk c2){
        Reservation r = insertReservation(seatCount, flightId, mealChoice, customer);
        r = setClerkToReserv(r, c2);
        return r;
    }
    private Reservation setClerkToReserv(Reservation r, Clerk c){
        PreparedStatement preStt = null;
        Reservation s = null;
        try{            
            int rNo = r.getReservation_id();
            int clerkId = c.getUser_id();
            preStt = conn.prepareStatement("UPDATE reservation SET clerk_id = ? WHERE reservation_no = ?");
            preStt.setInt(1, clerkId);
            preStt.setInt(2, rNo);
            int re = preStt.executeUpdate();
            if( re == 1){
                s = r;
                s.setClerk(c);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("DatabaseController.setClerkToReserv() SQLException");
        }
        return s;
    }
    public Reservation insertReservation(int seatCount, int flightId, String mealChoice, Customer customer)
    {
        ResultSet rs = null;
        PreparedStatement preStt = null;
        Reservation r = null; 

        int customerId = customer.getCustomer_id();
        try
        {
            checkConnection();       
            try
            {
                if(!(reservationExists(flightId, customerId )))
                {
                    System.out.println("Customer is not in this flight");
                    checkConnection(); 
                    preStt = conn.prepareStatement("INSERT into reservation(customer_id, flight_id, seat_count, meal_choice)"
                    + " values (?, ?, ?, ?)");
                    preStt.setInt(1, customerId);
                    preStt.setInt(2, flightId);
                    preStt.setInt(3,seatCount);
                    preStt.setString(4, mealChoice);
                    preStt.executeUpdate();
                    preStt = conn.prepareStatement("SELECT reservation_no FROM reservation WHERE flight_id = ? and customer_id = ?");
                    preStt.setInt(1, flightId);
                    preStt.setInt(2, customerId);
                    rs = preStt.executeQuery();
                    while(rs.next()){
                    r = new Reservation(rs.getInt(1), customer, flightId,null , 0, seatCount, mealChoice );   
                    }
                    return r;
                }
                else
                {
                    System.out.println("Customer is in this flight");
                    checkConnection(); 
                    preStt = conn.prepareStatement("SELECT seat_count " +
                    "FROM reservation WHERE flight_id = ? and customer_id = ?");
                    preStt.setInt(1, flightId);
                    preStt.setInt(2, customerId);
                    rs = preStt.executeQuery();
                    int nsC = 0;
                    while(rs.next()){
                    nsC = rs.getInt(1);
                    }
                    preStt = conn.prepareStatement("UPDATE reservation SET seat_count = ? WHERE flight_id = ? and customer_id = ?");
                    preStt.setInt(1, nsC + seatCount);
                    preStt.setInt(2, flightId);
                    preStt.setInt(3, customerId);
                    preStt.executeUpdate();
                    preStt = conn.prepareStatement("SELECT reservation_no FROM reservation WHERE flight_id = ? and customer_id = ?");
                    preStt.setInt(1, flightId);
                    preStt.setInt(2, customerId);
                    rs = preStt.executeQuery();
                    while(rs.next()){
                    r = new Reservation(rs.getInt(1), customer, flightId, null , 0,  seatCount + nsC, mealChoice );   
                    }
                    return r;

                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
                System.out.println("ardbs.DatabaseController.insertReservation() SQLException");
            }
        }
        catch(Exception s)
        {
            s.printStackTrace();
            System.out.println("ardbs.DatabaseController.getinsertReservation() connection Exception!");
        }
        return r;
    }
    public boolean deleteRes(Reservation r){
        PreparedStatement preStt;
        ResultSet rs = null;
        if(reservationExists(r.getFlightId(), r.getCustomer().getCustomer_id())){
            try{
                preStt = conn.prepareStatement("DELETE FROM reserved_seat WHERE res_no = ?");
                preStt.setInt(1, r.getReservation_id());
                preStt.executeUpdate();
                preStt = conn.prepareStatement("DELETE FROM reservation WHERE reservation_no = ?");
                preStt.setInt(1, r.getReservation_id());
                preStt.executeUpdate();
                return true;
            }
            catch(SQLException e){
                e.printStackTrace();
                System.out.println("DatabaseController.RservationListCustomer() SQLException");
            }
        }         
        return false;
    }
    public ArrayList<Reservation> reservationListCustomer(Customer c){
        ResultSet rs = null;
        PreparedStatement preStt = null;
        ArrayList<Reservation> rList = new ArrayList<Reservation>();
        
        try{
            preStt = conn.prepareStatement("SELECT * FROM reservation WHERE customer_id = ?");
            preStt.setInt(1, c.getCustomer_id());
            int rNo = -1;
            int flightId = -1;
            int clerkId = -1;
            int promId = -1;
            int seatC = -1;
            String mealChoice = "";
            rs = preStt.executeQuery();
            //ResultSetMetaData rsmd = rs.getMetaData();
            //int columnCount = rsmd.getColumnCount();
            Reservation r1;
            while(rs.next())
            {
                rNo = rs.getInt(1);
                flightId = rs.getInt(3);
                clerkId = rs.getInt(4);
                promId = rs.getInt(5);
                seatC = rs.getInt(6);
                mealChoice = rs.getString(7);
                Clerk c1;
                   if(clerkId != 0)
                   {
                       c1 = getClerk(clerkId);
                       r1 = new Reservation(rNo, c, flightId, c1, promId, seatC, mealChoice);
                   }
                   else
                   {
                        r1 = new Reservation(rNo, c, flightId, promId, seatC, mealChoice);
                   }
                   //public Reservation(int reservation_id, Customer customer, int flightId, Clerk clerk, int promotion_id, int seat_count, String meal_choice) 
                   
                   rList.add(r1);                
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("DatabaseController.RservationListCustomer() SQLException");
        }
        return rList;
    }
}
