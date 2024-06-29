import {Component, OnInit} from '@angular/core';

import {RatingComponent} from "../../component/rating/rating.component";
import {BookResponse} from "../../../../services/models/book-response";
import {PageResponseFeedbackResponse} from "../../../../services/models/page-response-feedback-response";
import {BookService} from "../../../../services/services/book.service";
import {FeedbackService} from "../../../../services/services/feedback.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-book-details',
  standalone: true,
  imports: [RatingComponent],
  templateUrl: './book-details.component.html',
  styleUrl: './book-details.component.scss'
})
export class BookDetailsComponent implements OnInit {
  book: BookResponse = {};
  feedbacks: PageResponseFeedbackResponse = {};
  page = 0;
  size = 5;
  pages: any = [];
  private bookId = 0;

  constructor(
    private bookService: BookService,
    private feedbackService: FeedbackService,
    private activatedRoute: ActivatedRoute
  ) {
  }

  get isLastPage() {
    return this.page === this.feedbacks.totalPages as number - 1;
  }

  ngOnInit(): void {
    this.bookId = this.activatedRoute.snapshot.params['bookId'];
    if (this.bookId) {
      this.bookService.getBook({
        'bookId': this.bookId
      }).subscribe({
        next: (book) => {
          this.book = book;
          this.findAllFeedbacks();
        }
      });
    }
  }

  gotToPage(page: number) {
    this.page = page;
    this.findAllFeedbacks();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllFeedbacks();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllFeedbacks();
  }

  goToLastPage() {
    this.page = this.feedbacks.totalPages as number - 1;
    this.findAllFeedbacks();
  }

  goToNextPage() {
    this.page++;
    this.findAllFeedbacks();
  }

  private findAllFeedbacks() {
    this.feedbackService.findAllFeedbacksByBook({
      'bookId': this.bookId,
      page: this.page,
      size: this.size
    }).subscribe({
      next: (data) => {
        this.feedbacks = data;
      }
    });
  }

}
