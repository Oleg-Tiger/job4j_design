package ru.job4j.search;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class SearchFilesByTheCriterion {

    public static void main(String[] args) throws IOException {
        NameArgs nameArgs = NameArgs.of(args);
        String dir = nameArgs.get("d");
        String name = nameArgs.get("n");
        String type = nameArgs.get("t");
        String rsl = nameArgs.get("o");

        if (dir == null || name == null || type == null || rsl == null) {
            throw new IllegalArgumentException(
                    "The input arguments must match the template -d=rootDirectory -n=fileName/mask/regex -t=mask -o=fileForResults"
            );
        }
        if (!type.equals("mask") && !type.equals("name") && !type.equals("regex")) {
            throw new IllegalArgumentException(
                    "The search type argument must be \"mask\", \"name\" or \"regex\""
            );
        }
        if (!Files.exists(Paths.get(dir))) {
            throw new FileNotFoundException("The source directory does not exist");
        }
            Files.createFile(Paths.get(rsl));
       try (FileOutputStream fos = new FileOutputStream(rsl)) {
           search(Paths.get(dir), name, type).forEach(x -> {
               try {
                   byte[] b = (x.toFile().getAbsolutePath() + System.lineSeparator()).getBytes();
                   fos.write(b);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           });
       }
    }

    private static List<Path> search(Path root, String name, String type) throws IOException {
        Predicate<Path> criterion;
        if (type.equals("name")) {
            criterion = x -> x.toFile().getName().equals(name);
        } else if (type.equals("mask")) {
            criterion = x -> x.toFile().getName().contains(name);
        } else {
            criterion = x -> x.toFile().getName().matches(name);
        }
        SearchFiles searcher = new SearchFiles(criterion);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }
}

