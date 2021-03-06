package com.codeup.blog.blog.models;
import javax.persistence.*;


@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "VARCHAR(2000)")
    private String url;


    @OneToOne
    private Post post;

    public Photo(String url) {
        this.url = url;
    }


    public Photo(long id, String url) {
        this.id = id;
        this.url = url;
    }

    public Photo () {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
