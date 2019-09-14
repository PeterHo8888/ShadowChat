
public class User {
    private static int id = 0;
    
    private int myID;
    private String name;
    
    public User()
    {
        myID = id++;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public int getID() 
    {
        return myID;
    }
    
    public String getName()
    {
        return name;
    }
}
