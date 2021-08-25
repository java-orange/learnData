package com.hvisions.jxhRoom.swagger;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * <p>Title: SwaggerDoc</p>
 * <p>Description: swagger-导出文档,运行测试方法即可生成对应的文档，需要首先先启动服务,生成目录默认为./build目录下</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2018/11/19</p>
 *
 * @author :leiming
 * @version :1.0.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Component
@Data
public class SwaggerDoc {
    @Value(value = "${h-visions.swagger.api-url}")
    private String url;
    @Value(value = "${h-visions.swagger.asciidoc-dir}")
    private String asciidocDir;
    @Value(value = "${h-visions.swagger.markdown-dir}")
    private String markdownDir;

    /**
     * 生成AsciiDocs格式文档
     *
     * @throws MalformedURLException url出错
     */
    @Test
    public void generateAsciiDocs() throws MalformedURLException {
        //    输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL(url))
                .withConfig(config)
                .build()
                .toFolder(Paths.get(asciidocDir));
    }

    /**
     * 生成Markdown格式文档
     *
     * @throws MalformedURLException url出错
     */
    @Test
    public void generateMarkdownDocs() throws MalformedURLException {
        //    输出Markdown格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL(url))
                .withConfig(config)
                .build()
                .toFolder(Paths.get(markdownDir));
    }

    /**
     * 生成AsciiDocs格式文档,并汇总成一个文件
     *
     * @throws MalformedURLException url出错
     */
    @Test
    public void generateAsciiDocsToFile() throws MalformedURLException {
        //    输出Ascii到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL(url))
                .withConfig(config)
                .build()
                .toFile(Paths.get(asciidocDir));
    }

    /**
     * 生成Markdown格式文档,并汇总成一个文件
     *
     * @throws MalformedURLException url出错
     */
    @Test
    public void generateMarkdownDocsToFile() throws MalformedURLException {
        //    输出Markdown到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL(url))
                .withConfig(config)
                .build()
                .toFile(Paths.get(markdownDir));
    }

}
















