package com.khrd.spring_boot_mini_project.service.bookMarkImpl;

import com.khrd.spring_boot_mini_project.exception.NotFoundException;
import com.khrd.spring_boot_mini_project.exception.ResourceNotFoundException;
import com.khrd.spring_boot_mini_project.model.entity.*;
import com.khrd.spring_boot_mini_project.model.response.bookmarkResponse.BookMarkResponseDTO;
import com.khrd.spring_boot_mini_project.model.userDetail.CustomUserDetails;
import com.khrd.spring_boot_mini_project.repository.ArticleRepository;
import com.khrd.spring_boot_mini_project.repository.BookMarkRepository;
import com.khrd.spring_boot_mini_project.repository.UserRepository;
import com.khrd.spring_boot_mini_project.service.BookMarkService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookMarkImpl implements BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final ArticleRepository articleRepository;

    @Override
    public List<BookMarkResponseDTO> getAllBookMark(Pageable pageable) {
        Page<Bookmark> bookmarkPage = bookMarkRepository.findAll(pageable);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer getUserId = customUserDetails.getUserId();

        List<BookMarkResponseDTO> bookMarkResponseDTOS = new ArrayList<>();

        for (Bookmark bookmark : bookmarkPage.getContent()) {
            if (bookmark.getStatus()) {
                Article article = bookmark.getArticle();
                List<Integer> categoryIdList = new ArrayList<>();

                for (CategoryArticle categoryArticle : article.getCategoryArticles()) {
                    categoryIdList.add(categoryArticle.getCategory().getCategoryId());
                }

                bookMarkResponseDTOS.add(article.toResponse(getUserId, categoryIdList, article.getComments()));
            }
        }

        return bookMarkResponseDTOS;
    }


    @Override
    public Bookmark createBookMark(Integer articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            throw new ResourceNotFoundException("Article id " + articleId + " not found.");
        }

        Article article = optionalArticle.get();
        Bookmark bookmark = new Bookmark();
        bookmark.setCreateAt(LocalDateTime.now());
        bookmark.setStatus(true);
        bookmark.setUpdateAt(LocalDateTime.now());
        bookmark.setArticle(article);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        bookmark.setUser(user);

        return bookMarkRepository.save(bookmark);
    }

    @Override
    public Bookmark updateBookmark(Integer bookmarkId) {
        if (bookMarkRepository.findById(bookmarkId).isPresent()) {
            Bookmark mark = bookMarkRepository.findById(bookmarkId).get();
            mark.setUpdateAt(LocalDateTime.now());
            mark.setStatus(false);
            return bookMarkRepository.save(mark);
        }
        else  {
            throw new ResourceNotFoundException("BookMark id " + bookmarkId + " not found.");
        }
    }


}
