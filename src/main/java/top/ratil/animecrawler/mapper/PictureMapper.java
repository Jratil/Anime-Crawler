package top.ratil.animecrawler.mapper;

import top.ratil.animecrawler.entity.Picture;

import java.time.LocalDate;
import java.util.List;

public interface PictureMapper {

    Picture selectById(int picId);

    List<Picture> selectByDate(LocalDate date);

    List<Picture> selectByGallery(String galleryUrl);


    int insertByPicture(Picture picture);
}
