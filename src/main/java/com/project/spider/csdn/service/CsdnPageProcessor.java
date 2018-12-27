package com.project.spider.csdn.service;

import com.project.spider.BasePageProcessor;
import com.project.spider.csdn.dao.CsdnRepository;
import com.project.spider.csdn.info.CsdnCrawlerEntity;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ftc
 * @date 2018-12-25
 */
@Service("csdnPageProcessor")
public class CsdnPageProcessor extends BasePageProcessor {

    @Resource
    private CsdnRepository csdnRepository;

    private final static String regex = "^https://blog\\.csdn\\.net+.*(article/details)/[0-9]*$";
    private static volatile int count = 0;

    @Override
    public void process(Page page) {
        System.out.println("csdbRepository:" + csdnRepository);
        List<String> links = page.getHtml().links().all();
        links.forEach(link -> {
            if (link.matches(regex)) {
                page.addTargetRequest(link);
            }
        });
        if (page.getUrl().regex(regex).match()) {
            count ++;
            String title = page.getHtml()
                                .xpath("//div[@class='article-title-box']//h1[@class='title-article']/text()")
                                .get();

            Selectable selectable = page.getHtml().xpath("//div[@class='article-info-box']//div[@class='article-bar-top']");
            String createTime = selectable.xpath("//span[@class='time']/text()").get();
            String author = selectable.xpath("//a[@class='follow-nickname']/text()").get();
            List<String> tags = selectable.xpath("//span[@class='tags-box artic-tag-box']//a[@class='tag-link']/text()").all();
            String tag = "";
            for (String oneTag : tags) {
                tag += oneTag + ",";
            }
            System.out.println("title:" + title + ", createTime:" + createTime + ", author:" + author + ", tag:" + tag + ", total:" + count);

            CsdnCrawlerEntity entity = new CsdnCrawlerEntity();
            entity.setAuthor(author);
            entity.setCreateTime(createTime);
            entity.setTag(tag);
            entity.setTitle(title);
            entity.setUrl(page.getUrl().get());

            csdnRepository.persistAndFlush(entity);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}
