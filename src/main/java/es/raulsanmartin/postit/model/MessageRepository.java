package es.raulsanmartin.postit.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {
    List<Message> findFirst10ByOrderByTimestampDesc();

    List<Message> findByUserOrderByTimestampDesc(User user);
}