package com.lollion.Sinoptic;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
public static Document getPage() throws IOException {
	String url = "http://www.pogoda.spb.ru/";
	Document page = Jsoup.parse(new URL(url), 3000);
	return page;
}
private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");
public static String getDateFromString(String dateString) throws Exception {
	Matcher matcher = pattern.matcher(dateString);
	if(matcher.find()) {
		return matcher.group();
	}
	throw new Exception("can`t extract date from string");
}

private static int print4Values(Elements elements, int index) {
	int iterationCount = 4;
	if(index==0) {
		Element valueLn = elements.get(3);
		boolean isMorning = valueLn.text().contains("Утро");
		
			if(isMorning) {
				iterationCount=3;
			}
	
}
	

for(int i=0;i<4;i++) {
	Element valueLine = elements.get(index+i);
	for(Element td: valueLine.select("td")) {
		System.out.print(td.text() + "   ");
		
	}
	System.out.println();
}
System.out.println();
	return iterationCount;
}
public static void main(String[] args) throws Exception{
	Document page = getPage();
	Element tableWth = page.select("table[class=wt]").first();
	Elements names= tableWth.select("tr[class=wth]");
	Elements values = tableWth.select("tr[valign=top]");
	int index=0;
	for(Element name: names) {
		String dateString = name.select("th[id=dt]").text();
		String date = getDateFromString(dateString);
		//System.out.println( "Погодные явления    Температура   Давление Влажность  Ветер");	
		System.out.println(date + "   Погодные явления    Температура     Давление    Влажность    Ветер");
		int iterationCount = print4Values(values, index);
		index=index+iterationCount;
	}

	
}
}
