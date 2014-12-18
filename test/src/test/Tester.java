package test;

import main.Generator;

public class Tester {

	
	public static void main(String args[])
	{
		
		Generator g = new Generator();
		
		String print = g.calculateChecksum("test.html","http://localhost:8080/StaticHash/index.html");
		
		System.out.println(print);
		
	}
	
}
