package com.studentspace.repository;
import com.studentspace.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByModuleIdOrderByDateEnvoiAsc(Long moduleId);
    long countByModuleId(Long moduleId);
}
