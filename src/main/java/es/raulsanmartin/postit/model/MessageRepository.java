package es.raulsanmartin.postit.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {
    List<Message> findFirst10ByOrderByTimestampDesc();

    List<Message> findFirst10ByResponseToIsNullOrderByTimestampDesc();

    List<Message> findFirst20ByResponseToIsNullOrderByTimestampDesc();

    List<Message> findFirst10ByUserInOrderByTimestampDesc(List<User> users);

    List<Message> findByUserOrderByTimestampDesc(User user);

    List<Message> findByUserAndResponseToIsNullOrderByTimestampDesc(User user);

    List<Message> findByResponseToOrderByTimestampAsc(Message message);
}