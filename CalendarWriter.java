import java.io.IOException;
import java.io.PrintStream;


public class CalendarWriter {
	private int[] monthDay;		//保存一年里每个月有多少天
	private int year;		//用户指定的年份

	private final int startWeekDay;		//保存1月1日为星期几,以备后面输出空格
	private int tmpstartDay;		//保存当前输出到的日期为星期几,方便输出该月结束后,下个月开始的空格
	private String[] monthName;		//每个月的名字,由lang给定的语言选择对应的月份名
	private String[] weekDayName;		//星期的名字,由lang给定的语言选择对应的星期名
	private boolean lang;		//语言 false为Chinese,true为English
	public static final String[] EnglishWeek = new String[]{"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};			//英文星期
	public static final String[] ChineseWeek = new String[]{"日","一","二","三","四","五","六"};			//中文星期
	public static final String[] ChineseMonth = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};		//中文月份

	/*
	 * 英文月份
	 */
	public static final String[] EnglishMonth = new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
	public static final boolean Chinese = false;
	public static final boolean English = true;

	//构造函数,通过给定年份,1月1日为星期几,每个月的天数,以及语言
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
    //向文件或者终端中输出当前输出的月份与星期日到星期六
	public void writeHeader(int month) throws IOException {
		System.out.println("\t   " + monthName[month-1]);
		for(int i = 0;i<7;i++) {
			System.out.print("   " + weekDayName[i] + " ");
		}
		System.out.println();
	}

    /*
    向文件或者终端中输出当前输出的月份所有的日期,
    每个日期都与writeHeader输出的星期对应(通过输出空格保持一致)
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
				if(lang == Chinese && year >= 1905) {		//最大支持1905年以前的农历
				for(int kk = 1;kk<tmp2;kk++) {
					System.out.print("      ");
				}
				tmp2 = 1;
				Lunar l;
				for(int k = tmp;k<=i;k++) {

					l = new Lunar(year, month-1, k);
					if(l.getChinaDayString().compareTo("初一") == 0)
						System.out.print(l.getChinaMonth() + "月 ");
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

    /*通过调用12次writeHeader与writeBody完成整个操作,
    与write(String)不同的是,它是向标准输出中输出
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
    与write()不同的是向给定file名字的PrintStream中输出
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
