public class MyClass {
	int speed;
	public MyClass(){
		speed = 25;
	}
	public void car(){
		System.out.println("this is inside the function");
	}
	
	public static void main(String[] args){
		MyClass mycar1 = new MyClass();
		MyClass mycar2 = new MyClass();
		mycar1.speed =20;
		System.out.println(mycar1.speed);
		System.out.println(mycar2.speed);
		mycar1.car();
	} 
}
public class Srajan
{
	System.out.println("Hello");
}
