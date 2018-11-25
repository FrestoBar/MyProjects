package first;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class Qwerty extends javafx.application.Application {

	private static MediaPlayer mediaPlayer;
	private static File file;
	private static  FileChooser fileChooser = new FileChooser();
	private static Long income = 0L;
	private static Scanner scan = new Scanner(System.in);
	private static int sec = 1;
	private static Long trackSize = 0L;
	private static List<File> files;
	private static ChangeListener<Duration> progressChangeListener;
	private static List<Long> audioDurations;
	private static int songIndex = 1;
	private static Long controlTime = 0L;
	private static List<MediaPlayer> players;

	public static void main(String[] args) {
		Application.launch(args);
	}

	private static MediaPlayer createPlayer(Media aMediaSrc) {
		final MediaPlayer player = new MediaPlayer(aMediaSrc);
		player.setOnError(new Runnable() {
			@Override
			public void run() {
				System.out.println("Media error occurred: " + player.getError());
			}
		});
		return player;
	}

	private static void playMusic() {

		players = new ArrayList<MediaPlayer>();
		
		
		initDuration(0);
		
		mediaPlayer = players.get(0);
		
		controlTime = audioDurations.get(0);

		System.out.println("Track size " + trackSize + " Income " + income);
		if (trackSize <= income) {
			
			System.out.println("Do you want to choose one more?");
			scan.nextLine();
			String yesOrNo = scan.nextLine();
			
			if (yesOrNo.equalsIgnoreCase("y") || yesOrNo.equalsIgnoreCase("yes")) {
				
				System.out.println("Please choose " + (income / 180) + " songs");
				fileChooser = new FileChooser();
				List<File> temp = files;
				files = new ArrayList<>();
				files.addAll(temp);
				files.addAll(fileChooser.showOpenMultipleDialog(null));
				

				for (int i = 0; i < files.size(); i++) {
					System.out.println("Track name: " + files.get(i));
				}
				
				initDuration(temp.size());
				
			} else {
				System.out.println("\nOkey song cycled");
			}
		} else if (trackSize > income) {
			System.out.println("Music play");
		}

		

		// mediaPlayer.setStopTime(Duration.seconds(income));
		 mediaPlayer.setAutoPlay(true);
		 clock();

	}
	
	private static void initDuration(int i) {
		
		try {
			Media hit;
			
			for (int j = i; j < files.size(); j++) {
				hit = new Media(files.get(j).toURI().toString());
				players.add(createPlayer(hit));
			}
			
			for (; i < files.size(); i++) {
				Mp3File mp3file = new Mp3File(files.get(i));
				Long length = mp3file.getLengthInSeconds();
				trackSize += length;
				audioDurations.add(i, length);
			}
			System.out.println("Length of this mp3 is: " + trackSize + " seconds");
			
			for (int j = 0; j < audioDurations.size();j++) {
				System.out.println("Track №" + j + " = " + audioDurations.get(j));
			}
		} catch (UnsupportedTagException e) {
			e.printStackTrace();
		} catch (InvalidDataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void alarm() {

		Media alarm = new Media(new File("Clock.mp3").toURI().toString());
		mediaPlayer = new MediaPlayer(alarm);
		System.out.println("Alarm!!!");

		mediaPlayer.play();
	}

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
					}else if(sec == controlTime) {
						
						mediaPlayer.stop();
						mediaPlayer = null;
						changeSong();
						controlTime += audioDurations.get(songIndex++);
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
	
	private static void changeSong() {
		mediaPlayer = players.get(songIndex);
		mediaPlayer.play();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		System.out.print("Enter time:");
		income = scan.nextLong();

		// file = fileChooser.showOpenDialog(primaryStage);
		files = fileChooser.showOpenMultipleDialog(null);
		System.out.println(files);
		
		audioDurations = new ArrayList<Long>();
		
		playMusic();

	}

}
/*
 * 
 *
 * */
