package resourcedemand;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by mrombach on 23.06.2017.
 */
public class ResourceDemandingBehaviour {
    public int calculate(int number) {
        int n = number * ThreadLocalRandom.current().nextInt(3000000, 3010000);

        // initially assume all integers are prime
        BitSet isPrime = new BitSet(n+1);
        for (int i = 2; i <= n; i++) {
            isPrime.set(i);
        }

        // mark non-primes <= n using Sieve of Eratosthenes
        for (int factor = 2; factor*factor <= n; factor++) {

            // if factor is prime, then mark multiples of factor as nonprime
            // suffices to consider mutiples factor, factor+1, ...,  n/factor
            if (isPrime.get(factor)) {
                for (int j = factor; factor*j <= n; j++) {
                    isPrime.clear(factor*j);
                }
            }
        }

        // count primes
        int primes = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrime.get(i)) primes++;
        }
        return primes;
    }
}
