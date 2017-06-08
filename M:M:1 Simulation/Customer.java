//Class handles execution of all events (birth, death and monitor) 
import java.util.*;

public class Customer {
    //global vars declarations 
    double TimeOfBirth;
    double waitTime;
    double turnaroundTime;
    double time;
    String type;
    static Random r = new Random();
    
    //Contructors for the Customer class
     public Customer(double time, String type) {
         this.time = time;
         this.type = type;
     }
     
     public Customer(double birth) {
         TimeOfBirth = birth;
         waitTime = 0;  //Tw
         turnaroundTime = 0;  //Tw+ts == Tq
    }
     
     //Helper Functions for processor()
     private static double expoCalc(double x) { 
         return -x * Math.log(1-r.nextDouble());
     }
          
     private static void addNewBirth() {
         Customer newBirth = new Customer(Simulation.globalClock + expoCalc(1/Simulation.lambda), "Birth");
         Simulation.calendar.Enqueue(newBirth);
     }
 
     private static void addNewDeath() {
         Customer newDeath = new Customer(Simulation.globalClock + expoCalc(Simulation.Ts), "Death");
         Simulation.calendar.Enqueue(newDeath);
     }
     
     private static void addNewMonitor() {
         Customer newMonitor = new Customer(Simulation.globalClock + expoCalc(2/Simulation.lambda), "Monitor");
         Simulation.calendar.Enqueue(newMonitor);
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
             if(Simulation.emptyServer == true) {
                 
                 Simulation.emptyServer = false;
                 addNewDeath();
    
                 Customer create = new Customer(Simulation.globalClock);
                 create.waitTime = 0;
                 create.turnaroundTime = expoCalc(Simulation.Ts);
                 Simulation.finishedEvents.add(create);
             }
         else 
             Simulation.queue.add(new Customer(time));
            
         // add Birth event
         addNewBirth();
         }
         
         
         //IF DEATH EVENT
         // -first check that the queue isn't empty 
         // - if it isn't the remove a customer from the waiting queue
         // - then "kill" and add event to the calendar 
         
         // - next, update tracking elements 
         
         // - however, if the server turns out to be empty then --> change state to true
         else if(type.compareTo("Death") == 0)  {
             if(Simulation.queue.isEmpty() == false) {

                 Customer removed = Simulation.queue.poll();
                 addNewDeath();

                 removed.waitTime = Simulation.globalClock-removed.TimeOfBirth;         
                 removed.turnaroundTime = expoCalc(Simulation.Ts);
                 Simulation.finishedEvents.add(removed);
             }
             
             else //server is emptty
                 Simulation.emptyServer = true;
         }
  
  
         //IF MONITER EVENT
         // - basic function of the moniter event is to update sumQ and sumW
         // - start by checking if the server is not empty
         // - if it isn't empty then add the size of the q to sumQ 
         // - then you continue on to add a moniter event. 
         else {   
             // CALCULATE current q and w   
        
             Simulation.monitorCounter++;
             Simulation.sumW += Simulation.queue.size();   
             if(Simulation.emptyServer == false)
                 Simulation.sumQ += Simulation.queue.size() + 1;
   
             addNewMonitor();
         }
   
 }//handler
     
} //class
