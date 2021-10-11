package be.vdab.keuken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class ArtikelTest {
    private Artikel artikel;

    @BeforeEach
    void beforeEach(){
        artikel = new Artikel("test", BigDecimal.ONE, BigDecimal.TEN);
    }

    @Test
    void verhoogVerkoopPrijs () {
        artikel.verhoogVerkoopPrijs(BigDecimal.TEN);
        assertThat(artikel.getVerkoopprijs()).isEqualByComparingTo("20");
    }

    @Test
    void verhoogVerkoopPrijsMetNullMistlukt(){
        assertThatNullPointerException().isThrownBy(() -> artikel.verhoogVerkoopPrijs(null));
    }

    @Test
    void verhoogVerkoopPrijsMet0Mislukt(){
        assertThatIllegalArgumentException().isThrownBy(() -> artikel.verhoogVerkoopPrijs(BigDecimal.ZERO));
    }

    @Test
    void negatievePrijsverhogingMislukt() {
        assertThatIllegalArgumentException().isThrownBy(() -> artikel.verhoogVerkoopPrijs(BigDecimal.valueOf(-1)));
    }
}
