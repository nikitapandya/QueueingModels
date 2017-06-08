//Class handles execution of all events (birth, death and monitor) 
import java.util.*;

public class KCustomer {
    //global vars declarations 
    double TimeOfBirth;
    double waitTime;
    double turnaroundTime;
    double time;
    String type;
    static Random r = new Random();
    double currentQueueSize;
    
    //Contructors for the Customer class
     public KCustomer(double time, String type) {
         this.time = time;
         this.type = type;
     }
     
     public KCustomer(double birth) {
         TimeOfBirth = birth;
         waitTime = 0;  //Tw
         turnaroundTime = 0;  //Tw+ts == Tq
    }
     
     //Helper Functions for processor()
     private static double expoCalc(double x) { 
         return -x * Math.log(1-r.nextDouble());
     }
          
     private static void addNewBirth() {
         KCustomer newBirth = new KCustomer(KSimulation.globalClock + expoCalc(1/KSimulation.lambda), "Birth");
         KSimulation.calendar.Enqueue(newBirth);
     }
 
     private static void addNewDeath() {
         KCustomer newDeath = new KCustomer(KSimulation.globalClock + expoCalc(KSimulation.Ts), "Death");
         KSimulation.calendar.Enqueue(newDeath);
     }
     
     private static void addNewMonitor() {
         KCustomer newMonitor = new KCustomer(KSimulation.globalClock + expoCalc(2/KSimulation.lambda), "Monitor");
         KSimulation.calendar.Enqueue(newMonitor);
     }
  
     // Method for processing BIRTH, DEATH AND MONITOR events 
     public void processor() {
     
     //Condition for if birth event
     //Condition for if death event
     //Condition for if moniter event
     
         //IF BIRTH EVENT: 
         // - first check if server is empty
         // - if it is, change emty server to false and "kill" and add event to the calendar 
         // - next add dead event to list of finished events 
         
         // - if server is not empty == wait in queue & add event to queue
         // - then finally, create and add a new customer to put into the calendar or future events list
         if(type.compareTo("Birth") == 0 ) {
             if(KSimulation.emptyServer == true) {
                 
                 KSimulation.emptyServer = false;
                 addNewDeath();
    
                 KCustomer create = new KCustomer(KSimulation.globalClock);
                 create.waitTime = 0;
                 create.turnaroundTime = expoCalc(KSimulation.Ts);
                 create.currentQueueSize = 0;
                 KSimulation.finishedEvents.add(create);
             }
             else {
                 if (KSimulation.queue.size()+1 <= KSimulation.K) 
                     KSimulation.queue.add(new KCustomer(time));
                 else 
                     KSimulation.drops += 1;
             }
         
          addNewBirth();
             
         }
         
         
         //IF DEATH EVENT
         // -first check that the queue isn't empty 
         // - if it isn't the remove a customer from the waiting queue
         // - then "kill" and add event to the calendar 
         
         // - next, update tracking elements 
         
         // - however, if the server turns out to be empty then --> change state to true
         else if(type.compareTo("Death") == 0)  {
             if(KSimulation.queue.isEmpty() == false) {

                 KCustomer removed = KSimulation.queue.poll();
                 addNewDeath();

                 removed.waitTime = KSimulation.globalClock-removed.TimeOfBirth;         
                 removed.turnaroundTime = expoCalc(KSimulation.Ts);
                 removed.currentQueueSize = KSimulation.queue.size();
                 KSimulation.finishedEvents.add(removed);
             }
             
             else //server is emptty
                 KSimulation.emptyServer = true;
         }
  
  
         //IF MONITER EVENT
         // - basic function of the moniter event is to update sumQ and sumW
         // - start by checking if the server is not empty
         // - if it isn't empty then add the size of the q to sumQ 
         // - then you continue on to add a moniter event. 
         else {   
             // CALCULATE current q and w   
        
             KSimulation.monitorCounter++;
             KSimulation.sumW += KSimulation.queue.size();   
             if(KSimulation.emptyServer == false)
                 KSimulation.sumQ += KSimulation.queue.size() + 1;
   
             addNewMonitor();
         }
   
 }//handler
     
} //class
