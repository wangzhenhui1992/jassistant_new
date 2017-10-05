package ldjp.jassistant.gui;

import java.awt.KeyboardFocusManager;
import java.io.File;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import ldjp.jassistant.common.PJConst;
import ldjp.jassistant.util.ResourceManager;
import ldjp.jassistant.util.StringUtil;
import ldjp.jassistant.util.UIUtil;

/**
 * main entry
 */
public class Main {

	/**
	 * main frame
	 */
	public static FrmMain mdiMain = null;

	/**
	 * config path, if not specified, it will use user home
	 */
	static String configPath = PJConst.DEFAULT_CONFIG_DIR;

	/**
	 * default orginal config file in jar
	 */
	static String defaultConfigFile = PJConst.DEFAULT_CONFIGFILE_PATH;


	/**
	 * main entry method
	 *
	 * @param args the first parameter may be the config path
	 */
	public static void main(String[] args) {
	    final String[] options = new String[] { "中文","日本語", "English"};
        final int option = JOptionPane.showOptionDialog(null, null, "Select Language 言語選択 语言选择",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                options[1]);
        if (option == 0) {
            Locale.setDefault(Locale.CHINA);
        } else if (option == 1) {
            Locale.setDefault(Locale.JAPAN);
        } else if (option == 2) {
            Locale.setDefault(Locale.ENGLISH);
        }
//		initLog();
		if (args != null && args.length > 0) {
			configPath = args[0];
		} else {
			String userHome = System.getProperty("user.home");
			if (!StringUtil.isEmpty(userHome)) {
				configPath = StringUtil.concatPath(userHome, ".jassistant");
				String oldConfigPath = StringUtil.concatPath(userHome, "jassistant");
				if ((new File(oldConfigPath)).exists()) {
					configPath = oldConfigPath;
				}
			}
		}

		System.setSecurityManager(null);

		if (UIUtil.isJDK140()) {
			// fixjdk14 bug
			fixBug4665081();
		}
		// init resources and look and feed
		initResources();

		try {
			UIManager.setLookAndFeel(ResourceManager.getDefaultLookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}

		mdiMain = new FrmMain();

		if (UIUtil.isJDK140Later()) {
			mdiMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
			System.setProperty("swing.useSystemFontSettings", "false");
		}

		mdiMain.setVisible(true);
	}


	/**
	 * get the main mdi main frame
	 *
	 */
	public static FrmMain getMDIMain() {
		return mdiMain;
	}


	/**
	 * init resources (messages, configuration, template file)
	 *
	 */
	private static void initResources() {
		ResourceManager.initMessages();
		ResourceManager.initWords();
		ResourceManager.initConfiguration(configPath, defaultConfigFile);
		ResourceManager.initTemplate(configPath);
		ResourceManager.initLookAndFeel();
		ResourceManager.initClassLoader();
	}

	/*
	private static void initLog() {
		try {
			System.setOut(new java.io.PrintStream(
				new java.io.FileOutputStream("log.txt", true)));
			System.setErr(new java.io.PrintStream(
				new java.io.FileOutputStream("log.txt", true)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * only for jdk1.4, if you use jdk1.3 compile, please uncomment it.
	 * fix bug 4665081
	 */
	static void fixBug4665081() {
		KeyboardFocusManager f = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		f.addPropertyChangeListener(new java.beans.PropertyChangeListener () {
			public void propertyChange(java.beans.PropertyChangeEvent e) {
				if (e.getPropertyName().equals("focusedWindow")) {
					Thread.yield();
				}
			}
		});
	}
}
