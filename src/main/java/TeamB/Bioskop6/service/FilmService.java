package TeamB.Bioskop6.service;

import TeamB.Bioskop6.Helpers.DataNotFoundException;
import TeamB.Bioskop6.entity.Film;
import TeamB.Bioskop6.repository.FilmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;

    public List<Film> getAllFilm(){
        return this.filmRepository.findAll();
    }

    public Film getOneFilm(Integer id) throws DataNotFoundException {
        Optional<Film> optionalFilm = this.filmRepository.findById(id);

        if(optionalFilm.isEmpty()){
            throw new DataNotFoundException("Film is Not Available");
        }

        return optionalFilm.get();

    }

    public Film createFilm (Film film){
        return this.filmRepository.save(film);
    }

    public Film updateFilm(Film film) throws DataNotFoundException {
        this.getOneFilm(film.getFilmCode());

        return this.filmRepository.save(film);
    }

    public void deleteFilm(Film films) throws DataNotFoundException{
        Optional<Film> deleteFilm = this.filmRepository.findById(films.getFilmCode());

        if(deleteFilm.isEmpty()){
            throw new DataNotFoundException("Film is Not Found");
        }

        this.filmRepository.delete(deleteFilm.get());
    }
}
