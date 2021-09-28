package ru.mgazizova.demodb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mgazizova.demodb.datasource.FilmFinder;
import ru.mgazizova.demodb.model.Film;

/**
 * @author vtarasov
 * @since 04.11.18
 */
@RestController
public class ImageController {

    @Autowired
    private FilmFinder filmFinder;

    @GetMapping("film/{id}/image")
    @ResponseBody
    public byte[] image(@PathVariable int id) {
        int i;
        Film film = filmFinder.load(id);
        return film.getImage();
    }
}
