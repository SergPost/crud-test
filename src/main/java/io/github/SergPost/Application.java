package io.github.SergPost;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public CommandLineRunner demo(BookRepo repo) {
        return args -> {
            Iterable<Book> books = repo.findAll();
            if (books.iterator().hasNext()) {
                for (Book book : books) {
                    System.out.println(book.toString());
                }
                return;
            }
        };
    }
}
