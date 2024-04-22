package Entities;

public abstract class Upload {
    public String file;
    public int processId;
    public Integer completedLines;
    public Integer inCompletedLines;
    public java.sql.Date timeStamp;
    public boolean done = false;
}
