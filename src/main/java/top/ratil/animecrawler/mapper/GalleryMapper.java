package top.ratil.animecrawler.mapper;

import top.ratil.animecrawler.entity.Gallery;

import java.time.LocalDate;
import java.util.List;

public interface GalleryMapper {

    int insertByList(List<Gallery> galleryList);

    Gallery selectByPosition(int position);

    Gallery selectByDate(LocalDate date);
}
