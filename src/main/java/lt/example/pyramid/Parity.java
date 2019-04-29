package lt.example.pyramid;

public enum Parity {

    even(0), odd(1);

    private int number;

    private Parity(int number) {
        this.number = number;
    }

    public static Parity getParity(int number) {
        return number % 2 == 0 ? even : odd;
    }

}
