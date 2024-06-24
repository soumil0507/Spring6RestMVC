package bose.soumil.spring6restmvc.controller;

import bose.soumil.spring6restmvc.model.Beer;
import bose.soumil.spring6restmvc.services.BeerService;
import bose.soumil.spring6restmvc.services.BeerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

//    asking spring boot to provide object mapper

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;

    @BeforeEach
    void setup() {
        //     this is going to set up a new beer list before each test
        beerServiceImpl = new BeerServiceImpl();

    }

    //    --------------------------- Test Delete Beer ----------------------------------

    @Test
    void testPatchBeer() throws Exception{
        Beer beer = beerServiceImpl.listBeers().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mockMvc.perform(patch("/api/v1/beer/"+beer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }


    //    --------------------------- Test Delete Beer ----------------------------------

    @Test
    void testDeleteBeer() throws Exception{
        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(delete("/api/v1/beer/"+beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //        making sure the argument we are passing is getting passed
        ArgumentCaptor<UUID> uuidargumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(beerService).deleteById(uuidargumentCaptor.capture());

        //        asserting that the id we passed is actually being passed
        assertThat(beer.getId()).isEqualTo(uuidargumentCaptor.getValue());
    }


    //    --------------------------- Test Put Beer ----------------------------------

    @Test
    void testUpdateBeer() throws Exception {

        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(put("/api/v1/beer/" + beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        verify(beerService).updateBeerById(any(UUID.class), any(Beer.class));
    }


    //    --------------------------- Test createBeer ----------------------------------


    @Test
    void testCreateNewBeer() throws Exception{
//        jackson has object mapper which we can use to serialize and deserialize data
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.findAndRegisterModules();

        Beer beer = beerServiceImpl.listBeers().get(0);

//        System.out.println(objectMapper.writeValueAsString(beer));

//        we are going to pass this beer object to create a beer
        beer.setVersion(null);
        beer.setId(null);

        BDDMockito.given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        System.out.println(beerServiceImpl.listBeers());

    }


    //    --------------------------- Test listBeers ----------------------------------
    @Test
    void testListBeers() throws Exception {
        BDDMockito.given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", Is.is(3)));
    }


//    --------------------------- Test getBeerByID ----------------------------------
    @Test
    void getBeerByID() throws Exception {

//        initialize an object of beerServiceImpl and get the first id

        Beer testBeer = beerServiceImpl.listBeers().get(0);

//        whenever we run getBeerById it will return testBeer

        BDDMockito.given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

//        Mockito.when(beerService.getBeerById(any(UUID.class))).thenReturn(testBeer);

        mockMvc.perform(get("/api/v1/beer/"+ testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", Is.is(testBeer.getBeerName())));
    }
}