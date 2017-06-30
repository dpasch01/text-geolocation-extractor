package com.location.extractor.service;

import com.bericotech.clavin.gazetteer.GeoName;
import com.bericotech.clavin.resolver.ResolvedLocation;
import com.location.extractor.clavin.CLAVIN;
import com.location.extractor.clavin.CLAVINObject;
import com.location.extractor.utils.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Service {

    public static CLAVINObject resolve(String text) throws Exception {
        HashMap<String, Integer> resolvedCounter = new HashMap<>();
        HashMap<String, GeoName> resolvedMap = new HashMap<>();
        HashMap<String, Integer> parentCounter = new HashMap<>();
        HashMap<String, HashSet<String>> parentMap = new HashMap<>();

        for (ResolvedLocation resolvedLocation : CLAVIN.resolve(text)) {

            Integer count = resolvedCounter.get(resolvedLocation.getMatchedName());
            if (resolvedCounter.get(resolvedLocation.getMatchedName()) == null) {
                resolvedCounter.put(resolvedLocation.getMatchedName(), 1);
                resolvedMap.put(resolvedLocation.getMatchedName(), resolvedLocation.getGeoname());
            } else {
                count++;
                resolvedCounter.put(resolvedLocation.getMatchedName(), count);
            }

            String parent = resolvedLocation.getGeoname().getPrimaryCountryName();
            count = parentCounter.get(parent);
            if (parentCounter.get(parent) == null) {
                parentCounter.put(parent, 1);
                HashSet<String> children = parentMap.get(parent);
                if (children == null) {
                    children = new HashSet<>();
                    children.add(resolvedLocation.getMatchedName());
                    parentMap.put(parent, children);
                } else {
                    children.add(resolvedLocation.getMatchedName());
                    parentMap.put(parent, children);
                }
            } else {
                count++;
                parentCounter.put(parent, count);
            }
        }

        String parent = new LinkedList<>(Utils.sortByValue(parentCounter).keySet()).get(0);
        HashMap<String, Integer> temp = new HashMap<>();
        for (String l : parentMap.get(parent)) {
            temp.put(l, resolvedCounter.get(l));
        }

        String location = new LinkedList<>(Utils.sortByValue(temp).keySet()).get(0);

        return new CLAVINObject(location, parent, resolvedMap.get(location).getLatitude(), resolvedMap.get(location).getLongitude());
    }

    public static CLAVINObject resolveURL(String url) throws Exception {
        Document document = Jsoup.connect(url).get();
        String text = document.body().text();
        return resolve(text);
    }

    public static void main(String args[]) throws Exception {
        System.out.println(CLAVIN.resolve("Μολις εφυγα απο την Ανδόρα."));
//        Service.resolveURL("http://edition.cnn.com/2017/06/30/europe/germany-gay-marriage-vote/index.html");
    }
}
