package executortest;

import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Client {

	public static void main(String[] args) {

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.gc();
			}
		}, 10000, 30000);

		ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>(), new RejectedExecutionHandler() {

					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.out.println("Runnable rejected :: " + LocalDate.now());
					}
				});

		while (true) {
			int counter = ThreadLocalRandom.current().nextInt(5, 10 + 1);

			for (int i = 0; i < counter; i++) {
				executorService.execute(new Runnable() {

					public void run() {

						System.out.println("Thread executed");
					}
				});
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
