package org.ocmc.ioc.liturgical.schemas.constants;

public enum YEAR_MODIFIERS {
	BC("BC")
	, AD("AD")
	, unknown("unknown")
	;

	public String keyname = "";
	
	private YEAR_MODIFIERS(
			String keyname
			) {
		this.keyname = keyname;
	}
		
}
