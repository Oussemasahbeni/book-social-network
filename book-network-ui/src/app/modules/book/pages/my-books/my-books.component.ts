import {Component, OnInit} from '@angular/core';
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";
import {BookService} from "../../../../services/services/book.service";
import {Router, RouterModule} from "@angular/router";

import {BookCardComponent} from "../../component/book-card/book-card.component";
import {BookResponse} from "../../../../services/models/book-response";

@Component({
  selector: 'app-my-books',
  standalone: true,
  imports: [BookCardComponent, RouterModule],
  templateUrl: './my-books.component.html',
  styleUrl: './my-books.component.scss'
})
export class MyBooksComponent implements OnInit {

  bookResponse: PageResponseBookResponse = {};
  page = 0;
  size = 5;
  pages: any = [];
  message = '';
  level: 'success' | 'error' = 'success';

  constructor(
    private bookService: BookService,
    private router: Router
  ) {
  }

  get isLastPage() {
    return this.page === this.bookResponse.totalPages as number - 1;
  }

  ngOnInit(): void {
    this.findAllBooks();
  }

  gotToPage(page: number) {
    this.page = page;
    this.findAllBooks();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllBooks();
  }

  goToLastPage() {
    this.page = this.bookResponse.totalPages as number - 1;
    this.findAllBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllBooks();
  }

  archiveBook(book: BookResponse) {
    this.bookService.updateArchiveStatus({
      'bookId': book.id as number
    }).subscribe({
      next: () => {
        book.archived = !book.archived;
      }
    });
  }

  shareBook(book: BookResponse) {
    this.bookService.updateSharableStatus({
      'bookId': book.id as number
    }).subscribe({
      next: () => {
        book.sharable = !book.sharable;
      }
    });
  }

  editBook(book: BookResponse) {
    this.router.navigate(['books', 'manage', book.id]);
  }

  private findAllBooks() {
    this.bookService.findAllBooksByOwner({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (books) => {
          this.bookResponse = books;
          this.pages = Array(this.bookResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
        }
      });
  }

}
