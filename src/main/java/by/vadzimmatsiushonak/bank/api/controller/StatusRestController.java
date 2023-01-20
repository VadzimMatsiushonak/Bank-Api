package by.vadzimmatsiushonak.bank.api.controller;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static org.springframework.http.HttpStatus.CREATED;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "_Status", description = EMPTY_DESCRIPTION)
@RestController
public class StatusRestController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${kafka.topic.logging}")
    private String loggingTopic;

    @GetMapping("/api/v1/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/api/v1/kafka/log")
    public ResponseEntity<String> kafkaLogStatus(@RequestBody String msg) {
        kafkaTemplate.send(loggingTopic, msg);
        return ResponseEntity.status(CREATED).body(msg);
    }

}
