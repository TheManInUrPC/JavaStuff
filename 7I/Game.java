//+++++++++++++++++++++++++ Game.java +++++++++++++++++++++++++++++
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
/**
 * Game.java - implementation of a solitaire card game.
 *
 * @author rdb
 * March 2009
 * mlb 10/09: new cards , new shuffle, new baseDeck
 * rdb 03/14: Changes to reflect Pyramid solitaire and Pyramid class.
 */

public class Game extends JPanel
{
    //----------------------- class variables ---------------------------
    private static int   seed = 0;
    static         Game  theGame;
    static         int   numRows = 4;
    //----------------------- instance variables ------------------------

    private CardStack             _drawPile = null;
    private CardStack             _discards = null;
    private ArrayList<Card>       _baseDeck = null;
    private int                   _parentWidth;
    private Pyramid<Card>         _pyramid;
    private Random                _rng;
    private ArrayList<Card>       _pyramidCards;

    //----- positioning variables

    private   int discardX  = 60,  discardY  = 140;
    private   int drawPileX = 60,  drawPileY = 40;
    private   int pyramidX  = 400,   pyramidY  = 40;

    //---------------------- constructor -----------------------------
    /**
     * Game is where most of the game-based code is found.
     *
     * @param pWidth int     width of the panel (apprx)
     */
    public Game( int pWidth )
    {
        this.setLayout( null );
        theGame = this;

        _parentWidth = pWidth;
        _baseDeck = new ArrayList<Card>();
        _pyramidCards = new ArrayList<Card>();

        createDeck();
        _discards = new CardStack( this, discardX, discardY );
        _discards.setYOffset( 2 );

        _drawPile = new CardStack( this, drawPileX, drawPileY );
        _drawPile.setXOffset( 0 );
        _rng = new Random( seed );
        makeNewDeck();
    }
    //------------------------- drawCard() ----------------------------
    /**
     * Draw a card from the display pile.
     *
     * @return String
     */
    public String drawCard()
    {
        String msg = null;
        ////////////////////////////////////////////////////////////////
        //
        // To draw a card, you need to pop it from the _drawPile
        //   and push it onto the _discards pile.
        // Of course, check if it is empty.
        // If the draw pile stack is empty, this method should return
        //   a non-null message -- indicating the game is over.
        ///////////////////////////////////////////////////////////////
        if ( _drawPile.size() != 0 )
        {
            Card next = _drawPile.pop();
            _discards.push( next );
        }
        else
            msg = "No more cards to draw." ;

        update();
        return msg;
    }
    //------------------------ makeNewDeck() --------------------------
    /**
     * Create new deck.
     */
    public void makeNewDeck()
    {
        Collections.shuffle( _baseDeck, _rng );
        replay();
    }

    //---------------------- replay( ) -----------------------------
    /**
     * Replay the game.
     */
    public void replay()
    {
        _discards.clear();

        deckToDrawPile( _baseDeck );
        dealCards( _drawPile );

        update();
    }
    //---------------------- draw( ) -----------------------------
    /**
     * Draw from draw pile. Simulate click on draw pile.
     */
    public void draw()
    {
        play( _drawPile.top() );
    }
    //---------------------- undoPlay( ) -----------------------------
    /**
     * Undo the previous play.
     */
    public void undoPlay()
    {
        /////////////////////////////////////////////////////////////
        // 7I: Do the "inverse" of the last play
        /////////////////////////////////////////////////////////////
        System.out.println( "Undo not implemented." );



        reDrawPyramid();
        update();
    }
    //---------------------- autoPlay( ) ----------------------------
    /**
     * Go into auto play mode.
     */
    public void autoPlay()
    {
        deckToDrawPile( _baseDeck );

        _discards.clear();
        _pyramid = new Pyramid<Card>( numRows, pyramidX, pyramidY,
                                  Card.width, Card.height );
        dealCards( _drawPile );
        //////////////////////////////////////////////////////////////
        // This will be part of 7P
        //////////////////////////////////////////////////////////////

        System.out.println( "Auto play not implemented." );




        update();
    }
    //------------------------ play( Card ) --------------------------
    /**
     * Play a card.
     *
     * @param picked Card     a card that is to be played.
     * @return boolean        true means a valid play occurred.
     */
    public boolean play( Card picked )
    {
       
        boolean playedFromPyramid = false;

        if ( picked == _drawPile.top() )
            drawCard();
        else if ( playPyramidCard( picked ) )
        {
            playedFromPyramid = true;
            ///////////////////////////////////////////////////////////
            // 7I: Need to check here to see if game has ended
            ///////////////////////////////////////////////////////////
            //If Player loses
            if( _drawPile.top() == null )
            {
                System.out.println( "You Lose");
            }
            else if(   )
            {
               
                
            }
                    
                
              
         
                
                





        }
        return playedFromPyramid;


    }
    //------------------------ playPyramidCard ------------------------
    /**
     * Play a card from the pyramid.
     *
     * @param picked Card
     * @return boolean
     */
    private boolean playPyramidCard( Card picked )
    {
        boolean success = false;

        PyramidNode cardNode = picked.getNode();
        {
            Card.Rank pickedRank = picked.getRank();
            Card top = _discards.top();
            int diff = Math.abs( pickedRank.ordinal()
                                 - top.getRank().ordinal() );

            if ( diff == 1 || diff == 12 )
            {
                _discards.push( picked );
                update();
                success = true;
            }
        }
        return success;
    }
    //----------------------------- update() ----------------------
    /**
     * Update the display components as needed.
     */
    public void update()
    {
        /////////////////////////////////////////////////////////////
        // You might need to do something here. I didn't, but there
        //    or valid implementations that might require it.
        /////////////////////////////////////////////////////////////

        // show all cards on the _discards stack
        _discards.showCards( -1 );

        // show no cards on the draw pile
        _drawPile.showCards( 0 );
        this.repaint();
    }


