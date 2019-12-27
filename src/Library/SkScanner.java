package Library;


import java.io.*;

/*
 ** SkScanner: ±è¼º±â ±³¼ö°¡ ÀÛ¼ºÇÑ ÀÔ·ÂÀ» À§ÇÑ Å¬·¡½º
 **    o ÀÌ SkScanner Å¬·¡½º´Â ÀÚÀ¯·Ó°Ô ÀÔ·ÂµÈ µ¥ÀÌÅÍ Áß¿¡¼­ ¹®ÀÚ, Á¤¼ö, ½Ç¼ö, ¹®ÀÚ¿­, boolean, ½Äº°ÀÚ¸¦ 
 **        Ã£¾Æ¼­ ÀÔ·ÂÇÏ´Â ¸Ş¼ÒµåµéÀ» Á¤ÀÇÇÑ´Ù.
 **    o SkScanner Å¬·¡½ºÀÇ °´Ã¼¸¦ »ı¼ºÇÏÁö ¾Ê°í SkScanner Å¬·¡½ºÀÇ ¸Ş¼Òµå¸¦ »ç¿ëÇÒ ¼ö ÀÖµµ·Ï 
 **        ¸ğµç ¸Ş¼Òµå´Â static ¸Ş¼ÒµåÀÌ´Ù.
 **
 **    o »ç¿ë ¿¹ :  
 **        char c = SkScanner.getChar(); (¶Ç´Â SkScanner.getchar(); - ÀÌ´Â C ¾ğ¾î¿¡¼­ Á¦°øµÇ±â ¶§¹®)
 **        int n = SkScanner.getInt();             // ÀÔ·ÂµÇ´Â ¹®ÀÚ¿­¿¡¼­ Ã¹¹øÂ° Á¤¼ö¸¦ Ã£¾Æ int °ªÀ¸·Î ¹İÈ¯
 **        int n = SkScanner.getLong();            // ÀÔ·ÂµÇ´Â ¹®ÀÚ¿­¿¡¼­ Ã¹¹øÂ° Á¤¼ö¸¦ Ã£¾Æ long °ªÀ¸·Î ¹İÈ¯
 **        double d = SkScanner.getDouble()        // ÀÔ·ÂµÇ´Â ¹®ÀÚ¿­¿¡¼­ Ã¹¹øÂ° ½Ç¼ö¸¦ Ã£¾Æ double °ªÀ¸·Î ¹İÈ¯
 **        float f = SkScanner.getFloat()          // ÀÔ·ÂµÇ´Â ¹®ÀÚ¿­¿¡¼­ Ã¹¹øÂ° ½Ç¼ö¸¦ Ã£¾Æ float °ªÀ¸·Î ¹İÈ¯
 **        String name = SkScanner.getString();    // '\n'ÀÌ ÀÔ·ÂµÉ ¶§±îÁö ÀÔ·ÂµÈ ¹®ÀÚµéÀ» String °ªÀ¸·Î ¹İÈ¯
 **        boolean bool = SkScanner.getBoolean();  // ÀÔ·ÂµÇ´Â ¹®ÀÚ¿­¿¡¼­ true ¶Ç´Â false¸¦ Ã£¾Æ boolean °ªÀ¸·Î ¹İÈ¯
 **        String id = SkScanner.getIdentifier();  // ÀÔ·ÂµÇ´Â ¹®ÀÚ¿­¿¡¼­ JavaÀÇ ½Äº°ÀÚ¸¦ Ã£¾Æ String °ªÀ¸·Î ¹İÈ¯
 **                                                //   ½Äº°ÀÚÀÇ Ã¹±ÛÀÚ: (¿µ¹®ÀÚ ÇÑ±Û _ $), ²¿¸®ºÎºĞ: (¿µ¹®ÀÚ ÇÑ±Û ¼ıÀÚ _ $)
 **    o ÃÖÁ¾ ¼öÁ¤: 2015³â 7¿ù 19ÀÏ                                            
 ** 
 **    o Âü°í»çÇ×
 **        ÀÌ Å¬·¡½ºÀÇ ¸Ş¼ÒµåµéÀº Ç¥ÁØÀÔ·ÂÀÌ Å°º¸µåÀÎ °æ¿ì¿¡´Â Àß µ¿ÀÛÇÑ´Ù. 
 **        ±×·±µ¥ Ç¥ÁØÀÔ·ÂÀÌ Å°º¸µå°¡ ¾Æ´Ï°í ÆÄÀÏ µîÀ¸·Î º¯°æµÈ »óÅÂ¿¡¼­ ÀÔ·Â ¸Ş¼Òµå°¡ È£ÃâµÇ¸é
 **        Ã³¸® Áß¿¡ getcharPrivate()¿¡¼­ EOF(End Of File) ¹®ÀÚ¸¦ ¸¸³ª¸é -1ÀÌ ¹İÈ¯µÇ¸ç, 
 **        ÀÌ¶§ÀÇ ÀÔ·ÂÀº Á¦Àç·Î Ã³¸®µÇÁö ¾Ê°í ¿À·ù°¡ ¹ß»ıÇÒ ¼ö ÀÖ´Ù.
 **        ÀÌ¸¦ À§ÇØ ¿¹¿Ü Ã³¸®¸¦ ÇÒ ¼ö ÀÖÀ¸³ª °£ÆíÇÏ°Ô »ç¿ëÇÏ°Ô ÇÏ±â À§ÇØ ¿¹¿Ü Ã³¸®´Â ÇÏÁö ¾Ê¾ÒÀ½                                                   
 */

public class SkScanner { 
	final static int MAX_LENGTH_OF_PEEKCHARS = 100;
	final static int MAX_LENGTH_OF_STRING = 2000;
	
	static java.io.BufferedReader in;

