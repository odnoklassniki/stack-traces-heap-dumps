package demo4;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Demonstrates Safepoint Bias problem of popular CPU profilers.
 * E.g. Visual VM shows nearly 100% time is spent in System.identityHashCode,
 * but this is not true.
 * A more accurate async-profiler (https://github.com/jvm-profiling-tools/async-profiler)
 * will show something like this:
 *
 *     13,59%    __kernel_sin(double, double, int)
 *     12,76%    java.util.IdentityHashMap.resize
 *     10,59%    java.util.concurrent.ThreadLocalRandom.current
 *     9,06%    java.util.IdentityHashMap.put
 *     7,16%    ObjectSynchronizer::FastHashCode(Thread*, oopDesc*)
 *     6,24%    JVM_IHashCode
 *     6,22%    demo4.Location.random
 *     5,72%    SharedRuntime::dcos(double)
 *     4,24%    demo4.Location.distanceTo
 *     3,84%    __ieee754_rem_pio2(double, double*)
 *     3,78%    java.util.IdentityHashMap.hash
 *     3,13%    java.lang.System.identityHashCode
 */
public class Location {
    static final double R = 6371009;

    double lat;
    double lng;

    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public static Location random() {
        double lat = ThreadLocalRandom.current().nextDouble() * 30 + 40;
        double lng = ThreadLocalRandom.current().nextDouble() * 100 + 35;
        return new Location(lat, lng);
    }

    private static double toRadians(double x) {
        return x * Math.PI / 180;
    }

    public double distanceTo(Location other) {
        double dlat = toRadians(other.lat - this.lat);
        double dlng = toRadians(other.lng - this.lng);
        double mlat = toRadians((this.lat + other.lat) / 2);
        return R * Math.sqrt(Math.pow(dlat, 2) + Math.pow(Math.cos(mlat) * dlng, 2));
    }

    private static Map<Location, Double> calcDistances(Location target) {
        Map<Location, Double> distances = new IdentityHashMap<>();
        for (int i = 0; i < 100; i++) {
            Location location = Location.random();
            distances.put(location, location.distanceTo(target));
        }
        return distances;
    }

    public static void main(String[] args) throws Exception {
        Location oslo = new Location(59.91, 10.76);
        for (int i = 0; i < 10000000; i++) {
            calcDistances(oslo);
        }
    }
}
