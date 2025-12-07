package uj.wmii.pwj.w7.insurance;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

public class FloridaInsurance {

    public static void main(String[] args) {
        List<InsuranceEntry> entries = loadInsuranceData("FL_insurance.csv.zip");

        generateCountFile(entries);
        generateTiv2012File(entries);
        generateMostValuableFile(entries);
    }

    private static List<InsuranceEntry> loadInsuranceData(String zipFileName) {
        List<InsuranceEntry> entries = new ArrayList<>();

        try (ZipFile zipFile = new ZipFile(zipFileName)) {
            var zipEntry = zipFile.getEntry("FL_insurance.csv");

            if (zipEntry == null) {
                throw new IllegalStateException("FL_insurance.csv not found in ZIP file");
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(zipFile.getInputStream(zipEntry)))) {

                reader.readLine();

                String line;
                while ((line = reader.readLine()) != null) {
                    entries.add(new InsuranceEntry(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entries;
    }

    private static void generateCountFile(List<InsuranceEntry> entries) {
        long count = entries.stream()
                .map(InsuranceEntry::getCounty)
                .distinct()
                .count();

        try (PrintWriter writer = new PrintWriter("count.txt")) {
            writer.print(count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void generateTiv2012File(List<InsuranceEntry> entries) {
        double sum = entries.stream()
                .mapToDouble(InsuranceEntry::getTiv2012)
                .sum();

        try (PrintWriter writer = new PrintWriter("tiv2012.txt")) {
            writer.printf(Locale.US, "%.2f", sum);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void generateMostValuableFile(List<InsuranceEntry> entries) {
        Map<String, Double> countyIncreases = entries.stream()
                .collect(Collectors.groupingBy(
                        InsuranceEntry::getCounty,
                        Collectors.summingDouble(InsuranceEntry::getValueIncrease)
                ));

        List<Map.Entry<String, Double>> top10 = countyIncreases.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toList());

        try (PrintWriter writer = new PrintWriter("most_valuable.txt")) {
            writer.print("country,value");
            for (Map.Entry<String, Double> entry : top10) {
                writer.printf(Locale.US, "%n%s,%.2f", entry.getKey(), entry.getValue());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
