package ru.mgazizova.demodb.web.admin;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mgazizova.demodb.datasource.CountryFinder;
import ru.mgazizova.demodb.datasource.FactoryFinder;
import ru.mgazizova.demodb.model.Country;
import ru.mgazizova.demodb.model.Factory;

/**
 * @author vtarasov
 * @since 03.11.18
 */
@Controller
public class FactoriesController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CountryFinder countryFinder;

    @Autowired
    private FactoryFinder factoryFinder;

    @GetMapping("admin/factories")
    public String index(ModelMap model) throws Exception {
        List<Factory> factories = factoryFinder.list();
        model.addAttribute("factories", factories);
        return "admin/factories/index";
    }

    @GetMapping("admin/factories/add")
    public String getAdd(ModelMap model) throws Exception {
        List<Country> countries = countryFinder.list();
        model.addAttribute("countries", countries);

        return "admin/factories/add";
    }

    @PostMapping("admin/factories/add")
    @Transactional
    public String add(@RequestParam String name, @RequestParam String country) throws Exception {
        Factory factory = new Factory();
        factory.setName(name);

        if (country != null && !"null".equals(country)) {
            factory.setCountry(countryFinder.load(Integer.parseInt(country)));
        }

        em.persist(factory);

        return "redirect:/admin/factories";
    }

    @GetMapping("admin/factories/{id}/edit")
    public String getEdit(ModelMap model, @PathVariable int id) throws Exception  {
        Factory factory = factoryFinder.load(id);
        List<Country> countries = countryFinder.list();

        model.addAttribute("factory", factory);
        model.addAttribute("countries", countries);

        return "admin/factories/edit";
    }

    @PostMapping("admin/factories/{id}/edit")
    @Transactional
    public String edit(@PathVariable int id, @RequestParam String name, @RequestParam String country) throws Exception {
        Factory factory = factoryFinder.load(id);
        factory.setName(name);

        if (country != null && !"null".equals(country)) {
            factory.setCountry(countryFinder.load(Integer.parseInt(country)));
        } else {
            factory.setCountry(null);
        }

        em.merge(factory);

        return "redirect:/admin/factories";
    }

    @GetMapping("admin/factories/{id}/delete")
    @Transactional
    public String delete(@PathVariable int id) throws Exception {
        Factory factory = factoryFinder.load(id);
        em.remove(factory);

        return "redirect:/admin/factories";
    }
}
