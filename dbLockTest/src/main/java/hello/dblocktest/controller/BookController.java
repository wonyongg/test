package hello.dblocktest.controller;

import hello.dblocktest.Dto.Dto;
import hello.dblocktest.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PutMapping("/books")
    public ResponseEntity<?> upsertBook(@RequestBody Dto dto) {

        bookService.upsertBook(dto);

        return ResponseEntity.ok().build();
    }
}
