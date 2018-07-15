package psm.percentile.common.tools;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import psm.percentile.common.model.LambdaMuSigmaProperties;

/**
 * Created by Patrycja on 15.05.2017.
 */
@Component
public class PercentileConverter {

    @Autowired
    NormalDistribution normalDistribution;

    public int valueToPercentile(
            double x,
            LambdaMuSigmaProperties lms) {
        return lms == null ? -1 : valueToPercentile(x, lms.getMu(), lms.getLambda(), lms.getSigma());
    }

    public int valueToPercentile(
            double x,
            double m,
            double l,
            double s) {
        double z = (l != 0) ? ((Math.pow(x / m, l) - 1) / (l * s)) : (Math.log(x / m) / s);
        double p = normalDistribution.cumulativeProbability(z) * 100;
        return (int) Math.round(p);
    }

    public double percentileToValue(int percentile, double m, double l, double s) { //zwraca wartosc dla zadaneg percentyla na posdtawie LMS
        double correctedPercentile = percentile;
        if (percentile <= 0) {
            correctedPercentile = 0.25;
        } else if (percentile >= 100) {
            correctedPercentile = 99.75;
        }
        double z = normalDistribution.inverseCumulativeProbability(correctedPercentile / 100.0);
        double x = (l != 0) ? Math.pow(z * l * s + 1, 1 / l) * m : Math.exp(z * s) * m;
        return x;
    }

    public double percentileToValue(Integer x, LambdaMuSigmaProperties lms) {
        return lms == null ? -1 : percentileToValue(x, lms.getMu(), lms.getLambda(), lms.getSigma());
    }
}
