package com.initialjiang.peers.javaxsound.test;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class JRH_Record_Voice {

	public static void main(String[] args) {
		try {
			AudioFormat af = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100F, 16, 2, 4, 44100F, true);
			DataLine.Info tinfo = new DataLine.Info(TargetDataLine.class, af);
			final TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(tinfo);
			targetDataLine.open(af);
			targetDataLine.start();

			// 录音到文件
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						File audioFile = new File("rr.wav");
						if (audioFile.exists()) {
							audioFile.delete();
						}
						AudioSystem.write(new AudioInputStream(targetDataLine), AudioFileFormat.Type.WAVE, audioFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();

			Thread.sleep(10000);
			
			targetDataLine.stop();
			targetDataLine.close();
			
			System.out.println("exit");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
