package hello.dblocktest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 책의 고유 id

    private String title; // 책명

    private String author; // 저자

    private String borrower; // 대여자

    private int loanPeriod; // 대여일수

    private int version; // 최신 업데이트 버전

    public void updateBorrowerAndLoanPeriod(String borrower, int loanPeriod) {
        this.borrower = borrower;
        this.loanPeriod = loanPeriod;
    }

    private Book(String title, String author, String borrower, int loanPeriod) {
        this.title = title;
        this.author = author;
        this.borrower = borrower;
        this.loanPeriod = loanPeriod;
        this.version = version++;
    }

    public static Book createBookOf(String title, String author, String borrower, int loanPeriod) {

        return new Book(title, author, borrower, loanPeriod);
    }
}
