/* KilCli, an OGC mud client program
 * Copyright (C) 2002, 3 Jason Baumeister
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the project nor the names of its contributors
 *  may be used to endorse or promote products derived from this software
 *  without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE PROJECT AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE PROJECT OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */

package terris.kilcli.writer;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * TriggerWriter for KilCli is the class responsible for<br>
 * adding, deleting, and rewriting the triggers.txt file<br>
 * Ver: 0.3.7
 */

public class TriggerWriter {
	private static String slash = System.getProperty("file.separator");
	private static String fileName = "config" + slash + "triggers.txt";
	private static PrintWriter outFile;

	/**
	 * Adds a trigger to the triggers.txt file
	 */

	public static void add(String enabled, String casesensitive, String text, String script, String word) {
		try {
			outFile = new PrintWriter( new BufferedWriter(new FileWriter(fileName, true)));
		} catch (IOException ioe) {}
		if (word != null) {
			outFile.println(enabled + "|" + casesensitive + "|" + text + "|" + script + "|" + word);
		} else {
			outFile.println(enabled + "|" + casesensitive + "|" + text + "|" + script);
		}
		outFile.close();
	}

	/**
	 * Updates the triggers.txt file
	 */

	public static void update(JComboBox[] enabledArray, JComboBox[] caseArray, JTextField[] textArray, JTextField[] scriptArray, JTextField[] targetArray) {
		try {
			outFile = new PrintWriter( new BufferedWriter(new FileWriter(fileName)));
		} catch (IOException ioe) {}
		for (int i = 0; i < enabledArray.length; i++) {
			if (targetArray[i].getText().length() < 1) {
				outFile.println(enabledArray[i].getSelectedItem() + "|" + caseArray[i].getSelectedItem() + "|" + textArray[i].getText() + "|" + scriptArray[i].getText());
				outFile.flush();
			} else {
				outFile.println(enabledArray[i].getSelectedItem() + "|" + caseArray[i].getSelectedItem() + "|" + textArray[i].getText() + "|" + scriptArray[i].getText() + "|" + targetArray[i].getText());
				outFile.flush();
			}
		}
		outFile.close();
	}

	/**
	 * Sets the profile name to know which triggers.txt file to write to
	 *
	 * @param profile - the name of the current profile
	 */
	public static void setProfile(String profile) {
		fileName = "config" + slash + profile + slash + "triggers.txt";
	}
}