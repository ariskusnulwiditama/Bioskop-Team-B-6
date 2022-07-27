package TeamB.Bioskop6.controller;

import TeamB.Bioskop6.helper.ResourceAlreadyExistException;
import TeamB.Bioskop6.helper.ResourceNotFoundException;
import TeamB.Bioskop6.dto.FilmRequestDTO;
import TeamB.Bioskop6.service.FilmServiceImpl;
import TeamB.Bioskop6.service.ReportPDFImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Tag(name = "7. Film Controller")
@SecurityRequirement(name = "bearer-key")
public class FilmController {

    @Autowired
    private final FilmServiceImpl filmService;
    private HttpServletResponse response;
    private ReportPDFImpl reportReservation;

    /***
     * Get All data from film table into list
     * @return
     */
    @GetMapping("/film")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getAll() {
        return filmService.getAllFilm();
    }

    /***
     * Get a data from film table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("/film/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Integer id) throws ResourceNotFoundException {
        return filmService.getFilmById(id);
    }

    /***
     * Create a new data in film table
     * @param filmRequestDTO
     * @return
     * @throws ResourceAlreadyExistException
     */
    @PostMapping("/film/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> create(FilmRequestDTO filmRequestDTO) throws ResourceAlreadyExistException {
        return filmService.createFilm(filmRequestDTO);
    }

    /***
     * Update existing data in film table
     * @param filmRequestDTO
     * @return
     * @throws ResourceNotFoundException
     */
    @PutMapping("/film/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> update(FilmRequestDTO filmRequestDTO) throws ResourceNotFoundException {
        return filmService.updateFilm(filmRequestDTO);
    }

    /***
     * Delete existing data in film table by its ID
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/film/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws ResourceNotFoundException {
        return filmService.deleteFilm(id);
    }

    /***
     * print report of films table
     * @return void
     * @throws Exception
     */
    @GetMapping("/films/print")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void printReport() throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"report.pdf\"");
        JasperPrint jasperPrint = this.reportReservation.generateJasperPrint();
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
