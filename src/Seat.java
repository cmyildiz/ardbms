
import java.util.Scanner;
public class Seat 
{
    //dsgfsdg
    private String seatNo;
    private boolean isFirstRow;
    private boolean isEcon;
    private boolean isReserved;
    private boolean isWing;
    private boolean isEmergency;
    private boolean isDoor;
    private boolean isSpace;
    private boolean isWC;
    private boolean isSeat;
    private int x;
    private int y;

    public Seat()
    {
        seatNo="";
        x=-1;
        y=-1;
        isFirstRow=false;
        isEcon=false;
        isReserved=false;
        isWing=false;
        isEmergency=false;
        isDoor=false;
        isSpace=false;
        isWC=false;
        isSeat=false;
    }
    
    public Seat(String seatNo, int x, int y)
    {
        this.seatNo=seatNo;
        this.x=x;
        this.y=y;
        isFirstRow=false;
        isEcon=false;
        isReserved=false;
        isWing=false;
        isEmergency=false;
        isDoor=false;
        isSpace=false;
        isWC=false;
        isSeat=false;
        initializer();
    }
    
    private void initializer()
    {
        if(y==0)
        {
            isFirstRow=true;
        }
        else if(x==0)
        {
            if(seatNo.equals("W"))
                isWing=true;
            else if(seatNo.equals("E"))
                isEmergency=true;
        }
        else if(x!=0 && y!=0)
        {
            if(seatNo.equals("W"))
                isWing=true;
            else if(seatNo.equals("E"))
                isEmergency=true;
        }
        else if(seatNo.equals("X"))
            isSpace=true;
        else if(seatNo.equals("WC"))
            isWC=true;
        else if(seatNo.equals("DR"))
            isDoor=true;
        else
            isSeat=true;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    /**
     * @return the isEcon
     */
    public boolean isIsEcon() {
        return isEcon;
    }

    /**
     * @param isEcon the isEcon to set
     */
    public void setIsEcon(boolean isEcon) {
        this.isEcon = isEcon;
    }

    public boolean isIsReserved() {
        return isReserved;
    }

    /**
     * @param isReserved the isReserved to set
     */
    public void setIsReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
    
    public boolean isLeftCol(){
        return isFirstRow && seatNo.equals("LC");
    }
    
    public boolean isRightCol(){
        return isFirstRow && seatNo.equals("RC");
    }
    
    public boolean isFirstRow(){
        return isFirstRow;
    }
    
    public void setFirstRow(){
        isFirstRow=true;
    }
    
    public void setSeat(boolean b){
        isSeat=b;
    }

}
