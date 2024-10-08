/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package predatorprey;
import java.awt.event.MouseListener;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

/**
 *
 * @author ishy1
 */
public class GridPanel extends javax.swing.JPanel implements MouseListener{

    /**
     * Creates new form GridPanel
     */
    
    GameModel gm;

    //variables
    boolean runSimulation;
    boolean editLayout;
    
    final int gridLength = 20;
    final int gridHeight = 20;
    
    int mouseX;
    int mouseY;
    
    int[][]grid;
    int[][]preylessSteps; 
    int[][]foodlessSteps; 
    int[][]bushes; 
    
    //0 - blank, 1 - prey, 2 - predator, 3 - bush
    int layoutValue;
    
    int preyPopulation;
    int predatorPopulation;
    
    Timer animationTimer;
    
    //constructor
    public GridPanel(GameModel g) {
        initComponents();
        gm = g;
        
        //intialize starting grid and other arrays
        grid = PredatorPrey.initializeGrid(gridLength,gridHeight);
        preylessSteps = new int[grid.length][grid[0].length];
        foodlessSteps = new int[grid.length][grid[0].length];
        bushes = PredatorPrey.initializeBushes(grid.length,grid[0].length);
        
        //set size of panel
        setSize(800,638);
        
        //set values for speed slider
        speedSlider.setMinimum(800);
        speedSlider.setMaximum(900);
                
        // tell the program we want to listen to the mouse
        addMouseListener(this);
        
        //create timer
        animationTimer = new Timer(speedSlider.getValue(), new animationTimerTick());
        animationTimer.start();
        
        
        repaint();
    }
    
    //resets the simulation
    private void resetSimulation(){
        //regenerate a grid and reset other arrays
        grid = PredatorPrey.initializeGrid(gridLength, gridHeight);
        preylessSteps = new int[grid.length][grid[0].length];
        foodlessSteps = new int[grid.length][grid[0].length];
        bushes = PredatorPrey.initializeBushes(grid.length,grid[0].length);
        
        //reset variables
        runSimulation = false;
        editLayout = false;
    }
    
