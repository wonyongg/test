package resttemplate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import resttemplate.dto.Dto;

public interface ServerService {

    public Dto.Response check(Dto.Post post) throws JsonProcessingException;
}
