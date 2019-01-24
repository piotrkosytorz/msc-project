package algorithms.lftj0.modules;

public class LFJ<T> {

    LinearIterator[] iters;
    int p;  // position
    boolean atEnd;

    public LFJ(LinearIterator[] iters) {
        this.iters = iters;
        this.p = 0;
        this.atEnd = false;
    }

    public void init() {
        boolean anyIterAtEnd = false;

        for (int i = 0; i < this.iters.length; i++) {
            if (this.iters[i].atEnd()) {
                anyIterAtEnd = true;
                break;
            }
        }

        this.atEnd = anyIterAtEnd;

        if (!this.atEnd) {
            this.sortIters();
        }
    }

    private void sortIters() {

    }

    T key() {
        return null;
    }

    void next() {

    }

    void seek(T key) {

    }

    boolean atEnd() {
        return false;
    }


}
