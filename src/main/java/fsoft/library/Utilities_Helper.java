package fsoft.library;

import net.htmlparser.jericho.CharacterReference;


public class Utilities_Helper {
	public static boolean checkPass(String pass1, String pass2) {
		boolean flag  = false;
		
		if(pass1!= null) {
			if(pass1.length()>5) {
				if(pass1.equals(pass2)) {
					flag = true;
					
				}
			}
		}
			
		return flag;
	}
	
	public static String encode(String unicode) {
		return CharacterReference.encode(unicode);
	}
	
	public static String decode(String html) {
		return CharacterReference.decode(html);
	}
}
