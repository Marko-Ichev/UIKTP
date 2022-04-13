package team20.mk.ukim.finki.skit.service;

import team20.mk.ukim.finki.skit.model.Item;
import team20.mk.ukim.finki.skit.model.Subject;

import java.util.List;

public interface SubjectService {

    List<Subject> getAllSubjects();
    List<Item> getAllItemsForSubject(Long id);
}
