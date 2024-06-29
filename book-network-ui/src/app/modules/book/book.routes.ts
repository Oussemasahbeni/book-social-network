import {Routes} from "@angular/router";
import {MainComponent} from "./pages/main/main.component";
import {BookListComponent} from "./pages/book-list/book-list.component";
import {MyBooksComponent} from "./pages/my-books/my-books.component";
import {BorrowedBookListComponent} from "./pages/borrowed-book-list/borrowed-book-list.component";
import {ReturnedBooksComponent} from "./pages/returned-books/returned-books.component";
import {BookDetailsComponent} from "./pages/book-details/book-details.component";
import {ManageBookComponent} from "./pages/manage-book/manage-book.component";
import {AuthGuard} from "../../services/guard/auth.guard";

export default [
  {
    path: '',
    component: MainComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: BookListComponent,
        canActivate: [AuthGuard],

      },
      {
        path: 'my-books',
        component: MyBooksComponent,
        canActivate: [AuthGuard],

      },
      {
        path: 'my-borrowed-books',
        component: BorrowedBookListComponent,
        canActivate: [AuthGuard],

      },
      {
        path: 'my-returned-books',
        component: ReturnedBooksComponent,
        canActivate: [AuthGuard],

      },
      {
        path: 'details/:bookId',
        component: BookDetailsComponent,
        canActivate: [AuthGuard],

      },
      {
        path: 'manage',
        component: ManageBookComponent,
        canActivate: [AuthGuard],

      },
      {
        path: 'manage/:bookId',
        component: ManageBookComponent,
        canActivate: [AuthGuard],
      }
    ]
  }
] as Routes;
