
public class Flight {
    private int flight_id;
    private String flight_no;
    private Airport iata_destination;
    private Airport iata_depart;
    private Airplane airplane;
    private String fdate;
    private String depart_time;
    private int reserved_seat_count;
    private int duration;
    private double bus_price;
    private double econ_price;
    private String arrival_time;
    
    Flight(){
        
    }

    public Flight(int flight_id, String flight_no, Airport iata_destination, Airport iata_depart, Airplane airplane, String fdate, String depart_time, int reserved_seat_count, int duration, double bus_price, double econ_price) {
        this.flight_id = flight_id;
        this.flight_no = flight_no;
        this.iata_destination = iata_destination;
        this.iata_depart = iata_depart;
        this.airplane = airplane;
        this.fdate = fdate;
        this.depart_time = depart_time;
        this.reserved_seat_count = reserved_seat_count;
        this.duration = duration;
        this.bus_price = bus_price;
        this.econ_price = econ_price;
    }
    public Flight(String depart_time, int duration,  String flight_no,  double bus_price, double econ_price){
        this.flight_no = flight_no;
        this.depart_time = depart_time;
        this.duration = duration;
        this.bus_price = bus_price;
        this.econ_price = econ_price;
    }

    Flight(int flight_id, String departT, int duration, String flightNo, double busP, double econP) {
        this.flight_id = flight_id;
        this.flight_no = flightNo;
        this.depart_time = departT;
        this.duration = duration;
        this.bus_price = busP;
        this.econ_price =  econP;
    }
    
    public void initializer(Flight f)
    {
        this.flight_id = f.getFlight_id();
        this.flight_no = f.getFlight_no();
        this.depart_time = f.getDepart_time();
        this.duration = f.getDuration();
        this.bus_price = f.getBus_price();
        this.econ_price =  f.getEcon_price();       
    }
    
    public void displayForCustomer(){
            System.out.println(flight_no + "\t" + depart_time + "\t" + duration + "\t" + bus_price + "\t" + econ_price + "\n" );
    }

    
    /*public String arrivalTime(DatabaseController db){
            
            return "";
    }*/
    /**
     * @return the flight_id
     */
    public int getFlight_id() {
        return flight_id;
    }

    /**
     * @param flight_id the flight_id to set
     */
    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    /**
     * @return the flight_no
     */
    public String getFlight_no() {
        return flight_no;
    }

    /**
     * @param flight_no the flight_no to set
     */
    public void setFlight_no(String flight_no) {
        this.flight_no = flight_no;
    }

    /**
     * @return the iata_destination
     */
    public Airport getIata_destination() {
        return iata_destination;
    }

    /**
     * @param iata_destination the iata_destination to set
     */
    public void setIata_destination(Airport iata_destination) {
        this.iata_destination = iata_destination;
    }

    /**
     * @return the iata_depart
     */
    public Airport getIata_depart() {
        return iata_depart;
    }

    /**
     * @param iata_depart the iata_depart to set
     */
    public void setIata_depart(Airport iata_depart) {
        this.iata_depart = iata_depart;
    }

    /**
     * @return the airplane
     */
    public Airplane getAirplane() {
        return airplane;
    }

    /**
     * @param airplane the airplane to set
     */
    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    /**
     * @return the fdate
     */
    public String getFdate() {
        return fdate;
    }

    /**
     * @param fdate the fdate to set
     */
    public void setFdate(String fdate) {
        this.fdate = fdate;
    }

    /**
     * @return the depart_time
     */
    public String getDepart_time() {
        return depart_time;
    }

    /**
     * @param depart_time the depart_time to set
     */
    public void setDepart_time(String depart_time) {
        this.depart_time = depart_time;
    }

    /**
     * @return the reserved_seat_count
     */
    public int getReserved_seat_count() {
        return reserved_seat_count;
    }

    /**
     * @param reserved_seat_count the reserved_seat_count to set
     */
    public void setReserved_seat_count(int reserved_seat_count) {
        this.reserved_seat_count = reserved_seat_count;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the bus_price
     */
    public double getBus_price() {
        return bus_price;
    }

    /**
     * @param bus_price the bus_price to set
     */
    public void setBus_price(double bus_price) {
        this.bus_price = bus_price;
    }

    /**
     * @return the econ_price
     */
    public double getEcon_price() {
        return econ_price;
    }

    /**
     * @param econ_price the econ_price to set
     */
    public void setEcon_price(double econ_price) {
        this.econ_price = econ_price;
    }
    
}
