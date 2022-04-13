package team20.mk.ukim.finki.skit.service.Impl;

import org.springframework.stereotype.Service;
import team20.mk.ukim.finki.skit.model.Item;
import team20.mk.ukim.finki.skit.model.Subject;
import team20.mk.ukim.finki.skit.repository.SubjectRepository;
import team20.mk.ukim.finki.skit.service.SubjectService;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {


    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> getAllSubjects() {
        return this.subjectRepository.findAll();
    }

    @Override
    public List<Item> getAllItemsForSubject(Long id) {

        Subject toFind=this.subjectRepository.findById(id).get();

        return toFind.getAllItems();
    }
}
