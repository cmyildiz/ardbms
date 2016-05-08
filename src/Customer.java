/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardbs;

import static java.sql.JDBCType.NULL;

/**
 *
 * @author NECATİ YILDIZ
 */
public class Customer {
    private int customer_id;
    private boolean is_register;
    private String password;
    private String TCKN;
    private String cname;
    private String csurname;
    private String birthday;
    private String mail;
    private String mob_phone;
    
    Customer(){
        
    }

    public Customer(int customer_id, boolean is_register, String password, String TCKN, String cname, String csurname, String birthday, String mail, String mob_phone) {
        this.customer_id = customer_id;
        this.is_register = is_register;
        this.password = password;
        this.TCKN = TCKN;
        this.cname = cname;
        this.csurname = csurname;
        this.birthday = birthday;
        this.mail = mail;
        this.mob_phone = mob_phone;
    }
    
     public Customer( String TCKN, String cname, String csurname, String birthday, String mail, String mob_phone) {
        this.customer_id = customer_id;
        this.is_register = false;
        this.password = null;
        this.TCKN = TCKN;
        this.cname = cname;
        this.csurname = csurname;
        this.birthday = birthday;
        this.mail = mail;
        this.mob_phone = mob_phone;
    }
    
     public void display(){
         if(this == null){
             System.out.println("This Customer is NULL");
         }
         else{
            if(is_register)
               System.out.println(customer_id + "\t"+ password + "\t" + TCKN + "\t" + cname + "\t" + csurname + "\t" + birthday + "\t" + mail + "\t"  + mob_phone);
            else
                System.out.println(customer_id + "\t"+ "Unregistered" + "\t" + TCKN + "\t" + cname + "\t" + csurname + "\t" + birthday + "\t" + mail + "\t"  + mob_phone);
     
         }
     }
    /**
     * @return the customer_id
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * @param customer_id the customer_id to set
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * @return the is_register
     */
    public boolean isIs_register() {
        return is_register;
    }

    /**
     * @param is_register the is_register to set
     */
    public void setIs_register(boolean is_register) {
        this.is_register = is_register;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the TCKN
     */
    public String getTCKN() {
        return TCKN;
    }

    /**
     * @param TCKN the TCKN to set
     */
    public void setTCKN(String TCKN) {
        this.TCKN = TCKN;
    }

    /**
     * @return the cname
     */
    public String getCname() {
        return cname;
    }

    /**
     * @param cname the cname to set
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * @return the csurname
     */
    public String getCsurname() {
        return csurname;
    }

    /**
     * @param csurname the csurname to set
     */
    public void setCsurname(String csurname) {
        this.csurname = csurname;
    }

    /**
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the mob_phone
     */
    public String getMob_phone() {
        return mob_phone;
    }

    /**
     * @param mob_phone the mob_phone to set
     */
    public void setMob_phone(String mob_phone) {
        this.mob_phone = mob_phone;
    }
}
