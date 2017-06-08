M/M/1/K:
    
TO RUN:
RUN THE KSIMULATION.java and 
INPUT K, LAMBDA, SERVICE TIME, AND RUN TIME - in this order

KCalendar.java:
It is a class that creates the MM1 queue schedule of events like births,
deaths and monitors. It takes care of adding the events in an ascending
order to the calendar linked list. The class uses enqueue and dequeue method.

KCustomer.java
 In this class, the events (births, deaths, monitors) times are being calculated.
They are the requests that get added to the calendar to determine
the birth and death or events. The constructor initializes the events with two attributes:
the time the request gets generated (global request at that moment), and the type of the event
(Birth, Death, Monitor)THE THING THAT CHANGES FROM M/M/1 IS THAT NOW, WE CHECK IF THERE IS SPACE IN
BUFFER. WE DO SO BY ADDING THE EVENT ONLY IF QUEUE SIZE +1 < K (K IS GIVEN).
    
KSimulation.java:
This class is the simulation/controller of the MM1 queue. We first initializes the system (using the initializer method). We also have a linked list of all the customers that passed the system (deadcustomers)
so that we can keep track of the total number of customers in each simulation (excluding the monitor events)
Finally, we have a method that prints out the results.


M/D/1/K

TO RUN:
    
RUN THE DKSIMULATION.java and 
INPUT K, LAMBDA, SERVICE TIME, AND RUN TIME - in this order


The calendar file remains the same as above. However, DKCustomer.java has been modified.
The functioning remains the same as explained above but the exponential time has been changed by the
service time which is given as the input. The service time is constant.
Due to Ts constant, service time, q and tq are also constant.

DKSimulation.java is almost the same as the above simulation in M/M/1/K except the service 
time remains constant unlike the exponential service time. 