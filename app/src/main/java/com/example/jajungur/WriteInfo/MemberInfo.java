package com.example.jajungur.WriteInfo;

public class MemberInfo {

    public static String name;
    public static String phonenumber;
    public static String email;
    public static String grade;
    public static String ImgUri;

    public MemberInfo(String name, String phonenumber, String email,String ImgUri, String grade)
    {
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.grade = grade;
        this.ImgUri = ImgUri;

    }
    public String getName()
    {
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return this.email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhonenumber()
    {
        return this.phonenumber;
    }
    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getgrade()
    {
        return this.grade;
    }
    public void setgrade(String grade)
    {
        this.grade = grade;
    }
    public String getImgUri(){ return this.ImgUri; }
    public void setImgUri(String ImgUri){ this.ImgUri = ImgUri;}

}
