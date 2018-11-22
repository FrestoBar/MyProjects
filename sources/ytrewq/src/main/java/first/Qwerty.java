package first;

import java.io.File;
import java.util.Scanner;

import javafx.application.Application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Qwerty extends javafx.application.Application {

	private static MediaPlayer mediaPlayer;
	private static File file;
	private static final FileChooser fileChooser = new FileChooser();
	private static Long income = 0L;
	private static Scanner scan = new Scanner(System.in);
	private static int sec = 1;

	public static void main(String[] args) {
		Application.launch(args);

	}

	private static void playMusic() {

		Media hit = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(hit);
		System.out.println("Music play");

		// System.out.println("Duration = " + file.length());

		mediaPlayer.setStopTime(Duration.seconds(income));
		mediaPlayer.setAutoPlay(true);
		clock();
		// mediaPlayer.stop();
		// System.out.println("Music stoped");
	}

	private static void alarm() {

		Media alarm = new Media(new File("Clock.mp3").toURI().toString());
		mediaPlayer = new MediaPlayer(alarm);
		System.out.println("Alarm!!!");

		mediaPlayer.play();
	}

//	private static void getsize(File file) {
//		MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
//		metaRetriever.setDataSource(filePath);
//	}

	private static void clock() {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {

					if (sec == income) {
						mediaPlayer.stop();
						mediaPlayer = null;
						alarm();
						break;
					}

					System.out.println(sec);
					sec++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});
		t.start();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		System.out.print("Enter time:");
		income = scan.nextLong();

		file = fileChooser.showOpenDialog(primaryStage);

		playMusic();

	}

}
/*
 * 
 *
 * */