	static {
		try {
			// Windows¿¡¼­ ÇÑ±ÛÀ» ÀÔ·ÂÇÏ±â À§ÇØ ¹®ÀÚ¼ÂÀ» "ms949"·Î ÁöÁ¤
			in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in, "ms949")); 
		} catch(Exception e) { 
			in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		}
	}
	
	private static int[] peekChars = new int[MAX_LENGTH_OF_PEEKCHARS]; // ÃÖ´ë 100°³ÀÇ ÀĞÀº ¹®ÀÚ¸¦ ´Ù½Ã ÀĞ±â À§ÇØ ÀúÀåÇÏ´Â peekChars ¹è¿­ 
	private static int peekcIndex = -1;	       // peekChars ¹è¿­¿¡¼­ÀÇ ¸¶Áö¸· ÀúÀå À§Ä¡, -1ÀÌ¸é ÀúÀåµÈ ¹®ÀÚ°¡ ¾øÀ½ 
	
	private static int sign;  // ÀĞÀº Á¤¼ö ¶Ç´Â ½Ç¼öÀÇ ºÎÈ£¸¦ ±â·ÏÇÏ±â À§ÇÑ º¯¼ö

	private SkScanner()  {
	}

	// getcharPrivate() : ÇÑ ¹®ÀÚ¸¦ ÀĞ±â À§ÇÑ ³»ºÎ Àü¿ë ¸Ş¼Òµå. 
	//   peekcIndex°¡ -1ÀÌ ¾Æ´Ï¸é peekChars[peekcIndex]¸¦ ¹İÈ¯
	//   peekcIndex°¡ -1ÀÌ¸é in.read()¸¦ ÀÌ¿ëÇÏ¿© ÇÑ ¹®ÀÚ¸¦ ÀĞ¾îµéÀÓ
	private static int getcharPrivate() {	
		int retval;

		try {
			if (peekcIndex >=  0) { 
				// peekcIndex°¡ -1ÀÌ ¾Æ´Ï¸é peekChars[peekcIndex]¸¦ ¹İÈ¯ÇÏ°í, peekcIndex °¨¼Ò
				retval = peekChars[peekcIndex--]; 

				return retval;
			}

			return in.read();  // Å°º¸µå¿¡¼­ ÀÔ·ÂµÈ ¹®ÀÚ¸¦ ÀĞ¾î ¹İÈ¯
		} catch (IOException e)  {  return -1;}
	}


	// ungetc(): in.read()¸¦ ÀÌ¿ëÇÏ¿© ÀÌ¹Ì ÀÔ·ÂÇÑ ¹®ÀÚ¸¦ ´Ù½Ã ÀĞÀ» ¼ö ÀÖµµ·Ï peekChars ¹è¿­¿¡ ÀúÀåÇÏ´Â ¸Ş¼Òµå
	private  static void ungetc(int c) {	
		peekChars[++peekcIndex] = c;	// peekcIndex¸¦ 1 Áõ°¡ÇÑ ÈÄ ÁÖ¾îÁø ¹®ÀÚ¸¦ peekChars[peekcIndex]¿¡ ÀúÀå
	} 

	// skipUntilDigit() : Ã¹ µğÁöÆ®°¡ ³ª¿Ã ¶§±îÁö ¹®ÀÚµéÀ» skipÇÏ¿© Ã¹ µğÁöÆ®¸¦ ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	//   ´Ü À½¼öºÎÈ£°¡ µğÁöÆ® ¾Õ¿¡ ³ª¿Ã ¶§¿¡´Â sign ÇÊµå¸¦ -1·Î ¸¸µç´Ù.
	private static int skipUntilDigit() {	
		int c;

		sign = 1;    // signÀº +·Î ½ÃÀÛÇÏ°Ô ÇÔ
		while((c=getcharPrivate()) != -1)  {
			if (c>='0' && c <= '9')  // ÀĞÀº ¹®ÀÚ°¡ µğÁöÆ®ÀÌ¸é ÀÌ¸¦ ¹İÈ¯
				return c;
			else if (c == '-')       // ÀĞÀº ¹®ÀÚ°¡ '-'ÀÌ¸é signÀ» À½¼ö(-1)·Î
				sign = -1;	     	
			else
				sign = 1;	         // ±×¿Ü´Â ¹«½ÃÇÏ°í signÀ» ¾ç¼ö(1)·Î set
		}

		return -1;
	}
	
	// skipUntilDigitOrDot() : Ã¹ µğÁöÆ® ¶Ç´Â '.'°¡ ³ª¿Ã ¶§±îÁö ¹®ÀÚµéÀ» skipÇÏ¿© Ã¹ µğÁöÆ®¸¦ ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	//   ´Ü À½¼öºÎÈ£°¡ µğÁöÆ® ¾Õ¿¡ ³ª¿Ã ¶§¿¡´Â sign ÇÊµå¸¦ -1·Î ¸¸µç´Ù.
	//   ½ºÅµÀ» ÇÒ ¶§ ºÎÈ£(+/-)°¡ ³ªÅ¸³ª¸é ÀÌ¸¦ Ç¥½ÃÇÏ±â À§ÇØ sing º¯¼ö¸¦ 1/-1À¸·Î ¼¼ÆÃÇÔ
	//   ½Ç¼ö¿¡¼­ +.123 ¶Ç´Â -.123 µîÀÇ ÆĞÅÏÀ» À§ÇØ '.' ÀÌÀü¿¡ ÀÔ·ÂµÈ ºÎÈ£µµ Ã³¸®ÇÔ 
	private static int skipUntilDigitOrDot() {	
		int c, cPriv = -1;

		sign = 1;    // signÀº +·Î ½ÃÀÛÇÏ°Ô ÇÔ
		
		while((c=getcharPrivate()) != -1)  {
			if (c>='0' && c <= '9' || c == '.')  // ÀĞÀº ¹®ÀÚ°¡ µğÁöÆ® ¶Ç´Â '.'ÀÌ¸é ÀÌ¸¦ ¹İÈ¯
				return c;
			else if (c == '+' || (c=='.' && cPriv=='+'))         // ÀĞÀº ¹®ÀÚ°¡ '+' ¶Ç´Â "+."ÀÌ¸é signÀ» ¾ç¼ö(1)·Î
				sign = 1;	     	
			else if (c == '-' || (c=='.' && cPriv=='-'))         // ÀĞÀº ¹®ÀÚ°¡ '-' ¶Ç´Â "-."ÀÌ¸é signÀ» À½¼ö(-1)·Î
				sign = -1;	     	
			else 
				sign = 1;	     // ±×¿Ü´Â ¹«½ÃÇÏ°í signÀ» ¾ç¼ö(1)·Î set
			
			cPriv = c;
		}

		return -1;
	}

	// removeLastNewLineChar(): Á¤¼ö, ½Ç¼ö, ¹®ÀÚ¿­, ½Äº°ÀÚ µîÀ» ÀĞ±â À§ÇØ ÀÔ·ÂµÈ '\r', '\n'À» Á¦°Å½ÃÅ´
	//   Á¤¼ö, ½Ç¼ö, ¹®ÀÚ¿­, ½Äº°ÀÚ µîÀ» ÀĞ±â À§ÇØ ÀÔ·ÂµÈ ¸¶Áö¸· '\r', '\n'À» Á¦°Å½ÃÄÑ 
	//   ´ÙÀ½¿¡ getChar()¸¦ ÇßÀ» ¶§ '\r', '\n'ÀÌ ÀĞÇôÁöÁö ¾Ê°Ô ÇÔ
	//   ÀÌ ¸Ş¼Òµå´Â ¹İµå½Ã getLong(), getDouble(), getString(),  getIndeifier()¸Ş¼ÒµåÀÇ ¸¶¹Ì¸·¿¡¼­ È£ÃâµÇ¾î¾ß ÇÔ
	static void removeLastNewLineChar() {
		int c1, c2;
		if ( (c1 = getcharPrivate()) == '\r') {
			if ( (c2 = getcharPrivate()) == '\n')		
				return;  // '\r', '\n'À» ÀĞ¾î Á¦°Å½ÃÅ´ 
			else {
				ungetc(c2);
				ungetc(c1);
			}
		} else
			ungetc(c1);
	}

	// getChar() : Ç¥ÁØÀÔ·Â¿¡¼­ÀÇ ¹®ÀÚµé¸¦ ÀĞ¾î ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	//   
	public static  char getChar() {
		return (char) getcharPrivate();
	}

	// getchar() : Ç¥ÁØÀÔ·Â¿¡¼­ÀÇ ¹®ÀÚµé¸¦ ÀĞ¾î ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	//   
	public static char getchar() {
		return getChar();
	}


	// getLong() : Ç¥ÁØÀÔ·Â¿¡¼­ÀÇ Á¤¼ö¹®ÀÚµé¸¦ ÀĞ¾î lopn Å¸ÀÔÀÇ °ªÀ¸·Î º¯È¯ÇÏ¿© ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	//     ¾ç¼ö»Ó ¾Æ´Ï¶ó À½¼öµµ Ã³¸®, Á¤¼ö ¾ÕÀÇ µğÁöÆ®°¡ ¾Æ´Ñ ¹®ÀÚ´Â ½ºÅµÇÔ
	//     Á¤¼ö Áß°£¿¡´Â _µµ °¡´ÉÇÔ(1_234_567, 1_____234______567)
	public static long getLong() {
		int c;

		c = skipUntilDigit();   // Ã¹ ¹øÂ° µğÁöÆ®¸¦ ¾òÀ» ‹š±îÁö ¹®ÀÚ¸¦ ÀĞ¾î ¹«½ÃÇÔ		
		ungetc(c);              // ÀĞÀº Ã¹ ¹øÂ° µğÁöÆ®¸¦ ´Ù½Ã ÀĞ±â À§ÇØ ungetc½ÃÅ´
		
		long l = getLongWithoutSkip(); // ½ºÅµ ¾øÀÌ Á¤¼ö¸¦ ÀĞ¾î long °ªÀ¸·Î ¹İÈ¯
		
		removeLastNewLineChar();       // Á¤¼ö, ½Ç¼ö, ¹®ÀÚ¿­, ½Äº°ÀÚ µîÀ» ÀĞ±â À§ÇØ ÀÔ·ÂµÈ '\r', '\n'À» Á¦°Å½ÃÅ´
		return l;
	}
		
	// getInt() : Ç¥ÁØÀÔ·Â¿¡¼­ÀÇ Á¤¼ö¹®ÀÚµé¸¦ ÀĞ¾î int Á¤¼ö·Î º¯È¯ÇÏ¿© ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	//     ¾ç¼ö»Ó ¾Æ´Ï¶ó À½¼öµµ Ã³¸®, Á¤¼ö ¾ÕÀÇ µğÁöÆ®°¡ ¾Æ´Ñ ¹®ÀÚ´Â ½ºÅµÇÔ
	public static  int getInt() {
		return (int) getLong() ;
	}

	// getLongStringWithoutSkip() : Ç¥ÁØÀÔ·Â¿¡¼­ÀÇ ½ºÅµ¾øÀÌ µğÁöÆ® ¹®ÀÚµé¸¦ ÀĞ¾î String Å¸ÀÔÀÇ °ªÀ¸·Î º¯È¯ÇÏ¿© ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	//     µğÁöÆ®°¡ ¾Æ´Ñ ¹®ÀÚ´Â ¸ğµÎ ½ºÅµµÈ »óÅÂ¿¡¼­ È£ÃâµÊ
	//     Java¿¡¼­´Â Á¤¼ö Áß°£¿¡ _¸¦ Çã¿ëÇÏ¿© ±ä ÀÚ¸®¼öÀÇ Á¤¼ö¸¦ ÀĞ±â ½±°Ô Ç¥Çö(1_234_567, 1_____234______567)ÇÏ¹Ç·Î
	//     Á¤¼ö Áß°£ÀÇ _´Â ¹«½ÃÇÔ
	private static String getLongStringWithoutSkip() {
		int c;
		String s = "";

		int countUnderScore = 0;  // Á¤¼ö Áß°£¿¡ ³ªÅ¸³ª´Â ¿¬¼ÓµÈ _ÀÇ °³¼ö

		while((c = getcharPrivate()) >='0' && c <='9' || c == '_')  { // ¿¬¼ÓµÇ´Â µğÁöÆ® ¶Ç´Â _¿¡ ´ëÇÏ¿©			
			if (c >= '0' && c <= '9') {
				s = s + (char) c;         //   ¹®ÀÚ¿­¿¡ Á¢¼Ó·Î º¯È¯
				countUnderScore = 0;
			}
			else if (c == '_') {  // -°¡ ÀÔ·ÂµÇ¸é
				countUnderScore++; // ¿¬¼ÓµÈ _ÀÇ °³¼ö¸¦ 1 Áõ°¡½ÃÅ´
			}
		}

		if (countUnderScore > 0) // Á¤¼ö°¡ ³¡³­ ÈÄ ÀÔ·ÂµÈ _¸¦ ¸ğµÎ ungetc½ÃÅ´
			for (int i=0; i<countUnderScore; i++)
				ungetc('_');

		
		ungetc(c);           // ¸¶Áö¸· ÀĞÀº ¹®ÀÚ´Â ´ÙÀ½ getcharPrivate()ÀÌ ÀĞµµ·Ï º¸°ü
		
// System.out.println("  <for debug in getLongStringWithoutSkip() > s = " + s);
		
		return s ;
	}
	
	// getLongWithoutSkip() : Ç¥ÁØÀÔ·Â¿¡¼­ÀÇ ½ºÅµ¾øÀÌ µğÁöÆ® ¹®ÀÚµé¸¦ ÀĞ¾î lopn Å¸ÀÔÀÇ °ªÀ¸·Î º¯È¯ÇÏ¿© ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	//     ¾ç¼ö»Ó ¾Æ´Ï¶ó À½¼öµµ Ã³¸®, µğÁöÆ®°¡ ¾Æ´Ñ ¹®ÀÚ´Â ¸ğµÎ ½ºÅµµÈ »óÅÂ¿¡¼­ È£ÃâµÊ
	//     ÀÌ ¸Ş¼Òµå°¡ È£ÃâµÇ±â Àü¿¡ ºÎÈ£°¡ Ã³¸®µÇ¾î sign º¯¼ö¿¡ ÀúÀåµÇ¾î ÀÖÀ¸¹Ç·Î signÀ» °öÇØÁÜ 
	private static  long getLongWithoutSkip() {
		return sign * Long.parseLong(getLongStringWithoutSkip());
	}
	// getintWithoutSkip() : Ç¥ÁØÀÔ·Â¿¡¼­ÀÇ ½ºÅµ¾øÀÌ Á¤¼ö¹®ÀÚµé¸¦ ÀĞ¾î int Å¸ÀÔÀÇ °ªÀ¸·Î º¯È¯ÇÏ¿© ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	//     ¾ç¼ö»Ó ¾Æ´Ï¶ó À½¼öµµ Ã³¸®, µğÁöÆ®°¡ ¾Æ´Ñ ¹®ÀÚ´Â ¸ğµÎ ½ºÅµµÈ »óÅÂ¿¡¼­ È£ÃâµÊ
	private static  int getIntWithoutSkip() {
		return (int) getLongWithoutSkip();
	}

	// getDouble() : 23.45, -12.3, -0.0123, +.01234, -.1234e33 1.234e+12, 1.234E-12 µî
	//  JavaÀÇ ½Ç¼ö °ªÀ» ÀÔ·ÂÇÏ¿© double °ªÀ¸·Î ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	//    ½Ç¼ö Çü½Ä: "[+/-Á¤¼öºÎ].[¼Ò¼öºÎ]E/e[+/-Áö¼öºÎ]" 
	//
	//    ½Ç¼ö ÀÔ·ÂÀ» À§ÇØ ¹İµå½Ã ¼ıÀÚ°¡ . Àü¿¡ ³ªÅ¸³ªµçÁö . ´ÙÀ½¿¡ ¼ıÀÚ°¡ ³ªÅ¸³ª¾ß ÇÔ
	//    ¿Ã¹Ù¸¥ ½Ç¼ö¸¦ ÀÔ·ÂÇÏ´Â °ÍÀº ´Ù¼Ò ¾î·Á¿ò
	//       ¿¹¸¦ µé¾î "...---.-12.345.012"ÀÌ ÀÔ·ÂµÇ¸é -12.345À¸·Î ÀÔ·ÂÇØ¾ßÇÏ¹Ç·Î
	//    Á¤¼öºÎ°¡ ¾øÀ¸¸é¼­ '.' ´ÙÀ½¿¡ ¼Ò¼öºÎ µğÁöÆ®°¡ ÀÔ·ÂµÇÁö ¾ÊÀ¸¸é ´Ù½Ã ½Ç¼ö ÀÔ·ÂÀÌ ½ÃÀÛµÇ¾î¾ß ÇÑ´Ù. 
	public static double getDouble() {
		long n = 0;          // Á¤¼öºÎ ÀúÀåÇÒ º¯¼ö 
		double d = 0;       // ¼Ò¼öºÎ ÀúÀåÇÒ º¯¼ö
		long exp = 0;        // Áö¼öºÎ ÀúÀåÇÒ º¯¼ö
		
		int signInt = 1;    // ºÎÈ£¸¦ ÀúÀåÇÒ º¯¼ö
		int c;

		c = skipUntilDigitOrDot();   // Ã¹ ¹øÂ° µğÁöÆ® ¶Ç´Â '.'¸¦ ¾ò´Â´Ù	
		signInt = sign;       // Á¤¼öºÎÀÇ ºÎÈ£¸¦ ÀúÀå

		ungetc(c);           // ¹æ±İ ÀĞÀº ¹®ÀÚ¸¦ ´Ù½Ã ÀĞ±â À§ÇØ ungetc()

//		System.out.println("  <for debug >c1 = " + c + " " + (char) c);
		
		boolean isThereIntPart = false;          // Á¤¼öºÎ Á¸Àç À¯¹«¸¦ ÀúÀå
		boolean isThereUnderPointtPart = false;  // ¼Ò¼öºÎ Á¸Àç À¯¹«¸¦ ÀúÀå
		
		if (c>='0' && c<='9') { // Á¤¼öºÎ°¡ Á¸ÀçÇÏ¸é
			n = getLongWithoutSkip();         // µğÁöÆ® ¾Æ´Ñ ¹®ÀÚ¿¡ ´ëÇÑ ½ºÅµ ¾øÀÌ Á¤¼öºÎ ÀÔ·Â
			isThereIntPart = true;
		}

		c = getcharPrivate();  // ¼Ò¼öºÎ¸¦ È®ÀÎÇÏ±â À§ÇØ ¹®ÀÚ¸¦ ÀĞÀ½
//	System.out.println("  <for debug > c2 = " + c + " " +(char) c);

		if (c=='.') {   // ´ÙÀ½¿¡  '.' ¹®ÀÚ°¡ ´ÙÀ½¿¡ ³ªÅ¸³ª¸é ¼Ò¼öºÎ°¡ ½ÃÀÛµÉ °¡´É¼ºÀÌ ÀÖÀ¸¹Ç·Î 
			int c2 = getcharPrivate();
			if (c2>='0' && c2 <='9') {      // '.' ¹®ÀÚ ´ÙÀ½ ¹®ÀÚ°¡ ¼ıÀÚ µğÁöÆ® ¹®ÀÚÀÌ¸é ¼Ò¼öºÎ°¡ Á¸ÀçÇÏ¹Ç·Î ÀÌ¸¦ Ã³¸®
//	System.out.println("  <for debug > c3 = "+ c + " " + (char) c);
			    ungetc(c2);                 // ¹æ±İ ÀĞÀº ¹®ÀÚ¸¦ ´Ù½Ã ÀĞ±â À§ÇØ ungetc()
				d = getDoubleUnderPoint();  // ¼Ò¼öºÎ¸¦ ÀÔ·Â
				
				isThereUnderPointtPart = true;
			}
			else {      // '.' ´ÙÀ½¿¡ ¼Ò¼öºÎ°¡ ³ªÅ¸³ªÁö ¾Ê´Â °æ¿ìÀÌ¹Ç·Î
				if (isThereIntPart) {  // Á¤¼öºÎ°¡ ÀÖÀ¸¸é ¼Ò¼öºÎ´Â 0ÀÓ
					d = 0;  // ¼Ò¼öºÎ´Â 0
					ungetc(c2);           // ÀĞÀº ¹®ÀÚ¸¦ ´Ù½Ã ÀĞ±â À§ÇØ ÀúÀå
				}
				else {  // Á¤¼öºÎµµ ¾ø°í ¼Ò¼öºÎµµ ¾øÀ¸¸é ½Ç¼ö°¡ ÀÔ·ÂÀÌ ¾ÈµÈ »óÅÂÀÌ¹Ç·Î '.'ÀÎ c´Â ¹«½ÃÇÏ°í ´Ù½Ã ½Ç¼ö ÀÔ·Â ½Ãµµ
					ungetc(c2);           // ÀĞÀº ¹®ÀÚ¸¦ ´Ù½Ã ÀĞ±â À§ÇØ ÀúÀå
					return getDouble();   // Áö±İ±îÁö´Â ÀÔ·ÂÀ» ¹«½ÃÇÏ°í »õ·Î¿î ½Ç¼ö¸¦ ÀÔ·ÂÇÏ¹Ç·Î Àç±Í È£ÃâÀÌ »ç¿ëµÊ
				}
			}		
		}
		else
			ungetc(c); // ¼Ò¼öºÎ°¡ ¾øÀ¸¸é ÀĞÀº ¹®ÀÚ¸¦ ungetc()
		
		c = getcharPrivate();  // Áö¼öºÎ¸¦ È®ÀÎÇÏ±â À§ÇØ ¹®ÀÚ¸¦ ÀĞÀ½
		if (c=='e' || c=='E') { // e ¶Ç´Â E·Î Áö¼öºÎ°¡ ½ÃÀÛµÇ¸é
			int c2 = getcharPrivate();
			
			if (c2 >= '0' && c2 <= '9') {  // ºÎÈ£°¡ ¾ø´Â Áö¼öºÎ°¡ ÀÖÀ¸¸é
				ungetc(c2); // ÀĞÀº µğÁöÆ® ¹®ÀÚ¸¦ ´Ù½Ã ÀĞ±â À§ÇØ ÀúÀå
				
				exp = getIntWithoutSkip();         // µğÁöÆ® ¾Æ´Ñ ¹®ÀÚ¿¡ ´ëÇÑ ½ºÅµ ¾øÀÌ Á¤¼öºÎ ÀÔ·ÂÇÏ¿© Áö¼öºÎ °ªÀ¸·Î ÀúÀå				
			}
			else { // ºÎÈ£ ÀÖ´Â Áö¼öºÎ°¡ ÀÖ´Â°¡¸¦ Ã¼Å©ÇÏ¿© Ã³¸®ÇÔ
				if (c2 == '+' || c2 == '-') {
					int c3 = getcharPrivate();
					if (c3 >= '0' && c3 <= '9') {  // +/- ´ÙÀ½¿¡ Áö¼öºÎÀÎ Á¤¼ö°¡ ÀÖÀ¸¸é
						ungetc(c3); // ÀĞÀº ¹®ÀÚ¸¦ ´Ù½Ã ÀĞ±â À§ÇØ ÀúÀå

						exp = getIntWithoutSkip(); // µğÁöÆ® ¾Æ´Ñ ¹®ÀÚ¿¡ ´ëÇÑ ½ºÅµ ¾øÀÌ Á¤¼öºÎ ÀÔ·ÂÇÏ¿© Áö¼öºÎ °ªÀ¸·Î ÀúÀå			
						exp = exp * ( (c2 == '+') ? 1 : -1 ) ;   // +Áö¼öºÎ´Â 1, -Áö¼öºÎ´Â -1À» °öÇÔ	
						
//	System.out.println("\n  <for debug > c2 = " + c2 + " " + (char) c2 + ", exp = " + exp);
					}
					else {  // e·Î ½ÃÀÛÇÏ°í +/-°¡ ÀÔ·ÂµÇ¾úÀ¸³ª  +/- ´ÙÀ½¿¡ ¼ıÀÚ ¾Æ´Ï¸é Áö¼öºÎ°¡ ¾ø´Â °ÍÀ¸·Î Ã³¸® 
						ungetc(c3);
						ungetc(c2);
						ungetc(c);
					}
				}
				else { // e·Î ½ÃÀÛÇßÀ¸³ª ¼ıÀÚ, + , -°¡ ¾Æ´Ï¸é Áö¼öºÎ°¡ ¾ø´Â °ÍÀ¸·Î Ã³¸®
					ungetc(c2);
					ungetc(c);				
				}
			}
	    }
		else
			ungetc(c); // Áö¼öºÎ°¡ ¾øÀ¸¸é ÀĞÀº ¹®ÀÚ¸¦ ungetc() 

		if (signInt == 1) // ÀĞÀº Á¤¼öºÎÀÇ ºÎÈ£¿¡ µû¶ó
			d = n + d;	    // ºÎÈ£°¡ ¾ç¼öÀÌ¸é ¼Ò¼öºÎ¸¦ Á¤¼öºÎ¿¡ ´Ü¼øÈ÷ ´õÇØÁÜ
		else 
			d = n - d;     // ºÎÈ£°¡ À½¼öÀÌ¸é ¼Ò¼öºÎ¸¦ Á¤¼öºÎ¿¡¼­ »©ÁÖ¾î¾ß ÇÔ

		if (exp != 0)      //  Áö¼öºÎ°¡ ÀÖÀ¸¸é Áö¼öºÎ¸¸Å­ °öÇØÁÜ
			d = d * powor10(exp);
		
		removeLastNewLineChar();  // Á¤¼ö, ½Ç¼ö, ¹®ÀÚ¿­, ½Äº°ÀÚ µîÀ» ÀĞ±â À§ÇØ ÀÔ·ÂµÈ '\r', '\n'À» Á¦°Å½ÃÅ´
		
		return d;
	}

	// getFoloat() : 23.45, -12.3, -0.012_3, +.012_34, -.1234e3_3 1.234e+12, 1.234E-1_2 µî
	//    JavaÀÇ ½Ç¼ö °ªÀ» ÀÔ·ÂÇÏ¿© float °ªÀ¸·Î ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	//    o ½Ç¼ö Çü½Ä: "[+/-Á¤¼öºÎ].[¼Ò¼öºÎ]E/e[+/-Áö¼öºÎ]" 
	public static float getFloat() {
		return (float) getDouble();
	}

	// ¼Ò¼öÁ¡ ÀÌÇÏÀÇ ½ÇºÎºÎ¸¦ ÀĞ¾îµéÀÓ
	private static double getDoubleUnderPoint() {
		String longString = getLongStringWithoutSkip();  // ¼Ò¼öÁ¡ ÀÌÇÏÀÇ Á¤¼ö¸¦ ÀĞ¾î ¹®ÀÚ¿­·Î ¹İÈ¯
		
		long  nUnerPoint  = Long.parseLong(longString);  // ¼Ò¼öÁ¡ ÀÌÇÏÀÇ long °ªÀ» ±¸ÇÔ
		int noDigit = longString.length();   // ¼Ò¼öÁ¡ ÀÌÇÏÀÇ Á¤¼öºÎ ÀÚ¸®¼ö ±¸ÇÔ
		
// System.out.println("  <for debug in getDoubleUnderPoint() > s="+longString+ "  noDigit="+noDigit);  

		return  (double) nUnerPoint / powor10(noDigit);  // '.' ÀÌÇÏ Á¤¼öºÎ¸¦ 10^ÀÚ¸®¼ö·Î ³ª´©¾î ¼Ò¼öºÎ·Î ¹İÈ¯
	}

	// ÁÖ¾îÁø Á¤¼öÀÇ ÀÚ¸®¼ö¸¦ ±¸ÇÔÇÏ¿© ¹İÈ¯
	public static int getNoDigit(int n) {
		int no = 0;

		while (n != 0) {
			no++;
			n = n / 10;
		}

		return no;
	}

	// 10ÀÇ n Áö¼ö°ªÀ» ¹İÈ¯
	public static double powor10(long n) {
		double pow = 1;

		if (n > 0) {
			while(n-- > 0) 
				pow = pow * 10;
		}
		else if (n < 0){
			while(n++ < 0) 
				pow = pow / 10;
		}

		//	System.out.println("\n  <for debug >n = " + n + ", exp = " + pow);
		
		return pow;
	}

    // nextInt(): JavaÀÇ Scanner Å¬·¡½º¿¡ ÀÖ´Â ¸Ş¼Òµå¿Í °°À½
	public static int nextInt() {
		return getInt();
	}

	// getBoolean() : "true" ¶Ç´Â "false"¸¦ ÀÔ·ÂÇÏ¿© true ¶Ç´Â false¸¦ boolean °ªÀ¸·Î ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	//    "true" ¶Ç´Â "false"¸¦ ÀÔ·ÂÇÏ±â À§ÇØ ½Äº°ÀÚ°¡ ¹İµå½Ã ¸ÕÀú ³ªÅ¸³ª¾ß ÇÔ
	//    ÀÔ·ÂµÈ ½Äº°ÀÚ°¡ "true" ¶Ç´Â "false"ÀÌ¸é boolean Å¸ÀÔÀÇ true ¶Ç´Â false ¸¦ ¹İÈ¯,
	//    ±×·¸Áö ¾ÊÀº °æ¿ì ´ÙÀ½ ½Äº°ÀÚ¸¦ ÀÔ·ÂÇÏ¿© È®ÀÎÀ» ¹İº¹ÇÔ
	public static boolean getBoolean()  {
		while (true) {
			String id = getIdentifier();
			
//			System.out.println("  o identifier = '" + id + "'"); 
			
			if (id.equals("true"))
				return true;
			else if (id.equals("false"))
				return false;			
		}
	}

	// getIdentifier() : JavaÀÇ ½Äº°ÀÚ¸¦ ÀÔ·ÂÇÏ¿© ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	//  ½Äº°ÀÚÀÇ Ã¹¹®ÀÚ: ¿µ¹®ÀÚ, ÇÑ±Û, '_', '$'
	//  ½Äº°ÀÚÀÇ ²¿¸®ºÎºĞ: ¿µ¹®ÀÚ, ÇÑ±Û, ¼ıÀÚ, '_', '$'
	//  (±×¸®½º ¹®ÀÚ, Áß±¹¾î ¹®ÀÚ, ÀÏº»¾î ¹®ÀÚ µîµµ ½Äº°ÀÚ¿¡ Æ÷ÇÔµÇ³ª ¿©±â¿¡¼­´Â Ã³¸®ÇÏÁö ¾ÊÀ½)
	public static String getIdentifier()  {
		int head = skipUntilFistCharOfIentifier(); // ½Äº°ÀÚÀÇ Ã¹ ¹®ÀÚ¸¦ Ã£¾Æ ÀÔ·ÂÇÏ¿© ¹İÈ¯
//		System.out.println("  o head of identifier = '" + (char) head + "' (" + head + ")"); 
		
		String identifier = getTailOfID(head); // ½Äº°ÀÚÀÇ ²¿¸®ºÎºĞÀ» ÀÔ·ÂÇÏ¿© Ã¹ ¹®ÀÚ head¿Í °áÇÕÇÏ¿© ¹İÈ¯
		
		removeLastNewLineChar();  // Á¤¼ö, ½Ç¼ö, ¹®ÀÚ¿­, ½Äº°ÀÚ µîÀ» ÀĞ±â À§ÇØ ÀÔ·ÂµÈ '\r', '\n'À» Á¦°Å½ÃÅ´

		return identifier;		
	}
	
	// skipUntilFistCharOfIentifier() : ½Äº°ÀÚÀÇ Ã¹¹®ÀÚÀÎ ¿µ¹®ÀÚ, ÇÑ±Û, '_', '$'°¡ ³ª¿Ã ¶§±îÁö 
	//   ¹®ÀÚµéÀ» skipÇÏ¿© ½Äº°ÀÚÀÇ Ã¹ ¹®ÀÚ¸¦ ¹İÈ¯ÇÏ´Â ¸Ş¼Òµå
	public  static int skipUntilFistCharOfIentifier() {
		int c;

		while(true)  {
			c = getcharPrivate();
			
			if (isAlphabet(c))      // ÀĞÀº ¹®ÀÚ°¡ ¾ËÆÄº£Æ®ÀÌ¸é ÀÌ´Â ½Äº°ÀÚ Ã¹¹®ÀÚ¿¡ ÇØ´ç, ÀÌ¸¦ ¹İÈ¯
				return c;
			else if (c == '_' || c=='$')    // ÀĞÀº ¹®ÀÚ°¡ '_' ¶Ç´Â "$"ÀÌ¸é ÀÌ´Â ½Äº°ÀÚ Ã¹¹®ÀÚ¿¡ ÇØ´ç, ÀÌ¸¦ ¹İÈ¯
				return c;    	
		}
	}

	// isAlphabet() : ÁÖ¾îÁø ¹®ÀÚ°¡ JavaÀÇ ½Äº°ÀÚÀÇ ¾ËÆÄº£Æ®¿¡ ÇØ´çµÇ´ÂÁö¸¦ °Ë»çÇÏ´Â  ¸Ş¼Òµå
	//  ¾ËÆÄº£Æ®: ¿µ¹®ÀÚ, ÇÑ±Û, '_', '$'
	//  (±×¸®½º ¹®ÀÚ, Áß±¹¾î ¹®ÀÚ, ÀÏº»¾î ¹®ÀÚ µîµµ ¾ËÆÄº£Æ®¿¡ Æ÷ÇÔµÇ³ª ¿©±â¿¡¼­´Â Ã³¸®ÇÏÁö ¾ÊÀ½)
   public static boolean isAlphabet(int c) {
		if (c>='A' && c <= 'Z' || c>='a' && c <= 'z' )  // ÀĞÀº ¹®ÀÚ°¡ ¿µ¹®ÀÚÀÌ¸é ÀÌ¸¦ ¹İÈ¯
			return true;
		// À¯´ÏÄÚµå¿¡¼­ ÇÑ±Û ÀÚÀ½°ú ¸ğÀ½Àº '¤¡'(12593)¿¡¼­ '¤Ó'(12643)±îÁö ¹èÁ¤µÊ
		// À¯´ÏÄÚµå¿¡¼­ ÇÑ±Û ¹®ÀÚ´Â '°¡'('\uAC00': 44032)¿¡¼­ 'ÆR'('\uD7A3': 55203)±îÁö ¹èÁ¤µÊ
		else if (c >= 12593 && c <= 12643)   // ÀĞÀº ¹®ÀÚ°¡ ÇÑ±Û ÀÚÀ½ ¶Ç´Â ¸ğÀ½ÀÌ¸é ÀÌ´Â ½Äº°ÀÚ Ã¹¹®ÀÚ¿¡ ÇØ´çµÊ
			return true;
		else if (c >= 44032 && c <= 55203)   // ÀĞÀº ¹®ÀÚ°¡ ÇÑ±Û À½ÀıÀÌ¸é ÀÌ´Â ½Äº°ÀÚ Ã¹¹®ÀÚ¿¡ ÇØ´çµÊ
			return true;

		return false;
    	
    }
    
	// getTailOfID(int head): Java ½Äº°ÀÚÀÇ ²¿¸®ºÎºĞÀ» ÀÔ·ÂÇÏ¿© ÁÖ¾îÁø Çìµå¹®ÀÚ head¿Í ÇÔ²² ÀüÃ¼ ½Äº°ÀÚ¸¦ ±¸¼ºÇÏ¿© ¹®ÀÚ¿­·Î ¹İÈ¯
	//  ½Äº°ÀÚÀÇ Ã¹¹®ÀÚ: ¿µ¹®ÀÚ, ÇÑ±Û, '_', '$'
	//  ½Äº°ÀÚÀÇ ²¿¸®ºÎºĞ: ¿µ¹®ÀÚ, ÇÑ±Û, ¼ıÀÚ, '_', '$'
	public  static String getTailOfID(int head) {
		int i=0;

		int c;

		char cs[] = new char[MAX_LENGTH_OF_STRING];
 
		cs[0] = (char) head;
		i++;
		
		while(true)  {
			c = getcharPrivate();
//			System.out.println("  o tail of identifier = '" + (char) c + "' (" + c + ")"); 
			
			if (isAlphabet(c))              // ÀĞÀº ¹®ÀÚ°¡ ¾ËÆÄº£Æ®ÀÌ¸é ÀÌ´Â ½Äº°ÀÚ ²¿¸®¿¡ ÇØ´çµÊ
				cs[i++] = (char) c; 
			else if (c == '_' || c=='$')    // ÀĞÀº ¹®ÀÚ°¡ '_' ¶Ç´Â "$"ÀÌ¸é ÀÌ´Â ½Äº°ÀÚ ²¿¸®¿¡ ÇØ´çµÊ
				cs[i++] = (char) c;   	
			else if (c >= '0' && c<='9')         // ÀĞÀº ¹®ÀÚ°¡ µğÁöÆ®ÀÌ¸é ÀÌ´Â ½Äº°ÀÚ ²¿¸®¿¡ ÇØ´çµÊ
				cs[i++] = (char) c;    	
			else
				break;
		}
		
		ungetc(c); // ¸¶Áö¸· ÀĞÀº ¹®ÀÚ´Â ½Äº°ÀÚ ¹®ÀÚ ¾Æ´Ï¹Ç·Î ´ÙÀ½¿¡ ÀĞÀ» ¼ö ÀÖµµ·Ï ungetc½ÃÅ´

		cs[i] = 0;

		String s =  new String(cs, 0, i);  // ¹®ÀÚ ¹è¿­À» ¹®ÀÚ¿­·Î º¯È¯ 

		return s;	

	}  
	

	// getString() : '\n', '\r', -1ÀÌ ÀÔ·ÂµÉ ¶§±îÁö ÀÔ·ÂµÈ ¹®ÀÚµéÀ» String °ªÀ¸·Î ¹İÈ¯
	//    ¿£ÅÍÅ°°¡ '\n', '\r' µÎ°³ÀÇ ¹®ÀÚ·Î ÀÌ·ç¾îÁö´Â °æ¿ì¸¦ À§ÇØ 
	//    Ã³À½¿¡ ³ªÅ¸³ª´Â '\n', '\r' ¹®ÀÚ´Â ¹«½ÃÇÑ´Ù.  
	public  static String getString()  {
		int i=0;

		int c;

		char cs[] = new char[MAX_LENGTH_OF_STRING];

		while((c = getcharPrivate()) != '\n' &&  c != '\r' && c != -1) 
			cs[i++] = (char) c;
		ungetc(c);
		
		cs[i] = 0;

		String s =  new String(cs, 0, i);
		
		removeLastNewLineChar();  // Á¤¼ö, ½Ç¼ö, ¹®ÀÚ¿­, ½Äº°ÀÚ µîÀ» ÀĞ±â À§ÇØ ÀÔ·ÂµÈ '\r', '\n'À» Á¦°Å½ÃÅ´

		return s;	 
	}
	
	// µğÁöÆ® ¹®ÀÚ°¡ ÀÔ·ÂµÉ ¶§±îÁöÀÇ ¹®ÀÚµéÀ» ÀĞ¾î String ¹®ÀÚ¿­À» ¹İÈ¯
	public  static String getStringUntilNotDigit()  {
		int i=0;

		int c;

		char cs[] = new char[MAX_LENGTH_OF_STRING];


		while((c =  getcharPrivate()) >= '0' &&  c <= '9' ) 
			cs[i++] = (char) c;
		
		ungetc(c);  // ¸¶Áö¸· ÀĞÀº ¹®ÀÚ¸¦ ´Ù½Ã ÀĞÀ» ¼ö ÀÖµµ·Ï ungetc()

		cs[i] = 0;

		String s =  new String(cs, 0, i);  // ¹®ÀÚ¹è¿­À» String °´Ã¼·Î º¯È¯

		return s;	 
	}

	// nextString(): JavaÀÇ Scanner Å¬·¡½º¿¡ ÀÖ´Â ¸Ş¼Òµå¿Í °°À½
	public static String nextString() {
		return getString();
	} 


	public static String toKSC5601(String str) // throws java.io.UnsupportedEncodingException 
	{ 

		if (str == null) return null; 

		try {
			return new String(str.getBytes("8859_1"), "KSC5601"); 
		}
		catch(Exception e) {}

		return null;

	} 

	/*
	 * ÇÁ·ÒÇÁÆ® ¸Ş½ÃÁö¸¦ Ãâ·ÂÇÑ µÚ µ¥ÀÌÅÍ ÀÔ·ÂÇÏ´Â ¸Ş¼Òµåµé
	 * 
	 * ÀÔ·ÂÇÒ ¶§ ÇÁ·ÒÇÁÆ® ¸Ş½ÃÁö ¾øÀÌ ÀÔ·ÂÀ» ÇÏ¸é »ç¿ëÀÚ´Â ¸·¿¬È÷ ±â´Ù¸®´Â °æ¿ì°¡ ¹ß»ıÇÑ´Ù.
	 * ÀÌ·¯ÇÑ °æ¿ì¸¦ ¹æÁöÇÏ±â À§ÇØ ÀÔ·Â½Ã¿¡´Â ¸Ş½ÃÁö¸¦ ¹Ì¸® Ãâ·ÂÇÏ¿© 
	 * ÀÔ·ÂÇÒ µ¥ÀÌÅÍÀÇ ¿ëµµ³ª ±â´ÉÀ» ¾Ë·ÁÁÖ´Â ¸Ş½ÃÁö¸¦ ÇÁ·ÒÇÁÆ® ¸Ş½ÃÁö¶ó ÇÑ´Ù.
	 * 
	 * Æ¯È÷ Å°º¸µå¿¡¼­ÀÇ ÀÔ·Â½Ã ÇÁ·ÒÇÁÆ® ¸Ş½ÃÁö´Â Áß¿äÇÏ´Ù.  
	 */
	
	// ÇÁ·ÒÇÁÆ® ¸Ş½ÃÁö¿Í ¹®ÀÚ ÀÔ·Â
	public static char getCharWithPrompt(String msg) {
		System.out.print(msg);
        return getChar();
	}

	public static char getChar(String prompt) {	
		return getCharWithPrompt(prompt);
	}
	
	// ÇÁ·ÒÇÁÆ® ¸Ş½ÃÁö¿Í int Á¤¼ö ÀÔ·Â
	public static int getIntWithPrompt(String msg) {
		System.out.print(msg);
		return getInt();
	}
	
	public static int getInt(String prompt) {
		return getIntWithPrompt(prompt);
	}

	// ÇÁ·ÒÇÁÆ® ¸Ş½ÃÁö¿Í long Á¤¼ö ÀÔ·Â
	public static long getLongWithPrompt(String msg) {
		System.out.print(msg);
		return getLong();
	}
	
	public static long getLong(String prompt) {
		return getLongWithPrompt(prompt);
	}

	// ÇÁ·ÒÇÁÆ® ¸Ş½ÃÁö¿Í double ½Ç¼ö ÀÔ·Â
	public static double getDoubleWithPrompt(String msg) {
		System.out.print(msg);
		return getDouble();
	}

	public static double getDouble(String prompt) {		
		return getDoubleWithPrompt(prompt);
	}

	
	// ÇÁ·ÒÇÁÆ® ¸Ş½ÃÁö¿Í float ½Ç¼ö ÀÔ·Â
	public static float getFloatWithPrompt(String msg) {
		System.out.print(msg);
		return getFloat();
	}
	
	public static float getFloat(String prompt) {		
		return getFloatWithPrompt(prompt);
	}
	
	// ÇÁ·ÒÇÁÆ® ¸Ş½ÃÁö¿Í boolean °ª ÀÔ·Â
	public static boolean getBooleanWithPrompt(String msg) {
		System.out.print(msg);
		return getBoolean();
	}
	
	public static boolean getBoolean(String prompt) {		
		return getBooleanWithPrompt(prompt);
	}
	
	// ÇÁ·ÒÇÁÆ® ¸Ş½ÃÁö¿Í ¹®ÀÚ¿­ ÀÔ·Â
	public static String getStringWithPrompt(String msg) {
		System.out.print(msg);
		return getString();
	}
	
	public static String getString(String prompt) {
		return getStringWithPrompt(prompt);
	}
	
	// ÇÁ·ÒÇÁÆ® ¸Ş½ÃÁö¿Í ½Äº°ÀÚ ÀÔ·Â
	public static String getIdentifierWithPrompt(String msg) {
		System.out.print(msg);
		return getIdentifier();
	}
	
	public static String getIdentifier(String prompt) {
		return getIdentifierWithPrompt(prompt);
	}
	
	

	public static void main(String args[]) {	
		while(true) {

			/*
			 *  ÀÌ ºÎºĞÀº Å×½ºÆ®¸¦ À§ÇÑ ºÎºĞÀÓ
			 *  
			 *  ÀÔ·Â ¸Ş¼Òµå¸¦ ÇÑ¹ø¾¿ È£ÃâÇÔ
			 */
			
//
//			System.out.print("\n  * input int (ÃÖ´ë°ª: " + (0x7FFF_FFFF) +") > ");
//			int no = SkScanner.getInt(); 
//			System.out.println("  o int = " + no);
//
//			System.out.print("\n  * input long(ÃÖ´ë°ª: " + (0x7FFF_FFFF_FFFF_FFFFL) +") > ");
//			long l = SkScanner.getLong(); 
//			System.out.println("  o long = " + l);
//
			System.out.print("\n  * input double > ");
			double  d = SkScanner.getDouble();
			System.out.println("  o double = " + d);
			System.out.print("\n  * input char > ");
			char c = SkScanner.getChar(); 
			System.out.println("  o char = "+ c +" (" + (int) c + ")");

			System.out.print("\n  * input String > ");
			String s = SkScanner.getString();
			System.out.println("  o String = '" + s + "'");  
	
			System.out.print("\n  * input identifier > ");
			String id = SkScanner.getIdentifier();
			System.out.println("  o identifier = " + id);  

			System.out.print("\n  * input boolean (true or false) > ");
			boolean b = SkScanner.getBoolean();
			System.out.println("  o boolean = " + b);  

			/*
			 * ÇÁ·ÒÇÁÆ® ¸Ş½ÃÁö¸¦ Ãâ·ÂÇÏ´Â ÀÔ·Â ¸Ş¼Òµå È£Ãâ
			 */
			c = SkScanner.getCharWithPrompt("\n  * input char > "); 
			System.out.println("  o char = "+ c +" (" + (int) c + ")");

			int no = SkScanner.getIntWithPrompt("\n  * input int > "); 
			System.out.println("  o int = " + no);

			d = SkScanner.getDoubleWithPrompt("\n  * input double > ");
			System.out.println("  o double = " + d);

			s = SkScanner.getString("\n  * input String > ");
			System.out.println("  o String = '" + s + "'");  
	
			id = SkScanner.getIdentifier("\n  * input identifier > ");
			System.out.println("  o identifier = " + id);  

			b = SkScanner.getBoolean("\n  * input boolean (true or false) > ");
			System.out.println("  o boolean = " + b);  
			
			char c1=0;
			System.out.println("  * Hit any char and ehit Enter key!!" );  
			while(c1 != '\n') {
				c1=getchar();

				System.out.print((int)c1 + " ");
			}
			
			System.out.print("End");
		}
	}
}

