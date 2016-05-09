public class Reservation 
{
    private int reservation_id;
    private Customer customer;
    private int flightId;
    private Clerk clerk;
    private int promotion_id;
    private int seat_count;
    private String meal_choice;
    
    Reservation()
    {
        reservation_id=0;
        customer=null;
        flightId=0;
        clerk=null;
        promotion_id=0;
        seat_count=0;
        meal_choice="";        
    }

    public Reservation(int reservation_id, Customer customer, int flightId, Clerk clerk, int promotion_id, int seat_count, String meal_choice) 
    {
        this.reservation_id = reservation_id;
        this.customer = customer;
        this.flightId = flightId;
        this.clerk = clerk;
        this.promotion_id = promotion_id;
        this.seat_count = seat_count;
        this.meal_choice = meal_choice;
    }
    public Reservation(int reservation_id, Customer customer, int flightId, int promotion_id, int seat_count, String meal_choice) 
    {
        this.reservation_id = reservation_id;
        this.customer = customer;
        this.flightId = flightId;
        this.clerk = null;
        this.promotion_id = promotion_id;
        this.seat_count = seat_count;
        this.meal_choice = meal_choice;
    }
    public void display(){
        System.out.println(reservation_id + "\t " +customer.getCustomer_id() + "\t " + flightId + "\t " + seat_count + "\t " + meal_choice);
    }

    /**
     * @return the reservation_id
     */
    public int getReservation_id() {
        return reservation_id;
    }

    /**
     * @param reservation_id the reservation_id to set
     */
    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the flight
     */
    public int getFlightId() {
        return flightId;
    }

    /**
     * @param flight the flight to set
     */
    public void setFlight(int flight) {
        this.flightId = flight;
    }

    /**
     * @return the clerk
     */
    public Clerk getClerk() {
        return clerk;
    }

    /**
     * @param clerk the clerk to set
     */
    public void setClerk(Clerk clerk) {
        this.clerk = clerk;
    }

    /**
     * @return the promotion_id
     */
    public int getPromotion_id() {
        return promotion_id;
    }

    /**
     * @param promotion_id the promotion_id to set
     */
    public void setPromotion_id(int promotion_id) {
        this.promotion_id = promotion_id;
    }

    /**
     * @return the seat_count
     */
    public int getSeat_count() {
        return seat_count;
    }

    /**
     * @param seat_count the seat_count to set
     */
    public void setSeat_count(int seat_count) {
        this.seat_count = seat_count;
    }

    /**
     * @return the meal_choice
     */
    public String getMeal_choice() {
        return meal_choice;
    }

    /**
     * @param meal_choice the meal_choice to set
     */
    public void setMeal_choice(String meal_choice) {
        this.meal_choice = meal_choice;
    }
    
}
