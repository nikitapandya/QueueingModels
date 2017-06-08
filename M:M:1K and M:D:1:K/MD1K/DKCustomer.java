//Class handles execution of all events (birth, death and monitor) 
import java.util.*;

public class DKCustomer {
    //global vars declarations 
    double TimeOfBirth;
    double waitTime;
    double turnaroundTime;
    double time;
    String type;
    static double constantServiceTime = DKSimulation.Ts;
    static Random r = new Random();
    double currentQueueSize; 
    
    //Contructors for the Customer class
     public DKCustomer(double time, String type) {
         this.time = time;
         this.type = type;
     }
     
     public DKCustomer(double birth) {
         TimeOfBirth = birth;
         waitTime = 0;  //Tw
         turnaroundTime = 0;  //Tw+ts == Tq
    }
     
     //Helper Functions for processor()
     /*private static double expoCalc(double x) { 
         return -x * Math.log(1-r.nextDouble());
     }*/
     
     private static void addNewBirth() {
         DKCustomer newBirth = new DKCustomer(DKSimulation.globalClock + constantServiceTime , "Birth"); //expoCalc(1/DKSimulation.lambda)
         DKSimulation.calendar.Enqueue(newBirth);
     }
 
     private static void addNewDeath() {
         DKCustomer newDeath = new DKCustomer(DKSimulation.globalClock + constantServiceTime, "Death"); //expoCalc(DKSimulation.Ts)
         DKSimulation.calendar.Enqueue(newDeath);
     }
     
     private static void addNewMonitor() {
         DKCustomer newMonitor = new DKCustomer(DKSimulation.globalClock + constantServiceTime, "Monitor"); //expoCalc(2/DKSimulation.lambda)
         DKSimulation.calendar.Enqueue(newMonitor);
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
             if(DKSimulation.emptyServer == true) {
                 
                 DKSimulation.emptyServer = false;
                 addNewDeath();
    
                 DKCustomer create = new DKCustomer(DKSimulation.globalClock);
                 create.waitTime = 0;
                 create.turnaroundTime = constantServiceTime;//expoCalc(DKSimulation.Ts);
                 create.currentQueueSize = 0;
                 DKSimulation.finishedEvents.add(create);
             }
             else {
                 if (DKSimulation.queue.size()+1 <= DKSimulation.K) 
                     DKSimulation.queue.add(new DKCustomer(time));
                 else 
                     DKSimulation.drops += 1;
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
             if(DKSimulation.queue.isEmpty() == false) {

                 DKCustomer removed = DKSimulation.queue.poll();
                 addNewDeath();

                 removed.waitTime = DKSimulation.globalClock-removed.TimeOfBirth;         
                 removed.turnaroundTime = constantServiceTime; //expoCalc(DKSimulation.Ts);
                 removed.currentQueueSize = DKSimulation.queue.size() +1;
                 DKSimulation.finishedEvents.add(removed);
             }
             
             else //server is emptty
                 DKSimulation.emptyServer = true;
         }
  
  
         //IF MONITER EVENT
         // - basic function of the moniter event is to update sumQ and sumW
         // - start by checking if the server is not empty
         // - if it isn't empty then add the size of the q to sumQ 
         // - then you continue on to add a moniter event. 
         else {   
             // CALCULATE current q and w   
        
             DKSimulation.monitorCounter++;
             DKSimulation.sumW += DKSimulation.queue.size();   
             if(DKSimulation.emptyServer == false)
                 DKSimulation.sumQ += DKSimulation.queue.size() + 1.0;
   
             addNewMonitor();
         }
   
 }//handler
     
} //class
