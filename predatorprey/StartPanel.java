/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package predatorprey;

/**
 *
 * @author ishy1
 */
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
 

public class StartPanel extends javax.swing.JPanel {

    /**
     * Creates new form StartPanel
     */

    GameModel gm;
    int[][]grid;
    int[][]preylessSteps;
    int[][]foodlessSteps;
    int[][]bushes;
    
    
    Timer animationTimer;

    
    public StartPanel(GameModel g) {
        initComponents();
        
        //initalize grid and arrays for small simulation animation
        grid = PredatorPrey.initializeGrid(3,3);
        preylessSteps = new int[grid.length][grid[0].length];
        foodlessSteps = new int[grid.length][grid[0].length];
        bushes = new int[grid.length][grid[0].length];
        
        gm = g;
        
        //create timer
        animationTimer = new Timer(300, new StartPanel.animationTimerTick());
        animationTimer.start();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scoreLabel1 = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        infoButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        scoreLabel1.setFont(new java.awt.Font("Ink Free", 0, 36)); // NOI18N
        scoreLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreLabel1.setText("Preadtor & Prey");

        startButton.setFont(new java.awt.Font("Corbel Light", 0, 18)); // NOI18N
        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        infoButton.setFont(new java.awt.Font("Candara Light", 0, 18)); // NOI18N
        infoButton.setText("Info");
        infoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoButtonActionPerformed(evt);
            }
        });

        exitButton.setFont(new java.awt.Font("Candara Light", 0, 18)); // NOI18N
        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Javanese Text", 0, 18)); // NOI18N
        jLabel1.setText("Simulation");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(248, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(infoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(scoreLabel1)
                        .addGap(37, 37, 37)))
                .addGap(228, 228, 228))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(scoreLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(infoButton)
                    .addComponent(exitButton))
                .addContainerGap(409, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        CardLayout cl = (CardLayout) gm.jPanel1.getLayout();
        cl.show(gm.jPanel1,"GridPanel");
    }//GEN-LAST:event_startButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void infoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoButtonActionPerformed
        CardLayout cl = (CardLayout) gm.jPanel1.getLayout();
        cl.show(gm.jPanel1,"InfoPanel");
    }//GEN-LAST:event_infoButtonActionPerformed

    public void paintComponent(Graphics g){
       super.paintComponent(g);
        
       //draw small grid animation
        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                if (grid[i][j] == 1){
                    g.setColor(Color.BLUE);
                    g.fillRect(i*50+325, j*50+320, 50, 50); 
                }
                else if (grid[i][j]==2){
                    g.setColor(Color.RED);
                    g.fillRect(i*50+325, j*50+320, 50, 50);                 }
                
                g.setColor(Color.BLACK);
                g.drawRect(i*50+325, j*50+320, 50, 50);
                
            }
        }
       
    }
    
    private class animationTimerTick implements ActionListener{
        //code that occurs when animation timer ticks
        public void actionPerformed(ActionEvent ae){
            PredatorPrey.oneTurn(grid, preylessSteps, foodlessSteps, bushes); 
            
            //check if animation grid has no more preadtors
            boolean empty = true;
            for (int i=0; i<grid.length; i++){
                for (int j=0; j<grid[0].length; j++){
                    if (grid[i][j] == 2){
                        empty = false;
                    }
                }
            }
            
            //reset grid if no more predators
            if (empty){
                grid = PredatorPrey.initializeGrid(3,3);
            }
            
            
            repaint();

        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitButton;
    private javax.swing.JButton infoButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel scoreLabel1;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
