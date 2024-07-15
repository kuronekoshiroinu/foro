import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicoController {

    private final TopicoRepository topicoRepository;

    @Autowired
    public TopicoController(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    @GetMapping
    public List<Topico> getAllTopics() {
        return topicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> getTopicById(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        return optionalTopico.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Topico> createTopic(@RequestBody Topico topico) {
        Topico savedTopico = topicoRepository.save(topico);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTopico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> updateTopic(@PathVariable Long id, @RequestBody Topico topico) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            Topico existingTopico = optionalTopico.get();
            existingTopico.setTitulo(topico.getTitulo());
            existingTopico.setContenido(topico.getContenido());
            topicoRepository.save(existingTopico);
            return ResponseEntity.ok(existingTopico);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