    //-------------------- dealCards() ----------------------------
    /**
     * Deal the cards from the drawPile to the pyramid.
     *    This version just stores all the dealt cards in an ArrayList.
     * @param deck CardStack    deck to deal from
     */
    public void dealCards( CardStack deck )
    {
        /////////////////////////////////////////////////////////////
        // This approach creates PyramidNodes for each Card in the
        //    pyramid, but does not put the nodes into a tree.
        // You'll have to figure out where/how to do that and what
        //    changes you'll have to make here.
        /////////////////////////////////////////////////////////////
        int xGap = 2;
        int yDelta = 30;
        for ( int level = 0; level < numRows; level++ )
        {
            int span = Card.width * ( level + 1 ) + xGap * level;

            int xPos = pyramidX - span / 2;
            int yPos = pyramidY + level * yDelta;
            for ( int n = 0; n < level + 1; n++ )
            {
                PyramidNode<Card> node = new PyramidNode<Card>( xPos, yPos,
                                             Card.width, Card.height );
                Card card = deck.pop();
                _pyramidCards.add( card );
                card.setNode( node );
                node.setData( card );
                card.setFaceUp( true );
                card.setLocation( xPos, yPos );
                this.setComponentZOrder( card, 0 );
                xPos += Card.width + xGap;
            }
        }
        reDrawPyramid();
    }
    //-------------------- reDrawPyramid ----------------------------
    /**
     * Draw the pyramid tree cards.
     */
    public void reDrawPyramid()
    {
        //////////////////////////////////////////////////////////////
        //  In its present form it just redisplays every card that was
        //     once in the pyramid, whereever they happen to be.
        //  It may or may not be good enough for your implementation.
        //////////////////////////////////////////////////////////////
        for ( Card card: _pyramidCards )
        {
            card.setFaceUp( true );
            this.setComponentZOrder( card, 0 );
        }

    }

    //////////////////////////////////////////////////////////////////
    // You probably don't need to edit anything below here,
    //    but you may if you wish.
    //////////////////////////////////////////////////////////////////
    //------------------------ newSeed( ) -----------------------------
    /**
     * Set new random number generator seed.
     *
     * @param newSeed int
     */
    public void newSeed( int newSeed )
    {
        _rng = new Random( newSeed );
        seed = newSeed;
    }
    //------------------------ setRows( ) -----------------------------
    /**
     * Change the number of rows in the game.
     *     Won't have effect until next game is played.
     * @param rows int
     */
    public void setRows( int rows )
    {
        numRows = rows;
    }
    //------------------- theGame() --------------------------------
    /**
     * Singleton pattern requires this method to return a reference
     *    to only allowed instance.
     * @return Game
     */
    public static Game theGame()
    {
        return theGame;
    }
    //------------------ showDeck() ----------------------------------
    /**
     * Turn all cards in deck face up and spread them out.
     */
    public void showDeck()
    {
        _drawPile.setXOffset( 11 );
        _drawPile.showCards( -1 );
        this.repaint();
    }
    //------------------ hideDeck() ----------------------------------
    /**
     * Turn all cards in deck face down and stack them up again.
     */
    public void hideDeck()
    {
        _drawPile.setXOffset( 0 );
        _drawPile.showCards( 0 );
        this.repaint();
    }

    //------------------------ createDeck() ---------------------------
    /**
     * Create a deck of cards in the _base variable.
     */
    private void createDeck()
    {
        int  cardIndex = 0;

        for ( Card.Suit suit: Card.Suit.values() )
        {
            for ( Card.Rank rank: Card.Rank.values() )
            {
                Card card = new Card( rank, suit );
                _baseDeck.add( 0, card );
                this.add( card );
            }
        }
    }

    //------------------------ deckToDrawPile( Card[] ) ---------------
    /**
     * Copy an array of cards into CardStack representing draw pile.
     *
     * @param deck ArrayList<Card>
     */
    private void deckToDrawPile( ArrayList<Card> deck )
    {
        _drawPile.clear();
        for ( int c = 0; c < 52; c++ )
            _drawPile.push( deck.get( c ) );
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //--------------------------- main ---------------------------------
    /**
     * A convenient tool for invoking main class.
     * @param args String[]    Command line arguments
     */
    public static void main( String[] args )
    {
        // Invoke main class's main
        PyramidSolitaire.main( args );
    }
}
