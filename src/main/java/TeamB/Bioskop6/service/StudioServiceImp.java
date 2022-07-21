package TeamB.Bioskop6.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import TeamB.Bioskop6.entity.Studio;
import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.repository.StudioRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudioServiceImp implements StudioService {
    
    private StudioRepository studioRepository;

    @Override
    public List<Studio> getAllStudios() {
        List<Studio> studioList = studioRepository.findAll();
        return studioList;
    }

    @Override
    public Studio getStudioById(Integer id) throws ResourceNotFoundException {
        Optional<Studio> optionalStudio = studioRepository.findById(id);
        if (optionalStudio.isEmpty()) {
            throw new ResourceNotFoundException("Studio with ID "+ id + " is not available!");
        }
        return optionalStudio.get();
    }

    @Override
    public Studio createStudio(Studio studio) throws ResourceAlreadyExistException {
        Optional<Studio> optionalStudio = studioRepository.findById(studio.getStudioId());
        if (optionalStudio.isPresent()) {
            throw new ResourceAlreadyExistException("Studio with ID " + studio.getStudioId() + " is already exists!");
        }
        Studio newStudio = studioRepository.save(studio);
        return newStudio;
    }

    @Override
    public Studio updateStudio(Studio studio) throws ResourceNotFoundException {
        Optional<Studio> optionalStudio = studioRepository.findById(studio.getStudioId());
        if (optionalStudio.isEmpty()) {
            throw new ResourceNotFoundException("Studio with ID " + studio.getStudioId() + " is not available!");
        }
        Studio updatedStudio = studioRepository.save(studio);
        return updatedStudio;
    }

    @Override
    public void deleteStudio(Integer id) throws ResourceNotFoundException {
        Optional<Studio> optionalStudio = studioRepository.findById(id);
        if (optionalStudio.isEmpty()) {
            throw new ResourceNotFoundException("Studio with ID " + id + " is not available!");
        }
        studioRepository.delete(optionalStudio.get());
    }
}
