import java.io.IOException;
import java.io.PrintStream;


public class CalendarWriter {
	private int[] monthDay;		//����һ����ÿ�����ж�����
	private int year;		//�û�ָ�������

	private final int startWeekDay;		//����1��1��Ϊ���ڼ�,�Ա���������ո�
	private int tmpstartDay;		//���浱ǰ�����������Ϊ���ڼ�,����������½�����,�¸��¿�ʼ�Ŀո�
	private String[] monthName;		//ÿ���µ�����,��lang����������ѡ���Ӧ���·���
	private String[] weekDayName;		//���ڵ�����,��lang����������ѡ���Ӧ��������
	private boolean lang;		//���� falseΪChinese,trueΪEnglish
	public static final String[] EnglishWeek = new String[]{"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};			//Ӣ������
	public static final String[] ChineseWeek = new String[]{"��","һ","��","��","��","��","��"};			//��������
	public static final String[] ChineseMonth = new String[]{"1��","2��","3��","4��","5��","6��","7��","8��","9��","10��","11��","12��"};		//�����·�

	/*
	 * Ӣ���·�
	 */
	public static final String[] EnglishMonth = new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
	public static final boolean Chinese = false;
	public static final boolean English = true;

	//���캯��,ͨ���������,1��1��Ϊ���ڼ�,ÿ���µ�����,�Լ�����
	public CalendarWriter(int year, int startWeek, int[] monthDay, boolean lang) throws IOException {
		if(startWeek <=0 || startWeek > 7 || monthDay.length != 12)
			throw
				new IllegalArgumentException("Illegal argument! Check startWeek([1,7]) or monthDay(length must be 12).");

		this.year = year;
		this.startWeekDay = startWeek;

		this.monthDay = monthDay;
		this.monthName = monthName;
		this.tmpstartDay = startWeekDay;
		if(lang) {
			weekDayName = EnglishWeek;
			monthName = EnglishMonth;
		} else {
			weekDayName = ChineseWeek;
			monthName = ChineseMonth;
		}
		this.lang = lang;
	}
    //���ļ������ն��������ǰ������·��������յ�������
	public void writeHeader(int month) throws IOException {
		System.out.println("\t   " + monthName[month-1]);
		for(int i = 0;i<7;i++) {
			System.out.print("   " + weekDayName[i] + " ");
		}
		System.out.println();
	}

    /*
    ���ļ������ն��������ǰ������·����е�����,
    ÿ�����ڶ���writeHeader��������ڶ�Ӧ(ͨ������ո񱣳�һ��)
    */
	public void writeBody(int month) throws IOException {

		for(int i = 1;i<tmpstartDay;i++) {
			System.out.print("      ");
			if(lang)
				System.out.print(" ");
		}
		int tmp = 1;
		int tmp2 = tmpstartDay;
		for(int i = 1;i<=monthDay[month-1];i++,tmpstartDay++) {
			if(i<10)
				System.out.print("   " + i + "  ");
			else
				System.out.print("  "+i + "  ");

			if(lang)
				System.out.print(" ");

			if(i == monthDay[month-1] || tmpstartDay%7 == 0 ) {
				System.out.println();
				if(lang == Chinese && year >= 1905) {		//���֧��1905����ǰ��ũ��
				for(int kk = 1;kk<tmp2;kk++) {
					System.out.print("      ");
				}
				tmp2 = 1;
				Lunar l;
				for(int k = tmp;k<=i;k++) {

					l = new Lunar(year, month-1, k);
					if(l.getChinaDayString().compareTo("��һ") == 0)
						System.out.print(l.getChinaMonth() + "�� ");
					else
						System.out.print(l.getChinaDayString() + "  ");
				}
				System.out.println();
				tmp = i+1;
				}
			}
		}

		System.out.println("\n");
                tmpstartDay %= 7;
                if(tmpstartDay == 0)
                    tmpstartDay = 7;
	}

    /*ͨ������12��writeHeader��writeBody�����������,
    ��write(String)��ͬ����,�������׼��������
    */
	public void write() throws IOException {
		System.out.print("\t   " + year);
		System.out.println("\n");
		for(int month = 1;month<=12;month++) {
			writeHeader(month);
			writeBody(month);
		}
		tmpstartDay = startWeekDay;
	}

    /*
    ��write()��ͬ���������file���ֵ�PrintStream�����
    */
	public void write(String file) throws IOException {
		PrintStream out = System.out;
		System.setOut(new PrintStream(file));
		System.out.print("\t   " + year);
		System.out.println("\n");
		for(int month = 1;month<=12;month++) {
			writeHeader(month);
			writeBody(month);
		}
		System.setOut(out);
		tmpstartDay = startWeekDay;
	}

}
