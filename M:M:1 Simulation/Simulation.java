//The controller which contains the main function, all global variables
//the main queue and linked lists, printing mechanisms and the while loop and initializer. 
import java.util.*;

public class Simulation { 
    //global vars declarations 
    public static  Scanner a = new Scanner(System.in);
    public static  Scanner b = new Scanner(System.in);
    public static  Scanner c = new Scanner(System.in);
    
    public static double lambda= b.nextDouble();
    public static double Ts= a.nextDouble();
    public static double runTime= c.nextDouble();
    public static double warmUpTime = runTime*2;
    
    public static double globalClock = 0;
    public static boolean emptyServer = true;
 
    public static double sumQ = 0;
    public static double sumW = 0;
    public static double sumOfTq = 0;
    public static double sumOfTw = 0;
    public static double monitorCounter = 0;
    
    public static Calendar calendar = new Calendar();
    public static Queue<Customer> queue = new LinkedList<Customer>();
    public static Queue<Customer> finishedEvents = new LinkedList<Customer>();
    
 // Print Results
    private static void printStats(double lambda, double Ts) {
        int size = finishedEvents.size();
  
        while(!finishedEvents.isEmpty()) {
            Customer temp = finishedEvents.poll(); 
            sumOfTq += temp.turnaroundTime;
            sumOfTw += temp.waitTime;
        }

        System.out.println("Simulator Results");
        System.out.println("q\t=\t" +sumQ/monitorCounter);
        System.out.println("w\t=\t" +sumW/monitorCounter);
        System.out.println("Tq\t=\t" + (sumQ/monitorCounter)/lambda); 
        System.out.println("Tw\t=\t" + (sumW/monitorCounter)/lambda); 
    }
 
    //Main Function
    public static void main(String[] args)  {
        
        //first customer
        Customer first = new Customer(0, "Birth");
        calendar.Enqueue(first);
  
        // START MONITORING
        Customer initMoniter = new Customer(100, "Monitor");
        calendar.Enqueue(initMoniter);
  
        while(globalClock <= warmUpTime) {
            Customer c = calendar.Dequeue();
            globalClock = c.time;
            c.processor();
        }
  
        printStats(lambda,Ts);
    }

} 