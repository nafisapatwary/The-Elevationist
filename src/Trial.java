public class Trial {
    private int trialNum;
    private double time;

    public Trial(int n, double t){
        this.trialNum = n;
        this.time = t;
    }

    public int getTrialNum() {
        return trialNum;
    }

    public double getTime() {
        return time;
    }

    public String toString(){
        return "Trial Num " + trialNum + ": " + time;
    }
}
