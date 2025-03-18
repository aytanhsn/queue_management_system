package jpaprojects.queuemanagementsystem3.service;
import jpaprojects.queuemanagementsystem3.entity.Queue;
import jpaprojects.queuemanagementsystem3.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QueueService {

    private final QueueRepository queueRepository;

    public QueueService(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    public Queue createQueue(String serviceName) {
        Queue queue = new Queue();
        queue.setQueueNumber(UUID.randomUUID().toString());
        queue.setServiceName(serviceName);
        queue.setStatus("Waiting");
        queue.setCreatedAt(LocalDateTime.now());
        queueRepository.save(queue);
        return queue;
    }

    public Queue getQueueStatus(String queueNumber) {
        return queueRepository.findByQueueNumber(queueNumber).orElse(null);
    }

    public Queue operateService() {
        Queue queue = queueRepository.findAll().stream()
                .filter(q -> q.getStatus().equals("Waiting"))
                .findFirst()
                .orElse(null);
        if (queue == null) {
            return null;
        }
        queue.setStatus("Operating");
        queueRepository.save(queue);
        return queue;
    }

    public Queue terminateService(String queueNumber) {
        Queue queue = queueRepository.findByQueueNumber(queueNumber).orElse(null);
        if (queue == null) {
            return null;
        }
        queue.setStatus("Terminating");
        queueRepository.save(queue);
        return queue;
    }

    @Scheduled(fixedRate = 60000)
    public void deleteExpiredQueue() {
        List<Queue> queues = queueRepository.findAll();
        queues.stream()
                .filter(q -> q.getStatus().equals("Waiting") && q.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(15)))
                .forEach(q -> {
                    q.setStatus("Expired");
                    queueRepository.save(q);
                });
    }

    public List<Queue> getAllQueues() {
        return queueRepository.findAll();
    }
}

