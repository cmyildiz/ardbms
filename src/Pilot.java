/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardbs;

/**
 *
 * @author NECATİ YILDIZ
 */
public class Pilot extends Crew{
    private int rank;
    private String certificate_type;
    
    Pilot(){
        super();
    }

    public Pilot(int crew_id, String name, String surname, int rank, String certificate_type) {
        super(crew_id, name, surname);
        this.rank = rank;
        this.certificate_type = certificate_type;
    }
    public void displayPilot()
    {
        System.out.println(getCrew_id() + "\t" + getName()  + "\t" + getSurname() + "\t" + rank + "\t" + certificate_type);
    } 
}
