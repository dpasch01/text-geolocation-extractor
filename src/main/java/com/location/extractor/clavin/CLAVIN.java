package com.location.extractor.clavin;

import com.bericotech.clavin.ClavinException;
import com.bericotech.clavin.GeoParser;
import com.bericotech.clavin.GeoParserFactory;
import com.bericotech.clavin.resolver.ResolvedLocation;

import java.util.List;

public class CLAVIN {

    public static GeoParser GEOPARSER;

    static {
        try {
            GEOPARSER = GeoParserFactory.getDefault("./index");
        } catch (ClavinException e) {
            e.printStackTrace();
        }
    }

    public static List<ResolvedLocation> resolve(String text) throws Exception {
        return GEOPARSER.parse(text);
    }

}
