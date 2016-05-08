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
public abstract class Crew {
    private int crew_id;
    private String name;
    private String surname;
    
    Crew(){
        
    }
    Crew(int crew_id, String name, String surname){
        this.crew_id = crew_id;
        this.name = name;
        this.surname = surname;
    }

    /**
     * @return the crew_id
     */
    public int getCrew_id() {
        return crew_id;
    }

    /**
     * @param crew_id the crew_id to set
     */
    public void setCrew_id(int crew_id) {
        this.crew_id = crew_id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
