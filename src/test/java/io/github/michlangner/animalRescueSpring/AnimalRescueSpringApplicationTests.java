package io.github.michlangner.animalRescueSpring;

import io.github.michlangner.animalRescueSpring.api.dto.AllAnimalsResponse;
import io.github.michlangner.animalRescueSpring.api.dto.ErrorDto;
import io.github.michlangner.animalRescueSpring.api.dto.SingleAnimalResponse;
import io.github.michlangner.animalRescueSpring.domain.Dog;
import io.github.michlangner.animalRescueSpring.domain.Specie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnimalRescueSpringApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenApplicationStartShouldCreateRestTemplate() {
        assertThat(restTemplate).isNotNull();
    }

    @Test
    void whenApplicationStartShloudReturnEmptyListOfAnimal() {
        //given
        String getAllAnimalsUrl = "/animals";
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Integer expectedAnimalCount = 0;

        //when
        ResponseEntity<AllAnimalsResponse> animalEntity =
                restTemplate.getForEntity(getAllAnimalsUrl, AllAnimalsResponse.class);

        //then
        assertThat(animalEntity.getStatusCode()).isEqualTo(expectedStatusCode);
        assertThat(animalEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(animalEntity.hasBody()).isTrue();
        assertThat(animalEntity.getBody().getAnimals().isEmpty()).isTrue();
        assertThat(animalEntity.getBody().getTotalAnimals()).isEqualTo(expectedAnimalCount);
    }
    @Test
    void whenUserAddAnimalApplicationShouldReturnThisAnimalAndListOdAnimalIsNotEmpty() {
        //given
        String getDogUrl;
        Integer dogAge = 28;
        Specie specie = Specie.DOG;
        String animalBody = """
                {"name": "Miauczek",
                "age": 28
                }
                """;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> httpEntity = new HttpEntity<>(animalBody,httpHeaders);
        String postAnimalUrl = "/animals/dogs";
        HttpStatus expectedStatusCode = HttpStatus.CREATED;


        //create dog
        ResponseEntity<Dog> addDog =
                restTemplate.postForEntity(postAnimalUrl, httpEntity, Dog.class);
        String dogId = addDog.getBody().getId();
        getDogUrl = "/animals/" + dogId;
        ResponseEntity<SingleAnimalResponse> allDogsEntity =
                restTemplate.getForEntity(getDogUrl, SingleAnimalResponse.class);


        //dog created
        assertThat(addDog.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(allDogsEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(allDogsEntity.getBody().getAge()).isEqualTo(dogAge);
        assertThat(allDogsEntity.getBody().getSpecie()).isEqualTo(specie);

    }
    @ParameterizedTest
    @MethodSource("badRequestArguments")
    void shouldReturnBadRequestWhenAgeIsNegative(String name, Integer age, String message){
        // given
        String animalBody = """
            {"name": "%s",
            "age": %d
            }
            """;
        String formattedAnimalBody = String.format(animalBody, name, age);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(formattedAnimalBody, httpHeaders);
        String postAnimalUrl = "/animals/dogs";
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

        //when
        ResponseEntity<ErrorDto> stringResponseEntity =
                restTemplate.postForEntity(postAnimalUrl, stringHttpEntity, ErrorDto.class);
        assertThat(stringResponseEntity.getStatusCode()).isEqualTo(expectedStatus);
        assertThat(stringResponseEntity.getBody().getMessage()).isEqualTo(message);
    }
    static Stream<Arguments> badRequestArguments(){
        return Stream.of(
                Arguments.of("Reksio", null , "age: Age cannot be null"),
                Arguments.of("R", 10, "name: Name have to be between 2 or 30"),
                Arguments.of("Reksio", 2500, "age: Max age is 200"),
                Arguments.of("    ", 10, "name: Name cannot be empty"));
    }

}
