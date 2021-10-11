package be.vdab.keuken.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Sql("/insertArtikel.sql")
@Import(JpaArtikelRepository.class)
class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaArtikelRepository repository;

    JpaArtikelRepositoryTest(JpaArtikelRepository repository) {
        this.repository = repository;
    }

    private long idVanTestArt() {
        return jdbcTemplate.queryForObject("select id from artikels where naam = 'testA'", Long.class);

    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestArt())).hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("testA"));
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }
}
