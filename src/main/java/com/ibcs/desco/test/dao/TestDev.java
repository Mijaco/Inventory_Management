package com.ibcs.desco.test.dao;


public class TestDev {

	public static void doIt(Object Object) {

		System.out.println(Object.getClass());

	}

	public static class MyPair {
		private final String key;
		private final Double value;

		public MyPair(String aKey, Double aValue) {
			key = aKey;
			value = aValue;
		}

		public String key() {
			return key;
		}

		public Double value() {
			return value;
		}
	}

	public static void main(String[] args) {
		String a = "A&B";
		//String [] abc = a.split("&\\w+=");
		String [] abc = a.split("\\&");
		//System.out.println(a.replace("&", "n"));
		String x = "";
		for(int i=0; i<abc.length;i++){		
			x+=abc[i]+"'||'&'||'";					
		}
		x = x.substring(0, x.length()-9);
		System.out.println(x);
		
	}

}
