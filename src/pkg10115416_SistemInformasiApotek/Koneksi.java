/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg10115416_SistemInformasiApotek;

import java.io.FileInputStream;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author 
     * Yayan Heryanto - 10115416
     * Iqbal Hasan - 10115567
     * Septa Farid Kurnia - 10115407
     * Nicko Novendestra - 10115257
 */
public class Koneksi {
    public Properties myPanel, myLanguage;
    public String strNamePanel;
    
    public Koneksi() {
        
    }
    
    public String settingPanel(String nmPanel){
        try {
            myPanel = new Properties();
            myPanel.load(new FileInputStream("lib/database.ini"));
            strNamePanel = myPanel.getProperty(nmPanel);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            System.err.println(e.getMessage());
            System.exit(0);
        }
        return strNamePanel;
    }
}
