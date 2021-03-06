//+++++++++++++++++++++++++++++++++ GUI +++++++++++++++++++++++++++++++
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * GUI.java -- skeleton class.
 * Creates the panel for showing cards
 *
 * @author rdb 
 * 03/18/08
 */

public class GUI extends JPanel 
{
    //------------------------- instance variables -----------------------
    private Game    _game;             // the game object
    private int     _width  = 900;     // width of the panel
    private int     _height = 600;     // height of the panel
    
    //------------------------- constructor ----------------------------
    /**
     * GUI constructor creates the game and waits for interaction.
     * 
     * @param frame Container
     */
    public GUI( Container frame ) 
    {
        super();                 
        setLayout( new BorderLayout() );
        
        _game = new Game( _width );
        this.add( _game, BorderLayout.CENTER );
        
        setSize( _width, _height );
                
        ///////////////////////////////////////////////////////
        // build the gui
        ///////////////////////////////////////////////////////
        String[] buttonLabels = { "New Game", "Replay", "Draw", 
                                  "Undo", "AutoPlay" };
        Component buttonMenu = makeButtonMenu( buttonLabels );
        this.add( buttonMenu, BorderLayout.NORTH );
        
        Component controlMenu = makeControlMenu();
        this.add( controlMenu, BorderLayout.SOUTH );
    }
    //------------------- makeControlMenu --------------------------------
    /**
     * Create a control menu that includes two Spinners.
     * @return Component
     */
    private Component makeControlMenu( )
    {
        JPanel sMenu = new JPanel(); 
        LabeledSpinner seedSpinner;
        seedSpinner = new LabeledSpinner( "RandomSeed", 0, 30, 0 );
        
        // use an "anonymous" class for the listener
        ChangeListener  cl = new ChangeListener()
        {  
            /**
             * stateChanged method.
             * @param ev ChagedEvent
             */
            public void stateChanged( ChangeEvent ev )
            {  
                JSpinner spinner = (JSpinner) ev.getSource();
                Number value = (Number) spinner.getValue();
                Game.theGame.newSeed( value.intValue() );
            }
        };
        seedSpinner.addChangeListener( cl );
        sMenu.add( seedSpinner );
        
        LabeledSpinner rowsSpinner = new LabeledSpinner( "#rows", 3, 7, 4 );
        
        // use an "anonymous" class for the listener
        cl = new ChangeListener()
        {  
            /**
             * stateChanged method.
             * @param ev ChagedEvent
             */
            public void stateChanged( ChangeEvent ev )
            {  
                JSpinner spinner = (JSpinner) ev.getSource();
                Number value = (Number) spinner.getValue();
                Game.theGame.setRows( value.intValue() );
            }
        };
        rowsSpinner.addChangeListener( cl );
        sMenu.add( rowsSpinner );
        
        return sMenu;
    }
    //------------------- makeButtonMenu ------------------------------
    /**
     * create the button menu for this application.
     * @param labels String[]    labels for menu items.
     * @return Component
     */
    private Component makeButtonMenu( String[] labels )
    {
        JPanel bMenu = new JPanel( new GridLayout( 1, 0 ) ); 
        JButton button;
        for ( int i = 0; i < labels.length; i++ )
        {
            button = new JButton( labels[ i ] );
            bMenu.add( button );
            button.addActionListener( new ButtonListener( i ) );
        }
        bMenu.setSize( 40, 400 );
        
        return bMenu;
    }
    //+++++++++++++++++ ButtonListener inner class +++++++++++++++++++++
    /**
     * ButtonListener -- distributes button events to appropriate methods
     *                   of the GUI class.
     * @author rdb
     */
    public class ButtonListener implements ActionListener
    {
        int _buttonId;
        /**
         * Button listener component.
         * @param buttonId int
         */
        public ButtonListener( int buttonId )
        {
            _buttonId = buttonId;
        }
        /**
         * The event handler.
         * @param ev ActionEvent
         */
        public void actionPerformed( ActionEvent ev )
        {
            String msg = null;
            switch ( _buttonId )
            {
                case 0:
                    _game.makeNewDeck();
                    break;
                case 1:
                    _game.replay();
                    break;                    
                case 2:
                    _game.draw();
                    break;                    
                case 3:
                    _game.undoPlay();
                    break;                    
                case 4:
                    _game.autoPlay();
                    break;                    
            }               
        }
    }
    //------------------- endGame( String ) ----------------------------
    /**
     * At end of game pop up a dialog.
     * 
     * @param msg String
     */
    private void endGame( String msg )
    {
        msg = msg + "\nWant a new deck?";
        int choice = JOptionPane.showConfirmDialog( null, msg );
        if ( choice == 0 )
            _game.makeNewDeck();
        else if ( choice == 1 )
            System.exit( 0 );
    }
    //------------------ main ------------------------------------------   
    /**
     * This main is a convenience call to the application.
     * 
     * @param args String[]            command line arguments.
     */
    public static void main( String[] args )
    {
        // Invoke main class's main
        PyramidSolitaire.main( args );
    }
}   
