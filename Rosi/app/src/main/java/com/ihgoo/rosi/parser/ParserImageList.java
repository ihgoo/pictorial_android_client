package com.ihgoo.rosi.parser;


import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ihgoo.rosi.bean.ImageBean;
import com.ihgoo.rosi.bean.ImageListBean;


public class ParserImageList {

	public static ImageListBean parser(String mRet) {
		
		ImageListBean imageListBean = new ImageListBean();
		if (mRet!=null) {
			Document document = Jsoup.parse(mRet);
			Elements elements = document.getElementsByClass("post-inner");
			
			
			for (Element element : elements) {
				
				String imgurl = element.select("a[title]").attr("href");
				Element element2 = element.select("img[src]").first();
				String src = element2.attr("src");
				String width = element2.attr("width");
				String height = element2.attr("height");
				String alt = element2.attr("alt");
				
				ImageBean imageBean = new ImageBean();
				imageBean.setAlt(alt);
				imageBean.setDetailurl(imgurl);
				imageBean.setHeight(height);
				imageBean.setWidth(width);
				imageBean.setImgurl(src);
				
				imageListBean.add(imageBean);
			}
			
			
		}
		return imageListBean;
		
	}

}
