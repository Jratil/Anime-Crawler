package top.ratil.animecrawler.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;

public class Picture implements Serializable {
    private Integer picId;
    private String picUrl;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate picDate;
    private String galleryUrl;

    public Picture() {
    }

    public Picture(Integer picId, String picUrl, LocalDate picDate, String galleryUrl) {
        this.picId = picId;
        this.picUrl = picUrl;
        this.picDate = picDate;
        this.galleryUrl = galleryUrl;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "picId=" + picId +
                ", picUrl='" + picUrl + '\'' +
                ", picDate=" + picDate +
                ", galleryUrl='" + galleryUrl + '\'' +
                '}';
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public LocalDate getPicDate() {
        return picDate;
    }

    public void setPicDate(LocalDate picDate) {
        this.picDate = picDate;
    }

    public String getGalleryUrl() {
        return galleryUrl;
    }

    public void setGalleryUrl(String galleryUrl) {
        this.galleryUrl = galleryUrl;
    }
}
