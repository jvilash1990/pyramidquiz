package lt.example.pyramid;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility class to calculate longest path over binary tree by given rules:
 * <ol>
 *   <li>Starting from the top and move downwards to an adjacent number as in below.</li>
 *   <li>You are only allowed to walk downwards and diagonally.</li>
 *   <li>You should walk over the numbers as evens and odds subsequently. Suppose that you are on an even number the next
 * number you walk must be odd, or if you are stepping over an odd number the next number must be even. In other words,
 * the final path would be like Odd -> even -> odd -> even …</li>
 *   <li>You must reach to the bottom of the pyramid. Your goal is to find the maximum sum if you walk the path. Assume
 * that there is at least 1 valid path to the bottom. If there are multiple paths giving the same sum, you can choose
 * any of them.</li>
 * </ol>
 *
 * @author dstulgis
 */
public class LongestBinaryPathFinder {

    /**
     * Calculates path from binary tree top, summing up downwards or downwards and to the right number.
     * Walks over the numbers as evens and odds subsequently.
     *
     * @return longest path over binary tree or -1 if there is no full path.
     * @throws PathFinderError on invalid input.
     */
    public long calculateLongestPath(URI inputURI) {
        Path inputPath = Paths.get(inputURI);
        long lineNumber = 1;
        long expectedNumbersInLine = 1;

        try {
            BufferedReader reader = Files.newBufferedReader(inputPath);
            String line = reader.readLine();
            if (line == null) {
                throw new PathFinderError(inputURI.getPath() + " input file is empty.");
            }

            PathMetaData metadata = new PathMetaData(Integer.parseInt(line));
            List<PathMetaData> paths = new ArrayList<>();
            paths.add(metadata);

            lineNumber++;
            expectedNumbersInLine++;

            while ((line = reader.readLine()) != null) {
                List<PathMetaData> updatedPaths = new ArrayList<>();
                String[] numbers = line.split(" ");
                if (numbers.length != expectedNumbersInLine) {
                    throw new PathFinderError("Invalid input at line " + lineNumber + ", expected " + expectedNumbersInLine
                            + ", but was " + numbers.length);
                }

                updatedPaths.addAll(paths.stream()
                        .map(path -> getNewPathBranches(path, numbers))
                        .flatMap(pathsList -> pathsList.stream())
                        .collect(Collectors.toList()));

                paths = updatedPaths;
                lineNumber++;
                expectedNumbersInLine++;
            }

            return paths.stream().sorted().findFirst().orElse(new PathMetaData(-1)).getSum();
        } catch (IOException | NumberFormatException e) {
            throw new PathFinderError("Failed reading file: " + inputURI.getPath(), e);
        }
    }

    private Collection<PathMetaData> getNewPathBranches(PathMetaData path, String[] numbers) {
        List<PathMetaData> newPaths = new ArrayList<>();
        PathMetaData path2 = path.clone();
        findPath(path, Integer.parseInt(numbers[path.getLastPositionIndex()]), path.getLastPositionIndex())
                .ifPresent(newPath -> newPaths.add(newPath));
        findPath(path2, Integer.parseInt(numbers[path2.getLastPositionIndex() + 1]), path2.getLastPositionIndex() + 1)
                .ifPresent(newPath -> newPaths.add(newPath));
        return newPaths;
    }

    private Optional<PathMetaData> findPath(PathMetaData path, int number, int newPositionIndex) {
        Parity newParity = Parity.getParity(number);
        if (newParity.equals(path.getParity())) {
            return Optional.empty();
        }
        return Optional.of(new PathMetaData(path.getSum() + number, newPositionIndex, newParity));
    }

}
