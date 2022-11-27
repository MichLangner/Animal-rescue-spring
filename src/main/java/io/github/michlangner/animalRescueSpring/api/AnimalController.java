package io.github.michlangner.animalRescueSpring.api;

import io.github.michlangner.animalRescueSpring.api.dto.AllAnimalsResponse;
import io.github.michlangner.animalRescueSpring.api.dto.CreateAnimalRequest;
import io.github.michlangner.animalRescueSpring.api.dto.ErrorDto;
import io.github.michlangner.animalRescueSpring.api.dto.SingleAnimalResponse;
import io.github.michlangner.animalRescueSpring.domain.Animal;
import io.github.michlangner.animalRescueSpring.domain.Specie;
import io.github.michlangner.animalRescueSpring.infrastructure.AnimalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping(path = "/animals")
    public AllAnimalsResponse getAnimals(@RequestParam(required = false, defaultValue = "3") Integer limit) {
        AllAnimalsResponse animalsResponse = new AllAnimalsResponse(
                animalService.listOfAnimals(limit));
        return animalsResponse;
    }

    @GetMapping(path = "/animals/{id}")
    public SingleAnimalResponse getById(@PathVariable String id) {
        log.info(id);
        return new SingleAnimalResponse(
                animalService.singleAnimal(id).getAge(),
                animalService.singleAnimal(id).getSpecie());
    }

    @PutMapping(path = "/animals/{specie}/{id}")
    public ResponseEntity<Animal> upsertAnimal(
            @PathVariable(name = "id") String id,
            @PathVariable String specie,
            @RequestBody CreateAnimalRequest createAnimalRequest) {
        log.info("Animal exists : {}", animalService.animalExist(id));
        Animal animal = animalService.createAnimal((
                        parseStringToSpecie(specie)),
                createAnimalRequest.getAge(),
                createAnimalRequest.getName(),
                id);
        return ResponseEntity.ok().body(animal);
    }

    @PostMapping(path = "/animals/{specie}") // pobieramy z frontu i tworzymy zasoby
    public ResponseEntity<Animal> addAnimal(
            @Valid
            @RequestBody CreateAnimalRequest request,
            @PathVariable String specie) {
        log.info(request.toString());
        log.info(specie);
        Animal animal = animalService.createAnimal(parseStringToSpecie(specie), request.getAge(), request.getName(), null);
        return ResponseEntity.created(URI.create("/animals")).body(animal);
    }

    @DeleteMapping(path = "/animals/{id}")
    public ResponseEntity<Animal> deleteAnimal(@PathVariable String id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }

    private Specie parseStringToSpecie(String rawSpecie) {
        return Arrays.stream(Specie.values())
                .filter(specie -> specie.getPluralValue().equals(rawSpecie))
                .findFirst().get();
    }

    // exception handler - wywołuje metode w przypadku wykrycia błedu, jak poleci wyjątek i zwróci wartość jaką podam
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getAllErrors().
                forEach(s -> errorMap.put(((FieldError) s).getField(), s.getDefaultMessage()));
        String message = errorMap.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(new ErrorDto(message, Instant.now(), HttpStatus.BAD_REQUEST));

    }

}
