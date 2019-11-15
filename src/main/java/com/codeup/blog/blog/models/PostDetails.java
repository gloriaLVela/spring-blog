package com.codeup.blog.blog.models;

import javax.persistence.*;

@Entity
public class PostDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "int(11) UNSIGNED")
    private long id;

    @Column(columnDefinition = "BOOLEAN DEFAULT 0")
    private boolean isAwesome;

    @Column(columnDefinition = "TEXT")
    private String historyOfPost;

    @Column(columnDefinition ="VARCHAR (255)")
    private String topicDescription;

    @OneToOne
    private Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAwesome() {
        return isAwesome;
    }

    public void setAwesome(boolean awesome) {
        isAwesome = awesome;
    }

    public String getHistoryOfPost() {
        return historyOfPost;
    }

    public void setHistoryOfPost(String historyOfPost) {
        this.historyOfPost = historyOfPost;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }


    @Override
    public String toString() {
        return "PostDetails{" +
                "id=" + id +
                ", isAwesome=" + isAwesome +
                ", historyOfPost='" + historyOfPost + '\'' +
                ", topicDescription='" + topicDescription + '\'' +
                '}';
    }
}
