package jpaprojects.queuemanagementsystem3.repository;

import jpaprojects.queuemanagementsystem3.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {
    Optional<Queue> findByQueueNumber(String queueNumber);
}
