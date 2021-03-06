package com.codeup.blog.blog.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "int(11) UNSIGNED")
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "Varchar(255)")
    private String blog_description;

    @Column(columnDefinition = "Varchar(255)")
    private String blog_image;

    @Column(columnDefinition = "Varchar(255)")
    private String blog_image_credit;

    @Column(name = "timestamp", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date time_stamp;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public User() {
    }

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
        blog_description = copy.blog_description;
        blog_image = copy.blog_image;
        blog_image_credit = copy.blog_image_credit;
    }

    public User(String username, String email, String password, String blog_description, String blog_image, String blog_image_credit, Date time_stamp) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.blog_description = blog_description;
        this.blog_image = blog_image;
        this.blog_image_credit = blog_image_credit;
        this.time_stamp = time_stamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBlog_image() {
        return blog_image;
    }

    public void setBlog_image(String blog_image) {
        if (blog_image.length() > 0) {
            this.blog_image = blog_image;
        }
    }

    public List<Post> getPosts() {
        return posts;
    }


    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getBlog_description() {
        return blog_description;
    }

    public void setBlog_description(String blog_description) {
        this.blog_description = blog_description;
    }

    public Date getTime_stamp() {
        return time_stamp;
    }

    public String getTime_stamp_String() {
        String pattern = "MMMM dd,yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(time_stamp);
        return date;
    }

    public void setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getBlog_image_credit() {
        return blog_image_credit;
    }

    public void setBlog_image_credit(String blog_image_credit) {
        this.blog_image_credit = blog_image_credit;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", blog_description='" + blog_description + '\'' +
                ", blog_image='" + blog_image + '\'' +
                ", time_stamp=" + time_stamp +
                ", posts=" + posts +
                ", categories=" + categories +
                '}';
    }
}
