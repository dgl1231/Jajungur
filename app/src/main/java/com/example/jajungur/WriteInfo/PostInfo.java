package com.example.jajungur.WriteInfo;

import java.util.ArrayList;
import java.util.Date;

public class PostInfo {

    private String Title;
    private String Contents;
    private String Price;
    private String publisher;
    private String ImgUri;
    private Date createdAt;

    public PostInfo(String Title, String Contents, String Price, String publisher, String ImgUri, Date createdAt){
        this.Title = Title;
        this.Contents = Contents;
        this.Price = Price;
        this.publisher = publisher;
        this.ImgUri = ImgUri;
        this.createdAt = createdAt;
    }

    public String getTitle(){ return this.Title; }
    public void setTitle(String Title){ this.Title = Title;}
    public String getContents(){ return this.Contents; }
    public void setContents(String contents){ this.Contents = contents;}
    public String getPrice(){ return this.Price; }
    public void setPrice(String Price){ this.Price = Price;}
    public String getpublisher(){ return this.publisher; }
    public void setpublisher(String publisher){ this.publisher = publisher;}
    public String getImgUri(){ return this.ImgUri; }
    public void setImgUri(String ImgUri){ this.ImgUri = ImgUri;}
    public Date getcreatedAt(){ return this.createdAt; }
    public void setcreatedAt(Date createdAt){ this.createdAt = createdAt;}
}
