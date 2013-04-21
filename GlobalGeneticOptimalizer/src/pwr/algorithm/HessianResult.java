package pwr.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.graphbuilder.math.Expression;

public class HessianResult {
	private Expression[][] hessian;
	private List<Expression> firstDiffList;
	
	public HessianResult(int size) {
		hessian = new Expression[size][size];
		firstDiffList = new ArrayList<>(size);
	}
	
	public HessianResult(Expression[][] hessian, List<Expression> firstDiffList) {
		this.hessian = hessian;
		this.firstDiffList = firstDiffList;
	}

	public Expression[][] getHessian() {
		return hessian;
	}
	public void setHessian(Expression[][] hessian) {
		this.hessian = hessian;
	}
	public List<Expression> getFirstDiffList() {
		return firstDiffList;
	}
	public void setFirstDiffList(List<Expression> firstDiffList) {
		this.firstDiffList = firstDiffList;
	}
	
	
}
