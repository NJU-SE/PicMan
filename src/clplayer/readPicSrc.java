package clplayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

//import org.apache.lucene.index.Term;

//import participle.stopAnalyzer;

public class readPicSrc {
	private static String trainFilepath = "./trainingset/train.data";
	private static String tagsFilepath = "./trainingset/allTags.txt";
	private static String vectorPath = "trainVector.txt";
	private static int totalDocCount = 0;
	private static HashMap<String, Integer> hasWords = new HashMap<String, Integer>();//�洢���г��ֵĴʻ�
	private static HashMap<String,String> hasTrain = new HashMap<String, String>() ;//�洢ѵ���ı����ݺͱ�ǩ��Ϣ
	private static HashMap<String,String> hasNumTrain = new HashMap<String, String>();
	private static HashMap<String, Integer> hasTagCount  = new HashMap<String, Integer>();//ÿһ����ǩ��Ŵ洢
	public static void readTraindata(String readPath) throws IOException{
		
		
		// ���˵�������еĵ��ʣ����
		System.out.println("Dealing tagsfile...");
		hashTagCount(tagsFilepath);
		System.out.println("Dealing trainfile...");
		dealTrainFile(trainFilepath);
		//���ֻ�ÿһ���ı���String
		System.out.println("numberLessTraining...");
		numberLessTrain();
		//��ÿһ��tag������ѵ���ı���������������
		System.out.println("Vectoring each tags...");
		vectorDeal();
		
		// ��������ÿһ������˵���еĹؼ��ʽ�����������
		// ����ǩ���ֻ�
		//��ÿһ���ı��Ķ�Ӧ��ǩ��ÿһ���ؼ���CHI�ͳ�ƣ�����ABCD��ֵ
		
	}
	public static void numberLessTrain(){
		Iterator<String> train = hasTrain.keySet().iterator();
		while(train.hasNext()){
			String txtString = (String)train.next();
			String tags = hasTrain.get(txtString);
			StringBuilder sbTxt = new StringBuilder();
			String[] txtArgs = txtString.split(" ");
			for(int i = 0; i < txtArgs.length; i++){//�滻��Ϊ����
				sbTxt.append(Integer.toString(hasWords.get(txtArgs[i]))+ " ");
			}
			//System.out.println(sbtag.toString()+sbTxt.toString());
			hasNumTrain.put(sbTxt.toString(),tags);
		}
	}
	public static void vectorDeal() throws FileNotFoundException{
		File outVector = new File(vectorPath);
		PrintWriter outFile = null; 
		outFile = new PrintWriter(outVector); 
		
		System.out.println(totalDocCount);
		Iterator<String> tagd = hasTagCount.keySet().iterator();
		while(tagd.hasNext()){//��ÿһ��tag���������е�����
			
			String tag = (String)tagd.next();
			ArrayList<String> tagTxt = findTagTxt(tag);
			ArrayList<String> notTagTxt = findNotTagTxt(tag);
			System.out.println("tagTxt:"+tagTxt.size()+" notTagTxt:"+notTagTxt.size());
			Iterator<String> hashTrainIt = hasNumTrain.keySet().iterator();
			while(hashTrainIt.hasNext()){
				String txtNum = (String)hashTrainIt.next();
				String txtTagNum = hasNumTrain.get(txtNum);
				if(txtTagNum.indexOf(tag) != -1){
					outFile.write(tag+"#$#");
					String[] txtNumArgs = txtNum.split(" ");
					int countA = 0;
					int countB = 0;
					int countC = 0;
					int countD = 0;
					//HashMap<String,Double> vectorList = new HashMap<String,Double>();
					double[][] listVector =  new double[txtNumArgs.length][2];
					for(int j = 0; j<txtNumArgs.length;j++){
						countA = countA(tagTxt,txtNumArgs[j]);
						countB = countB(notTagTxt,txtNumArgs[j]);
						countC = countC(tagTxt,txtNumArgs[j]);
						countD = countD(notTagTxt,txtNumArgs[j]);
						//System.out.println("hihao"+" "+countA+" "+countB+" "+countC+" "+countD);
						if(countA == 0)
							countA = 1;
						if(countB == 0)
							countB = 1;
						if(countC == 0)
							countC = 1;
						if(countD == 0)
							countD = 1;
						int countAll = countA+countB+countC+countD;
						double chi1 = countAll*(countA*countD - countC*countB)/((countA+countC)*(countB+countD));
						double chi2 = countAll*(countA*countD - countC*countB)/((countA+countB)*(countC+countD));
						double chi = chi1*chi2/countAll;
						listVector[j][0] =  Integer.parseInt(txtNumArgs[j]);
						listVector[j][1] = chi;
					}
					for(int i =0; i<8;i++){
						int maxIndex = i;
						for(int j =i;  j<listVector.length; j++){
							if(listVector[maxIndex][1]<listVector[j][1])
								maxIndex = j;
						}
						double tmp1 = listVector[i][0];
						double tmp2 = listVector[i][1];
						listVector[i][0] = listVector[maxIndex][0];
						listVector[i][1] = listVector[maxIndex][1];
						listVector[maxIndex][0] = tmp1;
						listVector[maxIndex][1] = tmp2;
					}
					StringBuilder sb = new StringBuilder();
					for(int i =0; i<8; i++)
						sb.append(" "+(int)listVector[i][0]);
					
					outFile.write(sb.toString()+"\n");
				}
			}
		}
		outFile.flush(); 
		outFile.close();
	}
	
