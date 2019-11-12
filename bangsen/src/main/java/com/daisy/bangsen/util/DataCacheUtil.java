package com.daisy.bangsen.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataCacheUtil {
    public static final String APPID = "wx46a7bdb0c081aabf";
    public static final String APPSECRET = "2bf0250ac605bf76c622c15695d8b2a5";
    public static ConcurrentHashMap<String, Map<String, String>> AccessData = new ConcurrentHashMap();
    public static ConcurrentHashMap<String, String> AccessToken_auth = new ConcurrentHashMap();
    public static String WeightPaltformToken = "";
    public static ConcurrentHashMap<String, String> aucodeMap = new ConcurrentHashMap<>();
    public static Map<String, String> TerminAndNumbersMaps = new HashMap<>();
    public static Map<String, HashMap> CarPositons = new HashMap<>();
    public static Map<String, HashMap> TodaysWeight = new HashMap<>();
}

