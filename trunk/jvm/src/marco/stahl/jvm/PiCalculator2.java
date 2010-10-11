package marco.stahl.jvm;

public class PiCalculator2 {

	
	static double pi(int i,int j) {
		if (i==0) {
			double sum = 0;
			for(int i2=0;i2<j;i2++){
				double i2d= i2*2;
				double add = 4 /(i2d+1); 
				if (i2%2==0) {
					sum+=add;
				} else {
					sum-=add;
				}
			}
			return sum;
		} else {
			return (pi(i-1,j)+pi(i-1,j+1))/2;
		}
	}
	
	
    public static void main2(String [] args)
    {
        System.out.println(pi(10,10));
        System.out.println(Math.PI);
    }
	
	
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
