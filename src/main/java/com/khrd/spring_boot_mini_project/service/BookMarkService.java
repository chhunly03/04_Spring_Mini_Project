package com.khrd.spring_boot_mini_project.service;

import com.khrd.spring_boot_mini_project.model.entity.Bookmark;
import com.khrd.spring_boot_mini_project.model.response.bookmarkResponse.BookMarkResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookMarkService {
    List<BookMarkResponseDTO> getAllBookMark(Pageable pageable);

    Bookmark createBookMark(Integer articleId);

    Bookmark updateBookmark(Integer bookmarkId);
}
