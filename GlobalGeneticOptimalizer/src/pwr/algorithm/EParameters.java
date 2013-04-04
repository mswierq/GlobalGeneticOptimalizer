package pwr.algorithm;


public enum EParameters {
	X1,
	X2,
	X3,
	X4,
	X5;
	
	public static String getEParameter(int value){
		switch(value){
			case 0: return X1.name();
			case 1: return X2.name();
			case 2: return X3.name();
			case 3: return X4.name();
			case 4: return X5.name();
			default: return null;
		}
	}
}
