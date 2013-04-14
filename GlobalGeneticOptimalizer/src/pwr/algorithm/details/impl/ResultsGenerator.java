package pwr.algorithm.details.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import pwr.algorithm.Specimen;

//klasa ma sluzyc do generowania plikow matlabowych (skryptow), ktore maja miedzyinnym
//generowac wykres itp. Do rozszerzenia w przyszlosci mysle, ze moze sie przydac do pozniejszego
//tworzenia raportu :)

public class ResultsGenerator {
	private ArrayList<Specimen> matchTrace;
	private String matchTraceSaveFile;
	
	public ResultsGenerator() {
		matchTrace = new ArrayList<Specimen>();
	}
	
	public void addMatchToTrace(Specimen newBestMatch) {
		matchTrace.add(newBestMatch);
	}
	
	public void setMatchTraceSaveFile(String fileAddress) {
		matchTraceSaveFile = fileAddress;
	}
	
	public void clearMatchTrace() {
		matchTrace.clear();
	}
	
	public void saveMatchTraceToFile() throws IOException {
		if(matchTrace.isEmpty()) {
			return;
		}
		
		FileOutputStream matchTraceFile = new FileOutputStream(matchTraceSaveFile);
		OutputStreamWriter streamWriter = new OutputStreamWriter(matchTraceFile);
		String matlabScript = "";
		
		//Wektor liczby probek
		matlabScript += "%Score trace plot\n";
		matlabScript += "N = 1:" + matchTrace.size() + ";\n";
		
		//Generowanie wektora dla wartosci funkcji celu
		matlabScript += "ScoreTrace = [";
		for (Specimen s: matchTrace) {
			matlabScript += s.getScore() + " ";
		}
		matlabScript += "];\n";
		
		matlabScript += "figure;\nplot(N,ScoreTrace);\ntitle('Score Trace');"
					  +	"\nxlabel('Step');\nylabel('Score');\n\n";
		
		int chromosomeLength = matchTrace.get(0).getChromosome().size();
		
		//Generowanie wektorow dla zmiennych funkcji celu
		matlabScript += "%Variables trace plot\n";
		for(int i = 0; i < chromosomeLength; i++) {	
			matlabScript += "X" + (i+1) + " = [";
			for (Specimen s: matchTrace) {
				matlabScript += s.getChromosome().get(i) + " ";
			}
			matlabScript += "];\n";
			matlabScript += "figure;\nplot(N,X" + (i+1) + ");\ntitle('Trace X" + (i+1) + "');"
						  +	"\nxlabel('Step');\nylabel('X" + (i+1) + "');\n\n";
		}
		
		streamWriter.write(matlabScript);
		streamWriter.close();
	}
}
