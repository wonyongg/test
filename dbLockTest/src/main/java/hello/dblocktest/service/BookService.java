package hello.dblocktest.service;

import hello.dblocktest.Dto.Dto;
import hello.dblocktest.entity.Book;
import hello.dblocktest.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public void upsertBook(Dto dto) {

        log.info("db 접근");
        Optional<Book> optionalBook = bookRepository.findByTitleAndAuthor(dto.getTitle(), dto.getAuthor());
        log.info("db 조회 완료");

        if (optionalBook.isPresent()) {
            log.info("기존 Book  엔티티 업데이트");
            optionalBook.get().updateBorrowerAndLoanPeriod(dto.getBorrower(), dto.getLoanPeriod());
        } else {
            log.info("새로운 Book 엔티티 생성");
            Book newBook = Book.createBookOf(dto.getTitle(), dto.getAuthor(), dto.getBorrower(), dto.getLoanPeriod());
            bookRepository.save(newBook);
        }
    }
}
