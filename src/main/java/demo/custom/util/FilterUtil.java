package demo.custom.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class FilterUtil {
	/*CamelCase Convert*/

	public static String haeConvert2Camel(String str)
	{
		if ((str.indexOf('_') < 0) && (Character.isLowerCase(str.charAt(0)))) 
		{
			return str;
		}
	 
		StringBuilder result = new StringBuilder();
 	 
		boolean nextUpper = false;
 	 
		int len = str.length();	  
 
		for (int i = 0; i < len; i++) 
		{	 
			char currentChar = str.charAt(i);
 		 
			if (currentChar == '_') 
			{
				nextUpper = true;
			}
			else if (nextUpper) 
			{
				result.append(Character.toUpperCase(currentChar));
				nextUpper = false;
			} else 
			{
				result.append(Character.toLowerCase(currentChar));
			}
		}	  
		
		return result.toString();
	 }
	 
	/*교차접속 스크립트 공격 취약성 방지(파라미터 문자열 교체)*/

	public static String clearXSSMinimum(String value)
	{
		if (value == null || value.trim().equals("")) 
		{
			return "";
		}
		
		String returnValue = value;

		//returnValue = returnValue.replaceAll("&", "&amp;");
		returnValue = returnValue.replaceAll("<", "&lt;");
		returnValue = returnValue.replaceAll(">", "&gt;");
		returnValue = returnValue.replaceAll("\"", "&#34;");
		returnValue = returnValue.replaceAll("\'", "&#39;");
		
		return returnValue;
	}
	
	public static String clearXSSMaximum(String value)
	{
		String returnValue = value;
		
		returnValue = clearXSSMinimum(returnValue);

		returnValue = returnValue.replaceAll("%00", null);
		returnValue = returnValue.replaceAll("%", "&#37;");		// \\. => .
		returnValue = returnValue.replaceAll("\\.\\./", ""); 	// ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\
		returnValue = returnValue.replaceAll("\\./", ""); 		// ./
		returnValue = returnValue.replaceAll("%2F", "");

		return returnValue;
	}

	public static String filePathBlackList(String value) 
	{
		String returnValue = value;
		
		if (returnValue == null || returnValue.trim().equals("")) 
		{
			return "";
		}

		returnValue = returnValue.replaceAll("\\.\\./", ""); 	// ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\

		return returnValue;
	}

	public static String filterXSS(String value) {
		if (value != null && !"".equals(value)) {
			Pattern scriptPattern  = null;

			String[] checkStr_arr = {
										"<script>(.*?)</script>","<script(.*?)>","</script>","<javascript>(.*?)</javascript>","<javascript(.*?)>","</javascript>",
										"<iframe>(.*?)</iframe>","<iframe(.*?)>","</iframe>","<vbscript>(.*?)</vbscript>","<vbscript(.*?)>","</vbscript>",
										"<object>(.*?)</object>","<object(.*?)>","</object>","<marquee>(.*?)</marquee>","<marquee(.*?)>","</marquee>",
										
										"onload(.*?)=","onerror(.*?)=","onclick(.*?)=","onmouseover(.*?)=","onstart(.*?)=",

										"eval\\((.*?)\\)","expression\\((.*?)\\)",
										
										"javascript:","vbscript:"

										//"<img>(.*?)</img>","<img(.*?)>","</img>",
		
									};
			for(String checkStr : checkStr_arr){
				scriptPattern = Pattern.compile(checkStr, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

				while(scriptPattern.matcher(value).find()){
					value = scriptPattern.matcher(value).replaceAll("");
				}
			}
		}
		return value;
	}
	
	public static String filterSqlInjection(String value) {
		if (value != null && !"".equals(value)) {
			//Pattern scriptPattern = Pattern.compile("['\"\\-#()@;=*/+]");
			//value = scriptPattern.matcher(value).replaceAll("");
			//Pattern scriptPattern = Pattern.compile("(drop|delete|update|select|insert|create|from|where|and|or|union)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			Pattern scriptPattern = Pattern.compile("(drop|delete|update|select|insert|create|from|where|and|union)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			value = scriptPattern.matcher(value).replaceAll("[" + value + "]");
		} else {
			value = "";
		}
		return value;
	}
	
	/**
	 * 행안부 보안취약점 점검 조치 방안.
	 *
	 * @param value
	 * @return
	 */
	public static String filePathReplaceAll(String value) 
	{
		String returnValue = value;
		
		if (returnValue == null || returnValue.trim().equals("")) 
		{
			return "";
		}

		returnValue = returnValue.replaceAll("/", "");
		returnValue = returnValue.replaceAll("\\", "");
		returnValue = returnValue.replaceAll("\\.\\.", ""); 	// ..
		returnValue = returnValue.replaceAll("&", "");

		return returnValue;
	}

	public static String filePathWhiteList(String value) 
	{
		return value; 
	}
	
	public static boolean isIPAddress(String str) 
	{
		Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
		
		return ipPattern.matcher(str).matches();
    }
	 
	public static String removeCRLF(String parameter)
	{
		return parameter.replaceAll("\r", "").replaceAll("\n", "").replaceAll("multipart/related", "").replaceAll("ContentType-Type", "");
	}
	 
	public static String removeSQLInjectionRisk(String parameter) 
	{
		return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("%", "").replaceAll(";", "").replaceAll("-", "").replaceAll("\\+", "").replaceAll(",", "");
	}
	 
	public static String removeOSCmdRisk(String parameter) 
	{
		return parameter.replaceAll("\\p{Space}", "").replaceAll("\\*", "").replaceAll("|", "").replaceAll(";", "");
	}
	 
	/*
	 * HTML 태그를 제거합니다.
	 */
	public static String removeHTMLTag(String str)
	{
		return str.replaceAll("\\<.*?\\>", "");
	}
	
	/*
	 * STYLE 태그를 제거합니다.
	 */
	public static String removeSTYLETag(String str)
	{
		Matcher m;
		
		Pattern style = Pattern.compile("<style[^>]*>.*</style>", Pattern.DOTALL);
		
		m = style.matcher(str);
		str = m.replaceAll("");
		
		return str;
	}

}