	public static ArrayList<String> findTagTxt(String tag){
		ArrayList<String> tagTxt = new ArrayList<String>();
		Iterator<String> hashTrainIt = hasNumTrain.keySet().iterator();
		while(hashTrainIt.hasNext()){
			String txtNum = (String)hashTrainIt.next();
			String txtTagNum = hasNumTrain.get(txtNum);
			if(txtTagNum.indexOf(tag) != -1){
				tagTxt.add(txtNum);
			}
		}
		return tagTxt;
	}
	public static ArrayList<String> findNotTagTxt(String tag){
		ArrayList<String> tagTxt = new ArrayList<String>();
		Iterator<String> hashTrainIt = hasNumTrain.keySet().iterator();
		while(hashTrainIt.hasNext()){
			String txtNum = (String)hashTrainIt.next();
			String txtTagNum = hasNumTrain.get(txtNum);
			if(txtTagNum.indexOf(tag) == -1){
				String[] s=txtTagNum.split(" ");
				for(int i =0; i<s.length; i++)
					tagTxt.add(txtNum);
			}
		}
		return tagTxt;
	}
	public static int countA(ArrayList<String> tagTxt,String word){
		int count = 0;
		for(int k = 0; k<tagTxt.size(); k ++){
			if(tagTxt.get(k).indexOf(word) != -1){
				count ++;
			}
		}
		return count;
	}
	public static int countB(ArrayList<String> tagTxt,String word){
		int count = 0;
		for(int k = 0; k<tagTxt.size(); k ++){
			if(tagTxt.get(k).indexOf(word) != -1){
				count ++;
			}
		}
		return count;
	}
	public static int countC(ArrayList<String> tagTxt,String word){
		int count = 0;
		for(int k = 0; k<tagTxt.size(); k ++){
			if(tagTxt.get(k).indexOf(word) == -1){
				count ++;
			}
		}
		return count;
	}
	public static int countD(ArrayList<String> tagTxt,String word){
		int count = 0;
		for(int k = 0; k<tagTxt.size(); k ++){
			if(tagTxt.get(k).indexOf(word) == -1){
				count ++;
			}
		}
		return count;
	}
	public static void numberlessChange(){
		Iterator<String> it = hasTrain.keySet().iterator();  
        while(it.hasNext()) {  
            String key = (String)it.next();  
            String []tags = hasTrain.get(key).split(",");
            //System.out.println(key); 
            
        }
	}
	
	public static void dealTrainFile(String readPath) throws IOException{
		File trainFile = new File(readPath);
		InputStreamReader read = new InputStreamReader(new FileInputStream(trainFile));//���ǵ������ʽ
		BufferedReader bufferedReader = new BufferedReader(read);
		int wordsCount = 0;
		totalDocCount = 0;//�ĵ�����
		String tmp = null;
		while ((tmp = bufferedReader.readLine()) != null) {
			String[] temp1 = tmp.split("\\#\\$\\#");
			//temp1[1]Ϊ�������ݣ�ͬʱҲ��hash��key����temp1[2]��tag��Ҳ���Ǵ洢������
			String txt = "abdc";//stopAnalyzer.stopAnaNotCount(temp1[1]);
			//System.out.println(txt);
			hasTrain.put(txt, temp1[2]);
			String[] analysisRe = txt.split(" ");
			totalDocCount += temp1[2].split(",").length;
			//System.out.println(temp1[2] + temp1[2].split(",").length);
			for (int i = 0; i < analysisRe.length; i++) {
				// �����޴˵��ʣ���ӵ�����
				if (!hasWords.containsKey(analysisRe[i])) {
					hasWords.put(analysisRe[i], wordsCount);
					wordsCount++;
				}
			}
		}
		read.close();
	}
	public static void hashTagCount(String readPath)throws FileNotFoundException {
		File tagFile = new File(readPath);
		Scanner input = new Scanner(tagFile);
		int count = 0;
		while (input.hasNextLine()) {
			String s = input.nextLine();
			hasTagCount.put(s, count);
			count++;
		}
		// System.out.println(has.toString());
		input.close();
	}
}
