package top.ratil.animecrawler.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.apache.tomcat.jni.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Gallery implements Serializable {
    private Integer id;
    private Integer picNum;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate galleryDate;
    private String galleryUrl;
    private List<Picture> pictureList;

    public Gallery(Integer id, Integer picNum, LocalDate galleryDate, String galleryUrl) {
        this.id = id;
        this.picNum = picNum;
        this.galleryDate = galleryDate;
        this.galleryUrl = galleryUrl;
    }

    public Gallery() {
    }

    @Override
    public String toString() {
        return "Gallery{" +
                "id=" + id +
                ", picNum=" + picNum +
                ", galleryDate=" + galleryDate +
                ", galleryUrl='" + galleryUrl + '\'' +
                ", pictureList=" + pictureList +
                '}';
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPicNum() {
        return picNum;
    }

    public void setPicNum(Integer picNum) {
        this.picNum = picNum;
    }

    public LocalDate getGalleryDate() {
        return galleryDate;
    }

    public void setGalleryDate(LocalDate galleryDate) {
        this.galleryDate = galleryDate;
    }

    public String getGalleryUrl() {
        return galleryUrl;
    }

    public void setGalleryUrl(String galleryUrl) {
        this.galleryUrl = galleryUrl;
    }
}