    //paints the simulation grid
    public void paintComponent(Graphics g){
       super.paintComponent(g);
        
       //for each grid cell
        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                
                //paint a blue square if cell contains prey
                if (grid[i][j] == 1){
                    g.setColor(Color.BLUE);
                    g.fillRect(i*600/grid[0].length, j*600/grid[0].length, 600/grid[0].length, 600/grid[0].length); 
                }
                //paint a red square if cell contains predator
                else if (grid[i][j]==2){
                    g.setColor(Color.RED);
                    g.fillRect(i*600/grid[0].length, j*600/grid[0].length, 600/grid[0].length, 600/grid[0].length);
                }
                
                //paint a small green rect if cell contains a bush
                if(bushes[i][j] == 1){
                    g.setColor(Color.GREEN);
                    g.fillRect(i*600/grid[0].length, j*600/grid[0].length + 300/grid[0].length, 600/grid[0].length, 300/grid[0].length); 
                }
                
                //draw grid outlines
                g.setColor(Color.BLACK);
                g.drawRect(i*600/grid[0].length, j*600/grid[0].length, 600/grid[0].length, 600/grid[0].length);
                
            }
        }
       
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    //when mouse if clicked
    public void mousePressed(MouseEvent e) {
        //get mouse coordinates 
        mouseX = e.getX();
        mouseY = e.getY();
        
        //if edit button is toggled
        if(editLayout){
            //find the indices of the square clicked
            boolean stop = false;
            int xIndex;
            int yIndex;
            
            for (int i=0; i<grid.length && !stop; i++){
                for (int j=0; j<grid[0].length && !stop; j++){
                    if (i*600/grid.length <= mouseX && mouseX <= i*600/grid.length+600/grid.length 
                            && j*600/grid.length <= mouseY && mouseY <= j*600/grid.length+600/grid.length){
                        xIndex = i;
                        yIndex = j;
                        stop = true;
                        
                        //if empty space selected, add empty space to grid cell clicked
                        if (layoutValue == 0){
                            grid[xIndex][yIndex] = 0;
                            bushes[xIndex][yIndex] = 0;
                        }
                        //if prey selected, add prey to grid cell clicked
                        else if (layoutValue == 1){
                            grid[xIndex][yIndex] = 1;
                        }
                        //if predator space selected, add predator to grid cell clicked
                        else if (layoutValue == 2){
                            grid[xIndex][yIndex] = 2;
                        }
                        //if bush selected, add bush to grid cell clicked
                        else if (layoutValue == 3){
                            bushes[xIndex][yIndex] = 1;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    //code that occurs when animation timer ticks
    private class animationTimerTick implements ActionListener{
        public void actionPerformed(ActionEvent ae){
           //if simulation is running
            if (runSimulation){
               //move one turn
               PredatorPrey.oneTurn(grid, preylessSteps, foodlessSteps, bushes); 
           }
           
           //adjust currently selected value of custom layout dropbox
           if (customBox.getSelectedItem() == "None"){
               layoutValue = 0;
           }
           else if (customBox.getSelectedItem() == "Prey"){
               layoutValue = 1;
           }
           else if (customBox.getSelectedItem() == "Predator"){
               layoutValue = 2;
           }
           else if (customBox.getSelectedItem() == "Bush"){
               layoutValue = 3;
           }
           //get populations
           predatorPopulation = PredatorPrey.getPopulation(grid, 2);
           preyPopulation = PredatorPrey.getPopulation(grid, 1);

           //display populations
           predPopLabel.setText(Integer.toString(predatorPopulation));
           preyPopLabel.setText(Integer.toString(preyPopulation));
           
           //update timer 
           animationTimer.setDelay(1000-speedSlider.getValue());
           
           //update screen
           repaint();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        runButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        speedSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        predPopLabel = new javax.swing.JLabel();
        preyPopLabel = new javax.swing.JLabel();
        customBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        menuButton = new javax.swing.JButton();
        editButton = new javax.swing.JToggleButton();

        runButton.setText("Run");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        pauseButton.setText("Pause");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Speed");

        jLabel2.setText("1x");

        jLabel3.setText("2x");

        jLabel4.setText("Populations");

        jLabel5.setText("Predators");

        jLabel6.setText("Prey");

        predPopLabel.setText("Num");

        preyPopLabel.setText("Num");

        customBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Prey", "Predator", "Bush" }));
        customBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customBoxActionPerformed(evt);
            }
        });

        jLabel7.setText("Custom Layout");

        menuButton.setText("Back");
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });

        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(607, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resetButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(predPopLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(preyPopLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(19, 19, 19))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(59, 59, 59)
                                        .addComponent(jLabel3)))
                                .addGap(9, 9, 9))
                            .addComponent(customBox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(menuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(36, 36, 36)))
                .addGap(43, 43, 43))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(predPopLabel)
                    .addComponent(preyPopLabel))
                .addGap(34, 34, 34)
                .addComponent(runButton)
                .addGap(18, 18, 18)
                .addComponent(pauseButton)
                .addGap(18, 18, 18)
                .addComponent(resetButton)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(29, 29, 29)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(menuButton)
                .addGap(56, 56, 56))
        );
    }// </editor-fold>//GEN-END:initComponents

    //run button
    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        runSimulation = true;
    }//GEN-LAST:event_runButtonActionPerformed

    //pause button
    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        //pause simulation
        runSimulation = false;
    }//GEN-LAST:event_pauseButtonActionPerformed

    //reset button
    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed

        resetSimulation();
    }//GEN-LAST:event_resetButtonActionPerformed

    //back button
    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        //return to main menu
        CardLayout cl = (CardLayout) gm.jPanel1.getLayout();
        cl.show(gm.jPanel1,"StartPanel");
        resetSimulation();
    }//GEN-LAST:event_menuButtonActionPerformed

    private void customBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customBoxActionPerformed
    }//GEN-LAST:event_customBoxActionPerformed

    //edit toggle button
    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        //toggle edit button on/off
        if (editLayout){
            editLayout = false;
        }
        else{
            editLayout = true;
        }
    }//GEN-LAST:event_editButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> customBox;
    private javax.swing.JToggleButton editButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JButton menuButton;
    private javax.swing.JButton pauseButton;
    private javax.swing.JLabel predPopLabel;
    private javax.swing.JLabel preyPopLabel;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton runButton;
    private javax.swing.JSlider speedSlider;
    // End of variables declaration//GEN-END:variables
}
