package Premptive;

import java.util.*;

public class LongestRemainTimeFirst {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] arrStr = sc.nextLine().split(" ");
        String[] burstStr = sc.nextLine().split(" ");

        int n = arrStr.length;

        Process[] processes = new Process[n];
        for (int i = 0; i < n; i++) {
            int arrival = Integer.parseInt(arrStr[i]);
            int burst = Integer.parseInt(burstStr[i]);
            processes[i] = new Process(i + 1, arrival, burst);
        }

        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrival));

        PriorityQueue<Process> pq = new PriorityQueue<>(
           (p1 ,p2)->p1.remaining!=p2.remaining ? p2.remaining - p1.remaining : p1.arrival - p2.arrival);
        

        int currentTime = 0, completed = 0, i = 0;

        while (completed < n) {
            // Add all processes that have arrived by currentTime
            while (i < n && processes[i].arrival <= currentTime) {
                pq.offer(processes[i]);
                i++;
            }

            if (pq.isEmpty()) {
                currentTime++;
                continue;
            }

            Process curr = pq.poll();

            if (curr.start == -1) {
                curr.start = currentTime;
            }

            curr.remaining--;
            currentTime++;

            // Add new processes that arrive during this 1-unit execution
            while (i < n && processes[i].arrival <= currentTime) {
                pq.offer(processes[i]);
                i++;
            }

            if (curr.remaining > 0) {
                pq.offer(curr); // Put back the remaining process
            } else {
                curr.complete = currentTime;
                curr.tat = curr.complete - curr.arrival;
                curr.wait = curr.tat - curr.burst;
                completed++;
            }
        }

        int totalTat = 0, totalWait = 0;
        for (Process p : processes) {
            totalTat += p.tat;
            totalWait += p.wait;
        }

        System.out.println(totalWait / n);
        System.out.println(totalTat / n);
    }

    static class Process {
        int pid;
        int arrival, burst, remaining;
        int start = -1, complete, tat, wait;

        Process(int pid, int arrival, int burst) {
            this.pid = pid;
            this.arrival = arrival;
            this.burst = burst;
            this.remaining = burst;
        }
    }
}
