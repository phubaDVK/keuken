package be.vdab.keuken.repository;

import be.vdab.keuken.domain.Artikel;

import java.util.Optional;

public interface ArtikelRepository {
    Optional<Artikel> findById(long id);
}
