import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class Main {
	private static Scanner in = new Scanner(System.in);		//����Main���й��õ�����ӿ�

	private static void option(int opt) throws IOException {		//�����û������ѡ����з�֧����(�û�ѡ��1.�������getCalendar,ѡ��2,�������searchDay)
		switch (opt) {
		case 1:
			getCalendar();
			break;
		case 2:
			System.out.println("����Ҫ��ѯ������  ��ʽ(xxxx-xx-xx)");
			while (!searchDay())
				System.out.println("������ȷ������");
			;
			break;
		}
	}

	private static boolean searchDay() {	//�û���Ҫ�������ĳһ����Ҹ���������Ϣ,�ú���ͨ��jdk�Դ�����е�java.util.Calendar���û���������ڷ��ض�Ӧ����.

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
		System.out.println(s + "Ϊ ����" + CalendarWriter.ChineseWeek[w - 1]);
		System.out.println();
		return true;
	}

	private static void getCalendar() throws IOException {		//ͨ���û���������,�ļ����Լ��������ɲ�ͬ��AllCalendar����
		System.out.println("����Ҫ��ѯ�����");
		int year = 2015;
		for (;;) {
			year = in.nextInt();
			if (year > 0)
				break;
			else
				System.out.println("������������ȷ����� like:2015");
		}
		in.nextLine();
		System.out.println("��Ҫ���������ļ�");
		String file = in.nextLine();
		System.out.println("����������ʾ������(1.����,2.Ӣ��),Ĭ��Ϊ����");
		int lang = in.nextInt();
		switch (lang) {
		case 1:
			new AllCalendar(file, year, CalendarWriter.Chinese);
			break;
		case 2:
			new AllCalendar(file, year, CalendarWriter.English);
			break;
		default:
			System.out.println("δ��ʶ������Ҫ������,ʹ���������");
			new AllCalendar(file, year, CalendarWriter.Chinese);
			break;
		}
		System.out.println("�Ѿ�д���ļ�\n");
	}

	public static void main(String[] args) throws IOException {
		int year;
		boolean lang;

		System.out.println("������                 by �ƿ�133������");
		for (;;) {
			System.out.println("1.��ѯĳ������ 2.��ѯĳ��Ϊ���ڼ�");
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
