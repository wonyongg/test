package resttemplate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import resttemplate.dto.Dto;

import java.io.IOException;
import java.util.List;

public interface ServerService {

    Dto.Response check(Dto.Post post) throws JsonProcessingException;

    List<Dto.Response> getList() throws IOException;

    Dto.Response getPlayer(String name, String team) throws JsonProcessingException;
}
