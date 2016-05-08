public class FlightAttendant extends Crew{
    private String duty_type;
    
    FlightAttendant(){
        super();
    }
    FlightAttendant(String duty_type, int crew_id, String name, String surname){
        super(crew_id, name, surname);
        this.duty_type = duty_type;
    }
}
