/* KilCli, an OGC mud client program
 * Copyright (C) 2002 - 2004 Jason Baumeister
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

package terris.kilcli.gui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.JScrollPane;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.plaf.basic.*;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import terris.kilcli.io.*;
import terris.kilcli.writer.*;
import terris.kilcli.KilCli;
import terris.kilcli.thread.*;


/**
 * TriggerEditor for KilCli is the class used to edit the triggers<br>
 * through a gui<br>
 * Ver: 1.0.0
 */

public class TriggerEditor extends JDialog {
    private JScrollPane jScroll = new JScrollPane();
    private JSplitPane jSplit;
	private JTabbedPane tabbedpane;
	private JTextField text1;
	private JTextField text4;
	private JTextField text5;
	private JComboBox jComboBox1;
	private JComboBox jComboBox2;
	private JTextField text6;
	private JTextField text7;
	private JTextField text8;
	private JComboBox jComboBox3;
	private JComboBox jComboBox4;
	private JTextField[] targetArray;
	private JTextField[] textArray;
	private JTextField[] scriptArray;
	private JComboBox[] caseArray;
	private JComboBox[] enabledArray;
	private JCheckBox[] delArray;

	public void updateTheme() {
		repaint();
	}

	/**
	 * TriggerEditor constructor, with no arguments creates<br>
	 * a GUI to edit Triggers
	 */

