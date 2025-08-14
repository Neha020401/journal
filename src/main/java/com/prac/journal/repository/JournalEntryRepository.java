package com.prac.journal.repository;

import com.prac.journal.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository <JournalEntry  , ObjectId>{


}
