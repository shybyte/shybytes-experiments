package marco.stahl.jvm;

public class SimpleCalculator {

	public static int pow(int b,int e){
		int p = 1;
		for(int i=0;i<e;i++) {
			p=p*b;
		}
		return p;
	} 
	
	public static int mul(int x,int y){
		return x*y;
	}
	
	public static int sign(int x){
		if (x>0) {
			return 1;
		} else if (x<0) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public static int inc(int x){
		return x+1;
	}
	
    public static void main(String [] args)
    {
        System.out.println("Result:"+pow(2,8));
    }

}
