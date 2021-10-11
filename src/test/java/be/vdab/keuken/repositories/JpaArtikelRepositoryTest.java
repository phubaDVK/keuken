package be.vdab.keuken.repositories;

import be.vdab.keuken.domain.Artikel;
import be.vdab.keuken.domain.FoodArtikel;
import be.vdab.keuken.domain.NonFoodArtikel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Sql("/insertArtikel.sql")
@Import(JpaArtikelRepository.class)
class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaArtikelRepository repository;
    private static final String ARTIKELS = "artikels";
    private Artikel artikel;

    JpaArtikelRepositoryTest(JpaArtikelRepository repository) {
        this.repository = repository;
    }

    private long idVanTestFoodArtikel() {
        return jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'testfood'", Long.class);
    }
    private long idVanTestNonFoodArtikel() {
        return jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'testnonfood'", Long.class);
    }
    @Test
    void findFoodArtikelById() {
        assertThat(repository.findById(idVanTestFoodArtikel()))
                .containsInstanceOf(FoodArtikel.class)
                .hasValueSatisfying(
                        artikel -> assertThat(artikel.getNaam()).isEqualTo("testfood"));
    }
    @Test
    void findNonFoodArtikelById() {
        assertThat(repository.findById(idVanTestNonFoodArtikel()))
                .containsInstanceOf(NonFoodArtikel.class)
                .hasValueSatisfying(
                        artikel -> assertThat(artikel.getNaam()).isEqualTo("testnonfood"));
    }
    @Test
    void findOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }
    @Test
    void createFoodArtikel() {
        var artikel = new FoodArtikel("testfood2",BigDecimal.ONE,BigDecimal.TEN,7);
        repository.create(artikel);
        assertThat(countRowsInTableWhere(ARTIKELS,
                "id =" + artikel.getId())).isOne();
    }
    @Test
    void createNonFoodArtikel() {
        var artikel =
                new NonFoodArtikel("testnonfood2", BigDecimal.ONE, BigDecimal.TEN, 30);
        repository.create(artikel);
        assertThat(countRowsInTableWhere(ARTIKELS,
                "id=" + artikel.getId())).isOne();
    }
    @Test
    void findBijNaamContains() {
        assertThat(repository.findByNaamContains("es"))
                .hasSize(countRowsInTableWhere(ARTIKELS, "naam like '%es%'"))
                .extracting(Artikel::getNaam)
                .allSatisfy(naam -> assertThat(naam).containsIgnoringCase("es"))
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }
    @Test
    void verhoogAlleVerkoopPrijzen() {
        assertThat(repository.algemenePrijsVerhoging(BigDecimal.TEN))
                .isEqualTo(countRowsInTable("artikels"));
        assertThat(countRowsInTableWhere(ARTIKELS,
                "verkoopprijs = 132 and id = "+idVanTestFoodArtikel())).isOne();
    }

}
