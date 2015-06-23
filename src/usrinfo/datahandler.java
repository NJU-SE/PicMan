package usrinfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class datahandler
{
	public static Scanner fileinput1;							//train.data
	public static Scanner fileinput2;                           //allTags.txt
	public static File sourceFile1;
	public static File sourceFile2;
	static int tag_len = 354;                                          //����354����ǩ
	static int train_len = 4465;										//train���ݹ���4465��
	static String replace_sym = "[!,.:;?��()\\[\\]{}/<>\t&\\-=\\*\\\\]";		
	public static void ini_handler() throws IOException
	{
		FileInputStream sourceFile1 = new FileInputStream("train.data");
		FileInputStream sourceFile2 = new FileInputStream("allTags.txt");
		fileinput1 = new Scanner(sourceFile1);             //fileinput �ļ�ָ��	
		fileinput2 = new Scanner(sourceFile2);
	}
	public static void train() throws IOException
	{
		int smooth = 1;   //ƽ������
		
		double pf1l[] = new double[tag_len];             //pfl����
		double pf2l[] = new double[tag_len];
		File tempFile = new File("train_tag.txt");
		tempFile.createNewFile();
		PrintWriter fileoutput = new PrintWriter(tempFile);
		File tempFile5 = new File("train_data.txt");
		tempFile5.createNewFile();
		PrintWriter fileoutput5 = new PrintWriter(tempFile5);
		FileInputStream stop_Word_File = new FileInputStream("stop_word.txt");
		Scanner stop_fileinput = new Scanner(stop_Word_File);
		String[] stop_arr = new String[547];
		for(int i = 0;i < 547;i ++)							//��дֹͣ��
		{
			String s1 = stop_fileinput.nextLine();
			s1 = s1.trim();
			stop_arr[i] = s1;
		}
		for(int i = 0;i < tag_len;i++)					//���pf1l,pf2l����
		{
			int tagnum = 0;
			String tag = fileinput2.nextLine();
			for(int j = 0;j < train_len;j++)
			{
				String s1 = fileinput1.nextLine();	
				int flag = 0;
				int ktemp = 0;
				int ktempbegin = 0;
				int ktempend = 0;
				for(int k = 0;k < s1.length()-4;k++)					//�ҵ��ڶ����ָ���
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
				String s2 = s1.substring(ktemp);
				String[] tag_arr = s2.split(",");										//��ñ�ǩ
				String s3 = s1.substring(ktempbegin, ktempend);
				String s4 = s3.replaceAll(replace_sym, " ");
				s4 = s4.toLowerCase();
				String[] data_arr = s4.split(" +");
				for(int k = 0;k < data_arr.length;k++)
				{
					for(int l = 0;l < 547;l++)
					{
						if(data_arr[k].equals(stop_arr[l]))
						{
							data_arr[k] = "!!";
							break;
						}
					}
				}
				
				
				for(int k = 0;k < data_arr.length;k++)
				{
					if(!data_arr[k].equals("!!"))
						fileoutput5.print(data_arr[k] + " ");
				}
				for(int k = 0;k < tag_arr.length;k++)
				{
					fileoutput.print(tag_arr[k] + ",");
					if(tag_arr[k].equals(tag))
					{
						tagnum ++;
					}
				}
				fileoutput.println();
				fileoutput5.println();
			}
			fileoutput.close();
			fileoutput5.close();
			fileinput1 = new Scanner(new FileInputStream("train.data"));
			double temp_pf1l = 0;
			double temp_pf2l = 0;
			temp_pf1l += (double)(smooth + tagnum)/(2 + train_len);
			temp_pf2l += 1.0 - temp_pf1l;
			pf1l[i] = temp_pf1l;
			pf2l[i] = temp_pf2l;
		}	
		File tempFile1 = new File("pf1l.txt");
		tempFile1.createNewFile();
		PrintWriter fileoutput1 = new PrintWriter(tempFile1);
		for(int i = 0;i < tag_len;i++)
		{
			fileoutput1.println(pf1l[i]);
		}
		fileoutput1.close();		
	}
	public static void get_neighbour() throws IOException
	{
		//int nei_k = 15;     													//���������
		double[][] nei_arr = new double[train_len][train_len];					//�����������鲢��ʼ��
		for(int i = 0;i < train_len;i ++)											
			for(int j = 0;j < train_len;j++)
				nei_arr[i][j] = 0;
		FileInputStream inputStream = new FileInputStream("train_data.txt");
		Scanner streamInput = new Scanner(inputStream);
		String[][] data_arr = new String[train_len][1000];						//��ȡȫ������
		for(int i = 0;i < train_len;i++)
		{
			String s1 = streamInput.nextLine();
			//System.out.println(s1);
			s1 = s1.trim();
			String[] split_arr = s1.split(" +");
			for(int j = 0;j < split_arr.length;j++)
			{
				//System.out.println(split_arr.length);
				//System.out.println(split_arr[j]);
				data_arr[i][j] = split_arr[j];
			}
		}                                                          //ȫ��������data_arr��
		
		//��ʼ�������Ҿ���
		for(int i = 0;i < train_len;i++)                           //��ÿһ������
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
				while(data_arr[j][temp_data_index_j]!=null)
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
						if(data_arr[i][k].equals(data_arr[j][l]))
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
					nei_arr[j][i] = 0;
				}
				else
				{
					nei_arr[i][j] = (double)top/buttom;
					nei_arr[j][i] = (double)top/buttom;
				}
			}
		}
		File nei_file = new File("cos_nei.txt");
		nei_file.createNewFile();
		PrintWriter fileoutput_nei = new PrintWriter(nei_file);
		for(int i = 0;i < train_len;i++)
		{
			for(int j = 0;j < train_len;j++)
			{
				fileoutput_nei.print(nei_arr[i][j] + " ");
			}
			fileoutput_nei.println();
		}
		fileoutput_nei.close();
	}
	public static void selectionSort(double[] list,int[] index,int len)
	{
		for(int i = 0;i < len - 1;i++)
		{
			double currentMin = list[i];
			int currentMinIndex = i;
			int minIndex = index[i];
			for(int j = i + 1;j < len;j++)
			{
				if(currentMin > list[j])
				{
					currentMin = list[j];
					minIndex = index[j];
					currentMinIndex = j;
				}
			}
			if(currentMinIndex != i)
			{
				list[currentMinIndex] = list[i];
				index[currentMinIndex] = index[i];
				list[i] = currentMin;
				index[i] = minIndex;
			} 
		}
	}
	public static void get_near_nei() throws IOException
	{
		File nei_file = new File("cos_nei.txt");
		Scanner nei_Scanner = new Scanner(nei_file);
		double[][] nei_arr = new double[train_len][train_len];	
		for(int i = 0;i < train_len;i++)
		{
			String temp = nei_Scanner.nextLine();
			String s1 = temp.trim();
			String[] s2 = s1.split(" ");
			for(int j = 0;j < train_len;j++)
			{
				double dtemp = Double.valueOf(s2[j]).doubleValue();
				nei_arr[i][j] = dtemp;
			}
		}
		int[][] near_arr = new int[train_len][15];
		for(int i = 0;i < train_len;i ++)		
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
				selectionSort(value_arr,index_arr,15);
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
		File near_file = new File("near_nei.txt");
		near_file.createNewFile();
		PrintWriter near_output = new PrintWriter(near_file);
		for(int i = 0;i < train_len;i++)
		{
			for(int j = 0;j < 15;j++)
			{
				near_output.print(near_arr[i][j] + " ");
			}
			near_output.println();
		}
		near_output.close();
	}
	public static void output() throws FileNotFoundException
	{
		/*String s1 = fileinput1.nextLine();
		System.out.println(s1);
		s1 = fileinput1.nextLine();
		System.out.println(s1);*/
		/*
		for(int i = 0; i < 5;i ++)
		{
			String s1 = fileinput1.nextLine();
			System.out.println(s1);
			fileinput1 = new Scanner(new FileInputStream("train.data"));
		}*/
		/*String[][] arr = new String [5][6];
		for(int j = 0;j < 6;j ++)
		{
			arr[0][j] = "1";
		}
		for(int j =0;j < 4;j++)
		{
			arr[1][j] = "2";
		}
		System.out.println(arr[0].length);
		System.out.println(arr[1].length);
		System.out.println(arr[1][5]==null);
		System.out.println(arr[1][3]==null);*/
		FileInputStream file = new FileInputStream("allTags.txt");
		Scanner input = new Scanner(file);
		int len = 0;
		while(input.hasNextLine())
		{
			len++;
			input.nextLine();
		}
		System.out.println(len);
	}
	public static int mygetLen(String[] arr)
	{
		int len = 0;
		for(int i = 0;i < 10;i++)
		{
			if(arr[i] != null)
				len++;
			else if(arr[i] == null)
				break;
		}
		return len;
	}
	public static void get_relation() throws IOException
	{
		File nei_file = new File("near_nei.txt");
		Scanner nei_Scanner = new Scanner(nei_file);					
		int[][] nei_arr = new int[train_len][15];					//�����������nei_arr
		for(int i = 0;i < train_len;i++)
		{
			String s1 = nei_Scanner.nextLine();
			s1 = s1.trim();
			String[] s2 = s1.split(" ");
			for(int j = 0;j < 15;j++)
			{
				int dtemp = Integer.valueOf(s2[j]).intValue();
				nei_arr[i][j] = dtemp;
			}
		}
		File tag_file = new File("allTags.txt");
		Scanner tag_Scanner = new Scanner(tag_file);
		String[] all_tag = new String[tag_len];							//����tag���� all_tag
		for(int i = 0;i < tag_len;i++)
		{
			String s1 = tag_Scanner.nextLine();
			s1 = s1.trim();
			all_tag[i] = s1;
		}
		File train_tag_file = new File("train_tag.txt");				
		Scanner train_tag_Scanner = new Scanner(train_tag_file);
		String[][] train_tag_arr = new String[train_len][10];		//train������tag������ train_tag_arr
		for(int i = 0;i < train_len;i++)
		{
			String s1 = train_tag_Scanner.nextLine();
			s1 = s1.trim();
			String[] s2 = s1.split(",");
			for(int j = 0;j < s2.length;j++)
			{
				train_tag_arr[i][j] = s2[j];
			}
			
		}
		double[][] pgf1 = new double[tag_len][16];
		double[][] pgf0 = new double[tag_len][16];
		for(int i = 0;i < tag_len;i++)
		{
			int[] dj_arr = new int[16];
			int[] dj_arr_anti = new int[16];
			for(int j = 0;j < 16;j++)
			{
				dj_arr[j] = 0;
				dj_arr_anti[j] = 0;
			}
			for(int j = 0;j < train_len;j++)
			{
				int u = 0;
				for(int k = 0;k < 15;k++)
				{
					int len = 0;
					int num = nei_arr[j][k];
					len = mygetLen(train_tag_arr[num]);           //nei_arr[j][k]��ָ���ڵı�ǩ����
					for(int l = 0;l < len;l++)
					{
						if(train_tag_arr[num][l].equals(all_tag[i]))
							u++;
					}
				}
				int mylen = mygetLen(train_tag_arr[j]);           //�Լ�����tag����
				int flag = 0;
				for(int k = 0;k < mylen;k++)
				{
					if(train_tag_arr[j][k].equals(all_tag[i]))
					{
						flag = 1;
						break;
					}
				}
				if(flag == 1)
				{
					dj_arr[u] += 1;
				}
				else
				{
					dj_arr_anti[u] += 1;
				}
			}
			int dpsum = 0;
			int dp_antisum = 0;
			for(int j = 0;j < 16;j++)
			{
				dpsum += dj_arr[j];
				dp_antisum += dj_arr_anti[j];
			}
			for(int j = 0;j < 16;j++)
			{
				pgf1[i][j] = (double)(1 + dj_arr[j])/(1*(15 + 1) + dpsum);
				pgf0[i][j] = (double)(1 + dj_arr_anti[j])/(1*(15 + 1) + dp_antisum);
			}
		}
		File pgf1_file = new File("pgf1.txt");
		pgf1_file.createNewFile();
		PrintWriter pgf1_output = new PrintWriter(pgf1_file);
		File pgf0_file = new File("pgf0.txt");
		pgf0_file.createNewFile();
		PrintWriter pgf0_output = new PrintWriter(pgf0_file);
		for(int i = 0;i < tag_len;i++)
		{
			for(int j = 0;j < 16;j++)
			{
				pgf1_output.print(pgf1[i][j] + " ");
				pgf0_output.print(pgf0[i][j] + " ");
			}
			pgf1_output.println();
			pgf0_output.println();
		}
		pgf1_output.close();
		pgf0_output.close();
	}
}