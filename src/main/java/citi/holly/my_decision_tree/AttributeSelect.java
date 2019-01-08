package citi.holly.my_decision_tree;

import java.io.File;
import java.io.IOException;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.GainRatioAttributeEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class AttributeSelect {
//	private ArffLoader loader;
	private Instances dataSet;
//	private File arffFile;
	private int sizeOfDataset;
	private int numOfOldAttributes;
	private int numOfNewAttributes;
	private int classIndex;
	private int[] selectedAttributes;
	public AttributeSelect(){
//		sizeOfDataset = dataSet.numInstances();
//        numOfOldAttributes = dataSet.numAttributes();
//        classIndex = numOfOldAttributes - 1;
//        dataSet.setClassIndex(classIndex);
	}
	
	

	public AttributeSelect(Instances data) throws IOException {
		
//	        loader = new ArffLoader();
//	        arffFile = file;
//	        loader.setFile(arffFile);
//	        dataSet = loader.getDataSet();
		    
		    
		    dataSet = data;
	        sizeOfDataset = dataSet.numInstances();
	        numOfOldAttributes = dataSet.numAttributes();
	        classIndex = numOfOldAttributes - 1;
	        dataSet.setClassIndex(classIndex);
	}

	public void selectUsingGainRatioAttributeEval() throws Exception {
		ASEvaluation evaluator = new GainRatioAttributeEval();
		ASSearch search = new Ranker();
		AttributeSelection eval = null;
		eval = new AttributeSelection();
		eval.setEvaluator(evaluator);
		eval.setSearch(search);

		eval.SelectAttributes(dataSet);
		numOfNewAttributes = eval.numberAttributesSelected();
		selectedAttributes = eval.selectedAttributes();
		System.out.println( eval.toResultsString());
//		System.out.println(selectedAttributes);
		
		
	}
	public void selectUsingInfoGainAttributeEval() throws Exception {
		ASEvaluation evaluator = new InfoGainAttributeEval();
		ASSearch search = new Ranker();
		AttributeSelection eval = null;

		eval = new AttributeSelection();
		eval.setEvaluator(evaluator);
		eval.setSearch(search);

		eval.SelectAttributes(dataSet);
		numOfNewAttributes = eval.numberAttributesSelected();
		selectedAttributes = eval.selectedAttributes();
		System.out.println("result is " + eval.toResultsString());
		
	}


}
