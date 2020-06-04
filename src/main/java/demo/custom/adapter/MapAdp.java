package demo.custom.adapter;

import demo.custom.map.CstMap;

import lombok.Data;


@Data
public class MapAdp {
    private CstMap cstMap;

    public MapAdp(){
        
    }

	public MapAdp(CstMap cstMap) {
        this.setCstMap(cstMap);
        this.getCstMap();
	}
}