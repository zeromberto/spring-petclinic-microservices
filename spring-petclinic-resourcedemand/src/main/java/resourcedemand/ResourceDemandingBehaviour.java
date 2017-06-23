package resourcedemand;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by mrombach on 23.06.2017.
 */
public class ResourceDemandingBehaviour {
    public void calculate(int number) {
        int n = number * 99999;
        n = n + ThreadLocalRandom.current().nextInt(1, 5000 + 1);;

        // initially assume all integers are prime
        boolean[] isPrime = new boolean[n+1];
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }

        // mark non-primes <= n using Sieve of Eratosthenes
        for (int factor = 2; factor*factor <= n; factor++) {

            // if factor is prime, then mark multiples of factor as nonprime
            // suffices to consider mutiples factor, factor+1, ...,  n/factor
            if (isPrime[factor]) {
                for (int j = factor; factor*j <= n; j++) {
                    isPrime[factor*j] = false;
                }
            }
        }

        // count primes
        int primes = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) primes++;
        }
    }
}
