package com.codeup.blog.blog.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "int(11) UNSIGNED")
    private long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(columnDefinition = "VARCHAR(2000)")
    private String picture_url;

    @Column(columnDefinition = "Varchar(255)")
    private String picture_credit;

    @Column(name="timestamp", columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date time_stamp;

    @OneToOne(mappedBy = "post")
    private PostDetails postDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="post_categories",
            joinColumns={@JoinColumn(name="post_id")},
            inverseJoinColumns={@JoinColumn(name="category_id")}
    )
    private List<Category> categories;

    public Post() {

    }

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public PostDetails getPostDetails() {
        return postDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getTime_stamp_String() {
        String pattern = "MMMM dd,yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(time_stamp);
        return date;
    }

    public void setPostDetails(PostDetails postDetails) {
        this.postDetails = postDetails;
    }

    public List<Category> getCategories() {

        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getPicture_credit() {
        return picture_credit;
    }

    public void setPicture_credit(String picture_credit) {
        this.picture_credit = picture_credit;
    }
}

