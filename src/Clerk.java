package ardbs;

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
    
    Clerk(Branch branch, int user_id, String name, String surname, double salary, String mail, String password, String mobilePhone, String companyName)
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
