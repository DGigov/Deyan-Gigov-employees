package bg.sirma.stage2.Assignment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 
 * @author Deyan Gigov
 * @version 2.0
 *
 */
public class App {

    private static HashMap<String, Integer> hashMapPair = new HashMap<>();

    // Get String lines and make a employer
    private static Employer[] stringToEmployer(List<String> lines) {

        Employer employers[] = new Employer[lines.size()];

        int empID;
        int projectID;
        LocalDate dateFrom = new LocalDate();
        LocalDate dateTo = new LocalDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        int i = 0;
        for (String line : lines) {
            if (!line.isEmpty()) {
                String[] employerComponents = line.split(",");
                empID = Integer.parseInt(employerComponents[0]);
                projectID = Integer.parseInt(employerComponents[1]);
                dateFrom = (employerComponents[2].equals("NULL")) ? new LocalDate()
                        : formatter.parseLocalDate(employerComponents[2]);
                dateTo = (employerComponents[3].equals("NULL")) ? new LocalDate()
                        : formatter.parseLocalDate(employerComponents[3]);
                Employer employer = new Employer(empID, projectID, dateFrom, dateTo);
                employers[i] = employer;
                i++;
            }
        }
        return employers;
    }

    // Sort all data by project id
    private static HashMap<Integer, List<Employer>> mapOfProjects(List<Employer> lines) {
        HashMap<Integer, List<Employer>> hashMap = new HashMap<>();
        for (Employer line : lines) {
            if (!hashMap.containsKey(line.getProjectID())) {
                List<Employer> list = new ArrayList<Employer>();
                list.add(line);
                hashMap.put(line.getProjectID(), list);
            } else {
                hashMap.get(line.getProjectID()).add(line);
            }
        }
        return hashMap;
    }
    //Generate all combinations of pairs for every project
    private static void allPairsOfEmployers(HashMap<Integer, List<Employer>> hashMap) {

        int r = 2;
        for (Integer line : hashMap.keySet()) {
            List<Employer> empList = new ArrayList<>(hashMap.get(line));
            System.out.println("\n*********All Pairs employers combinations for project: " + line);
            printCombination(empList, empList.size(), r);
        }
        System.out.println("\n******Hash Map With Pairs******");
        for (Map.Entry<String, Integer> me : hashMapPair.entrySet()) {
            System.out.println("Team: " + me.getKey() + " All Days working together: " + me.getValue());
        }
        solution(hashMapPair);
    }

    private static void printCombination(List<Employer> arr, int n, int r) {
        // A temporary array to store all combination one by one
        List<Employer> tempList = new ArrayList<>(r);

        // Print all combination using temprary array 'tempList'
        combinationUtil(arr, tempList, 0, n - 1, 0, r);
    }

    private static void combinationUtil(List<Employer> arr, List<Employer> data, int start, int end, int index, int r) {

        if (index == r) {
            for (int j = 0; j < r; j++)
                System.out.print(data.get(j) + " & ");
            Employer first = data.get(0);
            Employer second = data.get(1);
            int daysWorkingTogether = 0;
            LocalDate d1 = first.getDateFrom();
            LocalDate d2 = first.getDateTo();
            LocalDate d3 = second.getDateFrom();
            LocalDate d4 = second.getDateTo();
            String pairsIds = String.valueOf(first.getEmpID()) + "-" + String.valueOf(second.getEmpID());
            
            if (d1.isBefore(d3) && d2.isAfter(d3) && d4.isBefore(d2)) {
                daysWorkingTogether = Days.daysBetween(d3, d4).getDays();
            } else if (d1.isBefore(d3) && d2.isAfter(d3) && d4.isAfter(d2)) {
                daysWorkingTogether = Days.daysBetween(d3, d2).getDays();
            } else if (d3.isBefore(d1) && d4.isAfter(d1) && d2.isAfter(d4)) {
                daysWorkingTogether = Days.daysBetween(d1, d4).getDays();
            } else if (d3.isBefore(d1) && d4.isAfter(d1) && d4.isAfter(d2)) {
                daysWorkingTogether = Days.daysBetween(d1, d2).getDays();
            }
            System.out.print("Days Working Together: " + daysWorkingTogether);
            System.out.println(" ");
            //Тhe sum of the days worked together in other projects
            if (hashMapPair.containsKey(pairsIds)) {
                daysWorkingTogether += hashMapPair.get(pairsIds);
            }
            hashMapPair.put(pairsIds, daysWorkingTogether);
            return;
        }
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data.add(index, arr.get(i));

            combinationUtil(arr, data, i + 1, end, index + 1, r);
        }
    }
    //Find the biggest value in the map.
    private static void solution(HashMap<String, Integer> map) {
        Map.Entry<String, Integer> maxEntry = null;

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        System.out.print("\nThe team that has worked the longest together is: " + maxEntry + " days");
    }

    public static void main(String[] args) throws URISyntaxException {

        final Path inputFile = Paths.get(App.class.getClassLoader().getResource("Inputfile.txt").toURI());

        try {
            List<String> lines = Files.readAllLines(inputFile);
            Employer[] linesWithEmployers = stringToEmployer(lines);
            HashMap<Integer, List<Employer>> mapProject = mapOfProjects(Arrays.asList(linesWithEmployers));

            System.out.println("*****Print input file*****");
            for (Employer line : linesWithEmployers) {
                System.out.println(line);
            }

            System.out.println("\n******Sort by Projects******");
            for (Map.Entry<Integer, List<Employer>> me : mapProject.entrySet()) {
                System.out.println("ProjectId: " + me.getKey() + " Employers: " + me.getValue());
            }

            allPairsOfEmployers(mapProject);

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
