package clplayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;



public class readPicTosend {
	private int numResult = 6;
	private double jieR = 100.0;
	private String trainFilePath = "./train.data";
	private  String tagsFilepath = "./allTags.txt";
	private  String vectorFilePath = "./trainVector.txt"; 
	private  int totalDocCount = 0;//总的文档数
	private  int totalWordCount = 0;//总的单词数（包括重复）
	private  int toatalWordKind = 0;//单词有多少种
	private  HashMap<String, Integer> hasWords = new HashMap<String, Integer>();//存储所有出现的词汇种类
	private  HashMap<String,String> hasTrain = new HashMap<String, String>() ;//存储训练文本数据和标签信息
	private  HashMap<String, Integer> hasTagCount  = new HashMap<String, Integer>();//每一个标签标号存储
	private  HashMap<String,ArrayList<String[]>> tagAllString =
			new HashMap<String,ArrayList<String[]>>();//存储所有的Tag和一个tag下的所有的文章
	private  HashMap<String, Integer> hasWordsLetter = new HashMap<String, Integer>();//存储所有出现的词汇种类 
	public  void firstDeal() throws IOException {
		// TODO 自动生成的方法存根
		// 添加说明文字中的单词，标号
		System.out.println("Dealing tagsfile...");
		dealTagCount(tagsFilepath);
		System.out.println("Dealing trainfile...");
		dealTrainFile(vectorFilePath);
		dealTrainFileLetter(trainFilePath);
		System.out.println("Dealing tag's string...");
		dealTagAllString();
		/*System.out.println("finish!!");
		System.out.println("totalDocCount:"+totalDocCount+" totalWordCount:"+totalWordCount
				+" toatalWordKind:"+toatalWordKind);*/
	}
	public int isBiger(Double[] list, double d){
		for(int i  =0 ; i< list.length; i++){
			if(list[i]<d){
				list[i] = d;
				return i;
			}
		}
		return -1;
	}
	public String dealTestDXS(String testList, readPicTosend rrf) {
		StringBuilder result  = new StringBuilder();
		Double[] tempDbl =  new Double[numResult];
		String[] tempStr = new String[numResult];
		for(int i = 0; i< tempDbl.length; i++){
			tempDbl[i] = 0.0;
			tempStr[i] = null;
		}
		// System.out.println(it.next());
		String testTxt = testList;
		Iterator<String> tagAllStringIt = hasTagCount.keySet().iterator();
		while (tagAllStringIt.hasNext()) {
			// System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			String tag = (String) tagAllStringIt.next();
			// System.out.println("prerdict if is " + tag + "or no!");
			// 假如是此标签，算出pwci/yes
			double pciYes = rrf.computeTagPci(tag, "yes");
			double pwciYes = rrf.computeTagPwci(testTxt, tag, "yes");
			double pciwyes = pciYes * pwciYes;
			int r = isBiger(tempDbl,pciwyes);
			if(r>=0){
				tempStr[r] = tag;
				tempDbl[r] = pciwyes;
			}
			// 假如不是此标签，算出pwci/no
			/*double pciNo = rrf.computeTagPci(tag, "no");
			double pwciNo = rrf.computeTagPwci(testTxt, tag, "no");
			double pciwno = pciNo * pwciNo;*/
			/*if ( pciwyes >0.1) {
				System.out.println("pciyes:" + pciYes + " pwciyes:" + pwciYes);
				System.out.println(tag + "| pciw/yes: " + pciwyes);
				System.out.println("pcino:" + pciNo + " pwcino:" + pwciNo);
				System.out.println(tag + "| pciw/no: " + pciwno);
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@select tag: "
								+ tag);
				result.append(tag + ",");
			}*/
			
		}
		for(int i =0; i<tempStr.length; i++){
			result.append(tempStr[i]+",");
			if(i+1<tempStr.length)
			if((tempDbl[i]/tempDbl[i+1]) > jieR)
				break;
		}
		return result.toString();
	}
	public double computeTagPwci(String testTxt,String tag,String yesOrno){
		double pwci =1;
		int tagWords = 0;
		ArrayList<String[]> arList = tagAllString.get(tag);
		for (Iterator<String[]> it = arList.iterator(); it.hasNext();) {
			String[] group = (String[])it.next();
			for(int k = 0; k<group.length; k++){
				String[] s = group[k].split(" ");
				tagWords += s.length;
			}
		}
		if (yesOrno.equals("yes")) {
			String[] wordList = testTxt.split(" ");
			Double[] pwcList = new Double[wordList.length];
			for(int i = 0; i<wordList.length; i++)
				pwcList[i] = 1.0;
			double divM = tagWords + toatalWordKind;
			for (int i = 0; i < wordList.length; i++) {
				String wi = wordList[i];
				int countAlltxt = 0;
				String wiNum;
				if(hasWordsLetter.containsKey(wi))
					wiNum = Integer.toString(hasWordsLetter.get(wi));
				else continue;
				//System.out.println("wiNum:"+wiNum);
				if(hasWords.containsKey(wiNum)){
					for (Iterator<String[]> it = arList.iterator(); it.hasNext();) {
						String[] group = (String[]) it.next();
						for(int j = 0; j<group.length; j++){
							if(wiNum.equals(group[j])){
								countAlltxt ++;
								break;
							}
						}
					}
					//System.out.println("yes countAlltxt"+countAlltxt);
					double id = 10000 * (countAlltxt + 1);
					pwcList[i] = id / divM;
				}
				 //System.out.println(wi+
				// " countwords:"+countAlltxt+" pwcList:"+pwcList[i]);
			}
			for (int p = 0; p < pwcList.length; p++) {
				// System.out.println("yes"+pwcList[p]);
				pwci *= pwcList[p];
			}
			if(pwci == 1.0)
				return 0;
			return pwci;
		}
		else if (yesOrno.equals("no")) {
	 		String[] wordList = testTxt.split(" ");
	 		Double[] pwcList = new Double[wordList.length];
	 		for(int i = 0; i<wordList.length; i++)
				pwcList[i] = 1.0;
			double notTagWords = totalWordCount - tagWords;
			double divM = notTagWords + toatalWordKind;
			for (int i = 0; i < wordList.length; i++) {
				String wi = wordList[i];
				int countAlltxt = 0;
				String wiNum;
				if(hasWordsLetter.containsKey(wi))
					wiNum = Integer.toString(hasWordsLetter.get(wi));
				else continue;
				//System.out.println("wiNum:" + wiNum);
				if (hasWords.containsKey(wiNum)) {
					Iterator<String> tagAllStringIt = tagAllString.keySet().iterator();
					while (tagAllStringIt.hasNext()) {
						String tagit = (String) tagAllStringIt.next();
						if (!tagit.equals(tag)) {
							ArrayList<String[]> tmpList = tagAllString.get(tagit);
							for (Iterator<String[]> it = tmpList.iterator(); it.hasNext();) {
								String[] group = (String[]) it.next();
								for (int k = 0; k < group.length; k++) {
									if(wiNum.equals(group[k])){
										countAlltxt ++;
										break;
									}
								}
							}
						}
					}
				}
			
				//System.out.println("no countAlltxt"+countAlltxt);
				pwcList[i] = 10000 * (countAlltxt + 1) / divM;
			}
			for (int p = 0; p < pwcList.length; p++) {
				 //System.out.println("no:"+pwcList[p]);
				pwci *= pwcList[p];
			}
			//System.out.println("pwci no tag"+ pwci);
			if(pwci == 1.0)
				return 0;
			return pwci;
		}
		return 0;
	}
	
	
	public   double computeTagPci(String tag,String yesOrno){
		double pci = 0;
		int tagAllWords = 0;
		//System.out.println("totalWordCount:"+totalWordCount);
		int totalWords = totalWordCount;
		Iterator<String> tagAllStringIt = tagAllString.keySet().iterator();
		while(tagAllStringIt.hasNext()){
			String myTag =(String)tagAllStringIt.next();
			if(myTag.equals(tag)){
				ArrayList<String[]> ar = tagAllString.get(tag);
				for(Iterator<String[]> it2 = ar.iterator();it2.hasNext();){
		             String[] g = it2.next();
		             tagAllWords += g.length;
		        }break;
			}
		}
		//System.out.println(" tagAllWords: "+tagAllWords+"totalWords: "+ totalWords);
		if(tagAllWords == 0){
			//System.out.println(tag+": tagAllWords: "+tagAllWords+"totalWords: "+ totalWords);
			if(yesOrno.equals("yes"))
				pci = 0;
			else if(yesOrno.equals("no"))
				pci = 1;
			return pci;
		}
		
		if(yesOrno.equals("yes")){
			double id = 1000*tagAllWords;
			pci = id/totalWords;
			//System.out.println("yes pci:"+pci);
		}
		else if(yesOrno.equals("no")){
			double id = 1000*(totalWords-tagAllWords);
			pci = id/totalWords;
			//System.out.println("no pci:"+pci);
		}
		return  pci;
	}
	
	
	public  void dealTagAllString(){
		Iterator<String> tagd = hasTagCount.keySet().iterator();
		while(tagd.hasNext()){
			String tag = (String)tagd.next();
			ArrayList<String[]> ar = new ArrayList<String[]>();//对每一个tag。在tagAllString中有一个对应的ArrayList<String>
			
			//System.out.println(tag+ ": ");
			Iterator<String> traind = hasTrain.keySet().iterator();
			while (traind.hasNext()) {
				String txt = (String) traind.next();
				String tags = hasTrain.get(txt);
				if (tags.equals(tag)) {
					//System.out.println(txt+"! !"+tag);
					ar.add(txt.split(" "));
				}
			}
			tagAllString.put(tag, ar);
		}
		
		
	}
	public  void dealTrainFile(String readPath) throws IOException{
		File trainFile = new File(readPath);
		InputStreamReader read = new InputStreamReader(new FileInputStream(trainFile));//考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		int wordsCount = 0;
		totalDocCount = 0;//文档总数
		String tmp = null;
		while ((tmp = bufferedReader.readLine()) != null) {
			String[] temp1 = tmp.split("\\#\\$\\#");
			//temp1[1]为描述内容，同时也是hash的key，而temp1[2]是tag。也就是存储的内容
			//System.out.println(txt);
			String txt = temp1[1];
			hasTrain.put(txt, temp1[0]);
			String[] analysisRe = txt.split(" ");
			int c = analysisRe.length;
			totalWordCount += c;
			totalDocCount += 1;
			for (int i = 0; i < c; i++) {
				// 若尚无此单词，添加到词条
				if (!hasWords.containsKey(analysisRe[i])) {
					hasWords.put(analysisRe[i], wordsCount);
					wordsCount++;
				}
			}
		}
		toatalWordKind = wordsCount;
		read.close();
	}
	public  void dealTrainFileLetter(String readPath) throws IOException{
		File trainFile = new File(readPath);
		InputStreamReader read = new InputStreamReader(new FileInputStream(trainFile));//考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		int wordsCount = 0;
		String tmp = null;
		while ((tmp = bufferedReader.readLine()) != null) {
			String[] temp1 = tmp.split("\\#\\$\\#");
			String txt = "abdcd";//stopAnalyzer.stopAnaNotCountOne(temp1[1]);
			String[] analysisRe = txt.split(" ");
			int c = analysisRe.length;
			for (int i = 0; i < c; i++) {
				// 若尚无此单词，添加到词条
				if (!hasWordsLetter.containsKey(analysisRe[i])) {
					hasWordsLetter.put(analysisRe[i], wordsCount);
					wordsCount++;
				}
			}
		}
		read.close();
	}
	public  void dealTagCount(String readPath)throws FileNotFoundException {
		File tagFile = new File(readPath);
		Scanner input = new Scanner(tagFile);
		int count = 0;
		while (input.hasNextLine()) {
			String s = input.nextLine();
			hasTagCount.put(s, count);
			count++;
		}
		input.close();
	}
	
}









