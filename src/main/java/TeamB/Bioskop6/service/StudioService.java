package TeamB.Bioskop6.service;

import java.util.List;

import TeamB.Bioskop6.entity.Studio;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;

public interface StudioService {
    List<Studio> getAllStudios();

    Studio getStudioById(Integer id) throws ResourceNotFoundException;

    Studio createStudio(Studio studio) throws ResourceAlreadyExistException;

    Studio updateStudio(Studio studio) throws ResourceNotFoundException;

    void deleteStudio(Integer id) throws ResourceNotFoundException;
}
