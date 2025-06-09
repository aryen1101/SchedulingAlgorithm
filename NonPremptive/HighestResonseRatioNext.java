package NonPremptive;
import java.util.Arrays;
import java.util.Scanner;

public class HighestResonseRatioNext {
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
        Arrays.sort(processes, (p1, p2) -> p1.arrival - p2.arrival);
        int currentTime = 0, complete = 0;
        while (complete < n) {
            double maxRR = Integer.MIN_VALUE;
            Process nextProcess = null;

            for (Process process : processes) {
                if (!process.visited && process.arrival <= currentTime) {
                    int waitingTime = currentTime - process.arrival;
                    double responseRatio = (waitingTime + process.burst) / (double) process.burst;

                    if (responseRatio > maxRR) {
                        maxRR = responseRatio;
                        nextProcess = process;
                    }

                }
            }
            if (nextProcess == null) {
                currentTime++;
                continue;
            }

            nextProcess.start = currentTime;
            nextProcess.complete = nextProcess.start + nextProcess.burst;
            nextProcess.tat = nextProcess.complete - nextProcess.arrival;
            nextProcess.wait = nextProcess.tat - nextProcess.burst;
            nextProcess.visited = true;

            currentTime = nextProcess.complete;
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
        int arrival, burst, start, complete, tat, wait;
        boolean visited = false;

        Process(int arrival, int burst) {
            this.arrival = arrival;
            this.burst = burst;
        }
    }
}
