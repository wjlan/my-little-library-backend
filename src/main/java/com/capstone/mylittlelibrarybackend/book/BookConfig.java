//package com.capstone.mylittlelibrarybackend.book;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class BookConfig {
//
//    @Bean
//    CommandLineRunner commandLineRunner(BookRepository bookRepository) {
//        return args -> {
//
//            Book theGreatGatsby = new Book(
//                    "The Great Gatsby",
//                    "F. Scott Fitzgerald",
//                    "Fiction",
//                    1925,
//                    "A novel about the American dream and the roaring twenties.",
//                    "English"
//            );
//
//            bookRepository.saveAll(List.of(theGreatGatsby));
//        };
//    }
//}
