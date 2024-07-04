package utility.group;

public class Pair<F,S> {
    private final F first;

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    private final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}
