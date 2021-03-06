package org.ocmc.ioc.liturgical.schemas.constants.nlp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.ocmc.ioc.liturgical.utils.GeneralUtils;

/**
 * Maps Perseus Grammatical abbreviations to Leipzig
 * 
 * <a href="https://www.eva.mpg.de/lingua/resources/glossing-rules.php">See glossing rules</a>
 * @author mac002
 *
 */
public class GRAMMAR_ABBREVIATIONS_MAPPER {
    private static Map<String, GRAMMAR_ABBREVIATIONS> map = new TreeMap<String,GRAMMAR_ABBREVIATIONS>();
    static {
    	map.put("TBD",GRAMMAR_ABBREVIATIONS.TBD);
    	map.put("1st", GRAMMAR_ABBREVIATIONS.P1);
    	map.put("2nd", GRAMMAR_ABBREVIATIONS.P2);
    	map.put("3rd", GRAMMAR_ABBREVIATIONS.P3);
    	map.put("aeolic", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("attic", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("contr", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("doric", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("dual", GRAMMAR_ABBREVIATIONS.DU);
    	map.put("enclitic", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("epic", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("exclam", GRAMMAR_ABBREVIATIONS.EXCLAMATION);
    	map.put("fem", GRAMMAR_ABBREVIATIONS.FEMININE);
    	map.put("geog_name", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("homeric", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("imperat", GRAMMAR_ABBREVIATIONS.IMP);
    	map.put("imperf", GRAMMAR_ABBREVIATIONS.IMPRF);
    	map.put("indeclform", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("ionic", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("irreg_superl", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("masc", GRAMMAR_ABBREVIATIONS.MASCULINE);
    	map.put("mp", GRAMMAR_ABBREVIATIONS.PASS);
    	map.put("neut", GRAMMAR_ABBREVIATIONS.NEUTER);
    	map.put("nu_movable", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("partic", GRAMMAR_ABBREVIATIONS.PART);
    	map.put("poetic", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("proclitic", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("pres_redupl", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("prs", GRAMMAR_ABBREVIATIONS.PRS);
    	map.put("redupl", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("short_subj", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("syll_augment", GRAMMAR_ABBREVIATIONS.EMPTY);
    	map.put("unaugmented", GRAMMAR_ABBREVIATIONS.EMPTY);
	}


    /**
     * Get the abbreviation label
     * @param s value to look for
     * @return enum for that value
     */
    public static GRAMMAR_ABBREVIATIONS getLabel(String s) {
    	String n = GeneralUtils.normalize(s);
    	if (map.containsKey(n)) {
    		return map.get(n);
    	} else {
    		return GRAMMAR_ABBREVIATIONS.TBD;
    	}
    }
    
    public static String getLabelValue(String s) {
    	if (map.containsKey(s)) {
    		return map.get(s).keyname.trim();
    	} else {
    		return s.toUpperCase();
    	}
    }

    /**
     * Converts a string that contains Perseus grammatical abbreviations into Leipzig ones
     * @param splitter string to use for the split
     * @param parse the parse
     * @param delimiter the delimiter
     * @return Leipzig abbreviations for that parse
     */
    public static String convertToLeipzig(String splitter, String parse, String delimiter) {
    	List<String> result = new ArrayList<String>();
    	String[] parts = parse.trim().toLowerCase().split(splitter);
    	for (String part : parts) {
    		String label = getLabelValue(part.trim());
    		if (label.length() > 0) {
        		result.add(label);
    		}
    	}
    	return StringUtils.join(result,delimiter);
    }
    
}
