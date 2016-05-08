package ardbs;
import java.util.ArrayList;
import java.util.Scanner;


public class Airplane 
{
    private ArrayList<Seat> seatList;
    private int airplane_id;
    private String company;
    private String model;
    private int seatCount;
    private int rank;

    Airplane()
    {
        seatList = null;
        airplane_id=0;
        company="";
        model="";
        seatCount=0;
        rank=-1;
    }

    Airplane(int airplane_id, String company, String model, int seat_count, int rank)
    {
        this.airplane_id = airplane_id;
        this.company = company;
        this.model = model;
        this.seatCount = 0;
        this.rank = rank;
        seatList=new ArrayList<Seat>();
    }
        
    public void addSeatToSeatPlan(Seat s)
    {
        getSeatList().add(s);
        seatCount++;
    }
    
    

    /**
     * @return the seatList
     */
    public ArrayList<Seat> getSeatList() {
        return seatList;
    }

    /**
     * @param seatList the seatList to set
     */
    public void setSeatList(ArrayList<Seat> seatList) {
        this.seatList = seatList;
    }

    /**
     * @return the airplane_id
     */
    public int getAirplane_id() {
        return airplane_id;
    }

    /**
     * @param airplane_id the airplane_id to set
     */
    public void setAirplane_id(int airplane_id) {
        this.airplane_id = airplane_id;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the seat_count
     */
    public int getSeatCount() {
        return seatCount;
    }

    /**
     * @param seat_count the seat_count to set
     */
    public void setSeat_count(int seat_count) {
        this.seatCount = seat_count;
    }

    /**
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(int rank) {
        this.rank = rank;
    }
    
    public void readSeatPlan(String seatPlanStr, int colCount)
    {
        Scanner scan = new Scanner(seatPlanStr);
        Scanner rowScanner;
        String rowStr="";
        String seatStr="";
        int x = -1;
        int y = -1;
        
        while(scan.hasNextLine())
        {
            y++;
            rowStr=scan.nextLine();
            
            rowScanner = new Scanner(rowStr);
            while(rowScanner.hasNext())
            {
                x++;
                seatStr=rowScanner.next();
                Seat s = new Seat(seatStr, x, y);
                if(y==0 && x==colCount)
                    s.setFirstRow();
                else if(x==colCount && Character.isDigit(seatStr.charAt(0)))
                    s.setSeat(false);
            }
            x=0;
        }
    }
}

