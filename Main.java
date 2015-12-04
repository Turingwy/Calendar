import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class Main {
	private static Scanner in = new Scanner(System.in);		//整个Main类中共用的输出接口

	private static void option(int opt) throws IOException {		//对于用户输入的选项进行分支处理(用户选择1.则会跳到getCalendar,选择2,则会跳到searchDay)
		switch (opt) {
		case 1:
			getCalendar();
			break;
		case 2:
			System.out.println("输入要查询的日期  形式(xxxx-xx-xx)");
			while (!searchDay())
				System.out.println("输入正确的日期");
			;
			break;
		}
	}

	private static boolean searchDay() {	//用户需要对输入的某一天查找给定星期信息,该函数通过jdk自带类库中的java.util.Calendar对用户输入的日期返回对应星期.

		String s = in.nextLine();

		String[] splits = s.split("-");
		if (splits.length != 3) {
			return false;
		}
		int year = Integer.parseInt(splits[0]);
		int month = Integer.parseInt(splits[1]);
		int day = Integer.parseInt(splits[2]);

		if (month <= 0 || month > 12 || AllCalendar.getMonth(year, month) < day) {
			return false;
		}

		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day);
		int w = c.get(Calendar.DAY_OF_WEEK);
		System.out.println(s + "为 星期" + CalendarWriter.ChineseWeek[w - 1]);
		System.out.println();
		return true;
	}

	private static void getCalendar() throws IOException {		//通过用户输入的年份,文件名以及语言生成不同的AllCalendar对象
		System.out.println("输入要查询的年份");
		int year = 2015;
		for (;;) {
			year = in.nextInt();
			if (year > 0)
				break;
			else
				System.out.println("请重新输入正确的年份 like:2015");
		}
		in.nextLine();
		System.out.println("你要导出到的文件");
		String file = in.nextLine();
		System.out.println("输入日期显示的语种(1.中文,2.英文),默认为中文");
		int lang = in.nextInt();
		switch (lang) {
		case 1:
			new AllCalendar(file, year, CalendarWriter.Chinese);
			break;
		case 2:
			new AllCalendar(file, year, CalendarWriter.English);
			break;
		default:
			System.out.println("未能识别你需要的语种,使用中文输出");
			new AllCalendar(file, year, CalendarWriter.Chinese);
			break;
		}
		System.out.println("已经写入文件\n");
	}

	public static void main(String[] args) throws IOException {
		int year;
		boolean lang;

		System.out.println("万年历                 by 计科133王东旭");
		for (;;) {
			System.out.println("1.查询某年日历 2.查询某日为星期几");
			int opt = 1;
			for (;;) {
				opt = in.nextInt();
				if (opt == 1 || opt == 2)
					break;
			}
			in.nextLine();
			option(opt);
		}

	}
}
