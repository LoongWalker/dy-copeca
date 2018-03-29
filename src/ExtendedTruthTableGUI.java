/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templatesf
 * and open the template in the editor.
 */


import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Sangharatana Godboley
 */
public class ExtendedTruthTableGUI extends javax.swing.JFrame {
private final Desktop desktop;
    /**
     * Creates new form ExtendedTruthTableGUI
     */
    public ExtendedTruthTableGUI() {
         desktop=Desktop.getDesktop();
        initComponents();
    }
 public void ETTCreation() throws FileNotFoundException, IOException {
        BufferedReader fpReadPredicate1 = new BufferedReader(new FileReader("../Predicates.txt"));
        BufferedReader fpttmax = new BufferedReader(new FileReader(jTextField1.getText()));
        String linenum=fpttmax.readLine();
        int maxval=0;
        while(linenum!=null){
            if(linenum.contains("Test Case File Number is")||linenum.contains("Predicate")){}
            else{maxval++;}
            linenum=fpttmax.readLine();
        }
        PrintWriter ett=new PrintWriter("../MCDC_report.txt");
        int no_of_input_file=0;
//        Scanner s=new Scanner(System.in);
//        System.out.println("Enter the number of test case file: ");
        no_of_input_file=maxval+5;
        String predicate=fpReadPredicate1.readLine();
        int count_predicate=0;
        int independent=0;
        int totalcondition=0;
        while(predicate!=null){
            if(predicate.contains("&&")||predicate.contains("||")){
                count_predicate++;
                ett.println("The extended truth table for predicate"+count_predicate+":"+predicate);
                int count_clause=0;
                String help=predicate;
//                help=help.replaceAll("(", "");
//                help=help.replaceAll(")", "");
                predicate=predicate.replace("||", "&&");
                ett.print("TestData_ID"+"                ");
                System.out.print("TestData_ID"+"                ");
                String contns="";
                String[] res = predicate.split("&&");
                for (String rp: res) {
                            count_clause++;
                            String kh=rp;
                           kh=kh.replace("!((", "");
                           kh=kh.replace("(", "");
                           kh=kh.replace(")", "");
                            contns=contns+kh+"                ";
                        }
                ett.print(contns);
                System.out.print(""+contns);
                ett.print("Result"+"                ");
                System.out.print("Result"+"                ");
                ett.println("                    "+contns);
                System.out.println("                    "+contns);
                String ttt[][]=new String[no_of_input_file][count_clause+2];
                
                 BufferedReader fptt = new BufferedReader(new FileReader(jTextField1.getText()));
                 String fileread=fptt.readLine();
                 int filecountmaintain=0;
                 int fileusedcount=0;
                 while(fileread!=null){
                     if(fileread.contains("Test Case File Number is:")){
                         filecountmaintain++;
                     }
                     
                     else{ 
                         if(fileread.contains("Predicate "+count_predicate+":")){
                             //System.out.println("****");
                         fileread=fptt.readLine();
                         String getvalues="";
                         getvalues=fileread;
                         ttt[fileusedcount][0]="TD_"+filecountmaintain+"";
                         int hs1=1;
                          String[] res1 = getvalues.split(",");
                            for (String rp: res1) {
                             switch (rp) {
                                 case "0":
                                     ttt[fileusedcount][hs1++]="F";
                                   // System.out.println(""+ttt[fileusedcount][hs1-1]);
                                     break; 
                                 case "1":
                                     ttt[fileusedcount][hs1++]="T";
                                    // System.out.println(""+ttt[fileusedcount][hs1-1]);
                                     break;
                                 case "X":
                                   ttt[fileusedcount][hs1++]="X";
                                    // System.out.println(""+ttt[fileusedcount][hs1-1]);
                                     break;
                             }
                                 }
                            fileusedcount++;
                    
                 }
                     }
                      fileread=fptt.readLine();
            }
                 
                 String extendedtruthtable[][]=new String[fileusedcount][count_clause+1];
                 for(int i=0;i<fileusedcount;i++){
                     for(int j=0;j<count_clause+1;j++){
                         extendedtruthtable[i][j]="";
                     }
                 }
                 int independentcluase[]=new int[count_clause];
                 for(int i=0;i<count_clause;i++){
                     independentcluase[i]=0;
                 }
                 for(int h14=0;h14<fileusedcount;h14++){
                    for(int h15=1;h15<=count_clause;h15++){
                        for(int h16=0;h16<fileusedcount;h16++){
                             if((!ttt[h14][h15].contentEquals("X"))&&(!ttt[h16][h15].contentEquals("X"))&&(!ttt[h14][h15].contentEquals(ttt[h16][h15]))&&(!ttt[h14][count_clause+1].contentEquals(ttt[h16][count_clause+1]))){
                                // System.out.println("*****");
                                int countcl=0;
                               // System.out.println(""+extendedtruthtable[h14][h15]);
                                for(int h17=1;h17<=count_clause;h17++){
                                    if(h15==h17){
                                        ;
                                    }
                                    else{
                                        if(ttt[h14][h17].contentEquals(ttt[h16][h17])||ttt[h14][h17].contentEquals("X")||ttt[h16][h17].contentEquals("X")){
                                            countcl++;
                                        }
                                    }
                                }
                                if(countcl==(count_clause-1)){
                                    extendedtruthtable[h14][h15]=extendedtruthtable[h14][h15]+ttt[h16][0]+",";
                                    independentcluase[h15-1]=1;
                                }
                            }
                        }
                    }
                 }
                  for(int i=0;i<count_clause;i++){
                     if(independentcluase[i]==1){
                     independent++;}
                 }
                  totalcondition=totalcondition+count_clause;
                  //---------------Displayning values-------------
                  for(int i=0;i<fileusedcount;i++){
                     for(int j=0;j<count_clause+2;j++){
                         if(j<count_clause+1)
                         {System.out.print(""+ttt[i][j]+"                        ");
                         ett.print(""+ttt[i][j]+"                        ");}
                         else{
                         System.out.print(""+ttt[i][j]+" ");
                         ett.print(""+ttt[i][j]+"");}
                     }
                     for(int j=0;j<count_clause+1;j++){
                         System.out.print(""+extendedtruthtable[i][j]+"         ");
                         ett.print(""+extendedtruthtable[i][j]+"         ");
                     }
                     System.out.println("");
                     ett.println();
                 }
//                 for(int i=0;i<fileusedcount;i++){
//                     for(int j=0;j<count_clause+1;j++){
//                         System.out.print(""+extendedtruthtable[i][j]+"          ");
//                     }
//                     System.out.println("");
//                 }
                 
            ett.flush();
            predicate=fpReadPredicate1.readLine();
        }
    }
        ett.println();
        ett.println("***************MC/DC Evaluation******************");
        float mcdc=(independent*100)/totalcondition;
        System.out.println("Independent Conditions:"+independent);
        ett.println("Independent Conditions:"+independent);
        System.out.println("Total Conditions present:"+totalcondition);
        ett.println("Total Conditions present:"+totalcondition);
        System.out.println("MC/DC%: "+mcdc);
        ett.println("MC/DC%: "+mcdc);
        ett.print("************************************************");
        ett.flush();
        jTextField2.setText(mcdc+"");
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Extended Truth Table GUI");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("DCOPECA");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Browse Test Evaluator file Generated by running C program"));

        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton2.setText("Execute");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Modified Condition Decision Coverage (MC/DC)%:");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(223, 223, 223)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel2)
                        .addGap(40, 40, 40)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(jLabel1)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
JFileChooser fc= new JFileChooser();
        fc.showOpenDialog(null);
        File f= fc.getSelectedFile();
        String fl=f.getAbsolutePath();
        jTextField1.setText(fl);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    try {
        ETTCreation();        // TODO add your handling code here:
    } catch (IOException ex) {
        Logger.getLogger(ExtendedTruthTableGUI.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExtendedTruthTableGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExtendedTruthTableGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExtendedTruthTableGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExtendedTruthTableGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExtendedTruthTableGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
