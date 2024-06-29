import {Component, OnInit} from '@angular/core';

import {FormsModule} from "@angular/forms";
import {PageResponseBorrowedBookResponse} from "../../../../services/models/page-response-borrowed-book-response";
import {BookResponse} from "../../../../services/models/book-response";
import {FeedbackRequest} from "../../../../services/models/feedback-request";
import {BookService} from "../../../../services/services/book.service";
import {FeedbackService} from "../../../../services/services/feedback.service";
import {BorrowedBookResponse} from "../../../../services/models/borrowed-book-response";
import {RatingComponent} from "../../component/rating/rating.component";
import {RouterModule} from "@angular/router";

@Component({
  selector: 'app-borrowed-book-list',
  standalone: true,
  imports: [FormsModule, RatingComponent, RouterModule],
  templateUrl: './borrowed-book-list.component.html',
  styleUrl: './borrowed-book-list.component.scss'
})
export class BorrowedBookListComponent implements OnInit {

  page = 0;
  size = 5;
  pages: any = [];
  borrowedBooks: PageResponseBorrowedBookResponse = {};
  selectedBook: BookResponse | undefined = undefined;
  feedbackRequest: FeedbackRequest = {bookId: 0, comment: '', rate: 0};

  constructor(
    private bookService: BookService,
    private feedbackService: FeedbackService
  ) {
  }

  get isLastPage() {
    return this.page === this.borrowedBooks.totalPages as number - 1;
  }

  ngOnInit(): void {
    this.findAllBorrowedBooks();
  }

  gotToPage(page: number) {
    this.page = page;
    this.findAllBorrowedBooks();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBorrowedBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllBorrowedBooks();
  }

  goToLastPage() {
    this.page = this.borrowedBooks.totalPages as number - 1;
    this.findAllBorrowedBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllBorrowedBooks();
  }

  returnBorrowedBook(book: BorrowedBookResponse) {
    this.selectedBook = book;
    this.feedbackRequest.bookId = book.id as number;
  }

  returnBook(withFeedback: boolean) {
    this.bookService.returnBook({
      'bookId': this.selectedBook?.id as number
    }).subscribe({
      next: () => {
        if (withFeedback) {
          this.giveFeedback();
        }
        this.selectedBook = undefined;
        this.findAllBorrowedBooks();
      }
    });
  }

  private findAllBorrowedBooks() {
    this.bookService.findAllBorrowedBooks({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (resp) => {
        this.borrowedBooks = resp;
        this.pages = Array(this.borrowedBooks.totalPages)
          .fill(0)
          .map((x, i) => i);
      }
    });
  }

  private giveFeedback() {
    this.feedbackService.saveFeedback({
      body: this.feedbackRequest
    }).subscribe();
  }
}
