package io.collective.articles;

public class ArticleInfo {
    private int id;
    private String title;
    private boolean available;

    public ArticleInfo() {
    }

    public ArticleInfo(int id, String title, boolean available) {
        this.id = id;
        this.title = title;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
