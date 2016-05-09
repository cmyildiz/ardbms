public class Clerk extends Staff
{

    /**
     * @return the companyName
     */
    public static String getCompanyName() {
        return companyName;
    }

    /**
     * @param aCompanyName the companyName to set
     */
    public static void setCompanyName(String aCompanyName) {
        companyName = aCompanyName;
    }
    private Branch branch;
    private static String companyName;
    
    Clerk()
    {
        super();
        branch=null;
        companyName="";
    }
    
    Clerk( int user_id, Branch branch, String name, String surname, double salary, String mail, String password, String mobilePhone, String companyName)
    {
        super(user_id, name, surname, salary, mail, password, mobilePhone);
        this.branch = branch;
        this.companyName=companyName;
    }
    Clerk(Branch branch, String name, String surname, double salary, String mail, String password, String mobilePhone, String companyName)
    {
        super(name, surname, salary, mail, password, mobilePhone);
        this.branch = branch;
        this.companyName=companyName;
    }
    /**
     * @return the branch
     */
    public void display(){
        System.out.println( super.getUser_id() + "\t" + branch.getBranch_id() + "\t" + super.getName()
                + "\t" + super.getSurname() + "\t" + super.getSalary() + "\t" + super.getMail() + "\t" +super.getPassword() 
                + "\t" + super.getMobilePhone() + "\t" + companyName);
    }
    public Branch getBranch() {
        return branch;
    }

    /**
     * @param branch the branch to set
     */
    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    
    
}
