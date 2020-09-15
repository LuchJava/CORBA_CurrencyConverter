package CurrConvertApp;


/**
* CurrConvertApp/CurrConvertPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from currconvert.idl
* Tuesday, September 15, 2020 9:46:47 AM SGT
*/

public abstract class CurrConvertPOA extends org.omg.PortableServer.Servant
 implements CurrConvertApp.CurrConvertOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("getSymbols", new java.lang.Integer (0));
    _methods.put ("symbolExist", new java.lang.Integer (1));
    _methods.put ("convertAll", new java.lang.Integer (2));
    _methods.put ("convertAllWithAmount", new java.lang.Integer (3));
    _methods.put ("convertSome", new java.lang.Integer (4));
    _methods.put ("convertSomeWithAmount", new java.lang.Integer (5));
    _methods.put ("displaySymbols", new java.lang.Integer (6));
    _methods.put ("shutdown", new java.lang.Integer (7));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // CurrConvertApp/CurrConvert/getSymbols
       {
         String $result = null;
         $result = this.getSymbols ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // CurrConvertApp/CurrConvert/symbolExist
       {
         String symbol = in.read_string ();
         boolean $result = false;
         $result = this.symbolExist (symbol);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // CurrConvertApp/CurrConvert/convertAll
       {
         String base = in.read_string ();
         String $result = null;
         $result = this.convertAll (base);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 3:  // CurrConvertApp/CurrConvert/convertAllWithAmount
       {
         double amount = in.read_double ();
         String base = in.read_string ();
         String $result = null;
         $result = this.convertAllWithAmount (amount, base);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 4:  // CurrConvertApp/CurrConvert/convertSome
       {
         String base = in.read_string ();
         String target = in.read_string ();
         String $result = null;
         $result = this.convertSome (base, target);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // CurrConvertApp/CurrConvert/convertSomeWithAmount
       {
         double amount = in.read_double ();
         String base = in.read_string ();
         String target = in.read_string ();
         String $result = null;
         $result = this.convertSomeWithAmount (amount, base, target);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 6:  // CurrConvertApp/CurrConvert/displaySymbols
       {
         String $result = null;
         $result = this.displaySymbols ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 7:  // CurrConvertApp/CurrConvert/shutdown
       {
         this.shutdown ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CurrConvertApp/CurrConvert:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public CurrConvert _this() 
  {
    return CurrConvertHelper.narrow(
    super._this_object());
  }

  public CurrConvert _this(org.omg.CORBA.ORB orb) 
  {
    return CurrConvertHelper.narrow(
    super._this_object(orb));
  }


} // class CurrConvertPOA