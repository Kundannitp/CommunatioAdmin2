package com.example.communatioadmin2;

public class Profilestudent {
    String name,email,contactno,college,imageurl;

    public Profilestudent(String name, String email, String contactno, String college, String imageurl) {
        this.name = name;
        this.email = email;
        this.contactno = contactno;
        this.college = college;
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
