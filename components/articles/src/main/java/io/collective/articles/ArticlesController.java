package io.collective.articles;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.collective.restsupport.BasicHandler;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public class ArticlesController extends BasicHandler {
    private final ArticleDataGateway gateway;

    public ArticlesController(ObjectMapper mapper, ArticleDataGateway gateway) {
        super(mapper);
        this.gateway = gateway;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
        get("/articles", List.of("application/json", "text/html"), baseRequest, response, () -> {
            try {
                List<ArticleRecord> allArticles = gateway.findAll();
                List<ArticleInfo> articlesInfo = allArticles.stream()
                        .map(article -> new ArticleInfo(article.getId(), article.getTitle(), article.isAvailable()))
                        .collect(Collectors.toList());
                writeJsonBody(response, articlesInfo);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        });

        get("/available", List.of("application/json"), baseRequest, response, () -> {
            try {
                List<ArticleRecord> availableArticles = gateway.findAvailable();
                List<ArticleInfo> articlesInfo = availableArticles.stream()
                        .map(article -> new ArticleInfo(article.getId(), article.getTitle(), article.isAvailable()))
                        .collect(Collectors.toList());
                writeJsonBody(response, articlesInfo);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        });
    }

    private void writeJsonBody(HttpServletResponse response, List<ArticleInfo> articles) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        new ObjectMapper().writeValue(response.getWriter(), articles);
    }
}
