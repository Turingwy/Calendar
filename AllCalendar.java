import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class AllCalendar {
	private Calendar cal;	//创建一个用户在Main.getCalendar()中输入的年份第一天的Calendar对象,例如用户输入2015,则new Calendar(2015,0,1);
	private CalendarWriter cfw;		//用于通过对AllCalendar提供的信息创建的输出类,以便输出结果到文件和终端
	
	/*
	 * 构造函数,file为输出文件名,year为给定年份,lang为中文或英文(宏在CalendarWriter中指定)
	 */
	public AllCalendar(String file ,int year, boolean lang) throws IOException {		
		cal = Calendar.getInstance();
		cal.set(year,0,1);
		int startweek = cal.get(Calendar.DAY_OF_WEEK);
		int[] monthDay = new int[12];
		for(int i = 0;i<12;i++) {
			monthDay[i] = getMonth(year, i+1);
		}
		cfw = new CalendarWriter(year, startweek, monthDay, lang);
		cfw.write();
		cfw.write(file);
	}
	
	/*
	 * 对给定年月返回该月有多少天 例如getMonth(2015,11) = 30
	 */
	public static int getMonth(int year, int month) {
		switch(month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 2:
				 if((year%4==0 && year%100!=0) || year%400==0){
					 return 29;
				 }
				 else 
					 return 28;
			default:
				return 30;
		}
				 
				
	}
	
	
}
