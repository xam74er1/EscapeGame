package Utils;

public class StringUtility {
	
	public static String sec2Time(int sec) {
		int h = sec/3600;
		
		sec = sec%3600;
		
		int min = sec/60;
		
		int s = sec%60;
		
		//return h+"h:"+min+"m:"+s+"s";	
		
		return String.format("%02d", h)+":"+String.format("%02d", min)+":"+String.format("%02d", s);
		}
	
	public static String sec2TimeUnite(int sec) {
		int h = sec/3600;
		
		sec = sec%3600;
		
		int min = sec/60;
		
		int s = sec%60;
		
		//return h+"h:"+min+"m:"+s+"s";	
		
		return String.format("%02d", h)+"H:"+String.format("%02d", min)+"m:"+String.format("%02d", s)+"s";
		}
	
	
	public static boolean stringIsNumber(String str) {
		return str.matches("-?\\d+");
	}

}
