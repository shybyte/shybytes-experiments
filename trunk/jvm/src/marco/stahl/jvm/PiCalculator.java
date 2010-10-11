package marco.stahl.jvm;

public class PiCalculator {	
    public static void main(String [] args)
    {
        double total = 0.0;
        int sign = 1;
        for (long j = 1; j <= 10000; j += 2)
        {
            total += sign / (double)j;
            sign *= -1;
        }
        double pi =  4 * total;
        System.out.println(pi);
    }

}
