import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Checker{
	private int windowSize = 1;
	private int result;

	public void setWindowSize(int n){
		if (n > 0) {
			windowSize = n;
		}
	}

	public int getResult() {
		return result;
	}

	public void activityNotifications(int[] expenditure) {
		int notifyCounter = 0;
		int[] windowArray = new int[windowSize];

		try {
			for (int i = windowSize; i < expenditure.length; i++) {

				for (int j = 0; j < windowSize; j++) {
					windowArray[j] = expenditure[i - windowSize + j];
				}

				int buf;
				boolean isSorted = false;
				while (!isSorted) {
					isSorted = true;
					for (int k = 0; k < windowSize-1; k++) {
						if (windowArray[k] > windowArray[k + 1]) {
							isSorted = false;
							buf = windowArray[k];
							windowArray[k] = windowArray[k + 1];
							windowArray[k + 1] = buf;
						}
					}
				}

				float median;
				if (windowSize % 2 == 0) {
					int indexValue = windowSize / 2 - 1;

					median = (float) (windowArray[indexValue] + windowArray[indexValue + 1]) / 2;
				} else {
					median = windowArray[windowSize / 2];
				}

				if (expenditure[i] >= median * 2) {
					notifyCounter++;
				}
			}
			result = notifyCounter;
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Выход за границы массива!");
		}
	}
}

public class Solution {

	/* HackerLand National Bank имеет простую политику предупреждени¤ клиентов о
	 * возможных мошеннических действи¤х на счете. ?сли сумма, потраченна¤ клиентом
	 * в определенный день, больше или равна медиане расходов клиента за конечное
	 * число дней, они отправл¤ют клиенту уведомление о потенциальном мошенничестве.
	 * Ѕанк не отправл¤ет клиенту никаких уведомлений до тех пор, пока у него нет,
	 * по крайней мере, этого конечного числа транзакций за предыдущие дни. ”читыва¤
	 * количество завершающих дней и общие ежедневные расходы клиента за период,
	 * найдите и распечатайте количество дней, когда клиент получит уведомление.
	 *
	 * 1 <= n <= 2*10e5, n - количество учтенных расходов
	 * 1 <= d <= n, d - количество дней дл¤ расчета медианы
	 * 0 <= exp[i] <= 200
	 *
	 * ѕример: 9 5
	 *	   2 3 4 2 3 6 8 4 5
	 * ќтвет: 2
	 * {2 2 3 3 4}, m = 3, 2 * 3 <= 6  1
	 * {2 3 3 4 6}, m = 3, 2 * 3 <= 8  1
	 * {3 3 4 6 8}, m = 4, 2 * 4 > 4   0
	 * {3 4 4 6 8}, m = 4, 2 * 4 > 5   0

	 * ѕример: 5 4
	 *	   1 2 3 4 4
	 * {1 2 3 4}, m = 2.5, 2 * 2.5 > 4 0
	 * ќтвет: 0
	 */

	// Complete the activityNotifications function below.

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

		String[] nd = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nd[0]);

		int d = Integer.parseInt(nd[1]);

		int[] expenditure = new int[n];

		String[] expenditureItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int expenditureItem = Integer.parseInt(expenditureItems[i]);
			expenditure[i] = expenditureItem;
		}

		Checker MyChecker = new Checker();
		MyChecker.setWindowSize(d);

		Thread thread = new Thread(() -> {
			MyChecker.activityNotifications(expenditure);
		});

		int r = MyChecker.getResult();

		bufferedWriter.write(String.valueOf(r));
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}

}