package lt.example.pyramid;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

public class LongestBinaryPathFinderTest {

    private LongestBinaryPathFinder pathFinder;

    @Before
    public void onSetup() {
        pathFinder = new LongestBinaryPathFinder();
    }

    @Test
    public void shouldFindSimpleLongestPath() {
        URI inputPath = resourceAsURI("simpleInput.txt");

        long result = pathFinder.calculateLongestPath(inputPath);

        assertThat(result, equalTo(16L));
    }

    @Test
    public void shouldFindLongestPath() {
        URI inputPath = resourceAsURI("questionTargetInput.txt");

        long result = pathFinder.calculateLongestPath(inputPath);

        assertThat(result, equalTo(8186L));
    }

    @Test(expected = PathFinderError.class)
    public void shouldFailParsingLetters() {
        URI inputPath = resourceAsURI("incorrectInput.txt");

        pathFinder.calculateLongestPath(inputPath);
    }

    @Test(expected = PathFinderError.class)
    public void shouldFailOnMissingNumber() {
        URI inputPath = resourceAsURI("incompleteInput.txt");

        pathFinder.calculateLongestPath(inputPath);
    }

    @Test(expected = PathFinderError.class)
    public void shouldFailOnExtraNumber() {
        URI inputPath = resourceAsURI("extraInput.txt");

        pathFinder.calculateLongestPath(inputPath);
    }

    @Test
    public void shouldNotFindLongestPath() {
        URI inputPath = resourceAsURI("noPathInput.txt");

        long result = pathFinder.calculateLongestPath(inputPath);

        assertThat(result, equalTo(-1L));
    }

    private URI resourceAsURI(String relativeFilePath) {
        try {
            return this.getClass().getClassLoader().getResource(relativeFilePath).toURI();
        } catch (URISyntaxException e) {
            fail("Failed reading input file: " + relativeFilePath);
            return null;
        }
    }

}
