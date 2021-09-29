package ru.mgazizova.demodb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.mgazizova.demodb.datasource.FilmFinder;
import ru.mgazizova.demodb.model.Film;

/**
 * @author vtarasov
 * @since 04.11.18
 */
@Controller
public class FilmController {

    @Autowired
    private FilmFinder filmFinder;

    @GetMapping("/film/{id}")
    public String index(ModelMap model, @PathVariable int id) throws Exception {
        Film film = filmFinder.load(id);
        model.addAttribute("film", film);

        return "film";
    }
}
