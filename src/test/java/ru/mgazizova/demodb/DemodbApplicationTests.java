package ru.mgazizova.demodb;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.mgazizova.demodb.datasource.CountryFinder;
import ru.mgazizova.demodb.model.Country;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DemodbApplicationTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CountryFinder countryFinder;

    @Transactional
    @Test
    public void shouldPersistCountry() {
        Country country = new Country();
        country.setName("TestCountry");
        em.persist(country);

        Country actualCountry = countryFinder.load(country.getId());
        Assert.assertEquals(country.getId(), actualCountry.getId());
        Assert.assertEquals(country.getName(), actualCountry.getName());
    }

    @Test
    public void shouldNotFindCountryByInvalidId() {
        Country actualCountry = countryFinder.load(-100);
        Assert.assertNull(actualCountry);
    }

    @Transactional
    @Test(expected = PersistenceException.class)
    public void shouldNotPersistNullCountry() {
        Country country = new Country();
        em.persist(country);
        em.flush();
    }
}
