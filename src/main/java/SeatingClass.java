import java.util.ArrayList;
import java.util.List;

public class SeatingClass {

    public static void main(String[] args) {
        SeatingClass seatingClass = new SeatingClass();
        int[][] noOfSeats = {{2, 3}, {3, 4}, {3, 3}};

        seatingClass.seatingArrangement(noOfSeats, 28);
    }

    public void seatingArrangement(int[][] noOfSeats, int passengersWaiting) {
        int totalRows = countTotalRows(noOfSeats);
        int mostColumns = countTotalColumns(noOfSeats);
        int[][] planeSeats = new int[totalRows][mostColumns];
        arrangePlaneSeats(planeSeats, noOfSeats, totalRows, mostColumns);
        int aisleSeats = countAisleSeats(noOfSeats);
        int windowSeats = countWindowSeats(noOfSeats);
        int middleSeats = totalSeats(planeSeats, totalRows, mostColumns) - aisleSeats - windowSeats;
        List<Integer> windowRows = getWindowRows(noOfSeats, totalRows);
        List<Integer> aisleRows = getAisleRows(noOfSeats);
        List<Integer> middleRows = getMiddleRows(totalRows, noOfSeats);
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < mostColumns; j++) {
                System.out.print(planeSeats[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println(windowRows.toString());
        System.out.println(middleRows.toString());
        System.out.println(aisleRows.toString());
        System.out.println(aisleSeats);
        System.out.println(windowSeats);
        System.out.println(middleSeats);

        fillPassengersInSeats(planeSeats, totalRows, mostColumns, aisleSeats, windowSeats, middleSeats,
                aisleRows, windowRows, middleRows, passengersWaiting);

        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < mostColumns; j++) {
                System.out.print(planeSeats[i][j] + "  ");
            }
            System.out.println();
        }
    }

    private void fillPassengersInSeats(int[][] planeSeats, int totalRows, int mostColumns, int aisleSeats, int windowSeats, int middleSeats, List<Integer> aisleRows, List<Integer> windowRows, List<Integer> middleRows, int passengersWaiting) {
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < mostColumns; j++) {
                if (planeSeats[i][j] == 9999) {
                    continue;
                } else {
                    if (aisleRows.contains(i)) {
                        int priority = 0;
                        priority += aisleRows.size() * (j);
                        int k = aisleRows.indexOf(i);
                        priority += k + 1;
                        if (priority <= passengersWaiting) {
                            planeSeats[i][j] = priority;
                        }
                    } else if (windowRows.contains(i)) {
                        int priority = aisleSeats + 1;
                        priority += windowRows.size() * (j);
                        int k = windowRows.indexOf(i);
                        priority += k + 1;
                        if (priority <= passengersWaiting) {
                            planeSeats[i][j] = priority;
                        }
                    } else if (middleRows.contains(i)) {
                        int priority = aisleSeats + windowSeats + 1;
                        priority += middleRows.size() * (j);
                        int k = middleRows.indexOf(i);
                        priority += k + 1;
                        if (priority <= passengersWaiting) {
                            planeSeats[i][j] = priority;
                        }
                    }
                }
            }
        }
    }

    private List<Integer> getMiddleRows(int totalRows, int[][] noOfSeats) {
        List<Integer> middleRows = new ArrayList<>();
        List<Integer> aisleRows = getAisleRows(noOfSeats);
        List<Integer> windowRows = getWindowRows(noOfSeats, totalRows);
        List<Integer> occupiedRows = new ArrayList<>();
        occupiedRows.addAll(aisleRows);
        occupiedRows.addAll(windowRows);
        for (int i = 0; i < totalRows; i++) {
            if (!occupiedRows.contains(i)) {
                middleRows.add(i);
            }
        }
        return middleRows;
    }

    private List<Integer> getAisleRows(int[][] noOfSeats) {
        int previousRow = 0;
        List<Integer> aisleRows = new ArrayList<>();
        int count = 0;
        for (int[] x : noOfSeats) {
            if (x[0] > 1) {
                if (count == 0) {
                    aisleRows.add(previousRow + x[0] - 1);
                } else if (count == noOfSeats.length - 1) {
                    aisleRows.add(previousRow);
                } else {
                    aisleRows.add(previousRow);
                    aisleRows.add(previousRow + x[0] - 1);
                }
                previousRow += x[0];
            } else {
                aisleRows.add(previousRow);
                previousRow += 1;
            }
            count++;
        }
        return aisleRows;
    }

    private List<Integer> getWindowRows(int[][] noOfSeats, int totalRows) {
        boolean firstRow = true;
        boolean lastRow = true;
        List<Integer> windowRows = new ArrayList<>();
        if (noOfSeats[0][0] == 1) {
            firstRow = false;
        }
        if (noOfSeats[noOfSeats.length - 1][0] == 1) {
            lastRow = false;
        }
        if (firstRow && lastRow) {
            windowRows.add(0);
            windowRows.add(totalRows - 1);
        } else if (firstRow) {
            windowRows.add(0);
        } else if (lastRow) {
            windowRows.add(totalRows - 1);
        }
        return windowRows;
    }

    private int totalSeats(int[][] planeSeats, int totalRows, int mostColumns) {
        int totalSeats = 0;
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < mostColumns; j++) {
                if (planeSeats[i][j] != 9999) {
                    totalSeats += 1;
                }
            }
        }
        return totalSeats;
    }

    private int countWindowSeats(int[][] noOfSeats) {
        int countWindowSeats = 0;
        if (noOfSeats[0][0] != 1) {
            countWindowSeats += noOfSeats[0][1];
        }
        if (noOfSeats[noOfSeats.length - 1][0] != 1) {
            countWindowSeats += noOfSeats[noOfSeats.length - 1][1];
        }
        return countWindowSeats;
    }

    private int countAisleSeats(int[][] noOfSeats) {
        int countAisleSeats = 0;
        for (int i = 0; i < noOfSeats.length; i++) {
            if (i == 0 || i == noOfSeats.length - 1 || noOfSeats[i][0] == 1) {
                countAisleSeats += noOfSeats[i][1];
            } else {
                countAisleSeats += noOfSeats[i][1] * 2;
            }
        }
        return countAisleSeats;
    }

    private void arrangePlaneSeats(int[][] planeSeats, int[][] noOfSeats, int totalRows, int mostColumns) {
        int previousRow = 0;
        for (int i = 0; i < noOfSeats.length; i++) {
            int[] seats = noOfSeats[i];
            for (int j = 0; j < seats[0]; j++) {
                for (int k = 0; k < seats[1]; k++) {
                    planeSeats[j + previousRow][k] = -1;
                }
            }
            previousRow += seats[0];
        }

        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < mostColumns; j++) {
                if (planeSeats[i][j] != -1) {
                    planeSeats[i][j] = 9999;
                }
            }
        }
    }

    private int countTotalColumns(int[][] noOfSeats) {
        int mostColumns = 0;
        for (int[] x : noOfSeats) {
            if (x[1] > mostColumns) {
                mostColumns = x[1];
            }

        }
        return mostColumns;
    }

    private int countTotalRows(int[][] noOfSeats) {
        int count = 0;
        for (int[] x : noOfSeats) {
            count += x[0];
        }
        return count;
    }
}
