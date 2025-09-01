package org.example.phishing.protector.app.utils;


import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class UrlExtractor {
    private final Pattern URL_PATTERN = Pattern.compile("(https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+)",
            Pattern.CASE_INSENSITIVE);

    public List<String> extractUrls(String text) {
        Matcher matcher = URL_PATTERN.matcher(text);
        List<String> urls = new ArrayList<>();

        while (matcher.find()) {
            String url = matcher.group();

            urls.add(url);
        }

        return urls;
    }
}
