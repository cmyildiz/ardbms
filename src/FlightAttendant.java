/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardbs;

/**
 *
 * @author Bugrahan
 */
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
