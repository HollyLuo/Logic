package citi.holly.my_decision_tree;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class Classifier {
	protected J48 tree;
	private Instances trainData=null;
    private Evaluation evaluation=null;
	private static final Logger logger = LoggerFactory.getLogger(Classifier.class);	

	
//	public Instances loadData(String file) throws Exception {
//		Instances data = DataSource.read(file);
//	    if (data.classIndex() == -1)
//	    	data.setClassIndex(data.numAttributes() - 1);
//	    
//		logger.info("Loaded model: {}",file);	
//		return data;
//	}
	
	public void learn(Instances trainData) throws Exception {
		this.trainData = trainData;
		try {			
			List<String> options = new ArrayList<>();
			//options.add("-U");            // unpruned tree
			//options.add("-C 0.05");       // confidence threshold for pruning. (Default: 0.25)
		    //options.add("-M 1");          // minimum number of instances per leaf. (Default: 2)
		    //options.add("-R");            // use reduced error pruning. No subtree raising is performed.
		    //options.add("-N 3");          // number of folds for reduced error pruning. One fold is used as the pruning set. (Default: 3)
		    //options.add("-B");            // Use binary splits for nominal attributes.
		    //options.add("-S");            // not perform subtree raising.
		    //options.add("-L");            // not clean up after the tree has been built.
		    //options.add("-A");            // if set, Laplace smoothing is used for predicted probabilites.
		    //options.add("-Q");            // The seed for reduced-error pruning.
			
			System.out.println(trainData.classAttribute());
			this.tree = new J48();
		    //tree.setOptions(options.toArray(new String[options.size()]));  		    
			tree.buildClassifier(trainData);
			logger.info("J48 Classifier is trained");
			
			//保存模型
//			 SerializationHelper.write("Tree.model", tree);
			
			// user trainData as testData
			System.out.println();
			System.out.println("===   Test The Trained Model   ===");
			Instances testData=new Instances(trainData);
			selfTestResult(tree,testData);
			
//			Evaluation eval = new Evaluation(testData);
//			eval.crossValidateModel(tree, testData, 10, new Random(5));
//			System.out.println(eval.toSummaryString("\nResult", false));
//			System.out.println(eval.toClassDetailsString());
//			System.out.println(eval.toMatrixString());
	
			
		} catch (IOException e) {
			logger.error("Error IO", e);
		} catch (Exception e) {
			logger.error("Problem found when training", e);
		}
		System.out.println();
		System.out.println("===   Predict Using The Trained Model   ===");
		 for(int i=0;i<trainData.numInstances();i++){
            Instance sample=trainData.instance(i);
            try {
//       			  Instance sample = trainData.instance(12);
       			  int trueSampleClass = (int) sample.classValue();
       			  System.out.println(sample);
       		      double res = tree.classifyInstance(sample);
       		      System.out.println("Predict: "+ trainData.classAttribute().value((int) res));
//       		      System.out.println("True: "+ trueSampleClass);
       		      System.out.println("True: "+ trainData.classAttribute().value(trueSampleClass));
       		     } catch (Exception e) {
       		            e.printStackTrace();
       		}
          }
		
		
	}
	
	
	private void selfTestResult(J48 tree2, Instances testData) throws Exception {
		 Instance tempInstance=null;
         int count=0;
         for(int i=0;i<testData.numInstances();i++){
             tempInstance=testData.instance(i);
             if(tree2.classifyInstance(tempInstance)!=tempInstance.classValue()){
                 count++;
                 System.out.println("第"+i+"个样本实例分类错误!");
             }
         }
         System.out.println("有"+count+"个样本分类错误!");
         
         Evaluation eval = new Evaluation(testData);
         eval.evaluateModel(tree2, testData);
		 System.out.println(eval.toSummaryString("\nResult", false));
//		 System.out.println(eval.toClassDetailsString());
//		 System.out.println(eval.toMatrixString());
         
	
    }


	public void visualize() throws Exception{
		// display classifier
	     final javax.swing.JFrame jf = 
	       new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
	     jf.setSize(1500,400);
	     jf.getContentPane().setLayout(new BorderLayout());
	     TreeVisualizer tv = new TreeVisualizer(null,((J48)tree).graph(),new PlaceNode2());
	     jf.getContentPane().add(tv, BorderLayout.CENTER);
	     jf.addWindowListener(new java.awt.event.WindowAdapter() {
	       public void windowClosing(java.awt.event.WindowEvent e) {
	         jf.dispose();
	       }
	     });
	 
	     jf.setVisible(true);
	     tv.fitToScreen();
	}
	

}
