package com.prac.journal.service;


import com.prac.journal.entity.JournalEntry;
import com.prac.journal.entity.User;
import com.prac.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private  UserService userService;
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName){
        User user = userService.findbyUserName(userName);
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalentries().add(saved);
        userService.saveEntry(user);
        return  saved ;
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll() ;
    }

    public Optional<JournalEntry> findbyId(ObjectId id){
        return  journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
        User user = userService.findbyUserName(userName);
        user.getJournalentries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }
}

