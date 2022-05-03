package team20.mk.ukim.finki.skit.service;

import team20.mk.ukim.finki.skit.model.Item;
import team20.mk.ukim.finki.skit.model.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    Optional<Subject> save(String name);
    List<Subject> getAllSubjects();
    List<Item> getAllItemsForSubject(Long id);
}
