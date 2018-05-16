package patterra.domain.repos;

import patterra.domain.Invention;

import java.util.List;

public interface InventionRepository {
    List<Invention> findAll();
    Invention findOne();
    boolean update(Invention invention);
}
