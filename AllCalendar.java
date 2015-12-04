import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class AllCalendar {
	private Calendar cal;	//����һ���û���Main.getCalendar()���������ݵ�һ���Calendar����,�����û�����2015,��new Calendar(2015,0,1);
	private CalendarWriter cfw;		//����ͨ����AllCalendar�ṩ����Ϣ�����������,�Ա����������ļ����ն�
	
	/*
	 * ���캯��,fileΪ����ļ���,yearΪ�������,langΪ���Ļ�Ӣ��(����CalendarWriter��ָ��)
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
	 * �Ը������·��ظ����ж����� ����getMonth(2015,11) = 30
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
