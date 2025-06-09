package Premptive;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class PrioritySchedulingPremp {
    public static void main(String[] args) {
           Scanner sc = new Scanner(System.in);
        String[] arrivalStr = sc.nextLine().split(" ");
        String[] burstStr = sc.nextLine().split(" ");
         String[] priorityStr = sc.nextLine().split(" ");
        int n = arrivalStr.length;
        Process[] processes = new Process[n];
        for(int i=0;i<n;i++){
            int arrival = Integer.parseInt(arrivalStr[i]);
            int burst = Integer.parseInt(burstStr[i]);
            int priority = Integer.parseInt(priorityStr[i]);
            processes[i] = new Process(arrival, burst , priority);

        }
        Arrays.sort(processes , (p1 , p2)-> p1.arrival - p2.arrival);

        int currentTime = 0, complete = 0;
        int index = 0;

        PriorityQueue<Process> queue = new PriorityQueue<>((p1,p2)->p1.priority!=p2.priority ? p1.priority - p2.priority : p1.arrival - p2.arrival);
        while(complete<n){
            while(index<n && processes[index].arrival<=currentTime){
                queue.offer(processes[index]);
                index++;
                
            }
            if(queue.isEmpty()){
                currentTime++;
                continue;
            }
            Process process = queue.poll();
            if(process.start == -1){
                process.start = currentTime;
            }
            process.remaining--;
            currentTime++;

             while(index<n && processes[index].arrival<=currentTime){
                queue.offer(processes[index]);
                index++;
                
            }

            if(process.remaining > 0){
                queue.add(process);
            }
            else {
                process.complete = currentTime;
                process.tat = process.complete - process.arrival;
                process.wait = process.tat - process.burst;
                process.isDone = true;
                complete++;
            }
           

        }

        int totalTat = 0, totalWait = 0;
        for (Process process : processes) {
            totalTat += process.tat;
            totalWait += process.wait;
        }
        System.out.println(totalWait / n);
        System.out.println(totalTat / n);

    }

    static class Process {
        int arrival, burst, start = -1, complete, tat, wait, remaining , priority;
        Boolean isDone = false;

        Process(int arrival, int burst , int priority) {
            this.arrival = arrival;
            this.burst = burst;
            this.remaining = burst;
            this.priority = priority;
        }
    }

}
