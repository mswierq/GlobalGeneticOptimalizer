package pwr.algorithm;

import java.util.ArrayList;
import java.util.List;

public class HessianResult {
	private String[][] hessian;
	private List<String> firstDiffList;
	
	public HessianResult(int size) {
		hessian = new String[size][size];
		firstDiffList = new ArrayList<String>(size);
	}
	
	public HessianResult(String[][] hessian, List<String> firstDiffList) {
		this.hessian = hessian;
		this.firstDiffList = firstDiffList;
	}

	public String[][] getHessian() {
		return hessian;
	}
	public void setHessian(String[][] hessian) {
		this.hessian = hessian;
	}
	public List<String> getFirstDiffList() {
		return firstDiffList;
	}
	public void setFirstDiffList(List<String> firstDiffList) {
		this.firstDiffList = firstDiffList;
	}
	
	
}
