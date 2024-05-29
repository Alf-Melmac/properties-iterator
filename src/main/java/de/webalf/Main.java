package de.webalf;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Properties;

public class Main {
	public static void main(String[] args) throws IOException {
		PrintStream console = System.out;
		PrintStream fileOut = new PrintStream(File.createTempFile("properties-destructor", ".txt"));
		PrintStream dualOut = new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				console.write(b);
				fileOut.write(b);
			}

			@Override
			public void write(byte[] b) throws IOException {
				console.write(b);
				fileOut.write(b);
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				console.write(b, off, len);
				fileOut.write(b, off, len);
			}
		});

		System.setOut(dualOut);

		new Main().db2IstToll();
	}

	private void db2IstToll() {
		short i = 0;
		int w = 0;
		final Properties copy = (Properties) System.getProperties().clone();
		try {
			while (true) {
				final Map<?, ?> sourceData = getMap();
				for (Map.Entry<?, ?> entry : sourceData.entrySet()) {
					System.out.println(w + "-" + i++);
					Thread.sleep(1000);
				}
				i = 0;
				w++;
			}
		} catch (Exception e) {
			System.out.println("!!!!!!!!--------");
			e.printStackTrace();
			final Properties now = (Properties) System.getProperties().clone();
			System.out.println("copy size: " + copy.size());
			System.out.println("now size: " + now.size());
			System.out.println("copy == now: " + copy.equals(now));
			extracted(copy);
			System.out.println("!!!!!!!!--------");
			extracted(now);
			System.out.println("!!!!!!!!--------");
		}
	}

	private static void extracted(Map<?, ?> sourceData) {
		for (Map.Entry<?, ?> entry : sourceData.entrySet()) {
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
	}

	private Map<?, ?> getMap() {
		return System.getProperties();
	}
}
