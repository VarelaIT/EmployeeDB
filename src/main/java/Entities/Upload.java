package Entities;

import java.sql.Timestamp;

public abstract class Upload {
    public String file;
    public int processId;
    public Integer completed;
    public Integer total;
    public Integer inCompletedLines;
    public Timestamp timeStamp;
}
