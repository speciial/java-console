package ui;

import java.util.ArrayList;
import java.util.HashMap;

public class PerformanceLogger {

    private static final HashMap<String, ArrayList<PerfLogPoint>> perfLog = new HashMap<>();

    private static PerfLogPoint currentLogPoint;

    public static void startLog(String name) {
        currentLogPoint = new PerfLogPoint();
        currentLogPoint.name = name;
        currentLogPoint.start = System.nanoTime();
    }

    public static void endLog() {
        if (currentLogPoint != null) {
            currentLogPoint.end = System.nanoTime();

            ArrayList<PerfLogPoint> pointList = perfLog.computeIfAbsent(currentLogPoint.name, k -> new ArrayList<>());
            pointList.add(currentLogPoint);

            currentLogPoint = null;
        }
    }

    public static void printCurrentStatistics(boolean shouldPrintDetails) {
        perfLog.forEach((s, perfLogPoints) -> {
            StringBuilder builder = new StringBuilder();
            builder.append(s).append(":\n[");

            long totalTime = 0;
            for (PerfLogPoint point : perfLogPoints) {
                long delta = point.end - point.start;
                totalTime += delta;

                if (shouldPrintDetails) {
                    float deltaInMS = (float) delta / 1000000.0f;
                    builder.append(deltaInMS).append("ms, ");
                }
            }
            if (!shouldPrintDetails) {
                builder.append("...");
            }
            builder.append("]\n");

            float totalTimeInMS = (float) totalTime / 1000000.0f;
            builder.append("Total Time: ").append(totalTimeInMS).append("ms\n");

            float averageTimeInMS = totalTimeInMS / perfLog.size();
            builder.append("Average Time: ").append(averageTimeInMS).append("ms\n");

            System.out.println(builder);
        });
    }

    public static class PerfLogPoint {
        public String name;

        public long start;
        public long end;
    }
}
