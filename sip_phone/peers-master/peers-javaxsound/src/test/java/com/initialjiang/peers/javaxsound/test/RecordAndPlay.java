package com.initialjiang.peers.javaxsound.test;

import java.io.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.TargetDataLine;

/**
 * @Description
 * TargetDataLine是声音的输入(麦克风),而SourceDataLine是输出(音响,耳机).
 * 整个过程,请参见下面的程序.(运行后,实时地从麦克风录音,实时输出).
 * @Date 2017年10月17日 下午5:50:39
 * @Author rhjiang@ctrip.com Copyright (c) All Rights Reserved, 2016.
 */
public class RecordAndPlay {
	static volatile boolean stop = false;

	public static void main(String[] args) {
		Play();
	}

	// 播放音频文件
	public static void Play() {

		try {
			 // new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100F, 8, 1, 1, 44100F, false);
			AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100F, 16, 2, 4, 44100F, true);
			DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
			TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
			targetDataLine.open(audioFormat);
			targetDataLine.start();
			
			DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
			final SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();
			
			FloatControl fc = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
			double value = 2;
			float dB = (float) (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) * 20.0);
			fc.setValue(dB);
			int nByte = 0;
			final int bufSize = 4 * 100;
			byte[] buffer = new byte[bufSize];
			while (nByte != -1) {
				// System.in.read();
				nByte = targetDataLine.read(buffer, 0, bufSize);
				sourceDataLine.write(buffer, 0, nByte);
			}
			sourceDataLine.stop();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
