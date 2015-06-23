package usrinfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class newFileHandler 
{
	public static FileInputStream sourceFile;
	public static Scanner fileInput;
	static int train_len = 4465;
	public static int file_len = 0;
	static String replace_sym = "[!,.:;?��()\\[\\]{}/<>\t&\\-=\\*\\\\]";	
	public static int tag_len = 354;
	public static void readfile() throws FileNotFoundException
	{
		sourceFile = new FileInputStream("test.data");
		fileInput = new Scanner(sourceFile);
		while(fileInput.hasNextLine())
		{
			file_len++;
			fileInput.nextLine();
		}
	}
	public static void getNei() throws IOException
	{
		FileInputStream stop_Word_File = new FileInputStream("stop_word.txt");
		Scanner stop_fileinput = new Scanner(stop_Word_File);
		String[] stop_arr = new String[547];
		for(int i = 0;i < 547;i ++)							//��дֹͣ��
		{
			String s1 = stop_fileinput.nextLine();
			s1 = s1.trim();
			stop_arr[i] = s1;
		}
		double[][] nei_arr = new double[file_len][train_len];
		for(int i = 0;i < file_len;i ++)											
			for(int j = 0;j < file_len;j++)
				nei_arr[i][j] = 0;
		int[][] near_arr = new int[file_len][15];
		String[][] data_arr = new String[file_len][1000];
		String[][] test_tag_arr = new String[file_len][15];
		System.out.println(file_len);
		fileInput = new Scanner(new FileInputStream("test.data"));
		String[] tag_arr = new String[tag_len];
		File tag_file = new File("allTags.txt");
		Scanner tag_input = new Scanner(tag_file);									//tag_arr���б�ǩ
		for(int i = 0;i < tag_len;i++)
		{
			tag_arr[i] = tag_input.nextLine();
		}
		String[][] train_data_arr = new String[train_len][1000];
		FileInputStream train_file = new FileInputStream("train_data.txt");
		Scanner train_input = new Scanner(train_file);
		for(int i = 0;i < train_len;i++)
		{
			String temp = train_input.nextLine();
			String s1 = temp.trim();
			String[] s2 = s1.split(" +");
			for(int j = 0;j < s2.length;j++)
			{
				train_data_arr[i][j] = s2[j];									//train_data_arr train��������
			}
		}
		double[] pf1l_arr = new double[tag_len];
		FileInputStream pf1l_file = new FileInputStream("pf1l.txt");
		Scanner pf1l_input = new Scanner(pf1l_file);
		for(int i = 0;i < tag_len;i++)
		{
			String temp = pf1l_input.nextLine();
			pf1l_arr[i] = Double.valueOf(temp).doubleValue();					//pf1l_arr pf1l��������
		}
		double[][] pgf1 = new double[tag_len][16];
		FileInputStream pgf1_file = new FileInputStream("pgf1.txt");
		Scanner pgf1_input = new Scanner(pgf1_file);
		for(int i = 0;i < tag_len;i++)
		{
			String s1 = pgf1_input.nextLine();
			String s2 = s1.trim();
			String[] s3= s2.split(" ");
			for(int j = 0;j < 16;j++)
			{
				pgf1[i][j] = Double.valueOf(s3[j]).doubleValue();
			}
		}
		double[][] pgf0 = new double[tag_len][16];
		FileInputStream pgf0_file = new FileInputStream("pgf0.txt");
		Scanner pgf0_input = new Scanner(pgf0_file);
		for(int i = 0;i < tag_len;i++)
		{
			String s1 = pgf0_input.nextLine();
			String s2 = s1.trim();
			String[] s3= s2.split(" ");
			for(int j = 0;j < 16;j++)
			{
				pgf0[i][j] = Double.valueOf(s3[j]).doubleValue();
			}
		}
		String[][] train_tag_arr = new String[train_len][1000];
		File train_tag_file = new File("train_tag.txt");
		Scanner train_tag_input = new Scanner(train_tag_file);
		for(int i = 0;i < train_len;i++)
		{
			String temp = train_tag_input.nextLine();
			String s1 = temp.trim();
			String[] s2 = s1.split(",");
			for(int j = 0;j < s2.length;j++)
			{
				train_tag_arr[i][j] = s2[j];										//train_tag_arr train���б�ǩ
			}
		}
		for(int j = 0;j < file_len;j++)
		{
			String s1 = fileInput.nextLine();	
			int flag = 0;
			int ktemp = 0;
			int ktempbegin = 0;
			int ktempend = 0;
			for(int k = 0;k < s1.length()-3;k++)					//�ҵ��ڶ����ָ���
			{
				if(s1.charAt(k) == '#' && s1.charAt(k+1) == '$' && s1.charAt(k+2) == '#' && flag == 0)
				{
					flag = 1;
					ktempbegin = k+3;
				}
				else if(s1.charAt(k) == '#' && s1.charAt(k+1) == '$' && s1.charAt(k+2) == '#' && flag == 1)
				{
					ktempend = k;
					ktemp = k+3;													
				}
			}
			System.out.println(s1.length());
			System.out.println(ktemp);
			System.out.println(ktempbegin);
			System.out.println(ktempend);
			String s2 = s1.substring(ktemp);
			String[] s6 = s2.split(",");										//��ñ�ǩ
			String s3 = s1.substring(ktempbegin, ktempend);
			String s4 = s3.replaceAll(replace_sym, " ");
			s4 = s4.toLowerCase();
			String[] s5 = s4.split(" +");
			for(int i = 0;i < s5.length;i++)
			{
				for(int l = 0;l < 547;l++)
				{
					if(s5[i].equals(stop_arr[l]))
					{
						s5[i] = "!!";
						break;
					}
				}
			}
			int num = 0;
			for(int i = 0;i < s5.length;i++)
			{
				if(!s5[i].equals("!!"))
				{
					data_arr[j][num] = s5[i];					//data_arr test.txt�е�����
					num++;
				}
			}
			for(int i = 0;i < s6.length;i++)
			{
				test_tag_arr[j][i] = s6[i];						//test_tag_arr test.txt�е�tag
			}
		}
		
		//��nei_arr
		
		for(int i = 0;i < file_len;i++)                           //��ÿһ������
		{
			int temp_data_index = 0;
			int src_len = 0;
			while(data_arr[i][temp_data_index]!=null)
			{
				src_len += 1;
				temp_data_index ++;
			}
			int[] x_arr = new int[src_len];							//ͳ��xn
			int[] y_arr = new int[src_len];							//ͳ��yn
			for(int j = i;j < train_len;j++)						//������кͱ���е����Ҿ���
			{
				int temp_data_index_j = 0;
				int dst_len = 0;
				while(train_data_arr[j][temp_data_index_j]!=null)
				{
					dst_len += 1;
					temp_data_index_j ++;
				}
				for(int k = 0;k < src_len;k++)
				{
					int knum = 0;
					for(int l = 0;l < src_len;l++)
					{
						if(data_arr[i][k].equals(data_arr[i][l]))
						{
							knum ++;
						}
					}
					x_arr[k] = knum;
				}
				for(int k = 0;k < src_len;k++)
				{
					int knum = 0;
					for(int l = 0;l < dst_len;l++)
					{
						if(data_arr[i][k].equals(train_data_arr[j][l]))
						{
							knum ++;
						}
					}
					y_arr[k] = knum;
				}
				int top = 0;												//���Ҿ������
				for(int k = 0;k < src_len;k++)
				{
					top += (x_arr[k]*y_arr[k]);
				}
				double buttomleft = 0;
				for(int k = 0;k < src_len;k++)
				{
					buttomleft += (x_arr[k] * x_arr[k]);
				}
				buttomleft = Math.sqrt(buttomleft);
				double buttomright = 0;
				for(int k = 0;k < src_len;k++)
				{
					buttomright += (y_arr[k] * y_arr[k]);
				}
				buttomright = Math.sqrt(buttomright);
				double buttom = buttomleft * buttomright;
				if(buttom == 0)
				{
					nei_arr[i][j] = 0;
					//nei_arr[j][i] = 0;
				}
				else
				{
					nei_arr[i][j] = (double)top/buttom;
					//nei_arr[j][i] = (double)top/buttom;
				}
			}
		}
		//end
		//find near neighbour
		
		for(int i = 0;i < file_len;i ++)		
		{
			int[] index_arr = new int[15];
			double[] value_arr = new double[15];
			for(int j = 0;j < 15;j ++)
			{
				index_arr[j] = j;
				value_arr[j] = nei_arr[i][j];
			}
			for(int j = 15;j < train_len;j++)
			{
				datahandler.selectionSort(value_arr,index_arr,15);
				for(int k = 0;k < 15;k++)
				{
					if(nei_arr[i][j] > value_arr[k])
					{
						value_arr[k] = nei_arr[i][j];
						index_arr[k] = j;
						break;
					}
				}
			}
			for(int j = 0;j < 15;j++)
			{
				near_arr[i][j] = index_arr[j];
			}
		}
		//end
		
		//beigin
		int[][] ctl_arr = new int[tag_len][file_len];
		for(int i = 0;i < tag_len;i++)				//��дctl����
		{
			//System.out.println(tag_arr[i]);
			String tagtemp = tag_arr[i];
			for(int j = 0;j < file_len;j++)			//ÿ������
			{
				int num = 0;
				for(int k = 0;k < 15;k++)			//ÿ������
				{
					for(int l = 0;l < datahandler.mygetLen(train_tag_arr[near_arr[j][k]]);l++)
					{
						//System.out.println(train_tag_arr[near_arr[j][k]][l]);
						if(tag_arr[i].equals(train_tag_arr[near_arr[j][k]][l]))
						{
							num ++;
						}
					}
				}
				ctl_arr[i][j] = num;
			}
		}
		//end
		
		//begin  ��rtl
		double[][] rtl_arr = new double[tag_len][file_len];
		for(int i = 0;i < tag_len;i++)
		{
			for(int j = 0;j < file_len;j++)
			{
				double e1 = pf1l_arr[i];
				int e2 = ctl_arr[i][j];
				double e3 = pgf1[i][e2];
				double e4 = e1*e3;
				double e5 = 1.0 - e1;
				double e6 = pgf0[i][e2];
				double e7 = e6+e4;
				double e8 = e4/e7;
				rtl_arr[i][j] = e8;
			}
		}
		//end
		double[][] real_rtl_arr = new double[file_len][tag_len];
		for(int i = 0;i < file_len;i++)
		{
			for(int j = 0;j < tag_len;j++)
			{
				real_rtl_arr[i][j] = rtl_arr[j][i];
			}
		}
		int[][] fin_arr = new int[file_len][4];
		for(int i = 0;i < file_len;i++)
		{
			int[] index_arr = new int[4];
			double[] value_arr = new double[4];
			for(int j = 0;j < 4;j ++)
			{
				index_arr[j] = j;
				value_arr[j] = real_rtl_arr[i][j];
			}
			for(int j = 4;j < tag_len;j++)
			{
				datahandler.selectionSort(value_arr,index_arr,4);
				for(int k = 0;k < 4;k++)
				{
					if(real_rtl_arr[i][j] > value_arr[k])
					{
						value_arr[k] = real_rtl_arr[i][j];
						index_arr[k] = j;
						break;
					}
				}
			}	
			for(int j = 0;j < 4;j++)
			{
				fin_arr[i][j] = index_arr[j];
			}
		}
		File nei_file = new File("result.txt");
		nei_file.createNewFile();
		PrintWriter fileoutput_nei = new PrintWriter(nei_file);
		for(int i = 0;i < file_len;i++)
		{
			fileoutput_nei.print( (i+1) + "#$#");
			for(int j = 0;j < 4;j++)
			{
				fileoutput_nei.print(tag_arr[fin_arr[i][j]] + ",");
			}
			fileoutput_nei.println();
		}
		fileoutput_nei.close();
	}
}
