
package sjf;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//costomized comaparing function with respect to BT
 class BTcomparator implements Comparator<Process> {
    
    public int compare(Process o1, Process o2) {
        if(o1.BT>o2.BT){
            return 1;
        }else if(o1.BT==o2.BT){
            if(o1.AT>o2.AT){
                return 1;
            }else{
                return -1;
            }
        }else{
            return -1;
        }
    }
}

//costomized comaparing function with respect to BT
 class ATcomparator implements Comparator<Process> {
    
    public int compare(Process o1, Process o2) {
        if(o1.AT>o2.AT){
            return 1;
        }else{
            return -1;
        }
    }
}

class Process{
    // Arrival Time
    int AT;
    // Burst Time
    int BT;
    // Waiting Time
    int WT;
    // Dispatch Time
    int DT;
    
    //constructor
    public Process(int AT,int BT){
        this.AT=AT;
        this.BT=BT;
    }
}

public class SJF {
   
    // the main list or process we take from user
   static ArrayList<Process> ProcessList=new ArrayList();
   
   //this will contain the result process in correct order
   static ArrayList<Process> output=new ArrayList(); 
    
   // recursive function
    static int Schedule(int time){
        
        //this will contain all the process that have been arrived till time( The parameter)
        ArrayList<Process> tempList=new ArrayList();
        
        //compare AT of process to check how many of them have arrived till time( the parameter)
        for(int i=0;i<ProcessList.size();i++){
            if(ProcessList.get(i).AT<=time){
                tempList.add(ProcessList.get(i));
            }else{
                break;
            }
        }
        
        // no process has arrived till given time
        if(tempList.size()<1){
        return 0;
        }
        //process has arrived till given time
        else{
            //sort the list with respect to Burst time
            Collections.sort(tempList, new BTcomparator());
            
            //add the first item after sorting i.e the item with lowest BT to output final list
            tempList.get(0).DT=time+tempList.get(0).BT;
            tempList.get(0).WT=time-tempList.get(0).AT;
            output.add(tempList.get(0));
            
            //remove the same item from the current list since it is executed
            ProcessList.remove(tempList.get(0));
            return Schedule(time+tempList.get(0).BT);
        }
    }
    
    static void Display(){
        System.out.println(" AT     |     BT   |    WT   |   DT");
        for(int i=0;i<output.size();i++){
            System.out.println("| "+output.get(i).AT+"      "+output.get(i).BT+"      "+output.get(i).WT+"      "+output.get(i).DT+" |");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("enter number of process");
        Scanner S=new Scanner(System.in);
        int n = S.nextInt();
        
        for(int i=0;i<n;i++){
            System.out.println("enter AT for process:"+i);
            int AT=S.nextInt();
            System.out.println("enter BT for process:"+i);
            int BT=S.nextInt();
            
            Process temp=new Process(AT,BT);
            ProcessList.add(temp);
        }
        System.out.println("Applying SJF process scheduling.......");
        
        Collections.sort(ProcessList, new ATcomparator());
        while(ProcessList.size()>0){
        Schedule(ProcessList.get(0).AT);
        }
        
        Display();
    }
    
}
