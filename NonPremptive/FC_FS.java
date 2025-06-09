package NonPremptive;
import java.util.Arrays;
import java.util.Scanner;

public class FC_FS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] arrivalStr = sc.nextLine().split(" ");
        String[] burstStr = sc.nextLine().split(" ");
        int n = arrivalStr.length;

        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            int arrival = Integer.parseInt(arrivalStr[i]);
            int burst = Integer.parseInt(burstStr[i]);
            processes[i] = new Process(arrival, burst);
        }

        Arrays.sort(processes, (p1, p2) -> p1.arrival != p2.arrival ? p1.arrival - p2.arrival : p1.burst - p2.burst);

        int currentTime = 0;
        for (Process process : processes) {
            currentTime = Math.max(currentTime, process.arrival);
            process.start = currentTime;
            process.complete = process.burst + process.start;
            process.tat = process.complete - process.arrival;
            process.wait = process.tat - process.burst;
            currentTime = process.complete;

        }

        int totalTat = 0, totalWait = 0;
        for (Process process : processes) {
            totalTat += process.tat;
            totalWait += process.wait;
        }

        System.out.println(totalWait / n);
        System.out.println(totalTat / n);

        sc.close();

    }

    static class Process {
        int arrival, burst, complete, start, wait, tat;

        Process(int arrival, int burst) {
            this.arrival = arrival;
            this.burst = burst;
        }
    }

}
