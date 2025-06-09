package Premptive;
import java.util.*;

public class RoundRobin {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] arrStr = sc.nextLine().split(" ");
        String[] burstStr = sc.nextLine().split(" ");
        int quantum = sc.nextInt();
        int n = arrStr.length;
        
        Process[] processes = new Process[n];
        for(int i=0;i<n;i++){
            int arrival = Integer.parseInt(arrStr[i]);
            int burst = Integer.parseInt(burstStr[i]);
            processes[i] = new Process(arrival, burst);
        }

        Arrays.sort(processes , (p1 , p2)->p1.arrival-p2.arrival);

        Queue<Process> queue = new LinkedList<>();
        int currentTime = 0, complete = 0;
        while(complete < n){
            for(Process p : processes){
                if(!p.visited && p.arrival<=currentTime){
                    queue.add(p);
                    p.visited = true;

                }
            }
            if(queue.isEmpty()){
                currentTime++;
                continue;
            }

            Process p = queue.poll();
            if(p.start == -1) p.start = currentTime;

            int execTime = Math.min(quantum , p.remaining);
            currentTime += execTime;
            p.remaining -= execTime;

            for(Process process : processes){
                if(!process.visited && process.arrival<=currentTime){
                    queue.add(process);
                    process.visited = true;

                }
            }
            if(p.remaining > 0){
                queue.add(p);
            }
            else{
                p.complete = currentTime;
                p.tat = p.complete - p.arrival;
                p.wait = p.tat - p.burst;
                complete++;
            }
        }
                int totalTat = 0 , totalWait = 0;
        for(Process process : processes){
            totalTat += process.tat;
            totalWait += process.wait;
        }
        System.out.println(totalWait / n);
        System.out.println(totalTat / n);

    }

    static class Process{
        int arrival , burst , start = -1 , complete , remaining , tat , wait;
        boolean visited = false;
        Process(int arrival , int burst){
            this.arrival =arrival;
            this.burst = burst;
            this.remaining = burst;
        }


    }
}