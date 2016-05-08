
import java.util.*;
import java.sql.*;

public class DatabaseController 
{
	private static final String JDBC_DRIVER ="com.mysql.jdbc.Driver";
	private static final  String URL = "jdbc:mysql://localhost:3306/ardbs";
	private static final String USERNAME = "root";
	private static final  String PASSWORD = "asdasdas9";
	private  Connection conn;
	protected  String sqlCommand;
	protected  Statement statement;
	protected  ResultSet rs;
	private  PreparedStatement preStt;
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
		try
		{
			conn.close ();
			statement.close ();
			preStt.close ();
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
        private ArrayList<String> makeArrayList(String ... strings){
            ArrayList<String> att = new ArrayList<String>();
            for(String s : strings){
                att.add(s);
            }
            return att;
        }
        public Clerk insertClerk(Branch branch, String name, String surname, double salary, String mail, String password, String mobilePhone, String companyName){
            Clerk c = new Clerk(branch, name, surname, salary, mail, password, mobilePhone, companyName);
            
            String b = "" + branch.getBranch_id();
            String s = "" + salary;
            
            ArrayList<String> att = makeArrayList(name, surname, s, mail, password, mobilePhone);
            insertToQuery("staff", att);
            if(!(dataExists("staff", "mail", mail))){
                String id = getIdString("staff", "user_id", "mail", mail);
                
            }
            else{
                System.out.println("Insertion failed to staff");
                return null;
            }
            //INSERT INTO `staff`(`user_id`, `name`, `surname`, `salary`, `mail`, `password`, `mob_phone`) VALUES 
            return c;
        }
        private void insertToQuery(String table_name, ArrayList<String> attributesToAdd){
            try{
                checkConnection();
                ArrayList<String> attNames = new ArrayList<String>();
                rs = statement.executeQuery("SELECT * FROM " + table_name);
                ResultSetMetaData rsmd = rs.getMetaData();
                int expectedAttributeNumber = rsmd.getColumnCount();
                
                for(int i = 1; i <= expectedAttributeNumber; i++){
                    String s = rsmd.getColumnName(i);
                    attNames.add(s);
                }
                if(expectedAttributeNumber == attributesToAdd.size()){
                    
                    String q = "INSERT INTO " + table_name + "( ";
                    for(int i = 0; i < attNames.size(); i++){
                        if(i == 0)
                            q += attNames.get(i) + " ";
                        else
                            q += ", " + attNames.get(i);
                    }
                    q += ") VALUES ( ";
                    for(int i = 0; i < expectedAttributeNumber; i++){
                        if(i == 0)
                            q += "?";
                        else
                            q += ", ?";
                    }
                    q += ")";
                    preStt = conn.prepareStatement(q);
                    for(int i = 0; i < expectedAttributeNumber; i++){
                        
                        preStt.setString(i + 1, attributesToAdd.get(i));
                    }                    
                    int result = preStt.executeUpdate();
                    System.out.println(result);
                    //preStt = conn.prepareStatement("INSERT INTO ? (sid, sname, bdate, address, scity, year, gpa ) values (?,?,?,?,?,?,?)
                }
                else if( expectedAttributeNumber - 1 == attributesToAdd.size()){
                    String q = "INSERT INTO " + table_name + "( ";
                    for(int i = 0; i < attNames.size(); i++){
                        if(i == 0){
                        }
                        else if(i == 1)
                            q += attNames.get(i) + " ";
                        else
                            q += ", " + attNames.get(i);
                    }
                    q += ") VALUES ( ";
                    for(int i = 0; i < expectedAttributeNumber; i++){
                        if(i == 0){
                        }
                        else if(i == 1)
                            q += " ?";
                        else
                            q += ", ?";
                    }
                    q += ")";
                    
                    preStt = conn.prepareStatement(q);
                    for(int i = 0; i < expectedAttributeNumber - 1; i++){
                        preStt.setString(i + 1, attributesToAdd.get(i));
                    }
                    int result = preStt.executeUpdate();
                    System.out.println(result);
                }
                
            }
            catch(SQLException e){
                System.out.println("SQLException InsertToQuery");        
                if(e instanceof SQLIntegrityConstraintViolationException)
                    {
                            System.out.println("You can't insert. It is a duplicate entry for " + table_name + "table.");
                    }                
            }
        }
	 
	//General purpose executeQuery() method
	public String queryExecute(String query)
	{
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
	public ArrayList<Flight> createFlightListCustomer(String dest_code, String depart_code, String fDate){
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
	
	
        
        public String showAirportsForCustomer(){
             checkConnection();
			String resultSet="";
                        resultSet = queryExecute(SHOW_AIRPORTS_AS_CUSTOMER);
                        return resultSet;
        }
        public String showFlightsForCustomer(String dest_code, String depart_code, String fDate){
                                
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
                                        String birthday, String mail, String mob_phone){
            //Customer c = new Customer(0, is_register, password, TCKN, cname, csurname, birthday, mail, mob_phone);
            if(!(customerExists(mail))){               
                try{
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
                    if(resultt == 1){
                        System.out.println("Registered Customer succesfully added.\n");
                    }
                }
                catch(SQLException e){
                    e.printStackTrace();
                    System.out.println("ardbs.DatabaseController.addCustomerReg() sqlException");
                }
            }
            else{
                System.out.println("This customer already exists!\n");
            }
            int id = -1;
            try{
                checkConnection();
                preStt = conn.prepareStatement("SELECT customer_id\n" +
                                                "FROM customer\n" +
                                            "WHERE mail = " + mail );
                rs = preStt.executeQuery();
               
                while(rs.next()){
                    id= rs.getInt(1);
                }
            }
            catch(SQLException e){
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
                String mob_phone){
            Customer c = new Customer(TCKN, cname, csurname, birthday, mail, mob_phone);
            
            if(customerExists(mail)){
                System.out.println("This customer already exists.\n" + TCKN + "\t" + cname + "\t" + csurname);              
                
            }
            else{
                try{
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
                    if(resultt == 1){
                        System.out.println("Unregistered customer succesfully added.\n");
                    }
                }
                catch(SQLException e){
                    e.printStackTrace();
                    System.out.println("ardbs.DatabaseController.addCustomerUnreg() sqlException");
                }
            }
                     
            return c;    
        }
        //Worked
        public Customer getCustomer(String mail){
            Customer c = null;
             if(customerExists(mail)){
                
                 try{
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
                        while(rs.next()){
                            int isReg = rs.getInt(2);
                            if(isReg == 1){
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
             else{
                 System.out.println("Customer does not exist  with mail = " + mail );
            }
             return c;
        }
        private Customer registerNonCustomer(String password, String TCKN, String cname, 
            String csurname, String birthday, String mail,
            String mob_phone){
            Customer c = addCustomerUnreg(TCKN, cname, csurname, birthday, mail, mob_phone);
            if(customerExists(mail)){
                c = registerCustomer( password, TCKN, cname, csurname, birthday, mail, mob_phone);
            }
            return c;
        }
        public Customer registerCustomer(String password, String TCKN, String cname, 
            String csurname, String birthday, String mail,
            String mob_phone){
            Customer c = null;

            if(customerExists(mail)){
                 try{
                    preStt = conn.prepareStatement("UPDATE customer SET password = ?, \n"+
                                                    "is_register = 1 WHERE mail = ?");
                    preStt.setString(1, password);
                    preStt.setString(2, mail);
                    preStt.executeUpdate();
                    c = getCustomer(mail);
                 }
                 catch(SQLException e){
                    e.printStackTrace();
                    System.out.println("ardbs.DatabaseController.register() SQLException");
                 }
            }
            else{
                c = registerNonCustomer(password, TCKN, cname, csurname, birthday, mail, mob_phone);                
            }
            return c;
        }
        //getid for string ids WORKED
        private String getIdNonString(String table_name, String idName, String ... uniqueAttributes)
        {
            String result = "";
            try
            {
                int counter = 0;
                String stt = "SELECT " + idName + " FROM " + table_name + " WHERE ";
                for(String s : uniqueAttributes){
                    if(counter == 0){
                        stt += s + " = ";
                    }
                    else if(counter % 2 == 1 ){
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
                while(rs.next()){
                    result = rs.getString(1);
                }
                     
            }
            catch(SQLException e){
                e.printStackTrace();
                System.out.println("ardbs.DatabaseController.getId() SQLException " + table_name);
            }
            return result;
            
        }
        private String getIdString(String table_name, String idName, String ... uniqueAttributes)
        {
            String result = "";
            try
            {
                int counter = 0;
                String stt = "SELECT " + idName + " FROM " + table_name + " WHERE ";
                for(String s : uniqueAttributes){
                    if(counter == 0){
                        stt += s + " = ";
                    }
                    else if(counter % 2 == 1 ){
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
                while(rs.next()){
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
            catch(SQLException e){
                e.printStackTrace();
                System.out.println("ardbs.DatabaseController.getId() SQLException " + table_name);
            }
            return result;
            
        }
        private boolean dataExists(String table_name, String uniqueAttributeName, String uniqueAttribute){
            try
            {
                String result = "";
                preStt = conn.prepareStatement("SELECT " + table_name + " FROM customer WHERE " + uniqueAttributeName + " = ?");
                preStt.setString(1, uniqueAttribute);
                rs = preStt.executeQuery();
                while(rs.next()){
                    result = rs.getString(1);
                }
                if(!(result.equals(""))){
                    return true;
                }                
                
            }
            catch(SQLException e){
                e.printStackTrace();
                System.out.println("ardbs.DatabaseController.dataExists() SQLException" + table_name + " and " + uniqueAttribute);
            }
            return false;
        }
        
        private boolean customerExists(String mail){
            try
            {
                String result = "";
                preStt = conn.prepareStatement("SELECT customer_id FROM customer WHERE mail = ?");
                preStt.setString(1, mail);
                rs = preStt.executeQuery();
                while(rs.next()){
                    result = rs.getString(1);
                }
                if(!(result.equals(""))){
                    return true;
                }                
                
            }
            catch(SQLException e){
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
            if(!(result.equals(""))){
                return true;
            }
            return false;
        }
         
        public Pilot getPilot(int pilotId)
        {
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
        /*public int arrivalTime(String flight_id){
            try{
                checkConnection();
                preStt = conn.prepareStatement("SELECT city.time_zone, flight.duration, flight.depart_time FROM city, airport, flight\n" +
                                               "WHERE flight.iata_depart = airport.iata_code\n" +
                                               "AND airport.city = city.name \n" +
                                               "AND aiport.country = city.country\n"+ 
                                               "AND flight_id = ?");
			preStt.setString(1, flight_id);
                        rs = preStt.executeQuery();
                        String depart = "";
                        int depTZ = 1000;
                        int duration;
                        Time depart_time;
                        
                        while(rs.next()){
                            depart = rs.getString(1);
                            duration = rs.getInt(2);
                            depart_time = rs.getTime(3);
                        }
			if(depart.length() < 5){
                            depTZ = 0;
                        }
                        else{
                            depTZ = Integer.parseInt(depart.substring(4, 5));
                        }
                        
            }
            catch(SQLException e){
                e.printStackTrace();
                System.out.println("SQLException arrivaTime() depart");
            }
             try{
                checkConnection();
                preStt = conn.prepareStatement("SELECT city.time_zone FROM city, airport, flight\n" +
                                               "WHERE flight.iata_destination = airport.iata_code\n" +
                                               "AND airport.city = city.name \n" +
                                               "AND aiport.country = city.country\n"+ 
                                               "AND flight_id = ?");
			preStt.setString(1, flight_id);
                        rs = preStt.executeQuery();
                        String dest = "";
                        int destTZ = 1000;
                        
                        while(rs.next()){
                            dest = rs.getString(1);                            
                        }
			if(dest.length() < 5){
                            destTZ = 0;
                        }
                        else{
                            destTZ = Integer.parseInt(dest.substring(4, 5));
                        }
                        
            }
            catch(SQLException e){
                e.printStackTrace();
                System.out.println("SQLException arrivaTime() dest");
            }
            return 0;
        }*/
        private boolean reservationExists(int flightId, int customerId)
        {
            String result = "";
            result = queryExecute("SELECT reservation_no\n" +
                                    "FROM reservation\n" +
                                    "WHERE flight_id = " + flightId + " and customer_id = " + customerId);
            if(!(result.equals(""))){
                return true;
            }
            return false;
        }
          
        public Reservation insertReservation(int seatCount, int flightId, String mealChoice, Customer customer)
        {
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
}
