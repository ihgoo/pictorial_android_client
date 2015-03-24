package com.ihgoo.rosi.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ihgoo.rosi.bean.ImageSimpleBean;

public class ParserDetailList {

	public static List<ImageSimpleBean> parser(String mRet) {
		List<ImageSimpleBean> imageSimpleBeans = new ArrayList<ImageSimpleBean>();
		
		if (mRet!=null) {
			Document document = Jsoup.parse(mRet);
			Elements elements = document.getElementsByClass("gallery-icon");
			
			for (Element element : elements) {
				
				ImageSimpleBean imageSimpleBean = new ImageSimpleBean();
				
				String url = element.select("img[src]").attr("src");
				String href = element.select("a[href]").attr("href");
				String height =  element.select("img[src]").attr("height");
				String width = element.select("img[src]").attr("width");
				
				
				imageSimpleBean.setWidth(width);
				imageSimpleBean.setHeight(height);
				imageSimpleBean.setUrl(url);
				imageSimpleBean.setDetailurl(href);
				imageSimpleBeans.add(imageSimpleBean);
			}
		}
		return imageSimpleBeans;
	}

}
