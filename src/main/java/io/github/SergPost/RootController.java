package io.github.SergPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Controller
public class RootController {
    
    private static final int PAGE_SIZE = 10;
    private static final String SORT = "title";
    
    private final BookRepo repo;
    
    @Autowired
    public RootController(BookRepo repo) {
        this.repo = repo;
    }
    
    @GetMapping("/")
    public String books(
            @RequestParam(value = "q", required = false) String query,
            Model model,
            @PageableDefault(size = PAGE_SIZE, sort = SORT, direction = ASC) Pageable pageable
    ) {
        Page<Book> books = repo.findByTitle((query != null) ? query : "", pageable);
        model.addAttribute("books", books);
        model.addAttribute("query", query);

        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("current", pageable.getPageNumber());
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());

        return "books";
    }
    
    @GetMapping("/book/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("book", repo.findOne(id));
        return "edit";
    }
    
    @PostMapping("/book")
    public String save(@ModelAttribute Book book) {
        repo.save(book);
        try {
            return "redirect:/?q=" + URLEncoder.encode(book.getTitle(), "UTF8");
        } catch (UnsupportedEncodingException ignore) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/book/delete/{id}")
    public String delete(@PathVariable Integer id) {
        repo.delete(id);
        return "redirect:/";
    }
    
    @GetMapping("/book")
    public String create(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "edit";
    }
}
