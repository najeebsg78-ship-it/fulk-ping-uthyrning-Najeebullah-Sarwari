package se.fulkoping.uthyrning.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.fulkoping.uthyrning.dto.SakRequest;
import se.fulkoping.uthyrning.dto.SakResponse;
import se.fulkoping.uthyrning.exception.ConflictException;
import se.fulkoping.uthyrning.exception.NotFoundException;
import se.fulkoping.uthyrning.model.Sak;
import se.fulkoping.uthyrning.repository.PersonRepository;
import se.fulkoping.uthyrning.repository.SakRepository;

import java.util.List;

@Service
public class SakService {

    private final SakRepository sakRepository;
    private final PersonRepository personRepository;

    //Constructor
    public SakService(SakRepository sakRepository, PersonRepository personRepository) {
        this.sakRepository = sakRepository;
        this.personRepository = personRepository;
    }

    //findSak
    private Sak findSak(Long id) {
        return sakRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("saken med id " + id + " fanns inte"));
    }

    //toResponse
    private SakResponse toResponse(Sak sak) {
        return new SakResponse(
                sak.getId(),
                sak.getName(),
                sak.getDescription(),
                sak.getPrice()
        );
    }

    //getAll
    @Transactional(readOnly = true)
    public List<SakResponse> getAll() {
        return sakRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //getById
    @Transactional(readOnly = true)
    public SakResponse getById(Long id) {
        Sak sak = findSak(id);
        SakResponse sakResponse = toResponse(sak);
        return sakResponse;

    }

    //create
    @Transactional
    public SakResponse create(SakRequest sakRequest) {
        Sak sak = new Sak(
                sakRequest.name(),
                sakRequest.description(),
                sakRequest.price()
        );

        Sak saved = sakRepository.save(sak);
        return toResponse(saved);
    }

    //update
    @Transactional
    public SakResponse update (Long id, SakRequest sakRequest){
        Sak sak = findSak(id);
        sak.setName(sakRequest.name());
        sak.setDescription(sakRequest.description());
        sak.setPrice(sakRequest.price());

        Sak saved = sakRepository.save(sak);
        return toResponse(saved);
    }

    //delete
    @Transactional
    public void delete (Long id){
        Sak sak = findSak(id);

        if (personRepository.existsBySakerId(id)){
            throw new ConflictException("saken med id " + id + " är kopplad till en person och kan inte tas bort");
        }

        sakRepository.delete(sak);
    }

}
