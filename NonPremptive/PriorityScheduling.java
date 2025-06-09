package NonPremptive;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class PriorityScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] arrivalStr = sc.nextLine().split(" ");
        String[] burstStr = sc.nextLine().split(" ");
        String[] priorityStr = sc.nextLine().split(" ");
        int n = arrivalStr.length;
        Process[] processes = new Process[n];
        for (int i = 0; i < n; i++) {
            int arrival = Integer.parseInt(arrivalStr[i]);
            int burst = Integer.parseInt(burstStr[i]);
            int priority = Integer.parseInt(priorityStr[i]);
            processes[i] = new Process(arrival, burst, priority);

        }
        Arrays.sort(processes, (p1, p2) -> p1.arrival - p2.arrival);

        PriorityQueue<Process> queue = new PriorityQueue<>(
                (x, y) -> x.priority != y.priority ? x.priority - y.priority : x.arrival - y.arrival);

        int currentTime = 0, complete = 0;
        while (complete < n) {
            for (Process process : processes) {
                if (!process.visited && process.arrival <= currentTime) {
                    queue.add(process);
                    process.visited = true;
                }
            }
            if (queue.isEmpty()) {
                currentTime++;
                continue;
            }
            Process process = queue.poll();
            process.start = currentTime;
            process.complete = process.start + process.burst;
            process.tat = process.complete - process.arrival;
            process.wait = process.tat - process.burst;
            currentTime = process.complete;
            complete++;

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
        int arrival, burst, start, complete, tat, wait, priority;
        boolean visited;

        Process(int arrival, int burst, int priority) {
            this.arrival = arrival;
            this.burst = burst;
            this.priority = priority;
        }
    }

}
