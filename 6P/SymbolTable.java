/**
 * SymbolTable - this class  maintains a symbol table for variables.
 * 
 * This functionality lends itself to a class that uses the Singleton pattern.
 * That is, it enforces a restriction that only 1 instance can ever be created.
 * 
 */
import java.util.*;

public class SymbolTable
{
   //--------------------- class variables -------------------------------
   private static SymbolTable _theTable = null;
   
   //--------------------- instance variables ----------------------------
   // Use a Hashtable or a HashMap to store information (value) using the id 
   // as the key
   ///////////////////////////////////////////////////////////
   
   private Hashtable<String, Float> values;
   
   
   //------------- constructor --------------------------------------------
   /**
    * Note the constructor is private!
    */
   private SymbolTable()
   {
      //////// Create the hashtable or hashmap ///////////////////
       
       values = new Hashtable< String, Float >();
   }
   //------------- instance -----------------------------------------------
   /**
    * user code must call a static method to get the reference to the 
    * only allowed instance -- first call creates the instance.
    */
   public static SymbolTable instance()
   {
      if ( _theTable == null )
         _theTable = new SymbolTable();
      return _theTable;
   }
   
   //------------------------ setValue( String, float  ) ---------------------
   /**
    * Set an identifier's value to the specified value.
    */
   public void setValue( String var, float num )
   {
      /////////////////////////////////////////////////////// 
      //    Need to save information into the hash table
      //
      // You are allowed to change the signatures; for example, you
      //    can use Float or Number as the parameter type, both
      //    here and in getValue
      //////////////////////////////////////////////////////////
       
       values.put( var, num );
   }
   //------------------------ getValue( String ) ---------------------------
   /**
    * Look up an identifier in the hash table and return its value.
    * If the identifier is not in the table, add it with a value of 0
    * and return 0.
    */
   public float getValue( String var )
   {
      /////////////////////////////////////////////////////////////
      //  Use var as hash table key get value; return it as a float
      //    or a Number (Float)
      ////////////////////////////////////////////////////////////
       if( values.containsKey( var ) )
       {
           return values.get( var );
       }
       else
       {
           setValue( var, 0 );
           return 0;
       }
     
   }
   //------------------------- toString() -------------------------------
   public String toString()
   {
      //Print all key/values in the symbol table
      System.out.println( "Keys: " + values.keySet() );
      
      System.out.println( "Values: " + values.values() );
      
      return "{ }";
   }
   //--------------------------- main -----------------------------------
   /**
    * Simple unit testing for SymbolTable
    */
   public static void main( String[] args )
   {
      SymbolTable st = SymbolTable.instance();
      st.setValue( "a", 4.0f );
      float val = st.getValue( "a" );
      System.out.println( "Test: should print 4: " + val );
      
      val = st.getValue( "b" );
      System.out.println( "Test: should print 0: " + val );
      
      st.setValue( "a", 6.0f );
      val = st.getValue( "a" );
      System.out.println( "Test: should print 6: " + val );
      
      st.toString();
   }
}
