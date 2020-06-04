package demo.custom.util;

import demo.custom.map.CstMap;

public class PaginationUtil {

	private static int pageSize = 10; // 페이지 그룹 범위 1 2 3 5 6 7 8 9 10
	private static int cntPage = 10; //페이지당 데이터 개수
	
	/**
	 * 페이지 정보를 가져온다.
	 */
	public static CstMap getPage(CstMap cstMap) throws Exception
	{
		if ("".equals(cstMap.getString("pageIndex")))
		{
			cstMap.put("pageIndex", 1);
		}
    	
    	//1페이지에 가져올 리스트 갯수
    	if("".equals(cstMap.getString("cntPage")))
		{
			cstMap.put("cntPage", cntPage);
		}
		
    	
    	//1페이지에 가져올 페이지 갯수
    	if("".equals(cstMap.getString("pageSize")))
    	{
    		cstMap.put("pageSize", pageSize);
		}
    	
		cstMap.put("firstIndex", (Integer.parseInt(cstMap.getString("pageIndex")) - 1) * Integer.parseInt(cstMap.getString("cntPage")));
		
		CstMap pageMap  = new CstMap();
		pageMap.putAll(cstMap);
		
		return pageMap;
	}
}