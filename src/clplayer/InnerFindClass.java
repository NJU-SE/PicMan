package clplayer;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

import javax.swing.JOptionPane;
public class InnerFindClass{
	//data
	 String [][] dictionaryArray ;//һ�������������洢��Ԫ���ֱ�ΪID�����е��ʣ����꣬���н���
	 int numberOfWords = 0;//�洢�ж�����
	 int [][] listOfWordPersition = new int[26][3];//��ĸ��ʼ�ĵط��ͽ����ĵط�
	 
	 public  InnerFindClass()throws Exception{
		 buildWordList();
		 buildFindList();
	 }
	 
	public int  getNumOfWord(){
		return numberOfWords;
	}
	public  void buildWordList()throws Exception
	{
		System.out.println("Test begin!");
		File sourceFile = new File("dictionary.txt");
		Scanner input = new Scanner(sourceFile);
		if(!sourceFile.exists())
		{
			JOptionPane.showMessageDialog(null, "there is a misstake in the program,file input error!lease contact the builder!\n"+
			 "15850776828");
			System.exit(0);
		}
		StringBuilder  dictionaryString = new StringBuilder();
		//Scanner inputKuo = new Scanner(System.in);
		input.nextLine();
		while(input.hasNext()){
			dictionaryString.append(input.nextLine()+'\n');
			numberOfWords++;
		} 
		
		input.close();
		input = new Scanner(sourceFile);
		
		dictionaryArray =new String [numberOfWords][2];//һ�������������洢��Ԫ���ֱ�ΪID�����е��ʣ����꣬���н���
		String s;
		s = input.nextLine();
		
		for(int i = 0;i<numberOfWords;i++)//ת����Ϊstring����
		{
			// s= input.next();
			//dictionaryArray[i][0] = s; 
			
			//System.out.printf("%s",dictionaryArray[i][0]);
			s=input.nextLine();
			s=s.trim();
			dictionaryArray[i] = s.split("\t");
			dictionaryArray[i][0].trim();
			dictionaryArray[i][1].trim();
			//dictionaryArray[i][1].replaceAll("...", " sth "); //why not 
			//System.out.printf("%s ",dictionaryArray[i][0]);
			//System.out.printf("%s ",dictionaryArray[i][1]);
			s=s.replace(dictionaryArray[i][0] , " ");
			s=s.replace(dictionaryArray[i][1] , " ");
			s = s.trim();
			dictionaryArray[i][2] = s;
			//System.out.printf("%s \n",dictionaryArray[i][2]);
		}
		//for(int i=0;i<numberOfWords;i++)
	//	{
	//	    System.out.println(dictionaryArray[i][1].charAt(0)+"00");
	//	}
		
		/*for(int i = 0;i<26;i++)
			System.out.println(i+" "+listOfWordPersition[i][0] +" "+ listOfWordPersition[i][1]);
		//System.out.printf("%d",numberOfWords);*/
		input.close();
	}
	
	public void buildFindList()//������a~b������λ������
	{
		char letterTemp = 'a';
		int persi = 0;
		//int inTo = 1;
		listOfWordPersition[persi][0] = 0;
		for(int i=0;i<numberOfWords;i++)
		{
		    if( Character.toLowerCase( dictionaryArray[i][1].charAt(0)) != letterTemp )//�����´��ܲ���˵��һ���д�д��ͷ��ѽ��˵�඼���ᰡ
			{
				listOfWordPersition[persi][1] = i-1;
				persi ++;
				listOfWordPersition[persi][0] = i;
				letterTemp = Character.toLowerCase( dictionaryArray[i][1].charAt(0));
				//System.out.printf("%c\n",letterTemp);
			}
		}
		listOfWordPersition[persi][1] = numberOfWords-1;
	}
	public String accurateFind(String source)//String ��ƥ��,��׼ƥ�䣬һ�θ���һ������
	{
		source = source.trim();
		if(source.getBytes().length != source.length() )
			return "error input! Your string has include other letter such as Chiness!";
		source = source.toLowerCase();
		Pattern p = Pattern.compile(".*\\d+.*");//�ж��Ƿ�������
		Matcher m = p.matcher(source);
		if (m.matches())
           return "error input! Your string has include numbers";
			
		int fisrtLetter = (int) source.charAt(0) - (int) 'a';
		//System.out.println(listOfWordPersition[fisrtLetter][0]+" "+listOfWordPersition[fisrtLetter][1]);
		//�۰����Ԫ��
		String s = " ";
		int highPersition = listOfWordPersition[fisrtLetter][1];
		int lowPersition = listOfWordPersition[fisrtLetter][0];
		int persition = (highPersition + lowPersition) /2;
		while(highPersition >= lowPersition && 
				highPersition<=listOfWordPersition[fisrtLetter][1] && lowPersition>= listOfWordPersition[fisrtLetter][0])
		{
			persition = (highPersition + lowPersition) /2;
			if(dictionaryArray[persition][1].equals(source) == false)//�ַ�����ƥ��
			{
				if(dictionaryArray[persition][1].compareTo(source) > 0)
					highPersition = persition-1;
				else if(dictionaryArray[persition][1].compareTo(source) < 0)
					lowPersition  =persition+1;
			}
			else
			{ 
				s = dictionaryArray[persition][1] +"  "+ dictionaryArray[persition][2];
				break;
			}
		}
		if(!(highPersition >= lowPersition && 
				highPersition<=listOfWordPersition[fisrtLetter][1] && lowPersition>= listOfWordPersition[fisrtLetter][0]))
			s = "Not such word";
		return s;
	}
	
	
	
	
	
	public  String[] vagueFind(String source)//ģ�����ң��ṩ5��������ĵ��ʻ����Ǵ���
	{
		String [] arrayResult = new String[5];
		source = source.toLowerCase();
		source = source.trim();
		if(source.getBytes().length != source.length() )
			return arrayResult;
		
		Pattern p = Pattern.compile(".*\\d+.*");//�ж��Ƿ�������
		Matcher m = p.matcher(source);
		if (m.matches())
           return arrayResult;
		
		// �ҵ�һ��ʼ��������ĸ�Ĵʵ�,˳����ң����ص�һ�γ��ֵĵ���ƥ���еĺ����������
		int fisrtLetter = (int) source.charAt(0) - (int) 'a';
		//String resultList = "";
		p = Pattern.compile(source+".*");
		for(int i = listOfWordPersition[fisrtLetter][0]; i<= listOfWordPersition[fisrtLetter][1]; i++)
		{
			m=p.matcher(dictionaryArray[i][1]);
			if(m.matches())
			{
				
				for( int j =0;j<5;j++){
					if(j+i<listOfWordPersition[fisrtLetter][1])
					{
						arrayResult[j]= dictionaryArray[i+j][1];
					}
					else break;
				}
				
				break;
			}
			else continue;
		}
		
		return arrayResult;
	}
	
}



















