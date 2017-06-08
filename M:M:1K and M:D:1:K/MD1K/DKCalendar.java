//This class contains a "calendar" or "schedule" of events which is a linked list of "future" events.
//Also in this class, is the Enqueue and Dequeue functions. 

import java.util.*;

public class DKCalendar {
    
    public static LinkedList<DKCustomer> calendar = new LinkedList<DKCustomer>();
 
    //Enqueue the event into Queue
    public static void Enqueue(DKCustomer cust1) {    
        for(int i=0; i<calendar.size(); i++) {
            if(calendar.get(i).time >= cust1.time) {
                calendar.add(i, cust1);
                return;
            }     
        }   
        calendar.addLast(cust1); //adds to the end of the queue
    }
 
    //Dequeue the Queue
    public static DKCustomer Dequeue()  {
        return calendar.poll();
    }
 
}
 
 
 

