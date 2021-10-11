package be.vdab.keuken.repositories;

import be.vdab.keuken.domain.Artikel;
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

    private long idVanTestArt() {
        return jdbcTemplate.queryForObject("select id from artikels where naam = 'testA'", Long.class);

    }

    @BeforeEach
    void beforeEach(){
       artikel = new Artikel("test2", BigDecimal.ONE, BigDecimal.TEN);
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestArt())).hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("testA"));
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void create(){
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(countRowsInTableWhere(ARTIKELS, "id=" + artikel.getId())).isOne();
    }
}
