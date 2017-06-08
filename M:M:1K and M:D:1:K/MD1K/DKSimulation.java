//The controller which contains the main function, all global variables
//the main queue and linked lists, printing mechanisms and the while loop and initializer. 
import java.util.*;

public class DKSimulation { 
    //global vars declarations 
    public static  Scanner a = new Scanner(System.in);
    public static  Scanner b = new Scanner(System.in);
    public static  Scanner c = new Scanner(System.in);
    public static  Scanner d = new Scanner(System.in);
    
    public static double K= d.nextInt();
    public static double lambda= b.nextDouble();
    public static double Ts= a.nextDouble();
    public static double runTime= c.nextDouble();
    public static double warmUpTime = runTime*2;
    
    public static double globalClock = 0;
    public static boolean emptyServer = true;
    
    public static double Q = 0;
    public static double TQ = 0;
    public static double W = 0;
    public static double TW = 0;
 
    public static double sumQ = 0;
    public static double sumW = 0;
    public static double sumOfTq = 0;
    public static double sumOfTw = 0;
    public static double monitorCounter = 0;
    public static double rho = lambda*Ts;
    public static double drops = 0;
    
    public static double zScore = 1.96;
    public static double sumTQsquared = 0;
    public static double sdTQ = 0;
    public static double ciTQ = 0;
    public static double queueSizesqrd = 0;
    public static double sdQ = 0;
    public static double ciQ = 0;
    
    public static DKCalendar calendar = new DKCalendar();
    public static Queue<DKCustomer> queue = new LinkedList<DKCustomer>();
    public static Queue<DKCustomer> finishedEvents = new LinkedList<DKCustomer>();
    
 // Print Results
    private static void printStats(double lambda, double Ts) {
        int size = finishedEvents.size();
  
        while(!finishedEvents.isEmpty()) {
            DKCustomer temp = finishedEvents.poll(); 
            sumOfTq += temp.turnaroundTime;
            sumOfTw += temp.waitTime;
            sumTQsquared += Math.pow(temp.turnaroundTime,2);
            queueSizesqrd += Math.pow(temp.currentQueueSize,2);
        }
        
        Q = sumQ/monitorCounter;
        W = sumW/monitorCounter;
        TQ = (sumQ/monitorCounter)/lambda;
        TW = (sumW/monitorCounter)/lambda;

        System.out.println("Simulator Results");
        System.out.println("q\t=\t" + Q);
        System.out.println("w\t=\t" + W);
        System.out.println("Tq\t=\t" + TQ); 
        System.out.println("Tw\t=\t" + TW); 
        
        System.out.println("         ");
        
        System.out.println("Number of Total Events: " + size);
        System.out.println("Number of drops aka Rejection Probability: " + drops/size);
        
        System.out.println("         ");
        
        //CONFIDENCE INTERVAL TQ
        sdTQ = Math.sqrt((sumTQsquared - Math.pow(TQ,2) / (size-1)));
        ciTQ = (zScore * (sdTQ/Math.sqrt(size)));
        System.out.println("Confidence interval of Tq: " + "[" + (TQ-ciTQ) + "," + (TQ+ciTQ) +"]");
        
        //CONFIDENCE INTERVAL Q
        sdQ = Math.sqrt((queueSizesqrd - Math.pow(Q,2) / (size-1)));
        ciQ = zScore * (sdQ/Math.sqrt(size));
        System.out.println("Confidence interval of q: " + "[" + (Q-ciQ) + "," + (Q+ciQ) +"]");
    }
 
    //Main Function
    public static void main(String[] args)  {
        
        //first customer
        DKCustomer first = new DKCustomer(0, "Birth");
        calendar.Enqueue(first);
  
        // START MONITORING
        DKCustomer initMoniter = new DKCustomer(100, "Monitor");
        calendar.Enqueue(initMoniter);
  
        while(globalClock <= warmUpTime) {
            DKCustomer c = calendar.Dequeue();
            globalClock = c.time;
            c.processor();
        }
  
        printStats(lambda,Ts);
    }

} 