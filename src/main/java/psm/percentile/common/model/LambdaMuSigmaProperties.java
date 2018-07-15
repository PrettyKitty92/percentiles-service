package psm.percentile.common.model;

/**
 * Created by Patrycja on 15.05.2017.
 */
public class LambdaMuSigmaProperties {

    private double lambda;
    private double mu;
    private double sigma;

    public LambdaMuSigmaProperties() {}

    public LambdaMuSigmaProperties(double lambda, double mu, double sigma) {
        this.lambda = lambda;
        this.mu = mu;
        this.sigma = sigma;
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public double getMu() {
        return mu;
    }

    public void setMu(double mu) {
        this.mu = mu;
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    @Override
    public String toString() {
        return "LambdaMuSigmaProperties{" +
                "lambda=" + lambda +
                ", mu=" + mu +
                ", sigma=" + sigma +
                '}' + "\n";
    }
}
