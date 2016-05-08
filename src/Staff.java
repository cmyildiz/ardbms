package ardbs;

public abstract class Staff 
{
    private int user_id;
    private String name;
    private String surname;
    private double salary;
    private String mail;
    private String password;
    private String mobilePhone;
    
    Staff(int user_id, String name, String surname, double salary, String mail, String password, String mobilePhone)
    {
        this.user_id = user_id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.mail = mail;
        this.password = password;
        this.mobilePhone = mobilePhone;
    }
    Staff(String name, String surname, double salary, String mail, String password, String mobilePhone)
    {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.mail = mail;
        this.password = password;
        this.mobilePhone = mobilePhone;
    }
    
    Staff()
    {
        user_id=-1;
        name="";
        surname="";
        salary=0;
        mail="";
        mobilePhone="";
    }

    public int getUser_id() {
        return user_id;
    }
    
    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    /**
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
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
     * @return the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
}