    public TriggerEditor() {
        super(KilCliThread.getKilCli(), "Trigger Editor", false);

        tabbedpane = new JTabbedPane();
        tabbedpane.setTabPlacement(JTabbedPane.TOP);
		JPanel bottom = new JPanel();
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			//function for when someone clicks OK
		    public void actionPerformed(ActionEvent e) {
				int tmp = tabbedpane.getSelectedIndex();
				if (tmp == 0) {
					update();
					close();
				} else if (tmp == 1) {
					create();
					close();
				}
		    }
		});

		JButton apply = new JButton("Apply");
		apply.addActionListener(new ActionListener() {
			//function for when someone clicks Apply
			public void actionPerformed(ActionEvent e) {
				int tmp = tabbedpane.getSelectedIndex();
				if (tmp == 1) {
					create();
				} else if (tmp == 0) {
					update();
				}
			}
		});
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			//function for when someone clicks Reset
			public void actionPerformed(ActionEvent e) {
				int tmp = tabbedpane.getSelectedIndex();
				if (tmp == 0) {
					jScroll = createEdit();
				} else if (tmp == 1) {
					clear();
				}
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			//function to clear fields;
		    public void actionPerformed(ActionEvent e) {
				close();
		    }
		});
		bottom.add(ok);
		bottom.add(apply);
		bottom.add(reset);
		bottom.add(cancel);

        jSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, tabbedpane, bottom);
        jSplit.setDividerSize(0);


		getContentPane().add(jSplit, BorderLayout.CENTER);


		String name;
		JPanel view;
		name = "Edit Triggers";
		jScroll = createEdit();
 		tabbedpane.add(name, jScroll);

		name = "New Trigger";
		view = new JPanel();
		JPanel cell = new JPanel();
		view.setLayout(new GridLayout(6, 2, 0, 5));
		JLabel pix = new JLabel ("Trigger Text:");
		cell.add(pix);
		view.add(cell);
		cell = new JPanel();
		text1 = new JTextField(20);
		cell.add(text1);
		view.add(cell);
		cell = new JPanel();
		pix = new JLabel("Enabled:");
		cell.add(pix);
		view.add(cell);
		cell = new JPanel();
		jComboBox1 = new JComboBox();
		jComboBox1.addItem("True");
		jComboBox1.addItem("False");
		jComboBox1.setSelectedIndex(0);
		cell.add(jComboBox1);
		view.add(cell);
		cell = new JPanel();
		pix = new JLabel("Case Sensitive:");
		cell.add(pix);
		view.add(cell);
		cell = new JPanel();
		jComboBox2 = new JComboBox();
		jComboBox2.addItem("True");
		jComboBox2.addItem("False");
		jComboBox2.setSelectedIndex(0);
		cell.add(jComboBox2);
		view.add(cell);
		cell = new JPanel();
		pix = new JLabel("Script to run:");
		cell.add(pix);
		view.add(cell);
		cell = new JPanel();
		text4 = new JTextField(15);
		cell.add(text4);
		view.add(cell);
		cell = new JPanel();
		pix = new JLabel("Word #(s) to pass:");
		pix.setToolTipText("<html>Leave blank to avoid passing anything<br>To pass multiple, separate their numbers by |<br>For Example: O|2|3<br>Would pass the first, third, and fourth words");
		cell.add(pix);
		view.add(cell);
		cell = new JPanel();
		text5 = new JTextField(9);
		text5.setToolTipText("<html>Leave blank to avoid passing anything<br>To pass multiple, separate their numbers by |<br>For Example: O|2|3<br>Would pass the first, third, and fourth words");
		cell.add(text5);
		view.add(cell);
		cell = new JPanel();
		tabbedpane.add(name, view);

		//get screen dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


        //set tabbed pane visible
		tabbedpane.setVisible(true);

        //...Then set the window size
        setSize((int)(screenSize.width * 0.60), (int)(screenSize.height * 0.35));

        //Set the window's location.
        setLocation((int)(screenSize.width * 0.25), (int)(screenSize.height * 0.25));
        jSplit.setDividerLocation((int)(this.getHeight() - 75));
        setVisible(true);

	}

	/**
	 * Method to close this frame
	 */
	private void close() {
		this.dispose();
	}

	/**
	 * Method that clears the New tab's textfields
	 */
	private void clear() {
		text1.setText("");
		jComboBox1.setSelectedIndex(0);
		jComboBox2.setSelectedIndex(0);
		text4.setText("");
		text5.setText("");
	}

	/**
	 * Method to create a new Trigger from the New tab's info
	 */
	private void create() {
		if ((text1.getText().length() > 0) && (text4.getText().length() > 0)) {
			if (text5.getText().length() > 0) {
				TriggerWriter.add((String)(jComboBox1.getSelectedItem()), (String)(jComboBox2.getSelectedItem()), text1.getText(), text4.getText(), text5.getText());
			} else {
				TriggerWriter.add((String)(jComboBox1.getSelectedItem()), (String)(jComboBox2.getSelectedItem()), text1.getText(), text4.getText(), null);
			}
			try {
				Trigger.load(KilCli.getProfile());
				JOptionPane confirm = new JOptionPane();
				confirm.showMessageDialog(this, "Trigger " + text1.getText() + " created");
				clear();
				jScroll = createEdit();

			} catch (IOException ioe) {}
		} else {
			new JOptionPane().showMessageDialog(this, "Bad input error, could not create trigger");
		}
	}

	/**
	 * Method to update the triggers from the Edit menu's info
	 */
	private void update() {
		for (int i = 0; i < (textArray.length); i++) {
			while ((i < delArray.length) && (delArray[i].isSelected())) {

				for (int j = i; j < targetArray.length - 1; j++) {
					targetArray[j] = targetArray[j+1];
				}
				JTextField[] tmp = new JTextField[(targetArray.length - 1)];
				System.arraycopy(targetArray, 0, tmp, 0, targetArray.length - 1);
				targetArray = tmp;

				for (int j = i; j < textArray.length - 1; j++) {
					textArray[j] = textArray[j+1];
				}
				tmp = new JTextField[(textArray.length - 1)];
				System.arraycopy(textArray, 0, tmp, 0, textArray.length - 1);
				textArray = tmp;

				for (int j = i; j < scriptArray.length - 1; j++) {
					scriptArray[j] = scriptArray[j+1];
				}
				tmp = new JTextField[(scriptArray.length - 1)];
				System.arraycopy(scriptArray, 0, tmp, 0, scriptArray.length - 1);
				scriptArray = tmp;
				tmp = null;

				for (int j = i; j < caseArray.length - 1; j++) {
					caseArray[j] = caseArray[j+1];
				}
				JComboBox[] tmp2 = new JComboBox[(caseArray.length - 1)];
				System.arraycopy(caseArray, 0, tmp2, 0, caseArray.length - 1);
				caseArray = tmp2;

				for (int j = i; j < enabledArray.length - 1; j++) {
					enabledArray[j] = enabledArray[j+1];
				}
				tmp2 = new JComboBox[(enabledArray.length - 1)];
				System.arraycopy(enabledArray, 0, tmp2, 0, enabledArray.length - 1);
				enabledArray = tmp2;
				tmp2 = null;

				for (int j = i; j < delArray.length - 1; j++) {
					delArray[j] = delArray[j+1];
				}
				JCheckBox[] tmp3 = new JCheckBox[(delArray.length - 1)];
				System.arraycopy(delArray, 0, tmp3, 0, delArray.length - 1);
				delArray = tmp3;
				tmp3 = null;
			}
		}
		for (int i = 0; i < textArray.length; i++) {
			if ((textArray[i].getText().length() < 1) || (scriptArray[i].getText().length() < 1)) {
				new JOptionPane().showMessageDialog(this, "Error, blank text or script, could not update");
				return;
			}
		}
		TriggerWriter.update(enabledArray, caseArray, textArray, scriptArray, targetArray);
		try {
			Trigger.load(KilCli.getProfile());
			JOptionPane confirm = new JOptionPane();
			confirm.showMessageDialog(this, "Triggers Updated");
			jScroll = createEdit();

		} catch (IOException ioe) {}
	}

	/**
	 * Method to create the Edit tab's information
	 */
	private JScrollPane createEdit() {
        jScroll.setVerticalScrollBarPolicy(22);
		JPanel view = new JPanel();
		JPanel cell = new JPanel();
		JLabel pix = new JLabel();
		JCheckBox jCheck;
        ArrayList triggers = Trigger.getTriggers();
        int[][] targets = Trigger.getTargets();
        view = new JPanel();
        GridBagLayout gridBagLayout1 = new GridBagLayout();
		view.setLayout(gridBagLayout1);

        jScroll.setViewportView(view);
		cell = new JPanel();
		pix = new JLabel("Del");
		cell.add(pix);
		view.add(cell, createConstraints(0, 0, new Insets(0, 0, 0, 0), -9));
		cell = new JPanel();
		pix = new JLabel("Trigger Text");
		cell.add(pix);
		view.add(cell, createConstraints(1, 0, new Insets(0, 0, 0, 0), 54));
		cell = new JPanel();
		pix = new JLabel("Enabled");
		cell.add(pix);
		view.add(cell, createConstraints(2, 0, new Insets(0, 0, 0, 9), 0));
		cell = new JPanel();
		pix = new JLabel("Case");
		cell.add(pix);
		view.add(cell, createConstraints(3, 0, new Insets(0, 0, 0, 0), 8));
		cell = new JPanel();
		pix = new JLabel("Script");
		cell.add(pix);
		view.add(cell, createConstraints(4, 0, new Insets(0, 0, 0, 16), 13));
		cell = new JPanel();
		pix = new JLabel("Word #(s)");
		cell.add(pix);
		view.add(cell, createConstraints(5, 0, new Insets(0, 0, 0, 0), -7));
		int tmp = 1;
		textArray = new JTextField[(triggers.size() / 4)];
		enabledArray = new JComboBox[(triggers.size() / 4)];
		caseArray = new JComboBox[(triggers.size() / 4)];
		scriptArray = new JTextField[(triggers.size() / 4)];
		targetArray = new JTextField[(triggers.size() / 4)];
		delArray = new JCheckBox[(triggers.size() / 4)];
		for (int i = 0; i < triggers.size(); i+=4) {
			cell = new JPanel();
			jCheck = new JCheckBox();
			delArray[tmp - 1] = jCheck;
			cell.add(jCheck);
			view.add(cell, createConstraints(0, tmp, new Insets(0, 0, 0, 0), 0));
			cell = new JPanel();
			text6 = new JTextField(15);
			text6.setText((String)(triggers.get(i+2)));
			textArray[tmp-1] = text6;
			cell.add(text6);
			view.add(cell, createConstraints(1, tmp, new Insets(0, 0, 0, 0), 0));
			cell = new JPanel();
			jComboBox3 = new JComboBox();
			jComboBox3.addItem("True");
			jComboBox3.addItem("False");
			if (((String)(triggers.get(i))).toLowerCase().equals("true")) {
				jComboBox3.setSelectedIndex(0);
			} else {
				jComboBox3.setSelectedIndex(1);
			}
			enabledArray[tmp-1] = jComboBox3;
			cell.add(jComboBox3);
			view.add(cell, createConstraints(2, tmp, new Insets(0, 0, 0, 0), 0));
			cell = new JPanel();
			jComboBox4 = new JComboBox();
			jComboBox4.addItem("True");
			jComboBox4.addItem("False");
			if (((String)(triggers.get(i+1))).toLowerCase().equals("true")) {
				jComboBox4.setSelectedIndex(0);
			} else {
				jComboBox4.setSelectedIndex(1);
			}
			caseArray[tmp-1] = jComboBox4;
			cell.add(jComboBox4);
			view.add(cell, createConstraints(3, tmp, new Insets(0, 0, 0, 0), 0));
			cell = new JPanel();
			text7 = new JTextField(8);
			text7.setText((String)(triggers.get(i+3)));
			scriptArray[tmp-1] = text7;
			cell.add(text7);
			view.add(cell, createConstraints(4, tmp, new Insets(0, 0, 0, 0), 0));
			cell = new JPanel();
			text8 = new JTextField(14);
			text8.setToolTipText("<html>Leave blank to avoid passing anything<br>Leave values at -1 if you aren't using them");
			if (targets[tmp-1][0] != -1) {
				String temp = "";
				for (int j = 0; j < 5; j++) {
					temp = temp + targets[tmp-1][j] + "|";
				}
				temp = temp.substring(0, temp.length() - 1);
				text8.setText(temp);
				text8.setCaretPosition(0);
			}
			targetArray[tmp-1] = text8;
			cell.add(text8);
			view.add(cell, createConstraints(5, tmp, new Insets(0, 0, 0, 0), 0));
			tmp++;
		}
		jScroll.setViewportView(view);
		return jScroll;
	}

	/**
	 * Creates a GridBagConstraints object with the specified grids, insets<br>
	 * and ipadx, filling in defaults for the other settings
	 *
	 * @return GridBagContraints object with necessary values
	 * @param gx - gridx value
	 * @param gy - gridy value
	 * @param i - insets value
	 * @param ix - ipadx value
	 */
	private GridBagConstraints createConstraints(int gx, int gy, Insets i, int ix) {
		GridBagConstraints temp = new GridBagConstraints();
		temp.gridx = gx;
		temp.gridy = gy;
		temp.gridwidth = 1;
		temp.gridheight = 1;
		temp.weightx = 0.0;
		temp.weighty = 0.0;
		temp.anchor = GridBagConstraints.CENTER;
		temp.fill = GridBagConstraints.NONE;
		temp.insets = i;
		temp.ipadx = ix;
		temp.ipady = 0;
		return temp;
	}
}
