package com.oussema.bsn.book;

import com.oussema.bsn.common.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "book", description = "the book API")
public class BookController {

    private final BookService bookservice;

    @PostMapping
    public ResponseEntity<Integer> create(
            @Valid @RequestBody BookRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookservice.createBook(request, connectedUser));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Integer bookId) {
        return ResponseEntity.ok(bookservice.findById(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookservice.findAll(page, size, connectedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookservice.findAllByOwner(page, size, connectedUser));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookservice.findAllBorrowedBooks(page, size, connectedUser));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookservice.findAllReturnedBooks(page, size, connectedUser));
    }

    @PatchMapping("/sharable/{bookId}")
    public ResponseEntity<Integer> updateSharableStatus(@PathVariable Integer bookId, Authentication connectedUser) {
        return ResponseEntity.ok(bookservice.updateSharableStatus(bookId, connectedUser));
    }

    @PatchMapping("/archive/{bookId}")
    public ResponseEntity<Integer> updateArchiveStatus(@PathVariable Integer bookId, Authentication connectedUser) {
        return ResponseEntity.ok(bookservice.updateArchiveStatus(bookId, connectedUser));
    }


    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<Integer> borrowBook(@PathVariable Integer bookId, Authentication connectedUser) {
        return ResponseEntity.ok(bookservice.borrowBook(bookId, connectedUser));
    }

    @PatchMapping("/return/{bookId}")
    public ResponseEntity<Integer> returnBook(@PathVariable Integer bookId, Authentication connectedUser) {
        return ResponseEntity.ok(bookservice.returnBook(bookId, connectedUser));
    }

    @PatchMapping("/borrow/return/approve/{bookId}")
    public ResponseEntity<Integer> approveReturnedBorrowBook(@PathVariable Integer bookId, Authentication connectedUser) {
        return ResponseEntity.ok(bookservice.approveReturnedBorrowBook(bookId, connectedUser));
    }

    @PostMapping(value = "/cover/{bookId}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadBookCoverPicture(@PathVariable Integer bookId, @Parameter() @RequestPart("file") MultipartFile file, Authentication connectedUser) {
        bookservice.uploadBookCoverPicture(bookId, file, connectedUser);
        return ResponseEntity.accepted().build();
    }


}
