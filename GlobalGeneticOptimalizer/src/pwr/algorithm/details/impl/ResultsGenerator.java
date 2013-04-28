package pwr.algorithm.details.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import pwr.algorithm.Specimen;


public class ResultsGenerator {
	private ArrayList<Specimen> matchTrace;
	private String matchTraceSaveDir;
	private boolean enableTimestamp;
	private final String fileSep;
	
	public ResultsGenerator() {
		matchTrace = new ArrayList<Specimen>();
		enableTimestamp = true;
		fileSep = System.getProperty("file.separator");
	}
	
	public void addMatchToTrace(Specimen newBestMatch) {
		matchTrace.add(newBestMatch);
	}
	
	public void setMatchTraceSaveDirectory(String dirAddress) {
		if(dirAddress.endsWith(fileSep)) {
			dirAddress = dirAddress.substring(0, dirAddress.length()-1);
		}
		
		if(enableTimestamp) {
			DateFormat dateFormat = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss_SSSS");
			Calendar cal = Calendar.getInstance();
			dirAddress += dateFormat.format(cal.getTime());
		}
		
		File dir = new File(dirAddress);
		if(!dir.exists()) {
			if(dir.mkdirs()) {
				matchTraceSaveDir = dir.getAbsolutePath();
			} else {
				matchTraceSaveDir = null;
			}
		}
	}
	
	public void clearMatchTrace() {
		matchTrace.clear();
	}
	
	public void enableTimestamp(boolean enable) {
		enableTimestamp = enable;
	}
	
	public void saveMatchTrace() throws IOException {
		if(matchTrace.isEmpty()) {
			return;
		}
		
		if(matchTraceSaveDir == null) {
			return;
		}
		
		int varsCount = matchTrace.get(0).getChromosome().size();
		
		saveScoreVector();
		for(int i = 1; i <= varsCount ; i++) {
			saveVariableVector(i);
		}
		createRunScript();
	}
	
	private void saveScoreVector() throws IOException {
		FileOutputStream scoreTraceFile = new FileOutputStream(matchTraceSaveDir + fileSep + "scoreTrace.m");
		OutputStreamWriter streamWriter = new OutputStreamWriter(scoreTraceFile);
		String matlabScript = "";
		
		matlabScript += "ScoreTrace = [";
		for (Specimen s: matchTrace) {
			matlabScript += s.getScore() + " ...\n";
		}
		matlabScript = matlabScript.substring(0, matlabScript.length()-5) + "];";
		
		streamWriter.write(matlabScript);
		streamWriter.close();
	}
	
	private void saveVariableVector(int varIndex) throws IOException {
		FileOutputStream varTraceFile = new FileOutputStream(matchTraceSaveDir + fileSep + "X" + varIndex + "Trace.m");
		OutputStreamWriter streamWriter = new OutputStreamWriter(varTraceFile);
		String matlabScript = "";
		
		matlabScript += "X" + varIndex + " = [";
		for (Specimen s: matchTrace) {
			matlabScript += s.getChromosome().get(varIndex-1) + " ...\n";
		}
		matlabScript = matlabScript.substring(0, matlabScript.length()-5) + "];";
		
		streamWriter.write(matlabScript);
		streamWriter.close();
	}
	
	private void createRunScript () throws IOException {
		FileOutputStream runScriptFile = new FileOutputStream(matchTraceSaveDir + fileSep + "runScript.m");
		OutputStreamWriter streamWriter = new OutputStreamWriter(runScriptFile);
		int varsCount = matchTrace.get(0).getChromosome().size();
		String matlabScript = "";
		
		matlabScript += "%% trace vector evaluation\n";
		
		matlabScript += "eval('scoreTrace');\n";
		for(int i = 0; i < varsCount ; i++) {
			matlabScript += "eval('X" + (i+1) + "Trace');\n";
		}
		
		matlabScript += "\n%% trace plot\n";
		
		matlabScript += "N = 1:" + matchTrace.size() + ";\n\n";
		
		matlabScript += "figure;\nplot(N,ScoreTrace);\ntitle('Funckja celu');"
				  	 +	"\nxlabel('Iteracja');\nylabel('Wynik');\n\n";
		
		for(int i = 0; i < varsCount ; i++) {
			matlabScript += "figure;\nplot(N,X" + (i+1) + ");\ntitle('X" + (i+1) + "');"
						 +	"\nxlabel('Iteracja');\nylabel('X" + (i+1) + "');\n\n";
		}
		
		streamWriter.write(matlabScript);
		streamWriter.close();
	}
}
