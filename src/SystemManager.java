public class SystemManager extends Staff
{
    private int permissionLevel;
    
    public SystemManager()
    {
        super();
        permissionLevel=-1;
    }
    
    public SystemManager(int user_id, String name, String surname, double salary, String mail,String password,  String mobilePhone, int permissionLevel)
    {
        super(user_id, name, surname, salary, mail, password, mobilePhone);
        this.permissionLevel=permissionLevel;
    }
    
    public void display(){
        System.out.println( super.getUser_id() + "\t" + super.getName()
                + "\t" + super.getSurname() + "\t" + super.getSalary() + "\t" + super.getMail() + "\t" +super.getPassword() 
                + "\t" + super.getMobilePhone() + "\t" + permissionLevel);
    }
    
    public int getPermissionLevel() 
    {
        return permissionLevel;
    }

    public void setPermissionLevel(int permissionLevel) 
    {
        this.permissionLevel = permissionLevel;
    }
}
