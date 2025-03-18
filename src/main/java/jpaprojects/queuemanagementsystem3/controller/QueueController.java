package jpaprojects.queuemanagementsystem3.controller;
import jpaprojects.queuemanagementsystem3.service.QueueService;
import jpaprojects.queuemanagementsystem3.entity.Queue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queue")
public class QueueController {
    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping("/create")
    public Queue createQueue(@RequestParam String serviceName) {
        return queueService.createQueue(serviceName);
    }

    @GetMapping("/status/{queueNumber}")
    public Queue getStatus(@PathVariable String queueNumber) {
        return queueService.getQueueStatus(queueNumber);
    }

    @PostMapping("/call-next")
    public Queue callNext() {
        return queueService.operateService();
    }

    @PostMapping("/terminate/{queueNumber}")
    public Queue terminateQueue(@PathVariable String queueNumber) {
        return queueService.terminateService(queueNumber);
    }

    @DeleteMapping("/delete-expired")
    public String deleteQueue() {
        queueService.deleteExpiredQueue();
        return "Expired queues deleted successfully.";
    }

    @GetMapping("/all")
    public List<Queue> getAllQueues() {
        return queueService.getAllQueues();
    }
}

