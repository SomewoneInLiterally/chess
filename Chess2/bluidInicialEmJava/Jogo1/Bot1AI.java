package bluidInicialEmJava.telaDeTitulo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Simple AI helper that selects between options.
 * Provides random and weighted selection utilities.
 */
public class Bot1AI {
    private final Random rnd;

    public Bot1AI() {
        this(new Random());
    }

    public Bot1AI(long seed) {
        this(new Random(seed));
    }

    public Bot1AI(Random rnd) {
        this.rnd = rnd == null ? new Random() : rnd;
    }

    /**
     * Choose a random element from a list. Returns null if list is null or empty.
     */
    public <T> T chooseRandom(List<T> options) {
        if (options == null || options.isEmpty()) return null;
        return options.get(rnd.nextInt(options.size()));
    }

    /**
     * Choose a random key from a map. Returns null if map is null or empty.
     */
    public <T> T chooseRandomKey(Map<T, ?> map) {
        if (map == null || map.isEmpty()) return null;
        Object[] keys = map.keySet().toArray();
        @SuppressWarnings("unchecked")
        T key = (T) keys[rnd.nextInt(keys.length)];
        return key;
    }

    /**
     * Choose an index based on weights. Weights must be non-negative and at least one > 0.
     * Returns -1 on invalid input.
     */
    public int chooseIndexByWeights(double[] weights) {
        if (weights == null || weights.length == 0) return -1;
        double sum = 0;
        for (double w : weights) {
            if (w < 0) return -1;
            sum += w;
        }
        if (sum <= 0) return -1;
        double r = rnd.nextDouble() * sum;
        double cumulative = 0;
        for (int i = 0; i < weights.length; i++) {
            cumulative += weights[i];
            if (r < cumulative) return i;
        }
        return weights.length - 1; // fallback
    }

    /**
     * Choose an element from an array using a parallel weights array.
     * Returns null on invalid input.
     */
    public <T> T chooseWeighted(T[] options, double[] weights) {
        if (options == null || weights == null) return null;
        if (options.length != weights.length || options.length == 0) return null;
        int idx = chooseIndexByWeights(weights);
        return idx >= 0 ? options[idx] : null;
    }

    /**
     * Choose an element from a list using a parallel weights array.
     * Returns null on invalid input.
     */
    public <T> T chooseWeighted(List<T> options, double[] weights) {
        if (options == null || weights == null) return null;
        if (options.size() != weights.length || options.isEmpty()) return null;
        int idx = chooseIndexByWeights(weights);
        return idx >= 0 ? options.get(idx) : null;
    }
}
