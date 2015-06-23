package usrinfo;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Model {
	private double Smooth = 0.05;
	private int k;//K-NN
	private int M;//the words num; abuou 20k
	private int N;//the train data num
	private int Q;// label num in pool
	private HashMap<Integer, Double>[] train_data;//M X N array, each instance stored in one column
	private int[][] train_target;// Q X N array if the ith training instance belongs to the jth class, then train_target(j,i) equals +1, otherwise train_target(j,i) equals 0
	private double[] Prior;//Q X 1
	private double[] PriorN;//Q X 1
	private double[][] Cond;//Q X (k + 1)
	private double[][] CondN;// Q X (k + 1)
	private Set<String> stopword;
	private Map<String, Integer> tagset;
	private Map<String, Integer> wordlist = new HashMap<String, Integer>();
//	private Map<String, Double> wordIDF = new HashMap<String, Double>();
	private int T;//test num
	private HashMap<Integer, Double> test_data;//test data
	private int[]test_target;// the test target  TODO
	private Stemmer stm = null;//tranceport a word to it's root
	public Model(int k, double Smooth){
		Scanner input = null;
		stm = new Stemmer();
		try {
			this.k = k;
			this.Smooth = Smooth;
			readTagSet("./allTags.txt");
			trainWordList("./train.data");
			M = wordlist.size();
			initmatrix();
			input = new Scanner(new File("./train.data"));
			int index = 0;
			while(input.hasNext()){//read data
				String line = input.nextLine();
				String[] wandt = line.split("\\#\\$\\#");
				String[] vec = wandt[1].split("[^a-zA-Z0-9]");
				double max = 0;
				for(String s:vec){
					if(s != null && s.length() > 2){
						try{
							Integer.valueOf(s);
						}catch(NumberFormatException e){
							stm.add(s.toLowerCase().toCharArray(), s.length());
							stm.stem();
							String sp = stm.toString();
							Integer idrow =  wordlist.get(sp);	
							if(idrow == null)//遇到不在单词表中的单词直接忽略
								continue;
							int row = idrow.intValue();
							Double old = train_data[index].get(row);
							if(old == null)
								train_data[index].put(row, 1.0);
							else{								
								train_data[index].put(row, old.doubleValue() + 1);
							}
							if(train_data[index].get(row) > max)
								max = train_data[index].get(row);
						}
					}
				}
				//使用词频
				//max = vec.length;
				for(Integer hasva:train_data[index].keySet()){
					Double old = train_data[index].get(hasva);
					//Double didf = wordIDF.get(wordlist.get(hasva));
					//double idf = 0;
					//if(didf != null)
					//	idf = didf.doubleValue();
					double tf = old.doubleValue()/max;
					train_data[index].put(hasva, tf);
				}
				
				//tags
				String[] tags = wandt[2].split(",");
				for(String one:tags){
					Integer iid = tagset.get(one);
					if(iid != null){
						int tagid = iid.intValue();					
						train_target[tagid][index] = 1;
					}//else System.out.println(one);
				}
				index++;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(input!=null)
				input.close();
		}
	}
	
	public Model(String modelfilename){
		//从文件中读取模型
		stm = new Stemmer();
		try {
			Scanner modelinput = new Scanner(new File(modelfilename));
			wordlist = new HashMap<String, Integer>();
			int len = modelinput.nextInt();
			String wordstr = modelinput.nextLine();
			//System.out.println(wordstr);
			wordstr = modelinput.nextLine();
			//System.out.println(wordstr);
			String[] words = wordstr.split(" ");
			for(int i = 0; i < len * 2; i += 2){
				//System.out.println( words[i] + "???" + words.length + "\n" );
			//	if(Integer.valueOf(words[i]) == 100)
				//	System.out.println( words[i + 1]);
				//wordIDF.put(words[i+2], Double.valueOf(words[i]));
				wordlist.put(words[i+1],Integer.valueOf(words[i]));
			}
			String temp = modelinput.next();
			Smooth = modelinput.nextDouble();
			temp = modelinput.next();//M
			M = modelinput.nextInt();
			temp = modelinput.next();//N
			N = modelinput.nextInt();
			temp = modelinput.next();//k
			k = modelinput.nextInt();
			temp = modelinput.next();//Q
			Q = modelinput.nextInt();
			temp = modelinput.nextLine();
			temp = modelinput.nextLine();//target set:
			temp = modelinput.nextLine();
			String[] targets = temp.split("\\#");
			tagset = new HashMap<String, Integer>();
			for(String s:targets){
				String[] tag = s.split(",");
				tagset.put(tag[0], Integer.valueOf(tag[1]));
			}
			initmatrix();//初始化矩阵
			temp = modelinput.nextLine();//train_data
			for(int i = 0; i < N; i++){
				String line = modelinput.nextLine();
				String[] cells = line.split(" ");
				for(String s:cells){
					String[] entry = s.split(",");
					train_data[Integer.valueOf(entry[0])].put(Integer.valueOf(entry[1]), Double.valueOf(entry[2]));
				}
			}
			
			temp = modelinput.nextLine();//train_target
			for(int i = 0; i < N; i++){
				String oneline = modelinput.nextLine();
				String[] tags = oneline.split(" ");
				for(String s:tags){
					this.train_target[Integer.valueOf(s)][i] = 1;
				}
			}
			
			temp = modelinput.nextLine();//Prior
			temp = modelinput.nextLine();
			String[] po = temp.split(" ");
			for(int i = 0;i < Q; i ++){
				Prior[i] = Double.valueOf(po[i]);
			}
			
			temp = modelinput.nextLine();//PriorN
			temp = modelinput.nextLine();
			String[] pon = temp.split(" ");
			for(int i = 0;i < Q; i ++){
				PriorN[i] = Double.valueOf(pon[i]);
			}
			
			temp = modelinput.nextLine();//Cond
			for(int i = 0; i < Q; i++){
				String s = modelinput.nextLine();
				String[] line = s.split(" ");
				int j = 0;
				for(String st:line){
					Cond[i][j++] = Double.valueOf(st);
				}
			}
			temp = modelinput.nextLine();//CondN
			for(int i = 0; i < Q; i++){
				String s = modelinput.nextLine();
				String[] line = s.split(" ");
				int j = 0;
				for(String st:line){
					CondN[i][j++] = Double.valueOf(st);
				}
			}
			
			System.out.println("Model read end M:" + M + " N:" + N + " k:" + k + " Q:" + Q + " Smooth:" + Smooth);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void readTagSet(String filename){
		tagset = new HashMap<String, Integer>();
		try {
			Scanner input = new Scanner(new File(filename));
			Q =0;
			while(input.hasNext()){
				String tag = input.nextLine();
				tagset.put(tag, Q++);
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void initmatrix(){
		train_data = new HashMap[N];
		for(int i = 0; i < N; i ++){
			train_data[i] = new HashMap<Integer, Double>();
		}
		train_target = new int[Q][N];
		Prior = new double[Q];
		PriorN = new double[Q];
		Cond = new double[Q][k+1];
		CondN = new double[Q][k+1];
		
		for(int i = 0; i < Q; i++){
			Prior[i] = 0;
			PriorN[i] = 0;
			for(int j = 0; j < N; j++){
				train_target[i][j] = 0;
			}
			for(int j = 0; j <= k; j++){
				Cond[i][j] = 0;
				CondN[i][j] = 0;
			}
		}
	}
	
	private void trainWordList(String filename){
		try {
			Scanner stopinput = new Scanner(new File("./stop"));
			stopword = new HashSet<String>();
			while(stopinput.hasNext()){
				String s = stopinput.nextLine();
				stopword.add(s.toLowerCase());
			}
			Scanner input = new Scanner(new File(filename));
			String contain = null;
			int id = 0;
			N = 0;
			while(input.hasNext()){
				N++;
				//boolean flag = true;
				contain = input.nextLine();
				String[] wandt = contain.split("\\#\\$\\#");
				String[] words = wandt[1].split("[^a-zA-Z0-9]");
				for(String s:words){
					if(s != null && s.length() > 2)
						try{
							Integer.valueOf(s);
						}catch(NumberFormatException e){
							stm.add(s.toLowerCase().toCharArray(), s.length());
							stm.stem();//词根转化
							String sp =  stm.toString();
							if(stopword.contains(sp))
								continue;
							if(wordlist.putIfAbsent(sp, id) == null)
								id++;
							/*if(flag){
								Double pnum = wordIDF.get(sp);
								if(pnum == null)
									wordIDF.put(sp, 1.0);
								else
									wordIDF.put(sp, wordIDF.get(sp) + 1);
								flag = false;//每个训练集只算一次
							}*/
						}
				}
				
			}
		/*	for(String s:wordIDF.keySet()){
				double idf = Math.log(N/(wordIDF.get(s)));
				wordIDF.put(s, idf);
				System.out.print(idf + " ");
			}
			System.out.println();*/
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void test(String testdatafile, String ouputfile){//prepare T test_data & the space of test_target
		test_data = new HashMap<Integer, Double>();
		test_target = new int[Q];
		String[] tags = new String[Q];
		for(String s:tagset.keySet()){
			tags[tagset.get(s)] = s;
		}
		try {
			Scanner testinput = new Scanner(new File(testdatafile));
			DataOutputStream testoutput = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(ouputfile)));
			int index = 0;
			while(testinput.hasNext()){
				
				test_data.clear();//清空,重新开始下一条测试
				for(int i = 0; i < Q; i++){
					test_target[i] = 0;//重新刷新target
				}
					
				String line = testinput.nextLine();
				String[] entry = line.split("\\#\\$\\#");
				String[] vec = entry[1].split("[^a-zA-Z0-9]");
				double max = 0;
				for(String s:vec){
					if(s != null && s.length() > 2){
						try{
							Integer.valueOf(s);
						}catch(NumberFormatException e){
							stm.add(s.toLowerCase().toCharArray(), s.length());
							stm.stem();
							String sp = stm.toString();
							Integer idrow =  wordlist.get(sp);	
							if(idrow == null)//遇到不在单词表中的单词直接忽略
								continue;
							int row = idrow.intValue();
							Double old = test_data.get(row);
							if(old == null)
								test_data.put(row, 1.0);
							else{								
								test_data.put(row, old.doubleValue() + 1);
							}
							if(test_data.get(row) > max)
								max = test_data.get(row);
						}
					}
				}
				//使用词频
				//max = vec.length;
				for(Integer hasva:test_data.keySet()){
					Double old = test_data.get(hasva);
					//Double didf = wordIDF.get(wordlist.get(hasva));
				//	double idf = 0;
					//if(didf != null)
				//		idf = didf.doubleValue();
					double tf = old.doubleValue()/max;
					test_data.put(hasva, tf);
				}
				
				this.pridictnow();
				//TODO write a entry out
				testoutput.writeBytes(entry[0] + "#$#");
				for(int i = 0; i < Q; i++){
					if(test_target[i] == 1)
						testoutput.writeBytes(tags[i] + ",");
				}
				testoutput.writeBytes("\n");
			}
			testoutput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pridictnow(){
		double[] dist_matrix = new double[N];
		for(int j = 0; j < N; j++){
			/*double dist = 0;
			for(Integer sum:test_data.keySet()){
				Double d2 = train_data[j].get(sum);
				if(d2 == null)
					dist += test_data.get(sum) * test_data.get(sum);
				else
					dist += (test_data.get(sum) - d2) * (test_data.get(sum) - d2);
			}
			for(Integer sum:train_data[j].keySet()){
				Double d1 = test_data.get(sum);
				if(d1 == null)
					dist += train_data[j].get(sum) * train_data[j].get(sum);
			}*/
			//train_data[j].keySet()
			/*for(int p = 0; p < M; p++){
				dist += (train_data[p][i] - train_data[p][j]) * (train_data[p][i] - train_data[p][j]);
			}*/
			double dist = this.distance(test_data, train_data[j]);
			//dist = Math.sqrt(dist);
			//double dist = 1 - this.cossim(test_data, train_data[j]);
			dist_matrix[j] = dist;
		}
		double[] Neighbors_dist = new  double[k];
		int[] Neighbors_index = new int[k];
		for(int j = 0; j < k; j++){
			Neighbors_dist[j]= 100000;//a large num
		}
		for(int c = 0; c < N; c ++){
			int max = 0;
			for(int t = 0; t < k; t++){
				if(Neighbors_dist[t] > Neighbors_dist[max])
					max = t;
			}
			if(dist_matrix[c] < Neighbors_dist[max]){
				Neighbors_dist[max] = dist_matrix[c];
				Neighbors_index[max] = c;
			}
			/*for(int t = 0; t < k; t ++){
				if(dist_matrix[c] < Neighbors_dist[t]){
					Neighbors_dist[t] = dist_matrix[c];
					Neighbors_index[t] = c;
					break;
				}
			}*/
		}
		//to predict
		double[] outpri = new double[Q];
		int[] temp = new int[Q];
		for(int i = 0; i < Q; i++){
			temp[i] = 0;
			for(int t = 0; t < k; t++){
				temp[i] += train_target[i][Neighbors_index[t]];
			}
		}
		for(int i = 0; i < Q; i++){
			double Prob_in = Prior[i] * Cond[i][temp[i]];
			double Prob_out = PriorN[i] * CondN[i][temp[i]];
			if((Prob_in + Prob_out) == 0)
				outpri[i] = Prior[i];
			else
				outpri[i] = Prob_in/(Prob_in + Prob_out);
		}
		//
		int coun = 0;
		for(int i = 0; i < Q; i++){
			//System.out.print(outpri[i] + " ");
			//outpri[i] *= 2;
			if(outpri[i] < 0.5){
				test_target[i] = 0;
			}
			else{
				test_target[i] = 1;
				coun ++;
			}
		}
		if(coun < 3){//未预测出标签,直接选择最大概率的三个
			int[] max3= new int[3];
			max3[0] = 0;
			max3[1] = 1;
			max3[2] = 2;
			for(int i = 3; i < Q; i++){
				int minm = 0;
				for(int j = 0; j < 3; j++){
					if(outpri[max3[minm]] > outpri[max3[j]]){
						minm = j;
					}
				}
				if(outpri[i] > outpri[max3[minm]]){
					max3[minm] = i;
				}
			}
			test_target[max3[0]] = 1;
			test_target[max3[1]] = 1;
			test_target[max3[2]] = 1;
		//	System.out.println("the top3 is: " + max3[0] + " " + max3[1] + " " + max3[2]);
		}

	//	System.out.println();
	}
	
	
	public void train(){
		double[][] dist_matrix = new double[N][N];
		
		//computing distance
		for(int i = 0; i < N; i++){
			if(i % 1000 == 0)
				System.out.println("computing distance for instance: " + i);
			for(int j = i + 1; j < N; j++){
				/*double dist = 0;
				for(Integer sum:train_data[i].keySet()){
					Double d2 = train_data[j].get(sum);
					if(d2 == null)
						dist += train_data[i].get(sum) * train_data[i].get(sum);
					else
						dist += (train_data[i].get(sum) - d2) * (train_data[i].get(sum) - d2);
				}
				for(Integer sum:train_data[j].keySet()){
					Double d1 = train_data[i].get(sum);
					if(d1 == null)
						dist += train_data[j].get(sum) * train_data[j].get(sum);
				}
				//train_data[j].keySet()
				/*for(int p = 0; p < M; p++){
					dist += (train_data[p][i] - train_data[p][j]) * (train_data[p][i] - train_data[p][j]);
				}*/
				//dist = Math.sqrt(dist);
				double dist = this.distance(train_data[i], train_data[j]);
				//double dist = 1 - this.cossim(train_data[i], train_data[j]);
				dist_matrix[i][j] = dist;
				dist_matrix[j][i] = dist;
			}
			dist_matrix[i][i] = 10000;//a large num
		}
		
		//computing Prior & PriorN
		for(int i = 0; i < Q; i++){
			double temp_Ci = 0;
			for(int j = 0; j < N; j++){
				temp_Ci += train_target[i][j];
			}
			Prior[i] = (Smooth + temp_Ci)/(Smooth * 2 + N);
			PriorN[i] = 1 - Prior[i];
		}
		
		//computing Cond & condN
		int[][] Neighbors = new int[k][N];//used to store the index of ith instance's neighbors in Negihbors[][i];
		double[][] Neighbors_dist = new double[k][N];//
		//find neighbors
		for(int i = 0; i < N; i++){
			for(int j = 0; j < k; j++){
				Neighbors_dist[j][i] = 100000;//a large num
			}
			for(int c = 0; c < N; c ++){
				int max = 0;
				for(int t = 0; t < k; t++){
					if(Neighbors_dist[t][i] > Neighbors_dist[max][i])
						max = t;//find the max dist in nbr
				}
				if(dist_matrix[c][i] < Neighbors_dist[max][i]){
					Neighbors_dist[max][i] = dist_matrix[c][i];
					Neighbors[max][i] = c;
				}
				/*for(int t = 0; t < k; t ++){
					if(dist_matrix[c][i] < Neighbors_dist[t][i]){
						Neighbors_dist[t][i] = dist_matrix[c][i];
						Neighbors[t][i] = c;
						break;
					}
				}*/
				
			}
		/*	for(int o = 0; o < 3; o++){
				System.out.print(Neighbors[o][i] + " ");
			}
			System.out.println();*/
		}
		double[][] temp_Ci = new double[Q][k+1];
		double[][] temp_NCi = new double[Q][k+1];
		for(int i = 0; i < Q; i++){
			for(int j = 0; j <= k; j++){
				temp_Ci[i][j] = 0;
				temp_NCi[i][j] = 0;
			}
		}
		for(int i = 0; i< N; i++){
			//
			int[] temp = new int[Q];			
			for(int j = 0; j < Q; j++){
				temp[j] = 0;
				for(int t = 0; t < k; t++){
					if(train_target[j][Neighbors[t][i]] == 1)
						temp[j] += 1;
				}
			}
			
			for(int j = 0; j < Q; j++){
				if(train_target[j][i] == 1){
					temp_Ci[j][temp[j]] += 1;
				}else{
					temp_NCi[j][temp[j]] += 1;
				}
			}
			
		}
		
		for(int i = 0; i < Q; i++){
			double temp1 = 0, temp2 = 0;
			for(int j = 0; j <= k; j++){
				temp1 += temp_Ci[i][j];
				temp2 += temp_NCi[i][j];
			}
			for(int j = 0; j <= k; j++){
				Cond[i][j] = (Smooth+temp_Ci[i][j])/(Smooth*(k+1)+temp1);
				CondN[i][j] = (Smooth+temp_NCi[i][j])/(Smooth*(k+1)+temp2);
			}
		}
	}
	//private double adcossim(HashMap<Integer, Double> a, HashMap<Integer, Double> b){
		
	//	return 0;
//	}
	
	private double cossim(HashMap<Integer, Double> a, HashMap<Integer, Double> b){
		double inmul = 0;
		double alen = 0, blen = 0;
		for(Integer in:a.keySet()){
			double athe = a.get(in).doubleValue();
			alen += athe * athe;
			Double the = b.get(in);
			if(the != null){
				inmul += athe * the.doubleValue();
			}
		}
		for(Integer in:b.keySet()){
			blen += b.get(in).doubleValue() * b.get(in).doubleValue();
		}
		alen = Math.sqrt(alen);
		blen = Math.sqrt(blen);
		if(alen == 0 || blen == 0)
			return 0;
		else
			return inmul / (alen * blen);
	}
	
	private double distance(HashMap<Integer, Double> a, HashMap<Integer, Double> b){
		double dist = 0;
		for(Integer sum:a.keySet()){
			Double d2 = b.get(sum);
			if(d2 == null)
				dist += a.get(sum) * a.get(sum);
			else
				dist += (a.get(sum) - d2) * (a.get(sum) - d2);
		}
		for(Integer sum:b.keySet()){
			Double d1 = a.get(sum);
			if(d1 == null)
				dist += b.get(sum) * b.get(sum);
		}
		//train_data[j].keySet()
		/*for(int p = 0; p < M; p++){
			dist += (train_data[p][i] - train_data[p][j]) * (train_data[p][i] - train_data[p][j]);
		}*/
		dist = Math.sqrt(dist);
		return dist;
	}
	private void wirteWordList(){
		try {			
			PrintWriter output = new PrintWriter(new File("./Model.txt"));
			output.printf("%d\n", wordlist.size());//model first line is the word num
			for(String s:wordlist.keySet()){//model the next line is the word list
				//double idf = 0;
				//Double didf = wordIDF.get(s);
				//if(didf != null)
				//	idf = didf.doubleValue();
				output.printf("%d %s ", wordlist.get(s), s);
			}
			output.print("\n");
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void writeModel(){
		try {
			wirteWordList();
			DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("./Model.txt", true)));
			output.writeBytes("Smooth " + Smooth + "\n");
			output.writeBytes("M " + M + "\n");
			output.writeBytes("N " + N + "\n");
			output.writeBytes("k " + k + "\n");
			output.writeBytes("Q " + Q + "\n");
			output.writeBytes("tagset:\n");
			for(String s:tagset.keySet()){
				output.writeBytes(s + "," + tagset.get(s) + "#");
			}
			output.writeBytes("\n");
			//write train_data
			output.writeBytes("train_data:\n");
		//	for(int j = 0; j < M; j++){
			for(int i = 0; i < N; i++){
				for(Integer nuzero:train_data[i].keySet()){
					output.writeBytes(i + "," + nuzero.intValue() + "," + train_data[i].get(nuzero) + " ");
				}
				output.writeBytes("\n");
					/*if(train_data[j][i] != 0)
						output.writeBytes(i + "," + j + "," + train_data[j][i] + " ");
					*/
			}
				
	//		}
			//write train_target
			output.writeBytes("train_target:\n");
			for(int j = 0; j < N; j++){
				for(int i = 0; i < Q; i++){
					if(train_target[i][j]  == 1)
						output.writeBytes(i + " ");
				}
				output.writeBytes("\n");
			}
			output.writeBytes("Prior:\n");
			for(int i = 0;i < Q; i ++){
				output.writeBytes(Prior[i] + " ");
			}
			output.writeBytes("\n");
			output.writeBytes("PriorN:\n");
			for(int i = 0;i < Q; i ++){
				output.writeBytes(PriorN[i] + " ");
			}
			output.writeBytes("\n");
			output.writeBytes("Cond:\n");
			for(int i = 0; i < Q; i++){
				for(int j = 0; j <= k; j++){
					output.writeBytes(Cond[i][j] + " ");
				}
				output.writeBytes("\n");
			}
			output.writeBytes("CondN:\n");
			for(int i = 0; i < Q; i++){
				for(int j = 0; j <= k; j++){
					output.writeBytes(CondN[i][j] + " ");
				}
				output.writeBytes("\n");
			}
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public double hamming_loss(int[][] a, int[][] b, int len){
		double loss = 0;
		for(int i = 0; i < len; i ++){
			int aloss = 0;
			for(int j = 0; j < Q; j ++){
				if(a[j][i] != b[j][i]){
					aloss ++;
				}
			}
			loss += aloss;
		}
		return loss/(len * Q);
	}
	public class F1{
		public double macroF1;
		public double microF1;
	}
	public F1  mXcroF1(int[][] a, int[][] b, int len){
		F1 f1 = new F1();
		int tp = 0, fp = 0, tn = 0, fn = 0;
		int[] true_positives = new int[Q];
		int[] false_positives = new int[Q];
		int[] true_negatives = new int[Q];
		int[] false_negatives = new int[Q];
		for(int i = 0; i < Q; i ++){
			true_positives[i] = 0;
			false_positives[i] = 0;
			true_negatives[i] = 0;
			false_negatives[i] = 0;
		}
		for(int i = 0; i < len; i ++){
			for(int j = 0; j < Q; j ++){
			//	if(j == 0)
			//		System.out.println(a[j][i] + " " + b[j][i]);
				if(a[j][i] == 1 && b[j][i] == 1){//tp
					tp++;
					true_positives[j] ++;
				}else if(a[j][i] == 1 && b[j][i] != 1){//fn
					fn++;
					false_negatives[j] ++;
				}else if(a[j][i] != 1 && b[j][i] == 1){//fp
					fp++;
					false_positives[j] ++;
				}else{//tn
					tn++;
					true_negatives[j] ++;
				}
			}
		}
		double precision = (tp + 0.0)/(tp+fp);
		double recall = (tp + 0.0)/(tp + fn);
		f1.microF1 = 2*precision * recall / (precision + recall);
		double macrof1 = 0;
		int div = Q;
		for(int i = 0; i < Q; i ++){
			double pr = 0;
			double re = 0;
			if((true_positives[i] + false_positives[i]) != 0)
				pr = (true_positives[i] + 0.0)/(true_positives[i] + false_positives[i]);
			if((true_positives[i] + false_negatives[i]) != 0)
				re = (true_positives[i] + 0.0)/(true_positives[i] + false_negatives[i]);
			double maf1 = 0;
			if((pr + re) == 0){
				maf1 = 0;
				div --;
			}else maf1 = 2 * pr * re / (pr + re);
			macrof1 += maf1;
		//	System.out.println("macroF1:" + macrof1 + "pr:" + pr + "re:" + re);
		}
		f1.macroF1 = macrof1 / div;
		return f1;
	}
	
	public double evaluate(String truetag, String pretag){
		double av = 0;
		try {
			Scanner truetaginput = new Scanner(new File(truetag));
			Scanner pretaginput = new Scanner(new File(pretag));
			int[][] ttag = new int[Q][2000];
			int[][] ptag = new int[Q][2000];
			for(int i = 0; i < Q; i ++){
				for(int j = 0; j < 2000; j++){
					ttag[i][j] = 0;
					ptag[i][j] = 0;
				}
			}
			int len = 0;
			while(truetaginput.hasNext()){
				String tstr = truetaginput.nextLine();
				String pstr = pretaginput.nextLine();
				String[] tt = tstr.split("\\#\\$\\#");
				String[] pt = pstr.split("\\#\\$\\#");
				String[] tts = tt[2].split(",");
				String[] pts = null;
				if(pt.length > 1)
					pts = pt[1].split(",");
				for(String s:tts){
				//	System.out.println(s);
					Integer id = tagset.get(s);
					if(id != null)
						ttag[id.intValue()][len] = 1;
				}
				if(pts != null){
					for(String s:pts){
						ptag[tagset.get(s)][len] = 1;
					}
				}
				len ++;
			}
			double hloss = this.hamming_loss(ttag, ptag, len);
			F1 f = this.mXcroF1(ttag, ptag, len);
			System.out.println("hamming loss:" + hloss + " macro_f1:" + f.macroF1 + " micro_f1:" + f.microF1);
			av = 2 + 10*hloss - f.macroF1 - f.microF1;//to
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return av;
	}
	
	public static void main(String[] args){
		/*double min_loss = 1000;
		int nbr = 0;
		double smooth = 0;
		for(int i = 5; i <= 5; i += 2){
			for(double sm = 1; sm < 10; sm += 1){
				Model m1 = new Model(i, sm);
				m1.train();
				m1.writeModel();
				Model m = new Model("./Model.txt");
				m.test("./test.data", "./test.output");
				double a = m.evaluate("./test.data", "./test.output");
				if(a < min_loss){
					nbr = i;
					smooth = sm;
					min_loss = a;
				}
			}
		}
		System.out.println("the max nbr is " + nbr + " smooth is " + smooth);
	*/
	
		/*Model m1 = new Model(5, 6);
		m1.train();
		m1.writeModel();*/
			System.out.println("input \"Test\" to test, \"Train\" to train");
			Scanner S = new Scanner(System.in);
			String com = S.next();
			if(com.equals("Test")){
				System.out.println("Starting read the model named Model.txt");
				Model m = new Model("./Model.txt");
				System.out.println("Starting test");
				m.test("./test.data", "./result.txt");
				//m.evaluate("./test.data", "./result.txt");
				System.out.println("Done~~~~");
			}else if(com.equals("Train")){
				Model m1 = new Model(5, 6);
				m1.train();
				m1.writeModel();
			}else{
				System.out.println("ERROR COMMAND");
			}
			//m.evaluate("./test.data", "./test.output");
		
	}
}
