package com.jalian.hw7.dao.entities;

import java.sql.Date;
import java.util.HashSet;

public class Article extends BaseEntity {
    private int userId;
    private String userUsername;
    private Category category;
    private HashSet<Tag> tags;
    private String content;
    private String brief;
    private String type;
    private String title;
    private double price;
    private Date creatDate;
    private String isPublished;
    private Date publishDate;
    private Date lastUpdate;

    public Article(int id, String content, String brief, String title, Date creatDate, Date lastUpdate) {
        super(id);
        this.content = content;
        this.brief = brief;
        this.title = title;
        this.creatDate = creatDate;
        this.lastUpdate = lastUpdate;
        this.isPublished = "no";
    }

    public Article(String content, String brief, String title, Date creatDate, Date lastUpdate) {
        super();
        this.content = content;
        this.brief = brief;
        this.title = title;
        this.creatDate = creatDate;
        this.lastUpdate = lastUpdate;
        this.isPublished = "no";
    }

    public Article(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public String isPublished() {
        return isPublished;
    }

    public void setPublished(String published) {
        isPublished = published;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Category getCategories() {
        return category;
    }

    public void setCategories(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("illegal price");
        }
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashSet<Tag> getTags() {
        return tags;
    }

    public void setTags(HashSet<Tag> tags) {
        this.tags = tags;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(String isPublished) {
        this.isPublished = isPublished;
    }

    @Override
    public String toString() {
        return "id: " + String.valueOf(id) + ", content: " + content + ", brief: " + brief + ", title: " + title + ", type:" + type + ", price:" + price + ", create date: " +
                creatDate.toString() + ", last update:" + lastUpdate.toString() + ", category = " + category.toString() + ", tags: " + tagsToString();
    }

    private String tagsToString() {
        StringBuilder str = new StringBuilder();
        if (tags == null || tags.size() == 0) {
            return "";
        }
        for (Tag tag : tags) {
            str.append(tag.toString());
            str.append("/");
        }
        return str.toString();
    }
}