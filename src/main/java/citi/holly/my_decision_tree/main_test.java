package citi.holly.my_decision_tree;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.pmml.jaxbbindings.TrainingInstances;

public class main_test {
	
	public static Instances LoadData(String file) throws Exception {
		DataSource source =new DataSource(file);
		Instances data = source.getDataSet();
	    if (data.classIndex() == -1){
	    	data.setClassIndex(data.numAttributes() - 1);
	    	}
	    
	    
		return data;
	}
	
	public static void main(String[] args) throws Exception {
		AttributeSelect select = new AttributeSelect();
		
		Instances data = LoadData("/Users/ling/Desktop/Logic/csv_data/4.1Complete-#of Processes Securities.csv");
				
		select = new AttributeSelect(data);
		select.selectUsingGainRatioAttributeEval();	
		System.out.println("===   Classifier is trained   ===");
		
		Classifier classifier = new Classifier();
		classifier.learn(data);
		((citi.holly.my_decision_tree.Classifier)classifier).visualize();
//		Thread.currentThread().sleep(300000);

		

	}
	
	

}
