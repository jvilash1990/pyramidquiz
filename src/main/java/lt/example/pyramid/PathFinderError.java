package lt.example.pyramid;

public class PathFinderError extends RuntimeException {

    public PathFinderError (Throwable e) {
        super(e);
    }

    public PathFinderError (String message) {
        super(message);
    }

    public PathFinderError (String message, Throwable e) {
        super(message, e);
    }

}
